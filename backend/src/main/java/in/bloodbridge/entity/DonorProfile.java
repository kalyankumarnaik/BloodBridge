package in.bloodbridge.entity;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "donor_profiles")
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false)
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 10)
    private BloodGroup bloodGroup;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    @Column(name = "approx_lat")
    private Double approxLat;

    @Column(name = "approx_lon")
    private Double approxLon;

    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private DonorAvailability availability = DonorAvailability.AVAILABLE;

    @Column(name = "snooze_until")
    private Instant snoozeUntil;

    @Column(name = "show_in_search", nullable = false)
        private boolean showInSearch = true;

    @Column(name = "share_contact_on_accept", nullable = false)
        private boolean shareContactOnAccept = false;

    @Column(name = "emergency_alerts_enabled", nullable = false)
        private boolean emergencyAlertsEnabled = true;

    @Column(name = "notification_consent_at")
    private Instant notificationConsentAt;

    @Column(name = "verified_by_admin", nullable = false)
        private boolean verifiedByAdmin = false;

    @Column(name = "last_active_at")
    private Instant lastActiveAt;

    @Column(name = "total_donations", nullable = false)
        private int totalDonations = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public DonorProfile() {
    }

    public DonorProfile(UUID id, User user, String fullName, LocalDate dob, BloodGroup bloodGroup, String city, String state, String pinCode, Double approxLat, Double approxLon, LocalDate lastDonationDate, DonorAvailability availability, Instant snoozeUntil, boolean showInSearch, boolean shareContactOnAccept, boolean emergencyAlertsEnabled, Instant notificationConsentAt, boolean verifiedByAdmin, Instant lastActiveAt, int totalDonations, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.approxLat = approxLat;
        this.approxLon = approxLon;
        this.lastDonationDate = lastDonationDate;
        this.availability = availability;
        this.snoozeUntil = snoozeUntil;
        this.showInSearch = showInSearch;
        this.shareContactOnAccept = shareContactOnAccept;
        this.emergencyAlertsEnabled = emergencyAlertsEnabled;
        this.notificationConsentAt = notificationConsentAt;
        this.verifiedByAdmin = verifiedByAdmin;
        this.lastActiveAt = lastActiveAt;
        this.totalDonations = totalDonations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
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
    public Instant getSnoozeUntil() {
        return snoozeUntil;
    }
    public void setSnoozeUntil(Instant snoozeUntil) {
        this.snoozeUntil = snoozeUntil;
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
    public Instant getNotificationConsentAt() {
        return notificationConsentAt;
    }
    public void setNotificationConsentAt(Instant notificationConsentAt) {
        this.notificationConsentAt = notificationConsentAt;
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
    public Instant getLastActiveAt() {
        return lastActiveAt;
    }
    public void setLastActiveAt(Instant lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
    public int getTotalDonations() {
        return totalDonations;
    }
    public void setTotalDonations(int totalDonations) {
        this.totalDonations = totalDonations;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static class DonorProfileBuilder {
        private UUID id;
        private User user;
        private String fullName;
        private LocalDate dob;
        private BloodGroup bloodGroup;
        private String city;
        private String state;
        private String pinCode;
        private Double approxLat;
        private Double approxLon;
        private LocalDate lastDonationDate;
        private DonorAvailability availability = DonorAvailability.AVAILABLE;
        private Instant snoozeUntil;
        private boolean showInSearch = true;
        private boolean shareContactOnAccept = false;
        private boolean emergencyAlertsEnabled = true;
        private Instant notificationConsentAt;
        private boolean verifiedByAdmin = false;
        private Instant lastActiveAt;
        private int totalDonations = 0;
        private Instant createdAt;
        private Instant updatedAt;

        public DonorProfileBuilder() {}

        public DonorProfileBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonorProfileBuilder user(User user) {
            this.user = user;
            return this;
        }

        public DonorProfileBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public DonorProfileBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public DonorProfileBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonorProfileBuilder city(String city) {
            this.city = city;
            return this;
        }

        public DonorProfileBuilder state(String state) {
            this.state = state;
            return this;
        }

        public DonorProfileBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public DonorProfileBuilder approxLat(Double approxLat) {
            this.approxLat = approxLat;
            return this;
        }

        public DonorProfileBuilder approxLon(Double approxLon) {
            this.approxLon = approxLon;
            return this;
        }

        public DonorProfileBuilder lastDonationDate(LocalDate lastDonationDate) {
            this.lastDonationDate = lastDonationDate;
            return this;
        }

        public DonorProfileBuilder availability(DonorAvailability availability) {
            this.availability = availability;
            return this;
        }

        public DonorProfileBuilder snoozeUntil(Instant snoozeUntil) {
            this.snoozeUntil = snoozeUntil;
            return this;
        }

        public DonorProfileBuilder showInSearch(boolean showInSearch) {
            this.showInSearch = showInSearch;
            return this;
        }

        public DonorProfileBuilder shareContactOnAccept(boolean shareContactOnAccept) {
            this.shareContactOnAccept = shareContactOnAccept;
            return this;
        }

        public DonorProfileBuilder emergencyAlertsEnabled(boolean emergencyAlertsEnabled) {
            this.emergencyAlertsEnabled = emergencyAlertsEnabled;
            return this;
        }

        public DonorProfileBuilder notificationConsentAt(Instant notificationConsentAt) {
            this.notificationConsentAt = notificationConsentAt;
            return this;
        }

        public DonorProfileBuilder verifiedByAdmin(boolean verifiedByAdmin) {
            this.verifiedByAdmin = verifiedByAdmin;
            return this;
        }

        public DonorProfileBuilder lastActiveAt(Instant lastActiveAt) {
            this.lastActiveAt = lastActiveAt;
            return this;
        }

        public DonorProfileBuilder totalDonations(int totalDonations) {
            this.totalDonations = totalDonations;
            return this;
        }

        public DonorProfileBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonorProfileBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public DonorProfile build() {
            return new DonorProfile(this.id, this.user, this.fullName, this.dob, this.bloodGroup, this.city, this.state, this.pinCode, this.approxLat, this.approxLon, this.lastDonationDate, this.availability, this.snoozeUntil, this.showInSearch, this.shareContactOnAccept, this.emergencyAlertsEnabled, this.notificationConsentAt, this.verifiedByAdmin, this.lastActiveAt, this.totalDonations, this.createdAt, this.updatedAt);
        }
    }

    public static DonorProfileBuilder builder() {
        return new DonorProfileBuilder();
    }

}
