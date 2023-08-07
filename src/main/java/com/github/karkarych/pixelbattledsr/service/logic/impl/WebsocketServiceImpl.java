package com.github.karkarych.pixelbattledsr.service.logic.impl;

import com.github.karkarych.pixelbattledsr.service.logic.WebsocketService;
import com.github.karkarych.pixelbattledsr.service.model.WebSocketResponse;
import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WebsocketServiceImpl implements WebsocketService {

  private final SimpMessagingTemplate template;

  @Override
  public void sendUpdatedField(CoordinatesRequest request) {
    template.convertAndSend("/topic/main", request);
  }

  @Override
  public void sendTopPlayers(WebSocketResponse<UserResponse> response) {
    template.convertAndSend("/topic/top", response);
  }

  @Override
  public void sendTopTeams(WebSocketResponse<TeamResponse> response) {
    template.convertAndSend("/topic/top", response);
  }
}
