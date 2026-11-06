package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.ContactPreference;
import in.bloodbridge.enums.RequestPriority;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

public class EmergencyRequestCreateDto {

    @NotBlank(message = "Patient name is required")
    @Size(max = 150, message = "Patient name cannot exceed 150 characters")
    private String patientName;

    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;

    @Min(value = 1, message = "At least 1 unit must be requested")
    @Max(value = 10, message = "A maximum of 10 units can be requested per single request")
        private int units = 1;

    private UUID hospitalProfileId;

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotBlank(message = "Hospital address is required")
    private String hospitalAddress;

    @NotBlank(message = "City is required")
    private String city;

    private String state;

    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "PIN code must be a valid 6-digit Indian postal code")
    private String pinCode;

    private Double lat;
    private Double lon;

    @NotNull(message = "Required-by date and time is required")
    private Instant requiredBy;

    @NotNull(message = "Priority is required")
        private RequestPriority priority = RequestPriority.URGENT;

    @NotNull(message = "Contact preference is required")
        private ContactPreference contactPreference = ContactPreference.PHONE;

    @Size(max = 500, message = "Additional notes cannot exceed 500 characters")
    private String notes;

    @AssertTrue(message = "You must confirm that all details provided are genuine and medical needs are authentic")
        private boolean genuineConsent = true;


    public EmergencyRequestCreateDto() {
    }

    public EmergencyRequestCreateDto(String patientName, BloodGroup bloodGroup, int units, UUID hospitalProfileId, String hospitalName, String hospitalAddress, String city, String state, String pinCode, Double lat, Double lon, Instant requiredBy, RequestPriority priority, ContactPreference contactPreference, String notes, boolean genuineConsent) {
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.hospitalProfileId = hospitalProfileId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.lat = lat;
        this.lon = lon;
        this.requiredBy = requiredBy;
        this.priority = priority;
        this.contactPreference = contactPreference;
        this.notes = notes;
        this.genuineConsent = genuineConsent;
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
    public boolean isGenuineConsent() {
        return genuineConsent;
    }
    public boolean getGenuineConsent() {
        return genuineConsent;
    }
    public void setGenuineConsent(boolean genuineConsent) {
        this.genuineConsent = genuineConsent;
    }
    public static class EmergencyRequestCreateDtoBuilder {
        private String patientName;
        private BloodGroup bloodGroup;
        private int units = 1;
        private UUID hospitalProfileId;
        private String hospitalName;
        private String hospitalAddress;
        private String city;
        private String state;
        private String pinCode;
        private Double lat;
        private Double lon;
        private Instant requiredBy;
        private RequestPriority priority = RequestPriority.URGENT;
        private ContactPreference contactPreference = ContactPreference.PHONE;
        private String notes;
        private boolean genuineConsent = true;

        public EmergencyRequestCreateDtoBuilder() {}

        public EmergencyRequestCreateDtoBuilder patientName(String patientName) {
            this.patientName = patientName;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder units(int units) {
            this.units = units;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder hospitalProfileId(UUID hospitalProfileId) {
            this.hospitalProfileId = hospitalProfileId;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder hospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder requiredBy(Instant requiredBy) {
            this.requiredBy = requiredBy;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder priority(RequestPriority priority) {
            this.priority = priority;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder contactPreference(ContactPreference contactPreference) {
            this.contactPreference = contactPreference;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public EmergencyRequestCreateDtoBuilder genuineConsent(boolean genuineConsent) {
            this.genuineConsent = genuineConsent;
            return this;
        }

        public EmergencyRequestCreateDto build() {
            return new EmergencyRequestCreateDto(this.patientName, this.bloodGroup, this.units, this.hospitalProfileId, this.hospitalName, this.hospitalAddress, this.city, this.state, this.pinCode, this.lat, this.lon, this.requiredBy, this.priority, this.contactPreference, this.notes, this.genuineConsent);
        }
    }

    public static EmergencyRequestCreateDtoBuilder builder() {
        return new EmergencyRequestCreateDtoBuilder();
    }

}
