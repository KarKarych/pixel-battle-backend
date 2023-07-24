package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.field.FieldResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface FieldService {

  FieldResponse getField();

  void saveCoordinates(@Valid CoordinatesRequest request, String username);

  void createField();
}
