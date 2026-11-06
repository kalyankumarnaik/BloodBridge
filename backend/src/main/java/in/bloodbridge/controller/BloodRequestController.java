package in.bloodbridge.controller;

import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.BloodRequestDto;
import in.bloodbridge.dto.DonorResponseDto;
import in.bloodbridge.dto.EmergencyRequestCreateDto;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorResponseStatus;
import in.bloodbridge.enums.RequestPriority;
import in.bloodbridge.enums.RequestStatus;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.BloodRequestService;
import in.bloodbridge.service.DonorResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Blood Requests", description = "Endpoints for managing emergency blood requests, state machine transitions, and donor responses")
@RestController
@RequestMapping("/api/v1/requests")
public class BloodRequestController {
    public BloodRequestController(BloodRequestService bloodRequestService, DonorResponseService donorResponseService) {
        this.bloodRequestService = bloodRequestService;
        this.donorResponseService = donorResponseService;
    }


    private final BloodRequestService bloodRequestService;
    private final DonorResponseService donorResponseService;

    @Operation(summary = "Create an emergency blood request with duplicate detection and validation")
    @PostMapping
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> createRequest(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody EmergencyRequestCreateDto dto,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        BloodRequestDto created = bloodRequestService.createEmergencyRequest(principal.getId(), dto, ip, ua);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.ok(created, "Blood request submitted successfully"));
    }

    @Operation(summary = "Search public/verified blood requests")
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<BloodRequestDto>>> searchRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon,
            Pageable pageable) {

        RequestStatus st = (status != null && !status.isBlank()) ? RequestStatus.valueOf(status.toUpperCase()) : null;
        BloodGroup bg = (bloodGroup != null && !bloodGroup.isBlank()) ? BloodGroup.fromLabel(bloodGroup) : null;
        RequestPriority pr = (priority != null && !priority.isBlank()) ? RequestPriority.valueOf(priority.toUpperCase()) : null;

        Page<BloodRequestDto> results = bloodRequestService.searchRequests(st, bg, city, pr, pageable, userLat, userLon);
        return ResponseEntity.ok(ApiResponseDto.ok(results));
    }

    @Operation(summary = "Get blood request details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> getRequestById(
            @PathVariable UUID id,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon) {
        BloodRequestDto dto = bloodRequestService.getRequestById(id, userLat, userLon);
        return ResponseEntity.ok(ApiResponseDto.ok(dto));
    }

    @Operation(summary = "Get blood request details by unique code (e.g., BB-20261106-XXXXXX)")
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> getRequestByCode(
            @PathVariable String code,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon) {
        BloodRequestDto dto = bloodRequestService.getRequestByCode(code, userLat, userLon);
        return ResponseEntity.ok(ApiResponseDto.ok(dto));
    }

    @Operation(summary = "Get requests created by the authenticated requester")
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<Page<BloodRequestDto>>> getMyRequests(
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable) {
        Page<BloodRequestDto> page = bloodRequestService.getRequestsByRequester(principal.getId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(page));
    }

    @Operation(summary = "Get active requests compatible with the authenticated donor")
    @GetMapping("/compatible")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<List<BloodRequestDto>>> getCompatibleRequests(
            @AuthenticationPrincipal UserPrincipal principal) {
        List<BloodRequestDto> list = bloodRequestService.getCompatibleRequestsForDonor(principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(list));
    }

    @Operation(summary = "Verify and activate an emergency request (Hospital or Admin)")
    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('HOSPITAL', 'ADMIN')")
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> verifyRequest(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        BloodRequestDto verified = bloodRequestService.verifyAndActivate(id, principal.getId(), ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(verified, "Request verified and matching donors notified"));
    }

    @Operation(summary = "Cancel a blood request")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> cancelRequest(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        BloodRequestDto cancelled = bloodRequestService.cancelRequest(id, principal.getId(), reason, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(cancelled, "Request cancelled"));
    }

    @Operation(summary = "Mark a blood request as fulfilled")
    @PostMapping("/{id}/fulfill")
    public ResponseEntity<ApiResponseDto<BloodRequestDto>> fulfillRequest(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        BloodRequestDto fulfilled = bloodRequestService.fulfillRequest(id, principal.getId(), ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(fulfilled, "Request marked as fulfilled"));
    }

    @Operation(summary = "Donor responds to an active emergency request (Accept or Decline)")
    @PostMapping("/{id}/respond")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<DonorResponseDto>> respondToRequest(
            @PathVariable UUID id,
            @RequestParam DonorResponseStatus status,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false, defaultValue = "false") boolean contactConsent,
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        DonorResponseDto resp = donorResponseService.respondToRequest(principal.getId(), id, status, reason, contactConsent, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(resp, "Response submitted successfully"));
    }

    @Operation(summary = "Get responses for a blood request (authorized for the request creator)")
    @GetMapping("/{id}/responses")
    public ResponseEntity<ApiResponseDto<Page<DonorResponseDto>>> getRequestResponses(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable) {
        Page<DonorResponseDto> responses = donorResponseService.getResponsesForRequest(id, principal.getId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(responses));
    }
}
