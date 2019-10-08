package com.taoyuanx.mvcseed.DO;

import javax.persistence.*;

import com.taoyuanx.commons.mybatis.encrypt.EncodeAliasTypeHandlerextends;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import java.util.Date;

@Data
@Table(name = "user")
public class UserDO {
    /**
     * 用户id
     */
    @Id
    private Long id;

    /**
     * 账户名
     */
    @ColumnType(typeHandler = EncodeAliasTypeHandlerextends.class)
    private String username;

    /**
     * 密码
     */
    private String password;

    private Date createdate;
}