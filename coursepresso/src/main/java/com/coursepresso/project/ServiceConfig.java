package com.coursepresso.project;

import com.coursepresso.project.repository.*;
import com.coursepresso.project.service.*;
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

  @Bean
  public ApplianceRepository applianceRepository() {
    return createService("appliance.repository", ApplianceRepository.class);
  }

  @Bean
  public CoursePrerequisiteRepository coursePrerequisiteRepository() {
    return createService(
        "course-prerequisite.repository", CoursePrerequisiteRepository.class
    );
  }

  @Bean
  public CourseRepository courseRepository() {
    return createService("course.repository", CourseRepository.class);
  }

  @Bean
  public CourseSectionRepository courseSectionRepository() {
    return createService(
        "course-section.repository", CourseSectionRepository.class
    );
  }

  @Bean
  public DepartmentRepository departmentepository() {
    return createService("department.repository", DepartmentRepository.class);
  }

  @Bean
  public MeetingDayRepository meetingDayRepository() {
    return createService("meeting-day.repository", MeetingDayRepository.class);
  }

  @Bean
  public MeetingTimeRepository meetingTimeRepository() {
    return createService("meeting-time.repository", MeetingTimeRepository.class);
  }

  @Bean
  public ProfessorRepository professorRepository() {
    return createService("professor.repository", ProfessorRepository.class);
  }

  @Bean
  public RoomRepository roomRepository() {
    return createService("room.repository", RoomRepository.class);
  }

  @Bean
  public TermRepository termRepository() {
    return createService("term.repository", TermRepository.class);
  }

  @SuppressWarnings("unchecked")
  protected <T> T createService(String endPoint, Class<T> serviceInterface) {
    HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();

    String serverUrl = String.format(
        "https://ec2-54-152-123-197.compute-1.amazonaws.com:8443/coursepresso/%s",
        endPoint
    );
    factory.setServiceUrl(serverUrl);
    factory.setServiceInterface(serviceInterface);
    factory.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
    factory.afterPropertiesSet();

    return (T) factory.getObject();
  }

  @Bean
  public HttpInvokerRequestExecutor httpInvokerRequestExecutor() {
    return new HttpComponentsHttpInvokerRequestExecutor();
  }
}
