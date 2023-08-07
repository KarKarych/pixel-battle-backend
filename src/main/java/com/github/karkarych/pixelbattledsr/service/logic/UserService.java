package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.UserTeamRequest;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface UserService {

  PageResponse<TeamResponse> getUserTeams(@NotNull UUID userId, @NotNull UserTeamRequest userTeamRequest);

  List<UserResponse> getTopUsers(int topLimit);
}
