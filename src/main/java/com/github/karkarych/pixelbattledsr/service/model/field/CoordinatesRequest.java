package com.github.karkarych.pixelbattledsr.service.model.field;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CoordinatesRequest(
  @NotNull
  Integer row,
  @NotNull
  Integer column,
  @NotNull
  @Size(max = 7)
  @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "hex invalid")
  String color
) {
}
