package com.coursepresso.project;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@Import({ DataSourceConfig.class, ControllerConfig.class })
public class MainConfig {

  public MainConfig() {
    super();
  }
}
