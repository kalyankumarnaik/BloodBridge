package in.bloodbridge.repository;

import in.bloodbridge.entity.DonorProfile;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonorProfileRepository extends JpaRepository<DonorProfile, UUID> {
    Optional<DonorProfile> findByUserId(UUID userId);

    @Query("SELECT dp FROM DonorProfile dp WHERE " +
           "dp.showInSearch = true AND " +
           "dp.user.status = in.bloodbridge.enums.UserStatus.ACTIVE AND " +
           "(:bloodGroups IS NULL OR dp.bloodGroup IN :bloodGroups) AND " +
           "(:city IS NULL OR LOWER(dp.city) = LOWER(:city)) AND " +
           "(:pinCode IS NULL OR dp.pinCode = :pinCode) AND " +
           "(:availability IS NULL OR dp.availability = :availability) AND " +
           "(:verifiedOnly IS NULL OR :verifiedOnly = false OR dp.verifiedByAdmin = true)")
    Page<DonorProfile> searchDonors(
            @Param("bloodGroups") Collection<BloodGroup> bloodGroups,
            @Param("city") String city,
            @Param("pinCode") String pinCode,
            @Param("availability") DonorAvailability availability,
            @Param("verifiedOnly") Boolean verifiedOnly,
            Pageable pageable
    );

    @Query("SELECT dp FROM DonorProfile dp WHERE " +
           "dp.availability = in.bloodbridge.enums.DonorAvailability.AVAILABLE AND " +
           "dp.emergencyAlertsEnabled = true AND " +
           "dp.user.status = in.bloodbridge.enums.UserStatus.ACTIVE AND " +
           "dp.bloodGroup IN :bloodGroups")
    List<DonorProfile> findCompatibleEmergencyDonors(@Param("bloodGroups") Collection<BloodGroup> bloodGroups);

    long countByAvailability(DonorAvailability availability);
    long countByBloodGroup(BloodGroup bloodGroup);
}
