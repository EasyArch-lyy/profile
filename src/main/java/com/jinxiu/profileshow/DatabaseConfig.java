package com.jinxiu.profileshow;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.apache.commons.lang.ArrayUtils;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@MapperScan(basePackages = {
        "com.jinxiu.profileshow.dao"
},
        sqlSessionFactoryRef = "profileFactory")
public class DatabaseConfig {

    private static final List<String> mappers =
            Arrays.asList(
                    "classpath:com/jinxiu/profileshow/mapper/**/*Mapper.xml"
            );

    @Bean(name = "profileDatabase")
    @ConfigurationProperties(prefix = "profile.datasource.connection")
    @Primary
    public HikariDataSource database() {

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "profileFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("profileDatabase") final DataSource dataSource) throws Exception {

        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        for (String mapper : DatabaseConfig.mappers) {
            resources = (Resource[]) ArrayUtils.addAll(resources, resolver.getResources(mapper));
        }
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean.getObject();
    }

}
