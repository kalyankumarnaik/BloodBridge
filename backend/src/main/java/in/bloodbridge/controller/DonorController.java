package in.bloodbridge.controller;

import in.bloodbridge.dto.*;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.DonorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Donor Operations", description = "Endpoints for searching donors, managing donor profiles, and consent-based contact workflows")
@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }


    private final DonorService donorService;

    @Operation(summary = "Search compatible, available voluntary donors with privacy-safe projections")
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<DonorPublicCardDto>>> searchDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false, defaultValue = "false") Boolean includeCompatible,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String pinCode,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double maxDistanceKm,
            @RequestParam(required = false) String availability,
            @RequestParam(required = false) Boolean verifiedOnly,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "distance") String sort) {

        BloodGroup bg = (bloodGroup != null && !bloodGroup.isBlank()) ? BloodGroup.fromLabel(bloodGroup) : null;
        DonorAvailability avail = (availability != null && !availability.isBlank()) ? DonorAvailability.valueOf(availability.toUpperCase()) : null;

        DonorSearchCriteriaDto criteria = DonorSearchCriteriaDto.builder()
                .bloodGroup(bg)
                .includeCompatible(includeCompatible)
                .city(city)
                .pinCode(pinCode)
                .latitude(latitude)
                .longitude(longitude)
                .maxDistanceKm(maxDistanceKm)
                .availability(avail)
                .verifiedOnly(verifiedOnly)
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        Page<DonorPublicCardDto> results = donorService.searchDonors(criteria);
        return ResponseEntity.ok(ApiResponseDto.ok(results));
    }

    @Operation(summary = "Get private profile details of the authenticated donor")
    @GetMapping("/me")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<DonorPrivateProfileDto>> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        DonorPrivateProfileDto profile = donorService.getProfileByUserId(principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(profile));
    }

    @Operation(summary = "Update profile, availability, and privacy settings of the authenticated donor")
    @PutMapping("/me")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<DonorPrivateProfileDto>> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody DonorPrivateProfileDto updateDto) {
        DonorPrivateProfileDto updated = donorService.updateProfile(principal.getId(), updateDto);
        return ResponseEntity.ok(ApiResponseDto.ok(updated, "Profile updated successfully"));
    }

    @Operation(summary = "Request contact details from a voluntary donor (rate limited to 5/day)")
    @PostMapping("/contact-requests")
    public ResponseEntity<ApiResponseDto<ContactRequestDto>> createContactRequest(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ContactRequestCreateDto dto,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        ContactRequestDto created = donorService.requestContact(principal.getId(), dto, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(created, "Contact request dispatched to donor"));
    }

    @Operation(summary = "Donor responds to a contact request (Accept or Decline)")
    @PostMapping("/contact-requests/{id}/respond")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<ContactRequestDto>> respondToContactRequest(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable UUID id,
            @RequestParam boolean accept,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        ContactRequestDto updated = donorService.respondToContactRequest(principal.getId(), id, accept, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(updated, accept ? "Contact request accepted" : "Contact request declined"));
    }

    @Operation(summary = "Get received contact requests for the authenticated donor")
    @GetMapping("/contact-requests")
    @PreAuthorize("hasRole('DONOR')")
    public ResponseEntity<ApiResponseDto<Page<ContactRequestDto>>> getMyReceivedContactRequests(
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable) {
        Page<ContactRequestDto> page = donorService.getDonorContactRequests(principal.getId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(page));
    }

    @Operation(summary = "Get sent contact requests for the authenticated requester")
    @GetMapping("/contact-requests/sent")
    public ResponseEntity<ApiResponseDto<Page<ContactRequestDto>>> getMySentContactRequests(
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable) {
        Page<ContactRequestDto> page = donorService.getRequesterContactRequests(principal.getId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(page));
    }
}
