package in.bloodbridge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token_hash", nullable = false, unique = true, length = 64)
    private String tokenHash;

    @Column(name = "family_id", nullable = false)
    private UUID familyId;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "replaced_by", length = 64)
    private String replacedBy;

    @Column(length = 64)
    private String ip;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public RefreshToken() {
    }

    public RefreshToken(UUID id, User user, String tokenHash, UUID familyId, Instant expiresAt, Instant revokedAt, String replacedBy, String ip, String userAgent, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.tokenHash = tokenHash;
        this.familyId = familyId;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
        this.replacedBy = replacedBy;
        this.ip = ip;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
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
    public String getTokenHash() {
        return tokenHash;
    }
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }
    public UUID getFamilyId() {
        return familyId;
    }
    public void setFamilyId(UUID familyId) {
        this.familyId = familyId;
    }
    public Instant getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
    public Instant getRevokedAt() {
        return revokedAt;
    }
    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }
    public String getReplacedBy() {
        return replacedBy;
    }
    public void setReplacedBy(String replacedBy) {
        this.replacedBy = replacedBy;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class RefreshTokenBuilder {
        private UUID id;
        private User user;
        private String tokenHash;
        private UUID familyId;
        private Instant expiresAt;
        private Instant revokedAt;
        private String replacedBy;
        private String ip;
        private String userAgent;
        private Instant createdAt;

        public RefreshTokenBuilder() {}

        public RefreshTokenBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public RefreshTokenBuilder user(User user) {
            this.user = user;
            return this;
        }

        public RefreshTokenBuilder tokenHash(String tokenHash) {
            this.tokenHash = tokenHash;
            return this;
        }

        public RefreshTokenBuilder familyId(UUID familyId) {
            this.familyId = familyId;
            return this;
        }

        public RefreshTokenBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public RefreshTokenBuilder revokedAt(Instant revokedAt) {
            this.revokedAt = revokedAt;
            return this;
        }

        public RefreshTokenBuilder replacedBy(String replacedBy) {
            this.replacedBy = replacedBy;
            return this;
        }

        public RefreshTokenBuilder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public RefreshTokenBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public RefreshTokenBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this.id, this.user, this.tokenHash, this.familyId, this.expiresAt, this.revokedAt, this.replacedBy, this.ip, this.userAgent, this.createdAt);
        }
    }

    public static RefreshTokenBuilder builder() {
        return new RefreshTokenBuilder();
    }

}
