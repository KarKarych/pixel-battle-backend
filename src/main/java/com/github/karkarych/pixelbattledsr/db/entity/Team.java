package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table(name = "teams")
@Entity
public class Team {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "captured_cells", nullable = false)
  private Integer capturedCells;

  @ManyToMany
  @JoinTable(
    name = "teams_users",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new LinkedHashSet<>();
}
