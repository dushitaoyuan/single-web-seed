package com.taoyuanx.codegen.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

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
        return toHumpName(tableName, true);

    }

    public static String toHumpName(String name, boolean firstUpper) {
        name = name.toLowerCase();
        char[] chars = name.toCharArray();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            if (firstUpper && i == 0 && Character.isLowerCase(temp)) {
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

        typeMap.put("NUMERIC", BigDecimal.class);
        typeMap.put("NCLOB", String.class);
        typeMap.put("OTHER", Object.class);
        /**
         * 日期可自行配置为 LocalXXXX
         */
        typeMap.put("DATE", Date.class);
        typeMap.put("DATETIME", Date.class);
        typeMap.put("TIMESTAMP", Date.class);
        typeMap.put("TIME", Date.class);
    }

    /**
     * jdbc类型转换为javaType
     *
     * @param columnType
     * @return
     */
    public static Class type(String columnType) {
        int index = columnType.indexOf("(");
        if (index > 0) {
            columnType = columnType.substring(0, index);
        }
        return typeMap.get(columnType.toUpperCase());
    }

    public static String jdbcType(String columnType) {
        int index = columnType.indexOf("(");
        //去除括号
        if (index > 0) {
            columnType = columnType.substring(0, index);
        }
        columnType=columnType.toUpperCase();
        if (typeMap.containsKey(columnType)) {
            return columnType;
        }
        return null;

    }

    public static final Set<String> excludes = new HashSet<>();

    static {
        excludes.add(".java");
        excludes.add(".xml");

    }

    public static String changePackageToFilePath(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            return null;
        }
        String finalPackageName = packageName;
        Optional<String> first = excludes.stream().filter(exclude -> {
            return finalPackageName.endsWith(exclude);
        }).findFirst();
        if (first.isPresent()) {
            return packageName = packageName.substring(0, packageName.lastIndexOf(first.get())).replace(".", File.separator) + first.get();
        }
        return packageName.replace(".", File.separator);

    }


    public  static String unCapFirst(String str){
        return  str.substring(0,1).toLowerCase()+str.substring(1);
    }
    public  static String capFirst(String str){
        return  str.substring(0,1).toUpperCase()+str.substring(1);
    }
    public static void forceMakeDirs(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static void main(String[] args) {
        System.out.println(unCapFirst("User"));
    }

}
