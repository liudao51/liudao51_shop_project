package com.liudao51.shop.entity.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页对象
 */
public class PageInfo<T> implements Serializable {
    //页面(或配置)参数
    private Integer pageNo;  //当前页
    private Integer pageSize;  //每页显示条数

    //数据库查询得到
    private Integer totalRecord;  //总记录条数
    private List<T> records;  //将每页要显示的数据放在list集合中

    //需要计算得到
    private Integer totalPage; //总页数
    private Integer prePage; //前一页
    private Integer nextPage; //后一页
    private Integer startIndex; //记录索引开始位置（在数据库中要从第几行数据开始拿）

    public PageInfo(Integer pageNo, Integer pageSize) {
        this.init(pageNo, pageSize, 0);
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer totalRecord) {
        this.init(pageNo, pageSize, totalRecord);
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer totalRecord, List<T> records) {
        this.init(pageNo, pageSize, totalRecord);
        this.setRecords(records);
    }

    private void init(Integer pageNo, Integer pageSize, Integer totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        //总页数
        this.totalPage = (totalRecord % pageSize == 0) ? (totalRecord / pageSize) : (totalRecord / pageSize + 1);

        //前一页
        this.prePage = (pageNo - 1) > 1 ? (pageNo - 1) : 1;

        //后一页
        this.nextPage = (pageNo + 1) < totalRecord ? pageNo + 1 : totalRecord;

        //记录索引开始位置
        this.startIndex = (pageNo - 1) * pageSize;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalRecord() {
        return this.totalRecord;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getPrePage() {
        return this.prePage;
    }

    public Integer getNextPage() {
        return this.nextPage;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}