package com.taoyuanx.commons.mybatis.encrypt;

import com.taoyuanx.commons.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author 桃源
 * @description 使用方式;注册类型处理器,敏感字段javaType设置为Encode 或 typeHandler=com.ncs.pm.commons.mybatis.encrypt.EncodeAliasTypeHandlerextends
 * @date 2019/2/20
 */

public class EncodeAliasTypeHandlerextends extends BaseTypeHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(EncodeAliasTypeHandlerextends.class);

    private IEncode encode;
    //关闭敏感字段解析,encode字段解析使用stringTypeHandler解析
    private boolean switch_off = true;


    public EncodeAliasTypeHandlerextends(IEncode encode) {
        encode = encode;
        switch_off = false;
    }

    public EncodeAliasTypeHandlerextends() {
        initConfig();
    }


    /**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     *
     * @param ps        当前的PreparedStatement对象
     * @param i         当前参数的位置
     * @param parameter 当前参数的Java对象
     * @param jdbcType  当前参数的数据库类型
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, doHandle(ENCODE_MODE, parameter));
    }

    /**
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
     *
     * @param rs         当前的结果集
     * @param columnName 当前的字段名称
     * @return 转换后的Java对象
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return doHandle(DECODE_MODE, rs.getString(columnName));
    }

    /**
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
     *
     * @param rs          当前的结果集
     * @param columnIndex 当前字段的位置
     * @return 转换后的Java对象
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return doHandle(DECODE_MODE, rs.getString(columnIndex));
    }

    /**
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
     *
     * @param cs          当前的CallableStatement执行后的CallableStatement
     * @param columnIndex 当前输出参数的位置
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return doHandle(DECODE_MODE, cs.getString(columnIndex));
    }

    private static final int DECODE_MODE = 0;
    private static final int ENCODE_MODE = 1;

    private String doHandle(int mode, String parameter) {
        if (switch_off) {
            return parameter;
        }
        String result = parameter;
        if (StringUtils.isEmpty(parameter)) {
            return parameter;
        }
        try {
            if (mode == DECODE_MODE) {
                result = encode.decode(parameter);
                LOG.debug("解密参数:[{}],解密结果:[{}]", parameter, result);
            } else {
                result = encode.encode(parameter);
                LOG.debug("加密参数:[{}],加密结果:[{}]", parameter, result);

            }
        } catch (Exception e) {
            LOG.debug("处理参数:[{}],失败", parameter, e);
        }
        return result;

    }

    private void initConfig() {
        try {
            Properties variables =configuration==null?new Properties(): configuration.getVariables();
            String passowrdStr = variables.getProperty(AesSymmetricEncode.DB_ENCODE_PASSWORD);
            String ivStr = variables.getProperty(AesSymmetricEncode.DB_ENCODE_IV);
            String tagStr = variables.getProperty(AesSymmetricEncode.DB_ENCODE_TAG);
            if (StringUtils.isEmpty(passowrdStr)) {
                passowrdStr = PropertiesUtil.getSystemProperty(AesSymmetricEncode.DB_ENCODE_PASSWORD);
                ivStr = PropertiesUtil.getSystemProperty(AesSymmetricEncode.DB_ENCODE_IV);
                tagStr =PropertiesUtil.getSystemProperty(AesSymmetricEncode.DB_ENCODE_TAG);
            }
            byte[] iv = ivStr.getBytes("UTF-8");
            byte[] passwordByte = passowrdStr.getBytes("UTF-8");
            Boolean openTag = true;
            if (StringUtils.isNotEmpty(tagStr)) {
                openTag = Boolean.valueOf(tagStr);
            }
            encode = new AesSymmetricEncode(passwordByte, iv, openTag);
            switch_off = false;
        } catch (Exception e) {
            throw new RuntimeException("数据库加密初始配置失败", e);
        }
    }

}