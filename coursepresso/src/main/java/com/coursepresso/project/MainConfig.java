package com.coursepresso.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.coursepresso.project.repository")
@Import({ DataSourceConfig.class, ControllerConfig.class, ServiceConfig.class })
public class MainConfig {

  public MainConfig() {
    super();
  }
}
