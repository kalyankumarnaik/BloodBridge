package in.bloodbridge.repository;

import in.bloodbridge.entity.DonorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonorResponseRepository extends JpaRepository<DonorResponse, UUID> {
    Optional<DonorResponse> findByRequestIdAndDonorUserId(UUID requestId, UUID donorUserId);
    List<DonorResponse> findByRequestId(UUID requestId);
    Page<DonorResponse> findByDonorUserId(UUID donorUserId, Pageable pageable);
    Page<DonorResponse> findByRequestId(UUID requestId, Pageable pageable);
}
