package com.taoyuanx.codegen.handlers;

import com.taoyuanx.codegen.model.TableInfo;

/**
 * @author dushitaoyuan
 * @date 2019/9/2821:40
 * @desc: 表结构处理
 */
public interface ITableHandler {
    /**
     * 表解析
     *
     * @param tableSchema
     * @param tableName
     * @return
     */
    TableInfo explain(String tableSchema, String tableName);
}
