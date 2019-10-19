package com.taoyuanx.codegen.generate.type.impl;

import cn.hutool.core.map.MapUtil;
import com.taoyuanx.codegen.generate.type.TypeHander;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dushitaoyuan
 * @date 2019/10/1921:45
 * @desc: 抽象类型转换
 */
public abstract class AbstractTypeHanler implements TypeHander {

    //columnType到javaType 转换
    protected    Map<String, String> typeMappingHolder = new ConcurrentHashMap<>();
    //mybatis columnType到jdbcType 转换
    protected   Map<String, String> jdbcMappingHolder = new ConcurrentHashMap<>();
    public static final String BLOB_JAVA_TYPE = "byte[]";
    public static final String STRING_JAVA_TYPE = "String";

    public String columnTypeToJavaType(String columnType) {
        //处理blob
        if(isBlob(columnType)){
            return BLOB_JAVA_TYPE;
        }
        //处理text
        if(columnType.contains("TEXT")){
            return STRING_JAVA_TYPE;
        }
        return typeMappingHolder.get(columnType);
    }

    public String columnTypeToJdbcType(String columnType) {
        return jdbcMappingHolder.get(columnType);
    }

    public boolean isBlob(String columnType) {
        if (columnType.contains("BLOB")) {
            return true;
        }
        String javaType = typeMappingHolder.get(columnType);
        if (Objects.nonNull(javaType) && javaType.equals(BLOB_JAVA_TYPE)) {
            return true;
        }
        return false;
    }


    protected void initMapping(Map<String, String> typeMapping, Map<String, String> jdbcMapping) {
        //初始化列类型到java类型的映射
        //字符
        typeMappingHolder.put("CHAR", "String");
        typeMappingHolder.put("VARCHAR", "String");
        typeMappingHolder.put("LONGVARCHAR", "String");
        typeMappingHolder.put("TEXT", "String");
        //布尔
        typeMappingHolder.put("BIT", "Boolean");
        typeMappingHolder.put("BOOLEAN", "Boolean");
        //blob
        typeMappingHolder.put("BINARY", "byte[]");
        typeMappingHolder.put("VARBINARY", "byte[]");
        typeMappingHolder.put("LONGVARBINARY", "byte[]");
        typeMappingHolder.put("BLOB", "byte[]");
        //日期,自行覆盖转为localxxx
        typeMappingHolder.put("DATE", "java.util.Date");
        typeMappingHolder.put("TIME", "java.util.Date");
        typeMappingHolder.put("TIMESTAMP", "java.util.Date");
        typeMappingHolder.put("DATETIME", "java.util.Date");
        //数字
        typeMappingHolder.put("NUMERIC", "java.math.BigDecimal");
        typeMappingHolder.put("DECIMAL", "java.math.BigDecimal");
        typeMappingHolder.put("BIGINT", "Long");
        typeMappingHolder.put("REAL", "Float");
        typeMappingHolder.put("FLOAT", "Double");
        typeMappingHolder.put("DOUBLE", "Double");
        typeMappingHolder.put("SMALLINT", "Short");
        typeMappingHolder.put("INTEGER", "Interger");
        typeMappingHolder.put("TINYINT", "Byte");

        //初始化 columenType 到mybatis jdbcType 的转换
        jdbcMappingHolder.put("CHAR", "CHAR");
        jdbcMappingHolder.put("VARCHAR", "VARCHAR");
        jdbcMappingHolder.put("LONGVARCHAR", "VARCHAR");
        jdbcMappingHolder.put("NUMERIC", "NUMERIC");
        jdbcMappingHolder.put("DECIMAL", "DECIMAL");
        jdbcMappingHolder.put("BIT", "BIT");
        jdbcMappingHolder.put("BOOLEAN", "BOOLEAN");
        jdbcMappingHolder.put("TINYINT", "TINYINT");
        jdbcMappingHolder.put("SMALLINT", "SMALLINT");
        jdbcMappingHolder.put("INT", "INTEGER");
        jdbcMappingHolder.put("BIGINT", "BIGINT");
        jdbcMappingHolder.put("FLOAT", "FLOAT");
        jdbcMappingHolder.put("DATE", "DATE");
        jdbcMappingHolder.put("TIME", "TIME");
        jdbcMappingHolder.put("TIMESTAMP", "TIMESTAMP");
        jdbcMappingHolder.put("DATETIME", "TIMESTAMP");
        if (MapUtil.isNotEmpty(typeMapping)) {
            typeMappingHolder.putAll(typeMapping);
        }
        if (MapUtil.isNotEmpty(jdbcMapping)) {
            jdbcMappingHolder.putAll(jdbcMapping);

        }
    }




}
