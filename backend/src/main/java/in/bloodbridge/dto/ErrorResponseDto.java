package in.bloodbridge.dto;

import java.time.Instant;
import java.util.List;

public class ErrorResponseDto {
    private Instant timestamp = Instant.now();
    private int status;
    private String code;
    private String message;
    private List<FieldErrorDto> fieldErrors;
    private String traceId;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(Instant timestamp, int status, String code, String message, List<FieldErrorDto> fieldErrors, String traceId) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.traceId = traceId;
    }

    public static class FieldErrorDto {
        private String field;
        private String message;

        public FieldErrorDto() {}

        public FieldErrorDto(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponseDtoBuilder {
        private Instant timestamp = Instant.now();
        private int status;
        private String code;
        private String message;
        private List<FieldErrorDto> fieldErrors;
        private String traceId;

        public ErrorResponseDtoBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseDtoBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorResponseDtoBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseDtoBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseDtoBuilder fieldErrors(List<FieldErrorDto> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public ErrorResponseDtoBuilder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ErrorResponseDto build() {
            return new ErrorResponseDto(timestamp, status, code, message, fieldErrors, traceId);
        }
    }

    public static ErrorResponseDtoBuilder builder() {
        return new ErrorResponseDtoBuilder();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldErrorDto> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
