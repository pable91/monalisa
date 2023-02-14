package com.monalisa.global.error;

import com.monalisa.global.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String message;
    private final int status;
    private final List<FieldErrorValue> errors;
    private final String code;

    @Getter
    private static class FieldErrorValue {
        private final String field;
        private final Object value;
        private final String reason;

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
        this.errors = new ArrayList<>(errors);
        this.code = errorCode.getCode();
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = Collections.emptyList();
        this.code = errorCode.getCode();
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        final List<FieldErrorValue> tmpErrors = createFieldErrorValueList(bindingResult);
        return new ErrorResponse(errorCode, tmpErrors);
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    private static List<FieldErrorValue> createFieldErrorValueList(BindingResult bindingResult) {
        final List<FieldErrorValue> tmpErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> FieldErrorValue.of(fieldError))
                .collect(Collectors.toList());
        return tmpErrors;
    }
}
