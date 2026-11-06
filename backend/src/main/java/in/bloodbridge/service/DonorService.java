package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.*;
import in.bloodbridge.entity.*;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.ContactRequestStatus;
import in.bloodbridge.enums.DonorAvailability;
import in.bloodbridge.enums.NotificationType;
import in.bloodbridge.exception.BadRequestException;
import in.bloodbridge.exception.ForbiddenException;
import in.bloodbridge.exception.ResourceNotFoundException;
import in.bloodbridge.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonorService {
    private static final Logger log = LoggerFactory.getLogger(DonorService.class);

    public DonorService(DonorProfileRepository donorProfileRepository, UserRepository userRepository, ContactRequestRepository contactRequestRepository, BloodRequestRepository bloodRequestRepository, CompatibilityService compatibilityService, GeolocationService geolocationService, NotificationService notificationService, AuditService auditService) {
        this.donorProfileRepository = donorProfileRepository;
        this.userRepository = userRepository;
        this.contactRequestRepository = contactRequestRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.compatibilityService = compatibilityService;
        this.geolocationService = geolocationService;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }


    private final DonorProfileRepository donorProfileRepository;
    private final UserRepository userRepository;
    private final ContactRequestRepository contactRequestRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final CompatibilityService compatibilityService;
    private final GeolocationService geolocationService;
    private final NotificationService notificationService;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public Page<DonorPublicCardDto> searchDonors(DonorSearchCriteriaDto criteria) {
        Set<BloodGroup> targetBloodGroups = null;
        if (criteria.getBloodGroup() != null) {
            targetBloodGroups = new HashSet<>();
            targetBloodGroups.add(criteria.getBloodGroup());
            if (Boolean.TRUE.equals(criteria.getIncludeCompatible())) {
                targetBloodGroups.addAll(compatibilityService.getCompatibleDonorsForRecipient(criteria.getBloodGroup()));
            }
        }

        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<DonorProfile> page = donorProfileRepository.searchDonors(
                targetBloodGroups,
                (criteria.getCity() != null && !criteria.getCity().isBlank()) ? criteria.getCity().trim() : null,
                (criteria.getPinCode() != null && !criteria.getPinCode().isBlank()) ? criteria.getPinCode().trim() : null,
                criteria.getAvailability(),
                criteria.getVerifiedOnly(),
                pageable
        );

        List<DonorPublicCardDto> cards = page.getContent().stream()
                .map(donor -> mapToPublicCardDto(donor, criteria.getLatitude(), criteria.getLongitude()))
                .filter(card -> {
                    if (criteria.getMaxDistanceKm() != null && card.getExactDistanceKm() != null) {
                        return card.getExactDistanceKm() <= criteria.getMaxDistanceKm();
                    }
                    return true;
                })
                .sorted((a, b) -> {
                    if ("verified".equalsIgnoreCase(criteria.getSort())) {
                        return Boolean.compare(b.isVerified(), a.isVerified());
                    }
                    if (a.getExactDistanceKm() != null && b.getExactDistanceKm() != null) {
                        return Double.compare(a.getExactDistanceKm(), b.getExactDistanceKm());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(cards, pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DonorPrivateProfileDto getProfileByUserId(UUID userId) {
        DonorProfile donor = donorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + userId));
        return mapToPrivateProfileDto(donor);
    }

    @Transactional
    public DonorPrivateProfileDto updateProfile(UUID userId, DonorPrivateProfileDto dto) {
        DonorProfile donor = donorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + userId));

        if (dto.getCity() != null) donor.setCity(dto.getCity().trim());
        if (dto.getState() != null) donor.setState(dto.getState().trim());
        if (dto.getPinCode() != null) donor.setPinCode(dto.getPinCode().trim());
        if (dto.getApproxLat() != null) donor.setApproxLat(dto.getApproxLat());
        if (dto.getApproxLon() != null) donor.setApproxLon(dto.getApproxLon());
        if (dto.getLastDonationDate() != null) donor.setLastDonationDate(dto.getLastDonationDate());
        if (dto.getAvailability() != null) donor.setAvailability(dto.getAvailability());

        donor.setShowInSearch(dto.isShowInSearch());
        donor.setShareContactOnAccept(dto.isShareContactOnAccept());
        donor.setEmergencyAlertsEnabled(dto.isEmergencyAlertsEnabled());

        donor = donorProfileRepository.save(donor);
        return mapToPrivateProfileDto(donor);
    }

    @Transactional
    public ContactRequestDto requestContact(UUID requesterUserId, ContactRequestCreateDto dto, String ipAddress, String userAgent) {
        // Enforce daily rate limit of 5 requests per day
        Instant oneDayAgo = Instant.now().minus(24, ChronoUnit.HOURS);
        long recentCount = contactRequestRepository.countByRequesterUserIdAndCreatedAtAfter(requesterUserId, oneDayAgo);
        if (recentCount >= 5) {
            throw new BadRequestException("Daily contact request limit reached (maximum 5 requests per 24 hours).");
        }

        User requester = userRepository.findById(requesterUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Requester user not found"));
        User donorUser = userRepository.findById(dto.getDonorUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Donor user not found"));

        BloodRequest bloodRequest = null;
        if (dto.getRequestId() != null) {
            bloodRequest = bloodRequestRepository.findById(dto.getRequestId()).orElse(null);
        }

        ContactRequest cr = ContactRequest.builder()
                .request(bloodRequest)
                .donorUser(donorUser)
                .requesterUser(requester)
                .status(ContactRequestStatus.PENDING)
                .expiresAt(Instant.now().plus(48, ChronoUnit.HOURS))
                .build();
        cr = contactRequestRepository.save(cr);

        // Notify donor
        String reqCode = bloodRequest != null ? bloodRequest.getRequestCode() : "General";
        notificationService.dispatch(
                donorUser.getId(),
                NotificationType.CONTACT_REQUEST,
                "New Blood Contact Request",
                "A requester has requested to connect with you regarding blood donation inquiry (" + reqCode + ").",
                "{\"contactRequestId\":\"" + cr.getId() + "\"}",
                reqCode,
                false
        );

        auditService.logAction(requesterUserId, "CONTACT_REQUEST_CREATED", "contact_requests",
                cr.getId().toString(), "Requester contacted donor " + donorUser.getId(), ipAddress, userAgent);

        return mapToContactRequestDto(cr, null);
    }

    @Transactional
    public ContactRequestDto respondToContactRequest(UUID donorUserId, UUID contactRequestId, boolean accept, String ipAddress, String userAgent) {
        ContactRequest cr = contactRequestRepository.findById(contactRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact request not found: " + contactRequestId));

        if (!cr.getDonorUser().getId().equals(donorUserId)) {
            throw new ForbiddenException("You are not authorized to respond to this contact request");
        }

        cr.setStatus(accept ? ContactRequestStatus.ACCEPTED : ContactRequestStatus.DECLINED);
        if (accept) {
            cr.setConsentRevealedAt(Instant.now());
        }
        cr = contactRequestRepository.save(cr);

        // If accepted, reveal contact details to requester and audit log
        if (accept) {
            auditService.logAction(donorUserId, "CONTACT_CONSENT_REVEALED", "contact_requests",
                    cr.getId().toString(), "Donor granted contact consent to requester " + cr.getRequesterUser().getId(), ipAddress, userAgent);

            notificationService.dispatch(
                    cr.getRequesterUser().getId(),
                    NotificationType.CONTACT_REQUEST,
                    "Contact Request Accepted!",
                    "Donor " + maskName(cr.getDonorUser().getEmail()) + " has accepted your contact request. Contact details are now accessible in your dashboard.",
                    "{\"contactRequestId\":\"" + cr.getId() + "\"}",
                    cr.getRequest() != null ? cr.getRequest().getRequestCode() : "General",
                    false
            );
        }

        return mapToContactRequestDto(cr, accept ? cr.getDonorUser() : null);
    }

    @Transactional(readOnly = true)
    public Page<ContactRequestDto> getDonorContactRequests(UUID donorUserId, Pageable pageable) {
        return contactRequestRepository.findByDonorUserId(donorUserId, pageable)
                .map(cr -> mapToContactRequestDto(cr, null));
    }

    @Transactional(readOnly = true)
    public Page<ContactRequestDto> getRequesterContactRequests(UUID requesterUserId, Pageable pageable) {
        return contactRequestRepository.findByRequesterUserId(requesterUserId, pageable)
                .map(cr -> {
                    // Only reveal phone if accepted and consent unexpired
                    boolean isRevealed = cr.getStatus() == ContactRequestStatus.ACCEPTED
                            && cr.getExpiresAt() != null && cr.getExpiresAt().isAfter(Instant.now());
                    return mapToContactRequestDto(cr, isRevealed ? cr.getDonorUser() : null);
                });
    }

    private DonorPublicCardDto mapToPublicCardDto(DonorProfile donor, Double reqLat, Double reqLon) {
        Double distanceKm = null;
        if (reqLat != null && reqLon != null && donor.getApproxLat() != null && donor.getApproxLon() != null) {
            distanceKm = geolocationService.calculateDistanceKm(reqLat, reqLon, donor.getApproxLat(), donor.getApproxLon());
        }

        String distanceBand = geolocationService.formatDistanceBand(distanceKm);
        String maskedName = maskName(donor.getFullName());
        String pinCodePrefix = (donor.getPinCode() != null && donor.getPinCode().length() >= 3)
                ? donor.getPinCode().substring(0, 3) + "***"
                : "******";

        String lastActiveBucket = computeLastActiveBucket(donor.getLastActiveAt());
        String informationalReminder = computeCourtesyReminder(donor.getLastDonationDate());

        return DonorPublicCardDto.builder()
                .donorRef(donor.getUser().getId())
                .maskedName(maskedName)
                .bloodGroup(donor.getBloodGroup())
                .bloodGroupLabel(donor.getBloodGroup().getLabel())
                .city(donor.getCity())
                .state(donor.getState())
                .pinCodePrefix(pinCodePrefix)
                .distanceBand(distanceBand)
                .exactDistanceKm(distanceKm)
                .availability(donor.getAvailability())
                .verified(donor.isVerifiedByAdmin())
                .lastActiveBucket(lastActiveBucket)
                .totalDonations(donor.getTotalDonations())
                .informationalReminder(informationalReminder)
                .build();
    }

    private DonorPrivateProfileDto mapToPrivateProfileDto(DonorProfile donor) {
        String reminder = computeCourtesyReminder(donor.getLastDonationDate());
        return DonorPrivateProfileDto.builder()
                .id(donor.getId())
                .userId(donor.getUser().getId())
                .fullName(donor.getFullName())
                .email(donor.getUser().getEmail())
                .phone(donor.getUser().getPhone())
                .bloodGroup(donor.getBloodGroup())
                .bloodGroupLabel(donor.getBloodGroup().getLabel())
                .dob(donor.getDob())
                .city(donor.getCity())
                .state(donor.getState())
                .pinCode(donor.getPinCode())
                .approxLat(donor.getApproxLat())
                .approxLon(donor.getApproxLon())
                .lastDonationDate(donor.getLastDonationDate())
                .availability(donor.getAvailability())
                .showInSearch(donor.isShowInSearch())
                .shareContactOnAccept(donor.isShareContactOnAccept())
                .emergencyAlertsEnabled(donor.isEmergencyAlertsEnabled())
                .verifiedByAdmin(donor.isVerifiedByAdmin())
                .totalDonations(donor.getTotalDonations())
                .informationalReminder(reminder)
                .createdAt(donor.getCreatedAt())
                .build();
    }

    private ContactRequestDto mapToContactRequestDto(ContactRequest cr, User donorUserWithConsent) {
        return ContactRequestDto.builder()
                .id(cr.getId())
                .requestId(cr.getRequest() != null ? cr.getRequest().getId() : null)
                .requestCode(cr.getRequest() != null ? cr.getRequest().getRequestCode() : "Direct Inquiry")
                .donorUserId(cr.getDonorUser().getId())
                .donorName(donorUserWithConsent != null ? donorUserWithConsent.getEmail() : maskName(cr.getDonorUser().getEmail()))
                .requesterUserId(cr.getRequesterUser().getId())
                .requesterName(cr.getRequesterUser().getEmail())
                .status(cr.getStatus())
                .consentRevealedAt(cr.getConsentRevealedAt())
                .expiresAt(cr.getExpiresAt())
                .revealedPhone(donorUserWithConsent != null ? donorUserWithConsent.getPhone() : null)
                .revealedEmail(donorUserWithConsent != null ? donorUserWithConsent.getEmail() : null)
                .createdAt(cr.getCreatedAt())
                .build();
    }

    private String maskName(String name) {
        if (name == null || name.isBlank()) return "Voluntary Donor";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0];
        return parts[0] + " " + parts[parts.length - 1].charAt(0) + ".";
    }

    private String computeLastActiveBucket(Instant lastActiveAt) {
        if (lastActiveAt == null) return "Recently active";
        long hours = ChronoUnit.HOURS.between(lastActiveAt, Instant.now());
        if (hours < 24) return "Active today";
        if (hours < 168) return "Active this week";
        return "Active recently";
    }

    private String computeCourtesyReminder(LocalDate lastDonationDate) {
        if (lastDonationDate == null) {
            return "Informational reminder: No previous donation recorded. Standard health and hemoglobin screening will be evaluated at the donation centre.";
        }
        long daysSince = ChronoUnit.DAYS.between(lastDonationDate, LocalDate.now());
        if (daysSince >= 90) {
            return "Informational reminder: " + daysSince + " days since last recorded donation. Final medical eligibility is determined exclusively by the donation centre.";
        } else {
            long remaining = 90 - daysSince;
            return "Informational reminder: " + remaining + " days remaining before the standard 90-day whole blood interval. Medical eligibility remains subject to clinical assessment.";
        }
    }
}
