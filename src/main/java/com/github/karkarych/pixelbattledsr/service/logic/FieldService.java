package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.FieldResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface FieldService {

  FieldResponse getField();

  void saveCoordinates(@Valid CoordinatesRequest request);

  void createField();
}
