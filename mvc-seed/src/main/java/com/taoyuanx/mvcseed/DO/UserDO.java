package com.taoyuanx.mvcseed.DO;

import javax.persistence.*;
import lombok.Data;

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
    private String username;

    /**
     * 密码
     */
    private String password;
}