package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.CommunalFacilitiesMapper;
import com.ovit.nswy.member.perfectInfo.mapper.EmployeeRosterMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.CommunalFacilitiesService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共设施
 */
@Service
public class CommunalFacilitiesServiceImpl implements CommunalFacilitiesService {

    @Autowired
    private CommunalFacilitiesMapper communalFacilitiesMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    /**
     * 交通设施
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findTrafficOfUserInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //交通设施及用户信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findTrafficOfUserInfo(params);
        if(list.size()>0){
            for(Map<String,Object> traffic : list ){
                traffic.put("status","1".equals(MapUtils.getString(traffic,"status"))? true : false);
                String pictureList = MapUtils.getString(traffic,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    traffic.put("pictureList",members);
                }else{
                    traffic.put("pictureList", new String[]{});
                }
                JSONArray siteInfoArray = JSONArray.parseArray(MapUtils.getString(traffic, "siteInfo"));
                if (siteInfoArray == null) {
                    traffic.put("siteInfo", new ArrayList<>());
                } else {
                    traffic.put("siteInfo", siteInfoArray);
                }
            }
            map.put("trafficOfUser", list);

        } else {
            map.put("trafficOfUser", new ArrayList<>());
        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", "交通设施及用户信息");
        } else {
            String propertyName = MapUtils.getString(propertyInfo, "propertyName");
            map.put("propertyName", propertyName);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }


        if(MapUtils.isNotEmpty(textPreview)){
            String textPreviews = MapUtils.getString(textPreview,"textPreview");
            if(StringUtil.isNotEmpty(textPreviews)){
                String[] members = textPreviews.split("&");
                textPreview.put("textPreview",members);
            }else{
                textPreview.put("textPreview", new String[]{});
            }
            map.put("textPreview",textPreview);


        }else{

            Map<String,Object> text = new HashMap<>();
            text.put("textPreview",new String[]{});
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);

            map.put("textPreview",text);

        }

        return map;
    }

    @Override
    public void updateTrafficOfUserInfoName(Map<String,Object> params){

        params.put("trafficOfUserName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findTrafficOfUserInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateTrafficOfUserInfoName(params);
        }else{
            communalFacilitiesMapper.saveTrafficOfUserInfo(params);
        }

    }



    @Override
    public void saveTrafficOfUserInfo(Map<String,Object> params){
        Map<String,Object> trafficOfUser = (Map<String, Object>) params.get("trafficOfUser");
        String user_id = MapUtils.getString(params,"account");
        trafficOfUser.put("account",user_id);
        trafficOfUser.put("trafficOfUserName",MapUtils.getString(params,"trafficOfUserName"));
        trafficOfUser.put("yearId",MapUtils.getString(params,"yearId"));
        trafficOfUser.put("templateId",MapUtils.getString(params,"templateId"));
        String pictureList = MapUtils.getString(trafficOfUser,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                trafficOfUser.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                trafficOfUser.put("pictureList", memberMerge);
            }
        }else{
            trafficOfUser.put("pictureList", "");
        }
        String json = JSON.toJSONString(trafficOfUser.get("siteInfo"));
        trafficOfUser.put("siteInfo", json);

        communalFacilitiesMapper.saveTrafficOfUserInfo(trafficOfUser);

    }




    @Override
    public void updateTrafficInfoName(Map<String,Object> params){
        params.put("trafficName",MapUtils.getString(params,"propertyName"));;
        List<Map<String,Object>> list = communalFacilitiesMapper.findTrafficInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateTrafficInfoName(params);
        }else{
            communalFacilitiesMapper.saveTrafficInfo(params);
        }

    }



    @Override
    public void saveTrafficInfo(Map<String,Object> params){
        Map<String,Object> trafficInfo = (Map<String, Object>) params.get("trafficInfo");
        String user_id = MapUtils.getString(params,"account");
        trafficInfo.put("account",user_id);
        trafficInfo.put("trafficName",MapUtils.getString(params,"trafficName"));
        trafficInfo.put("yearId",MapUtils.getString(params,"yearId"));
        trafficInfo.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveTrafficInfo(trafficInfo);



    }

    @Override
    public void saveTextPreviewTwo(Map<String,Object> params){
        Map<String,Object> textPreviewMap = (Map<String,Object>)params.get("textPreview");

        if(MapUtils.isNotEmpty(textPreviewMap)){
            String textPreview = MapUtils.getString(textPreviewMap,"textPreview");
            if(StringUtil.isNotEmpty(textPreview)) {
                JSONArray textPreviewModel = JSONArray.parseArray(textPreview);
                if (textPreviewModel.size() == 0) {
                    textPreviewMap.put("textPreview", "");
                } else {
                    String[] members = ImageUtil.getJsonToStringArray(textPreview);
                    StringBuffer sb = new StringBuffer();
                    for (String str : members) {
                        sb.append(str).append("&");
                    }
                    String sbString = sb.toString();
                    String result = sbString.substring(0, sbString.length() - 1);
                    textPreviewMap.put("textPreview", result);
                }
            }else{
                textPreviewMap.put("textPreview", "");
            }
            communalFacilitiesMapper.saveTextPreview(textPreviewMap);
        }


    }


    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){

            communalFacilitiesMapper.saveTextPreview(textPreview);
        }
    }

    @Override
    public void deleteTrafficOfUserInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteTrafficOfUserInfo(params);
    }

    @Override
    public void deleteTrafficInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteTrafficInfo(params);
    }


    /**
     * 通信设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findCommunicationFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("communicationFacilitiesInfoName", "通信设施信息");
        } else {
            map.put("communicationFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        //通信设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findCommunicationFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> communication:list ){
                communication.put("status","1".equals(MapUtils.getString(communication,"status"))? true : false);
                String pictureList = MapUtils.getString(communication,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    communication.put("pictureList",members);
                }else{
                    communication.put("pictureList", new String[]{});
                }
            }
            //map.put("communicationFacilitiesInfoName",MapUtils.getString(list.get(0),"communicationFacilitiesInfoName"));

            map.put("communicationFacilitiesInfo",list);


        }else{
            //map.put("communicationFacilitiesInfoName","");
            map.put("communicationFacilitiesInfo",new String[]{});


        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateCommunicationFacilitiesInfoName(Map<String,Object> params){

        params.put("communicationFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findCommunicationFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateCommunicationFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveCommunicationFacilitiesInfo(params);
        }

    }



    @Override
    public void saveCommunicationFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> communication = (Map<String, Object>) params.get("communicationFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        communication.put("account",user_id);
        String pictureList = MapUtils.getString(communication,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                communication.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                communication.put("pictureList", memberMerge);
            }
        }else{
            communication.put("pictureList", "");
        }

        communication.put("communicationFacilitiesInfoName",MapUtils.getString(params,"communicationFacilitiesInfoName"));
        communication.put("yearId",MapUtils.getString(params,"yearId"));
        communication.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveCommunicationFacilitiesInfo(communication);

    }


    @Override
    public void deleteCommunicationFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteCommunicationFacilitiesInfo(params);
    }


    /**
     * 自来水设施及用户信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findTapWaternFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //自来水设施及用户信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findTapWaternFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> communication:list ){
                communication.put("status","1".equals(MapUtils.getString(communication,"status"))? true : false);
                String pictureList = MapUtils.getString(communication,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    communication.put("pictureList",members);
                }else{
                    communication.put("pictureList", new String[]{});
                }
            }
            //map.put("tapWaternFacilitiesInfoName",MapUtils.getString(list.get(0),"tapWaternFacilitiesInfoName"));

            map.put("tapWaternFacilitiesInfo",list);


        }else{
            //map.put("tapWaternFacilitiesInfoName","");
            map.put("tapWaternFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("tapWaternFacilitiesInfoName", "自来水设施及用户信息");
        } else {
            map.put("tapWaternFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateTapWaternFacilitiesInfoName(Map<String,Object> params){

        params.put("tapWaternFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findCommunicationFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateTapWaternFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveTapWaternFacilitiesInfo(params);
        }

    }



    @Override
    public void saveTapWaternFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> tapWatern = (Map<String, Object>) params.get("tapWaternFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        tapWatern.put("account",user_id);
        String pictureList = MapUtils.getString(tapWatern,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                tapWatern.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                tapWatern.put("pictureList", memberMerge);
            }
        }else{
            tapWatern.put("pictureList", "");
        }

        tapWatern.put("tapWaternFacilitiesInfoName",MapUtils.getString(params,"tapWaternFacilitiesInfoName"));
        tapWatern.put("yearId",MapUtils.getString(params,"yearId"));
        tapWatern.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveTapWaternFacilitiesInfo(tapWatern);

    }


    @Override
    public void deleteTapWaternFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteTapWaternFacilitiesInfo(params);
    }


    /**
     * 水利设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findWaterConservancyFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //水利设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findWaterConservancyFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> water:list ){
                water.put("status","1".equals(MapUtils.getString(water,"status"))? true : false);
                String pictureList = MapUtils.getString(water,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    water.put("pictureList",members);
                }else{
                    water.put("pictureList", new String[]{});
                }
            }
            //map.put("waterConservancyFacilitiesInfoName",MapUtils.getString(list.get(0),"waterConservancyFacilitiesInfoName"));

            map.put("waterConservancyFacilitiesInfo",list);


        }else{
            //map.put("waterConservancyFacilitiesInfoName","");
            map.put("waterConservancyFacilitiesInfo",new String[]{});


        }
        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("waterConservancyFacilitiesInfoName", "自来水设施及用户信息");
        } else {
            map.put("waterConservancyFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateWaterConservancyFacilitiesInfoName(Map<String,Object> params){

        params.put("waterConservancyFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findWaterConservancyFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateWaterConservancyFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveWaterConservancyFacilitiesInfo(params);
        }

    }



    @Override
    public void saveWaterConservancyFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> water = (Map<String, Object>) params.get("waterConservancyFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        water.put("account",user_id);
        String pictureList = MapUtils.getString(water,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                water.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                water.put("pictureList", memberMerge);
            }
        }else{
            water.put("pictureList", "");
        }

        water.put("waterConservancyFacilitiesInfoName",MapUtils.getString(params,"waterConservancyFacilitiesInfoName"));
        water.put("yearId",MapUtils.getString(params,"yearId"));
        water.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveWaterConservancyFacilitiesInfo(water);

    }


    @Override
    public void deleteWaterConservancyFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteWaterConservancyFacilitiesInfo(params);
    }



    /**
     * 垃圾处理设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findGarbageDisposaFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //垃圾处理设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findGarbageDisposaFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> garbage:list ){
                garbage.put("status","1".equals(MapUtils.getString(garbage,"status"))? true : false);
                String pictureList = MapUtils.getString(garbage,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    garbage.put("pictureList",members);
                }else{
                    garbage.put("pictureList", new String[]{});
                }
            }
            //map.put("garbageDisposaFacilitiesInfoName",MapUtils.getString(list.get(0),"garbageDisposaFacilitiesInfoName"));

            map.put("garbageDisposaFacilitiesInfo",list);


        }else{
            //map.put("garbageDisposaFacilitiesInfoName","");
            map.put("garbageDisposaFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("garbageDisposaFacilitiesInfoName", "垃圾处理设施信息");
        } else {
            map.put("garbageDisposaFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateGarbageDisposaFacilitiesInfoName(Map<String,Object> params){

        params.put("garbageDisposaFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findGarbageDisposaFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateGarbageDisposaFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveGarbageDisposaFacilitiesInfo(params);
        }

    }



    @Override
    public void saveGarbageDisposaFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> garbage = (Map<String, Object>) params.get("garbageDisposaFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        garbage.put("account",user_id);
        String pictureList = MapUtils.getString(garbage,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                garbage.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                garbage.put("pictureList", memberMerge);
            }
        }else{
            garbage.put("pictureList", "");
        }

        garbage.put("garbageDisposaFacilitiesInfoName",MapUtils.getString(params,"garbageDisposaFacilitiesInfoName"));
        garbage.put("yearId",MapUtils.getString(params,"yearId"));
        garbage.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveGarbageDisposaFacilitiesInfo(garbage);

    }


    @Override
    public void deleteGarbageDisposaFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteGarbageDisposaFacilitiesInfo(params);
    }


    /**
     * 安保设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findSecurityFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //安保设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findSecurityFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> security:list ){
                security.put("status","1".equals(MapUtils.getString(security,"status"))? true : false);
                String pictureList = MapUtils.getString(security,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    security.put("pictureList",members);
                }else{
                    security.put("pictureList", new String[]{});
                }
            }
            //map.put("securityFacilitiesInfoName",MapUtils.getString(list.get(0),"securityFacilitiesInfoName"));

            map.put("securityFacilitiesInfo",list);


        }else{
            //map.put("securityFacilitiesInfoName","");
            map.put("securityFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("securityFacilitiesInfoName", "安保设施信息");
        } else {
            map.put("securityFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateSecurityFacilitiesInfoName(Map<String,Object> params){

        params.put("securityFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findSecurityFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateSecurityFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveSecurityFacilitiesInfo(params);
        }

    }



    @Override
    public void saveSecurityFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> security = (Map<String, Object>) params.get("securityFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        security.put("account",user_id);
        String pictureList = MapUtils.getString(security,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                security.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                security.put("pictureList", memberMerge);
            }
        }else{
            security.put("pictureList", "");
        }

        security.put("securityFacilitiesInfoName",MapUtils.getString(params,"securityFacilitiesInfoName"));
        security.put("yearId",MapUtils.getString(params,"yearId"));
        security.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveSecurityFacilitiesInfo(security);

    }


    @Override
    public void deleteSecurityFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteSecurityFacilitiesInfo(params);
    }


    /**
     * 教育设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findEducationFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //教育设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findEducationFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> education:list ){
                education.put("status","1".equals(MapUtils.getString(education,"status"))? true : false);
                String pictureList = MapUtils.getString(education,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    education.put("pictureList",members);
                }else{
                    education.put("pictureList", new String[]{});
                }
            }
            //map.put("educationFacilitiesInfoName",MapUtils.getString(list.get(0),"educationFacilitiesInfoName"));

            map.put("educationFacilitiesInfo",list);


        }else{
            //map.put("educationFacilitiesInfoName","");
            map.put("educationFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("educationFacilitiesInfoName", "教育设施信息");
        } else {
            map.put("educationFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateEducationFacilitiesInfoName(Map<String,Object> params){

        params.put("educationFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findEducationFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateEducationFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveEducationFacilitiesInfo(params);
        }

    }



    @Override
    public void saveEducationFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> education = (Map<String, Object>) params.get("educationFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        education.put("account",user_id);
        String pictureList = MapUtils.getString(education,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                education.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                education.put("pictureList", memberMerge);
            }
        }else{
            education.put("pictureList", "");
        }

        education.put("educationFacilitiesInfoName",MapUtils.getString(params,"educationFacilitiesInfoName"));
        education.put("yearId",MapUtils.getString(params,"yearId"));
        education.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveEducationFacilitiesInfo(education);

    }


    @Override
    public void deleteEducationFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteEducationFacilitiesInfo(params);
    }



    /**
     * 文化设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findCultureFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //文化设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findCultureFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> culture:list ){
                culture.put("status","1".equals(MapUtils.getString(culture,"status"))? true : false);
                String pictureList = MapUtils.getString(culture,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    culture.put("pictureList",members);
                }else{
                    culture.put("pictureList", new String[]{});
                }
            }
            //map.put("cultureFacilitiesInfoName",MapUtils.getString(list.get(0),"cultureFacilitiesInfoName"));

            map.put("cultureFacilitiesInfo",list);


        }else{
            //map.put("cultureFacilitiesInfoName","");
            map.put("cultureFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("cultureFacilitiesInfoName", "文化设施信息");
        } else {
            map.put("cultureFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateCultureFacilitiesInfoName(Map<String,Object> params){

        params.put("cultureFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findCultureFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateCultureFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveEducationFacilitiesInfo(params);
        }

    }



    @Override
    public void saveCultureFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> culture = (Map<String, Object>) params.get("cultureFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        culture.put("account",user_id);
        String pictureList = MapUtils.getString(culture,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                culture.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                culture.put("pictureList", memberMerge);
            }
        }else{
            culture.put("pictureList", "");
        }

        culture.put("cultureFacilitiesInfoName",MapUtils.getString(params,"cultureFacilitiesInfoName"));
        culture.put("yearId",MapUtils.getString(params,"yearId"));
        culture.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveCultureFacilitiesInfo(culture);

    }


    @Override
    public void deleteCultureFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteCultureFacilitiesInfo(params);
    }



    /**
     * 体育设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findSportsFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //体育设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findSportsFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> sports:list ){
                sports.put("status","1".equals(MapUtils.getString(sports,"status"))? true : false);
                String pictureList = MapUtils.getString(sports,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    sports.put("pictureList",members);
                }else{
                    sports.put("pictureList", new String[]{});
                }
            }
            //map.put("sportsFacilitiesInfoName",MapUtils.getString(list.get(0),"sportsFacilitiesInfoName"));

            map.put("sportsFacilitiesInfo",list);


        }else{
            //map.put("sportsFacilitiesInfoName","");
            map.put("sportsFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("sportsFacilitiesInfoName", "体育设施信息");
        } else {
            map.put("sportsFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateSportsFacilitiesInfoName(Map<String,Object> params){

        params.put("sportsFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findSportsFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateSportsFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveSportsFacilitiesInfo(params);
        }

    }



    @Override
    public void saveSportsFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> sports = (Map<String, Object>) params.get("sportsFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        sports.put("account",user_id);
        String pictureList = MapUtils.getString(sports,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                sports.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                sports.put("pictureList", memberMerge);
            }
        }else{
            sports.put("pictureList", "");
        }

        sports.put("sportsFacilitiesInfoName",MapUtils.getString(params,"sportsFacilitiesInfoName"));
        sports.put("yearId",MapUtils.getString(params,"yearId"));
        sports.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveSportsFacilitiesInfo(sports);

    }


    @Override
    public void deleteSportsFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteSportsFacilitiesInfo(params);
    }


    /**
     * 养老福利设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findOldAgeWelfareFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //养老福利设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findOldAgeWelfareFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> oldAge:list ){
                oldAge.put("status","1".equals(MapUtils.getString(oldAge,"status"))? true : false);
                String pictureList = MapUtils.getString(oldAge,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    oldAge.put("pictureList",members);
                }else{
                    oldAge.put("pictureList", new String[]{});
                }
            }
            //map.put("oldAgeWelfareFacilitiesInfoName",MapUtils.getString(list.get(0),"oldAgeWelfareFacilitiesInfoName"));

            map.put("oldAgeWelfareFacilitiesInfo",list);


        }else{
            //map.put("oldAgeWelfareFacilitiesInfoName","");
            map.put("oldAgeWelfareFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("oldAgeWelfareFacilitiesInfoName", "体育设施信息");
        } else {
            map.put("oldAgeWelfareFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateOldAgeWelfareFacilitiesInfoName(Map<String,Object> params){

        params.put("oldAgeWelfareFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findOldAgeWelfareFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateOldAgeWelfareFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveOldAgeWelfareFacilitiesInfo(params);
        }

    }



    @Override
    public void saveOldAgeWelfareFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> oldAge = (Map<String, Object>) params.get("oldAgeWelfareFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        oldAge.put("account",user_id);
        String pictureList = MapUtils.getString(oldAge,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                oldAge.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                oldAge.put("pictureList", memberMerge);
            }
        }else{
            oldAge.put("pictureList", "");
        }

        oldAge.put("oldAgeWelfareFacilitiesInfoName",MapUtils.getString(params,"oldAgeWelfareFacilitiesInfoName"));
        oldAge.put("yearId",MapUtils.getString(params,"yearId"));
        oldAge.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveOldAgeWelfareFacilitiesInfo(oldAge);

    }


    @Override
    public void deleteOldAgeWelfareFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteOldAgeWelfareFacilitiesInfo(params);
    }



    /**
     * 其它便民设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findOtherFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //其它便民设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findOtherFacilitiesInfo(params);
        if(list.size()>0){

            for(Map<String,Object> other:list ){
                other.put("status","1".equals(MapUtils.getString(other,"status"))? true : false);
                String pictureList = MapUtils.getString(other,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    other.put("pictureList",members);
                }else{
                    other.put("pictureList", new String[]{});
                }
            }
            //map.put("otherFacilitiesInfoName",MapUtils.getString(list.get(0),"otherFacilitiesInfoName"));

            map.put("otherFacilitiesInfo",list);


        }else{
            //map.put("otherFacilitiesInfoName","");
            map.put("otherFacilitiesInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("otherFacilitiesInfoName", "其它便民设施信息");
        } else {
            map.put("otherFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }

    @Override
    public void updateOtherFacilitiesInfoName(Map<String,Object> params){

        params.put("otherFacilitiesInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = communalFacilitiesMapper.findOtherFacilitiesInfo(params);
        if(list.size()>0){
            communalFacilitiesMapper.updateOtherFacilitiesInfoName(params);
        }else{
            communalFacilitiesMapper.saveOtherFacilitiesInfo(params);
        }

    }



    @Override
    public void saveOtherFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> other = (Map<String, Object>) params.get("otherFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        other.put("account",user_id);
        String pictureList = MapUtils.getString(other,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                other.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                other.put("pictureList", memberMerge);
            }
        }else{
            other.put("pictureList", "");
        }

        other.put("otherFacilitiesInfoName",MapUtils.getString(params,"otherFacilitiesInfoName"));
        other.put("yearId",MapUtils.getString(params,"yearId"));
        other.put("templateId",MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveOtherFacilitiesInfo(other);

    }


    @Override
    public void deleteOtherFacilitiesInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteOtherFacilitiesInfo(params);
    }

    @Override
    public void saveIotDeviceInfo(Map<String,Object> params) {
        Map<String,Object> iotDevice = (Map<String, Object>) params.get("iotDeviceInfo");
        String account = MapUtils.getString(params,"account");
        iotDevice.put("account", account);
        String pictureList = MapUtils.getString(iotDevice,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                iotDevice.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                iotDevice.put("pictureList", memberMerge);
            }
        }else{
            iotDevice.put("pictureList", "");
        }
        iotDevice.put("yearId", MapUtils.getString(params,"yearId"));
        iotDevice.put("templateId", MapUtils.getString(params,"templateId"));
        communalFacilitiesMapper.saveIotDeviceInfo(iotDevice);

    }

    /**
     * 物联设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findIotDeviceInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id", MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //其它便民设施信息
        List<Map<String,Object>> list = communalFacilitiesMapper.findIotDeviceInfo(params);
        if(list.size()>0) {

            for (Map<String,Object> other:list) {
                other.put("status","1".equals(MapUtils.getString(other,"status"))? true : false);
                String pictureList = MapUtils.getString(other,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)) {
                    String[] members = pictureList.split(";");
                    other.put("pictureList",members);
                } else {
                    other.put("pictureList", new String[]{});
                }
            }
            map.put("iotDeviceInfo", list);

        } else {
            map.put("iotDeviceInfo", new String[]{});
        }

        /*Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("otherFacilitiesInfoName", "其它便民设施信息");
        } else {
            map.put("otherFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/

        if(MapUtils.isEmpty(textPreview)) {
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0) {
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void deleteIotDeviceInfo(Map<String,Object> params){
        communalFacilitiesMapper.deleteIotDeviceInfo(params);
    }


}
