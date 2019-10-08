package com.taoyuanx.codegen.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.beans.Transient;

/**
 * @author dushitaoyuan
 * @date 2019/10/50:56
 * @desc: 实体属性
 */
@Data
public class EntityField {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 实体属性
     */
    private String fieldName;
    /**
     * 实体属性注释
     */
    private String  fieldComment;
    /**
     * java类型
     */
    private String javaType;

    /**
     * 列类型
     */
    private String columnType;
    @JsonIgnore
    private Class javaClass;

    @JsonIgnore
    private String fullJavaType;
    /**
     * 是否主键
     */
    private Boolean isKey;
}
