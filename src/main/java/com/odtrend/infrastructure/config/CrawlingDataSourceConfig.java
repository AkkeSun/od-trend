package com.odtrend.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.odtrend.adapter.out.persistence.crawling",
    entityManagerFactoryRef = "crawlingEntityManagerFactory",
    transactionManagerRef = "crawlingTransactionManager"
)
public class CrawlingDataSourceConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.crawling")
    public DataSourceProperties crawlingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource crawlingDataSource(
        @Qualifier("crawlingDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        return dataSourceProperties
            .initializeDataSourceBuilder()
            .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean crawlingEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("crawlingDataSource") DataSource dataSource
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", ddlAuto);
        props.put("hibernate.physical_naming_strategy",
            "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        return builder
            .dataSource(dataSource)
            .packages("com.odtrend.adapter.out.persistence.crawling")
            .persistenceUnit("crawlingEntityManager")
            .properties(props)
            .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager crawlingTransactionManager(
        @Qualifier("crawlingEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}