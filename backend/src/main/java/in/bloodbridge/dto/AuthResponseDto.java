package in.bloodbridge.dto;

import in.bloodbridge.enums.Role;
import in.bloodbridge.enums.UserStatus;
import java.util.UUID;

public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
        private String tokenType = "Bearer";
    private UUID userId;
    private String email;
    private String fullName;
    private Role role;
    private UserStatus status;
    private UUID profileId;
    private String bloodGroup;
    private String city;


    public AuthResponseDto() {
    }

    public AuthResponseDto(String accessToken, String refreshToken, String tokenType, UUID userId, String email, String fullName, Role role, UserStatus status, UUID profileId, String bloodGroup, String city) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.profileId = profileId;
        this.bloodGroup = bloodGroup;
        this.city = city;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public UUID getProfileId() {
        return profileId;
    }
    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }
    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public static class AuthResponseDtoBuilder {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private UUID userId;
        private String email;
        private String fullName;
        private Role role;
        private UserStatus status;
        private UUID profileId;
        private String bloodGroup;
        private String city;

        public AuthResponseDtoBuilder() {}

        public AuthResponseDtoBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AuthResponseDtoBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AuthResponseDtoBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public AuthResponseDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public AuthResponseDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthResponseDtoBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public AuthResponseDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public AuthResponseDtoBuilder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public AuthResponseDtoBuilder profileId(UUID profileId) {
            this.profileId = profileId;
            return this;
        }

        public AuthResponseDtoBuilder bloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public AuthResponseDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AuthResponseDto build() {
            return new AuthResponseDto(this.accessToken, this.refreshToken, this.tokenType, this.userId, this.email, this.fullName, this.role, this.status, this.profileId, this.bloodGroup, this.city);
        }
    }

    public static AuthResponseDtoBuilder builder() {
        return new AuthResponseDtoBuilder();
    }

}
