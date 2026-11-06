package in.bloodbridge.controller;

import in.bloodbridge.dto.ApiResponseDto;
import in.bloodbridge.dto.AuthRequestDto;
import in.bloodbridge.dto.AuthResponseDto;
import in.bloodbridge.dto.RefreshTokenRequestDto;
import in.bloodbridge.dto.RegisterRequestDto;
import in.bloodbridge.security.UserPrincipal;
import in.bloodbridge.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for registration, login, token refresh, and account management")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    private final AuthService authService;

    @Operation(summary = "Register a new user account (Donor, Patient, or Hospital)")
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto dto,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        AuthResponseDto resp = authService.register(dto, ip, ua);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.ok(resp, "Account registered successfully"));
    }

    @Operation(summary = "Authenticate user and issue JWT access & refresh tokens")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(
            @Valid @RequestBody AuthRequestDto dto,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        AuthResponseDto resp = authService.login(dto, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(resp, "Login successful"));
    }

    @Operation(summary = "Rotate refresh token and issue new access token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> refresh(
            @Valid @RequestBody RefreshTokenRequestDto dto,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        AuthResponseDto resp = authService.refreshToken(dto, ip, ua);
        return ResponseEntity.ok(ApiResponseDto.ok(resp, "Token refreshed successfully"));
    }

    @Operation(summary = "Request account deletion and anonymization (DPDP compliance)")
    @PostMapping("/delete-account")
    public ResponseEntity<ApiResponseDto<Void>> deleteAccount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        authService.requestDataDeletion(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Account deletion request submitted. Personal data will be anonymized."));
    }
}
