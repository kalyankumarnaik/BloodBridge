package in.bloodbridge.repository;

import in.bloodbridge.entity.BloodAvailability;
import in.bloodbridge.enums.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BloodAvailabilityRepository extends JpaRepository<BloodAvailability, UUID> {
    List<BloodAvailability> findByHospitalProfileId(UUID hospitalProfileId);
    Optional<BloodAvailability> findByHospitalProfileIdAndBloodGroup(UUID hospitalProfileId, BloodGroup bloodGroup);
    List<BloodAvailability> findByBloodGroup(BloodGroup bloodGroup);
}
