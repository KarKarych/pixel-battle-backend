package com.github.karkarych.pixelbattledsr.redis.repository;

import com.github.karkarych.pixelbattledsr.redis.entity.UserCache;
import org.springframework.data.repository.CrudRepository;

public interface UserCacheRepository extends CrudRepository<UserCache, String> {
}
