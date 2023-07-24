package com.github.karkarych.pixelbattledsr.rest;

import com.github.karkarych.pixelbattledsr.service.logic.FieldService;
import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.field.FieldResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/fields")
@RestController
public class FieldController {

  private final FieldService fieldService;

  @GetMapping("/main")
  public FieldResponse getField() {
    return fieldService.getField();
  }

  @PostMapping("/coordinates")
  public void saveCoordinates(
    @RequestBody CoordinatesRequest request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    fieldService.saveCoordinates(request, userDetails.getUsername());
  }
}
