package com.member.utils;

import java.util.List;

/**
 * Page是分页的模型对象
 *
 * @param <T> 是具体的JavaBean类，如User
 * @author ：liuyuntao
 */
public class Page<T> {
    /**
     * 每页条数
     */
    public static final Integer PAGE_SIZE = 5;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 总页码
     */
    private Integer pageTotal;
    /**
     * 当前页显示数量
     */
    private Integer pageSize = PAGE_SIZE;
    /**
     * 总记录数
     */
    private Integer pageTotalCount;
    /**
     * 当前页数据
     */
    private List<T> items;

    public Page() {
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                '}';
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }
}
