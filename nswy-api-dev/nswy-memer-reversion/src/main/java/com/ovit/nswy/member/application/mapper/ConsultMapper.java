package com.ovit.nswy.member.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConsultMapper {

    void publishStepOne(Map<String, Object> params);

    void updateStepOne(Map<String, Object> params);

    Map<String,Object> findStepOne(int id);

    Map<String,Object> getExpertInfo(Map<String, Object> params);

    Map<String,Object> findStepTwo(Map<String, Object> params);

    List<Map<String,Object>> contactList(Map<String, Object> params);

    List<Map<String,Object>> findContactInfo(Map<String, Object> params);

    void updateStepTwo(Map<String, Object> params);

    void saveStepTwo(Map<String, Object> params);

    void deleteContactInfo(Map<String, Object> params);

    void batchSaveContact(List<Map<String,Object>> list);

    List<Map<String,Object>> networkStationList(Map<String, Object> params);

    List<Map<String,Object>> findNetworkInfo(Map<String, Object> params);

    void updateStepThree(Map<String, Object> params);

    void deleteNetworkInfo(Map<String, Object> params);

    void batchSaveNetwork(List<Map<String,Object>> list);

    Map<String,Object> findStepThree(Map<String, Object> params);

    void updateStepFour(Map<String, Object> params);

    Map<String,Object> findStepFour(Map<String, Object> params);

    Map<String,Object> findConsultService(Map<String, Object> params);

    void updateImgAndContact(Map<String, Object> params);

    Map<String,Object> getConsultById(Map<String, Object> params);

    Map<String,Object> list(Map<String, Object> params);

    void delete(int id);

    List<Map<String,Object>> isExpert(Map<String, Object> params);

    Map<String,Object> findConsultOrder(Map<String, Object> params);

    List<Map<String,Object>> serviceList(Map<String, Object> params);

}
