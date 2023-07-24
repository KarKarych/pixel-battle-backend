package com.github.karkarych.pixelbattledsr.service.logic.impl;

import com.github.karkarych.pixelbattledsr.db.entity.User;
import com.github.karkarych.pixelbattledsr.db.repository.UserRepository;
import com.github.karkarych.pixelbattledsr.service.logic.AuthService;
import com.github.karkarych.pixelbattledsr.service.model.user.UserAuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.github.karkarych.pixelbattledsr.service.exception.model.AuthException.Code.AUTH_LOGIN_EXISTS;
import static com.github.karkarych.pixelbattledsr.service.exception.model.AuthException.Code.AUTH_PASSWORD_DOES_NOT_MATCH;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  @Override
  public void register(UserAuthRequest request) {
    if (userRepository.existsByLogin(request.login())) {
      throw AUTH_LOGIN_EXISTS.get();
    }
    userRepository.save(new User(request.login(), encoder.encode(request.password())));
  }

  @Override
  public UserDetails checkCredentials(UserAuthRequest request) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());
    if (!encoder.matches(request.password(), userDetails.getPassword())) {
      throw AUTH_PASSWORD_DOES_NOT_MATCH.get();
    }
    return userDetails;
  }
}
