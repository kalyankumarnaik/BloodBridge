package in.bloodbridge.dto;

import jakarta.validation.constraints.NotBlank;
public class HospitalVerificationSubmitDto {
    @NotBlank(message = "Document type is required")
    private String documentType;

    @NotBlank(message = "Document title or reference is required")
    private String documentName;

    private String notes;


    public HospitalVerificationSubmitDto() {
    }

    public HospitalVerificationSubmitDto(String documentType, String documentName, String notes) {
        this.documentType = documentType;
        this.documentName = documentName;
        this.notes = notes;
    }
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public static class HospitalVerificationSubmitDtoBuilder {
        private String documentType;
        private String documentName;
        private String notes;

        public HospitalVerificationSubmitDtoBuilder() {}

        public HospitalVerificationSubmitDtoBuilder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public HospitalVerificationSubmitDtoBuilder documentName(String documentName) {
            this.documentName = documentName;
            return this;
        }

        public HospitalVerificationSubmitDtoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public HospitalVerificationSubmitDto build() {
            return new HospitalVerificationSubmitDto(this.documentType, this.documentName, this.notes);
        }
    }

    public static HospitalVerificationSubmitDtoBuilder builder() {
        return new HospitalVerificationSubmitDtoBuilder();
    }

}
