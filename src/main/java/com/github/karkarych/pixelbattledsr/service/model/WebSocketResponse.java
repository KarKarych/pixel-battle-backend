package com.github.karkarych.pixelbattledsr.service.model;

import com.github.karkarych.pixelbattledsr.enumeration.ResponseType;

import java.util.List;


public record WebSocketResponse<T>(
  ResponseType type,
  List<? extends T> content
) {
}
