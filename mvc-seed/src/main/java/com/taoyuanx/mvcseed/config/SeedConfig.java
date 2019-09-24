package com.taoyuanx.mvcseed.config;

import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author dushitaoyuan
 * @date 2019/9/1116:59
 * @desc: 系统代码配置
 */
@Configuration
public class SeedConfig {
    /**
     * 统一结果处理
     */
    @RestControllerAdvice(basePackages = "com.taoyuanx.mvcseed.controller")
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
