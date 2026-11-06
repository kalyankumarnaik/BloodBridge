package in.bloodbridge.controller;

import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.BloodAvailabilityDto;
import in.bloodbridge.dto.HospitalProfileDto;
import in.bloodbridge.dto.HospitalVerificationSubmitDto;
import in.bloodbridge.enums.BloodAvailabilityStatus;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Hospital Operations", description = "Endpoints for hospital verification, profile management, and published blood availability")
@RestController
public class HospitalController {
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }


    private final HospitalService hospitalService;

    @Operation(summary = "Get profile of the authenticated hospital")
    @GetMapping("/api/v1/hospital/me")
    @PreAuthorize("hasRole('HOSPITAL')")
    public ResponseEntity<ApiResponseDto<HospitalProfileDto>> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        HospitalProfileDto profile = hospitalService.getProfileByUserId(principal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(profile));
    }

    @Operation(summary = "Upload verification documents (Clinical establishment license, blood bank license)")
    @PostMapping(value = "/api/v1/hospital/verify/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('HOSPITAL')")
    public ResponseEntity<ApiResponseDto<Void>> uploadVerificationDocument(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @ModelAttribute HospitalVerificationSubmitDto dto,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        hospitalService.submitVerificationDocument(principal.getId(), dto, file, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Document uploaded and submitted for review"));
    }

    @Operation(summary = "Publish or update verified blood availability for a blood group")
    @PostMapping("/api/v1/hospital/availability")
    @PreAuthorize("hasRole('HOSPITAL')")
    public ResponseEntity<ApiResponseDto<BloodAvailabilityDto>> updateAvailability(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam BloodGroup bloodGroup,
            @RequestParam BloodAvailabilityStatus status,
            @RequestParam(required = false) Integer unitsMin,
            @RequestParam(required = false) Integer unitsMax) {
        BloodAvailabilityDto updated = hospitalService.updateBloodAvailability(principal.getId(), bloodGroup, status, unitsMin, unitsMax);
        return ResponseEntity.ok(ApiResponseDto.ok(updated, "Blood availability updated"));
    }

    @Operation(summary = "Get blood availability for a specific hospital")
    @GetMapping("/api/v1/hospital/{id}/availability")
    public ResponseEntity<ApiResponseDto<List<BloodAvailabilityDto>>> getHospitalAvailability(@PathVariable UUID id) {
        List<BloodAvailabilityDto> list = hospitalService.getHospitalAvailability(id);
        return ResponseEntity.ok(ApiResponseDto.ok(list));
    }

    @Operation(summary = "Public search for verified blood availability and hospital blood banks")
    @GetMapping("/api/v1/blood-banks/public")
    public ResponseEntity<ApiResponseDto<List<BloodAvailabilityDto>>> getPublicAvailability(
            @RequestParam(required = false) String bloodGroup) {
        BloodGroup bg = (bloodGroup != null && !bloodGroup.isBlank()) ? BloodGroup.fromLabel(bloodGroup) : null;
        List<BloodAvailabilityDto> results = (bg != null)
                ? hospitalService.getPublicAvailabilityByBloodGroup(bg)
                : List.of();
        return ResponseEntity.ok(ApiResponseDto.ok(results));
    }

    @Operation(summary = "Get list of verified hospitals")
    @GetMapping("/api/v1/blood-banks/public/hospitals")
    public ResponseEntity<ApiResponseDto<Page<HospitalProfileDto>>> getVerifiedHospitals(Pageable pageable) {
        Page<HospitalProfileDto> list = hospitalService.getVerifiedHospitals(pageable);
        return ResponseEntity.ok(ApiResponseDto.ok(list));
    }
}
