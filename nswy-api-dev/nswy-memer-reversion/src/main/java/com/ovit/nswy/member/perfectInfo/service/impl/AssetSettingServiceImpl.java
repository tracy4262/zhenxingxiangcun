package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.AssetSettingMapper;
import com.ovit.nswy.member.perfectInfo.mapper.CommunalFacilitiesMapper;
import com.ovit.nswy.member.perfectInfo.mapper.EducationLiveMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.AssetSetingService;
import com.ovit.nswy.member.perfectInfo.service.EducationLiveService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产设置
 */
@Service
public class AssetSettingServiceImpl implements AssetSetingService {

    @Autowired
    private AssetSettingMapper assetSettingMapper;

    @Autowired
    private CommunalFacilitiesMapper communalFacilitiesMapper;


    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 土地使用权限信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findLandUseRightInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //土地使用权限信息
        List<Map<String,Object>> list = assetSettingMapper.findLandUseRightInfo(params);
        if(list.size()>0){

            for(Map<String,Object> landUseRight:list ){
                landUseRight.put("status","1".equals(MapUtils.getString(landUseRight,"status"))? true : false);
                String images = MapUtils.getString(landUseRight,"images");
                if(StringUtil.isNotEmpty(images)){
                    String[] members = images.split(";");
                    landUseRight.put("images",members);
                }else{
                    landUseRight.put("images", new String[]{});
                }
            }
            //map.put("landUseRightInfoName",MapUtils.getString(list.get(0),"landUseRightInfoName"));

            map.put("landUseRightInfo",list);


        }else{
           // map.put("landUseRightInfoName","土地使用权限信息");
            map.put("landUseRightInfo",new String[]{});

        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("landUseRightInfoName", "土地使用权限信息");
        } else {
            map.put("landUseRightInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateLandUseRightInfoName(Map<String,Object> params){

        params.put("landUseRightInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findLandUseRightInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateLandUseRightInfoName(params);
        }else{
            assetSettingMapper.saveLandUseRightInfo(params);
        }

    }



    @Override
    public void saveLandUseRightInfo(Map<String,Object> params){
        Map<String,Object> landUseRight = (Map<String, Object>) params.get("landUseRightInfo");
        String user_id = MapUtils.getString(params,"account");
        landUseRight.put("account",user_id);
        String images = MapUtils.getString(landUseRight,"images");
        if(StringUtil.isNotEmpty(images)) {
            JSONArray pictureListModel = JSONArray.parseArray(images);
            if (pictureListModel.size() == 0) {
                landUseRight.put("images", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(images);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                landUseRight.put("images", memberMerge);
            }
        }else{
            landUseRight.put("images", "");
        }

        landUseRight.put("landUseRightInfoName",MapUtils.getString(params,"landUseRightInfoName"));
        landUseRight.put("yearId",MapUtils.getString(params,"yearId"));
        landUseRight.put("templateId",MapUtils.getString(params,"templateId"));
        assetSettingMapper.saveLandUseRightInfo(landUseRight);

    }


    @Override
    public void deleteLandUseRightInfo(Map<String,Object> params){
        assetSettingMapper.deleteLandUseRightInfo(params);
    }

    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){

            communalFacilitiesMapper.saveTextPreview(textPreview);
        }
    }



