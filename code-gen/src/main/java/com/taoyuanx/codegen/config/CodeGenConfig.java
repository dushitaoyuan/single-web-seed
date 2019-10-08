package com.taoyuanx.codegen.config;

import com.taoyuanx.codegen.exception.SystemExceptionHandler;
import com.taoyuanx.codegen.generate.FreeMarkerRender;
import com.taoyuanx.codegen.generate.IRender;
import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import com.taoyuanx.commons.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dushitaoyuan
 * @date 2019/9/2623:27
 * @desc: 系统配置
 */
@Configuration

public class CodeGenConfig {
    @Autowired
    Environment environment;
    @Autowired
    SystemExceptionHandler systemExceptionHandler;


    @Bean
    public SpringContextUtil afterPropertiesSet() throws Exception {
        SpringContextUtil springContextUtil = new SpringContextUtil();
        springContextUtil.setConfigLocatioin("classpath:application.properties");
        springContextUtil.setDataCenterId(Integer.parseInt(environment.getProperty("mybatis-plus.global-config.datacenter-id")));
        springContextUtil.setWorkNodeId(Integer.parseInt(environment.getProperty("mybatis-plus.global-config.worker-id")));
        return springContextUtil;
    }

    @Bean
    @Autowired
    public IRender render(CodeGenProperties codeGenProperties){
        FreeMarkerRender render=new FreeMarkerRender(codeGenProperties.getTemplateDir());
        return render;
    }

    /**
     * 统一结果处理
     */
    @RestControllerAdvice(basePackages = "com.taoyuanx.codegen.controller")
    public static class ResponseHandler implements ResponseBodyAdvice<Object> {

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if (body instanceof Result) {
                return body;
            }
            if (body instanceof ResponseEntity) {
                return body;
            }
            return ResultBuilder.success(body);
        }
    }



}
