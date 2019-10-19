<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperFullName}">
    <resultMap id="BaseResultMap"
               type="${entityFullName}">
        <#list table.entityFields as field>
            <#if field.isKey??>
                <id column="${field.columnName}" property="${field.fieldName}"/>
            <#else>
                    <result column="${field.columnName}" property="${field.fieldName}" ${resolve(field.javaType,field.jdbcType,'javaType="%s" jdbcType="%s"')}/>
            </#if>
        </#list>
    </resultMap>
</mapper>