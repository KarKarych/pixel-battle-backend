package com.github.karkarych.pixelbattledsr.service.logic.impl;

import com.github.karkarych.pixelbattledsr.db.entity.Team;
import com.github.karkarych.pixelbattledsr.db.entity.User;
import com.github.karkarych.pixelbattledsr.db.repository.TeamRepository;
import com.github.karkarych.pixelbattledsr.db.repository.UserRepository;
import com.github.karkarych.pixelbattledsr.service.logic.TeamService;
import com.github.karkarych.pixelbattledsr.service.mapper.TeamMapper;
import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamRequest;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.github.karkarych.pixelbattledsr.service.exception.model.TeamException.Code.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

  private final TeamMapper teamMapper;
  private final UserRepository userRepository;
  private final TeamRepository teamRepository;

  @Override
  @Transactional(readOnly = true)
  public PageResponse<TeamResponse> getTeams(TeamRequest teamRequest) {
    int currentPage = teamRequest.currentPage();
    int pageSize = teamRequest.pageSize();

    Pageable pageRequest = PageRequest.of(currentPage, pageSize);

    Page<Team> pagedTeams = teamRepository.findAllTeamsWithUsers(pageRequest);
    List<TeamResponse> teamsResponse = teamMapper.fromEntities(pagedTeams.getContent(), true);

    return new PageResponse<>(
      teamsResponse,
      currentPage,
      pageSize,
      pagedTeams.getTotalPages()
    );
  }

  @Override
  @Transactional
  public TeamResponse createTeam(TeamSaveRequest teamRequest, String username) {
    User userPersisted = userRepository.findByLogin(username)
      .orElseThrow();

    Team teamTransient = new Team();
    teamTransient.setName(teamRequest.name());
    teamTransient.setCapturedCells(0);
    teamTransient.setOwnerId(userPersisted.getId());
    teamTransient.addUser(userPersisted);

    Team teamPersisted = teamRepository.save(teamTransient);

    return teamMapper.fromEntity(teamPersisted, true);
  }

  @Override
  @Transactional
  public void updateTeam(UUID teamId, TeamSaveRequest teamRequest, String username) {
    User userPersisted = userRepository.findByLogin(username)
      .orElseThrow();

    Team teamPersisted = teamRepository.findById(teamId)
      .orElseThrow(TEAM_NOT_FOUND::get);

    if (!teamPersisted.getOwnerId().equals(userPersisted.getId())) {
      throw TEAM_OWNER_MISMATCH.get();
    }

    teamPersisted.setName(teamRequest.name());
  }

  @Override
  @Transactional
  public void deleteTeam(UUID teamId, String username) {
    User userPersisted = userRepository.findByLogin(username)
      .orElseThrow();

    Team teamPersisted = teamRepository.findById(teamId)
      .orElseThrow(TEAM_NOT_FOUND::get);

    if (!teamPersisted.getOwnerId().equals(userPersisted.getId())) {
      throw TEAM_OWNER_MISMATCH.get();
    }

    teamRepository.delete(teamPersisted);
  }

  @Override
  @Transactional
  public void addUserToTeam(UUID teamId, String username) {
    User userPersisted = userRepository.findByLogin(username)
      .orElseThrow();

    Team teamPersisted = teamRepository.findByIdWithUsers(teamId)
      .orElseThrow(TEAM_NOT_FOUND::get);

    if (teamPersisted.getUsers().contains(userPersisted)) {
      throw TEAM_USER_IS_MEMBER.get();
    }

    teamPersisted.addUser(userPersisted);
  }

  @Override
  @Transactional
  public void removeUserFromTeam(UUID teamId, String username) {
    User userPersisted = userRepository.findByLogin(username)
      .orElseThrow();

    Team teamPersisted = teamRepository.findByIdWithUsers(teamId)
      .orElseThrow(TEAM_NOT_FOUND::get);

    if (!teamPersisted.getUsers().contains(userPersisted)) {
      throw TEAM_USER_IS_NOT_MEMBER.get();
    }

    teamPersisted.removeUser(userPersisted);
  }

  @Override
  public List<TeamResponse> getTopTeams(int topLimit) {
    List<Team> topTeams = teamRepository.findTopTeams(topLimit);
    return teamMapper.fromEntities(topTeams, false);
  }
}
