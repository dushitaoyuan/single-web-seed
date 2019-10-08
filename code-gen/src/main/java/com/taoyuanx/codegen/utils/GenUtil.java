package com.taoyuanx.codegen.utils;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/10/50:30
 * @desc: 工具类
 */
public class GenUtil {
    public static String toEntityName(String tablePrefix, String tableName) {
        if (StringUtils.isNotEmpty(tablePrefix) && tableName.startsWith(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return toHumpName(tableName,true);

    }

    public static String toHumpName(String name,boolean firstUpper) {
        name = name.toLowerCase();
        char[] chars = name.toCharArray();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            if (firstUpper&&i == 0 && Character.isLowerCase(temp)) {
                buf.append(Character.toUpperCase(temp));
                continue;
            }
            if (temp == '_') {
                buf.append(Character.toUpperCase(chars[i + 1]));
                i++;
                continue;
            } else {
                buf.append(chars[i]);
            }
        }
        return buf.toString();
    }

    private static final Map<String, Class> typeMap = new HashMap<>();

    static {
        typeMap.put("LONGVARCHAR", String.class);
        typeMap.put("REAL", BigDecimal.class);
        typeMap.put("SMALLINT", Short.class);
        typeMap.put("TIMESTAMP", Date.class);
        typeMap.put("FLOAT", Float.class);
        typeMap.put("NCHAR", String.class);
        typeMap.put("DOUBLE", Double.class);
        typeMap.put("BOOLEAN", Boolean.class);
        typeMap.put("BIGINT", Long.class);
        typeMap.put("BOOLEAN", Boolean.class);
        typeMap.put("NVARCHAR", String.class);
        typeMap.put("CLOB", String.class);
        typeMap.put("BIT", Boolean.class);
        typeMap.put("LONGVARBINARY", Byte[].class);
        typeMap.put("INTEGER", Integer.class);
        typeMap.put("BLOB", Byte[].class);
        typeMap.put("TINYINT", Byte.class);
        typeMap.put("ARRAY", Object.class);
        typeMap.put("CHAR", String.class);
        typeMap.put("VARCHAR", String.class);
        typeMap.put("DECIMAL", BigDecimal.class);
        typeMap.put("TIME", Date.class);
        typeMap.put("NUMERIC", BigDecimal.class);
        typeMap.put("NCLOB", String.class);
        typeMap.put("OTHER", Object.class);
        typeMap.put("DATE", Date.class);
        typeMap.put("DATETIME", Date.class);
    }

    public static Class type(String columnType) {
        int index = columnType.indexOf("(");
        if(index>0){
            columnType=columnType.substring(0,index);
        }
        return typeMap.get(columnType.toUpperCase());
    }

    public static void main(String[] args) {
        System.out.println(toEntityName("t_", "t_user_account"));
    }
}
