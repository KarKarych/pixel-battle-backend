package com.github.karkarych.pixelbattledsr.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PingController {

  @GetMapping("/ping")
  public String getVoid() {
    return "pong";
  }
}
