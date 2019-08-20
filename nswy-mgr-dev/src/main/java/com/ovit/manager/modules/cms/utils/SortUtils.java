package com.ovit.manager.modules.cms.utils;

import com.alibaba.fastjson.JSON;
import com.ovit.manager.modules.cms.entity.Sort;
import com.ovit.manager.modules.cms.entity.children;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 分类ID获取分类名称
 *
 * @author haoWen
 * @create 2018-01-23 10:59
 **/

public class SortUtils {

    public static String getSortName(String json,String id){
        long startTime = System.currentTimeMillis();
        String sortName = "";
        List<Sort> sortList = JSON.parseArray(json,Sort.class);
        for (Sort sort : sortList) {
            //取第一级比较
            if (id.equalsIgnoreCase(sort.getValue())) {
                sortName = sort.getLabel();
                continue;
            } else {
                //第二级不为空取第二级
                List<children> sortChildrenList = sort.getChildren();
                if (CollectionUtils.isNotEmpty(sortChildrenList)) {
                    for (children children : sortChildrenList) {
                        if (id.equalsIgnoreCase(children.getValue())) {
                            sortName = children.getLabel();
                            continue;
                        } else {
                            //第三级不为空取第三级
                            List<children> childrenList = children.getChildren();
                            if (CollectionUtils.isNotEmpty(childrenList)) {
                                for (children children1 : childrenList) {
                                    if (id.equalsIgnoreCase(children1.getValue())) {
                                        sortName = children1.getLabel();
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return sortName;
    }
}
