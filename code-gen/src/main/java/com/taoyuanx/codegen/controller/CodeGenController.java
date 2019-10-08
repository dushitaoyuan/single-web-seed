package com.taoyuanx.codegen.controller;

import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.domain.TableSchema;
import com.taoyuanx.codegen.exception.ServiceException;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.PageResult;
import com.taoyuanx.codegen.model.TableInfo;
import com.taoyuanx.codegen.service.GenService;
import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import com.taoyuanx.commons.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dushitaoyuan
 * @date 2019/9/2822:10
 * @desc: 代码生成controller
 */
@Controller
@RequestMapping("gen")
public class CodeGenController {
    @Autowired
    ITableHandler tableHandler;
    @Autowired
    GenService genService;


    @GetMapping("table")
    @ResponseBody
    public TableInfo explainTable(@RequestParam("tableName") String tableName, @RequestParam(required = false) String db) {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        return tableHandler.explain(tableSchema, tableName);
    }

    @GetMapping("gen")
    public void gen(@RequestParam("tableName") String tableName, @RequestParam(required = false) String db) {

    }

    @PostMapping("config")
    @ResponseBody
    public Result saveTableConfig(GenConfig genConfig) {
         genService.saveConfig(genConfig);
         return ResultBuilder.success();
    }

    @GetMapping("listTable")
    @ResponseBody
    public PageResult<TableComment> listTable(@RequestParam(required = false) String db,
    @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false,defaultValue = "1")Integer pageNum) {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        return genService.listAllTable(tableSchema,pageSize,pageNum);
    }


    @GetMapping("listSchema")
    @ResponseBody
    public PageResult<TableSchema> listSchema(@RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false,defaultValue = "1")Integer pageNum) {
        return genService.listSchema(pageSize, pageNum);
    }
}
