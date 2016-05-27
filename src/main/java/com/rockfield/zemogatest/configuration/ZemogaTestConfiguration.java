package com.rockfield.zemogatest.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.rockfield.zemogatest")
public class ZemogaTestConfiguration {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties props = new Properties();
        props.setProperty("user", "zemoga_test_db");
        props.setProperty("password", "Zem0ga.101");
        dataSource.setConnectionProperties(props);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://zemoga-test-db.crhpedy9xxto.us-east-1.rds.amazonaws.com:3306/zemoga_test_db?serverTimezone=UTC&useSSL=false");
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        DataSource dataSource = dataSource();

        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        //  TODO: Do we need to scan this, since it's done as part of @ComponentScan?
        bean.setPackagesToScan(new String[]{"com.rockfield.zemogatest"});

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        bean.setHibernateProperties(props);

        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        SessionFactory sf = sessionFactory().getObject();
        HibernateTransactionManager bean = new HibernateTransactionManager();
        bean.setSessionFactory(sf);
        return bean;
    }
}
