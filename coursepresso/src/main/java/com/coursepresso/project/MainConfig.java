package com.coursepresso.project;

import com.coursepresso.project.controller.MainController;
import com.coursepresso.project.controller.NewCourseSectionController;

import java.util.Properties;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javafx.fxml.FXMLLoader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
public class MainConfig {

  public MainConfig() {
    super();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean entityManagerFactory
        = new LocalContainerEntityManagerFactoryBean();

    entityManagerFactory.setDataSource(dataSource());
    entityManagerFactory.setPackagesToScan(
        new String[]{"com.coursepresso.project.entity"}
    );

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
    entityManagerFactory.setJpaProperties(hibernateProperties());

    return entityManagerFactory;
  }

  @Bean
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://160.153.57.72:3306/coursepresso");
    dataSource.setUsername("crmiller");
    dataSource.setPassword("Mi11er64");

    return dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      final EntityManagerFactory entityManagerFactory
  ) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();

    transactionManager.setEntityManagerFactory(entityManagerFactory);

    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
  
  @Bean
  public MainController mainPresenter() {
    return loadPresenter("/fxml/Main.fxml");
  }

  @Bean
  public NewCourseSectionController newCourseSectionPresenter() {
    return loadPresenter("/fxml/NewCourseSection.fxml");
  }

  final Properties hibernateProperties() {
    final Properties hibernateProperties = new Properties();

    hibernateProperties.setProperty(
        "hibernate.dialect",
        "org.hibernate.dialect.MySQL5Dialect"
    );

    return hibernateProperties;
  }

  @SuppressWarnings("unchecked")
  private <T> T loadPresenter(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.load(getClass().getResourceAsStream(fxmlFile));
      return (T) loader.getController();
    } catch (IOException e) {
      throw new RuntimeException(
          String.format("Unable to load FXML file '%s'", fxmlFile), e
      );
    }
  }
}
