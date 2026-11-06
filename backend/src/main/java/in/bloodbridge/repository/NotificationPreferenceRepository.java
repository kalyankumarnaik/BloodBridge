package in.bloodbridge.repository;

import in.bloodbridge.entity.NotificationPreference;
import in.bloodbridge.enums.NotificationChannel;
import in.bloodbridge.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, UUID> {
    Optional<NotificationPreference> findByUserIdAndChannelAndEventType(UUID userId, NotificationChannel channel, NotificationType eventType);
    List<NotificationPreference> findByUserId(UUID userId);
}
