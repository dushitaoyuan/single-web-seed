package com.taoyuanx.mvcseed;

import com.alibaba.fastjson.JSON;
import com.taoyuanx.codegen.CodeGenBootApplication;
import com.taoyuanx.codegen.config.CodeGenProperties;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.domain.TableColumn;
import com.taoyuanx.codegen.domain.TableComment;
import com.taoyuanx.codegen.generate.IRender;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.TableInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/9/2820:59
 * @desc: genDao test
 */
@SpringBootTest(classes = {CodeGenBootApplication.class})
@RunWith(SpringRunner.class)
public class GenDaoTest {
    @Autowired
    GenDao genDao;
    @Autowired
    ITableHandler tableHandler;

    @Autowired
    CodeGenProperties codeGenProperties;
    @Autowired
    IRender iRender;


    @Test
    public void tableHandlerTest(){
        TableInfo explain = tableHandler.explain("xa", "user");
        System.out.println(JSON.toJSONString(explain));
    }


    @Test
    public void genTest() throws FileNotFoundException {
        TableInfo explain = tableHandler.explain("xa", "user");
        Map<String,Object> rendreData=new HashMap<>();
        rendreData.put("table",explain);
        rendreData.put("config",codeGenProperties);
        iRender.render("DO.ftl",rendreData,new FileOutputStream("111.java"));

    }


}
