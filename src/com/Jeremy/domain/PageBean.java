package com.Jeremy.domain;

import java.io.Serializable;
import java.util.List;

//分页的持久化类
public class PageBean<T> implements Serializable {
    private int pageNumber;//页码
    private int pageSize;//单页记录数量
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private int startIndex;//开始页
    private List<T> data;//数据集合

    //构造
    public PageBean(int pageNumber, int pageSize, int totalRecord) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        //总记录数和单页记录数是否整除 计算总页数
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
        //起始项的计算
        this.startIndex = pageSize * (this.pageNumber - 1);

    }

    /**
     * 获取
     *
     * @return pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置
     *
     * @param pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 获取
     *
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取
     *
     * @return totalRecord
     */
    public int getTotalRecord() {
        return totalRecord;
    }

    /**
     * 设置
     *
     * @param totalRecord
     */
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * 获取
     *
     * @return totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 设置
     *
     * @param totalPage
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获取
     *
     * @return startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 设置
     *
     * @param startIndex
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 获取
     *
     * @return data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    public String toString() {
        return "PageBean{pageNumber = " + pageNumber + ", pageSize = " + pageSize + ", totalRecord = " + totalRecord + ", totalPage = " + totalPage + ", startIndex = " + startIndex + ", data = " + data + "}";
    }
}
