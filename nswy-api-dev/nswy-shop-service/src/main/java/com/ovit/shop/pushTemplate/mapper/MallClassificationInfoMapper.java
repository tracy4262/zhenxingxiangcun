package com.ovit.shop.pushTemplate.mapper;

import java.util.List;
import java.util.Map;

public interface MallClassificationInfoMapper {

    List<Map<String,Object>> findMallClassification(Map<String, Object> params);



}
