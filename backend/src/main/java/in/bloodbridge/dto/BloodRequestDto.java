package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.ContactPreference;
import in.bloodbridge.enums.RequestPriority;
import in.bloodbridge.enums.RequestStatus;
import java.time.Instant;
import java.util.UUID;

public class BloodRequestDto {
    private UUID id;
    private String requestCode;
    private UUID requesterId;
    private String requesterName;
    private UUID hospitalProfileId;
    private String hospitalName;
    private String hospitalAddress;
    private String city;
    private String state;
    private String pinCode;
    private Double lat;
    private Double lon;
    private String patientName;
    private BloodGroup bloodGroup;
    private String bloodGroupLabel;
    private int units;
    private int unitsFulfilled;
    private RequestPriority priority;
    private Instant requiredBy;
    private RequestStatus status;
    private ContactPreference contactPreference;
    private String notes;
    private UUID verifiedByUserId;
    private Instant verifiedAt;
    private String closedReason;
    private Instant createdAt;
    private Double distanceKm;
    private int responseCount;
    private String shareWhatsAppUrl; // client-friendly official WhatsApp link


    public BloodRequestDto() {
    }

    public BloodRequestDto(UUID id, String requestCode, UUID requesterId, String requesterName, UUID hospitalProfileId, String hospitalName, String hospitalAddress, String city, String state, String pinCode, Double lat, Double lon, String patientName, BloodGroup bloodGroup, String bloodGroupLabel, int units, int unitsFulfilled, RequestPriority priority, Instant requiredBy, RequestStatus status, ContactPreference contactPreference, String notes, UUID verifiedByUserId, Instant verifiedAt, String closedReason, Instant createdAt, Double distanceKm, int responseCount, String shareWhatsAppUrl) {
        this.id = id;
        this.requestCode = requestCode;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.hospitalProfileId = hospitalProfileId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.lat = lat;
        this.lon = lon;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.bloodGroupLabel = bloodGroupLabel;
        this.units = units;
        this.unitsFulfilled = unitsFulfilled;
        this.priority = priority;
        this.requiredBy = requiredBy;
        this.status = status;
        this.contactPreference = contactPreference;
        this.notes = notes;
        this.verifiedByUserId = verifiedByUserId;
        this.verifiedAt = verifiedAt;
        this.closedReason = closedReason;
        this.createdAt = createdAt;
        this.distanceKm = distanceKm;
        this.responseCount = responseCount;
        this.shareWhatsAppUrl = shareWhatsAppUrl;
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
    public UUID getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(UUID requesterId) {
        this.requesterId = requesterId;
    }
    public String getRequesterName() {
        return requesterName;
    }
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    public UUID getHospitalProfileId() {
        return hospitalProfileId;
    }
    public void setHospitalProfileId(UUID hospitalProfileId) {
        this.hospitalProfileId = hospitalProfileId;
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
    public String getBloodGroupLabel() {
        return bloodGroupLabel;
    }
    public void setBloodGroupLabel(String bloodGroupLabel) {
        this.bloodGroupLabel = bloodGroupLabel;
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
    public RequestPriority getPriority() {
        return priority;
    }
    public void setPriority(RequestPriority priority) {
        this.priority = priority;
    }
    public Instant getRequiredBy() {
        return requiredBy;
    }
    public void setRequiredBy(Instant requiredBy) {
        this.requiredBy = requiredBy;
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
    public UUID getVerifiedByUserId() {
        return verifiedByUserId;
    }
    public void setVerifiedByUserId(UUID verifiedByUserId) {
        this.verifiedByUserId = verifiedByUserId;
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
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Double getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }
    public int getResponseCount() {
        return responseCount;
    }
    public void setResponseCount(int responseCount) {
        this.responseCount = responseCount;
    }
    public String getShareWhatsAppUrl() {
        return shareWhatsAppUrl;
    }
    public void setShareWhatsAppUrl(String shareWhatsAppUrl) {
        this.shareWhatsAppUrl = shareWhatsAppUrl;
    }
    public static class BloodRequestDtoBuilder {
        private UUID id;
        private String requestCode;
        private UUID requesterId;
        private String requesterName;
        private UUID hospitalProfileId;
        private String hospitalName;
        private String hospitalAddress;
        private String city;
        private String state;
        private String pinCode;
        private Double lat;
        private Double lon;
        private String patientName;
        private BloodGroup bloodGroup;
        private String bloodGroupLabel;
        private int units;
        private int unitsFulfilled;
        private RequestPriority priority;
        private Instant requiredBy;
        private RequestStatus status;
        private ContactPreference contactPreference;
        private String notes;
        private UUID verifiedByUserId;
        private Instant verifiedAt;
        private String closedReason;
        private Instant createdAt;
        private Double distanceKm;
        private int responseCount;
        private String shareWhatsAppUrl;

        public BloodRequestDtoBuilder() {}

        public BloodRequestDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BloodRequestDtoBuilder requestCode(String requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public BloodRequestDtoBuilder requesterId(UUID requesterId) {
            this.requesterId = requesterId;
            return this;
        }

        public BloodRequestDtoBuilder requesterName(String requesterName) {
            this.requesterName = requesterName;
            return this;
        }

        public BloodRequestDtoBuilder hospitalProfileId(UUID hospitalProfileId) {
            this.hospitalProfileId = hospitalProfileId;
            return this;
        }

        public BloodRequestDtoBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public BloodRequestDtoBuilder hospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
            return this;
        }

        public BloodRequestDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public BloodRequestDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public BloodRequestDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public BloodRequestDtoBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public BloodRequestDtoBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public BloodRequestDtoBuilder patientName(String patientName) {
            this.patientName = patientName;
            return this;
        }

        public BloodRequestDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public BloodRequestDtoBuilder bloodGroupLabel(String bloodGroupLabel) {
            this.bloodGroupLabel = bloodGroupLabel;
            return this;
        }

        public BloodRequestDtoBuilder units(int units) {
            this.units = units;
            return this;
        }

        public BloodRequestDtoBuilder unitsFulfilled(int unitsFulfilled) {
            this.unitsFulfilled = unitsFulfilled;
            return this;
        }

        public BloodRequestDtoBuilder priority(RequestPriority priority) {
            this.priority = priority;
            return this;
        }

        public BloodRequestDtoBuilder requiredBy(Instant requiredBy) {
            this.requiredBy = requiredBy;
            return this;
        }

        public BloodRequestDtoBuilder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public BloodRequestDtoBuilder contactPreference(ContactPreference contactPreference) {
            this.contactPreference = contactPreference;
            return this;
        }

        public BloodRequestDtoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public BloodRequestDtoBuilder verifiedByUserId(UUID verifiedByUserId) {
            this.verifiedByUserId = verifiedByUserId;
            return this;
        }

        public BloodRequestDtoBuilder verifiedAt(Instant verifiedAt) {
            this.verifiedAt = verifiedAt;
            return this;
        }

        public BloodRequestDtoBuilder closedReason(String closedReason) {
            this.closedReason = closedReason;
            return this;
        }

        public BloodRequestDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BloodRequestDtoBuilder distanceKm(Double distanceKm) {
            this.distanceKm = distanceKm;
            return this;
        }

        public BloodRequestDtoBuilder responseCount(int responseCount) {
            this.responseCount = responseCount;
            return this;
        }

        public BloodRequestDtoBuilder shareWhatsAppUrl(String shareWhatsAppUrl) {
            this.shareWhatsAppUrl = shareWhatsAppUrl;
            return this;
        }

        public BloodRequestDto build() {
            return new BloodRequestDto(this.id, this.requestCode, this.requesterId, this.requesterName, this.hospitalProfileId, this.hospitalName, this.hospitalAddress, this.city, this.state, this.pinCode, this.lat, this.lon, this.patientName, this.bloodGroup, this.bloodGroupLabel, this.units, this.unitsFulfilled, this.priority, this.requiredBy, this.status, this.contactPreference, this.notes, this.verifiedByUserId, this.verifiedAt, this.closedReason, this.createdAt, this.distanceKm, this.responseCount, this.shareWhatsAppUrl);
        }
    }

    public static BloodRequestDtoBuilder builder() {
        return new BloodRequestDtoBuilder();
    }

}
