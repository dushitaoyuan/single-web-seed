package com.taoyuanx.commons.utils;

/**
 * @author dushitaoyuan
 * @date 2019/9/1116:40
 * @desc: 主键生成工具
 */
public class IdGenUtil {
    public static Long genId() {
        return SpringContextUtil.getSnowflake().nextId();
    }

}
