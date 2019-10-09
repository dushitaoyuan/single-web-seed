package com.taoyuanx.codegen.generate.freemarker;

import com.taoyuanx.codegen.generate.IRender;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/9/910:11
 * @desc: freemarker html render
 */
public class FreeMarkerRender implements IRender {
    private Configuration configuration;

    public FreeMarkerRender(Configuration configuration) {
        this.configuration = configuration;
        configuration.setEncoding(Locale.CHINESE, "UTF-8");
    }

    public FreeMarkerRender(String templateDir) {
        try {
            configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            if (templateDir.toLowerCase().startsWith("classpath:")) {
                templateDir = templateDir.toLowerCase().replaceFirst("classpath:", "/");
                configuration.setClassForTemplateLoading(FreeMarkerRender.class,
                        templateDir);
            } else {
                File file = new File(templateDir);
                if (!file.exists()) {
                    throw new RuntimeException(templateDir + " ->templateDir 不存在");
                }
                configuration.setTemplateLoader(new FileTemplateLoader(file));
            }
            configuration.setEncoding(Locale.CHINESE, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(String templatePath, Map<String, Object> renderData, OutputStream outputStream) {
        try {
            Template tp = configuration.getTemplate(templatePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            tp.process(renderData, outputStreamWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String render(String templateName,String templateContent, Map<String, Object> renderData) {
        try {
            Template tp = new Template(templateName,templateContent,configuration);
            StringWriter stringWriter = new StringWriter();
            tp.process(renderData, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
