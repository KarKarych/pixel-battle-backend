package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {

  @Override
  @NonNull
  @EntityGraph(value = "graph.TeamWithUsers")
  List<Team> findAllById(@NonNull Iterable<UUID> ids);

  @EntityGraph(value = "graph.TeamWithUsers")
  @Query("SELECT t FROM Team t")
  Page<Team> findAllTeamsWithUsers(Pageable pageable);

  @EntityGraph(value = "graph.TeamWithUsers")
  @Query("SELECT t FROM Team t WHERE t.id = :id")
  Optional<Team> findByIdWithUsers(UUID id);

  @Query(value = """  
    SELECT  t.id
     FROM   Team t
     WHERE  t.ownerId = :ownerId
     ORDER BY t.name DESC
    """)
  Page<UUID> findAllTeamIdsByOwnerIdWithUsers(UUID ownerId, Pageable pageable);

  @Query(value = """  
    SELECT  tu.id.teamId
     FROM   TeamsUser tu
     WHERE  tu.id.userId = :userId
    """)
  Page<UUID> findAllTeamsByUserIdWithUsers(UUID userId, Pageable pageable);

  @Query(value = """  
    SELECT  t.*
     FROM   teams t
     ORDER BY t.captured_cells DESC
     LIMIT  :limit
    """, nativeQuery = true)
  List<Team> findTopTeams(Integer limit);
}
