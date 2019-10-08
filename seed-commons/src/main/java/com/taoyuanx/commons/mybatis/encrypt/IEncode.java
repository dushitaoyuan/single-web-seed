package com.taoyuanx.commons.mybatis.encrypt;

/**
 * @author dushitaoyuan
 * @date 2019/9/2521:15
 * @desc: 加密抽象类
 */
public interface IEncode {

    String encode(String text) throws Exception;

    String decode(String encode) throws Exception;

    byte[] encode(byte[] data) throws Exception;

    byte[] decode(byte[] encode) throws Exception;
}
