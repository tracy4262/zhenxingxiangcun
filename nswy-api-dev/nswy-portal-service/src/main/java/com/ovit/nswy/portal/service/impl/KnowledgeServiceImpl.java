package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.mapper.InformationBookMapper;
import com.ovit.nswy.portal.mapper.KnowledgeMapper;
import com.ovit.nswy.portal.service.KnowledgeService;
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
 * 知识
 *
 * @author haoWen
 * @create 2018-01-15 14:57
 **/
@Service
public class KnowledgeServiceImpl implements KnowledgeService{
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private InformationBookMapper informationBookMapper;
    @Override
    public PageInfo<List<Map<String, Object>>> queryKnowledge(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> maps = knowledgeMapper.queryKnowledge(params);
        for (Map<String,Object> map : maps) {
            String classifiedId = MapUtils.getString(map,"classifiedId");
            map.put("classifiedName", SortUtils.getSortName(KNOWLEDGE_CLASS_DATA_FIX,classifiedId));
            if ("图书".equals(MapUtils.getString(map,"columnType"))){
                String bookFullPhoto = informationBookMapper.queryBookInfo(MapUtils.getString(map,"informationDetailId"));
                if (StringUtils.isNotEmpty(bookFullPhoto)) {
                    bookFullPhoto = bookFullPhoto.substring(5,bookFullPhoto.length());
                }
                map.put("image",bookFullPhoto);
            }
        }
        return new PageInfo(maps);
    }
}
