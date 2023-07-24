package com.github.karkarych.pixelbattledsr.security;

import com.github.karkarych.pixelbattledsr.db.entity.UserAuthority;
import com.github.karkarych.pixelbattledsr.db.repository.DeactivatedTokenRepository;
import com.github.karkarych.pixelbattledsr.db.repository.UserRepository;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Configuration
public class SecurityConfigurer {

  @Value("${settings.frontend-url}")
  private String frontendUrl;

  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenCookieJweStringSerializer tokenCookieJweStringSerializer(
    @Value("${settings.cookie-token-key}")
    String cookieTokenKey
  ) throws Exception {
    return new TokenCookieJweStringSerializer(new DirectEncrypter(
      OctetSequenceKey.parse(cookieTokenKey)
    ));
  }

  @Bean
  public TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy(
    TokenCookieJweStringSerializer tokenCookieJweStringSerializer
  ) {
    return new TokenCookieSessionAuthenticationStrategy(
      new DefaultTokenCookieFactory(),
      tokenCookieJweStringSerializer
    );
  }

  @Bean
  public TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer(
    @Value("${settings.cookie-token-key}")
    String cookieTokenKey,
    DeactivatedTokenRepository deactivatedTokenRepository
  ) throws Exception {
    return new TokenCookieAuthenticationConfigurer(
      new TokenCookieJweStringDeserializer(
        new DirectDecrypter(OctetSequenceKey.parse(cookieTokenKey))
      ),
      deactivatedTokenRepository
    );
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy,
    TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer
  ) throws Exception {

    http
      .cors(Customizer.withDefaults())
      .headers(headers -> headers.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable))
      .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
        .requestMatchers(OPTIONS).permitAll()
        .requestMatchers(GET, "/ws", "/fields/main").permitAll()
        .requestMatchers(POST, "/register", "/login", "/logout").permitAll()
        .anyRequest().authenticated()
      )
      .sessionManagement(sessionManagement -> sessionManagement
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy)
      )
      .csrf(AbstractHttpConfigurer::disable)
//      .csrf(csrf -> csrf
//        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//        .csrfTokenRepository(new CookieCsrfTokenRepository())
//        .sessionAuthenticationStrategy((authentication, request, response) -> {})
//        .ignoringRequestMatchers("/register", "/login", "/logout")
//      )
      .apply(tokenCookieAuthenticationConfigurer);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(frontendUrl));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public UserDetailsService userDetailsService() throws UsernameNotFoundException {
    return username -> {
      var userPersisted = userRepository.findByLoginWithAuthorities(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

      List<SimpleGrantedAuthority> userAuthorities = userPersisted.getUserAuthorities().stream()
        .map(UserAuthority::getAuthority)
        .map(SimpleGrantedAuthority::new)
        .toList();

      return User.builder()
        .username(userPersisted.getLogin())
        .password(userPersisted.getPasswordHash())
        .authorities(userAuthorities)
        .build();
    };
  }
}

