package in.bloodbridge.repository;

import in.bloodbridge.entity.DonationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistory, UUID> {
    Page<DonationHistory> findByDonorUserId(UUID donorUserId, Pageable pageable);
    List<DonationHistory> findTop5ByDonorUserIdOrderByDonatedOnDesc(UUID donorUserId);
    long countByDonorUserId(UUID donorUserId);
}