    /**
     * 房屋使用权信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findRightToUseHousingInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //房屋使用权信息
        List<Map<String,Object>> list = assetSettingMapper.findRightToUseHousingInfo(params);
        if(list.size()>0){

            for(Map<String,Object> rightToUse:list ){
                rightToUse.put("status","1".equals(MapUtils.getString(rightToUse,"status"))? true : false);
                String images = MapUtils.getString(rightToUse,"images");
                if(StringUtil.isNotEmpty(images)){
                    String[] members = images.split(";");
                    rightToUse.put("images",members);
                }else{
                    rightToUse.put("images", new String[]{});
                }
            }
            //map.put("rightToUseHousingInfoName",MapUtils.getString(list.get(0),"rightToUseHousingInfoName"));

            map.put("rightToUseHousingInfo",list);


        }else{

            //map.put("rightToUseHousingInfoName","房屋使用权信息");
            map.put("rightToUseHousingInfo",new String[]{});


        }
        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("rightToUseHousingInfoName", "房屋使用权信息");
        } else {
            map.put("rightToUseHousingInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateRightToUseHousingInfoName(Map<String,Object> params){

        params.put("rightToUseHousingInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findRightToUseHousingInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateRightToUseHousingInfoName(params);
        }else{
            assetSettingMapper.saveRightToUseHousingInfo(params);
        }

    }



    @Override
    public void saveRightToUseHousingInfo(Map<String,Object> params){
        Map<String,Object> rightToUse = (Map<String, Object>) params.get("rightToUseHousingInfo");
        String user_id = MapUtils.getString(params,"account");
        rightToUse.put("account",user_id);
        String images = MapUtils.getString(rightToUse,"images");
        if(StringUtil.isNotEmpty(images)) {
            JSONArray pictureListModel = JSONArray.parseArray(images);
            if (pictureListModel.size() == 0) {
                rightToUse.put("images", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(images);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                rightToUse.put("images", memberMerge);
            }
        }else{
            rightToUse.put("images", "");
        }

        rightToUse.put("rightToUseHousingInfoName",MapUtils.getString(params,"rightToUseHousingInfoName"));
        rightToUse.put("yearId",MapUtils.getString(params,"yearId"));
        rightToUse.put("templateId",MapUtils.getString(params,"templateId"));

        assetSettingMapper.saveRightToUseHousingInfo(rightToUse);

    }


    @Override
    public void deleteRightToUseHousingInfo(Map<String,Object> params){
        assetSettingMapper.deleteRightToUseHousingInfo(params);
    }




    /**
     * 生产类机械设备信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findProductionMachineInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //生产类机械设备信息
        List<Map<String,Object>> list = assetSettingMapper.findProductionMachineInfo(params);
        if(list.size()>0){

            for(Map<String,Object> production:list ){
                production.put("status","1".equals(MapUtils.getString(production,"status"))? true : false);

            }
            //map.put("productionMachineInfoName",MapUtils.getString(list.get(0),"productionMachineInfoName"));

            map.put("productionMachineInfo",list);


        }else{

            //map.put("productionMachineInfoName","生产类机械设备信息");
            map.put("productionMachineInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("productionMachineInfoName", "生产类机械设备信息");
        } else {
            map.put("productionMachineInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateProductionMachineInfoName(Map<String,Object> params){

        params.put("productionMachineInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findProductionMachineInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateProductionMachineInfoName(params);
        }else{
            assetSettingMapper.saveProductionMachineInfo(params);
        }

    }



    @Override
    public void saveProductionMachineInfo(Map<String,Object> params){
        Map<String,Object> production = (Map<String, Object>) params.get("productionMachineInfo");
        String user_id = MapUtils.getString(params,"account");
        production.put("account",user_id);


        production.put("productionMachineInfoName",MapUtils.getString(params,"productionMachineInfoName"));
        production.put("yearId",MapUtils.getString(params,"yearId"));
        production.put("templateId",MapUtils.getString(params,"templateId"));

        assetSettingMapper.saveProductionMachineInfo(production);

    }


    @Override
    public void deleteProductionMachineInfo(Map<String,Object> params){
        assetSettingMapper.deleteProductionMachineInfo(params);
    }



    /**
     * 交通运输类工具信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findTransportationToolsInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //交通运输类工具信息
        List<Map<String,Object>> list = assetSettingMapper.findTransportationToolsInfo(params);
        if(list.size()>0){

            for(Map<String,Object> rightToUse:list ){
                rightToUse.put("status","1".equals(MapUtils.getString(rightToUse,"status"))? true : false);

            }
            //map.put("transportationToolsInfoName",MapUtils.getString(list.get(0),"transportationToolsInfoName"));

            map.put("transportationToolsInfo",list);


        }else{

            //map.put("transportationToolsInfoName","交通运输类工具信息");
            map.put("transportationToolsInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("transportationToolsInfoName", "交通运输类工具信息");
        } else {
            map.put("transportationToolsInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateTransportationToolsInfoName(Map<String,Object> params){

        params.put("transportationToolsInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findTransportationToolsInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateTransportationToolsInfoName(params);
        }else{
            assetSettingMapper.saveTransportationToolsInfo(params);
        }

    }



    @Override
    public void saveTransportationToolsInfo(Map<String,Object> params){
        Map<String,Object> production = (Map<String, Object>) params.get("transportationToolsInfo");
        String user_id = MapUtils.getString(params,"account");
        production.put("account",user_id);

        production.put("transportationToolsInfoName",MapUtils.getString(params,"transportationToolsInfoName"));
        production.put("yearId",MapUtils.getString(params,"yearId"));
        production.put("templateId",MapUtils.getString(params,"templateId"));

        assetSettingMapper.saveTransportationToolsInfo(production);

    }


    @Override
    public void deleteTransportationToolsInfo(Map<String,Object> params){
        assetSettingMapper.deleteTransportationToolsInfo(params);
    }



    /**
     * 家用资产信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findHouseholdAssetsInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //家用资产信息
        List<Map<String,Object>> list = assetSettingMapper.findHouseholdAssetsInfo(params);
        if(list.size()>0){

            for(Map<String,Object> householdAssets:list ){
                householdAssets.put("status","1".equals(MapUtils.getString(householdAssets,"status"))? true : false);

            }
            //map.put("householdAssetsInfoName",MapUtils.getString(list.get(0),"householdAssetsInfoName"));

            map.put("householdAssetsInfo",list);


        }else{

            //map.put("householdAssetsInfoName","家用资产信息");
            map.put("householdAssetsInfo",new String[]{});


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("householdAssetsInfoName", "家用资产信息");
        } else {
            map.put("householdAssetsInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateHouseholdAssetsInfoName(Map<String,Object> params){

        params.put("householdAssetsInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findHouseholdAssetsInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateHouseholdAssetsInfoName(params);
        }else{
            assetSettingMapper.saveHouseholdAssetsInfo(params);
        }

    }



    @Override
    public void saveHouseholdAssetsInfo(Map<String,Object> params){
        Map<String,Object> householdAssets = (Map<String, Object>) params.get("householdAssetsInfo");
        String user_id = MapUtils.getString(params,"account");
        householdAssets.put("account",user_id);

        householdAssets.put("householdAssetsInfoName",MapUtils.getString(params,"householdAssetsInfoName"));
        householdAssets.put("yearId",MapUtils.getString(params,"yearId"));
        householdAssets.put("templateId",MapUtils.getString(params,"templateId"));

        assetSettingMapper.saveHouseholdAssetsInfo(householdAssets);

    }


    @Override
    public void deleteHouseholdAssetsInfo(Map<String,Object> params){
        assetSettingMapper.deleteHouseholdAssetsInfo(params);
    }



    /**
     * 设施农业信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findFacilityAgricultureInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //设施农业信息
        List<Map<String,Object>> list = assetSettingMapper.findFacilityAgricultureInfo(params);
        if(list.size()>0){

            List<Map<String,Object>> gardening = new ArrayList<>();

            List<Map<String,Object>> aquaculture = new ArrayList<>();

            List<Map<String,Object>> husbandry = new ArrayList<>();

            List<Map<String,Object>> facilities = new ArrayList<>();


            for(Map<String,Object> facility:list ){
                facility.put("status","1".equals(MapUtils.getString(facility,"status"))? true : false);

                if("0".equals(MapUtils.getString(facility,"type"))){
                    gardening.add(facility);
                }else if("1".equals(MapUtils.getString(facility,"type"))){
                    aquaculture.add(facility);
                }else if("2".equals(MapUtils.getString(facility,"type"))){
                    husbandry.add(facility);
                }else if("3".equals(MapUtils.getString(facility,"type"))){
                    facilities.add(facility);
                }

            }
           // map.put("facilityAgricultureInfoName",MapUtils.getString(list.get(0),"facilityAgricultureInfoName"));

            map.put("gardening",gardening); //设施园艺

            map.put("aquaculture",aquaculture);//水产养殖

            map.put("husbandry",husbandry);//畜牧养殖

            map.put("facilities",facilities);//设施食用菌



        }else{

            //map.put("facilityAgricultureInfoName","设施农业信息");

            map.put("gardening",new ArrayList<>()); //设施园艺

            map.put("aquaculture",new ArrayList<>());//水产养殖

            map.put("husbandry",new ArrayList<>());//畜牧养殖

            map.put("facilities",new ArrayList<>());//设施食用菌


        }

        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("facilityAgricultureInfoName", "设施农业信息");
        } else {
            map.put("facilityAgricultureInfoName", MapUtils.getString(propertyInfo, "propertyName"));
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
    public void updateFacilityAgricultureInfoName(Map<String,Object> params){

        params.put("facilityAgricultureInfoName",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = assetSettingMapper.findFacilityAgricultureInfo(params);
        if(list.size()>0){
            assetSettingMapper.updateFacilityAgricultureInfoName(params);
        }else{
            assetSettingMapper.saveHouseholdAssetsInfo(params);
        }

    }



    @Override
    public void saveFacilityAgricultureInfo(Map<String,Object> params){
        List<Map<String,Object>> facilityList = (List<Map<String,Object>>) params.get("facilityAgricultureInfo");
        String user_id = MapUtils.getString(params,"account");
        for(Map<String,Object> facility:facilityList){
            facility.put("account",user_id);

            facility.put("facilityAgricultureInfoName",MapUtils.getString(params,"facilityAgricultureInfoName"));
            facility.put("yearId",MapUtils.getString(params,"yearId"));
            facility.put("templateId",MapUtils.getString(params,"templateId"));

            assetSettingMapper.saveFacilityAgricultureInfo(facility);
        }


    }


    @Override
    public void deleteFacilityAgricultureInfo(Map<String,Object> params){
        assetSettingMapper.deleteFacilityAgricultureInfo(params);
    }



}
