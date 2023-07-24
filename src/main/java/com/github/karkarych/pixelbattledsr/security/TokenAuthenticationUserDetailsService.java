package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.db.repository.DeactivatedTokenRepository;
import com.github.karkarych.pixelbattledsr.service.model.auth.Token;
import com.github.karkarych.pixelbattledsr.service.model.auth.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class TokenAuthenticationUserDetailsService
  implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  private final DeactivatedTokenRepository deactivatedTokenRepository;

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
    throws UsernameNotFoundException {
    if (authenticationToken.getPrincipal() instanceof Token token) {
      boolean deactivatedTokenDoesNotExist = !deactivatedTokenRepository.existsById(token.id());
      boolean credentialsNonExpired = deactivatedTokenDoesNotExist && token.expiresAt().isAfter(Instant.now());

      List<SimpleGrantedAuthority> authorities = token.authorities().stream()
        .map(SimpleGrantedAuthority::new)
        .toList();

      return new TokenUser(
        token.subject(),
        "nopassword",
        true,
        true,
        credentialsNonExpired,
        true,
        authorities,
        token
      );
    }

    throw new UsernameNotFoundException("Principal must be of type Token");
  }
}
