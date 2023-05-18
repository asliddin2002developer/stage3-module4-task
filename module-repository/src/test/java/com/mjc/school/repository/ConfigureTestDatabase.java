package com.mjc.school.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@ComponentScan(value = "com.mjc.school.repository")
@EnableTransactionManagement
public class ConfigureTestDatabase {

    @Bean
    public DriverManagerDataSource dataSource(){
        var dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/newsdb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
        var adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");

        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            final DataSource dataSource,
            final JpaVendorAdapter jpaVendorAdapter
    ) {
         var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

         entityManagerFactoryBean.setDataSource(dataSource);
         entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
         entityManagerFactoryBean.setPackagesToScan("com.mjc.school.repository");

         return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
