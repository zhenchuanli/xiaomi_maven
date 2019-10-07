package com.qf.domain;

import java.util.List;

/**
 * Oldman 2019/9/11 11:24
 * bug? 不存在的
 */
public class PageBean<T> {
    private int pageNum;
    private int pageSize;
    private Long totalSize;
    private int pageCount;
    private List<T> data;
    private int startPage;
    private int endPage;

    public PageBean() {
    }

    public PageBean(int pageNum, int pageSize, Long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;
        //计算总页数
        pageCount = (int) (totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1);
        //计算导航的开始页，结束页
        //1.正常情况大于10页  当前页-4   当前页+5
        this.startPage = pageNum - 4;
        this.endPage = pageNum + 5;
        //2.当前页码小于5
        if (pageNum < 5) {
            this.startPage = 1;
            this.endPage = 10;
        }
        //3.当前页大于总页数-5
        if (pageNum > (pageCount - 5)) {
            this.startPage = pageCount - 9;
            this.endPage = pageCount;
        }
        //4.总页数小于10
        if (pageCount < 10) {
            this.startPage = 1;
            this.endPage = pageCount;
        }
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
