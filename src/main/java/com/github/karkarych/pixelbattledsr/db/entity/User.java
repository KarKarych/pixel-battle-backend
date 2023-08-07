package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
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
  @Column(name = "login", nullable = false)
  private String login;

  @Column(name = "email")
  private String email;

  @NotNull
  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @NotNull
  @Column(name = "captured_cells", nullable = false)
  private Integer capturedCells;

  @OneToMany(mappedBy = "user")
  private Set<UserAuthority> userAuthorities;

  public User(String login, String passwordHash) {
    this.login = login;
    this.passwordHash = passwordHash;
    this.capturedCells = 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
