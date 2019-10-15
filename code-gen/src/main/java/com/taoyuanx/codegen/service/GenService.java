package com.taoyuanx.codegen.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.domain.TableSchema;
import com.taoyuanx.codegen.enums.ConfigType;
import com.taoyuanx.codegen.model.PageResult;
import com.taoyuanx.codegen.model.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dushitaoyuan
 * @date 2019/9/2821:31
 */
@Service
public class GenService {
    @Autowired
    GenDao genDao;

    public PageResult<TableComment> listAllTable(String tableSchema, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TableComment> tableComments = genDao.listTables(tableSchema);
        return PageResult.change((Page) tableComments);
    }


    public PageResult<TableSchema> listSchema(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TableSchema> tableSchemaIPage = genDao.listAllTableSchema();
        return PageResult.change((Page) tableSchemaIPage);
    }

    public void saveConfig(GenConfig genConfig) {
        genDao.saveOrUpdateGenConfig(genConfig);
    }

    public PageResult<GenConfig> globalConfig(String tableSchema, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        String configKey=tableSchema+"."+ ConfigType.GLOBAL_CONFIG.key+"."+"%";
        Integer globalConfigType=ConfigType.GLOBAL_CONFIG.code;
        List<GenConfig> genConfigList = genDao.getAllConfig(configKey,globalConfigType);
        return PageResult.change((Page) genConfigList);
    }

    public void deleteConfig(String key) {
        genDao.delete(key);
    }
}
