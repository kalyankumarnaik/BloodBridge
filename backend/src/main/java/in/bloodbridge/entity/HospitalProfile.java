package in.bloodbridge.entity;

import in.bloodbridge.enums.VerificationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "hospital_profiles")
public class HospitalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "registration_number", nullable = false, unique = true, length = 100)
    private String registrationNumber;

    @Column(nullable = false, length = 50)
        private String type = "GENERAL";

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    private Double lat;

    private Double lon;

    @Column(name = "official_phone", nullable = false, length = 20)
    private String officialPhone;

    @Column(name = "contact_person", length = 150)
    private String contactPerson;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 50)
        private VerificationStatus verificationStatus = VerificationStatus.SUBMITTED;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public HospitalProfile() {
    }

    public HospitalProfile(UUID id, User user, String name, String registrationNumber, String type, String address, String city, String state, String pinCode, Double lat, Double lon, String officialPhone, String contactPerson, VerificationStatus verificationStatus, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.lat = lat;
        this.lon = lon;
        this.officialPhone = officialPhone;
        this.contactPerson = contactPerson;
        this.verificationStatus = verificationStatus;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public String getOfficialPhone() {
        return officialPhone;
    }
    public void setOfficialPhone(String officialPhone) {
        this.officialPhone = officialPhone;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }
    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
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
    public static class HospitalProfileBuilder {
        private UUID id;
        private User user;
        private String name;
        private String registrationNumber;
        private String type = "GENERAL";
        private String address;
        private String city;
        private String state;
        private String pinCode;
        private Double lat;
        private Double lon;
        private String officialPhone;
        private String contactPerson;
        private VerificationStatus verificationStatus = VerificationStatus.SUBMITTED;
        private Instant createdAt;
        private Instant updatedAt;

        public HospitalProfileBuilder() {}

        public HospitalProfileBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HospitalProfileBuilder user(User user) {
            this.user = user;
            return this;
        }

        public HospitalProfileBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HospitalProfileBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public HospitalProfileBuilder type(String type) {
            this.type = type;
            return this;
        }

        public HospitalProfileBuilder address(String address) {
            this.address = address;
            return this;
        }

        public HospitalProfileBuilder city(String city) {
            this.city = city;
            return this;
        }

        public HospitalProfileBuilder state(String state) {
            this.state = state;
            return this;
        }

        public HospitalProfileBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public HospitalProfileBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public HospitalProfileBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public HospitalProfileBuilder officialPhone(String officialPhone) {
            this.officialPhone = officialPhone;
            return this;
        }

        public HospitalProfileBuilder contactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public HospitalProfileBuilder verificationStatus(VerificationStatus verificationStatus) {
            this.verificationStatus = verificationStatus;
            return this;
        }

        public HospitalProfileBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HospitalProfileBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public HospitalProfile build() {
            return new HospitalProfile(this.id, this.user, this.name, this.registrationNumber, this.type, this.address, this.city, this.state, this.pinCode, this.lat, this.lon, this.officialPhone, this.contactPerson, this.verificationStatus, this.createdAt, this.updatedAt);
        }
    }

    public static HospitalProfileBuilder builder() {
        return new HospitalProfileBuilder();
    }

}
