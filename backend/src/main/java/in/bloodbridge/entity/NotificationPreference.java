package in.bloodbridge.entity;

import in.bloodbridge.enums.NotificationChannel;
import in.bloodbridge.enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "notification_preferences", uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_channel_event", columnNames = {"user_id", "channel", "event_type"})
})
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private NotificationType eventType;

    @Column(nullable = false)
        private boolean enabled = true;

    @Column(name = "quiet_hours_start")
    private LocalTime quietHoursStart;

    @Column(name = "quiet_hours_end")
    private LocalTime quietHoursEnd;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public NotificationPreference() {
    }

    public NotificationPreference(UUID id, User user, NotificationChannel channel, NotificationType eventType, boolean enabled, LocalTime quietHoursStart, LocalTime quietHoursEnd, Instant updatedAt) {
        this.id = id;
        this.user = user;
        this.channel = channel;
        this.eventType = eventType;
        this.enabled = enabled;
        this.quietHoursStart = quietHoursStart;
        this.quietHoursEnd = quietHoursEnd;
        this.updatedAt = updatedAt;
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
    public NotificationChannel getChannel() {
        return channel;
    }
    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }
    public NotificationType getEventType() {
        return eventType;
    }
    public void setEventType(NotificationType eventType) {
        this.eventType = eventType;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public LocalTime getQuietHoursStart() {
        return quietHoursStart;
    }
    public void setQuietHoursStart(LocalTime quietHoursStart) {
        this.quietHoursStart = quietHoursStart;
    }
    public LocalTime getQuietHoursEnd() {
        return quietHoursEnd;
    }
    public void setQuietHoursEnd(LocalTime quietHoursEnd) {
        this.quietHoursEnd = quietHoursEnd;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static class NotificationPreferenceBuilder {
        private UUID id;
        private User user;
        private NotificationChannel channel;
        private NotificationType eventType;
        private boolean enabled = true;
        private LocalTime quietHoursStart;
        private LocalTime quietHoursEnd;
        private Instant updatedAt;

        public NotificationPreferenceBuilder() {}

        public NotificationPreferenceBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public NotificationPreferenceBuilder user(User user) {
            this.user = user;
            return this;
        }

        public NotificationPreferenceBuilder channel(NotificationChannel channel) {
            this.channel = channel;
            return this;
        }

        public NotificationPreferenceBuilder eventType(NotificationType eventType) {
            this.eventType = eventType;
            return this;
        }

        public NotificationPreferenceBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public NotificationPreferenceBuilder quietHoursStart(LocalTime quietHoursStart) {
            this.quietHoursStart = quietHoursStart;
            return this;
        }

        public NotificationPreferenceBuilder quietHoursEnd(LocalTime quietHoursEnd) {
            this.quietHoursEnd = quietHoursEnd;
            return this;
        }

        public NotificationPreferenceBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public NotificationPreference build() {
            return new NotificationPreference(this.id, this.user, this.channel, this.eventType, this.enabled, this.quietHoursStart, this.quietHoursEnd, this.updatedAt);
        }
    }

    public static NotificationPreferenceBuilder builder() {
        return new NotificationPreferenceBuilder();
    }

}
