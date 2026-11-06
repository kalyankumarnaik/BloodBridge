package in.bloodbridge.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.bloodbridge.entity.AuditLog;
import in.bloodbridge.entity.User;
import in.bloodbridge.repository.AuditLogRepository;
import in.bloodbridge.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    public AuditService(AuditLogRepository auditLogRepository, UserRepository userRepository) {
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }


    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Async
    @Transactional
    public void logAction(UUID actorUserId, String action, String entityType, String entityId, String metadataJson, String ipAddress, String userAgent) {
        try {
            User actor = (actorUserId != null) ? userRepository.findById(actorUserId).orElse(null) : null;
            String ipHash = hashIp(ipAddress);

            AuditLog logEntry = AuditLog.builder()
                    .actorUser(actor)
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .ipHash(ipHash)
                    .userAgent(userAgent != null ? (userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent) : null)
                    .metadataJson(metadataJson)
                    .build();

            auditLogRepository.save(logEntry);
            log.info("[AUDIT] Action: {} | Entity: {} #{} | Actor: {}", action, entityType, entityId, (actor != null ? actor.getEmail() : "SYSTEM"));
        } catch (Exception e) {
            log.error("Failed to write audit log entry", e);
        }
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAllByOrderByOccurredAtDesc(pageable);
    }

    private String hashIp(String ip) {
        if (ip == null || ip.isBlank()) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ip.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().substring(0, 32);
        } catch (Exception e) {
            return "unknown";
        }
    }
}
