package com.taoyuanx.commons.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dushitaoyuan
 * @desc 自定义结果返回
 * @date 2019/8/30
 */
public class ResponseUtil {
    public static void responseJson(HttpServletResponse response, String result, Integer httpStatus) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(httpStatus);

        try {
            response.getWriter().write(result);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
