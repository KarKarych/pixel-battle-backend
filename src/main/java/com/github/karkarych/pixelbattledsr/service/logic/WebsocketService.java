package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.WebSocketResponse;
import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;

public interface WebsocketService {

  void sendUpdatedField(CoordinatesRequest request);

  void sendTopPlayers(WebSocketResponse<UserResponse> response);

  void sendTopTeams(WebSocketResponse<TeamResponse> response);
}
