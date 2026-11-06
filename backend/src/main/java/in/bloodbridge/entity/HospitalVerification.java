package in.bloodbridge.entity;

import in.bloodbridge.enums.VerificationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "hospital_verifications")
public class HospitalVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_profile_id", nullable = false)
    private HospitalProfile hospitalProfile;

    @Column(name = "document_type", nullable = false, length = 100)
    private String documentType;

    @Column(name = "storage_key", nullable = false, length = 255)
    private String storageKey;

    @Column(name = "original_filename", nullable = false, length = 200)
    private String originalFilename;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
        private VerificationStatus status = VerificationStatus.SUBMITTED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Column(name = "review_note", columnDefinition = "TEXT")
    private String reviewNote;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public HospitalVerification() {
    }

    public HospitalVerification(UUID id, HospitalProfile hospitalProfile, String documentType, String storageKey, String originalFilename, String contentType, long sizeBytes, VerificationStatus status, User reviewer, String reviewNote, Instant reviewedAt, Instant createdAt) {
        this.id = id;
        this.hospitalProfile = hospitalProfile;
        this.documentType = documentType;
        this.storageKey = storageKey;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.status = status;
        this.reviewer = reviewer;
        this.reviewNote = reviewNote;
        this.reviewedAt = reviewedAt;
        this.createdAt = createdAt;
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
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public String getStorageKey() {
        return storageKey;
    }
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }
    public String getOriginalFilename() {
        return originalFilename;
    }
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public long getSizeBytes() {
        return sizeBytes;
    }
    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }
    public VerificationStatus getStatus() {
        return status;
    }
    public void setStatus(VerificationStatus status) {
        this.status = status;
    }
    public User getReviewer() {
        return reviewer;
    }
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }
    public String getReviewNote() {
        return reviewNote;
    }
    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }
    public Instant getReviewedAt() {
        return reviewedAt;
    }
    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public static class HospitalVerificationBuilder {
        private UUID id;
        private HospitalProfile hospitalProfile;
        private String documentType;
        private String storageKey;
        private String originalFilename;
        private String contentType;
        private long sizeBytes;
        private VerificationStatus status = VerificationStatus.SUBMITTED;
        private User reviewer;
        private String reviewNote;
        private Instant reviewedAt;
        private Instant createdAt;

        public HospitalVerificationBuilder() {}

        public HospitalVerificationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HospitalVerificationBuilder hospitalProfile(HospitalProfile hospitalProfile) {
            this.hospitalProfile = hospitalProfile;
            return this;
        }

        public HospitalVerificationBuilder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public HospitalVerificationBuilder storageKey(String storageKey) {
            this.storageKey = storageKey;
            return this;
        }

        public HospitalVerificationBuilder originalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public HospitalVerificationBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public HospitalVerificationBuilder sizeBytes(long sizeBytes) {
            this.sizeBytes = sizeBytes;
            return this;
        }

        public HospitalVerificationBuilder status(VerificationStatus status) {
            this.status = status;
            return this;
        }

        public HospitalVerificationBuilder reviewer(User reviewer) {
            this.reviewer = reviewer;
            return this;
        }

        public HospitalVerificationBuilder reviewNote(String reviewNote) {
            this.reviewNote = reviewNote;
            return this;
        }

        public HospitalVerificationBuilder reviewedAt(Instant reviewedAt) {
            this.reviewedAt = reviewedAt;
            return this;
        }

        public HospitalVerificationBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HospitalVerification build() {
            return new HospitalVerification(this.id, this.hospitalProfile, this.documentType, this.storageKey, this.originalFilename, this.contentType, this.sizeBytes, this.status, this.reviewer, this.reviewNote, this.reviewedAt, this.createdAt);
        }
    }

    public static HospitalVerificationBuilder builder() {
        return new HospitalVerificationBuilder();
    }

}
