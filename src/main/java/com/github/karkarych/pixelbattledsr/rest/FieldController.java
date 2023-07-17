package com.github.karkarych.pixelbattledsr.rest;

import com.github.karkarych.pixelbattledsr.service.logic.FieldService;
import com.github.karkarych.pixelbattledsr.service.model.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.FieldResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fields")
@RestController
public class FieldController {

  private final FieldService fieldService;

  @GetMapping("/main")
  public FieldResponse getField() {
    return fieldService.getField();
  }

  @PostMapping("/coordinates")
  public void saveCoordinates(@RequestBody CoordinatesRequest request) {
    fieldService.saveCoordinates(request);
  }
}

//юзер контроллер
//логин
//регистер
//логаут
