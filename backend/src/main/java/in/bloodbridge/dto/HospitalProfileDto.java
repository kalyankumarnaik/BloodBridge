package in.bloodbridge.dto;

import in.bloodbridge.enums.VerificationStatus;
import java.time.Instant;
import java.util.UUID;

public class HospitalProfileDto {
    private UUID id;
    private UUID userId;
    private String name;
    private String registrationNumber;
    private String type;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private Double lat;
    private Double lon;
    private String officialPhone;
    private String contactPerson;
    private VerificationStatus verificationStatus;
    private Instant createdAt;


    public HospitalProfileDto() {
    }

    public HospitalProfileDto(UUID id, UUID userId, String name, String registrationNumber, String type, String address, String city, String state, String pinCode, Double lat, Double lon, String officialPhone, String contactPerson, VerificationStatus verificationStatus, Instant createdAt) {
        this.id = id;
        this.userId = userId;
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
    public static class HospitalProfileDtoBuilder {
        private UUID id;
        private UUID userId;
        private String name;
        private String registrationNumber;
        private String type;
        private String address;
        private String city;
        private String state;
        private String pinCode;
        private Double lat;
        private Double lon;
        private String officialPhone;
        private String contactPerson;
        private VerificationStatus verificationStatus;
        private Instant createdAt;

        public HospitalProfileDtoBuilder() {}

        public HospitalProfileDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HospitalProfileDtoBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public HospitalProfileDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HospitalProfileDtoBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public HospitalProfileDtoBuilder type(String type) {
            this.type = type;
            return this;
        }

        public HospitalProfileDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public HospitalProfileDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public HospitalProfileDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public HospitalProfileDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public HospitalProfileDtoBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public HospitalProfileDtoBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public HospitalProfileDtoBuilder officialPhone(String officialPhone) {
            this.officialPhone = officialPhone;
            return this;
        }

        public HospitalProfileDtoBuilder contactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public HospitalProfileDtoBuilder verificationStatus(VerificationStatus verificationStatus) {
            this.verificationStatus = verificationStatus;
            return this;
        }

        public HospitalProfileDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HospitalProfileDto build() {
            return new HospitalProfileDto(this.id, this.userId, this.name, this.registrationNumber, this.type, this.address, this.city, this.state, this.pinCode, this.lat, this.lon, this.officialPhone, this.contactPerson, this.verificationStatus, this.createdAt);
        }
    }

    public static HospitalProfileDtoBuilder builder() {
        return new HospitalProfileDtoBuilder();
    }

}
