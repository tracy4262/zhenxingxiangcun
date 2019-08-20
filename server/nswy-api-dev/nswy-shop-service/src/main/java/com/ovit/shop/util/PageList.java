package com.ovit.shop.util;

import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PageList<T> implements Serializable {
    private Integer currentPage;//当前页
    private int pageSize;//每页显示记录条数
    private int totalPage;//总页数
    private List<?> data;//每页显示的数据
    private int star;//开始数据
    private int total;

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<?> getData() {
        return data;
    }
    public void setData(List<?> data) {
        this.data = data;
    }
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static PageList pageInfoList(Map<String, Object> params, List list){
        PageList page = new PageList();
        try{
            page.setCurrentPage(MapUtils.getInteger(params,"pageNum"));
            page.setPageSize(MapUtils.getInteger(params,"pageSize"));
            //刚开始的页面为第一页
            if (page.getCurrentPage() == null){
                page.setCurrentPage(1);
            } else {
                page.setCurrentPage(page.getCurrentPage());
            }
            //每页的开始数
            page.setStar((page.getCurrentPage() - 1) * page.getPageSize());
            //list的大小
            int count = list.size();
            //总条数
            page.setTotal(count);
            //设置总页数
            page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
            //对list进行截取
            page.setData(list.subList(page.getStar(),count-page.getStar()>page.getPageSize()?page.getStar()+page.getPageSize():count));
        }catch(Exception e){
            e.printStackTrace();
        }
        return page;


    }

}
