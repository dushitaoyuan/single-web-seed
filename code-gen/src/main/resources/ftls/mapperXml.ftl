<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperFullName}">
    <resultMap id="BaseResultMap"
               type="${entityFullName}">
        <#list table.entityFields as field>
            <#if field.isKey??>
                <id column="${field.columnName}" <#if field.jdbcType??>jdbcType="${field.jdbcType}"</#if> <#if field.javaType??>javaType="${field.fullJavaType}"</#if> property="${field.fieldName}"/>
            <#else>
                <result column="${field.columnName}" <#if field.jdbcType??>jdbcType="${field.jdbcType}"</#if> <#if field.javaType??>javaType="${field.fullJavaType}"</#if> property="${field.fieldName}"/>
            </#if>
        </#list>
    </resultMap>
</mapper>