package in.bloodbridge.dto;

import java.util.List;
import java.util.Map;

public class AdminAnalyticsDto {
    private long totalDonors;
    private long activeDonors;
    private long totalRequests;
    private long activeRequests;
    private long fulfilledRequests;
    private long pendingHospitalVerifications;
    private long registeredHospitals;
    private long openReports;
    private Map<String, Long> bloodGroupDistribution;
    private List<MonthlyTrendDto> monthlyTrends;

    public AdminAnalyticsDto() {}

    public AdminAnalyticsDto(long totalDonors, long activeDonors, long totalRequests, long activeRequests,
                             long fulfilledRequests, long pendingHospitalVerifications, long registeredHospitals,
                             long openReports, Map<String, Long> bloodGroupDistribution, List<MonthlyTrendDto> monthlyTrends) {
        this.totalDonors = totalDonors;
        this.activeDonors = activeDonors;
        this.totalRequests = totalRequests;
        this.activeRequests = activeRequests;
        this.fulfilledRequests = fulfilledRequests;
        this.pendingHospitalVerifications = pendingHospitalVerifications;
        this.registeredHospitals = registeredHospitals;
        this.openReports = openReports;
        this.bloodGroupDistribution = bloodGroupDistribution;
        this.monthlyTrends = monthlyTrends;
    }

    public static class MonthlyTrendDto {
        private String month;
        private long count;

        public MonthlyTrendDto() {}

        public MonthlyTrendDto(String month, long count) {
            this.month = month;
            this.count = count;
        }

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }

    public static class AdminAnalyticsDtoBuilder {
        private long totalDonors;
        private long activeDonors;
        private long totalRequests;
        private long activeRequests;
        private long fulfilledRequests;
        private long pendingHospitalVerifications;
        private long registeredHospitals;
        private long openReports;
        private Map<String, Long> bloodGroupDistribution;
        private List<MonthlyTrendDto> monthlyTrends;

        public AdminAnalyticsDtoBuilder totalDonors(long totalDonors) { this.totalDonors = totalDonors; return this; }
        public AdminAnalyticsDtoBuilder activeDonors(long activeDonors) { this.activeDonors = activeDonors; return this; }
        public AdminAnalyticsDtoBuilder totalRequests(long totalRequests) { this.totalRequests = totalRequests; return this; }
        public AdminAnalyticsDtoBuilder activeRequests(long activeRequests) { this.activeRequests = activeRequests; return this; }
        public AdminAnalyticsDtoBuilder fulfilledRequests(long fulfilledRequests) { this.fulfilledRequests = fulfilledRequests; return this; }
        public AdminAnalyticsDtoBuilder pendingHospitalVerifications(long pendingHospitalVerifications) { this.pendingHospitalVerifications = pendingHospitalVerifications; return this; }
        public AdminAnalyticsDtoBuilder registeredHospitals(long registeredHospitals) { this.registeredHospitals = registeredHospitals; return this; }
        public AdminAnalyticsDtoBuilder openReports(long openReports) { this.openReports = openReports; return this; }
        public AdminAnalyticsDtoBuilder bloodGroupDistribution(Map<String, Long> bloodGroupDistribution) { this.bloodGroupDistribution = bloodGroupDistribution; return this; }
        public AdminAnalyticsDtoBuilder monthlyTrends(List<MonthlyTrendDto> monthlyTrends) { this.monthlyTrends = monthlyTrends; return this; }

        public AdminAnalyticsDto build() {
            return new AdminAnalyticsDto(totalDonors, activeDonors, totalRequests, activeRequests, fulfilledRequests,
                    pendingHospitalVerifications, registeredHospitals, openReports, bloodGroupDistribution, monthlyTrends);
        }
    }

    public static AdminAnalyticsDtoBuilder builder() {
        return new AdminAnalyticsDtoBuilder();
    }

    public long getTotalDonors() { return totalDonors; }
    public void setTotalDonors(long totalDonors) { this.totalDonors = totalDonors; }
    public long getActiveDonors() { return activeDonors; }
    public void setActiveDonors(long activeDonors) { this.activeDonors = activeDonors; }
    public long getTotalRequests() { return totalRequests; }
    public void setTotalRequests(long totalRequests) { this.totalRequests = totalRequests; }
    public long getActiveRequests() { return activeRequests; }
    public void setActiveRequests(long activeRequests) { this.activeRequests = activeRequests; }
    public long getFulfilledRequests() { return fulfilledRequests; }
    public void setFulfilledRequests(long fulfilledRequests) { this.fulfilledRequests = fulfilledRequests; }
    public long getPendingHospitalVerifications() { return pendingHospitalVerifications; }
    public void setPendingHospitalVerifications(long pendingHospitalVerifications) { this.pendingHospitalVerifications = pendingHospitalVerifications; }
    public long getRegisteredHospitals() { return registeredHospitals; }
    public void setRegisteredHospitals(long registeredHospitals) { this.registeredHospitals = registeredHospitals; }
    public long getOpenReports() { return openReports; }
    public void setOpenReports(long openReports) { this.openReports = openReports; }
    public Map<String, Long> getBloodGroupDistribution() { return bloodGroupDistribution; }
    public void setBloodGroupDistribution(Map<String, Long> bloodGroupDistribution) { this.bloodGroupDistribution = bloodGroupDistribution; }
    public List<MonthlyTrendDto> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(List<MonthlyTrendDto> monthlyTrends) { this.monthlyTrends = monthlyTrends; }
}
