package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.InformationDetail;

import java.util.List;
import java.util.Map;

public interface InformationDetailMapper {

    int insert(InformationDetail record);

    InformationDetail selectByPrimaryKey(Integer id);

    List<InformationDetail> findColumnInforMation(Map<String, Object> map);

    List<InformationDetail> findInforMationDetailLeftInforMation(Map<String, Object> map);

    int updateByPrimaryKey(InformationDetail record);

    void updateThumbUpNum(InformationDetail record);

    void updateBrowse(Map<String, Object> map);

    List<InformationDetail> findByLabel(Map<String,Object> map);

    List<InformationDetail> newinfo();

    List<Map<String,Object>> findInformationDetail(Integer id);

    int selectMinId();

    int selectMaxId();

    List<InformationDetail> relevantInformation(Map<String,Object> map);
}