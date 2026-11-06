package in.bloodbridge.repository;

import in.bloodbridge.entity.ContactRequest;
import in.bloodbridge.enums.ContactRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRequestRepository extends JpaRepository<ContactRequest, UUID> {
    Optional<ContactRequest> findByRequestIdAndDonorUserIdAndRequesterUserId(UUID requestId, UUID donorUserId, UUID requesterUserId);
    Page<ContactRequest> findByDonorUserId(UUID donorUserId, Pageable pageable);
    Page<ContactRequest> findByRequesterUserId(UUID requesterUserId, Pageable pageable);
    long countByRequesterUserIdAndCreatedAtAfter(UUID requesterUserId, Instant after);
}
