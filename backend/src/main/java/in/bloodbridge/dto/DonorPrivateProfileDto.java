package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class DonorPrivateProfileDto {
    private UUID id;
    private UUID userId;
    private String fullName;
    private String email;
    private String phone;
    private BloodGroup bloodGroup;
    private String bloodGroupLabel;
    private LocalDate dob;
    private String city;
    private String state;
    private String pinCode;
    private Double approxLat;
    private Double approxLon;
    private LocalDate lastDonationDate;
    private DonorAvailability availability;
    private boolean showInSearch;
    private boolean shareContactOnAccept;
    private boolean emergencyAlertsEnabled;
    private boolean verifiedByAdmin;
    private int totalDonations;
    private String informationalReminder;
    private Instant createdAt;


    public DonorPrivateProfileDto() {
    }

    public DonorPrivateProfileDto(UUID id, UUID userId, String fullName, String email, String phone, BloodGroup bloodGroup, String bloodGroupLabel, LocalDate dob, String city, String state, String pinCode, Double approxLat, Double approxLon, LocalDate lastDonationDate, DonorAvailability availability, boolean showInSearch, boolean shareContactOnAccept, boolean emergencyAlertsEnabled, boolean verifiedByAdmin, int totalDonations, String informationalReminder, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.bloodGroupLabel = bloodGroupLabel;
        this.dob = dob;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.approxLat = approxLat;
        this.approxLon = approxLon;
        this.lastDonationDate = lastDonationDate;
        this.availability = availability;
        this.showInSearch = showInSearch;
        this.shareContactOnAccept = shareContactOnAccept;
        this.emergencyAlertsEnabled = emergencyAlertsEnabled;
        this.verifiedByAdmin = verifiedByAdmin;
        this.totalDonations = totalDonations;
        this.informationalReminder = informationalReminder;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public String getBloodGroupLabel() {
        return bloodGroupLabel;
    }
    public void setBloodGroupLabel(String bloodGroupLabel) {
        this.bloodGroupLabel = bloodGroupLabel;
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
    public DonorAvailability getAvailability() {
        return availability;
    }
    public void setAvailability(DonorAvailability availability) {
        this.availability = availability;
    }
    public boolean isShowInSearch() {
        return showInSearch;
    }
    public boolean getShowInSearch() {
        return showInSearch;
    }
    public void setShowInSearch(boolean showInSearch) {
        this.showInSearch = showInSearch;
    }
    public boolean isShareContactOnAccept() {
        return shareContactOnAccept;
    }
    public boolean getShareContactOnAccept() {
        return shareContactOnAccept;
    }
    public void setShareContactOnAccept(boolean shareContactOnAccept) {
        this.shareContactOnAccept = shareContactOnAccept;
    }
    public boolean isEmergencyAlertsEnabled() {
        return emergencyAlertsEnabled;
    }
    public boolean getEmergencyAlertsEnabled() {
        return emergencyAlertsEnabled;
    }
    public void setEmergencyAlertsEnabled(boolean emergencyAlertsEnabled) {
        this.emergencyAlertsEnabled = emergencyAlertsEnabled;
    }
    public boolean isVerifiedByAdmin() {
        return verifiedByAdmin;
    }
    public boolean getVerifiedByAdmin() {
        return verifiedByAdmin;
    }
    public void setVerifiedByAdmin(boolean verifiedByAdmin) {
        this.verifiedByAdmin = verifiedByAdmin;
    }
    public int getTotalDonations() {
        return totalDonations;
    }
    public void setTotalDonations(int totalDonations) {
        this.totalDonations = totalDonations;
    }
    public String getInformationalReminder() {
        return informationalReminder;
    }
    public void setInformationalReminder(String informationalReminder) {
        this.informationalReminder = informationalReminder;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class DonorPrivateProfileDtoBuilder {
        private UUID id;
        private UUID userId;
        private String fullName;
        private String email;
        private String phone;
        private BloodGroup bloodGroup;
        private String bloodGroupLabel;
        private LocalDate dob;
        private String city;
        private String state;
        private String pinCode;
        private Double approxLat;
        private Double approxLon;
        private LocalDate lastDonationDate;
        private DonorAvailability availability;
        private boolean showInSearch;
        private boolean shareContactOnAccept;
        private boolean emergencyAlertsEnabled;
        private boolean verifiedByAdmin;
        private int totalDonations;
        private String informationalReminder;
        private Instant createdAt;

        public DonorPrivateProfileDtoBuilder() {}

        public DonorPrivateProfileDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonorPrivateProfileDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public DonorPrivateProfileDtoBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public DonorPrivateProfileDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DonorPrivateProfileDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public DonorPrivateProfileDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonorPrivateProfileDtoBuilder bloodGroupLabel(String bloodGroupLabel) {
            this.bloodGroupLabel = bloodGroupLabel;
            return this;
        }

        public DonorPrivateProfileDtoBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public DonorPrivateProfileDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public DonorPrivateProfileDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public DonorPrivateProfileDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public DonorPrivateProfileDtoBuilder approxLat(Double approxLat) {
            this.approxLat = approxLat;
            return this;
        }

        public DonorPrivateProfileDtoBuilder approxLon(Double approxLon) {
            this.approxLon = approxLon;
            return this;
        }

        public DonorPrivateProfileDtoBuilder lastDonationDate(LocalDate lastDonationDate) {
            this.lastDonationDate = lastDonationDate;
            return this;
        }

        public DonorPrivateProfileDtoBuilder availability(DonorAvailability availability) {
            this.availability = availability;
            return this;
        }

        public DonorPrivateProfileDtoBuilder showInSearch(boolean showInSearch) {
            this.showInSearch = showInSearch;
            return this;
        }

        public DonorPrivateProfileDtoBuilder shareContactOnAccept(boolean shareContactOnAccept) {
            this.shareContactOnAccept = shareContactOnAccept;
            return this;
        }

        public DonorPrivateProfileDtoBuilder emergencyAlertsEnabled(boolean emergencyAlertsEnabled) {
            this.emergencyAlertsEnabled = emergencyAlertsEnabled;
            return this;
        }

        public DonorPrivateProfileDtoBuilder verifiedByAdmin(boolean verifiedByAdmin) {
            this.verifiedByAdmin = verifiedByAdmin;
            return this;
        }

        public DonorPrivateProfileDtoBuilder totalDonations(int totalDonations) {
            this.totalDonations = totalDonations;
            return this;
        }

        public DonorPrivateProfileDtoBuilder informationalReminder(String informationalReminder) {
            this.informationalReminder = informationalReminder;
            return this;
        }

        public DonorPrivateProfileDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonorPrivateProfileDto build() {
            return new DonorPrivateProfileDto(this.id, this.userId, this.fullName, this.email, this.phone, this.bloodGroup, this.bloodGroupLabel, this.dob, this.city, this.state, this.pinCode, this.approxLat, this.approxLon, this.lastDonationDate, this.availability, this.showInSearch, this.shareContactOnAccept, this.emergencyAlertsEnabled, this.verifiedByAdmin, this.totalDonations, this.informationalReminder, this.createdAt);
        }
    }

    public static DonorPrivateProfileDtoBuilder builder() {
        return new DonorPrivateProfileDtoBuilder();
    }

}
