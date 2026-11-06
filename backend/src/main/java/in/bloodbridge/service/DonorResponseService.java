package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.DonorResponseDto;
import in.bloodbridge.entity.BloodRequest;
import in.bloodbridge.entity.DonorProfile;
import in.bloodbridge.entity.DonorResponse;
import in.bloodbridge.entity.User;
import in.bloodbridge.enums.DonorResponseStatus;
import in.bloodbridge.enums.NotificationType;
import in.bloodbridge.enums.RequestStatus;
import in.bloodbridge.exception.ConflictException;
import in.bloodbridge.exception.ResourceNotFoundException;
import in.bloodbridge.repository.BloodRequestRepository;
import in.bloodbridge.repository.DonorProfileRepository;
import in.bloodbridge.repository.DonorResponseRepository;
import in.bloodbridge.repository.UserRepository;
import in.bloodbridge.statemachine.RequestStateMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class DonorResponseService {
    private static final Logger log = LoggerFactory.getLogger(DonorResponseService.class);

    public DonorResponseService(DonorResponseRepository donorResponseRepository, BloodRequestRepository bloodRequestRepository, UserRepository userRepository, DonorProfileRepository donorProfileRepository, RequestStateMachine stateMachine, NotificationService notificationService, AuditService auditService) {
        this.donorResponseRepository = donorResponseRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.userRepository = userRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.stateMachine = stateMachine;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }


    private final DonorResponseRepository donorResponseRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final RequestStateMachine stateMachine;
    private final NotificationService notificationService;
    private final AuditService auditService;

    @Transactional
    public DonorResponseDto respondToRequest(UUID donorUserId, UUID requestId, DonorResponseStatus responseStatus, String reason, boolean contactConsent, String ipAddress, String userAgent) {
        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));
        User donorUser = userRepository.findById(donorUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor user not found"));

        if (stateMachine.isTerminal(request.getStatus())) {
            throw new ConflictException("Cannot respond to request that is already " + request.getStatus());
        }

        Optional<DonorResponse> existingOpt = donorResponseRepository.findByRequestIdAndDonorUserId(requestId, donorUserId);
        DonorResponse response = existingOpt.orElseGet(() -> DonorResponse.builder()
                .request(request)
                .donorUser(donorUser)
                .build());

        response.setResponse(responseStatus);
        response.setReason(reason);
        response.setContactConsent(contactConsent);
        response.setConsentExpiresAt(contactConsent ? Instant.now().plus(48, ChronoUnit.HOURS) : null);
        response = donorResponseRepository.save(response);

        // If accepted and request is ACTIVE, transition request to DONOR_RESPONDED
        if (responseStatus == DonorResponseStatus.ACCEPTED && request.getStatus() == RequestStatus.ACTIVE) {
            stateMachine.validateTransition(request.getStatus(), RequestStatus.DONOR_RESPONDED);
            request.setStatus(RequestStatus.DONOR_RESPONDED);
            bloodRequestRepository.save(request);
        }

        // Notify requester
        notificationService.dispatch(
                request.getRequester().getId(),
                NotificationType.DONOR_RESPONSE,
                "Donor Responded: " + responseStatus,
                "A donor has " + responseStatus.name().toLowerCase() + " your request for " + request.getRequestCode() + ".",
                "{\"requestCode\":\"" + request.getRequestCode() + "\"}",
                request.getRequestCode(),
                false
        );

        auditService.logAction(donorUserId, "DONOR_RESPONSE", "donor_responses",
                response.getId().toString(), "Response: " + responseStatus + " on request " + request.getRequestCode(), ipAddress, userAgent);

        return mapToDto(response, false);
    }

    @Transactional(readOnly = true)
    public Page<DonorResponseDto> getResponsesForRequest(UUID requestId, UUID requesterUserId, Pageable pageable) {
        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));

        boolean isAuthorizedRequester = request.getRequester().getId().equals(requesterUserId);

        return donorResponseRepository.findByRequestId(requestId, pageable)
                .map(resp -> {
                    // Only reveal contact if caller is the requester AND donor gave unexpired consent
                    boolean canReveal = isAuthorizedRequester
                            && resp.isContactConsent()
                            && resp.getConsentExpiresAt() != null
                            && resp.getConsentExpiresAt().isAfter(Instant.now());
                    return mapToDto(resp, canReveal);
                });
    }

    @Transactional(readOnly = true)
    public Page<DonorResponseDto> getResponsesByDonor(UUID donorUserId, Pageable pageable) {
        return donorResponseRepository.findByDonorUserId(donorUserId, pageable)
                .map(resp -> mapToDto(resp, false));
    }

    private DonorResponseDto mapToDto(DonorResponse r, boolean revealPhone) {
        DonorProfile dp = donorProfileRepository.findByUserId(r.getDonorUser().getId()).orElse(null);
        String bloodGroup = (dp != null) ? dp.getBloodGroup().getLabel() : "";
        String maskedName = (dp != null) ? maskName(dp.getFullName()) : "Donor";

        return DonorResponseDto.builder()
                .id(r.getId())
                .requestId(r.getRequest().getId())
                .requestCode(r.getRequest().getRequestCode())
                .donorUserId(r.getDonorUser().getId())
                .maskedDonorName(maskedName)
                .donorBloodGroup(bloodGroup)
                .response(r.getResponse())
                .reason(r.getReason())
                .contactConsent(r.isContactConsent())
                .consentExpiresAt(r.getConsentExpiresAt())
                .revealedPhone(revealPhone ? r.getDonorUser().getPhone() : null)
                .createdAt(r.getCreatedAt())
                .build();
    }

    private String maskName(String name) {
        if (name == null || name.isBlank()) return "Voluntary Donor";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0];
        return parts[0] + " " + parts[parts.length - 1].charAt(0) + ".";
    }
}
