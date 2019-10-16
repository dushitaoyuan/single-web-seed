package com.taoyuanx.codegen.handlers.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.Joiner;
import com.taoyuanx.codegen.config.CodeGenProperties;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.codegen.domain.TableColumn;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.enums.ConfigType;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.EntityField;
import com.taoyuanx.codegen.model.TableInfo;
import com.taoyuanx.codegen.utils.GenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dushitaoyuan
 * @date 2019/9/2821:44
 * @desc: mysql 表结构解析
 */
@Component
@Slf4j
public class MysqlTableHandler implements ITableHandler {
    @Autowired
    GenDao genDao;
    

    @Autowired
    CodeGenProperties codeGenProperties;
    @Autowired
    @Qualifier("genConfigCache")
    LoadingCache<String, GenConfig> genConfigCache;
    @Override
    public TableInfo explain(String tableSchema, String tableName) {
        Joiner joiner = Joiner.on(".");
        TableComment table = genDao.getTable(tableSchema, tableName);
        String keyPrefix = tableSchema + "." + tableName;
        List<TableColumn> tableColumns = genDao.listColumn(tableSchema, tableName);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(table.getTableName());
        tableInfo.setTableComment(table.getTableCommnet());
        String entityName = explainName(ConfigType.ENTITYNAME, joiner.join(new String[]{keyPrefix, ConfigType.ENTITYNAME.key}),
                table.getTableName(), tableSchema, tableName);
        tableInfo.setEntityName(entityName);
        String moduleName = explainName(ConfigType.MODULENAME, joiner.join(new String[]{keyPrefix, ConfigType.MODULENAME.key}),
                codeGenProperties.getModuleName(), tableSchema, tableName);
        tableInfo.setModuleName(moduleName);
        String parentPackageName = explainName(ConfigType.PARENTPACKAGENAME, joiner.join(new String[]{keyPrefix, ConfigType.PARENTPACKAGENAME.key}),
                codeGenProperties.getParentPackageName(), tableSchema, tableName);
        tableInfo.setParentPackageName(parentPackageName);
        String packageName = explainName(ConfigType.PACKAGENAME, joiner.join(new String[]{keyPrefix, ConfigType.PACKAGENAME.key}),
                codeGenProperties.getPackageName(), tableSchema, tableName);
        tableInfo.setPackageName(packageName);
        String entityComment = explainName(ConfigType.ENTITYCOMMENT, joiner.join(new String[]{keyPrefix, ConfigType.ENTITYCOMMENT.key}),
                table.getTableCommnet(), tableSchema, tableName);
        tableInfo.setEntityComment(entityComment);
        if (CollectionUtil.isNotEmpty(tableColumns)) {
            String globalConfigKeyLike=tableSchema+"."+ConfigType.GLOBAL_CONFIG.key+"%";
            List<GenConfig> allGlobalConfig = genDao.getAllConfig(globalConfigKeyLike, ConfigType.GLOBAL_CONFIG.code);
            boolean hasGlobal= CollectionUtil.isNotEmpty(allGlobalConfig);
            Map<String, List<GenConfig>> globalConfigMap = null;
            if(hasGlobal){
                globalConfigMap=allGlobalConfig.stream().collect(Collectors.groupingBy(GenConfig::getConfigKey));
            }
            List<EntityField> fieldList = new ArrayList<>(tableColumns.size());
            ListIterator<TableColumn> tableColumnListIterator = tableColumns.listIterator();
            while (tableColumnListIterator.hasNext()) {
                TableColumn tableColumn = tableColumnListIterator.next();
                EntityField field = new EntityField();
                field.setColumnName(tableColumn.getColumnName());
                String fieldName = null;

                if(hasGlobal){
                    String globalColumnNameKey=joiner.join(new String[]{tableSchema,ConfigType.GLOBAL_CONFIG.key, tableColumn.getColumnName()});
                    GenConfig globalColumnNameConfig = getGlobalConfig(globalColumnNameKey, globalConfigMap);
                    if(Objects.nonNull(globalColumnNameConfig)){
                        fieldName=globalColumnNameConfig.getConfigValue();
                    }else {
                        fieldName = explainName(ConfigType.FIELDNAME, joiner.join(new String[]{keyPrefix, tableColumn.getColumnName()}),
                                tableColumn.getColumnName(), tableSchema, tableName);
                    }
                }else {
                    fieldName = explainName(ConfigType.FIELDNAME, joiner.join(new String[]{keyPrefix, tableColumn.getColumnName()}),
                            tableColumn.getColumnName(), tableSchema, tableName);
                }
                field.setFieldName(fieldName);
                field.setColumnType(tableColumn.getColumnType());
                Class type = GenUtil.type(tableColumn.getColumnType());
                String javaType = explainName(ConfigType.JAVATYPE, joiner.join(new String[]{keyPrefix, tableColumn.getColumnName(), ConfigType.JAVATYPE.key}),
                        type.getSimpleName(), tableSchema, tableName);
                if (javaType.indexOf(".") > 0) {
                    try {
                        type = Class.forName(javaType);
                    } catch (ClassNotFoundException e) {
                        log.error("javaType not right", e);
                    }
                }
                String fullJavaType = type.getName();
                field.setJavaType(type.getSimpleName());
                field.setFullJavaType(fullJavaType);
                field.setJavaClass(type);
                field.setJdbcType(GenUtil.jdbcType(tableColumn.getColumnType()));
                autoImport(fullJavaType, tableInfo);
                String fieldComment = explainName(ConfigType.FIELDCOMMENT, joiner.join(new String[]{keyPrefix, tableColumn.getColumnName(), ConfigType.FIELDCOMMENT.key}),
                        tableColumn.getColumnComment(), tableSchema, tableName);
                field.setFieldComment(fieldComment);
                if (!StringUtils.isEmpty(tableColumn.getColumnKey())) {
                    field.setIsKey(true);
                    tableInfo.setPriKey(field);
                }
                fieldList.add(field);
            }
            tableInfo.setEntityFields(fieldList);
        }
        return tableInfo;
    }

