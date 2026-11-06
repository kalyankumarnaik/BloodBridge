package in.bloodbridge.dto;

import in.bloodbridge.enums.ContactRequestStatus;
import java.time.Instant;
import java.util.UUID;

public class ContactRequestDto {
    private UUID id;
    private UUID requestId;
    private String requestCode;
    private UUID donorUserId;
    private String donorName;
    private String donorBloodGroup;
    private UUID requesterUserId;
    private String requesterName;
    private ContactRequestStatus status;
    private Instant consentRevealedAt;
    private Instant expiresAt;
    private String revealedPhone;
    private String revealedEmail;
    private Instant createdAt;


    public ContactRequestDto() {
    }

    public ContactRequestDto(UUID id, UUID requestId, String requestCode, UUID donorUserId, String donorName, String donorBloodGroup, UUID requesterUserId, String requesterName, ContactRequestStatus status, Instant consentRevealedAt, Instant expiresAt, String revealedPhone, String revealedEmail, Instant createdAt) {
        this.id = id;
        this.requestId = requestId;
        this.requestCode = requestCode;
        this.donorUserId = donorUserId;
        this.donorName = donorName;
        this.donorBloodGroup = donorBloodGroup;
        this.requesterUserId = requesterUserId;
        this.requesterName = requesterName;
        this.status = status;
        this.consentRevealedAt = consentRevealedAt;
        this.expiresAt = expiresAt;
        this.revealedPhone = revealedPhone;
        this.revealedEmail = revealedEmail;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getRequestId() {
        return requestId;
    }
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
    public String getRequestCode() {
        return requestCode;
    }
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
    public UUID getDonorUserId() {
        return donorUserId;
    }
    public void setDonorUserId(UUID donorUserId) {
        this.donorUserId = donorUserId;
    }
    public String getDonorName() {
        return donorName;
    }
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    public String getDonorBloodGroup() {
        return donorBloodGroup;
    }
    public void setDonorBloodGroup(String donorBloodGroup) {
        this.donorBloodGroup = donorBloodGroup;
    }
    public UUID getRequesterUserId() {
        return requesterUserId;
    }
    public void setRequesterUserId(UUID requesterUserId) {
        this.requesterUserId = requesterUserId;
    }
    public String getRequesterName() {
        return requesterName;
    }
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    public ContactRequestStatus getStatus() {
        return status;
    }
    public void setStatus(ContactRequestStatus status) {
        this.status = status;
    }
    public Instant getConsentRevealedAt() {
        return consentRevealedAt;
    }
    public void setConsentRevealedAt(Instant consentRevealedAt) {
        this.consentRevealedAt = consentRevealedAt;
    }
    public Instant getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
    public String getRevealedPhone() {
        return revealedPhone;
    }
    public void setRevealedPhone(String revealedPhone) {
        this.revealedPhone = revealedPhone;
    }
    public String getRevealedEmail() {
        return revealedEmail;
    }
    public void setRevealedEmail(String revealedEmail) {
        this.revealedEmail = revealedEmail;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class ContactRequestDtoBuilder {
        private UUID id;
        private UUID requestId;
        private String requestCode;
        private UUID donorUserId;
        private String donorName;
        private String donorBloodGroup;
        private UUID requesterUserId;
        private String requesterName;
        private ContactRequestStatus status;
        private Instant consentRevealedAt;
        private Instant expiresAt;
        private String revealedPhone;
        private String revealedEmail;
        private Instant createdAt;

        public ContactRequestDtoBuilder() {}

        public ContactRequestDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ContactRequestDtoBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public ContactRequestDtoBuilder requestCode(String requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public ContactRequestDtoBuilder donorUserId(UUID donorUserId) {
            this.donorUserId = donorUserId;
            return this;
        }

        public ContactRequestDtoBuilder donorName(String donorName) {
            this.donorName = donorName;
            return this;
        }

        public ContactRequestDtoBuilder donorBloodGroup(String donorBloodGroup) {
            this.donorBloodGroup = donorBloodGroup;
            return this;
        }

        public ContactRequestDtoBuilder requesterUserId(UUID requesterUserId) {
            this.requesterUserId = requesterUserId;
            return this;
        }

        public ContactRequestDtoBuilder requesterName(String requesterName) {
            this.requesterName = requesterName;
            return this;
        }

        public ContactRequestDtoBuilder status(ContactRequestStatus status) {
            this.status = status;
            return this;
        }

        public ContactRequestDtoBuilder consentRevealedAt(Instant consentRevealedAt) {
            this.consentRevealedAt = consentRevealedAt;
            return this;
        }

        public ContactRequestDtoBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public ContactRequestDtoBuilder revealedPhone(String revealedPhone) {
            this.revealedPhone = revealedPhone;
            return this;
        }

        public ContactRequestDtoBuilder revealedEmail(String revealedEmail) {
            this.revealedEmail = revealedEmail;
            return this;
        }

        public ContactRequestDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ContactRequestDto build() {
            return new ContactRequestDto(this.id, this.requestId, this.requestCode, this.donorUserId, this.donorName, this.donorBloodGroup, this.requesterUserId, this.requesterName, this.status, this.consentRevealedAt, this.expiresAt, this.revealedPhone, this.revealedEmail, this.createdAt);
        }
    }

    public static ContactRequestDtoBuilder builder() {
        return new ContactRequestDtoBuilder();
    }

}
