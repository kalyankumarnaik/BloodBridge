package in.bloodbridge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "patient_profiles")
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(name = "relationship_default", length = 50)
    private String relationshipDefault;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public PatientProfile() {
    }

    public PatientProfile(UUID id, User user, String fullName, String city, String state, String relationshipDefault, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.city = city;
        this.state = state;
        this.relationshipDefault = relationshipDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public String getRelationshipDefault() {
        return relationshipDefault;
    }
    public void setRelationshipDefault(String relationshipDefault) {
        this.relationshipDefault = relationshipDefault;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static class PatientProfileBuilder {
        private UUID id;
        private User user;
        private String fullName;
        private String city;
        private String state;
        private String relationshipDefault;
        private Instant createdAt;
        private Instant updatedAt;

        public PatientProfileBuilder() {}

        public PatientProfileBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PatientProfileBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PatientProfileBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public PatientProfileBuilder city(String city) {
            this.city = city;
            return this;
        }

        public PatientProfileBuilder state(String state) {
            this.state = state;
            return this;
        }

        public PatientProfileBuilder relationshipDefault(String relationshipDefault) {
            this.relationshipDefault = relationshipDefault;
            return this;
        }

        public PatientProfileBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PatientProfileBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PatientProfile build() {
            return new PatientProfile(this.id, this.user, this.fullName, this.city, this.state, this.relationshipDefault, this.createdAt, this.updatedAt);
        }
    }

    public static PatientProfileBuilder builder() {
        return new PatientProfileBuilder();
    }

}
