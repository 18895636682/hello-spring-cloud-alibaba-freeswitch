package com.zhuzhi.spring.cloud.alibaba.freeswitch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author zhuz
 * @date 2020/3/27 22:53
 */
@Configuration
@MapperScan(basePackages = "com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper", sqlSessionFactoryRef = "primarySqlSessionFactory")
/*@PropertySource("classpath:config/datasource.properties")*/
public class CeDataSourceConfig {
    @Value("${spring.datasource.callengine.url}")
    private String url;

    @Value("${spring.datasource.callengine.driver-class-name}")
    private String driver_class_name;

    @Value("${spring.datasource.callengine.type}")
    private String type;

    @Value("${spring.datasource.callengine.username}")
    private String username;

    @Value("${spring.datasource.callengine.password}")
    private String password;

    @Value("${spring.datasource.callengine.hikari.minimum-idle}")
    private Integer minimum_idle;

    @Value("${spring.datasource.callengine.hikari.idle-timeout}")
    private Integer idle_timeout;

    @Value("${spring.datasource.callengine.hikari.maximum-pool-size}")
    private Integer maximum_pool_size;

    @Value("${spring.datasource.callengine.hikari.auto-commit}")
    private Boolean auto_commit;

    @Value("${spring.datasource.callengine.hikari.pool-name}")
    private String pool_name;

    @Value("${spring.datasource.callengine.hikari.max-lifetime}")
    private Integer max_lifetime;

    @Value("${spring.datasource.callengine.hikari.connection-timeout}")
    private Integer connection_timeout;

    @Value("${spring.datasource.callengine.hikari.connection-test-query}")
    private String connection_test_query;

    /*spring.datasource.callengine.url=jdbc:mysql://192.168.1.65:13306/callengine?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
    spring.datasource.callengine.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.callengine.type=com.zaxxer.hikari.HikariDataSource
    spring.datasource.callengine.username=root
    spring.datasource.callengine.password=cs2019
    spring.datasource.callengine.hikari.minimum-idle=5
    spring.datasource.callengine.hikari.idle-timeout=600000
    spring.datasource.callengine.hikari.maximum-pool-size=10
    spring.datasource.callengine.hikari.auto-commit=true
    spring.datasource.callengine.hikari.pool-name=MyHikariCPFS
    spring.datasource.callengine.hikari.max-lifetime=1800000
    spring.datasource.callengine.hikari.connection-timeout=30000
    spring.datasource.callengine.hikari.connection-test-query=SELECT 1*/

    @Bean(name = "primaryDataSource")
    @Primary
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driver_class_name);
        hikariDataSource.setMinimumIdle(minimum_idle);
        hikariDataSource.setIdleTimeout(idle_timeout);
        hikariDataSource.setMaximumPoolSize(maximum_pool_size);
        hikariDataSource.setAutoCommit(auto_commit);
        hikariDataSource.setPoolName(pool_name);
        hikariDataSource.setMaxLifetime(max_lifetime);
        hikariDataSource.setConnectionTimeout(connection_timeout);
        hikariDataSource.setConnectionTestQuery(connection_test_query);
        return hikariDataSource;
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return sessionFactory.getObject();
    }

}