package com.github.karkarych.pixelbattledsr.service.logic.impl;

import com.github.karkarych.pixelbattledsr.db.entity.Team;
import com.github.karkarych.pixelbattledsr.db.entity.User;
import com.github.karkarych.pixelbattledsr.db.repository.TeamRepository;
import com.github.karkarych.pixelbattledsr.db.repository.UserRepository;
import com.github.karkarych.pixelbattledsr.service.logic.UserService;
import com.github.karkarych.pixelbattledsr.service.mapper.TeamMapper;
import com.github.karkarych.pixelbattledsr.service.mapper.UserMapper;
import com.github.karkarych.pixelbattledsr.service.model.PageResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.TeamResponse;
import com.github.karkarych.pixelbattledsr.service.model.team.UserTeamRequest;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserMapper userMapper;

  private final UserRepository userRepository;
  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;

  @Override
  public PageResponse<TeamResponse> getUserTeams(UUID userId, UserTeamRequest userTeamRequest) {
    int currentPage = userTeamRequest.currentPage();
    int pageSize = userTeamRequest.pageSize();
    Pageable pageRequest = PageRequest.of(currentPage, pageSize);

    Page<UUID> pagedTeamIds;
    if (userTeamRequest.isOwner()) {
      pagedTeamIds = teamRepository.findAllTeamIdsByOwnerIdWithUsers(userId, pageRequest);
    } else {
      pagedTeamIds = teamRepository.findAllTeamsByUserIdWithUsers(userId, pageRequest);
    }

    List<Team> pagedTeams = teamRepository.findAllById(pagedTeamIds.getContent());
    List<TeamResponse> teamsResponse = teamMapper.fromEntities(pagedTeams, true);

    return new PageResponse<>(
      teamsResponse,
      currentPage,
      pageSize,
      pagedTeamIds.getTotalPages()
    );
  }

  @Override
  public List<UserResponse> getTopUsers(int topLimit) {
    List<User> topUsers = userRepository.findTopUsers(topLimit);
    return userMapper.fromEntities(topUsers);
  }
}
