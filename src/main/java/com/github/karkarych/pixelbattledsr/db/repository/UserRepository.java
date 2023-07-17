package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  @Query(value = "SELECT u.lastAccessDate FROM User u WHERE u.id = :id")
  Instant findLastAccessDateById(UUID id);

  @Modifying
  @Query("UPDATE User u SET u.capturedCells = u.capturedCells + 1 WHERE u.id = :userId")
  void increaseCapturedCellsById(UUID userId);

  @Modifying
  @Query("UPDATE User u SET u.capturedCells = u.capturedCells - 1 WHERE u.id = :userId")
  void decreaseCapturedCellsById(UUID userId);

  @Modifying
  @Query("UPDATE User u SET u.lastAccessDate = :lastAccessDate WHERE u.id = :userId")
  void updateLastAccessDate(UUID userId, Instant lastAccessDate);
}
