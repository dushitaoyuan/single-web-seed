package com.taoyuanx.codegen.generate.freemarker.func;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author dushitaoyuan
 * @date 2019/10/1922:34
 * @desc: mapper xml帮助函数
 */
public class ResolveMapperFunc implements TemplateMethodModelEx {
    public static final String FUN_NAME = "resolve";
    private static final int ARGS_LEN = 3;
    private static final int JAVATYPE_INDEX = 0;
    private static final int JDBCTYPE_INDEX = 1;
    private static final int FMT_INDEX = 2;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (Objects.nonNull(arguments)) {
            //blob 类型或jdbcType为空 不输出
            if (arguments.size() != ARGS_LEN) {
                throw new TemplateModelException("参数列表不匹配");
            }
            String javaType = arguments.get(JAVATYPE_INDEX).toString();
            if (StringUtils.isNotEmpty(javaType)) {
                Object jdbcType = arguments.get(JDBCTYPE_INDEX);
                if (!javaType.equals("byte[]")&&Objects.nonNull(jdbcType)) {
                    String fmt = arguments.get(FMT_INDEX).toString();
                    return new SimpleScalar(String.format(fmt, javaType,jdbcType.toString() ));
                }
            }
        }
        return "";
    }
}
