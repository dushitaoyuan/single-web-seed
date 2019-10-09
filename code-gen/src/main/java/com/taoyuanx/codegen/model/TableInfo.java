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
     * tableName 表名
     * entityName 对应实体名
     * tableComment 表注释
     * entityComment 实体注释(可在界面上修改)
     */
    private String tableName;
    private String entityName;

    private String tableComment;
    private String entityComment;
    /**
     * packageName 项目包名,依赖packageName生成 其他包名
     * moduleName 模块名  parentPackageName 父包名 组装packageName
     */
    private String packageName;
    private String moduleName;
    private String parentPackageName;
    /**
     * 字段信息
     */
    private List<EntityField> entityFields;

    /**
     * 主键
     */
    private EntityField  priKey;
    @JsonIgnore
    /**
     * 需导入的类
     */
    private  List<String> imports;

}
