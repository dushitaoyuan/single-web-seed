package com.taoyuanx.commons.mybatis.encrypt;


import com.taoyuanx.commons.utils.AesHelper;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;


/**
 * @author 桃源
 * @description aes 对称加密算法实现
 * @date 2019/2/20
 */
public class AesSymmetricEncode implements IEncode {
    //标记前缀特殊字符
    public static final String TAG_FLAG = "$t";
    private byte[] password;
    private byte[] iv;
    //是否标记
    private boolean tag;
    private int tagLength = TAG_FLAG.length();
    /**
     * 加密模式
     */
    public static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";

    public AesSymmetricEncode(byte[] password, byte[] iv, boolean openTag) {
        this.password = password;
        this.iv = iv;
        this.tag = openTag;
    }

    @Override
    public String encode(String content) throws Exception {
        if (content.startsWith(TAG_FLAG)) {
            return content;
        }
        byte[] result = encode(content.getBytes("UTF-8"));
        if (tag) {
            return TAG_FLAG + Base64.encodeBase64String(result);
        }
        return Base64.encodeBase64String(result);

    }

    @Override
    public String decode(String encode) throws Exception {
        if (encode.startsWith(TAG_FLAG)) {
            encode = encode.substring(tagLength);
        } else if (tag) {
            return encode;
        }
        return new String(decode(Base64.decodeBase64(encode)), "UTF-8");
    }

    @Override
    public byte[] encode(byte[] data) throws Exception {
        Cipher cipher = AesHelper.getAesCipher(iv, password, Cipher.ENCRYPT_MODE, CIPHER_MODE);
        byte[] result = cipher.doFinal(data);
        return result;
    }

    @Override
    public byte[] decode(byte[] encode) throws Exception {
        Cipher cipher = AesHelper.getAesCipher(iv, password, Cipher.DECRYPT_MODE, CIPHER_MODE);
        byte[] result = cipher.doFinal(encode);
        return result;
    }


    /**
     * 数据库加密配置:
     */

    public static final String DB_ENCODE_PASSWORD = "db.encode.password";
    public static final String DB_ENCODE_IV = "db.encode.iv";
    public static final String DB_ENCODE_TAG = "db.encode.tag";


}
