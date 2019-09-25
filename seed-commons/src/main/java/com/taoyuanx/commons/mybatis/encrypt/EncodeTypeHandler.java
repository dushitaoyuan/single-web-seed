package com.taoyuanx.commons.mybatis.encrypt;

import com.taoyuanx.commons.utils.PropertiesUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dushitaoyuan
 * @date 2019/9/2521:04
 * @desc: mybatis 字段加密处理
 */
public class EncodeTypeHandler extends BaseTypeHandler<String> {

    public  static  final  String DB_ENCODE_PASSWORD="db.encode.password";
    public  static  final  String DB_ENCODE_IV="db.encode.iv";
    public  static  final  String DB_ENCODE_tag="db.encode.tag";
    private  boolean _switch=true;
    private IEncode encode;
    public EncodeTypeHandler(IEncode encode) {
        this.encode=encode;
    }
    public EncodeTypeHandler() {
        String password = PropertiesUtil.getSystemProperty(DB_ENCODE_PASSWORD);
        String iv = PropertiesUtil.getSystemProperty(DB_ENCODE_PASSWORD);
        this.encode=encode;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String parameter) throws SQLException {
        return null;
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
