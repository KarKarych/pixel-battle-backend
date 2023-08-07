package com.github.karkarych.pixelbattledsr.service.model;

import java.util.List;


public record PageResponse<T>(
  List<? extends T> content,
  int currentPage,
  int pageSize,
  int totalPages
) {
}
