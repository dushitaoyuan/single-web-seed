package com.taoyuanx.codegen.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author dushitaoyuan
 * @date 2019/9/2820:52
 * @desc: 表注释
 */
@Data
public class TableComment {
    private String tableName;
    private String tableCommnet;
    private String engine;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
