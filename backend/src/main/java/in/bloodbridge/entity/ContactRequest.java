package in.bloodbridge.entity;

import in.bloodbridge.enums.ContactRequestStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "contact_requests")
public class ContactRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private BloodRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_user_id", nullable = false)
    private User donorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_user_id", nullable = false)
    private User requesterUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private ContactRequestStatus status = ContactRequestStatus.PENDING;

    @Column(name = "consent_revealed_at")
    private Instant consentRevealedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public ContactRequest() {
    }

    public ContactRequest(UUID id, BloodRequest request, User donorUser, User requesterUser, ContactRequestStatus status, Instant consentRevealedAt, Instant expiresAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.request = request;
        this.donorUser = donorUser;
        this.requesterUser = requesterUser;
        this.status = status;
        this.consentRevealedAt = consentRevealedAt;
        this.expiresAt = expiresAt;
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
    public User getRequesterUser() {
        return requesterUser;
    }
    public void setRequesterUser(User requesterUser) {
        this.requesterUser = requesterUser;
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
    public static class ContactRequestBuilder {
        private UUID id;
        private BloodRequest request;
        private User donorUser;
        private User requesterUser;
        private ContactRequestStatus status = ContactRequestStatus.PENDING;
        private Instant consentRevealedAt;
        private Instant expiresAt;
        private Instant createdAt;
        private Instant updatedAt;

        public ContactRequestBuilder() {}

        public ContactRequestBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ContactRequestBuilder request(BloodRequest request) {
            this.request = request;
            return this;
        }

        public ContactRequestBuilder donorUser(User donorUser) {
            this.donorUser = donorUser;
            return this;
        }

        public ContactRequestBuilder requesterUser(User requesterUser) {
            this.requesterUser = requesterUser;
            return this;
        }

        public ContactRequestBuilder status(ContactRequestStatus status) {
            this.status = status;
            return this;
        }

        public ContactRequestBuilder consentRevealedAt(Instant consentRevealedAt) {
            this.consentRevealedAt = consentRevealedAt;
            return this;
        }

        public ContactRequestBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public ContactRequestBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ContactRequestBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ContactRequest build() {
            return new ContactRequest(this.id, this.request, this.donorUser, this.requesterUser, this.status, this.consentRevealedAt, this.expiresAt, this.createdAt, this.updatedAt);
        }
    }

    public static ContactRequestBuilder builder() {
        return new ContactRequestBuilder();
    }

}
