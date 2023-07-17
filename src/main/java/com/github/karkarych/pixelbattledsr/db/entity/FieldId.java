package com.github.karkarych.pixelbattledsr.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FieldId {

  @NotNull
  @Column(name = "row_coordinate", nullable = false)
  private int row;

  @NotNull
  @Column(name = "column_coordinate", nullable = false)
  private int column;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    FieldId entity = (FieldId) o;
    return Objects.equals(this.column, entity.column) &&
      Objects.equals(this.row, entity.row);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
