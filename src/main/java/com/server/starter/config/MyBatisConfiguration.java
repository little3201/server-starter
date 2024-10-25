package com.server.starter.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Mybatis configuration
 *
 * @author wq li
 */
@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(basePackages = "com.server.starter.**.repository")
@Import(MyBatisJdbcConfiguration.class)
@MapperScan(basePackages = "com.server.starter.**.mapper")
public class MyBatisConfiguration {

    @Bean
    SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        // Configure MyBatis here
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
        );
        return factoryBean;
    }

    /**
     * 创建 SqlSessionTemplate，线程安全的 SqlSession
     *
     * @param sqlSessionFactory SqlSession Factory
     * @return SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
