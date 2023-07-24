package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@NamedEntityGraph(
  name = "graph.UserWithAuthorities",
  attributeNodes = @NamedAttributeNode(value = "userAuthorities")
)
@Table(name = "users")
@Entity
public class User {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "login", nullable = false, length = Integer.MAX_VALUE)
  private String login;

  @Column(name = "email", length = Integer.MAX_VALUE)
  private String email;

  @NotNull
  @Column(name = "password_hash", nullable = false, length = Integer.MAX_VALUE)
  private String passwordHash;

  @NotNull
  @Column(name = "captured_cells", nullable = false)
  private Integer capturedCells;

  @ManyToMany(mappedBy = "users")
  private Set<Team> teams = new LinkedHashSet<>();

  @OneToMany(mappedBy = "user")
  private Set<UserAuthority> userAuthorities = new LinkedHashSet<>();

  public User(String login, String passwordHash) {
    this.login = login;
    this.passwordHash = passwordHash;
    this.capturedCells = 0;
  }
}
