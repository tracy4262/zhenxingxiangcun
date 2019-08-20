package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface FamilyMemberService {


    /**
     * 通过用户名查询用户的家庭成员信息
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 修改用户的家庭成员信息
     * @param params
     * @return
     */
    void save(Map<String, Object> params);


    /**
     * 删除家庭成员信息
     * @param params
     */
    void delete(Map<String, Object> params);

}
