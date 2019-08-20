package com.ovit.nswy.member.userManage.service;

import java.util.List;
import java.util.Map;

public interface UserTemplateManageService {

    Map<String,Object> find(Map<String,Object> params);

    void saveOrUpdate(Map<String,Object> params);
}
