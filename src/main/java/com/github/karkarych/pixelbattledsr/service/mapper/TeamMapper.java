package com.github.karkarych.pixelbattledsr.service.mapper;

import com.github.karkarych.pixelbattledsr.db.entity.Team;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TeamMapper {

  private final UserMapper userMapper;

  public List<TeamResponse> fromEntities(Collection<Team> teams, boolean withUsers) {
    if (teams == null) {
      return null;
    }

    List<TeamResponse> teamsResponse = new ArrayList<>();
    for (Team team : teams) {
      teamsResponse.add(fromEntity(team, withUsers));
    }

    return teamsResponse;
  }

  public TeamResponse fromEntity(Team team, boolean withUsers) {
    if (team == null) {
      return null;
    }

    List<UserResponse> users = List.of();
    if (withUsers) {
      users = userMapper.fromEntities(team.getUsers());
    }

    return new TeamResponse(
      team.getId(),
      team.getName(),
      team.getCapturedCells(),
      users
    );
  }
}
