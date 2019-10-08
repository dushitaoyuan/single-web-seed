package com.taoyuanx.codegen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author dushitaoyuan
 * @date 2019/9/2820:52
 * @desc: 表信息结果
 */
@Data
public class TableInfo {
    /**
     * 代码生成的信息
     */
    private String tableName;
    private String entityName;
    private String tableComment;
    private String entityComment;
    private String packageName;
    private String moduleName;
    private String parentPackageName;
    /**
     * 字段信息
     */
    private List<EntityField> entityFields;
    @JsonIgnore
    /**
     * 导入
     */
    private  List<String> imports;

}
