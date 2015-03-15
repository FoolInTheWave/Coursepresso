package com.coursepresso.project;

import com.coursepresso.project.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;

/**
 *
 * @author Caleb Miller
 */
@Configuration
public class ServiceConfig {

  @Bean
  public SecurityService securityService() {
    return createService("security.service", SecurityService.class);
  }

  @SuppressWarnings("unchecked")
  protected <T> T createService(String endPoint, Class<T> serviceInterface) {
    HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
    String serverUrl = String.format("http://localhost:8080/coursepresso/%s", endPoint);
    factory.setServiceUrl(serverUrl);
    factory.setServiceInterface(serviceInterface);
    factory.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
    factory.afterPropertiesSet();
    return (T) factory.getObject();
  }

  @Bean
  public HttpInvokerRequestExecutor httpInvokerRequestExecutor() {
    //return new CommonsHttpInvokerRequestExecutor();
    return new HttpComponentsHttpInvokerRequestExecutor();
  }
}
