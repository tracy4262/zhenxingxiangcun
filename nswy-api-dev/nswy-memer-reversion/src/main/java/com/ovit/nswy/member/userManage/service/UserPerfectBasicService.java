package com.ovit.nswy.member.userManage.service;

import java.util.List;
import java.util.Map;

public interface UserPerfectBasicService {

    /**
     * 通过account在reversion_perfect_module表
     * 中查出用户的完善信息的模块
     * @param params
     * @return
     */
    List<Map<String,Object>> findUserModule(Map<String, Object> params);

    /**
     * 查询用户完善信息下某个大模块下的子模块
     * 通过app_id查询reversion_sys_dict得到
     * @param params
     * @return
     */
    List<Map<String,Object>> findSubModule(Map<String, Object> params);

    /**
     * 通过appId、account
     * 修改模块名称
     * @param params
     */
    void modifyModule(Map<String, Object> params);

    /**
     * 添加年度文件
     * @param params
     */
    void saveYearInfo(Map<String, Object> params);

    /**
     * 查询年度文件信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findYearInfo(Map<String, Object> params);


    /**
     * 删除年度文件
     * @param params
     */
    void deleteYearInfo(Map<String, Object> params);

    /**
     * 完善信息--模块数据
     * @param params
     * @return
     */
    List<Map<String,Object>> findModuleInfo(Map<String, Object> params);

    /**
     * 保存文字预览信息
     * @param params
     */
    void saveTextPreview(Map<String, Object> params);

    Map<String,Object> findPropertyInfo(Map<String, Object> params);

    void updatePropertyInfo(Map<String, Object> params);


    List<Map<String,Object>> findAllTextPreviewList(Map<String, Object> params);

    void saveAllTextPreview(Map<String, Object> params);

    /**
     * 保存title 数组类型
     * @param params
     */
    void updatePropertyStringInfo(Map<String, Object> params);

    Map<String,Object> findTableHead(Map<String,Object> params);


}
