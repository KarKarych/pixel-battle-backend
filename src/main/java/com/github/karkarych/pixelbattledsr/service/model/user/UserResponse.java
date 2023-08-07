package com.github.karkarych.pixelbattledsr.service.model.user;

import java.util.UUID;

public record UserResponse(
  UUID id,
  String login,
  Integer capturedCells
) {
}
