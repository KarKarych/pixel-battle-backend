package com.github.karkarych.pixelbattledsr.service.model.field;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoordinatesRequest(
  @NotNull
  Integer row,
  @NotNull
  Integer column,
  @Size(max = 7)
  @NotNull
  String color
) {
}
