package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface OutputProductService {


    /**
     * 通过用户名查询用户的产出产品信息
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 修改用户的产出产品信息
     * @param params
     * @return
     */
    void save(Map<String, Object> params);


    /**
     * 删除产出产品信息
     * @param params
     */
    void delete(Map<String, Object> params);

}
