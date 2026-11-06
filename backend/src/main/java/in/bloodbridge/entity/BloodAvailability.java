package in.bloodbridge.entity;

import in.bloodbridge.enums.BloodAvailabilityStatus;
import in.bloodbridge.enums.BloodGroup;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "blood_availability", uniqueConstraints = {
        @UniqueConstraint(name = "uq_hospital_blood_group", columnNames = {"hospital_profile_id", "blood_group"})
})
public class BloodAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_profile_id", nullable = false)
    private HospitalProfile hospitalProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 10)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private BloodAvailabilityStatus status = BloodAvailabilityStatus.AVAILABLE;

    @Column(name = "units_min")
    private Integer unitsMin;

    @Column(name = "units_max")
    private Integer unitsMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public BloodAvailability() {
    }

    public BloodAvailability(UUID id, HospitalProfile hospitalProfile, BloodGroup bloodGroup, BloodAvailabilityStatus status, Integer unitsMin, Integer unitsMax, User updatedBy, Instant updatedAt) {
        this.id = id;
        this.hospitalProfile = hospitalProfile;
        this.bloodGroup = bloodGroup;
        this.status = status;
        this.unitsMin = unitsMin;
        this.unitsMax = unitsMax;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public HospitalProfile getHospitalProfile() {
        return hospitalProfile;
    }
    public void setHospitalProfile(HospitalProfile hospitalProfile) {
        this.hospitalProfile = hospitalProfile;
    }
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
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
    public User getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static class BloodAvailabilityBuilder {
        private UUID id;
        private HospitalProfile hospitalProfile;
        private BloodGroup bloodGroup;
        private BloodAvailabilityStatus status = BloodAvailabilityStatus.AVAILABLE;
        private Integer unitsMin;
        private Integer unitsMax;
        private User updatedBy;
        private Instant updatedAt;

        public BloodAvailabilityBuilder() {}

        public BloodAvailabilityBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BloodAvailabilityBuilder hospitalProfile(HospitalProfile hospitalProfile) {
            this.hospitalProfile = hospitalProfile;
            return this;
        }

        public BloodAvailabilityBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public BloodAvailabilityBuilder status(BloodAvailabilityStatus status) {
            this.status = status;
            return this;
        }

        public BloodAvailabilityBuilder unitsMin(Integer unitsMin) {
            this.unitsMin = unitsMin;
            return this;
        }

        public BloodAvailabilityBuilder unitsMax(Integer unitsMax) {
            this.unitsMax = unitsMax;
            return this;
        }

        public BloodAvailabilityBuilder updatedBy(User updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public BloodAvailabilityBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BloodAvailability build() {
            return new BloodAvailability(this.id, this.hospitalProfile, this.bloodGroup, this.status, this.unitsMin, this.unitsMax, this.updatedBy, this.updatedAt);
        }
    }

    public static BloodAvailabilityBuilder builder() {
        return new BloodAvailabilityBuilder();
    }

}
