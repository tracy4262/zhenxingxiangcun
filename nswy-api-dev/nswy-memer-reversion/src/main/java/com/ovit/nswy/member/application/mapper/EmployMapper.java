package com.ovit.nswy.member.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployMapper {

    List<Map<String,Object>> findExpert(Map<String, Object> params);

    void saveNotice(Map<String, Object> params);

    void saveInvite(Map<String, Object> params);

    void deleteNotice(Map<String, Object> params);

    void deleteInvite(Map<String, Object> params);

    Map<String,Object> findInvite(Map<String, Object> params);

    void updateInvite(Map<String, Object> params);

    List<Map<String,Object>> manage(Map<String, Object> params);

    List<Map<String,Object>> beInvited(Map<String, Object> params);

    List<Map<String,Object>> beEmployed(Map<String, Object> params);

    List<Map<String,Object>> findConsultExpert(Map<String, Object> params);

    List<Map<String,Object>> consultManage(Map<String, Object> params);

    Map<String,Object> orderDetail(int id);

    List<Map<String,Object>> orderList();

    void updateConsultOrder(int id);

    List<Map<String,Object>> findOrderList();

    void updateServiceOrder(int id);

}
