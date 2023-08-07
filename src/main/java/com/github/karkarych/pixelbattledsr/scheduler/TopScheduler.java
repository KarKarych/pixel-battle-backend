package com.github.karkarych.pixelbattledsr.scheduler;

import com.github.karkarych.pixelbattledsr.enumeration.ResponseType;
import com.github.karkarych.pixelbattledsr.service.logic.TeamService;
import com.github.karkarych.pixelbattledsr.service.logic.UserService;
import com.github.karkarych.pixelbattledsr.service.logic.WebsocketService;
import com.github.karkarych.pixelbattledsr.service.model.WebSocketResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TopScheduler {

  @Value("${pixel-battle.top-limit}")
  private int topLimit;

  private final WebsocketService websocketService;
  private final UserService userService;
  private final TeamService teamService;

  @Scheduled(fixedDelay = 10_000)
  public void sendTopPlayers() {
    List<UserResponse> topUsers = userService.getTopUsers(topLimit);
    websocketService.sendTopPlayers(new WebSocketResponse<>(ResponseType.USER, topUsers));
  }

  @Scheduled(fixedDelay = 10_000)
  public void sendTopTeams() {
    List<TeamResponse> topTeams = teamService.getTopTeams(topLimit);
    websocketService.sendTopTeams(new WebSocketResponse<>(ResponseType.TEAM, topTeams));
  }
}
