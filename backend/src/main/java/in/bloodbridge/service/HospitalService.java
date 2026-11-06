package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.BloodAvailabilityDto;
import in.bloodbridge.dto.HospitalProfileDto;
import in.bloodbridge.dto.HospitalVerificationSubmitDto;
import in.bloodbridge.entity.BloodAvailability;
import in.bloodbridge.entity.HospitalProfile;
import in.bloodbridge.entity.HospitalVerification;
import in.bloodbridge.entity.User;
import in.bloodbridge.enums.BloodAvailabilityStatus;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.VerificationStatus;
import in.bloodbridge.exception.ForbiddenException;
import in.bloodbridge.exception.ResourceNotFoundException;
import in.bloodbridge.integration.FileStorageService;
import in.bloodbridge.repository.BloodAvailabilityRepository;
import in.bloodbridge.repository.HospitalProfileRepository;
import in.bloodbridge.repository.HospitalVerificationRepository;
import in.bloodbridge.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    private static final Logger log = LoggerFactory.getLogger(HospitalService.class);

    public HospitalService(HospitalProfileRepository hospitalProfileRepository, HospitalVerificationRepository verificationRepository, BloodAvailabilityRepository availabilityRepository, UserRepository userRepository, FileStorageService fileStorageService, AuditService auditService) {
        this.hospitalProfileRepository = hospitalProfileRepository;
        this.verificationRepository = verificationRepository;
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.auditService = auditService;
    }


    private final HospitalProfileRepository hospitalProfileRepository;
    private final HospitalVerificationRepository verificationRepository;
    private final BloodAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public HospitalProfileDto getProfileByUserId(UUID userId) {
        HospitalProfile hp = hospitalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital profile not found for user: " + userId));
        return mapToProfileDto(hp);
    }

    @Transactional
    public void submitVerificationDocument(UUID userId, HospitalVerificationSubmitDto dto, MultipartFile file, String ipAddress, String userAgent) {
        HospitalProfile hp = hospitalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital profile not found"));

        String storageKey = fileStorageService.storeFile(file);

        HospitalVerification hv = HospitalVerification.builder()
                .hospitalProfile(hp)
                .documentType(dto.getDocumentType())
                                .storageKey(storageKey)
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .sizeBytes(file.getSize())
                .status(VerificationStatus.SUBMITTED)
                .build();
        verificationRepository.save(hv);

        hp.setVerificationStatus(VerificationStatus.UNDER_REVIEW);
        hospitalProfileRepository.save(hp);

        auditService.logAction(userId, "HOSPITAL_DOC_SUBMITTED", "hospital_verifications",
                hv.getId().toString(), "Document: " + dto.getDocumentName(), ipAddress, userAgent);
    }

    @Transactional
    public BloodAvailabilityDto updateBloodAvailability(UUID userId, BloodGroup bloodGroup, BloodAvailabilityStatus status, Integer unitsMin, Integer unitsMax) {
        HospitalProfile hp = hospitalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital profile not found"));

        if (hp.getVerificationStatus() != VerificationStatus.VERIFIED) {
            throw new ForbiddenException("Only verified hospitals can publish blood availability updates.");
        }

        User user = userRepository.findById(userId).orElse(null);

        BloodAvailability ba = availabilityRepository.findByHospitalProfileIdAndBloodGroup(hp.getId(), bloodGroup)
                .orElseGet(() -> BloodAvailability.builder()
                        .hospitalProfile(hp)
                        .bloodGroup(bloodGroup)
                        .build());

        ba.setStatus(status);
        ba.setUnitsMin(unitsMin);
        ba.setUnitsMax(unitsMax);
        ba.setUpdatedBy(user);
        ba = availabilityRepository.save(ba);

        return mapToAvailabilityDto(ba);
    }

    @Transactional(readOnly = true)
    public List<BloodAvailabilityDto> getHospitalAvailability(UUID hospitalProfileId) {
        return availabilityRepository.findByHospitalProfileId(hospitalProfileId).stream()
                .map(this::mapToAvailabilityDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BloodAvailabilityDto> getPublicAvailabilityByBloodGroup(BloodGroup bloodGroup) {
        return availabilityRepository.findByBloodGroup(bloodGroup).stream()
                .filter(ba -> ba.getHospitalProfile().getVerificationStatus() == VerificationStatus.VERIFIED)
                .map(this::mapToAvailabilityDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<HospitalProfileDto> getVerifiedHospitals(Pageable pageable) {
        return hospitalProfileRepository.findByVerificationStatus(VerificationStatus.VERIFIED, pageable)
                .map(this::mapToProfileDto);
    }

    private HospitalProfileDto mapToProfileDto(HospitalProfile hp) {
        return HospitalProfileDto.builder()
                .id(hp.getId())
                .userId(hp.getUser().getId())
                .name(hp.getName())
                .registrationNumber(hp.getRegistrationNumber())
                .type(hp.getType())
                .address(hp.getAddress())
                .city(hp.getCity())
                .state(hp.getState())
                .pinCode(hp.getPinCode())
                .lat(hp.getLat())
                .lon(hp.getLon())
                .officialPhone(hp.getOfficialPhone())
                .contactPerson(hp.getContactPerson())
                .verificationStatus(hp.getVerificationStatus())
                .createdAt(hp.getCreatedAt())
                .build();
    }

    private BloodAvailabilityDto mapToAvailabilityDto(BloodAvailability ba) {
        boolean isStale = ba.getUpdatedAt().isBefore(Instant.now().minus(24, ChronoUnit.HOURS));
        return BloodAvailabilityDto.builder()
                .id(ba.getId())
                .hospitalProfileId(ba.getHospitalProfile().getId())
                .hospitalName(ba.getHospitalProfile().getName())
                .city(ba.getHospitalProfile().getCity())
                .bloodGroup(ba.getBloodGroup())
                .bloodGroupLabel(ba.getBloodGroup().getLabel())
                .status(ba.getStatus())
                .unitsMin(ba.getUnitsMin())
                .unitsMax(ba.getUnitsMax())
                .updatedAt(ba.getUpdatedAt())
                .isStale(isStale)
                .build();
    }
}
