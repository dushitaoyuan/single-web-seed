package com.taoyuanx.codegen.generate.type;

/**
 * @author dushitaoyuan
 * @date 2019/10/1921:38
 * @desc: 类型转换 实现 columnType 到 javaTye 到mybatis jdbcType的转换
 */
public interface TypeHander {
    //columnType 到 javaTye
    String columnTypeToJavaType(String columnType);

    //columnType - jdbcType
    String columnTypeToJdbcType(String columnType);

    //是否为blob类型
    boolean isBlob(String columnType);

    //去除列类型中括号并转大写
    default String stripToUpper(String columnType) {
        int index = columnType.indexOf("(");
        //去除括号
        if (index > 0) {
            columnType = columnType.substring(0, index);
        }
        return columnType.toUpperCase();
    }

    default String toFullJavaType(String javaType) {
        if(javaType.indexOf(".")>0){
            return  javaType;
        }else {
            return  "java.lang."+javaType;
        }
    }


}
