package com.github.karkarych.pixelbattledsr.rest;

import com.github.karkarych.pixelbattledsr.service.logic.TeamService;
import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamRequest;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

  private final TeamService teamService;

  @GetMapping
  PageResponse<TeamResponse> getTeams(TeamRequest teamRequest) {
    return teamService.getTeams(teamRequest);
  }

  @PostMapping
  TeamResponse createTeam(
    @RequestBody TeamSaveRequest teamRequest,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    return teamService.createTeam(teamRequest, userDetails.getUsername());
  }

  @PutMapping("/{teamId}")
  void updateTeam(
    @PathVariable UUID teamId,
    @RequestBody TeamSaveRequest teamRequest,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    teamService.updateTeam(teamId, teamRequest, userDetails.getUsername());
  }

  @DeleteMapping("/{teamId}")
  void deleteTeam(
    @PathVariable UUID teamId,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    teamService.deleteTeam(teamId, userDetails.getUsername());
  }

  @PostMapping("/{teamId}/user")
  void addUserToTeam(
    @PathVariable UUID teamId,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    teamService.addUserToTeam(teamId, userDetails.getUsername());
  }

  @DeleteMapping("/{teamId}/user")
  void removeUserFromTeam(
    @PathVariable UUID teamId,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    teamService.removeUserFromTeam(teamId, userDetails.getUsername());
  }
}
