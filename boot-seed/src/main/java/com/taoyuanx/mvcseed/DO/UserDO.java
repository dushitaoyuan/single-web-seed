package com.taoyuanx.mvcseed.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.taoyuanx.commons.mybatis.encrypt.EncodeAliasTypeHandlerextends;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user",resultMap = "BaseResultMap")
public class UserDO {
    /**
     * 用户id
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 账户名
     */
    @TableField(typeHandler = EncodeAliasTypeHandlerextends.class)
    private String username;

    /**
     * 密码
     */
    private String password;

    @TableField( )
    private Date createdate;
}