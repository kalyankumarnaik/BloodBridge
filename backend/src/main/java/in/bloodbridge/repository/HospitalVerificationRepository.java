package in.bloodbridge.repository;

import in.bloodbridge.entity.HospitalVerification;
import in.bloodbridge.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HospitalVerificationRepository extends JpaRepository<HospitalVerification, UUID> {
    List<HospitalVerification> findByHospitalProfileId(UUID hospitalProfileId);
    Page<HospitalVerification> findByStatus(VerificationStatus status, Pageable pageable);
}
