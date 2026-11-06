package in.bloodbridge.entity;

import in.bloodbridge.enums.DonorResponseStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "donor_responses", uniqueConstraints = {
        @UniqueConstraint(name = "uq_request_donor", columnNames = {"request_id", "donor_user_id"})
})
public class DonorResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private BloodRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_user_id", nullable = false)
    private User donorUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private DonorResponseStatus response = DonorResponseStatus.ACCEPTED;

    @Column(length = 255)
    private String reason;

    @Column(name = "contact_consent", nullable = false)
        private boolean contactConsent = false;

    @Column(name = "consent_expires_at")
    private Instant consentExpiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public DonorResponse() {
    }

    public DonorResponse(UUID id, BloodRequest request, User donorUser, DonorResponseStatus response, String reason, boolean contactConsent, Instant consentExpiresAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.request = request;
        this.donorUser = donorUser;
        this.response = response;
        this.reason = reason;
        this.contactConsent = contactConsent;
        this.consentExpiresAt = consentExpiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public BloodRequest getRequest() {
        return request;
    }
    public void setRequest(BloodRequest request) {
        this.request = request;
    }
    public User getDonorUser() {
        return donorUser;
    }
    public void setDonorUser(User donorUser) {
        this.donorUser = donorUser;
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
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static class DonorResponseBuilder {
        private UUID id;
        private BloodRequest request;
        private User donorUser;
        private DonorResponseStatus response = DonorResponseStatus.ACCEPTED;
        private String reason;
        private boolean contactConsent = false;
        private Instant consentExpiresAt;
        private Instant createdAt;
        private Instant updatedAt;

        public DonorResponseBuilder() {}

        public DonorResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonorResponseBuilder request(BloodRequest request) {
            this.request = request;
            return this;
        }

        public DonorResponseBuilder donorUser(User donorUser) {
            this.donorUser = donorUser;
            return this;
        }

        public DonorResponseBuilder response(DonorResponseStatus response) {
            this.response = response;
            return this;
        }

        public DonorResponseBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public DonorResponseBuilder contactConsent(boolean contactConsent) {
            this.contactConsent = contactConsent;
            return this;
        }

        public DonorResponseBuilder consentExpiresAt(Instant consentExpiresAt) {
            this.consentExpiresAt = consentExpiresAt;
            return this;
        }

        public DonorResponseBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonorResponseBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public DonorResponse build() {
            return new DonorResponse(this.id, this.request, this.donorUser, this.response, this.reason, this.contactConsent, this.consentExpiresAt, this.createdAt, this.updatedAt);
        }
    }

    public static DonorResponseBuilder builder() {
        return new DonorResponseBuilder();
    }

}
