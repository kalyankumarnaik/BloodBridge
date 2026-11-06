package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.dto.NotificationDto;
import in.bloodbridge.entity.Notification;
import in.bloodbridge.entity.NotificationPreference;
import in.bloodbridge.entity.User;
import in.bloodbridge.enums.NotificationChannel;
import in.bloodbridge.enums.NotificationDeliveryStatus;
import in.bloodbridge.enums.NotificationType;
import in.bloodbridge.integration.SmsProvider;
import in.bloodbridge.repository.NotificationPreferenceRepository;
import in.bloodbridge.repository.NotificationRepository;
import in.bloodbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationRepository notificationRepository, NotificationPreferenceRepository preferenceRepository, UserRepository userRepository, SmsProvider smsProvider) {
        this.notificationRepository = notificationRepository;
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
        this.smsProvider = smsProvider;
    }


    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final SmsProvider smsProvider;

    @Value("${bloodbridge.notifications.email.enabled:true}")
    private boolean emailEnabled;

    @Value("${bloodbridge.notifications.sms.enabled:true}")
    private boolean smsEnabled;

    @Value("${bloodbridge.notifications.whatsapp.base-url:https://wa.me/}")
    private String whatsappBaseUrl;

    @Async
    @Transactional
    public void dispatch(UUID userId, NotificationType type, String title, String body, String payloadJson, String requestId, boolean isCritical) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return;

        // 1. In-app notification
        Notification inApp = Notification.builder()
                .user(user)
                .type(type)
                .channel(NotificationChannel.IN_APP)
                .title(title)
                .body(body)
                .payloadJson(payloadJson)
                .status(NotificationDeliveryStatus.SENT)
                .requestId(requestId)
                .attempts(1)
                .build();
        notificationRepository.save(inApp);

        // Check quiet hours if not critical
        boolean inQuietHours = !isCritical && checkQuietHours(userId);

        // 2. Email Notification
        if (emailEnabled && !inQuietHours && isChannelEnabled(userId, NotificationChannel.EMAIL, type)) {
            sendEmailSimulation(user.getEmail(), title, body);
        }

        // 3. SMS Notification via SmsProvider
        if (smsEnabled && !inQuietHours && isChannelEnabled(userId, NotificationChannel.SMS, type)) {
            smsProvider.sendSms(user.getPhone(), "DLT_BLOOD_EMERGENCY", Map.of("title", title), body);
        }
    }

    private boolean checkQuietHours(UUID userId) {
        List<NotificationPreference> prefs = preferenceRepository.findByUserId(userId);
        LocalTime now = LocalTime.now();
        for (NotificationPreference pref : prefs) {
            if (pref.getQuietHoursStart() != null && pref.getQuietHoursEnd() != null) {
                if (pref.getQuietHoursStart().isBefore(pref.getQuietHoursEnd())) {
                    if (now.isAfter(pref.getQuietHoursStart()) && now.isBefore(pref.getQuietHoursEnd())) return true;
                } else {
                    if (now.isAfter(pref.getQuietHoursStart()) || now.isBefore(pref.getQuietHoursEnd())) return true;
                }
            }
        }
        return false;
    }

    private boolean isChannelEnabled(UUID userId, NotificationChannel channel, NotificationType type) {
        return preferenceRepository.findByUserIdAndChannelAndEventType(userId, channel, type)
                .map(NotificationPreference::isEnabled)
                .orElse(true);
    }

    private void sendEmailSimulation(String email, String title, String body) {
        log.info("[EMAIL SIMULATION] To: {} | Subject: {} | Body: {}", email, title, body);
    }

    public String generateWhatsAppShareUrl(String messageText) {
        try {
            return whatsappBaseUrl + "?text=" + URLEncoder.encode(messageText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return whatsappBaseUrl + "?text=" + messageText;
        }
    }

    @Transactional(readOnly = true)
    public Page<NotificationDto> getUserNotifications(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getRecentUserNotifications(UUID userId) {
        return notificationRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndReadAtIsNull(userId);
    }

    @Transactional
    public void markAsRead(UUID notificationId, UUID userId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            if (n.getUser().getId().equals(userId)) {
                n.setReadAt(Instant.now());
                notificationRepository.save(n);
            }
        });
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .filter(n -> n.getReadAt() == null)
                .collect(Collectors.toList());
        for (Notification n : unread) {
            n.setReadAt(Instant.now());
        }
        notificationRepository.saveAll(unread);
    }

    private NotificationDto mapToDto(Notification n) {
        return NotificationDto.builder()
                .id(n.getId())
                .userId(n.getUser().getId())
                .type(n.getType())
                .channel(n.getChannel())
                .title(n.getTitle())
                .body(n.getBody())
                .status(n.getStatus())
                .readAt(n.getReadAt())
                .requestId(n.getRequestId())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
