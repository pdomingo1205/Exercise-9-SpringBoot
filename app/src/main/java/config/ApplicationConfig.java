package config;

import javax.sql.DataSource;
import javax.persistence.*;
import java.util.Properties;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Configuration
@EnableTransactionManagement
class ApplicationConfig {
	@Autowired
	DataSource dataSource;
	Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(true);

	 	LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	 	factory.setDataSource(dataSource);
	 	factory.setPackagesToScan(new String[] {"models.entities"});
	 	factory.setJpaVendorAdapter(vendorAdapter);

        logger.info("Created entity manager factory");

	 	return factory;
	 }

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager(entityManagerFactory().getObject());
		logger.info("Created transaction Manager");
		return tm;
	 }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {

		return new PersistenceExceptionTranslationPostProcessor();
	 }

}
