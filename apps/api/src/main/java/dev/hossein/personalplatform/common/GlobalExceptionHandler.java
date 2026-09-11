package dev.hossein.personalplatform.common;

import dev.hossein.personalplatform.projects.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handleNotFound(ProjectNotFoundException exception) {
        return new ApiError(Instant.now(), 404, exception.getMessage(), Map.of());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleDomainError(RuntimeException exception) {
        return new ApiError(Instant.now(), 400, exception.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> details = new LinkedHashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            details.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        return new ApiError(
                Instant.now(),
                400,
                "Request validation failed",
                details
        );
    }
}
