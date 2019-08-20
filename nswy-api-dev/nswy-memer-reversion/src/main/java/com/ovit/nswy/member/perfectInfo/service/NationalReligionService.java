package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface NationalReligionService {


    /**
     * 通过用户名查询用户的民族宗教信息
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 修改用户的民族宗教信息
     * @param params
     * @return
     */
    void save(Map<String, Object> params);


    /**
     * 更新民族宗教信息
     * @param params
     */
    void update(Map<String, Object> params);

    /**
     * 查询该用户、年度文件下所有的信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findAll(Map<String, Object> params);

}
