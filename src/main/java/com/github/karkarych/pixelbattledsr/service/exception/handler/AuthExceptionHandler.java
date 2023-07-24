package com.github.karkarych.pixelbattledsr.service.exception.handler;

import com.github.karkarych.pixelbattledsr.service.exception.ErrorResponse;
import com.github.karkarych.pixelbattledsr.service.exception.model.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
    AuthException.Code code = ex.getCode();
    HttpStatus status = switch (code) {
      case AUTH_PASSWORD_DOES_NOT_MATCH, AUTH_LOGIN_EXISTS -> BAD_REQUEST;
    };

    String codeStr = code.toString();

    log.error(codeStr, ex);

    return ResponseEntity
      .status(status)
      .body(new ErrorResponse(codeStr, ex.getMessage()));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    log.error(ex.getMessage(), ex);

    return ResponseEntity
      .status(BAD_REQUEST)
      .body(new ErrorResponse(BAD_REQUEST.name(), ex.getMessage()));
  }
}
