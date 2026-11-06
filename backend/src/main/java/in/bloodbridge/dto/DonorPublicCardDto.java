package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
import java.util.UUID;

public class DonorPublicCardDto {
    private UUID donorRef; // opaque UUID reference
    private String maskedName; // e.g. "Rahul S."
    private BloodGroup bloodGroup;
    private String bloodGroupLabel;
    private String city;
    private String state;
    private String pinCodePrefix; // e.g. "110***"
    private String distanceBand; // e.g. "2–5 km", "< 5 km"
    private Double exactDistanceKm; // for sorting and proximity indicator
    private DonorAvailability availability;
    private boolean verified;
    private String lastActiveBucket; // e.g. "Active today", "Active this week"
    private int totalDonations;
    private String informationalReminder; // courtesy filter/reminder notice


    public DonorPublicCardDto() {
    }

    public DonorPublicCardDto(UUID donorRef, String maskedName, BloodGroup bloodGroup, String bloodGroupLabel, String city, String state, String pinCodePrefix, String distanceBand, Double exactDistanceKm, DonorAvailability availability, boolean verified, String lastActiveBucket, int totalDonations, String informationalReminder) {
        this.donorRef = donorRef;
        this.maskedName = maskedName;
        this.bloodGroup = bloodGroup;
        this.bloodGroupLabel = bloodGroupLabel;
        this.city = city;
        this.state = state;
        this.pinCodePrefix = pinCodePrefix;
        this.distanceBand = distanceBand;
        this.exactDistanceKm = exactDistanceKm;
        this.availability = availability;
        this.verified = verified;
        this.lastActiveBucket = lastActiveBucket;
        this.totalDonations = totalDonations;
        this.informationalReminder = informationalReminder;
    }
    public UUID getDonorRef() {
        return donorRef;
    }
    public void setDonorRef(UUID donorRef) {
        this.donorRef = donorRef;
    }
    public String getMaskedName() {
        return maskedName;
    }
    public void setMaskedName(String maskedName) {
        this.maskedName = maskedName;
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
    public String getPinCodePrefix() {
        return pinCodePrefix;
    }
    public void setPinCodePrefix(String pinCodePrefix) {
        this.pinCodePrefix = pinCodePrefix;
    }
    public String getDistanceBand() {
        return distanceBand;
    }
    public void setDistanceBand(String distanceBand) {
        this.distanceBand = distanceBand;
    }
    public Double getExactDistanceKm() {
        return exactDistanceKm;
    }
    public void setExactDistanceKm(Double exactDistanceKm) {
        this.exactDistanceKm = exactDistanceKm;
    }
    public DonorAvailability getAvailability() {
        return availability;
    }
    public void setAvailability(DonorAvailability availability) {
        this.availability = availability;
    }
    public boolean isVerified() {
        return verified;
    }
    public boolean getVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    public String getLastActiveBucket() {
        return lastActiveBucket;
    }
    public void setLastActiveBucket(String lastActiveBucket) {
        this.lastActiveBucket = lastActiveBucket;
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
    public static class DonorPublicCardDtoBuilder {
        private UUID donorRef;
        private String maskedName;
        private BloodGroup bloodGroup;
        private String bloodGroupLabel;
        private String city;
        private String state;
        private String pinCodePrefix;
        private String distanceBand;
        private Double exactDistanceKm;
        private DonorAvailability availability;
        private boolean verified;
        private String lastActiveBucket;
        private int totalDonations;
        private String informationalReminder;

        public DonorPublicCardDtoBuilder() {}

        public DonorPublicCardDtoBuilder donorRef(UUID donorRef) {
            this.donorRef = donorRef;
            return this;
        }

        public DonorPublicCardDtoBuilder maskedName(String maskedName) {
            this.maskedName = maskedName;
            return this;
        }

        public DonorPublicCardDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonorPublicCardDtoBuilder bloodGroupLabel(String bloodGroupLabel) {
            this.bloodGroupLabel = bloodGroupLabel;
            return this;
        }

        public DonorPublicCardDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public DonorPublicCardDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public DonorPublicCardDtoBuilder pinCodePrefix(String pinCodePrefix) {
            this.pinCodePrefix = pinCodePrefix;
            return this;
        }

        public DonorPublicCardDtoBuilder distanceBand(String distanceBand) {
            this.distanceBand = distanceBand;
            return this;
        }

        public DonorPublicCardDtoBuilder exactDistanceKm(Double exactDistanceKm) {
            this.exactDistanceKm = exactDistanceKm;
            return this;
        }

        public DonorPublicCardDtoBuilder availability(DonorAvailability availability) {
            this.availability = availability;
            return this;
        }

        public DonorPublicCardDtoBuilder verified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public DonorPublicCardDtoBuilder lastActiveBucket(String lastActiveBucket) {
            this.lastActiveBucket = lastActiveBucket;
            return this;
        }

        public DonorPublicCardDtoBuilder totalDonations(int totalDonations) {
            this.totalDonations = totalDonations;
            return this;
        }

        public DonorPublicCardDtoBuilder informationalReminder(String informationalReminder) {
            this.informationalReminder = informationalReminder;
            return this;
        }

        public DonorPublicCardDto build() {
            return new DonorPublicCardDto(this.donorRef, this.maskedName, this.bloodGroup, this.bloodGroupLabel, this.city, this.state, this.pinCodePrefix, this.distanceBand, this.exactDistanceKm, this.availability, this.verified, this.lastActiveBucket, this.totalDonations, this.informationalReminder);
        }
    }

    public static DonorPublicCardDtoBuilder builder() {
        return new DonorPublicCardDtoBuilder();
    }

}
