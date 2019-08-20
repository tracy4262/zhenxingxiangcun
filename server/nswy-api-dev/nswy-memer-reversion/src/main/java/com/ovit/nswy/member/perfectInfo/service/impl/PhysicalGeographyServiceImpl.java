package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.PhysicalGeographyService;
import com.ovit.nswy.member.util.ImageUtil;
import com.qq.connect.utils.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 自然地理
 */
@Service
public class PhysicalGeographyServiceImpl implements PhysicalGeographyService {

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;


    @Override
    public Map<String,Object> findGeographicalPosition(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));


        String[] string = new String[]{"会员所在地","会员所在地经纬度","会员四邻","查看实况地址"};
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {

            map.put("propertyName", string);
        } else {
            String pictureList =MapUtils.getString(propertyInfo, "propertyName");
            if(StringUtil.isNotEmpty(pictureList)){
                String[] members = pictureList.split(";");
                map.put("propertyName",members);
            }else{
                map.put("propertyName", new String[]{});
            }


        }

        List<Map<String,Object>> memberLocation =  physicalGeographyMapper.findMemberLocation(params);


        if(CollectionUtils.isNotEmpty(memberLocation)){
            Map<String,Object> locationMap = new HashMap<>();
            //locationMap.put("memberLocation_name",MapUtils.getString(memberLocation.get(0),"member_location_name"));
            map.put("memberLocation",memberLocation.get(0));
            map.put("memberLocationStatus","1".equals(MapUtils.getString(memberLocation.get(0),"status"))? true : false);

        }else{
            map.put("memberLocation",new HashMap<>());
            map.put("memberLocationStatus", true );

        }

        List<Map<String,Object>> memberLatitudeLongitude =  physicalGeographyMapper.findMemberLatitudeLongitude(params);
        if(CollectionUtils.isNotEmpty(memberLatitudeLongitude)){
            Map<String,Object> locationMap = new HashMap<>();
            //locationMap.put("memberLocation_name",MapUtils.getString(memberLocation.get(0),"member_location_name"));
            map.put("memberLatitudeLongitude",memberLatitudeLongitude.get(0));
            map.put("memberLatitudeLongitudeStatus","1".equals(MapUtils.getString(memberLatitudeLongitude.get(0),"status"))? true : false);

        }else{
            map.put("memberLatitudeLongitude",new HashMap<>());
            map.put("memberLatitudeLongitudeStatus", true );

        }

        List<Map<String,Object>> memberNeighbor =  physicalGeographyMapper.findMemberNeighbor(params);
        if(CollectionUtils.isNotEmpty(memberNeighbor)){
            Map<String,Object> locationMap = new HashMap<>();
            //locationMap.put("memberLocation_name",MapUtils.getString(memberLocation.get(0),"member_location_name"));
            map.put("memberNeighbor",memberNeighbor);
            map.put("memberNeighborStatus","1".equals(MapUtils.getString(memberNeighbor.get(0),"status"))? true : false);

        }else{

            List<Map<String,Object>> neighborList = new ArrayList<>();

            Map<String,Object> nameMap = new HashMap<>();
            nameMap.put("name","最东点经纬度");
            neighborList.add(nameMap);

            Map<String,Object> nameMap1 = new HashMap<>();
            nameMap1.put("name","最南点经纬度");
            neighborList.add(nameMap1);

            Map<String,Object> nameMap2 = new HashMap<>();
            nameMap2.put("name","最西点经纬度");
            neighborList.add(nameMap2);

            Map<String,Object> nameMap3 = new HashMap<>();
            nameMap3.put("name","最北点经纬度");
            neighborList.add(nameMap3);


            map.put("memberNeighbor",neighborList);
            map.put("memberNeighborStatus", true );

        }

        List<Map<String,Object>> memberLiveAddress =  physicalGeographyMapper.findMemberLiveAddress(params);
        if(CollectionUtils.isNotEmpty(memberLiveAddress)){
            Map<String,Object> locationMap = new HashMap<>();
            //locationMap.put("memberLocation_name",MapUtils.getString(memberLocation.get(0),"member_location_name"));
            map.put("memberLiveAddress",memberLiveAddress);
            map.put("memberLiveAddressStatus","1".equals(MapUtils.getString(memberLiveAddress.get(0),"status"))? true : false);

        }else{
            map.put("memberLiveAddress",new ArrayList<>());
            map.put("memberLiveAddressStatus", true );

        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }


        if(MapUtils.isNotEmpty(textPreview)){

            map.put("textPreview",textPreview);

        }else{

            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);

        }

        return map;
    }


    @Override
    public void saveGeographicalPosition(Map<String,Object> params){
        String user_id = MapUtils.getString(params,"user_id");
        //会员所在地
        Map<String,Object> memberLocations = MapUtils.getMap(params,"memberLocation");

        Map<String,Object> memberLocation = (Map<String,Object>)memberLocations.get("memberLocation");
        memberLocation.put("user_id",user_id);
        memberLocation.put("member_location_name",MapUtils.getString(memberLocations,"memberLocation_name"));
        memberLocation.put("year_id",MapUtils.getString(params,"yearId"));
        memberLocation.put("status",MapUtils.getBoolean(memberLocations,"status"));
        memberLocation.put("templateId",MapUtils.getString(params,"templateId"));
        physicalGeographyMapper.saveMemberLocation(memberLocation);

        //会员所在地经纬度
        Map<String,Object> memberLatitudeLongitudes = MapUtils.getMap(params,"memberLatitudeLongitude");
        Map<String,Object> memberLatitudeLongitude = ( Map<String,Object>)memberLatitudeLongitudes.get("memberLatitudeLongitude");
        memberLatitudeLongitude.put("user_id",user_id);
        memberLatitudeLongitude.put("member_latitude_longitude_name",MapUtils.getString( memberLatitudeLongitudes,"memberLatitudeLongitude_name"));
        memberLatitudeLongitude.put("year_id",MapUtils.getString(params,"yearId"));
        memberLatitudeLongitude.put("status",MapUtils.getBoolean(memberLatitudeLongitudes,"status"));
        memberLatitudeLongitude.put("templateId",MapUtils.getString(params,"templateId"));
        physicalGeographyMapper.saveMemberLatitudeLongitude(memberLatitudeLongitude);


        //会员四邻
        Map<String,Object> memberNeighbors =(Map<String, Object>) params.get("memberNeighbor");
        List<Map<String,Object>> memberNeighborList = (List<Map<String,Object>>)memberNeighbors.get("memberNeighbor");

        if(CollectionUtils.isNotEmpty(memberNeighborList)){
            for(Map<String,Object> map :memberNeighborList ){
                map.put("user_id",user_id);
                map.put("member_neighbor_name",MapUtils.getString(memberNeighbors,"memberNeighbor_name"));
                map.put("year_id",MapUtils.getString(params,"yearId"));
                map.put("status",MapUtils.getBoolean(memberNeighbors,"status"));
                map.put("templateId",MapUtils.getString(params,"templateId"));
                physicalGeographyMapper.saveMemberNeighbor(map);
            }
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("user_id",user_id);
            map.put("member_neighbor_name",MapUtils.getString(memberNeighbors,"memberNeighbor_name"));
            map.put("year_id",MapUtils.getString(params,"yearId"));
            map.put("status",MapUtils.getBoolean(memberNeighbors,"status"));
            map.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveMemberNeighbor(map);
        }




        //查看实况地址
        Map<String,Object> memberLiveAddres =(Map<String, Object>) params.get("memberLiveAddress");
        List<Map<String,Object>> memberLiveAddressList = (List<Map<String, Object>>) memberLiveAddres.get("memberLiveAddress");
        if(CollectionUtils.isNotEmpty(memberLiveAddressList)){
            for(Map<String,Object> map :memberLiveAddressList ){
                map.put("user_id",user_id);
                map.put("live_address_name",MapUtils.getString(memberLiveAddres,"memberLiveAddress_name"));
                map.put("year_id",MapUtils.getString(params,"yearId"));
                map.put("status",MapUtils.getBoolean(memberLiveAddres,"status"));
                map.put("templateId",MapUtils.getString(params,"templateId"));
                physicalGeographyMapper.saveMemberLiveAddress(map);
            }
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("user_id",user_id);
            map.put("live_address_name",MapUtils.getString(memberLiveAddres,"memberLiveAddress_name"));
            map.put("year_id",MapUtils.getString(params,"yearId"));
            map.put("status",MapUtils.getBoolean(memberLiveAddres,"status"));
            map.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveMemberLiveAddress(map);
        }



        saveTextPreview(params);


    }

    @Override
    public void deleteMemberNeighbor(Map<String,Object> params){
        physicalGeographyMapper.deleteMemberNeighbor(params);
    }

    @Override
    public void deleteMemberLiveAddress(Map<String,Object> params){
        physicalGeographyMapper.deleteMemberLiveAddress(params);
    }

    private void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveTextPreview(textPreview);
        }

    }

    @Override
    public void updateMemberLocationName(Map<String,Object> params){

        /**
         * 判断表中是否存在数据，如果有表示不是第一次存储， 则直接更新， 反之， 保存
         */
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("member_location_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> memberLocation =  physicalGeographyMapper.findMemberLocation(map);
        if(CollectionUtils.isNotEmpty(memberLocation)){
            physicalGeographyMapper.updateMemberLocationName(params);
        }else{
            physicalGeographyMapper.saveMemberLocation(params);
        }


    }



    @Override
    public void updateMemberLatitudeLongitudeName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("member_latitude_longitude_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> memberLatitudeLongitude =  physicalGeographyMapper.findMemberLatitudeLongitude(map);

        if(CollectionUtils.isNotEmpty(memberLatitudeLongitude)){
            physicalGeographyMapper.updateMemberLatitudeLongitudeName(params);
        }else{
            physicalGeographyMapper.saveMemberLatitudeLongitude(params);
        }


    }



    @Override
    public void updateMemberNeighborName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("member_neighbor_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> memberNeighbor =  physicalGeographyMapper.findMemberNeighbor(map);

        if(CollectionUtils.isNotEmpty(memberNeighbor)){
            physicalGeographyMapper.updateMemberNeighborName(params);
        }else{
            physicalGeographyMapper.saveMemberNeighbor(params);
        }

    }



    @Override
    public void updateMemberLiveAddressName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("live_address_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> memberLiveAddress =  physicalGeographyMapper.findMemberLiveAddress(map);

        if(CollectionUtils.isNotEmpty(memberLiveAddress)){
            physicalGeographyMapper.updateMemberLiveAddressName(params);
        }else{
            physicalGeographyMapper.saveMemberLiveAddress(params);
        }

    }

    @Override
    public Map<String,Object> findLandAreaInfo(Map<String,Object> params){
        /**
         *
         判断reversion_land_area_info表中是否存在数据， 如果存在数据取reversion_land_area_info表中land_area_name为模块属性 反之查询sys_dict表
         */
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("landAreaInfo_name", "国土面积信息");
        } else {
            map.put("landAreaInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = physicalGeographyMapper.findLandAreaInfo(params);
        if(CollectionUtils.isNotEmpty(list)){

            map.put("status","1".equals(MapUtils.getString(list.get(0),"status"))? true : false);
            map.put("landAreaInfo",list);

        }else{
            List<Map<String,Object>> landList = new ArrayList<>();

            Map<String,Object> nameMap = new HashMap<>();
            nameMap.put("land_area","国土总面积");
            landList.add(nameMap);

            Map<String,Object> nameMap1 = new HashMap<>();
            nameMap1.put("land_area","山地");
            landList.add(nameMap1);

            Map<String,Object> nameMap2 = new HashMap<>();
            nameMap2.put("land_area","盆地");
            landList.add(nameMap2);

            Map<String,Object> nameMap3 = new HashMap<>();
            nameMap3.put("land_area","丘陵");
            landList.add(nameMap3);

            Map<String,Object> nameMap4 = new HashMap<>();
            nameMap4.put("land_area","平原");
            landList.add(nameMap4);

            Map<String,Object> nameMap5 = new HashMap<>();
            nameMap5.put("land_area","高原");
            landList.add(nameMap5);


            map.put("status", true);
            map.put("landAreaInfo",landList);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(textPreview!=null){
            map.put("textPreview",textPreview);


        }else{

            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);


        }

        return map;
    }

    @Override
    public void updateLandAreaInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("land_area_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = physicalGeographyMapper.findLandAreaInfo(map);
        if(CollectionUtils.isNotEmpty(list)){
            physicalGeographyMapper.updateLandAreaInfoName(params);
        }else {
            physicalGeographyMapper.saveLandAreaInfo(params);
        }
    }



    @Override
    public void saveLandAreaInfo(Map<String,Object> params){

        Map<String,Object> landAreaInfos = (Map<String, Object>) params.get("landAreaInfo");
        List<Map<String,Object>> landAreaInfo = (List<Map<String, Object>>) landAreaInfos.get("landAreaInfo");
        String user_id = MapUtils.getString(params,"user_id");
        for(Map<String,Object> map : landAreaInfo){
            map.put("user_id",user_id);
            map.put("land_area_name",MapUtils.getString(landAreaInfos,"landAreaInfo_name"));
            map.put("year_id",MapUtils.getString(params,"yearId"));
            map.put("status",MapUtils.getBoolean(landAreaInfos,"status"));
            map.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveLandAreaInfo(map);
        }
        saveTextPreview(params);


    }

    @Override
    public void deleteLandAreaInfo(Map<String,Object> params){
        physicalGeographyMapper.deleteLandAreaInfo(params);
    }


    @Override
    public Map<String,Object> findTopographicFeature(Map<String,Object> params){
        /**
         *
         判断reversion_topographic_features_info表中是否存在数据， 如果存在数据取reversion_topographic_features_info表中topographic_features_name为模块属性 反之查询sys_dict表
         */
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("topographicFeature_name", "地形地貌信息");
        } else {
            map.put("topographicFeature_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        Map<String,Object> list = physicalGeographyMapper.findTopographicFeature(params);
        if(MapUtils.isNotEmpty(list)){
            String topographic = MapUtils.getString(list,"topographic");
            if(StringUtil.isNotEmpty(topographic)){
                String[] members = topographic.split(";");
                list.put("topographic",members);
            }else{
                list.put("topographic", new String[]{});
            }

            String features = MapUtils.getString(list,"features");
            if(StringUtil.isNotEmpty(features)){
                String[] members = features.split(";");
                list.put("features",members);
            }else{
                list.put("features", new String[]{});
            }
            map.put("status","1".equals(MapUtils.getString(list,"status"))? true : false);
            map.put("topographicFeature",list);
        }else{
            map.put("status",true);

            map.put("topographicFeature",new HashMap<>());
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isNotEmpty(textPreview)){

            map.put("textPreview",textPreview);

        }else{

            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);

        }

        return map;
    }

    @Override
    public void updateTopographicFeatureName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("topographic_features_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> list = physicalGeographyMapper.findTopographicFeature(map);
        if(MapUtils.isNotEmpty(list)){
            physicalGeographyMapper.updateTopographicFeatureName(params);
        }else{
            physicalGeographyMapper.saveTopographicFeature(params);
        }


    }



    @Override
    public void saveTopographicFeature(Map<String,Object> params){
        Map<String,Object> topographicFeature = (Map<String, Object>) params.get("topographicFeature");
        String topographic = MapUtils.getString(topographicFeature,"topographic");
        if(StringUtil.isNotEmpty(topographic)) {
            JSONArray topographic_model = JSONArray.parseArray(topographic);
            if (topographic_model.size() == 0) {
                topographicFeature.put("topographic", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(topographic);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                topographicFeature.put("topographic", memberMerge);
            }
        }else{
            topographicFeature.put("topographic", "");
        }

        String features = MapUtils.getString(topographicFeature,"features");
        if(StringUtil.isNotEmpty(features)) {
            JSONArray features_model = JSONArray.parseArray(features);
            if (features_model.size() == 0) {
                topographicFeature.put("features", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(features);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                topographicFeature.put("features", memberMerge);
            }
        }else{
            topographicFeature.put("features", "");
        }

        String user_id = MapUtils.getString(params,"user_id");
        topographicFeature.put("user_id",user_id);
        topographicFeature.put("topographic_features_name",MapUtils.getString(params,"topographicFeature_name"));
        topographicFeature.put("year_id",MapUtils.getString(params,"yearId"));
        topographicFeature.put("status",MapUtils.getBoolean(params,"status"));
        topographicFeature.put("templateId",MapUtils.getString(params,"templateId"));
        physicalGeographyMapper.saveTopographicFeature(topographicFeature);

        saveTextPreview(params);

    }

    @Override
    public Map<String,Object> findClimateInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("climateInfo_name", "气候信息");
        } else {
            map.put("climateInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        Map<String,Object> list = physicalGeographyMapper.findClimateInfo(params);
        if(MapUtils.isNotEmpty(list)){
            String climate_class = MapUtils.getString(list,"climate_class");
            if(StringUtil.isNotEmpty(climate_class)){
                String[] members = climate_class.split(";");
                list.put("climate_class",members);
            }else{
                list.put("climate_class", new String[]{});
            }

            String sunshine_time = MapUtils.getString(list,"sunshine_time");
            if(StringUtil.isNotEmpty(sunshine_time)){
                String[] members = sunshine_time.split(";");
                list.put("sunshine_time",members);
            }else{
                list.put("sunshine_time", new String[]{});
            }

            String average_temperature = MapUtils.getString(list,"average_temperature");
            if(StringUtil.isNotEmpty(average_temperature)){
                String[] members = average_temperature.split(";");
                list.put("average_temperature",members);
            }else{
                list.put("average_temperature", new String[]{});
            }

            String accumulated_temperature = MapUtils.getString(list,"accumulated_temperature");
            if(StringUtil.isNotEmpty(accumulated_temperature)){
                String[] members = accumulated_temperature.split(";");
                list.put("accumulated_temperature",members);
            }else{
                list.put("accumulated_temperature", new String[]{});
            }

            String diurnal_temperature_difference = MapUtils.getString(list,"diurnal_temperature_difference");
            if(StringUtil.isNotEmpty(diurnal_temperature_difference)){
                String[] members = diurnal_temperature_difference.split(";");
                list.put("diurnal_temperature_difference",members);
            }else{
                list.put("diurnal_temperature_difference", new String[]{});
            }



            String no_frost_date = MapUtils.getString(list,"no_frost_date");
            if(StringUtil.isNotEmpty(no_frost_date)){
                String[] members = no_frost_date.split(";");
                list.put("no_frost_date",members);
            }else{
                list.put("no_frost_date", new String[]{});
            }

            String avg_precipitation = MapUtils.getString(list,"avg_precipitation");
            if(StringUtil.isNotEmpty(avg_precipitation)){
                String[] members = avg_precipitation.split(";");
                list.put("avg_precipitation",members);
            }else{
                list.put("avg_precipitation", new String[]{});
            }

            String avg_vaporization = MapUtils.getString(list,"avg_vaporization");
            if(StringUtil.isNotEmpty(avg_vaporization)){
                String[] members = avg_vaporization.split(";");
                list.put("avg_vaporization",members);
            }else{
                list.put("avg_vaporization", new String[]{});
            }

            String avg_precipitation_day = MapUtils.getString(list,"avg_precipitation_day");
            if(StringUtil.isNotEmpty(avg_precipitation_day)){
                String[] members = avg_precipitation_day.split(";");
                list.put("avg_precipitation_day",members);
            }else{
                list.put("avg_precipitation_day", new String[]{});
            }

            String precipitation_period = MapUtils.getString(list,"precipitation_period");
            if(StringUtil.isNotEmpty(precipitation_period)){
                String[] members = precipitation_period.split(";");
                list.put("precipitation_period",members);
            }else{
                list.put("precipitation_period", new String[]{});
            }
            map.put("climateInfo",list);
            map.put("status","1".equals(MapUtils.getString(list,"status"))? true : false);
        }else{
            map.put("status",true);

            map.put("climateInfo",new HashMap<>());
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isNotEmpty(textPreview)){

            map.put("textPreview",textPreview);

        }else{


            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);

        }

        return map;
    }

    @Override
    public void updateClimateInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("climate_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> list = physicalGeographyMapper.findClimateInfo(map);
        if(MapUtils.isNotEmpty(list)){
            physicalGeographyMapper.updateClimateInfoName(params);
        }else{
            physicalGeographyMapper.saveClimateInfo(params);
        }
    }



    @Override
    public void saveClimateInfo(Map<String,Object> params){
        Map<String,Object> climateInfo = (Map<String, Object>) params.get("climateInfo");

        String climate_class = MapUtils.getString(climateInfo,"climate_class");
        if(StringUtil.isNotEmpty(climate_class)) {
            JSONArray topographic_model = JSONArray.parseArray(climate_class);
            if (topographic_model.size() == 0) {
                climateInfo.put("climate_class", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(climate_class);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("climate_class", memberMerge);
            }
        }else{
            climateInfo.put("climate_class", "");
        }

        String sunshine_time = MapUtils.getString(climateInfo,"sunshine_time");
        if(StringUtil.isNotEmpty(sunshine_time)) {
            JSONArray topographic_model = JSONArray.parseArray(sunshine_time);
            if (topographic_model.size() == 0) {
                climateInfo.put("sunshine_time", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(sunshine_time);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("sunshine_time", memberMerge);
            }
        }else{
            climateInfo.put("sunshine_time", "");
        }

        String average_temperature = MapUtils.getString(climateInfo,"average_temperature");
        if(StringUtil.isNotEmpty(average_temperature)) {
            JSONArray topographic_model = JSONArray.parseArray(average_temperature);
            if (topographic_model.size() == 0) {
                climateInfo.put("average_temperature", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(average_temperature);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("average_temperature", memberMerge);
            }
        }else{
            climateInfo.put("average_temperature", "");
        }

        String accumulated_temperature = MapUtils.getString(climateInfo,"accumulated_temperature");
        if(StringUtil.isNotEmpty(accumulated_temperature)) {
            JSONArray topographic_model = JSONArray.parseArray(accumulated_temperature);
            if (topographic_model.size() == 0) {
                climateInfo.put("accumulated_temperature", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(accumulated_temperature);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("accumulated_temperature", memberMerge);
            }
        }else{
            climateInfo.put("accumulated_temperature", "");
        }

        String diurnal_temperature_difference = MapUtils.getString(climateInfo,"diurnal_temperature_difference");
        if(StringUtil.isNotEmpty(diurnal_temperature_difference)) {
            JSONArray topographic_model = JSONArray.parseArray(diurnal_temperature_difference);
            if (topographic_model.size() == 0) {
                climateInfo.put("diurnal_temperature_difference", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(diurnal_temperature_difference);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("diurnal_temperature_difference", memberMerge);
            }
        }else{
            climateInfo.put("diurnal_temperature_difference", "");
        }


        String no_frost_date = MapUtils.getString(climateInfo,"no_frost_date");
        if(StringUtil.isNotEmpty(no_frost_date)) {
            JSONArray topographic_model = JSONArray.parseArray(no_frost_date);
            if (topographic_model.size() == 0) {
                climateInfo.put("no_frost_date", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(no_frost_date);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("no_frost_date", memberMerge);
            }
        }else{
            climateInfo.put("no_frost_date", "");
        }

        String avg_precipitation = MapUtils.getString(climateInfo,"avg_precipitation");
        if(StringUtil.isNotEmpty(avg_precipitation)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_precipitation);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_precipitation", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_precipitation);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_precipitation", memberMerge);
            }
        }else{
            climateInfo.put("avg_precipitation", "");
        }

        String avg_vaporization = MapUtils.getString(climateInfo,"avg_vaporization");
        if(StringUtil.isNotEmpty(avg_vaporization)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_vaporization);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_vaporization", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_vaporization);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_vaporization", memberMerge);
            }
        }else{
            climateInfo.put("avg_vaporization", "");
        }

        String avg_precipitation_day = MapUtils.getString(climateInfo,"avg_precipitation_day");
        if(StringUtil.isNotEmpty(avg_precipitation_day)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_precipitation_day);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_precipitation_day", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_precipitation_day);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_precipitation_day", memberMerge);
            }
        }else{
            climateInfo.put("avg_precipitation_day", "");
        }

        String precipitation_period = MapUtils.getString(climateInfo,"precipitation_period");
        if(StringUtil.isNotEmpty(precipitation_period)) {
            JSONArray topographic_model = JSONArray.parseArray(precipitation_period);
            if (topographic_model.size() == 0) {
                climateInfo.put("precipitation_period", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(precipitation_period);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("precipitation_period", memberMerge);
            }
        }else{
            climateInfo.put("precipitation_period", "");
        }

        String user_id = MapUtils.getString(params,"user_id");
        climateInfo.put("user_id",user_id);
        climateInfo.put("climate_name",MapUtils.getString(params,"climateInfo_name"));
        climateInfo.put("year_id",MapUtils.getString(params,"yearId"));
        climateInfo.put("status",MapUtils.getBoolean(params,"status"));
        climateInfo.put("templateId",MapUtils.getString(params,"templateId"));
        physicalGeographyMapper.saveClimateInfo(climateInfo);

        saveTextPreview(params);

    }


    @Override
    public Map<String,Object> findBiologicalResources(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("biologicalResources_name", "生物资源信息");
        } else {
            map.put("biologicalResources_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        Map<String,Object> list = physicalGeographyMapper.findBiologicalResources(params);
        if(MapUtils.isNotEmpty(list)){
            JSONArray json = JSONArray.parseArray(MapUtils.getString(list,"animalList"));
            list.put("animalList",json);

            JSONArray animalData = JSONArray.parseArray(MapUtils.getString(list,"animalData"));
            list.put("animalData",animalData);

            JSONArray jsons = JSONArray.parseArray(MapUtils.getString(list,"plantList"));
            list.put("plantList",jsons);

            JSONArray plantData = JSONArray.parseArray(MapUtils.getString(list,"plantData"));
            list.put("plantData",plantData);
            map.put("biologicalResources",list);
            map.put("status","1".equals(MapUtils.getString(list,"status"))? true : false);

        }else{
            Map<String,Object> biologicalMap = new HashMap();
            String plantList = " [{\"plantType\":\"国家Ⅰ级濒危植物\",\"plantName\":[]},{\"plantType\":\"国家Ⅱ级濒危植物\",\"plantName\":[]}]";
            JSONArray json = JSONArray.parseArray(plantList);
            biologicalMap.put("plantList",json);


            String animalList = " [{\"animalType\": \"国家Ⅰ级濒危动物\",\"animalName\": []},{\"animalType\": \"国家Ⅱ级濒危动物\",\"animalName\": []}]";
            JSONArray jsons = JSONArray.parseArray(animalList);
            biologicalMap.put("animalList",jsons);

            map.put("status",true);

            map.put("biologicalResources",biologicalMap);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(textPreview!=null){

            map.put("textPreview",textPreview);


        }else{

            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);

        }

        return map;
    }

    @Override
    public void updateBiologicalResourcesName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("biological_resources_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> list = physicalGeographyMapper.findBiologicalResources(map);
        if(MapUtils.isNotEmpty(list)){
            physicalGeographyMapper.updateBiologicalResourcesName(params);
        }else{
            physicalGeographyMapper.saveBiologicalResources(params);
        }
    }



    @Override
    public void saveBiologicalResources(Map<String,Object> params){
        Map<String,Object> biologicalResource = (Map<String, Object>) params.get("biologicalResources");

        String user_id = MapUtils.getString(params,"user_id");
        biologicalResource.put("user_id",user_id);
        biologicalResource.put("biological_resources_name",MapUtils.getString(params,"biologicalResources_name"));
        biologicalResource.put("year_id",MapUtils.getString(params,"yearId"));
        biologicalResource.put("status",MapUtils.getBoolean(params,"status"));
        biologicalResource.put("templateId",MapUtils.getString(params,"templateId"));

        String animalData  = JSON.toJSONString(biologicalResource.get("animalData"));

        biologicalResource.put("animalData", animalData);


        String animalList  = JSON.toJSONString(biologicalResource.get("animalList"));
        biologicalResource.put("animalList",animalList);


        String plantData  = JSON.toJSONString(biologicalResource.get("plantData"));

        biologicalResource.put("plantData", plantData);


        String plantList  = JSON.toJSONString(biologicalResource.get("plantList"));
        biologicalResource.put("plantList",plantList);



        physicalGeographyMapper.saveBiologicalResources(biologicalResource);



        saveTextPreview(params);

    }

    @Override
    public void deleteBiologicalResources(Map<String,Object> params){

        physicalGeographyMapper.deleteBiologicalResources(params);
    }

    @Override
    public Map<String,Object> findMineralsInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("minerals_name", "地址矿产信息");
        } else {
            map.put("minerals_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        List<Map<String,Object>> list = physicalGeographyMapper.findMineralsInfo(params);
        if(CollectionUtils.isNotEmpty(list)){
            for(Map<String,Object> areaInfo:list){
                String minerals_name = MapUtils.getString(areaInfo,"minerals_name");
                if(StringUtil.isNotEmpty(minerals_name)){
                    String[] members = minerals_name.split(";");
                    areaInfo.put("minerals_name",members);
                }else{
                    areaInfo.put("minerals_name", new String[]{});
                }
            }
            map.put("mineralsInfo",list);
            map.put("status","1".equals(MapUtils.getString(list.get(0),"status"))? true : false);
        }else{
            map.put("status",true);

            map.put("mineralsInfo",new ArrayList<>());
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isNotEmpty(textPreview)){
            map.put("textPreview",textPreview);
        }else{
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);
        }


        return map;
    }

    @Override
    public void updateMineralsInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        map.put("member_minerals_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = physicalGeographyMapper.findMineralsInfo(map);
        if(CollectionUtils.isNotEmpty(list)){
            physicalGeographyMapper.updateMineralsInfoName(params);
        }else{
            physicalGeographyMapper.saveMineralsInfo(params);
        }
    }



    @Override
    public void saveMineralsInfo(Map<String,Object> params){

        List<Map<String,Object>> mineralsInfo = (List<Map<String, Object>>) params.get("mineralsInfo");
        String user_id = MapUtils.getString(params,"user_id");
        for(Map<String,Object> map : mineralsInfo){
            String minerals_name = MapUtils.getString(map,"minerals_name");
            if(StringUtil.isNotEmpty(minerals_name)) {
                JSONArray topographic_model = JSONArray.parseArray(minerals_name);
                if (topographic_model.size() == 0) {
                    map.put("minerals_name", "");
                } else {
                    String[] members = ImageUtil.getJsonToStringArray(minerals_name);
                    String memberMerge = ImageUtil.stringArrayMerge(members);
                    map.put("minerals_name", memberMerge);
                }
            }else{
                map.put("minerals_name", "");
            }
            map.put("user_id",user_id);
            map.put("member_minerals_name",MapUtils.getString(params,"mineralsInfo_name"));
            map.put("year_id",MapUtils.getString(params,"yearId"));
            map.put("status",MapUtils.getBoolean(params,"status"));
            map.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveMineralsInfo(map);
        }

        saveTextPreview(params);

    }

    @Override
    public List<Map<String,Object>> findSysMineralsDictParent(Map<String,Object> params){
        return physicalGeographyMapper.findSysMineralsDictParent(new HashMap<>());
    }

    @Override
    public List<Map<String,Object>> findSysMineralsDict(Map<String,Object> params){
        return physicalGeographyMapper.findSysMineralsDict(params);
    }

}
