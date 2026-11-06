package in.bloodbridge.entity;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.ContactPreference;
import in.bloodbridge.enums.RequestPriority;
import in.bloodbridge.enums.RequestStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "request_code", nullable = false, unique = true, length = 50)
    private String requestCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_user_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_profile_id")
    private HospitalProfile hospitalProfile;

    @Column(name = "patient_name", nullable = false, length = 150)
    private String patientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 10)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
        private int units = 1;

    @Column(name = "units_fulfilled", nullable = false)
        private int unitsFulfilled = 0;

    @Column(name = "hospital_name", nullable = false, length = 200)
    private String hospitalName;

    @Column(name = "hospital_address", nullable = false, length = 255)
    private String hospitalAddress;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "pin_code", nullable = false, length = 10)
    private String pinCode;

    private Double lat;

    private Double lon;

    @Column(name = "required_by", nullable = false)
    private Instant requiredBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private RequestPriority priority = RequestPriority.URGENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private RequestStatus status = RequestStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_preference", nullable = false, length = 20)
        private ContactPreference contactPreference = ContactPreference.PHONE;

    @Column(length = 500)
    private String notes;

    @Column(name = "fingerprint_hash", length = 64)
    private String fingerprintHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by")
    private User verifiedBy;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "closed_reason", length = 255)
    private String closedReason;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public BloodRequest() {
    }

    public BloodRequest(UUID id, String requestCode, User requester, HospitalProfile hospitalProfile, String patientName, BloodGroup bloodGroup, int units, int unitsFulfilled, String hospitalName, String hospitalAddress, String city, String state, String pinCode, Double lat, Double lon, Instant requiredBy, RequestPriority priority, RequestStatus status, ContactPreference contactPreference, String notes, String fingerprintHash, User verifiedBy, Instant verifiedAt, String closedReason, Instant deletedAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.requestCode = requestCode;
        this.requester = requester;
        this.hospitalProfile = hospitalProfile;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.unitsFulfilled = unitsFulfilled;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.lat = lat;
        this.lon = lon;
        this.requiredBy = requiredBy;
        this.priority = priority;
        this.status = status;
        this.contactPreference = contactPreference;
        this.notes = notes;
        this.fingerprintHash = fingerprintHash;
        this.verifiedBy = verifiedBy;
        this.verifiedAt = verifiedAt;
        this.closedReason = closedReason;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getRequestCode() {
        return requestCode;
    }
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
    public User getRequester() {
        return requester;
    }
    public void setRequester(User requester) {
        this.requester = requester;
    }
    public HospitalProfile getHospitalProfile() {
        return hospitalProfile;
    }
    public void setHospitalProfile(HospitalProfile hospitalProfile) {
        this.hospitalProfile = hospitalProfile;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public int getUnits() {
        return units;
    }
    public void setUnits(int units) {
        this.units = units;
    }
    public int getUnitsFulfilled() {
        return unitsFulfilled;
    }
    public void setUnitsFulfilled(int unitsFulfilled) {
        this.unitsFulfilled = unitsFulfilled;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getHospitalAddress() {
        return hospitalAddress;
    }
    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
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
    public Instant getRequiredBy() {
        return requiredBy;
    }
    public void setRequiredBy(Instant requiredBy) {
        this.requiredBy = requiredBy;
    }
    public RequestPriority getPriority() {
        return priority;
    }
    public void setPriority(RequestPriority priority) {
        this.priority = priority;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    public ContactPreference getContactPreference() {
        return contactPreference;
    }
    public void setContactPreference(ContactPreference contactPreference) {
        this.contactPreference = contactPreference;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getFingerprintHash() {
        return fingerprintHash;
    }
    public void setFingerprintHash(String fingerprintHash) {
        this.fingerprintHash = fingerprintHash;
    }
    public User getVerifiedBy() {
        return verifiedBy;
    }
    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
    public Instant getVerifiedAt() {
        return verifiedAt;
    }
    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
    public String getClosedReason() {
        return closedReason;
    }
    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }
    public Instant getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
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
    public static class BloodRequestBuilder {
        private UUID id;
        private String requestCode;
        private User requester;
        private HospitalProfile hospitalProfile;
        private String patientName;
        private BloodGroup bloodGroup;
        private int units = 1;
        private int unitsFulfilled = 0;
        private String hospitalName;
        private String hospitalAddress;
        private String city;
        private String state;
        private String pinCode;
        private Double lat;
        private Double lon;
        private Instant requiredBy;
        private RequestPriority priority = RequestPriority.URGENT;
        private RequestStatus status = RequestStatus.PENDING;
        private ContactPreference contactPreference = ContactPreference.PHONE;
        private String notes;
        private String fingerprintHash;
        private User verifiedBy;
        private Instant verifiedAt;
        private String closedReason;
        private Instant deletedAt;
        private Instant createdAt;
        private Instant updatedAt;

        public BloodRequestBuilder() {}

        public BloodRequestBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BloodRequestBuilder requestCode(String requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public BloodRequestBuilder requester(User requester) {
            this.requester = requester;
            return this;
        }

        public BloodRequestBuilder hospitalProfile(HospitalProfile hospitalProfile) {
            this.hospitalProfile = hospitalProfile;
            return this;
        }

        public BloodRequestBuilder patientName(String patientName) {
            this.patientName = patientName;
            return this;
        }

        public BloodRequestBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public BloodRequestBuilder units(int units) {
            this.units = units;
            return this;
        }

        public BloodRequestBuilder unitsFulfilled(int unitsFulfilled) {
            this.unitsFulfilled = unitsFulfilled;
            return this;
        }

        public BloodRequestBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public BloodRequestBuilder hospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
            return this;
        }

        public BloodRequestBuilder city(String city) {
            this.city = city;
            return this;
        }

        public BloodRequestBuilder state(String state) {
            this.state = state;
            return this;
        }

        public BloodRequestBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public BloodRequestBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public BloodRequestBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public BloodRequestBuilder requiredBy(Instant requiredBy) {
            this.requiredBy = requiredBy;
            return this;
        }

        public BloodRequestBuilder priority(RequestPriority priority) {
            this.priority = priority;
            return this;
        }

        public BloodRequestBuilder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public BloodRequestBuilder contactPreference(ContactPreference contactPreference) {
            this.contactPreference = contactPreference;
            return this;
        }

        public BloodRequestBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public BloodRequestBuilder fingerprintHash(String fingerprintHash) {
            this.fingerprintHash = fingerprintHash;
            return this;
        }

        public BloodRequestBuilder verifiedBy(User verifiedBy) {
            this.verifiedBy = verifiedBy;
            return this;
        }

        public BloodRequestBuilder verifiedAt(Instant verifiedAt) {
            this.verifiedAt = verifiedAt;
            return this;
        }

        public BloodRequestBuilder closedReason(String closedReason) {
            this.closedReason = closedReason;
            return this;
        }

        public BloodRequestBuilder deletedAt(Instant deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public BloodRequestBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BloodRequestBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BloodRequest build() {
            return new BloodRequest(this.id, this.requestCode, this.requester, this.hospitalProfile, this.patientName, this.bloodGroup, this.units, this.unitsFulfilled, this.hospitalName, this.hospitalAddress, this.city, this.state, this.pinCode, this.lat, this.lon, this.requiredBy, this.priority, this.status, this.contactPreference, this.notes, this.fingerprintHash, this.verifiedBy, this.verifiedAt, this.closedReason, this.deletedAt, this.createdAt, this.updatedAt);
        }
    }

    public static BloodRequestBuilder builder() {
        return new BloodRequestBuilder();
    }

}
