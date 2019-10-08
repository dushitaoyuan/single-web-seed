package com.taoyuanx.codegen.dao;

import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.codegen.domain.TableColumn;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.domain.TableSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dushitaoyuan
 * @date 2019/9/2820:51
 * @desc: 数据库查询dao
 */
public interface GenDao {
    /**
     * 查询所有表及其注释
     *
     * @param tableSchema
     * @return
     */
    List<TableComment> listTables(@Param("tableSchema") String tableSchema);

    /**
     * 查询表的所有列信息
     *
     * @param table_schema
     * @param tableName
     * @return
     */
    List<TableColumn> listColumn(@Param("tableSchema") String table_schema, @Param("tableName") String tableName);

    /**
     * @param tableSchema
     * @param tableName
     * @return
     */
    TableComment getTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    List<TableSchema> listAllTableSchema();

    void saveOrUpdateGenConfig(GenConfig genConfig);

    GenConfig getByKey(@Param("configKey")String configKey,@Param("configType")Integer configType);
}
