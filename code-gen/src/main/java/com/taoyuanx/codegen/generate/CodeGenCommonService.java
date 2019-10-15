package com.taoyuanx.codegen.generate;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.taoyuanx.codegen.config.CodeGenProperties;
import com.taoyuanx.codegen.handlers.ITableHandler;
import com.taoyuanx.codegen.model.TableInfo;
import com.taoyuanx.codegen.utils.GenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dushitaoyuan
 * @desc 通用代码生成
 * @date 2019/10/9
 */
@Service
@Slf4j
public class CodeGenCommonService {
    @Autowired
    IRender iRender;
    @Autowired
    ITableHandler tableHandler;
    @Autowired
    CodeGenProperties codeGenProperties;

    public File generate(Map<String, Object> extData, String tableSchema, String tableName) throws Exception {
        String uuid= UUID.randomUUID().toString().replace("-","");
        File baseDir=new File(codeGenProperties.getTemplateOutDir(),uuid);


        /**
         * 数据填充
         */
        TableInfo tableInfo = tableHandler.explain(tableSchema, tableName);
        Map<String, Object> rendreData = new HashMap<>();
        rendreData.put("table", tableInfo);
        rendreData.put("config", codeGenProperties);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        rendreData.put("date", dateFormat.format(new Date()));
        if (!CollectionUtils.isEmpty(extData)) {
            rendreData.put("ext", extData);
        }

        /**
         * entity
         */
        String entityFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getEntitySuffix());
        String entityFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getEntityPackage();
        rendreData.put("entityFinalName", entityFinalName);
        rendreData.put("entityFinalPackage", entityFinalPackage);
        rendreData.put("entityFullName", entityFinalPackage + "." + entityFinalName);
        String fileName = iRender.render("entity", "${entityFullName}.java", rendreData);
        doGenerate(baseDir,"DO.ftl", fileName, rendreData);


        /**
         * DTO
         */

        String dtoFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getDtoSuffix());
        String dtoFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getDtoPackage();
        rendreData.put("dtoFinalName", dtoFinalName);
        rendreData.put("dtoFinalPackage", dtoFinalPackage);
        rendreData.put("dtoFullName", dtoFinalPackage + "." + dtoFinalName);
        fileName = iRender.render("DTO", "${dtoFullName}.java", rendreData);
        doGenerate(baseDir,"DTO.ftl", fileName, rendreData);
        /**
         * mapper
         */
        String mapperFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getMapperSuffix());
        String mapperFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getMapperPackage();
        rendreData.put("mapperFinalName", mapperFinalName);
        rendreData.put("mapperFinalPackage", mapperFinalPackage);
        rendreData.put("mapperFullName", mapperFinalPackage + "." + mapperFinalName);
        fileName = iRender.render("mapper", "${mapperFullName}.java", rendreData);
        doGenerate(baseDir,"mapper.ftl", fileName, rendreData);


        /**
         * mapper.xml
         */
        String mapperXmlFinalName = mapperFinalName;
        rendreData.put("mapperXmlFinalName", mapperXmlFinalName);
        fileName = iRender.render("mapperXml", "${config.mapperXmlPackage}.${mapperXmlFinalName}.xml", rendreData);
        doGenerate(baseDir,"mapperXml.ftl", fileName, rendreData);

        /**
         * service
         */
        String serviceFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getServiceSuffix());
        String serviceFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getServicePackage();
        rendreData.put("serviceFinalName", serviceFinalName);
        rendreData.put("serviceFinalPackage", serviceFinalPackage);
        rendreData.put("serviceFullName", serviceFinalPackage + "." + serviceFinalName);
        fileName = iRender.render("mapper", "${serviceFullName}.java", rendreData);
        doGenerate(baseDir,"service.ftl", fileName, rendreData);

        /**
         * serviceImpl
         */
        String serviceImplFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getServiceImplSuffix());
        String serviceImplFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getServiceImplPackage();
        rendreData.put("serviceImplFinalName", serviceImplFinalName);
        rendreData.put("serviceImplFinalPackage", serviceImplFinalPackage);
        rendreData.put("serviceImplFullName", serviceImplFinalPackage + "." + serviceImplFinalName);
        fileName = iRender.render("serviceImpl.ftl", "${serviceImplFullName}.java", rendreData);
        doGenerate(baseDir,"serviceImpl.ftl", fileName, rendreData);



        /**
         * controller
         */
        String controllerFinalName = suffix(tableInfo.getEntityName(), codeGenProperties.getControllerSuffix());
        String controllerFinalPackage = tableInfo.getPackageName() + "." + codeGenProperties.getControllerPackage();

        String controllerMapping=GenUtil.unCapFirst(tableInfo.getEntityName());
        rendreData.put("controllerFinalName", controllerFinalName);
        rendreData.put("controllerFinalPackage", controllerFinalPackage);
        rendreData.put("controllerFullName", controllerFinalPackage + "." + controllerFinalName);
        rendreData.put("controllerMapping", controllerMapping);

        fileName = iRender.render("controller", "${controllerFullName}.java", rendreData);
        doGenerate(baseDir,"controller.ftl", fileName, rendreData);
        File zip = ZipUtil.zip(baseDir);
        FileUtil.del(baseDir);
        return  zip;
    }


    private void doGenerate(File baseDir,String templatePath, String outPath, Map<String, Object> rendreData) {
        try {
            outPath = GenUtil.changePackageToFilePath(outPath);
            File file = new File(baseDir,outPath);
            GenUtil.forceMakeDirs(file);
            iRender.render(templatePath, rendreData, new FileOutputStream(file));
            log.info("模板{} 代码生成结束",templatePath);
        } catch (Exception e) {
            log.error("{}模板生成异常,{}", templatePath, e);
        }
    }

    private String suffix(String str, String suffix) {
        if (str.endsWith(suffix)) {
            return str;
        }
        return str + suffix;
    }
}
