package in.bloodbridge.repository;

import in.bloodbridge.entity.BloodRequest;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.RequestPriority;
import in.bloodbridge.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {
    Optional<BloodRequest> findByRequestCode(String requestCode);
    Page<BloodRequest> findByRequesterId(UUID requesterId, Pageable pageable);
    Page<BloodRequest> findByHospitalProfileId(UUID hospitalProfileId, Pageable pageable);

    @Query("SELECT br FROM BloodRequest br WHERE " +
           "br.fingerprintHash = :hash AND " +
           "br.status NOT IN (in.bloodbridge.enums.RequestStatus.FULFILLED, in.bloodbridge.enums.RequestStatus.CANCELLED, in.bloodbridge.enums.RequestStatus.EXPIRED)")
    List<BloodRequest> findActiveDuplicatesByHash(@Param("hash") String hash);

    @Query("SELECT br FROM BloodRequest br WHERE " +
           "(:status IS NULL OR br.status = :status) AND " +
           "(:bloodGroups IS NULL OR br.bloodGroup IN :bloodGroups) AND " +
           "(:city IS NULL OR LOWER(br.city) = LOWER(:city)) AND " +
           "(:priority IS NULL OR br.priority = :priority) AND " +
           "br.deletedAt IS NULL")
    Page<BloodRequest> searchRequests(
            @Param("status") RequestStatus status,
            @Param("bloodGroups") Collection<BloodGroup> bloodGroups,
            @Param("city") String city,
            @Param("priority") RequestPriority priority,
            Pageable pageable
    );

    @Query("SELECT br FROM BloodRequest br WHERE " +
           "br.status IN (in.bloodbridge.enums.RequestStatus.VERIFIED, in.bloodbridge.enums.RequestStatus.ACTIVE) AND " +
           "br.bloodGroup IN :bloodGroups AND br.deletedAt IS NULL")
    List<BloodRequest> findActiveCompatibleRequests(@Param("bloodGroups") Collection<BloodGroup> bloodGroups);

    List<BloodRequest> findByStatusAndRequiredByLessThan(RequestStatus status, Instant cutoff);

    long countByStatus(RequestStatus status);
    long countByRequesterIdAndStatusNotIn(UUID requesterId, Collection<RequestStatus> terminalStatuses);
}
