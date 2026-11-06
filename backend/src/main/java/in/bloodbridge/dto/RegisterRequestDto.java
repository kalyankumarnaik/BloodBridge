package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.Role;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegisterRequestDto {

    @NotNull(message = "Role is required")
    private Role role;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 10, message = "Password must be at least 10 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Mobile number must be a valid 10-digit Indian number starting with 6-9")
    private String phone;

    // Donor Profile
    private BloodGroup bloodGroup;
    private LocalDate dob;
    private String city;
    private String state;
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "PIN code must be a valid 6-digit Indian postal code")
    private String pinCode;
    private Double approxLat;
    private Double approxLon;
    private LocalDate lastDonationDate;
    private Boolean showInSearch;
    private Boolean shareContactOnAccept;
    private Boolean emergencyAlertsEnabled;

    // Hospital Profile
    private String hospitalName;
    private String registrationNumber;
    private String hospitalType;
    private String address;
    private String contactPerson;

    // Patient Profile
    private String relationshipDefault;


    public RegisterRequestDto() {
    }

    public RegisterRequestDto(Role role, String email, String password, String fullName, String phone, BloodGroup bloodGroup, LocalDate dob, String city, String state, String pinCode, Double approxLat, Double approxLon, LocalDate lastDonationDate, Boolean showInSearch, Boolean shareContactOnAccept, Boolean emergencyAlertsEnabled, String hospitalName, String registrationNumber, String hospitalType, String address, String contactPerson, String relationshipDefault) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.dob = dob;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.approxLat = approxLat;
        this.approxLon = approxLon;
        this.lastDonationDate = lastDonationDate;
        this.showInSearch = showInSearch;
        this.shareContactOnAccept = shareContactOnAccept;
        this.emergencyAlertsEnabled = emergencyAlertsEnabled;
        this.hospitalName = hospitalName;
        this.registrationNumber = registrationNumber;
        this.hospitalType = hospitalType;
        this.address = address;
        this.contactPerson = contactPerson;
        this.relationshipDefault = relationshipDefault;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
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
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public Double getApproxLat() {
        return approxLat;
    }
    public void setApproxLat(Double approxLat) {
        this.approxLat = approxLat;
    }
    public Double getApproxLon() {
        return approxLon;
    }
    public void setApproxLon(Double approxLon) {
        this.approxLon = approxLon;
    }
    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }
    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
    public Boolean isShowInSearch() {
        return showInSearch;
    }
    public Boolean getShowInSearch() {
        return showInSearch;
    }
    public void setShowInSearch(Boolean showInSearch) {
        this.showInSearch = showInSearch;
    }
    public Boolean isShareContactOnAccept() {
        return shareContactOnAccept;
    }
    public Boolean getShareContactOnAccept() {
        return shareContactOnAccept;
    }
    public void setShareContactOnAccept(Boolean shareContactOnAccept) {
        this.shareContactOnAccept = shareContactOnAccept;
    }
    public Boolean isEmergencyAlertsEnabled() {
        return emergencyAlertsEnabled;
    }
    public Boolean getEmergencyAlertsEnabled() {
        return emergencyAlertsEnabled;
    }
    public void setEmergencyAlertsEnabled(Boolean emergencyAlertsEnabled) {
        this.emergencyAlertsEnabled = emergencyAlertsEnabled;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getHospitalType() {
        return hospitalType;
    }
    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    public String getRelationshipDefault() {
        return relationshipDefault;
    }
    public void setRelationshipDefault(String relationshipDefault) {
        this.relationshipDefault = relationshipDefault;
    }
    public static class RegisterRequestDtoBuilder {
        private Role role;
        private String email;
        private String password;
        private String fullName;
        private String phone;
        private BloodGroup bloodGroup;
        private LocalDate dob;
        private String city;
        private String state;
        private String pinCode;
        private Double approxLat;
        private Double approxLon;
        private LocalDate lastDonationDate;
        private Boolean showInSearch;
        private Boolean shareContactOnAccept;
        private Boolean emergencyAlertsEnabled;
        private String hospitalName;
        private String registrationNumber;
        private String hospitalType;
        private String address;
        private String contactPerson;
        private String relationshipDefault;

        public RegisterRequestDtoBuilder() {}

        public RegisterRequestDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public RegisterRequestDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterRequestDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequestDtoBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public RegisterRequestDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public RegisterRequestDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public RegisterRequestDtoBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public RegisterRequestDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public RegisterRequestDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public RegisterRequestDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public RegisterRequestDtoBuilder approxLat(Double approxLat) {
            this.approxLat = approxLat;
            return this;
        }

        public RegisterRequestDtoBuilder approxLon(Double approxLon) {
            this.approxLon = approxLon;
            return this;
        }

        public RegisterRequestDtoBuilder lastDonationDate(LocalDate lastDonationDate) {
            this.lastDonationDate = lastDonationDate;
            return this;
        }

        public RegisterRequestDtoBuilder showInSearch(Boolean showInSearch) {
            this.showInSearch = showInSearch;
            return this;
        }

        public RegisterRequestDtoBuilder shareContactOnAccept(Boolean shareContactOnAccept) {
            this.shareContactOnAccept = shareContactOnAccept;
            return this;
        }

        public RegisterRequestDtoBuilder emergencyAlertsEnabled(Boolean emergencyAlertsEnabled) {
            this.emergencyAlertsEnabled = emergencyAlertsEnabled;
            return this;
        }

        public RegisterRequestDtoBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public RegisterRequestDtoBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public RegisterRequestDtoBuilder hospitalType(String hospitalType) {
            this.hospitalType = hospitalType;
            return this;
        }

        public RegisterRequestDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public RegisterRequestDtoBuilder contactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public RegisterRequestDtoBuilder relationshipDefault(String relationshipDefault) {
            this.relationshipDefault = relationshipDefault;
            return this;
        }

        public RegisterRequestDto build() {
            return new RegisterRequestDto(this.role, this.email, this.password, this.fullName, this.phone, this.bloodGroup, this.dob, this.city, this.state, this.pinCode, this.approxLat, this.approxLon, this.lastDonationDate, this.showInSearch, this.shareContactOnAccept, this.emergencyAlertsEnabled, this.hospitalName, this.registrationNumber, this.hospitalType, this.address, this.contactPerson, this.relationshipDefault);
        }
    }

    public static RegisterRequestDtoBuilder builder() {
        return new RegisterRequestDtoBuilder();
    }

}
