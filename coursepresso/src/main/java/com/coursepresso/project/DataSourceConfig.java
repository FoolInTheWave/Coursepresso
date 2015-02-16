package com.coursepresso.project;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Caleb Miller
 */
@Configuration
public class DataSourceConfig {
  
  @Bean
  public EntityManagerFactory entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = 
        new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.coursepresso.project.entity");
    factory.setDataSource(dataSource());
    factory.setJpaProperties(hibernateProperties());
    factory.afterPropertiesSet();

    return factory.getObject();
  }

  @Bean
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://160.153.57.72:3306/coursepresso?zeroDateTimeBehavior=convertToNull");
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

  final Properties hibernateProperties() {
    final Properties hibernateProperties = new Properties();

    hibernateProperties.setProperty(
        "hibernate.dialect",
        "org.hibernate.dialect.MySQL5Dialect"
    );

    return hibernateProperties;
  }
}
