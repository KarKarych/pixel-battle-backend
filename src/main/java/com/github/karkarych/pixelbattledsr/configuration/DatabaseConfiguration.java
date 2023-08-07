package com.github.karkarych.pixelbattledsr.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(
  basePackages = "com.github.karkarych.pixelbattledsr.db.repository"
)
@Configuration
public class DatabaseConfiguration {
}
