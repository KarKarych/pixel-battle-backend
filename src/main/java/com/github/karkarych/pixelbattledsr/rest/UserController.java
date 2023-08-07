package com.github.karkarych.pixelbattledsr.rest;


import com.github.karkarych.pixelbattledsr.service.logic.UserService;
import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.UserTeamRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}/teams")
  PageResponse<TeamResponse> getUserTeams(
    @PathVariable UUID userId,
    @RequestBody UserTeamRequest userTeamRequest
  ) {
    return userService.getUserTeams(userId, userTeamRequest);
  }
}
