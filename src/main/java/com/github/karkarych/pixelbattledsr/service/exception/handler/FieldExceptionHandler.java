package com.github.karkarych.pixelbattledsr.service.exception.handler;

import com.github.karkarych.pixelbattledsr.service.exception.ErrorResponse;
import com.github.karkarych.pixelbattledsr.service.exception.model.FieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class FieldExceptionHandler {

  @ExceptionHandler(FieldException.class)
  public ResponseEntity<ErrorResponse> handleFieldException(FieldException ex) {
    FieldException.Code code = ex.getCode();
    HttpStatus status = switch (code) {
      case FIELD_ROW_OUT_OF_RANGE, FIELD_COLUMN_OUT_OF_RANGE -> BAD_REQUEST;
      case FIELD_TOO_MANY_REQUEST_UPDATE -> TOO_MANY_REQUESTS;
      case FIELD_NOT_FOUND -> INTERNAL_SERVER_ERROR;
    };

    String codeStr = code.toString();

    log.error(codeStr, ex);

    return ResponseEntity
      .status(status)
      .body(new ErrorResponse(codeStr, ex.getMessage()));
  }
}
