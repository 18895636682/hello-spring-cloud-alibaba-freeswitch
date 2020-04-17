package com.zhuzhi.spring.cloud.alibaba.freeswitch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author tianl
 * @date 2020/3/27 22:53
 */
@Configuration
@MapperScan(basePackages = {"com.zhuzhi.spring.cloud.alibaba.freeswitch.mapperfs"}, sqlSessionFactoryRef = "secondSqlSessionFactory")
/*@PropertySource("classpath:config/datasource.properties")*/
public class FsDataSourceConfig {
    @Value("${spring.datasource.freeswitch.url}")
    private String url;

    @Value("${spring.datasource.freeswitch.driver-class-name}")
    private String driver_class_name;

    @Value("${spring.datasource.freeswitch.type}")
    private String type;

    @Value("${spring.datasource.freeswitch.username}")
    private String username;

    @Value("${spring.datasource.freeswitch.password}")
    private String password;

    @Value("${spring.datasource.freeswitch.hikari.minimum-idle}")
    private Integer minimum_idle;

    @Value("${spring.datasource.freeswitch.hikari.idle-timeout}")
    private Integer idle_timeout;

    @Value("${spring.datasource.freeswitch.hikari.maximum-pool-size}")
    private Integer maximum_pool_size;

    @Value("${spring.datasource.freeswitch.hikari.auto-commit}")
    private Boolean auto_commit;

    @Value("${spring.datasource.freeswitch.hikari.pool-name}")
    private String pool_name;

    @Value("${spring.datasource.freeswitch.hikari.max-lifetime}")
    private Integer max_lifetime;

    @Value("${spring.datasource.freeswitch.hikari.connection-timeout}")
    private Integer connection_timeout;

    @Value("${spring.datasource.freeswitch.hikari.connection-test-query}")
    private String connection_test_query;

    /*spring.datasource.freeswitch.url=jdbc:mysql://192.168.1.65:13306/freeswitch?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
    spring.datasource.freeswitch.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.freeswitch.type=com.zaxxer.hikari.HikariDataSource
    spring.datasource.freeswitch.username=root
    spring.datasource.freeswitch.password=cs2019
    spring.datasource.freeswitch.hikari.minimum-idle=5
    spring.datasource.freeswitch.hikari.idle-timeout=600000
    spring.datasource.freeswitch.hikari.maximum-pool-size=10
    spring.datasource.freeswitch.hikari.auto-commit=true
    spring.datasource.freeswitch.hikari.pool-name=MyHikariCPFS
    spring.datasource.freeswitch.hikari.max-lifetime=1800000
    spring.datasource.freeswitch.hikari.connection-timeout=30000
    spring.datasource.freeswitch.hikari.connection-test-query=SELECT 1*/

    @Bean(name = "secondDataSource")
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

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapperfs/*Mapper.xml"));
        return sessionFactory.getObject();
    }

}