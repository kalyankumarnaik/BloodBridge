package in.bloodbridge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class AuthRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 10, message = "Password must be at least 10 characters")
    private String password;


    public AuthRequestDto() {
    }

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static class AuthRequestDtoBuilder {
        private String email;
        private String password;

        public AuthRequestDtoBuilder() {}

        public AuthRequestDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthRequestDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AuthRequestDto build() {
            return new AuthRequestDto(this.email, this.password);
        }
    }

    public static AuthRequestDtoBuilder builder() {
        return new AuthRequestDtoBuilder();
    }

}
