package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@RequiredArgsConstructor
public class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

  private final Function<Authentication, Token> tokenCookieFactory;
  private final Function<Token, String> tokenStringSerializer;

  @Override
  public void onAuthentication(Authentication authentication, HttpServletRequest request,
                               HttpServletResponse response) throws SessionAuthenticationException {
    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      Token token = this.tokenCookieFactory.apply(authentication);
      String tokenString = this.tokenStringSerializer.apply(token);

      Cookie cookie = new Cookie("__Unsecure-Host-auth-token", tokenString);
      cookie.setPath("/");
      cookie.setDomain(null);
      cookie.setHttpOnly(false);
      cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.expiresAt()));

      response.addCookie(cookie);
    }
  }
}
