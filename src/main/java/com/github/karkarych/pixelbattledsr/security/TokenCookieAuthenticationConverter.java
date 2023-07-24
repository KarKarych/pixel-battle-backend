package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TokenCookieAuthenticationConverter implements AuthenticationConverter {

  private final Function<String, Token> tokenCookieStringDeserializer;

  @Override
  public Authentication convert(HttpServletRequest request) {
    if (request.getCookies() != null) {
      return Stream.of(request.getCookies())
        .filter(cookie -> cookie.getName().equals("__Unsecure-Host-auth-token"))
        .findFirst()
        .map(cookie -> {
          var token = this.tokenCookieStringDeserializer.apply(cookie.getValue());
          return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
        })
        .orElse(null);
    }

    return null;
  }
}
