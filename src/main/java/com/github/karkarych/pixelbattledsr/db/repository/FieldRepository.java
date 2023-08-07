package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.Field;
import com.github.karkarych.pixelbattledsr.db.entity.FieldId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepository extends JpaRepository<Field, FieldId> {

  @Query("SELECT MAX(f.id.row) FROM Field f")
  int findMaxRow();

  @Query("SELECT MAX(f.id.column) FROM Field f")
  int findMaxColumn();
}
