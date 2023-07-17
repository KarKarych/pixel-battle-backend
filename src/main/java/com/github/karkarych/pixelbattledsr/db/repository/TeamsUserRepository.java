package com.github.karkarych.pixelbattledsr.db.repository;

import com.github.karkarych.pixelbattledsr.db.entity.TeamsUser;
import com.github.karkarych.pixelbattledsr.db.entity.TeamsUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsUserRepository extends JpaRepository<TeamsUser, TeamsUserId> {
}
