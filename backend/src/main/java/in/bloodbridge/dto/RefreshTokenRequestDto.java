package in.bloodbridge.dto;

import jakarta.validation.constraints.NotBlank;
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;


    public RefreshTokenRequestDto() {
    }

    public RefreshTokenRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public static class RefreshTokenRequestDtoBuilder {
        private String refreshToken;

        public RefreshTokenRequestDtoBuilder() {}

        public RefreshTokenRequestDtoBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public RefreshTokenRequestDto build() {
            return new RefreshTokenRequestDto(this.refreshToken);
        }
    }

    public static RefreshTokenRequestDtoBuilder builder() {
        return new RefreshTokenRequestDtoBuilder();
    }

}
