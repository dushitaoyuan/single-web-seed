package com.taoyuanx.codegen.generate;

import java.io.OutputStream;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/9/821:43
 * @desc: html渲染接口
 */
public interface IRender {
    /**
     * html渲染接口
     * @param templatePath 模板路径
     * @param renderData 渲染数据
     * @param outputStream 输出路径
     * @return
     */
    void render(String templatePath, Map<String, Object> renderData, OutputStream outputStream);

    String render(String templateName,String templateContent,Map<String, Object> renderData);
}
