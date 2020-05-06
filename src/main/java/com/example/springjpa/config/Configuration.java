package com.example.springjpa.config;

import com.example.springjpa.model.DBConfigModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
public class Configuration {

    private HikariConfig hikariConfig = new HikariConfig();
    @Autowired
    @Qualifier("dbDevConfig")
    private DBConfigModel dbConfigModel;

    @Autowired
    private DataSource dataSource;
    @Profile("dev")
    @Bean
    public DataSource getDataSource(){
        hikariConfig.setDriverClassName( dbConfigModel.getDataSourceClassName() );
        hikariConfig.setJdbcUrl( dbConfigModel.getUrl() );
        hikariConfig.setUsername( dbConfigModel.getUsername() );
        hikariConfig.setPassword( dbConfigModel.getPassword() );
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        return new HikariDataSource( hikariConfig );
    }

    @Bean
    public LocalSessionFactoryBean getLocalSessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.example.springjpa");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.put("hibernate.format_sql","true");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        return localSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getLocalSessionFactoryBean().getObject());
        return transactionManager;
    }





}

