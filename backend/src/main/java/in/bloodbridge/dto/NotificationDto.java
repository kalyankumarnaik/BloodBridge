package in.bloodbridge.dto;

import in.bloodbridge.enums.NotificationChannel;
import in.bloodbridge.enums.NotificationDeliveryStatus;
import in.bloodbridge.enums.NotificationType;
import java.time.Instant;
import java.util.UUID;

public class NotificationDto {
    private UUID id;
    private UUID userId;
    private NotificationType type;
    private NotificationChannel channel;
    private String title;
    private String body;
    private NotificationDeliveryStatus status;
    private Instant readAt;
    private String requestId;
    private Instant createdAt;


    public NotificationDto() {
    }

    public NotificationDto(UUID id, UUID userId, NotificationType type, NotificationChannel channel, String title, String body, NotificationDeliveryStatus status, Instant readAt, String requestId, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.channel = channel;
        this.title = title;
        this.body = body;
        this.status = status;
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
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
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
    public NotificationDeliveryStatus getStatus() {
        return status;
    }
    public void setStatus(NotificationDeliveryStatus status) {
        this.status = status;
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
    public static class NotificationDtoBuilder {
        private UUID id;
        private UUID userId;
        private NotificationType type;
        private NotificationChannel channel;
        private String title;
        private String body;
        private NotificationDeliveryStatus status;
        private Instant readAt;
        private String requestId;
        private Instant createdAt;

        public NotificationDtoBuilder() {}

        public NotificationDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public NotificationDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public NotificationDtoBuilder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public NotificationDtoBuilder channel(NotificationChannel channel) {
            this.channel = channel;
            return this;
        }

        public NotificationDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public NotificationDtoBuilder body(String body) {
            this.body = body;
            return this;
        }

        public NotificationDtoBuilder status(NotificationDeliveryStatus status) {
            this.status = status;
            return this;
        }

        public NotificationDtoBuilder readAt(Instant readAt) {
            this.readAt = readAt;
            return this;
        }

        public NotificationDtoBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public NotificationDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NotificationDto build() {
            return new NotificationDto(this.id, this.userId, this.type, this.channel, this.title, this.body, this.status, this.readAt, this.requestId, this.createdAt);
        }
    }

    public static NotificationDtoBuilder builder() {
        return new NotificationDtoBuilder();
    }

}
