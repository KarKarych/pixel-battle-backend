package com.github.karkarych.pixelbattledsr.service.model.team;

import jakarta.validation.constraints.NotNull;

public record TeamSaveRequest(
  @NotNull
  String name
) {
}
