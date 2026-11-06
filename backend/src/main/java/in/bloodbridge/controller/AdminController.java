package in.bloodbridge.controller;

import in.bloodbridge.dto.AdminAnalyticsDto;
import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.UserDto;
import in.bloodbridge.entity.AuditLog;
import in.bloodbridge.enums.Role;
import in.bloodbridge.enums.UserStatus;
import in.bloodbridge.enums.VerificationStatus;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.AdminService;
import in.bloodbridge.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Administrator Management", description = "Endpoints for platform administrators: analytics, hospital approvals, moderation, audit logs, and data exports")
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    public AdminController(AdminService adminService, AuditService auditService) {
        this.adminService = adminService;
        this.auditService = auditService;
    }


    private final AdminService adminService;
    private final AuditService auditService;

    @Operation(summary = "Get platform-wide analytics and key performance indicators (KPIs)")
    @GetMapping("/analytics")
    public ResponseEntity<ApiResponseDto<AdminAnalyticsDto>> getAnalytics() {
        AdminAnalyticsDto analytics = adminService.getDashboardAnalytics();
        return ResponseEntity.ok(ApiResponseDto.ok(analytics));
    }

    @Operation(summary = "Manage registered users across roles with status filters and pagination")
    @GetMapping("/users")
    public ResponseEntity<ApiResponseDto<Page<UserDto>>> getUsers(
            @RequestParam(required = false) Role role,
            Pageable pageable) {
        Page<UserDto> users = adminService.getUsers(role, pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(users));
    }

    @Operation(summary = "Update user account status (e.g. SUSPEND or ACTIVATE)")
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ApiResponseDto<Void>> updateUserStatus(
            @PathVariable UUID id,
            @RequestParam UserStatus status,
            @RequestParam(required = false) String reason,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        adminService.updateUserStatus(id, principal.getId(), status, reason, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "User status updated to " + status));
    }

    @Operation(summary = "Review and approve/reject hospital verification request")
    @PostMapping("/hospitals/{id}/review")
    public ResponseEntity<ApiResponseDto<Void>> reviewHospital(
            @PathVariable UUID id,
            @RequestParam VerificationStatus status,
            @RequestParam(required = false) String reason,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        adminService.reviewHospital(id, principal.getId(), status, reason, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Hospital verification status updated to " + status));
    }

    @Operation(summary = "Export blood requests report in CSV format with formula injection sanitization")
    @GetMapping(value = "/reports/csv", produces = "text/csv")
    public ResponseEntity<String> exportRequestsCsv(@AuthenticationPrincipal UserPrincipal principal) {
        String csvData = adminService.exportRequestsCsv(principal.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bloodbridge_requests_" + System.currentTimeMillis() + ".csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }

    @Operation(summary = "Search platform audit logs for compliance, security, and privileged actions")
    @GetMapping("/audit-logs")
    public ResponseEntity<ApiResponseDto<Page<AuditLog>>> getAuditLogs(Pageable pageable) {
        Page<AuditLog> logs = auditService.getAuditLogs(pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(logs));
    }
}
