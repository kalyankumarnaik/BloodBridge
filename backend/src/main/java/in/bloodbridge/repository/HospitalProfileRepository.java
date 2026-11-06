package in.bloodbridge.repository;

import in.bloodbridge.entity.HospitalProfile;
import in.bloodbridge.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospitalProfileRepository extends JpaRepository<HospitalProfile, UUID> {
    Optional<HospitalProfile> findByUserId(UUID userId);
    Optional<HospitalProfile> findByRegistrationNumber(String registrationNumber);
    List<HospitalProfile> findByVerificationStatus(VerificationStatus status);
    Page<HospitalProfile> findByVerificationStatus(VerificationStatus status, Pageable pageable);
    long countByVerificationStatus(VerificationStatus status);
}
