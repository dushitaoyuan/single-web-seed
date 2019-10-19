package com.taoyuanx.mvcseed;

import com.alibaba.fastjson.JSON;
import com.taoyuanx.codegen.CodeGenBootApplication;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.generate.CodeGenCommonService;
import com.taoyuanx.codegen.generate.IRender;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.TableInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.HashMap;
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
    CodeGenCommonService codeGenCommonService;


    @Autowired
    IRender render;

    @Test
    public void tableHandlerTest() {
        TableInfo explain = tableHandler.explain("study", "user");
        System.out.println(JSON.toJSONString(explain));
    }


    @Test
    public void genTest() throws Exception {
        codeGenCommonService.generate(null, "study", "user");
    }

    @Test
    public void renderTest() throws Exception {
        Map<String, Object> data = new HashMap();
        data.put("va", "11");
        data.put("javaType", "Integer");
        data.put("jdbcType", "INTEGER");
        String result = render.render("test", "<@str value =\"${va}\" >我不为空</@str> "+"${resolve(javaType,jdbcType,'javaType=\"%s\" jdbcType=\"%s\"')}", data);


        System.out.println(result);
    }


}
