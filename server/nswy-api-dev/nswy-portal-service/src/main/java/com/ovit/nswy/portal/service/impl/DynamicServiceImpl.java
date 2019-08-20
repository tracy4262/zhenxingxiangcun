package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.mapper.DynamicMapper;
import com.ovit.nswy.portal.mapper.InformationBookMapper;
import com.ovit.nswy.portal.service.DynamicService;
import com.ovit.nswy.portal.util.PageUtils;
import com.ovit.nswy.portal.util.SortUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态
 *
 * @author haoWen
 * @create 2018-01-15 12:43
 **/
@Service
public class DynamicServiceImpl  implements DynamicService{
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private InformationBookMapper informationBookMapper;
    @Override
    public PageInfo<List<Map<String, Object>>> queryDynamicInfo(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = dynamicMapper.queryDynamicInfo(params);
        for (Map<String,Object> map : list) {
            String classifiedId = MapUtils.getString(map,"classifiedId");
            map.put("classifiedName", SortUtils.getSortName(INFO_CLASS_DATA, classifiedId));
            if ("图书".equals(MapUtils.getString(map,"columnType"))){
                String bookFullPhoto = informationBookMapper.queryBookInfo(MapUtils.getString(map,"informationDetailId"));
                if (StringUtils.isNotEmpty(bookFullPhoto)) {
                    bookFullPhoto = bookFullPhoto.substring(5,bookFullPhoto.length());
                }
                map.put("image",bookFullPhoto);
            }
        }
        return new PageInfo(list);
    }
}
