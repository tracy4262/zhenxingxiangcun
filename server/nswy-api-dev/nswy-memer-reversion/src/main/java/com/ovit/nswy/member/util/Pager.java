package com.ovit.nswy.member.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pager {

    private int items;
    private int page;
    private int pageSize;
    private List<Map<String,Object>> data = new ArrayList<>();

    public Pager(List<Map<String,Object>> list, int pageSize) {
        this.pageSize = pageSize;
        this.data.addAll(list);
    }

    public List<Map<String,Object>> calc(int pageNum) {
        this.page = calcPage(pageNum);
        return data.subList(getStartRow(), getEndRow());
    }

    private int calcPage(int page) {
        int pages;
        items = data.size();
        if (items <= 0) {
            return 1;
        }
        pages = (int) Math.ceil((double) items / pageSize);
        if (pages > 0) {
            return (page < 1) ? 1 : ((page > pages) ? pages : page);
        }
        return 0;
    }

    private int getStartRow() {
        int startRow;
        if (page > 0) {
            startRow = (pageSize * (page - 1));
        } else {
            startRow = 0;
        }
        return startRow;
    }

    /**
     * @return the endRow
     */
    private int getEndRow() {
        int endRow;
        if (page > 0) {
            endRow = Math.min(pageSize * page, items);
        } else {
            endRow = 0;
        }
        return endRow;
    }
}
