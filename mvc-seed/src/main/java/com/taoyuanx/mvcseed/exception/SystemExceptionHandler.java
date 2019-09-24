package com.taoyuanx.mvcseed.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import com.taoyuanx.commons.api.ResultCode;
import com.taoyuanx.commons.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
public class SystemExceptionHandler implements HandlerExceptionResolver {
    public static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception e) {

        if (isJson(request, handler)) {
            doHandleException(request, response, handler, e);
            return new ModelAndView();
        } else {
            ModelAndView modelAndView = new ModelAndView();
            //todo跳转到页面
            return modelAndView;

        }
    }


    public void doHandleException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                  Throwable e) {
        Result result = null;
        HttpStatus httpStatus = HttpStatus.OK;
        if (e instanceof ValidatorException) {
            //参数异常
            result = ResultBuilder.failed(ResultCode.PARAM_ERROR.code, e.getMessage());
        } else if (e instanceof ServiceException) {
            //业务异常
            ServiceException exception = (ServiceException) e;
            result = ResultBuilder.failed(exception.getErrorCode(), e.getMessage());
        } else if (e instanceof UnAuthException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            result = ResultBuilder.failed(ResultCode.UNAUTHORIZED.code, e.getMessage());
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException mediaEx = (HttpMediaTypeNotSupportedException) e;
            result = ResultBuilder.failed(ResultCode.UN_SUPPORT_MEDIATYPE.code, "不支持该媒体类型:" + mediaEx.getContentType());
        } else if (e instanceof JSONException) {
            result = ResultBuilder.failed(ResultCode.PARAM_ERROR.code, "参数异常,json格式非法:" + e.getMessage());
        } else if (e instanceof ServletException) {
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
            result = ResultBuilder.failed(e.getMessage());
        } else if (e instanceof NoHandlerFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            result = ResultBuilder.failed(ResultCode.NOT_FOUND.code, "接口 [" + ((NoHandlerFoundException) e).getRequestURL() + "] 不存在");
        } else {
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
            result = ResultBuilder.failed(ResultCode.INTERNAL_SERVER_ERROR.code, "接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
            if (handler != null && handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                LOG.error("接口 [{}] 出现异常，方法：{}.{}，异常摘要：{}", request.getRequestURI(),
                        handlerMethod.getBean().getClass().getName(),
                        handlerMethod.getMethod().getName(),
                        e.getMessage());
            }
            LOG.error("系统未知异常,异常信息:", e);
        }
        Integer statusCode = httpStatus == null ? HttpServletResponse.SC_OK : httpStatus.value();
        ResponseUtil.responseJson(response, JSON.toJSONString(result), statusCode);
    }


    public static boolean isJson(HttpServletRequest request, Object handler) {

        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(ResponseBody.class) || AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), RestController.class) != null) {
                return true;
            }
        }
        String contentType = request.getHeader("Content-Type");
        String accept = request.getHeader("Accept");
        if ((accept != null && accept.contains("json")) || (contentType != null && contentType.contains("json"))) {
            return true;
        } else {
            return false;
        }


    }
}
