package com.taoyuanx.codegen.generate.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/10/1722:28
 * @desc: freemarker 字符不为空输出
 */

public class StringEmptyTag implements TemplateDirectiveModel {
    public static final String VALUE_KEY = "value";
    public static final String TAG_NAME = "str";
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        if (map.containsKey(VALUE_KEY)) {
            String value = map.get(VALUE_KEY).toString();
            if (StringUtils.isNotEmpty(value)) {
                templateDirectiveBody.render(environment.getOut());
            }
        }
    }
}
