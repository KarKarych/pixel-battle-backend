package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.db.entity.DeactivatedToken;
import com.github.karkarych.pixelbattledsr.db.repository.DeactivatedTokenRepository;
import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import com.github.karkarych.pixelbattledsr.service.model.auth.TokenUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;

import java.util.function.Function;

@RequiredArgsConstructor
public class TokenCookieAuthenticationConfigurer
  extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity> {

  private final Function<String, Token> tokenCookieStringDeserializer;
  private final DeactivatedTokenRepository deactivatedTokenRepository;

  @Override
  public void init(HttpSecurity builder) throws Exception {
    builder.logout(logout -> logout
      .addLogoutHandler(new CookieClearingLogoutHandler("__Unsecure-Host-auth-token"))
      .addLogoutHandler((request, response, authentication) -> {
        if (authentication != null && authentication.getPrincipal() instanceof TokenUser user) {
          DeactivatedToken deactivatedToken = new DeactivatedToken();
          deactivatedToken.setId(user.getToken().id());
          deactivatedToken.setExpiresAt(user.getToken().expiresAt());
          deactivatedTokenRepository.save(deactivatedToken);

          response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }})
      .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
    );
  }

  @Override
  public void configure(HttpSecurity builder) {
    AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
    var cookieAuthenticationFilter = new AuthenticationFilter(
      authenticationManager,
      new TokenCookieAuthenticationConverter(this.tokenCookieStringDeserializer)
    );

    cookieAuthenticationFilter.setSuccessHandler((request, response, authentication) -> {});
    cookieAuthenticationFilter.setFailureHandler(
      new AuthenticationEntryPointFailureHandler(new Http403ForbiddenEntryPoint())
    );

    var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
    preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(
      new TokenAuthenticationUserDetailsService(this.deactivatedTokenRepository)
    );

    builder
      .addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
      .authenticationProvider(preAuthenticatedAuthenticationProvider);
  }
}
