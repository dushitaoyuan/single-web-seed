package com.taoyuanx.mvcseed;

import com.alibaba.fastjson.JSON;
import com.taoyuanx.codegen.CodeGenBootApplication;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.generate.CodeGenCommonService;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.TableInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

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

    @Test
    public void tableHandlerTest() {
        TableInfo explain = tableHandler.explain("study", "user");
        System.out.println(JSON.toJSONString(explain));
    }


    @Test
    public void genTest() throws FileNotFoundException {
        codeGenCommonService.generate(null, "study", "user");
    }


}
