package com.coursepresso.project;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({ DataSourceConfig.class, ControllerConfig.class })
public class MainConfig {

  public MainConfig() {
    super();
  }
}
