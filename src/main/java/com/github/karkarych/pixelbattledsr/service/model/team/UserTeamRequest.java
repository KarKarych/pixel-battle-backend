package com.github.karkarych.pixelbattledsr.service.model.team;

public record UserTeamRequest(
  boolean isOwner,
  Integer currentPage,
  Integer pageSize
) {
  public UserTeamRequest {
    if (currentPage == null) currentPage = 0;
    if (pageSize == null) pageSize = 10;
  }
}
