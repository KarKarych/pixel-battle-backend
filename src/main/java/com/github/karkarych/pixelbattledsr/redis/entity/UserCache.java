package com.github.karkarych.pixelbattledsr.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("UserCache")
public class UserCache {

  @Id
  private String id;

  private Instant lastAccessDate;
}
