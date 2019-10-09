package com.taoyuanx.codegen.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dushitaoyuan
 * @desc 分页查询条件
 * @date 2019/10/9
 */
@Getter
@Setter
public class PageVo<T> implements Serializable {
    private Integer pageSize = 10;
    private Integer pageNum = 1;
    private T query;

    public PageVo(Integer pageSize, Integer pageNum) {
        if (Objects.nonNull(pageSize)) {
            this.pageSize = pageSize;
        }
        if (Objects.nonNull(pageSize)) {
            this.pageNum = pageNum;
        }

    }
    public PageVo(Integer pageSize, Integer pageNum, T query) {
        this(pageSize, pageNum);
        this.query = query;
    }
}
