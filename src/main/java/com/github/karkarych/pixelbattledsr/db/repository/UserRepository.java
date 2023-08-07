package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByLogin(String login);

  Optional<User> findByLogin(String login);

  @Query("SELECT u FROM User u WHERE u.login = :login")
  @EntityGraph(value = "graph.UserWithAuthorities")
  Optional<User> findByLoginWithAuthorities(String login);

  @Modifying
  @Query("UPDATE User u SET u.capturedCells = u.capturedCells + 1 WHERE u.id = :userId")
  void increaseCapturedCellsById(UUID userId);

  @Modifying
  @Query("UPDATE User u SET u.capturedCells = u.capturedCells - 1 WHERE u.id = :userId")
  void decreaseCapturedCellsById(UUID userId);

  @Query(value = """  
    SELECT  u.*
     FROM   users u
     ORDER BY u.captured_cells DESC
     LIMIT :topLimit
    """, nativeQuery = true)
  List<User> findTopUsers(int topLimit);
}
