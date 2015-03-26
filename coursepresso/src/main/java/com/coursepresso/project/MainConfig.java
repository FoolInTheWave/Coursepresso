package com.coursepresso.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ControllerConfig.class, ServiceConfig.class })
public class MainConfig {

  public MainConfig() {
    super();
  }
}
