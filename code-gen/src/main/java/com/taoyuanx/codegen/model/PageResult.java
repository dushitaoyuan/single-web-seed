package com.taoyuanx.codegen.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dushitaoyuan
 * @date 2019/10/415:34
 * @desc: 分页返回结果
 */
@Getter
@Setter
public class PageResult<T> {
    private List<T> list;
    private Integer pageSize;
    private Integer pageNum;
    private Long total;
    private  Long toalPage;

    public static <T> PageResult<T> change(Page<T> page) {
        PageResult result = new PageResult<>();
        result.setList(page.getResult());
        result.setPageNum(page.getPageNum());
        result.setTotal(page.getTotal());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        return result;
    }
}
