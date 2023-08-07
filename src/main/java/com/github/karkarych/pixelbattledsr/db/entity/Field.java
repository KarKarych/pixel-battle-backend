package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fields")
@Entity
public class Field {

  @EmbeddedId
  private FieldId id;

  @NotNull
  @Column(name = "color", nullable = false)
  private String color;

  @Column(name = "owner_id")
  private UUID ownerId;
}
