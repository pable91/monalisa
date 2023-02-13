package com.monalisa.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldErrorValue> errors;
    private String code;

    @Getter
    private static class FieldErrorValue {
        private String field;
        private Object value;
        private String reason;

        private FieldErrorValue(FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue();
            this.reason = fieldError.getDefaultMessage();
        }

        public static FieldErrorValue of(FieldError fieldError) {
            return new FieldErrorValue(fieldError);
        }
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldErrorValue> errors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = errors;
        this.code = errorCode.getCode();
    }

    public ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = Collections.emptyList();
        this.code = errorCode.getCode();
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        List<FieldErrorValue> tmpErrors = createFieldErrorValueList(bindingResult);
        return new ErrorResponse(errorCode, tmpErrors);
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    private static List<FieldErrorValue> createFieldErrorValueList(BindingResult bindingResult) {
        List<FieldErrorValue> tmpErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> FieldErrorValue.of(fieldError))
                .collect(Collectors.toList());
        return tmpErrors;
    }
}
