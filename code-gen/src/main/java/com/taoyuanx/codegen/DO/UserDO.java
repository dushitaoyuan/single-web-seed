package com.taoyuanx.codegen.DO;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
*
*  @date: 2019-56-09 05:56:59
*  @desc: 账户表
*/
@Data
@TableName("user")
public class UserDO{
        /*
        * 主键
        */
        @TableId("id")
        private Long id;
        /*
        * 账户名
        */
        @TableField("username")
        private String username;
        /*
        * 密码
        */
        @TableField("password")
        private String password;
        /*
        * 状态
        */
        @TableField("status")
        private Byte status;
        /*
        * 创建时间
        */
        @TableField("create_date")
        private Date createDate;
        /*
        * 更新时间
        */
        @TableField("update_date")
        private Date updateDate;
}




