package in.bloodbridge.controller;

import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.CompatibilityResultDto;
import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.service.CompatibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Blood Compatibility Engine", description = "Endpoints for Red Blood Cell (RBC) compatibility guidance and matrix")
@RestController
@RequestMapping("/api/v1/compatibility")
public class CompatibilityController {
    public CompatibilityController(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }


    private final CompatibilityService compatibilityService;

    @Operation(summary = "Get red cell compatibility details for a specific blood group or entire ABO/Rh matrix")
    @GetMapping
    public ResponseEntity<ApiResponseDto<Object>> getCompatibility(
            @RequestParam(required = false) String bloodGroup) {
        if (bloodGroup != null && !bloodGroup.isBlank()) {
            BloodGroup bg = BloodGroup.fromLabel(bloodGroup);
            CompatibilityResultDto result = compatibilityService.getCompatibilityDetails(bg);
            return ResponseEntity.ok(ApiResponseDto.ok(result));
        }

        List<CompatibilityResultDto> all = compatibilityService.getAllCompatibilityGuides();
        return ResponseEntity.ok(ApiResponseDto.ok(all));
    }
}
