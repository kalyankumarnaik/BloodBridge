package in.bloodbridge.dto;

import in.bloodbridge.enums.Role;
import in.bloodbridge.enums.UserStatus;
import java.time.Instant;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String email;
    private String phone;
    private Role role;
    private UserStatus status;
    private Instant createdAt;


    public UserDto() {
    }

    public UserDto(UUID id, String email, String phone, Role role, UserStatus status, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class UserDtoBuilder {
        private UUID id;
        private String email;
        private String phone;
        private Role role;
        private UserStatus status;
        private Instant createdAt;

        public UserDtoBuilder() {}

        public UserDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserDtoBuilder status(UserStatus status) {
            this.status = status;
            return this;
        }

        public UserDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDto build() {
            return new UserDto(this.id, this.email, this.phone, this.role, this.status, this.createdAt);
        }
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

}
