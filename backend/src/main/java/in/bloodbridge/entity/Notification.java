package in.bloodbridge.entity;

import in.bloodbridge.enums.NotificationChannel;
import in.bloodbridge.enums.NotificationDeliveryStatus;
import in.bloodbridge.enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private NotificationChannel channel = NotificationChannel.IN_APP;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "payload_json", columnDefinition = "TEXT")
    private String payloadJson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private NotificationDeliveryStatus status = NotificationDeliveryStatus.SENT;

    @Column(nullable = false)
        private int attempts = 1;

    @Column(name = "last_error", columnDefinition = "TEXT")
    private String lastError;

    @Column(name = "next_attempt_at")
    private Instant nextAttemptAt;

    @Column(name = "read_at")
    private Instant readAt;

    @Column(name = "request_id")
    private String requestId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public Notification() {
    }

    public Notification(UUID id, User user, NotificationType type, NotificationChannel channel, String title, String body, String payloadJson, NotificationDeliveryStatus status, int attempts, String lastError, Instant nextAttemptAt, Instant readAt, String requestId, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.channel = channel;
        this.title = title;
        this.body = body;
        this.payloadJson = payloadJson;
        this.status = status;
        this.attempts = attempts;
        this.lastError = lastError;
        this.nextAttemptAt = nextAttemptAt;
        this.readAt = readAt;
        this.requestId = requestId;
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
    public NotificationType getType() {
        return type;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public NotificationChannel getChannel() {
        return channel;
    }
    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getPayloadJson() {
        return payloadJson;
    }
    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }
    public NotificationDeliveryStatus getStatus() {
        return status;
    }
    public void setStatus(NotificationDeliveryStatus status) {
        this.status = status;
    }
    public int getAttempts() {
        return attempts;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
    public String getLastError() {
        return lastError;
    }
    public void setLastError(String lastError) {
        this.lastError = lastError;
    }
    public Instant getNextAttemptAt() {
        return nextAttemptAt;
    }
    public void setNextAttemptAt(Instant nextAttemptAt) {
        this.nextAttemptAt = nextAttemptAt;
    }
    public Instant getReadAt() {
        return readAt;
    }
    public void setReadAt(Instant readAt) {
        this.readAt = readAt;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class NotificationBuilder {
        private UUID id;
        private User user;
        private NotificationType type;
        private NotificationChannel channel = NotificationChannel.IN_APP;
        private String title;
        private String body;
        private String payloadJson;
        private NotificationDeliveryStatus status = NotificationDeliveryStatus.SENT;
        private int attempts = 1;
        private String lastError;
        private Instant nextAttemptAt;
        private Instant readAt;
        private String requestId;
        private Instant createdAt;

        public NotificationBuilder() {}

        public NotificationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder user(User user) {
            this.user = user;
            return this;
        }

        public NotificationBuilder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public NotificationBuilder channel(NotificationChannel channel) {
            this.channel = channel;
            return this;
        }

        public NotificationBuilder title(String title) {
            this.title = title;
            return this;
        }

        public NotificationBuilder body(String body) {
            this.body = body;
            return this;
        }

        public NotificationBuilder payloadJson(String payloadJson) {
            this.payloadJson = payloadJson;
            return this;
        }

        public NotificationBuilder status(NotificationDeliveryStatus status) {
            this.status = status;
            return this;
        }

        public NotificationBuilder attempts(int attempts) {
            this.attempts = attempts;
            return this;
        }

        public NotificationBuilder lastError(String lastError) {
            this.lastError = lastError;
            return this;
        }

        public NotificationBuilder nextAttemptAt(Instant nextAttemptAt) {
            this.nextAttemptAt = nextAttemptAt;
            return this;
        }

        public NotificationBuilder readAt(Instant readAt) {
            this.readAt = readAt;
            return this;
        }

        public NotificationBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public NotificationBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Notification build() {
            return new Notification(this.id, this.user, this.type, this.channel, this.title, this.body, this.payloadJson, this.status, this.attempts, this.lastError, this.nextAttemptAt, this.readAt, this.requestId, this.createdAt);
        }
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

}
