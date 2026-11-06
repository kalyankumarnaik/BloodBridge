package in.bloodbridge.controller;

import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.NotificationDto;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Notifications", description = "Endpoints for viewing and acknowledging user notifications")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    private final NotificationService notificationService;

    @Operation(summary = "Get paginated notifications for the authenticated user")
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<NotificationDto>>> getMyNotifications(
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable) {
        Page<NotificationDto> page = notificationService.getUserNotifications(principal.getId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(page));
    }

    @Operation(summary = "Get count of unread notifications for the authenticated user")
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponseDto<Long>> getUnreadCount(
            @AuthenticationPrincipal UserPrincipal principal) {
        long count = notificationService.getUnreadCount(principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(count));
    }

    @Operation(summary = "Mark a single notification as read")
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponseDto<Void>> markAsRead(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal) {
        notificationService.markAsRead(id, principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Notification marked as read"));
    }

    @Operation(summary = "Mark all notifications as read")
    @PostMapping("/read-all")
    public ResponseEntity<ApiResponseDto<Void>> markAllAsRead(
            @AuthenticationPrincipal UserPrincipal principal) {
        notificationService.markAllAsRead(principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(null, "All notifications marked as read"));
    }
}
