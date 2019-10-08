package com.taoyuanx.codegen.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author dushitaoyuan
 * @date 2019/9/2820:54
 * @desc: 表的列信息
 */
@Data
public class TableColumn {
    private String columnName;
    private String columnComment;
    private String columnType;
    private String columnKey;
}
