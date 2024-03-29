package com.taoyuanx.codegen.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/10/53:27
 * @desc: 代码生成配置
 */
@Configuration
@ConfigurationProperties("code.gen")
@Data
public class CodeGenProperties  {
    private String tablePrefix;
    private String moduleName;
    private String parentPackageName;
    private String packageName;

    private String templateDir;
    private String templateOutDir;


    /**
     * 实体,dto,mapper,service,serviceImpl,controller 生成相关配置
     */

    private String entitySuffix = "DO";
    private String entityPackage = "DO";


    private String dtoSuffix = "DTO";
    private String dtoPackage = "DTO";

    private String mapperSuffix = "Dao";
    private String mapperPackage = "dao";


    private String mapperXmlSuffix = "Dao";
    private String mapperXmlPackage = "src/main/resources/mapper";

    private String serviceSuffix = "Service";
    private String servicePackage = "service";


    private String serviceImplSuffix = "ServiceImpl";
    private String serviceImplPackage = "service.impl";

    private String controllerPackage = "controller";

    private String controllerSuffix = "Controller";


    //类型映射 columnType-javaType
    private Map<String,String> typeMapping;
    //类型映射 columnType-jdbcType
    private Map<String,String> jdbcMapping;


}
