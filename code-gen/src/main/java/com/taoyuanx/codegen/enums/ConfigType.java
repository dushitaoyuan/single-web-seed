package com.taoyuanx.codegen.enums;

/**
 * @author dushitaoyuan
 * @date 2019/10/423:47
 * @desc: 配置类型 前后端需保持一致
 */
public enum ConfigType {
    ENTITYNAME(0, "实体名称","entityName"),
    PACKAGENAME(1, "包名","packageName"),

    FIELDNAME(2, "实体属性名","fieldName"),
    JAVATYPE(3, "实体java类型","javaType"),
    FIELDCOMMENT(4, "实体属性注释","fieldComment"),
    ENTITYCOMMENT(5, "实体注释","entityComment"),

    MODULENAME(6, "模块名","moduleName"),
    PARENTPACKAGENAME(7, "父包名","parentPackageName"),

    GLOBAL_CONFIG(8, "全局配置","global.columnName");

    public int code;
    public String desc;
    public String key;

    private ConfigType(int code, String desc,String key) {
        this.code = code;
        this.desc = desc;
        this.key=key;
    }
}
