package in.bloodbridge.dto;

import in.bloodbridge.enums.BloodGroup;
import java.util.List;

public class CompatibilityResultDto {
    private BloodGroup bloodGroup;
    private String bloodGroupLabel;
    private List<BloodGroup> compatibleDonorGroups;
    private List<BloodGroup> compatibleRecipientGroups;
    private String explanation;
    private String disclaimer;


    public CompatibilityResultDto() {
    }

    public CompatibilityResultDto(BloodGroup bloodGroup, String bloodGroupLabel, List<BloodGroup> compatibleDonorGroups, List<BloodGroup> compatibleRecipientGroups, String explanation, String disclaimer) {
        this.bloodGroup = bloodGroup;
        this.bloodGroupLabel = bloodGroupLabel;
        this.compatibleDonorGroups = compatibleDonorGroups;
        this.compatibleRecipientGroups = compatibleRecipientGroups;
        this.explanation = explanation;
        this.disclaimer = disclaimer;
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
    public List<BloodGroup> getCompatibleDonorGroups() {
        return compatibleDonorGroups;
    }
    public void setCompatibleDonorGroups(List<BloodGroup> compatibleDonorGroups) {
        this.compatibleDonorGroups = compatibleDonorGroups;
    }
    public List<BloodGroup> getCompatibleRecipientGroups() {
        return compatibleRecipientGroups;
    }
    public void setCompatibleRecipientGroups(List<BloodGroup> compatibleRecipientGroups) {
        this.compatibleRecipientGroups = compatibleRecipientGroups;
    }
    public String getExplanation() {
        return explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public String getDisclaimer() {
        return disclaimer;
    }
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
    public static class CompatibilityResultDtoBuilder {
        private BloodGroup bloodGroup;
        private String bloodGroupLabel;
        private List<BloodGroup> compatibleDonorGroups;
        private List<BloodGroup> compatibleRecipientGroups;
        private String explanation;
        private String disclaimer;

        public CompatibilityResultDtoBuilder() {}

        public CompatibilityResultDtoBuilder bloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
            return this;
        }

        public CompatibilityResultDtoBuilder bloodGroupLabel(String bloodGroupLabel) {
            this.bloodGroupLabel = bloodGroupLabel;
            return this;
        }

        public CompatibilityResultDtoBuilder compatibleDonorGroups(List<BloodGroup> compatibleDonorGroups) {
            this.compatibleDonorGroups = compatibleDonorGroups;
            return this;
        }

        public CompatibilityResultDtoBuilder compatibleRecipientGroups(List<BloodGroup> compatibleRecipientGroups) {
            this.compatibleRecipientGroups = compatibleRecipientGroups;
            return this;
        }

        public CompatibilityResultDtoBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public CompatibilityResultDtoBuilder disclaimer(String disclaimer) {
            this.disclaimer = disclaimer;
            return this;
        }

        public CompatibilityResultDto build() {
            return new CompatibilityResultDto(this.bloodGroup, this.bloodGroupLabel, this.compatibleDonorGroups, this.compatibleRecipientGroups, this.explanation, this.disclaimer);
        }
    }

    public static CompatibilityResultDtoBuilder builder() {
        return new CompatibilityResultDtoBuilder();
    }

}
