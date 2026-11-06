package in.bloodbridge.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ContactRequestCreateDto {
    @NotNull(message = "Donor reference is required")
    private UUID donorUserId;

    private UUID requestId; // optional tie to an active emergency request


    public ContactRequestCreateDto() {
    }

    public ContactRequestCreateDto(UUID donorUserId, UUID requestId) {
        this.donorUserId = donorUserId;
        this.requestId = requestId;
    }
    public UUID getDonorUserId() {
        return donorUserId;
    }
    public void setDonorUserId(UUID donorUserId) {
        this.donorUserId = donorUserId;
    }
    public UUID getRequestId() {
        return requestId;
    }
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
    public static class ContactRequestCreateDtoBuilder {
        private UUID donorUserId;
        private UUID requestId;

        public ContactRequestCreateDtoBuilder() {}

        public ContactRequestCreateDtoBuilder donorUserId(UUID donorUserId) {
            this.donorUserId = donorUserId;
            return this;
        }

        public ContactRequestCreateDtoBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public ContactRequestCreateDto build() {
            return new ContactRequestCreateDto(this.donorUserId, this.requestId);
        }
    }

    public static ContactRequestCreateDtoBuilder builder() {
        return new ContactRequestCreateDtoBuilder();
    }

}
