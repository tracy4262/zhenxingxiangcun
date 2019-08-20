package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.util.ConstantPool;

import java.util.List;
import java.util.Map;

public interface KnowledgeService extends ConstantPool{
    PageInfo<List<Map<String,Object>>> queryKnowledge(Map<String, Object> params);

}
