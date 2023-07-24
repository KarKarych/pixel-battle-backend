package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.user.UserAuthRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AuthService {

  void register(@Valid UserAuthRequest request);

  UserDetails checkCredentials(@Valid UserAuthRequest request);
}
