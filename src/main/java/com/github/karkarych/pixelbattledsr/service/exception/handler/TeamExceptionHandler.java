package com.github.karkarych.pixelbattledsr.service.exception.handler;

import com.github.karkarych.pixelbattledsr.service.exception.ErrorResponse;
import com.github.karkarych.pixelbattledsr.service.exception.model.TeamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class TeamExceptionHandler {

  @ExceptionHandler(TeamException.class)
  public ResponseEntity<ErrorResponse> handleTeamException(TeamException ex) {
    TeamException.Code code = ex.getCode();
    HttpStatus status = switch (code) {
      case TEAM_NOT_FOUND -> NOT_FOUND;
      case TEAM_OWNER_MISMATCH, TEAM_USER_IS_MEMBER, TEAM_USER_IS_NOT_MEMBER -> BAD_REQUEST;
    };

    String codeStr = code.toString();

    log.error(codeStr, ex);

    return ResponseEntity
      .status(status)
      .body(new ErrorResponse(codeStr, ex.getMessage()));
  }
}
