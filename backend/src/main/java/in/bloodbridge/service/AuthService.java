package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.AuthRequestDto;
import in.bloodbridge.dto.AuthResponseDto;
import in.bloodbridge.dto.RefreshTokenRequestDto;
import in.bloodbridge.dto.RegisterRequestDto;
import in.bloodbridge.entity.*;
import in.bloodbridge.enums.Role;
import in.bloodbridge.enums.UserStatus;
import in.bloodbridge.enums.VerificationStatus;
import in.bloodbridge.exception.BadRequestException;
import in.bloodbridge.exception.ConflictException;
import in.bloodbridge.exception.UnauthorizedException;
import in.bloodbridge.repository.*;
import in.bloodbridge.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, DonorProfileRepository donorProfileRepository, PatientProfileRepository patientProfileRepository, HospitalProfileRepository hospitalProfileRepository, RefreshTokenRepository refreshTokenRepository, DataDeletionRequestRepository deletionRequestRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, AuditService auditService) {
        this.userRepository = userRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.hospitalProfileRepository = hospitalProfileRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.deletionRequestRepository = deletionRequestRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.auditService = auditService;
    }


    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final HospitalProfileRepository hospitalProfileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final DataDeletionRequestRepository deletionRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuditService auditService;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto req, String ipAddress, String userAgent) {
        if (userRepository.existsByEmailIgnoreCase(req.getEmail().trim())) {
            throw new ConflictException("Email is already registered");
        }
        if (userRepository.existsByPhone(req.getPhone().trim())) {
            throw new ConflictException("Phone number is already registered");
        }

        User user = User.builder()
                .email(req.getEmail().trim().toLowerCase())
                .phone(req.getPhone().trim())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .status(UserStatus.ACTIVE)
                .emailVerifiedAt(Instant.now())
                .phoneVerifiedAt(Instant.now())
                .build();

        user = userRepository.save(user);

        UUID profileId = null;

        if (req.getRole() == Role.ROLE_DONOR) {
            if (req.getBloodGroup() == null || req.getDob() == null) {
                throw new BadRequestException("Blood group and date of birth are required for donor registration");
            }

            DonorProfile donor = DonorProfile.builder()
                    .user(user)
                    .fullName(req.getFullName().trim())
                    .dob(req.getDob())
                    .bloodGroup(req.getBloodGroup())
                    .city(req.getCity() != null ? req.getCity().trim() : "New Delhi")
                    .state(req.getState() != null ? req.getState().trim() : "Delhi")
                    .pinCode(req.getPinCode() != null ? req.getPinCode().trim() : "110001")
                    .approxLat(req.getApproxLat() != null ? req.getApproxLat() : 28.6139)
                    .approxLon(req.getApproxLon() != null ? req.getApproxLon() : 77.2090)
                    .lastDonationDate(req.getLastDonationDate())
                    .showInSearch(req.getShowInSearch() != null ? req.getShowInSearch() : true)
                    .shareContactOnAccept(req.getShareContactOnAccept() != null ? req.getShareContactOnAccept() : false)
                    .emergencyAlertsEnabled(req.getEmergencyAlertsEnabled() != null ? req.getEmergencyAlertsEnabled() : true)
                    .notificationConsentAt(Instant.now())
                    .lastActiveAt(Instant.now())
                    .build();
            donor = donorProfileRepository.save(donor);
            profileId = donor.getId();

        } else if (req.getRole() == Role.ROLE_PATIENT) {
            PatientProfile patient = PatientProfile.builder()
                    .user(user)
                    .fullName(req.getFullName().trim())
                    .city(req.getCity() != null ? req.getCity().trim() : "New Delhi")
                    .state(req.getState() != null ? req.getState().trim() : "Delhi")
                    .relationshipDefault(req.getRelationshipDefault() != null ? req.getRelationshipDefault() : "Self")
                    .build();
            patient = patientProfileRepository.save(patient);
            profileId = patient.getId();

        } else if (req.getRole() == Role.ROLE_HOSPITAL) {
            if (req.getHospitalName() == null || req.getRegistrationNumber() == null) {
                throw new BadRequestException("Hospital name and registration number are required");
            }
            HospitalProfile hospital = HospitalProfile.builder()
                    .user(user)
                    .name(req.getHospitalName().trim())
                    .registrationNumber(req.getRegistrationNumber().trim())
                    .type(req.getHospitalType() != null ? req.getHospitalType().trim() : "GENERAL")
                    .address(req.getAddress() != null ? req.getAddress().trim() : "Hospital Avenue")
                    .city(req.getCity() != null ? req.getCity().trim() : "New Delhi")
                    .state(req.getState() != null ? req.getState().trim() : "Delhi")
                    .pinCode(req.getPinCode() != null ? req.getPinCode().trim() : "110001")
                    .lat(req.getApproxLat() != null ? req.getApproxLat() : 28.6139)
                    .lon(req.getApproxLon() != null ? req.getApproxLon() : 77.2090)
                    .officialPhone(req.getPhone().trim())
                    .contactPerson(req.getContactPerson() != null ? req.getContactPerson().trim() : req.getFullName().trim())
                    .verificationStatus(VerificationStatus.SUBMITTED)
                    .build();
            hospital = hospitalProfileRepository.save(hospital);
            profileId = hospital.getId();
        }

        auditService.logAction(user.getId(), "USER_REGISTER", "users", user.getId().toString(),
                "Registered role: " + user.getRole(), ipAddress, userAgent);

        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());
        UUID familyId = UUID.randomUUID();
        String rawRefreshToken = UUID.randomUUID().toString();
        storeRefreshToken(user, rawRefreshToken, familyId, ipAddress, userAgent);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(rawRefreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(req.getFullName())
                .role(user.getRole())
                .status(user.getStatus())
                .profileId(profileId)
                .bloodGroup(req.getBloodGroup() != null ? req.getBloodGroup().getLabel() : null)
                .city(req.getCity())
                .build();
    }

    @Transactional
    public AuthResponseDto login(AuthRequestDto req, String ipAddress, String userAgent) {
        User user = userRepository.findByEmailIgnoreCase(req.getEmail().trim())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        // Check account lock
        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(Instant.now())) {
            throw new UnauthorizedException("Account is temporarily locked due to multiple failed login attempts. Please try again later.");
        }

        // Verify password
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            user.setFailedLoginCount(user.getFailedLoginCount() + 1);
            if (user.getFailedLoginCount() >= 5) {
                user.setLockedUntil(Instant.now().plus(15, ChronoUnit.MINUTES));
                user.setFailedLoginCount(0);
                log.warn("Account locked for user: {}", user.getEmail());
            }
            userRepository.save(user);

            auditService.logAction(user.getId(), "LOGIN_FAILED", "users", user.getId().toString(),
                    "Failed password attempt", ipAddress, userAgent);
            throw new UnauthorizedException("Invalid email or password");
        }

        // Check account status
        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new UnauthorizedException("Account has been suspended. Please contact platform administrators.");
        }

        // Reset failed login counter on success
        user.setFailedLoginCount(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        UUID profileId = null;
        String bloodGroup = null;
        String city = null;
        String fullName = user.getEmail();

        if (user.getRole() == Role.ROLE_DONOR) {
            DonorProfile dp = donorProfileRepository.findByUserId(user.getId()).orElse(null);
            if (dp != null) {
                profileId = dp.getId();
                bloodGroup = dp.getBloodGroup().getLabel();
                city = dp.getCity();
                fullName = dp.getFullName();
                dp.setLastActiveAt(Instant.now());
                donorProfileRepository.save(dp);
            }
        } else if (user.getRole() == Role.ROLE_HOSPITAL) {
            HospitalProfile hp = hospitalProfileRepository.findByUserId(user.getId()).orElse(null);
            if (hp != null) {
                profileId = hp.getId();
                city = hp.getCity();
                fullName = hp.getName();
            }
        } else if (user.getRole() == Role.ROLE_PATIENT) {
            PatientProfile pp = patientProfileRepository.findByUserId(user.getId()).orElse(null);
            if (pp != null) {
                profileId = pp.getId();
                city = pp.getCity();
                fullName = pp.getFullName();
            }
        }

        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());
        UUID familyId = UUID.randomUUID();
        String rawRefreshToken = UUID.randomUUID().toString();
        storeRefreshToken(user, rawRefreshToken, familyId, ipAddress, userAgent);

        auditService.logAction(user.getId(), "LOGIN_SUCCESS", "users", user.getId().toString(),
                "Successful login", ipAddress, userAgent);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(rawRefreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(fullName)
                .role(user.getRole())
                .status(user.getStatus())
                .profileId(profileId)
                .bloodGroup(bloodGroup)
                .city(city)
                .build();
    }

    @Transactional
    public AuthResponseDto refreshToken(RefreshTokenRequestDto req, String ipAddress, String userAgent) {
        String tokenHash = hashToken(req.getRefreshToken());
        RefreshToken token = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        // Reuse detection: if an already-revoked refresh token is presented, revoke the entire family
        if (token.getRevokedAt() != null) {
            log.warn("Refresh token reuse detected for family: {}. Revoking all family tokens.", token.getFamilyId());
            refreshTokenRepository.deleteByFamilyId(token.getFamilyId());
            auditService.logAction(token.getUser().getId(), "TOKEN_REUSE_DETECTED", "refresh_tokens",
                    token.getFamilyId().toString(), "Potential replay attack detected", ipAddress, userAgent);
            throw new UnauthorizedException("Security alert: Replay detected. Please log in again.");
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new UnauthorizedException("Refresh token has expired. Please log in again.");
        }

        User user = token.getUser();
        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new UnauthorizedException("Account is suspended.");
        }

        // Rotate token: revoke current token and issue new one in same family
        String newRawToken = UUID.randomUUID().toString();
        String newTokenHash = hashToken(newRawToken);

        token.setRevokedAt(Instant.now());
        token.setReplacedBy(newTokenHash);
        refreshTokenRepository.save(token);

        RefreshToken newToken = RefreshToken.builder()
                .user(user)
                .tokenHash(newTokenHash)
                .familyId(token.getFamilyId())
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .ip(ipAddress)
                .userAgent(userAgent != null && userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent)
                .build();
        refreshTokenRepository.save(newToken);

        String newAccessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());

        return AuthResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRawToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    @Transactional
    public void requestDataDeletion(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);

        DataDeletionRequest req = DataDeletionRequest.builder()
                .user(user)
                .status("PENDING")
                .build();
        deletionRequestRepository.save(req);

        auditService.logAction(userId, "DATA_DELETION_REQUESTED", "users", userId.toString(),
                "User requested account deletion and data anonymization", null, null);
    }

    private void storeRefreshToken(User user, String rawToken, UUID familyId, String ip, String userAgent) {
        String tokenHash = hashToken(rawToken);
        RefreshToken token = RefreshToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .familyId(familyId)
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .ip(ip)
                .userAgent(userAgent != null && userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent)
                .build();
        refreshTokenRepository.save(token);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 algorithm unavailable", e);
        }
    }
}
