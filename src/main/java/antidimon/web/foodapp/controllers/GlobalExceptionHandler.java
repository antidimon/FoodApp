package antidimon.web.foodapp.controllers;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorValidationResponse> handleConstraintViolationException(ConstraintViolationException e) {

        List<ValidationDetail> details = e.getConstraintViolations().stream()
                .map(violation -> new ValidationDetail(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();

        ErrorValidationResponse errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_INPUT",
                details
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidationResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_INPUT",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEnumValidation(HttpMessageNotReadableException e) {
        String invalidValue = extractInvalidEnumValue(e);
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Некорректное значение enum",
                        List.of(invalidValue).toString()
                )
        );
    }

    private String extractInvalidEnumValue(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        Pattern pattern = Pattern.compile("Cannot deserialize value of type .* from String \"([^\"]+)\"");
        Matcher matcher = pattern.matcher(message);
        return matcher.find() ? matcher.group(1) : "Некорректное значение";
    }


    public record ErrorResponse(
            int statusCode,
            String errorCode,
            String message
    ) {}

    public record ErrorValidationResponse(
            int statusCode,
            String errorCode,
            List<ValidationDetail> details
    ) {}

    public record ValidationDetail(
            String field,
            String message
    ) {}
}
