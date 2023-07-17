package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.CoordinatesRequest;

public interface WebsocketService {

  void sendUpdatedField(CoordinatesRequest request);
}
