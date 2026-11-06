package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonationSource;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class DonationRecordDto {
    private UUID id;
    private UUID donorUserId;
    @NotNull(message = "Donation date is required")
    private LocalDate donatedOn;
    @NotNull(message = "Hospital name is required")
    private String hospitalName;
    private UUID requestId;
    private DonationSource source;
    private String confirmedBy;
    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;
        private int unitsDonated = 1;
    private Instant createdAt;


    public DonationRecordDto() {
    }

    public DonationRecordDto(UUID id, UUID donorUserId, LocalDate donatedOn, String hospitalName, UUID requestId, DonationSource source, String confirmedBy, BloodGroup bloodGroup, int unitsDonated, Instant createdAt) {
        this.id = id;
        this.donorUserId = donorUserId;
        this.donatedOn = donatedOn;
        this.hospitalName = hospitalName;
        this.requestId = requestId;
        this.source = source;
        this.confirmedBy = confirmedBy;
        this.bloodGroup = bloodGroup;
        this.unitsDonated = unitsDonated;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getDonorUserId() {
        return donorUserId;
    }
    public void setDonorUserId(UUID donorUserId) {
        this.donorUserId = donorUserId;
    }
    public LocalDate getDonatedOn() {
        return donatedOn;
    }
    public void setDonatedOn(LocalDate donatedOn) {
        this.donatedOn = donatedOn;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public UUID getRequestId() {
        return requestId;
    }
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
    public DonationSource getSource() {
        return source;
    }
    public void setSource(DonationSource source) {
        this.source = source;
    }
    public String getConfirmedBy() {
        return confirmedBy;
    }
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public int getUnitsDonated() {
        return unitsDonated;
    }
    public void setUnitsDonated(int unitsDonated) {
        this.unitsDonated = unitsDonated;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class DonationRecordDtoBuilder {
        private UUID id;
        private UUID donorUserId;
        private LocalDate donatedOn;
        private String hospitalName;
        private UUID requestId;
        private DonationSource source;
        private String confirmedBy;
        private BloodGroup bloodGroup;
        private int unitsDonated = 1;
        private Instant createdAt;

        public DonationRecordDtoBuilder() {}

        public DonationRecordDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonationRecordDtoBuilder donorUserId(UUID donorUserId) {
            this.donorUserId = donorUserId;
            return this;
        }

        public DonationRecordDtoBuilder donatedOn(LocalDate donatedOn) {
            this.donatedOn = donatedOn;
            return this;
        }

        public DonationRecordDtoBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public DonationRecordDtoBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public DonationRecordDtoBuilder source(DonationSource source) {
            this.source = source;
            return this;
        }

        public DonationRecordDtoBuilder confirmedBy(String confirmedBy) {
            this.confirmedBy = confirmedBy;
            return this;
        }

        public DonationRecordDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonationRecordDtoBuilder unitsDonated(int unitsDonated) {
            this.unitsDonated = unitsDonated;
            return this;
        }

        public DonationRecordDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonationRecordDto build() {
            return new DonationRecordDto(this.id, this.donorUserId, this.donatedOn, this.hospitalName, this.requestId, this.source, this.confirmedBy, this.bloodGroup, this.unitsDonated, this.createdAt);
        }
    }

    public static DonationRecordDtoBuilder builder() {
        return new DonationRecordDtoBuilder();
    }

}
