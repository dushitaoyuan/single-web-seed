package com.taoyuanx.codegen.DTO;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author dushitaoyuan
 * @desc 分页结果
 * @date 2019/10/9
 */
@Data
public class PageDTO<T> implements Serializable {
    private List<T> list;
    private Long total;
    private Integer pageSize;
    private Integer pageNum;

    private PageDTO() {
    }

    public boolean hasData() {
        return Objects.nonNull(list) && !list.isEmpty();
    }

    public boolean noData() {
        return Objects.isNull(list) || list.isEmpty();
    }

    /**
     * github 分页支持
     *
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageDTO<T> githubPage(Page<T> page) {
        return newPage(page.getResult(), page.getTotal(), page.getPageSize(), page.getPageNum());
    }


    public static <T> PageDTO<T> changeListType(List<T> list, Page page) {
        return newPage(list, page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    public static <T> PageDTO<T> newPage(List<T> list, Long total, Integer pageSize, Integer pageNum) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotal(total);
        pageDTO.setPageNum(pageNum);
        pageDTO.setList(list);
        return pageDTO;
    }
}
