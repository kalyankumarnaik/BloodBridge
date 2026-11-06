package in.bloodbridge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "data_deletion_requests")
public class DataDeletionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
        private String status = "PENDING"; // PENDING, PROCESSED

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private Instant requestedAt;

    @Column(name = "anonymized_at")
    private Instant anonymizedAt;


    public DataDeletionRequest() {
    }

    public DataDeletionRequest(UUID id, User user, String status, Instant requestedAt, Instant anonymizedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.requestedAt = requestedAt;
        this.anonymizedAt = anonymizedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Instant getRequestedAt() {
        return requestedAt;
    }
    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }
    public Instant getAnonymizedAt() {
        return anonymizedAt;
    }
    public void setAnonymizedAt(Instant anonymizedAt) {
        this.anonymizedAt = anonymizedAt;
    }
    public static class DataDeletionRequestBuilder {
        private UUID id;
        private User user;
        private String status = "PENDING";
        private Instant requestedAt;
        private Instant anonymizedAt;

        public DataDeletionRequestBuilder() {}

        public DataDeletionRequestBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DataDeletionRequestBuilder user(User user) {
            this.user = user;
            return this;
        }

        public DataDeletionRequestBuilder status(String status) {
            this.status = status;
            return this;
        }

        public DataDeletionRequestBuilder requestedAt(Instant requestedAt) {
            this.requestedAt = requestedAt;
            return this;
        }

        public DataDeletionRequestBuilder anonymizedAt(Instant anonymizedAt) {
            this.anonymizedAt = anonymizedAt;
            return this;
        }

        public DataDeletionRequest build() {
            return new DataDeletionRequest(this.id, this.user, this.status, this.requestedAt, this.anonymizedAt);
        }
    }

    public static DataDeletionRequestBuilder builder() {
        return new DataDeletionRequestBuilder();
    }

}
