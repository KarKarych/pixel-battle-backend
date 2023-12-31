package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

public class DefaultTokenCookieFactory implements Function<Authentication, Token> {

  private final Duration tokenTtl = Duration.ofDays(7);

  @Override
  public Token apply(Authentication authentication) {
    Instant now = Instant.now();
    return new Token(
      UUID.randomUUID(),
      authentication.getName(),
      authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList(),
      now,
      now.plus(this.tokenTtl)
    );
  }
}
