package com.github.karkarych.pixelbattledsr.service.model.team;

public record TeamRequest(
  Integer currentPage,
  Integer pageSize
) {
  public TeamRequest {
    if (currentPage == null) currentPage = 0;
    if (pageSize == null) pageSize = 10;
  }
}
