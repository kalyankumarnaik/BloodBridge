package in.bloodbridge.repository;

import in.bloodbridge.entity.DataDeletionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataDeletionRequestRepository extends JpaRepository<DataDeletionRequest, UUID> {
    Optional<DataDeletionRequest> findByUserId(UUID userId);
}
