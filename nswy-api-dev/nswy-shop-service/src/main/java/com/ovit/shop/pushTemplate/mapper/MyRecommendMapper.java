package com.ovit.shop.pushTemplate.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyRecommendMapper {

    Map<String,Object> isHadRecommend(Map<String, Object> params);

    void singleSaveRecommend(Map<String, Object> params);

}
