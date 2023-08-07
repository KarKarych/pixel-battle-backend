package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "user_authorities")
@Entity
public class UserAuthority {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull
  @Column(name = "authority", nullable = false)
  private String authority;
}
