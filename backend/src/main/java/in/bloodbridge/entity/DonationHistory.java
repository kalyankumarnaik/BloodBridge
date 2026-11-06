package in.bloodbridge.entity;

import in.bloodbridge.enums.BloodGroup;
import in.bloodbridge.enums.DonationSource;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "donation_history")
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_user_id", nullable = false)
    private User donorUser;

    @Column(name = "donated_on", nullable = false)
    private LocalDate donatedOn;

    @Column(name = "hospital_name", nullable = false, length = 200)
    private String hospitalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private BloodRequest request;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private DonationSource source = DonationSource.SELF_REPORTED;

    @Column(name = "confirmed_by", length = 150)
    private String confirmedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 10)
    private BloodGroup bloodGroup;

    @Column(name = "units_donated", nullable = false)
        private int unitsDonated = 1;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public DonationHistory() {
    }

    public DonationHistory(UUID id, User donorUser, LocalDate donatedOn, String hospitalName, BloodRequest request, DonationSource source, String confirmedBy, BloodGroup bloodGroup, int unitsDonated, Instant createdAt) {
        this.id = id;
        this.donorUser = donorUser;
        this.donatedOn = donatedOn;
        this.hospitalName = hospitalName;
        this.request = request;
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
    public User getDonorUser() {
        return donorUser;
    }
    public void setDonorUser(User donorUser) {
        this.donorUser = donorUser;
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
    public BloodRequest getRequest() {
        return request;
    }
    public void setRequest(BloodRequest request) {
        this.request = request;
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
    public static class DonationHistoryBuilder {
        private UUID id;
        private User donorUser;
        private LocalDate donatedOn;
        private String hospitalName;
        private BloodRequest request;
        private DonationSource source = DonationSource.SELF_REPORTED;
        private String confirmedBy;
        private BloodGroup bloodGroup;
        private int unitsDonated = 1;
        private Instant createdAt;

        public DonationHistoryBuilder() {}

        public DonationHistoryBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonationHistoryBuilder donorUser(User donorUser) {
            this.donorUser = donorUser;
            return this;
        }

        public DonationHistoryBuilder donatedOn(LocalDate donatedOn) {
            this.donatedOn = donatedOn;
            return this;
        }

        public DonationHistoryBuilder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return this;
        }

        public DonationHistoryBuilder request(BloodRequest request) {
            this.request = request;
            return this;
        }

        public DonationHistoryBuilder source(DonationSource source) {
            this.source = source;
            return this;
        }

        public DonationHistoryBuilder confirmedBy(String confirmedBy) {
            this.confirmedBy = confirmedBy;
            return this;
        }

        public DonationHistoryBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public DonationHistoryBuilder unitsDonated(int unitsDonated) {
            this.unitsDonated = unitsDonated;
            return this;
        }

        public DonationHistoryBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DonationHistory build() {
            return new DonationHistory(this.id, this.donorUser, this.donatedOn, this.hospitalName, this.request, this.source, this.confirmedBy, this.bloodGroup, this.unitsDonated, this.createdAt);
        }
    }

    public static DonationHistoryBuilder builder() {
        return new DonationHistoryBuilder();
    }

}
