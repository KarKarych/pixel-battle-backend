package com.github.karkarych.pixelbattledsr.service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CoordinatesRequest(
  int row,
  int column,
  @Size(max = 7)
  @NotNull
  String color,
  @NotNull
  UUID userId
) {
}
