package in.bloodbridge.entity;

import in.bloodbridge.enums.ReportStatus;
import in.bloodbridge.enums.ReportType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_reports")
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_request_id")
    private BloodRequest targetRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ReportType reason;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
        private ReportStatus status = ReportStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    @Column(name = "resolution_note", columnDefinition = "TEXT")
    private String resolutionNote;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public UserReport() {
    }

    public UserReport(UUID id, User reporter, User targetUser, BloodRequest targetRequest, ReportType reason, String details, ReportStatus status, User resolvedBy, String resolutionNote, Instant createdAt) {
        this.id = id;
        this.reporter = reporter;
        this.targetUser = targetUser;
        this.targetRequest = targetRequest;
        this.reason = reason;
        this.details = details;
        this.status = status;
        this.resolvedBy = resolvedBy;
        this.resolutionNote = resolutionNote;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getReporter() {
        return reporter;
    }
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
    public User getTargetUser() {
        return targetUser;
    }
    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
    public BloodRequest getTargetRequest() {
        return targetRequest;
    }
    public void setTargetRequest(BloodRequest targetRequest) {
        this.targetRequest = targetRequest;
    }
    public ReportType getReason() {
        return reason;
    }
    public void setReason(ReportType reason) {
        this.reason = reason;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public ReportStatus getStatus() {
        return status;
    }
    public void setStatus(ReportStatus status) {
        this.status = status;
    }
    public User getResolvedBy() {
        return resolvedBy;
    }
    public void setResolvedBy(User resolvedBy) {
        this.resolvedBy = resolvedBy;
    }
    public String getResolutionNote() {
        return resolutionNote;
    }
    public void setResolutionNote(String resolutionNote) {
        this.resolutionNote = resolutionNote;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class UserReportBuilder {
        private UUID id;
        private User reporter;
        private User targetUser;
        private BloodRequest targetRequest;
        private ReportType reason;
        private String details;
        private ReportStatus status = ReportStatus.OPEN;
        private User resolvedBy;
        private String resolutionNote;
        private Instant createdAt;

        public UserReportBuilder() {}

        public UserReportBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserReportBuilder reporter(User reporter) {
            this.reporter = reporter;
            return this;
        }

        public UserReportBuilder targetUser(User targetUser) {
            this.targetUser = targetUser;
            return this;
        }

        public UserReportBuilder targetRequest(BloodRequest targetRequest) {
            this.targetRequest = targetRequest;
            return this;
        }

        public UserReportBuilder reason(ReportType reason) {
            this.reason = reason;
            return this;
        }

        public UserReportBuilder details(String details) {
            this.details = details;
            return this;
        }

        public UserReportBuilder status(ReportStatus status) {
            this.status = status;
            return this;
        }

        public UserReportBuilder resolvedBy(User resolvedBy) {
            this.resolvedBy = resolvedBy;
            return this;
        }

        public UserReportBuilder resolutionNote(String resolutionNote) {
            this.resolutionNote = resolutionNote;
            return this;
        }

        public UserReportBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserReport build() {
            return new UserReport(this.id, this.reporter, this.targetUser, this.targetRequest, this.reason, this.details, this.status, this.resolvedBy, this.resolutionNote, this.createdAt);
        }
    }

    public static UserReportBuilder builder() {
        return new UserReportBuilder();
    }

}
