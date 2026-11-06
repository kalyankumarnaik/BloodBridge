package in.bloodbridge.dto;

import in.bloodbridge.enums.ReportStatus;
import in.bloodbridge.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class UserReportDto {
    private UUID id;
    private UUID reporterId;
    private String reporterEmail;
    private UUID targetUserId;
    private UUID targetRequestId;
    private String targetRequestCode;
    @NotNull(message = "Reason is required")
    private ReportType reason;
    @NotBlank(message = "Details are required")
    private String details;
    private ReportStatus status;
    private String resolutionNote;
    private Instant createdAt;


    public UserReportDto() {
    }

    public UserReportDto(UUID id, UUID reporterId, String reporterEmail, UUID targetUserId, UUID targetRequestId, String targetRequestCode, ReportType reason, String details, ReportStatus status, String resolutionNote, Instant createdAt) {
        this.id = id;
        this.reporterId = reporterId;
        this.reporterEmail = reporterEmail;
        this.targetUserId = targetUserId;
        this.targetRequestId = targetRequestId;
        this.targetRequestCode = targetRequestCode;
        this.reason = reason;
        this.details = details;
        this.status = status;
        this.resolutionNote = resolutionNote;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getReporterId() {
        return reporterId;
    }
    public void setReporterId(UUID reporterId) {
        this.reporterId = reporterId;
    }
    public String getReporterEmail() {
        return reporterEmail;
    }
    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }
    public UUID getTargetUserId() {
        return targetUserId;
    }
    public void setTargetUserId(UUID targetUserId) {
        this.targetUserId = targetUserId;
    }
    public UUID getTargetRequestId() {
        return targetRequestId;
    }
    public void setTargetRequestId(UUID targetRequestId) {
        this.targetRequestId = targetRequestId;
    }
    public String getTargetRequestCode() {
        return targetRequestCode;
    }
    public void setTargetRequestCode(String targetRequestCode) {
        this.targetRequestCode = targetRequestCode;
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
    public static class UserReportDtoBuilder {
        private UUID id;
        private UUID reporterId;
        private String reporterEmail;
        private UUID targetUserId;
        private UUID targetRequestId;
        private String targetRequestCode;
        private ReportType reason;
        private String details;
        private ReportStatus status;
        private String resolutionNote;
        private Instant createdAt;

        public UserReportDtoBuilder() {}

        public UserReportDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserReportDtoBuilder reporterId(UUID reporterId) {
            this.reporterId = reporterId;
            return this;
        }

        public UserReportDtoBuilder reporterEmail(String reporterEmail) {
            this.reporterEmail = reporterEmail;
            return this;
        }

        public UserReportDtoBuilder targetUserId(UUID targetUserId) {
            this.targetUserId = targetUserId;
            return this;
        }

        public UserReportDtoBuilder targetRequestId(UUID targetRequestId) {
            this.targetRequestId = targetRequestId;
            return this;
        }

        public UserReportDtoBuilder targetRequestCode(String targetRequestCode) {
            this.targetRequestCode = targetRequestCode;
            return this;
        }

        public UserReportDtoBuilder reason(ReportType reason) {
            this.reason = reason;
            return this;
        }

        public UserReportDtoBuilder details(String details) {
            this.details = details;
            return this;
        }

        public UserReportDtoBuilder status(ReportStatus status) {
            this.status = status;
            return this;
        }

        public UserReportDtoBuilder resolutionNote(String resolutionNote) {
            this.resolutionNote = resolutionNote;
            return this;
        }

        public UserReportDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserReportDto build() {
            return new UserReportDto(this.id, this.reporterId, this.reporterEmail, this.targetUserId, this.targetRequestId, this.targetRequestCode, this.reason, this.details, this.status, this.resolutionNote, this.createdAt);
        }
    }

    public static UserReportDtoBuilder builder() {
        return new UserReportDtoBuilder();
    }

}
