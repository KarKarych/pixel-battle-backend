package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class TeamsUserId {

  @NotNull
  @Column(name = "team_id", nullable = false)
  private UUID teamId;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TeamsUserId entity = (TeamsUserId) o;
    return Objects.equals(this.teamId, entity.teamId) &&
      Objects.equals(this.userId, entity.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teamId, userId);
  }
}
