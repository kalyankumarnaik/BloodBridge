package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.BloodRequestDto;
import in.bloodbridge.dto.EmergencyRequestCreateDto;
import in.bloodbridge.entity.BloodRequest;
import in.bloodbridge.entity.DonorProfile;
import in.bloodbridge.entity.HospitalProfile;
import in.bloodbridge.entity.User;
import in.bloodbridge.enums.*;
import in.bloodbridge.exception.ConflictException;
import in.bloodbridge.exception.ForbiddenException;
import in.bloodbridge.exception.ResourceNotFoundException;
import in.bloodbridge.repository.BloodRequestRepository;
import in.bloodbridge.repository.DonorProfileRepository;
import in.bloodbridge.repository.DonorResponseRepository;
import in.bloodbridge.repository.HospitalProfileRepository;
import in.bloodbridge.repository.UserRepository;
import in.bloodbridge.statemachine.RequestStateMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BloodRequestService {
    private static final Logger log = LoggerFactory.getLogger(BloodRequestService.class);

    public BloodRequestService(BloodRequestRepository bloodRequestRepository, UserRepository userRepository, HospitalProfileRepository hospitalProfileRepository, DonorProfileRepository donorProfileRepository, DonorResponseRepository donorResponseRepository, RequestStateMachine stateMachine, CompatibilityService compatibilityService, GeolocationService geolocationService, NotificationService notificationService, AuditService auditService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.userRepository = userRepository;
        this.hospitalProfileRepository = hospitalProfileRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.donorResponseRepository = donorResponseRepository;
        this.stateMachine = stateMachine;
        this.compatibilityService = compatibilityService;
        this.geolocationService = geolocationService;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }


    private final BloodRequestRepository bloodRequestRepository;
    private final UserRepository userRepository;
    private final HospitalProfileRepository hospitalProfileRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final DonorResponseRepository donorResponseRepository;
    private final RequestStateMachine stateMachine;
    private final CompatibilityService compatibilityService;
    private final GeolocationService geolocationService;
    private final NotificationService notificationService;
    private final AuditService auditService;

    private static final String BASE32_CHARS = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional
    public BloodRequestDto createEmergencyRequest(UUID requesterUserId, EmergencyRequestCreateDto dto, String ipAddress, String userAgent) {
        User requester = userRepository.findById(requesterUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Cap open requests per requester (max 3)
        long openCount = bloodRequestRepository.countByRequesterIdAndStatusNotIn(
                requesterUserId,
                List.of(RequestStatus.FULFILLED, RequestStatus.CANCELLED, RequestStatus.EXPIRED)
        );
        if (openCount >= 3) {
            throw new ConflictException("You already have 3 active/open blood requests. Please complete or close existing requests first.");
        }

        // Duplicate prevention via SHA-256 fingerprint hash
        String fingerprint = generateFingerprintHash(dto.getPatientName(), dto.getBloodGroup(), dto.getHospitalName());
        List<BloodRequest> existing = bloodRequestRepository.findActiveDuplicatesByHash(fingerprint);
        if (!existing.isEmpty()) {
            throw new ConflictException("An active request already exists for this patient and blood group at " + dto.getHospitalName() +
                    ". Request ID: " + existing.get(0).getRequestCode());
        }

        HospitalProfile hospital = null;
        if (dto.getHospitalProfileId() != null) {
            hospital = hospitalProfileRepository.findById(dto.getHospitalProfileId()).orElse(null);
        }

        String requestCode = generateRequestCode();

        // Auto-verify if created by a verified hospital or admin
        boolean autoVerify = (requester.getRole() == Role.ROLE_HOSPITAL && requester.getStatus() == UserStatus.ACTIVE)
                || requester.getRole() == Role.ROLE_ADMIN;

        RequestStatus initialStatus = autoVerify ? RequestStatus.ACTIVE : RequestStatus.PENDING;

        BloodRequest request = BloodRequest.builder()
                .requestCode(requestCode)
                .requester(requester)
                .hospitalProfile(hospital)
                .patientName(dto.getPatientName().trim())
                .bloodGroup(dto.getBloodGroup())
                .units(dto.getUnits())
                .unitsFulfilled(0)
                .hospitalName(dto.getHospitalName().trim())
                .hospitalAddress(dto.getHospitalAddress().trim())
                .city(dto.getCity().trim())
                .state(dto.getState() != null ? dto.getState().trim() : "Delhi")
                .pinCode(dto.getPinCode().trim())
                .lat(dto.getLat() != null ? dto.getLat() : 28.6139)
                .lon(dto.getLon() != null ? dto.getLon() : 77.2090)
                .requiredBy(dto.getRequiredBy())
                .priority(dto.getPriority())
                .status(initialStatus)
                .contactPreference(dto.getContactPreference())
                .notes(dto.getNotes())
                .fingerprintHash(fingerprint)
                .verifiedBy(autoVerify ? requester : null)
                .verifiedAt(autoVerify ? Instant.now() : null)
                .build();

        request = bloodRequestRepository.save(request);

        auditService.logAction(requesterUserId, "REQUEST_CREATED", "blood_requests",
                request.getRequestCode(), "Created request with priority " + dto.getPriority(), ipAddress, userAgent);

        if (initialStatus == RequestStatus.ACTIVE) {
            dispatchDonorNotifications(request);
        }

        return mapToDto(request, null, null);
    }

    @Transactional
    public BloodRequestDto verifyAndActivate(UUID requestId, UUID reviewerUserId, String ipAddress, String userAgent) {
        BloodRequest req = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));
        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer user not found"));

        stateMachine.validateTransition(req.getStatus(), RequestStatus.VERIFIED);
        req.setStatus(RequestStatus.VERIFIED);
        req.setVerifiedBy(reviewer);
        req.setVerifiedAt(Instant.now());

        stateMachine.validateTransition(RequestStatus.VERIFIED, RequestStatus.ACTIVE);
        req.setStatus(RequestStatus.ACTIVE);
        req = bloodRequestRepository.save(req);

        dispatchDonorNotifications(req);

        // Notify requester
        notificationService.dispatch(
                req.getRequester().getId(),
                NotificationType.REQUEST_STATUS,
                "Emergency Blood Request Verified & Active",
                "Your blood request " + req.getRequestCode() + " has been verified. Matching donors in your area are being notified.",
                "{\"requestCode\":\"" + req.getRequestCode() + "\"}",
                req.getRequestCode(),
                false
        );

        auditService.logAction(reviewerUserId, "REQUEST_VERIFIED", "blood_requests",
                req.getRequestCode(), "Verified and activated by " + reviewer.getEmail(), ipAddress, userAgent);

        return mapToDto(req, null, null);
    }

    @Transactional
    public BloodRequestDto cancelRequest(UUID requestId, UUID userId, String reason, String ipAddress, String userAgent) {
        BloodRequest req = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!req.getRequester().getId().equals(userId) && user.getRole() != Role.ROLE_ADMIN) {
            throw new ForbiddenException("You are not authorized to cancel this request");
        }

        stateMachine.validateTransition(req.getStatus(), RequestStatus.CANCELLED);
        req.setStatus(RequestStatus.CANCELLED);
        req.setClosedReason(reason != null ? reason : "Cancelled by requester");
        req = bloodRequestRepository.save(req);

        auditService.logAction(userId, "REQUEST_CANCELLED", "blood_requests",
                req.getRequestCode(), "Cancelled: " + req.getClosedReason(), ipAddress, userAgent);

        return mapToDto(req, null, null);
    }

    @Transactional
    public BloodRequestDto fulfillRequest(UUID requestId, UUID userId, String ipAddress, String userAgent) {
        BloodRequest req = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));

        stateMachine.validateTransition(req.getStatus(), RequestStatus.FULFILLED);
        req.setStatus(RequestStatus.FULFILLED);
        req.setUnitsFulfilled(req.getUnits());
        req.setClosedReason("Fulfilled successfully");
        req = bloodRequestRepository.save(req);

        notificationService.dispatch(
                req.getRequester().getId(),
                NotificationType.REQUEST_STATUS,
                "Blood Request Fulfilled!",
                "Great news! Your request " + req.getRequestCode() + " has been successfully fulfilled.",
                "{\"requestCode\":\"" + req.getRequestCode() + "\"}",
                req.getRequestCode(),
                false
        );

        auditService.logAction(userId, "REQUEST_FULFILLED", "blood_requests",
                req.getRequestCode(), "Marked fulfilled", ipAddress, userAgent);

        return mapToDto(req, null, null);
    }

    @Transactional
    public void dispatchDonorNotifications(BloodRequest request) {
        List<BloodGroup> compatibleGroups = compatibilityService.getCompatibleDonorsForRecipient(request.getBloodGroup());
        List<DonorProfile> matchingDonors = donorProfileRepository.findCompatibleEmergencyDonors(compatibleGroups);

        double maxRadiusKm = request.getPriority() == RequestPriority.CRITICAL ? 100.0 : 25.0;
        int waveCount = 0;
        int maxWaveSize = 50; // Cap per wave per PRD Section 6.5

        for (DonorProfile donor : matchingDonors) {
            if (waveCount >= maxWaveSize) break;

            boolean withinRadius = geolocationService.isWithinRadius(
                    request.getLat(), request.getLon(),
                    donor.getApproxLat(), donor.getApproxLon(),
                    maxRadiusKm
            );

            if (withinRadius) {
                String title = String.format("URGENT: %s Blood Needed in %s", request.getBloodGroup().getLabel(), request.getCity());
                String body = String.format("Patient needs %d unit(s) of %s at %s, %s. Urgency: %s. Ref: %s",
                        request.getUnits(), request.getBloodGroup().getLabel(), request.getHospitalName(), request.getCity(),
                        request.getPriority(), request.getRequestCode());

                notificationService.dispatch(
                        donor.getUser().getId(),
                        NotificationType.EMERGENCY_REQUEST,
                        title,
                        body,
                        "{\"requestCode\":\"" + request.getRequestCode() + "\"}",
                        request.getRequestCode(),
                        request.getPriority() == RequestPriority.CRITICAL
                );
                waveCount++;
            }
        }
        log.info("Dispatched notification wave of {} donors for request {}", waveCount, request.getRequestCode());
    }

    @Transactional(readOnly = true)
    public BloodRequestDto getRequestById(UUID id, Double userLat, Double userLon) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found"));
        return mapToDto(req, userLat, userLon);
    }

    @Transactional(readOnly = true)
    public BloodRequestDto getRequestByCode(String code, Double userLat, Double userLon) {
        BloodRequest req = bloodRequestRepository.findByRequestCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with code: " + code));
        return mapToDto(req, userLat, userLon);
    }

    @Transactional(readOnly = true)
    public Page<BloodRequestDto> searchRequests(RequestStatus status, BloodGroup bloodGroup, String city, RequestPriority priority, Pageable pageable, Double userLat, Double userLon) {
        List<BloodGroup> groups = bloodGroup != null ? List.of(bloodGroup) : null;
        return bloodRequestRepository.searchRequests(status, groups, city, priority, pageable)
                .map(r -> mapToDto(r, userLat, userLon));
    }

    @Transactional(readOnly = true)
    public Page<BloodRequestDto> getRequestsByRequester(UUID requesterId, Pageable pageable) {
        return bloodRequestRepository.findByRequesterId(requesterId, pageable)
                .map(r -> mapToDto(r, null, null));
    }

    @Transactional(readOnly = true)
    public List<BloodRequestDto> getCompatibleRequestsForDonor(UUID donorUserId) {
        DonorProfile donor = donorProfileRepository.findByUserId(donorUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found"));

        List<BloodGroup> recipientGroups = compatibilityService.getCompatibleRecipientsForDonor(donor.getBloodGroup());
        List<BloodRequest> active = bloodRequestRepository.findActiveCompatibleRequests(recipientGroups);

        return active.stream()
                .map(r -> mapToDto(r, donor.getApproxLat(), donor.getApproxLon()))
                .filter(dto -> dto.getDistanceKm() == null || dto.getDistanceKm() <= 75.0)
                .toList();
    }

    private BloodRequestDto mapToDto(BloodRequest r, Double userLat, Double userLon) {
        Double dist = null;
        if (userLat != null && userLon != null && r.getLat() != null && r.getLon() != null) {
            dist = geolocationService.calculateDistanceKm(userLat, userLon, r.getLat(), r.getLon());
        }

        int responsesCount = donorResponseRepository.findByRequestId(r.getId()).size();

        String shareMsg = String.format("URGENT BLOOD NEEDED: %s blood required for %s at %s, %s. Help save a life! Request Ref: %s",
                r.getBloodGroup().getLabel(), r.getPatientName(), r.getHospitalName(), r.getCity(), r.getRequestCode());
        String whatsAppUrl = notificationService.generateWhatsAppShareUrl(shareMsg);

        return BloodRequestDto.builder()
                .id(r.getId())
                .requestCode(r.getRequestCode())
                .requesterId(r.getRequester().getId())
                .requesterName(r.getRequester().getEmail())
                .hospitalProfileId(r.getHospitalProfile() != null ? r.getHospitalProfile().getId() : null)
                .hospitalName(r.getHospitalName())
                .hospitalAddress(r.getHospitalAddress())
                .city(r.getCity())
                .state(r.getState())
                .pinCode(r.getPinCode())
                .lat(r.getLat())
                .lon(r.getLon())
                .patientName(r.getPatientName())
                .bloodGroup(r.getBloodGroup())
                .bloodGroupLabel(r.getBloodGroup().getLabel())
                .units(r.getUnits())
                .unitsFulfilled(r.getUnitsFulfilled())
                .priority(r.getPriority())
                .requiredBy(r.getRequiredBy())
                .status(r.getStatus())
                .contactPreference(r.getContactPreference())
                .notes(r.getNotes())
                .verifiedByUserId(r.getVerifiedBy() != null ? r.getVerifiedBy().getId() : null)
                .verifiedAt(r.getVerifiedAt())
                .closedReason(r.getClosedReason())
                .createdAt(r.getCreatedAt())
                .distanceKm(dist)
                .responseCount(responsesCount)
                .shareWhatsAppUrl(whatsAppUrl)
                .build();
    }

    private String generateRequestCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuilder sb = new StringBuilder("BB-").append(datePart).append("-");
        for (int i = 0; i < 6; i++) {
            sb.append(BASE32_CHARS.charAt(RANDOM.nextInt(BASE32_CHARS.length())));
        }
        return sb.toString();
    }

    private String generateFingerprintHash(String patientName, BloodGroup bloodGroup, String hospitalName) {
        try {
            String raw = (patientName.trim().toLowerCase() + "|" + bloodGroup.name() + "|" + hospitalName.trim().toLowerCase());
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 unavailable", e);
        }
    }
}
