package com.taoyuanx.codegen.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
@MapperScan("com.taoyuanx.codegen.dao")
public class MybatisPlusConfig {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;


    @PostConstruct
    public void addPageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("helperDialect", "mysql");
        properties.put("reasonable", "false");
        properties.put("supportMethodsArguments", "true");
        properties.put("params", "count=countSql");
        properties.putAll(properties);
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}