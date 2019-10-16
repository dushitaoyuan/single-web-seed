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
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dushitaoyuan
 * @date 2019/9/2822:10
 * @desc: 代码生成controller
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public  String index(){
        return "redirect:/htmls/index.html";
    }
}
