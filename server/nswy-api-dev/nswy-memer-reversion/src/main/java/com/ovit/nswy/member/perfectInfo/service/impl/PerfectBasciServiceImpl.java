package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PerfectBasciServiceImpl implements PerfectBasicService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    /**
     * 通过account在reversion_perfect_module表
     * 中查出用户的完善信息的模块
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findUserModule(Map<String,Object> params){

        return perfectBasicMapper.findUserModule(params);
    }

    /**
     * 查询用户完善信息下某个大模块下的子模块
     * 通过app_id查询reversion_sys_dict得到
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findSubModule(Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        List<Map<String,Object>> moduleList = perfectBasicMapper.findDynamicSubModule(params);
        for (Map<String,Object> map : moduleList) {
            //遍历模块数据，通过sys_dict_id和account获取是否完善的数据
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("account", account);
            userMap.put("dictId", MapUtils.getString(map, "dictId"));
            userMap.put("yearId", MapUtils.getString(params, "yearId"));
            userMap.put("templateId", MapUtils.getString(params, "templateId"));
            Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(userMap);
            //获取模块是否完善的信息
            map.put("isComplete", "1".equals(MapUtils.getString(previewMap,"isComplete")) ? true : false);
        }
        return moduleList;
    }

    /**
     * 通过appId、account
     * 修改模块名称
     * @param params
     */
    @Override
    public void modifyModule(Map<String, Object> params) {
        perfectBasicMapper.modifyModule(params);
    }

    /**
     * 添加年度文件
     * @param params
     */
    @Override
    public void saveYearInfo(Map<String, Object> params) {

        perfectBasicMapper.saveYearInfo(params);

    }

    /**
     * 查询年度文件信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findYearInfo(Map<String, Object> params) {
        return perfectBasicMapper.findYearInfo(params);
    }

    @Override
    public void deleteYearInfo(Map<String, Object> params) {
        perfectBasicMapper.deleteYearInfo(params);
    }

    @Override
    public List<Map<String, Object>> findModuleInfo(Map<String, Object> params) {
        return perfectBasicMapper.findModuleInfo(params);
    }

    @Override
    public void saveTextPreview(Map<String, Object> params) {

        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }
    }

    @Override
    public Map<String, Object> findPropertyInfo(Map<String, Object> params) {

        return perfectBasicMapper.findPropertyInfo(params);

    }


    @Override
    public void updatePropertyInfo(Map<String, Object> params) {

        Map<String,Object> propertyMap = perfectBasicMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyMap)) {
            //插入
            perfectBasicMapper.insertPropertyInfo(params);
        } else {
            //更新
            perfectBasicMapper.updatePropertyInfo(params);
        }

    }

    @Override
    public void updatePropertyStringInfo(Map<String, Object> params) {

        Map<String,Object> propertyMap = perfectBasicMapper.findPropertyInfo(params);
        String propertyName = MapUtils.getString(params,"propertyName");
        if(StringUtil.isNotEmpty(propertyName)) {
            JSONArray pictureListModel = JSONArray.parseArray(propertyName);
            String[] members = ImageUtil.getJsonToStringArray(propertyName);
            String memberMerge = ImageUtil.stringArrayMerge(members);
            params.put("propertyName", memberMerge);
        }

        if (MapUtils.isEmpty(propertyMap)) {
            //插入
            perfectBasicMapper.insertPropertyInfo(params);
        } else {
            //更新

            perfectBasicMapper.updatePropertyInfo(params);
        }

    }


    @Override
    public List<Map<String,Object>> findAllTextPreviewList(Map<String,Object> params){

        List<Map<String,Object>> allTextPreviewList = new ArrayList<>();

        String account = MapUtils.getString(params, "account");
        //根据account查询得到该用户下所有的模块
        List<Map<String,Object>> list  = perfectBasicMapper.findModuleInfo(params);
        for(Map<String,Object> modelInfo:list){

            //获取 模块id 模块名字

            Map<String,Object> previewList = new HashMap<>();


            previewList.put("parentId",MapUtils.getString(modelInfo,"appId"));

            previewList.put("appName",MapUtils.getString(modelInfo,"appName"));

            previewList.put("url",MapUtils.getString(modelInfo,"url"));

            params.put("appId",MapUtils.getString(modelInfo,"appId"));

            Map<String,Object> previewListMap = perfectBasicMapper.findAllTextPreviewList(params);

            if(MapUtils.isNotEmpty(previewListMap)){
                List<Map<String,Object>> allTextList = new LinkedList<>();
                allTextList.add(previewListMap);
                previewList.put("textPreview",allTextList);
            }else{
                //查询获取所有子模块id
                List<Map<String,Object>> moduleList = perfectBasicMapper.findSubModule(params);
                List<Map<String,Object>> textList = new LinkedList<>();

                for (Map<String,Object> map : moduleList) {
                    params.put("dictId",MapUtils.getString(map,"dictId"));

                    Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
                    if(MapUtils.isNotEmpty(previewMap)){


                        textList.add(previewMap);
                    }

                }

                previewList.put("textPreview",textList);
            }

            allTextPreviewList.add(previewList);

        }



        return allTextPreviewList;
    }

    @Override
    public void saveAllTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){

            perfectBasicMapper.saveAllTextPreview(textPreview);
        }
    }

    @Override
    public Map<String, Object> findUseTemplate(String account) {
        return perfectBasicMapper.findUseTemplate(account);
    }

    @Override
    public Map<String, Object> findNewYearInfo(String account) {
        return perfectBasicMapper.findNewYearInfo(account);
    }

    /**
     * 查询隐私信息，获取真实姓名
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findPrivacyInfo(Map<String, Object> params) {

        return perfectBasicMapper.findPrivacyInfo(params);

    }

    @Override
    public Map<String, Object> findMemberName(Map<String, Object> params) {
        return perfectBasicMapper.findMemberName(params);
    }

}
