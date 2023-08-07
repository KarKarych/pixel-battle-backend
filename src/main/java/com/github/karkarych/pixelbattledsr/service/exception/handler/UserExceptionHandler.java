package com.github.karkarych.pixelbattledsr.service.exception.handler;

import com.github.karkarych.pixelbattledsr.service.exception.ErrorResponse;
import com.github.karkarych.pixelbattledsr.service.exception.model.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
    UserException.Code code = ex.getCode();
    HttpStatus status = switch (code) {
      case USER_NOT_FOUND -> NOT_FOUND;
    };

    String codeStr = code.toString();

    log.error(codeStr, ex);

    return ResponseEntity
      .status(status)
      .body(new ErrorResponse(codeStr, ex.getMessage()));
  }
}
