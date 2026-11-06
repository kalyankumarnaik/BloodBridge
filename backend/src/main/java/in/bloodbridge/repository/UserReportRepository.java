package in.bloodbridge.repository;

import in.bloodbridge.entity.UserReport;
import in.bloodbridge.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, UUID> {
    Page<UserReport> findByStatus(ReportStatus status, Pageable pageable);
    long countByStatus(ReportStatus status);
}
