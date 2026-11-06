package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonorAvailability;
public class DonorSearchCriteriaDto {
    private BloodGroup bloodGroup;
    private Boolean includeCompatible;
    private String city;
    private String pinCode;
    private Double latitude;
    private Double longitude;
    private Double maxDistanceKm;
    private DonorAvailability availability;
    private Boolean verifiedOnly;
        private int page = 0;
        private int size = 10;
        private String sort = "distance";


    public DonorSearchCriteriaDto() {
    }

    public DonorSearchCriteriaDto(BloodGroup bloodGroup, Boolean includeCompatible, String city, String pinCode, Double latitude, Double longitude, Double maxDistanceKm, DonorAvailability availability, Boolean verifiedOnly, int page, int size, String sort) {
        this.bloodGroup = bloodGroup;
        this.includeCompatible = includeCompatible;
        this.city = city;
        this.pinCode = pinCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxDistanceKm = maxDistanceKm;
        this.availability = availability;
        this.verifiedOnly = verifiedOnly;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    public Boolean isIncludeCompatible() {
        return includeCompatible;
    }
    public Boolean getIncludeCompatible() {
        return includeCompatible;
    }
    public void setIncludeCompatible(Boolean includeCompatible) {
        this.includeCompatible = includeCompatible;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getMaxDistanceKm() {
        return maxDistanceKm;
    }
    public void setMaxDistanceKm(Double maxDistanceKm) {
        this.maxDistanceKm = maxDistanceKm;
    }
    public DonorAvailability getAvailability() {
        return availability;
    }
    public void setAvailability(DonorAvailability availability) {
        this.availability = availability;
    }
    public Boolean isVerifiedOnly() {
        return verifiedOnly;
    }
    public Boolean getVerifiedOnly() {
        return verifiedOnly;
    }
    public void setVerifiedOnly(Boolean verifiedOnly) {
        this.verifiedOnly = verifiedOnly;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public static class DonorSearchCriteriaDtoBuilder {
        private BloodGroup bloodGroup;
        private Boolean includeCompatible;
        private String city;
        private String pinCode;
        private Double latitude;
        private Double longitude;
        private Double maxDistanceKm;
        private DonorAvailability availability;
        private Boolean verifiedOnly;
        private int page = 0;
        private int size = 10;
        private String sort = "distance";

        public DonorSearchCriteriaDtoBuilder() {}

        public DonorSearchCriteriaDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder includeCompatible(Boolean includeCompatible) {
            this.includeCompatible = includeCompatible;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder pinCode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder maxDistanceKm(Double maxDistanceKm) {
            this.maxDistanceKm = maxDistanceKm;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder availability(DonorAvailability availability) {
            this.availability = availability;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder verifiedOnly(Boolean verifiedOnly) {
            this.verifiedOnly = verifiedOnly;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder page(int page) {
            this.page = page;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder size(int size) {
            this.size = size;
            return this;
        }

        public DonorSearchCriteriaDtoBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }

        public DonorSearchCriteriaDto build() {
            return new DonorSearchCriteriaDto(this.bloodGroup, this.includeCompatible, this.city, this.pinCode, this.latitude, this.longitude, this.maxDistanceKm, this.availability, this.verifiedOnly, this.page, this.size, this.sort);
        }
    }

    public static DonorSearchCriteriaDtoBuilder builder() {
        return new DonorSearchCriteriaDtoBuilder();
    }

}
