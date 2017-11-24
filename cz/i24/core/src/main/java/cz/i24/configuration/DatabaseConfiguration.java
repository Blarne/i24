/*
 * Copyright (c) 2017 Slovak Telekom a.s.
 *
 * Slovak Telekom is not responsible for defects arising from
 * unauthorized changes to the source code.
 */
package cz.i24.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * {@link Configuration} for DataSource.
 *
 * @author <a href="miroslav.svoboda.external@telekom.sk">Miroslav Svoboda</a>
 * @since 1.0, 5. 4. 2017 22:29:09
 */
@Configuration
@EnableJpaRepositories(
        // entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = { "cz.i24.repository" })
@EnableTransactionManagement
public class DatabaseConfiguration  {

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("dataSourceConfig") HikariConfig configuration) throws NamingException {
        return new HikariDataSource(configuration);
    }

    @Primary
    @Bean(name = "dataSourceConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig dataSourceConfig() {
        return new HikariConfig();
    }

    // @Primary
    // @Bean(name = "entityManagerFactory")
    // // @DependsOn(value = "i24Liquibase")
    // public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
    // @Qualifier("dataSource") DataSource dataSource) {
    // return builder
    // .dataSource(dataSource).packages("cz.i24.entity").persistenceUnit("i24Unit")
    // .build();
    // }
    //
    // @Primary
    // @Bean(name = "transactionManager")
    // public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory
    // entityManagerFactory) {
    // return new JpaTransactionManager(entityManagerFactory);
    // }
    //
    // @Bean(name = "i24LiquibaseProperties")
    // @ConfigurationProperties(prefix = "liquibase")
    // public LiquibaseProperties i24LiquibaseProperties() {
    // return new LiquibaseProperties();
    // }
    //
    // @Bean(name = "i24Liquibase")
    // public SpringLiquibase i24Liquibase(@Qualifier("dataSource") DataSource dataSource,
    // @Qualifier("i24LiquibaseProperties") LiquibaseProperties liquibaseProperties) {
    // SpringLiquibase liquibase = new SpringLiquibase();
    // liquibase.setDataSource(dataSource);
    // liquibase.setChangeLog(liquibaseProperties.getChangeLog());
    // liquibase.setContexts(liquibaseProperties.getContexts());
    // liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
    // liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    // liquibase.setShouldRun(liquibaseProperties.isEnabled());
    // liquibase.setLabels(liquibaseProperties.getLabels());
    // liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    // return liquibase;
    // }

}
