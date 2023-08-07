package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamRequest;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamSaveRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface TeamService {

  PageResponse<TeamResponse> getTeams(TeamRequest teamRequest);

  TeamResponse createTeam(@Valid TeamSaveRequest teamRequest, @NotNull String username);

  void updateTeam(@NotNull UUID teamId, TeamSaveRequest teamRequest, @NotNull String username);

  void deleteTeam(@NotNull UUID teamId, @NotNull String username);

  void addUserToTeam(@NotNull UUID teamId, @NotNull String username);

  void removeUserFromTeam(@NotNull UUID teamId, @NotNull String username);

  List<TeamResponse> getTopTeams(int topLimit);
}
