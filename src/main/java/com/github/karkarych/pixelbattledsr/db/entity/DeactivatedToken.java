package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Table(name = "deactivated_tokens")
@Entity
public class DeactivatedToken {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;
}
