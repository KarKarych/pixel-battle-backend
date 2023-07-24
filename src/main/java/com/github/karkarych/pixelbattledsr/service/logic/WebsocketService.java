package com.github.karkarych.pixelbattledsr.service.logic;

import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;

public interface WebsocketService {

  void sendUpdatedField(CoordinatesRequest request);
}