    private String explainName(ConfigType configType, String key, String value, String tableSchema, String tableName) {
        Optional<GenConfig> tableConfigValue = Optional.ofNullable(genConfigCache.get(key));
        if (tableConfigValue.isPresent()) {
            return tableConfigValue.get().getConfigValue();
        }
        switch (configType) {
            case ENTITYNAME:
                return GenUtil.toEntityName(codeGenProperties.getTablePrefix(), value);
            case MODULENAME:
                return codeGenProperties.getModuleName();
            case PARENTPACKAGENAME:
                return codeGenProperties.getParentPackageName();
            case PACKAGENAME:
                if (StringUtils.isEmpty(codeGenProperties.getPackageName())) {
                    return codeGenProperties.getParentPackageName() + "." + codeGenProperties.getModuleName();
                } else {
                    return codeGenProperties.getPackageName();
                }
            case FIELDNAME:
                return GenUtil.toHumpName(value, false);
            default:
                if(StringUtils.isEmpty(value)){
                    return null;
                }
                return value;
        }
    }


   

    private void autoImport(String fullType, TableInfo tableInfo) {
        if (StringUtils.hasText(fullType) && !fullType.startsWith("java.lang.")) {
            List<String> imports = tableInfo.getImports();
            if (Objects.isNull(imports)) {
                tableInfo.setImports(new ArrayList<>());
            }
            imports = tableInfo.getImports();
            if (!imports.contains(fullType)) {
                imports.add(fullType);
            }

        }
    }

    private  GenConfig getGlobalConfig(String globalColumnNameKey, Map<String, List<GenConfig>> globalConfigMap){
        List<GenConfig> genConfigList = globalConfigMap.get(globalColumnNameKey);
        if(CollectionUtil.isNotEmpty(genConfigList)){
            return  genConfigList.get(0);
        }
        return null;
    }
}
