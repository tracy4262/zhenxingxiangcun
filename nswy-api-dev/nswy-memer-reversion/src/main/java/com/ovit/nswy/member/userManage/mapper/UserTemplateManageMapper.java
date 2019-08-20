package com.ovit.nswy.member.userManage.mapper;



import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserTemplateManageMapper {

    Map<String,Object> find(Map<String,Object> params);

    Map<String,Object> findTemplate(Map<String,Object> params);


    void saveOrUpdate(Map<String,Object> params);

    void updateTemplate(Map<String,Object> params);

    void updateTemplateStatus(Map<String,Object> params);

}
