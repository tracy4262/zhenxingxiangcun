package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnvConditionMapper {

    List<Map<String,Object>> airQuaStandard();

    Map<String,Object> findAirQuaByAccount(Map<String, Object> params);

    void modifyAirQuaByAccount(Map<String, Object> params);

    void insertAirQuaByAccount(Map<String, Object> params);

    Map<String,Object> findSurfaceWaterQua(Map<String, Object> params);

    void modifySurfaceWaterQua(Map<String, Object> params);

    void insertSurfaceWaterQua(Map<String, Object> params);

    List<Map<String, Object>> findWaterStandard();

    List<Map<String, Object>> findEnvStandard();

    Map<String,Object> findEcologyEnvQua(Map<String, Object> params);

    void modifyEcologyEnvQua(Map<String, Object> params);

    void insertEcologyEnvQua(Map<String, Object> params);

}
