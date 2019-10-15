package com.taoyuanx.codegen.controller;

import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.domain.TableSchema;
import com.taoyuanx.codegen.generate.CodeGenCommonService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
    @Autowired
    CodeGenCommonService codeGenCommonService;


    @GetMapping("table")
    @ResponseBody
    public TableInfo explainTable(@RequestParam("tableName") String tableName, @RequestParam(required = false) String db) {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        return tableHandler.explain(tableSchema, tableName);
    }

    @GetMapping("gen")
    public void gen(@RequestParam("tableName") String tableName, @RequestParam(required = false) String db,
                    HttpServletResponse response, HttpServletRequest request) throws Exception {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        File file = codeGenCommonService.generate(null, db, tableName);

        response.setContentType(request.getServletContext().getMimeType(file.getName()));
        response.setHeader("Content-type", "application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentType(request.getServletContext().getMimeType(file.getName()));
        ServletOutputStream out = response.getOutputStream();
        handle(out,file);
        out.flush();
        file.delete();
        return;
    }

    @PostMapping("config")
    @ResponseBody
    public Result saveTableConfig(GenConfig genConfig) {
        genService.saveConfig(genConfig);
        return ResultBuilder.success();
    }

    @DeleteMapping("config/delete")
    @ResponseBody
    public Result delete(String configKey) {
        genService.deleteConfig(configKey);
        return ResultBuilder.success();
    }

    @GetMapping("global")
    @ResponseBody
    public PageResult<GenConfig> global(@RequestParam(required = false) String db,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        return genService.globalConfig(tableSchema, pageSize, pageNum);
    }



    @GetMapping("listTable")
    @ResponseBody
    public PageResult<TableComment> listTable(@RequestParam(required = false) String db,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        String tableSchema = db == null ? PropertiesUtil.getSystemProperty("code.gen.tableSchema") : db;
        return genService.listAllTable(tableSchema, pageSize, pageNum);
    }


    @GetMapping("listSchema")
    @ResponseBody
    public PageResult<TableSchema> listSchema(@RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        return genService.listSchema(pageSize, pageNum);
    }
    private Integer buffSize = 4 << 20;
    private void handle(OutputStream out, File localFile) throws Exception {
        FileChannel channel = new FileInputStream(localFile).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(buffSize);
        int len = 0;
        while ((len = channel.read(buffer)) > 0) {
            buffer.flip();
            out.write(buffer.array(), 0, len);
            buffer.clear();
        }
        channel.close();

    }
}
