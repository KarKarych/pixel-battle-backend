package com.github.karkarych.pixelbattledsr.service.exception.handler;

import com.github.karkarych.pixelbattledsr.service.exception.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ConstraintViolationExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
    String message = e.getConstraintViolations().stream()
      .map(this::createErrorMessage)
      .collect(Collectors.joining("; "));

    return ResponseEntity
      .status(BAD_REQUEST)
      .body(new ErrorResponse(BAD_REQUEST.name(), message.trim()));
  }

  private <T> String createErrorMessage(ConstraintViolation<T> cv) {
    String fieldName = getFieldName(cv.getPropertyPath().toString());
    String message = fieldName + " " + cv.getMessage();
    return message.trim();
  }

  private String getFieldName(String propertyPath) {
    String[] split = propertyPath.split("\\.");
    if (split.length == 0) {
      return "";
    }
    return split[split.length - 1];
  }
}
