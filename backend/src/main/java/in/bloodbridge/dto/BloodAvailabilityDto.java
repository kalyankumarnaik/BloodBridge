package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodAvailabilityStatus;
import in.bloodbridge.enums.BloodGroup;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class BloodAvailabilityDto {
    private UUID id;
    private UUID hospitalProfileId;
    private String hospitalName;
    private String city;
    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;
    private String bloodGroupLabel;
    @NotNull(message = "Status is required")
    private BloodAvailabilityStatus status;
    private Integer unitsMin;
    private Integer unitsMax;
    private Instant updatedAt;
    private boolean isStale; // older than 24h


    public BloodAvailabilityDto() {
    }

    public BloodAvailabilityDto(UUID id, UUID hospitalProfileId, String hospitalName, String city, BloodGroup bloodGroup, String bloodGroupLabel, BloodAvailabilityStatus status, Integer unitsMin, Integer unitsMax, Instant updatedAt, boolean isStale) {
        this.id = id;
        this.hospitalProfileId = hospitalProfileId;
        this.hospitalName = hospitalName;
        this.city = city;
        this.bloodGroup = bloodGroup;
        this.bloodGroupLabel = bloodGroupLabel;
        this.status = status;
        this.unitsMin = unitsMin;
        this.unitsMax = unitsMax;
        this.updatedAt = updatedAt;
        this.isStale = isStale;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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
    public BloodAvailabilityStatus getStatus() {
        return status;
    }
    public void setStatus(BloodAvailabilityStatus status) {
        this.status = status;
    }
    public Integer getUnitsMin() {
        return unitsMin;
    }
    public void setUnitsMin(Integer unitsMin) {
        this.unitsMin = unitsMin;
    }
    public Integer getUnitsMax() {
        return unitsMax;
    }
    public void setUnitsMax(Integer unitsMax) {
        this.unitsMax = unitsMax;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public boolean isIsStale() {
        return isStale;
    }
    public boolean getIsStale() {
        return isStale;
    }
    public void setIsStale(boolean isStale) {
        this.isStale = isStale;
    }
    public static class BloodAvailabilityDtoBuilder {
        private UUID id;
        private UUID hospitalProfileId;
        private String hospitalName;
        private String city;
        private BloodGroup bloodGroup;
        private String bloodGroupLabel;
        private BloodAvailabilityStatus status;
        private Integer unitsMin;
        private Integer unitsMax;
        private Instant updatedAt;
        private boolean isStale;

        public BloodAvailabilityDtoBuilder() {}

        public BloodAvailabilityDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BloodAvailabilityDtoBuilder hospitalProfileId(UUID hospitalProfileId) {
            this.hospitalProfileId = hospitalProfileId;
            return this;
        }

        public BloodAvailabilityDtoBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public BloodAvailabilityDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public BloodAvailabilityDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public BloodAvailabilityDtoBuilder bloodGroupLabel(String bloodGroupLabel) {
            this.bloodGroupLabel = bloodGroupLabel;
            return this;
        }

        public BloodAvailabilityDtoBuilder status(BloodAvailabilityStatus status) {
            this.status = status;
            return this;
        }

        public BloodAvailabilityDtoBuilder unitsMin(Integer unitsMin) {
            this.unitsMin = unitsMin;
            return this;
        }

        public BloodAvailabilityDtoBuilder unitsMax(Integer unitsMax) {
            this.unitsMax = unitsMax;
            return this;
        }

        public BloodAvailabilityDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BloodAvailabilityDtoBuilder isStale(boolean isStale) {
            this.isStale = isStale;
            return this;
        }

        public BloodAvailabilityDto build() {
            return new BloodAvailabilityDto(this.id, this.hospitalProfileId, this.hospitalName, this.city, this.bloodGroup, this.bloodGroupLabel, this.status, this.unitsMin, this.unitsMax, this.updatedAt, this.isStale);
        }
    }

    public static BloodAvailabilityDtoBuilder builder() {
        return new BloodAvailabilityDtoBuilder();
    }

}
