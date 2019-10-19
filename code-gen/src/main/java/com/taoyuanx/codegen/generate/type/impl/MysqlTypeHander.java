package com.taoyuanx.codegen.generate.type.impl;

import cn.hutool.core.map.MapUtil;
import com.taoyuanx.codegen.generate.type.TypeHander;
import com.vip.vjtools.vjkit.collection.CollectionUtil;

import java.sql.Struct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dushitaoyuan
 * @date 2019/10/1823:03
 * @desc: mysql类型处理
 */
public class MysqlTypeHander extends AbstractTypeHanler implements TypeHander {


    public MysqlTypeHander(Map<String, String> typeMapping, Map<String, String> jdbcMapping) {
        initMapping(typeMapping, jdbcMapping);
    }

}
