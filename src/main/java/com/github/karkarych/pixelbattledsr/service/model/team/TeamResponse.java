package com.github.karkarych.pixelbattledsr.service.model.team;

import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;

import java.util.List;
import java.util.UUID;

public record TeamResponse(
  UUID id,
  String name,
  Integer capturedCells,
  List<UserResponse> users
) {
}
