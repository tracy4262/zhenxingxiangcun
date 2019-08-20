package com.ovit.nswy.portal.mapper;

import java.util.List;
import java.util.Map;

public interface KnowledgeMapper {
    List<Map<String,Object>> queryKnowledge(Map<String, Object> params);
}
