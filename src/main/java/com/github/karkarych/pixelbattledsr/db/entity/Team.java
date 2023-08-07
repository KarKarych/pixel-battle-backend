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
@NamedEntityGraph(
  name = "graph.TeamWithUsers",
  attributeNodes = @NamedAttributeNode(value = "users")
)
@Table(name = "teams")
@Entity
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "captured_cells")
  private Integer capturedCells;

  @NotNull
  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;

  @ManyToMany
  @JoinTable(
    name = "teams_users",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users;

  public void addUser(User user) {
    if (users == null) {
      users = new LinkedHashSet<>();
    }

    users.add(user);
  }

  public void removeUser(User user) {
    if (users != null) {
      users.remove(user);
    }
  }
}
