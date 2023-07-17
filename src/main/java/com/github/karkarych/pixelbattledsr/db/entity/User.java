package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table(name = "users")
@Entity
public class User {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "login", nullable = false, length = Integer.MAX_VALUE)
  private String login;

  @NotNull
  @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
  private String email;

  @NotNull
  @Column(name = "last_access_date", nullable = false)
  private Instant lastAccessDate;

  @NotNull
  @Column(name = "password_hash", nullable = false, length = Integer.MAX_VALUE)
  private String passwordHash;

  @NotNull
  @Column(name = "captured_cells", nullable = false)
  private Integer capturedCells;

  @ManyToMany(mappedBy = "users")
  private Set<Team> teams = new LinkedHashSet<>();
}
