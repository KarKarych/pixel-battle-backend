package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.DeactivatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeactivatedTokenRepository extends JpaRepository<DeactivatedToken, UUID> {
}
