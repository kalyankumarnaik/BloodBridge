package in.bloodbridge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private User actorUser;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id", length = 100)
    private String entityId;

    @Column(name = "ip_hash", length = 64)
    private String ipHash;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "metadata_json", columnDefinition = "TEXT")
    private String metadataJson;

    @CreationTimestamp
    @Column(name = "occurred_at", nullable = false, updatable = false)
    private Instant occurredAt;


    public AuditLog() {
    }

    public AuditLog(UUID id, User actorUser, String action, String entityType, String entityId, String ipHash, String userAgent, String metadataJson, Instant occurredAt) {
        this.id = id;
        this.actorUser = actorUser;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.ipHash = ipHash;
        this.userAgent = userAgent;
        this.metadataJson = metadataJson;
        this.occurredAt = occurredAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getActorUser() {
        return actorUser;
    }
    public void setActorUser(User actorUser) {
        this.actorUser = actorUser;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getEntityType() {
        return entityType;
    }
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    public String getIpHash() {
        return ipHash;
    }
    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getMetadataJson() {
        return metadataJson;
    }
    public void setMetadataJson(String metadataJson) {
        this.metadataJson = metadataJson;
    }
    public Instant getOccurredAt() {
        return occurredAt;
    }
    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
    public static class AuditLogBuilder {
        private UUID id;
        private User actorUser;
        private String action;
        private String entityType;
        private String entityId;
        private String ipHash;
        private String userAgent;
        private String metadataJson;
        private Instant occurredAt;

        public AuditLogBuilder() {}

        public AuditLogBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AuditLogBuilder actorUser(User actorUser) {
            this.actorUser = actorUser;
            return this;
        }

        public AuditLogBuilder action(String action) {
            this.action = action;
            return this;
        }

        public AuditLogBuilder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public AuditLogBuilder entityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public AuditLogBuilder ipHash(String ipHash) {
            this.ipHash = ipHash;
            return this;
        }

        public AuditLogBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public AuditLogBuilder metadataJson(String metadataJson) {
            this.metadataJson = metadataJson;
            return this;
        }

        public AuditLogBuilder occurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(this.id, this.actorUser, this.action, this.entityType, this.entityId, this.ipHash, this.userAgent, this.metadataJson, this.occurredAt);
        }
    }

    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

}
