package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HistoryEvolutionMapper {

    List<Map<String,Object>> findHistoryEvolution(Map<String,Object> params);

    void updateHistoryEvolutionName(Map<String,Object> params);

    void saveHistoryEvolution(Map<String,Object> params);

    void deleteHistoryEvolution(Map<String,Object> params);



}
