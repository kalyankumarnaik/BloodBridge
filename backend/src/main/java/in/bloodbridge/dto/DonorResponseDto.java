package in.bloodbridge.dto;

import in.bloodbridge.enums.DonorResponseStatus;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class DonorResponseDto {
    private UUID id;
    @NotNull(message = "Request ID is required")
    private UUID requestId;
    private String requestCode;
    private UUID donorUserId;
    private String maskedDonorName;
    private String donorBloodGroup;
    private DonorResponseStatus response;
    private String reason;
    private boolean contactConsent;
    private Instant consentExpiresAt;
    private String revealedPhone; // null unless explicit unexpired consent exists!
    private Instant createdAt;


    public DonorResponseDto() {
    }

    public DonorResponseDto(UUID id, UUID requestId, String requestCode, UUID donorUserId, String maskedDonorName, String donorBloodGroup, DonorResponseStatus response, String reason, boolean contactConsent, Instant consentExpiresAt, String revealedPhone, Instant createdAt) {
        this.id = id;
        this.requestId = requestId;
        this.requestCode = requestCode;
        this.donorUserId = donorUserId;
        this.maskedDonorName = maskedDonorName;
        this.donorBloodGroup = donorBloodGroup;
        this.response = response;
        this.reason = reason;
        this.contactConsent = contactConsent;
        this.consentExpiresAt = consentExpiresAt;
        this.revealedPhone = revealedPhone;
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
    public String getMaskedDonorName() {
        return maskedDonorName;
    }
    public void setMaskedDonorName(String maskedDonorName) {
        this.maskedDonorName = maskedDonorName;
    }
    public String getDonorBloodGroup() {
        return donorBloodGroup;
    }
    public void setDonorBloodGroup(String donorBloodGroup) {
        this.donorBloodGroup = donorBloodGroup;
    }
    public DonorResponseStatus getResponse() {
        return response;
    }
    public void setResponse(DonorResponseStatus response) {
        this.response = response;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public boolean isContactConsent() {
        return contactConsent;
    }
    public boolean getContactConsent() {
        return contactConsent;
    }
    public void setContactConsent(boolean contactConsent) {
        this.contactConsent = contactConsent;
    }
    public Instant getConsentExpiresAt() {
        return consentExpiresAt;
    }
    public void setConsentExpiresAt(Instant consentExpiresAt) {
        this.consentExpiresAt = consentExpiresAt;
    }
    public String getRevealedPhone() {
        return revealedPhone;
    }
    public void setRevealedPhone(String revealedPhone) {
        this.revealedPhone = revealedPhone;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class DonorResponseDtoBuilder {
        private UUID id;
        private UUID requestId;
        private String requestCode;
        private UUID donorUserId;
        private String maskedDonorName;
        private String donorBloodGroup;
        private DonorResponseStatus response;
        private String reason;
        private boolean contactConsent;
        private Instant consentExpiresAt;
        private String revealedPhone;
        private Instant createdAt;

        public DonorResponseDtoBuilder() {}

        public DonorResponseDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonorResponseDtoBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public DonorResponseDtoBuilder requestCode(String requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public DonorResponseDtoBuilder donorUserId(UUID donorUserId) {
            this.donorUserId = donorUserId;
            return this;
        }

        public DonorResponseDtoBuilder maskedDonorName(String maskedDonorName) {
            this.maskedDonorName = maskedDonorName;
            return this;
        }

        public DonorResponseDtoBuilder donorBloodGroup(String donorBloodGroup) {
            this.donorBloodGroup = donorBloodGroup;
            return this;
        }

        public DonorResponseDtoBuilder response(DonorResponseStatus response) {
            this.response = response;
            return this;
        }

        public DonorResponseDtoBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public DonorResponseDtoBuilder contactConsent(boolean contactConsent) {
            this.contactConsent = contactConsent;
            return this;
        }

        public DonorResponseDtoBuilder consentExpiresAt(Instant consentExpiresAt) {
            this.consentExpiresAt = consentExpiresAt;
            return this;
        }

        public DonorResponseDtoBuilder revealedPhone(String revealedPhone) {
            this.revealedPhone = revealedPhone;
            return this;
        }

        public DonorResponseDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonorResponseDto build() {
            return new DonorResponseDto(this.id, this.requestId, this.requestCode, this.donorUserId, this.maskedDonorName, this.donorBloodGroup, this.response, this.reason, this.contactConsent, this.consentExpiresAt, this.revealedPhone, this.createdAt);
        }
    }

    public static DonorResponseDtoBuilder builder() {
        return new DonorResponseDtoBuilder();
    }

}
