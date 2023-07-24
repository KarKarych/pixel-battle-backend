package com.github.karkarych.pixelbattledsr.service.model.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserAuthRequest(
  @NotNull
  @Size(min = 3, max = 32)
  String login,
  @NotNull
  @Size(min = 6, max = 64)
  String password
) {
}
