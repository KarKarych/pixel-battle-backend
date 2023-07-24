package com.github.karkarych.pixelbattledsr.rest;

import com.github.karkarych.pixelbattledsr.security.TokenCookieSessionAuthenticationStrategy;
import com.github.karkarych.pixelbattledsr.service.logic.AuthService;
import com.github.karkarych.pixelbattledsr.service.model.auth.CsrfResponse;
import com.github.karkarych.pixelbattledsr.service.model.user.UserAuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

  private final CsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
  private final TokenCookieSessionAuthenticationStrategy authenticationStrategy;
  private final AuthService authService;

  @PostMapping("/register")
  public CsrfResponse register(
    @RequestBody
    UserAuthRequest request,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  ) {
    authService.register(request);

    return login(request, httpServletRequest, httpServletResponse);
  }

  @PostMapping("/login")
  public CsrfResponse login(
    @RequestBody
    UserAuthRequest request,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  ) {
    UserDetails userDetails = authService.checkCredentials(request);
    var authenticationToken = new UsernamePasswordAuthenticationToken(
      userDetails.getUsername(),
      userDetails.getAuthorities()
    );
    authenticationStrategy.onAuthentication(authenticationToken, httpServletRequest, httpServletResponse);

    DeferredCsrfToken deferredCsrfToken = csrfTokenRepository.loadDeferredToken(httpServletRequest, httpServletResponse);
    return new CsrfResponse(deferredCsrfToken.get().getToken());
  }
}
