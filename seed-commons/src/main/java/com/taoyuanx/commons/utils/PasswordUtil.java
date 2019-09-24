package com.taoyuanx.commons.utils;


import org.springframework.util.StringUtils;

/**
 * @author dushitaoyuan
 * @date 2019/9/1115:52
 * @desc: 密码工具类 BCrypt 实现
 */
public class PasswordUtil {
    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String passwordEncode(String password){
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        return  hashpw;
    }

    /**
     * 密码是否匹配
     * @param dbPassword 已加密的密码
     * @param passwordPlain
     * @return
     */
    public static boolean passwordEqual(String dbPassword,String passwordPlain){
        if(StringUtils.isEmpty(dbPassword)||StringUtils.isEmpty(passwordPlain)){
            return false;
        }
        return   BCrypt.checkpw(passwordPlain,dbPassword);
    }

}
