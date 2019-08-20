package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.portal.feign.RelatedServiceFeign;
import com.ovit.nswy.portal.mapper.ShopCommodityTypeInfoMapper;
import com.ovit.nswy.portal.mapper.UpCommodityBasicInfoMapper;
import com.ovit.nswy.portal.model.SpeciesQuery;
import com.ovit.nswy.portal.model.SpeciesResult;
import com.ovit.nswy.portal.service.ShopCommodityTypeInfoService;
import com.ovit.nswy.portal.service.UpCommodityBasicInfoService;
import com.ovit.nswy.portal.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpCommodityBasicInfoServiceImpl implements UpCommodityBasicInfoService {

    @Autowired
    private UpCommodityBasicInfoMapper upCommodityBasicInfoMapper;

    @Autowired
    private RelatedServiceFeign relatedServiceFeign;

    @Override
    public void insertCommodityInfo(Map<String, Object> commodityInfo) {
        //虫害id，可有多个
        String pestId = MapUtils.getString(commodityInfo, "pestData");
        JSONArray pestArray = JSONArray.parseArray(pestId);
        if (pestArray.size() == 0) {
            commodityInfo.put("pestData", "");
        } else {
            String[] pestIds = JsonUtils.getJsonToStringArray(pestId);
            String pestMerge = JsonUtils.stringArrayMerge(pestIds);
            commodityInfo.put("pestData", pestMerge);
        }

        //病害id，可有多个
        String diseaseId = MapUtils.getString(commodityInfo, "diseaseData");
        JSONArray diseaseArray = JSONArray.parseArray(diseaseId);
        if (diseaseArray.size() == 0) {
            commodityInfo.put("diseaseData", "");
        } else {
            String[] diseaseIds = JsonUtils.getJsonToStringArray(diseaseId);
            String diseaseMerge = JsonUtils.stringArrayMerge(diseaseIds);
            commodityInfo.put("diseaseData", diseaseMerge);
        }

        upCommodityBasicInfoMapper.insertCommodityInfo(commodityInfo);
    }

    @Override
    public Map<String, Object> getCommodityInfo(Map<String, Object> queryInfo) {

        Map<String,Object> commodityInfo = upCommodityBasicInfoMapper.getCommodityInfo(queryInfo);

        if (MapUtils.isNotEmpty(commodityInfo)) {

            String pestIds = MapUtils.getString(commodityInfo, "pestData");
            if (StringUtils.isBlank(pestIds)) {
                //没有虫害
                commodityInfo.put("pestData", new String[0]);
                commodityInfo.put("pestTextData", new String[0]);
            } else {
                String[] pestArray = pestIds.split(";");
                StringBuffer sb = new StringBuffer();
                for (String pest : pestArray) {
                    String pestName = getPestList(pest);
                    sb.append(pestName).append(";");
                }
                String pestNames = sb.substring(0, sb.toString().length() - 1);
                String[] pestNameArray = pestNames.split(";");
                commodityInfo.put("pestData", pestArray);
                commodityInfo.put("pestTextData", pestNameArray);
            }

            String diseaseIds = MapUtils.getString(commodityInfo, "diseaseData");
            if (StringUtils.isBlank(diseaseIds)) {
                //没有病害
                commodityInfo.put("diseaseData", new String[0]);
                commodityInfo.put("diseaseTextData", new String[0]);
            } else {
                String[] diseaseArray = diseaseIds.split(";");
                StringBuffer sb = new StringBuffer();
                for (String disease : diseaseArray) {
                    String diseaseName = getDiseaseList(disease);
                    sb.append(diseaseName).append(";");
                }
                String diseaseNames = sb.substring(0, sb.toString().length() - 1);
                String[] diseaseNameArray = diseaseNames.split(";");
                commodityInfo.put("diseaseData", diseaseArray);
                commodityInfo.put("diseaseTextData", diseaseNameArray);
            }
        }


        return commodityInfo;
    }

    @Override
    public void insertProductionInfo(Map<String, Object> productionInfo) {
        upCommodityBasicInfoMapper.insertProductionInfo(productionInfo);
    }

    @Override
    public Map<String, Object> getProductionInfo(Map<String, Object> queryInfo) {
        return upCommodityBasicInfoMapper.getProductionInfo(queryInfo);
    }

    @Override
    public void insertPlaceInfo(Map<String, Object> placeInfo) {
        upCommodityBasicInfoMapper.insertPlaceInfo(placeInfo);
    }

    @Override
    public Map<String, Object> getPlaceInfo(Map<String, Object> queryInfo) {
        return upCommodityBasicInfoMapper.getPlaceInfo(queryInfo);
    }

    @Override
    public void insertQualificationInfo(Map<String, Object> qualificationInfo) {
        //生产或销售许可证，可有多个
        String licensePicture = MapUtils.getString(qualificationInfo, "license");
        JSONArray licenseArray = JSONArray.parseArray(licensePicture);
        if (licenseArray.size() == 0) {
            qualificationInfo.put("license", "");
        } else {
            String[] licenses = JsonUtils.getJsonToStringArray(licensePicture);
            String licensesMerge = JsonUtils.stringArrayMerge(licenses);
            qualificationInfo.put("license", licensesMerge);
        }

        //品种审定编号
        String numberPicture = MapUtils.getString(qualificationInfo, "validationNumber");
        JSONArray numberArray = JSONArray.parseArray(numberPicture);
        if (numberArray.size() == 0) {
            qualificationInfo.put("validationNumber", "");
        } else {
            String[] numbers = JsonUtils.getJsonToStringArray(numberPicture);
            String numbersMerge = JsonUtils.stringArrayMerge(numbers);
            qualificationInfo.put("validationNumber", numbersMerge);
        }

        //产地检疫合格证
        String certificationPicture = MapUtils.getString(qualificationInfo, "certification");
        JSONArray certificationArray = JSONArray.parseArray(certificationPicture);
        if (certificationArray.size() == 0) {
            qualificationInfo.put("certification", "");
        } else {
            String[] certifications = JsonUtils.getJsonToStringArray(certificationPicture);
            String certificationsMerge = JsonUtils.stringArrayMerge(certifications);
            qualificationInfo.put("certification", certificationsMerge);
        }

        //检疫证书
        String certificatePicture = MapUtils.getString(qualificationInfo, "certificate");
        JSONArray certificateArray = JSONArray.parseArray(certificatePicture);
        if (certificateArray.size() == 0) {
            qualificationInfo.put("certificate", "");
        } else {
            String[] certificates = JsonUtils.getJsonToStringArray(certificatePicture);
            String certificatesMerge = JsonUtils.stringArrayMerge(certificates);
            qualificationInfo.put("certificate", certificatesMerge);
        }

        upCommodityBasicInfoMapper.insertQualificationInfo(qualificationInfo);
    }

    @Override
    public Map<String, Object> getQualificationInfo(Map<String, Object> queryInfo) {

        Map<String,Object> qualificationInfo = upCommodityBasicInfoMapper.getQualificationInfo(queryInfo);

        if (MapUtils.isNotEmpty(qualificationInfo)) {

            String licensePicture = MapUtils.getString(qualificationInfo, "license");
            if (StringUtils.isBlank(licensePicture)) {
                //没有生产或销售许可证
                qualificationInfo.put("license", new String[0]);
            } else {
                String[] licenses = licensePicture.split(";");
                qualificationInfo.put("license", licenses);
            }

            String numberPicture = MapUtils.getString(qualificationInfo, "validationNumber");
            if (StringUtils.isBlank(numberPicture)) {
                //没有品种审定编号
                qualificationInfo.put("validationNumber", new String[0]);
            } else {
                String[] numbers = numberPicture.split(";");
                qualificationInfo.put("validationNumber", numbers);
            }

            String certificationPicture = MapUtils.getString(qualificationInfo, "certification");
            if (StringUtils.isBlank(certificationPicture)) {
                //没有产地检疫合格证
                qualificationInfo.put("certification", new String[0]);
            } else {
                String[] certifications = certificationPicture.split(";");
                qualificationInfo.put("certification", certifications);
            }

            String certificatePicture = MapUtils.getString(qualificationInfo, "certificate");
            if (StringUtils.isBlank(certificatePicture)) {
                //没有检疫证书
                qualificationInfo.put("certificate", new String[0]);
            } else {
                String[] certificates = certificatePicture.split(";");
                qualificationInfo.put("certificate", certificates);
            }

        }

        return qualificationInfo;
    }

    @Override
    public void insertHonorInfo(Map<String, Object> honorInfo) {
        //无公害证书，可有多个
        String certificatePicture = MapUtils.getString(honorInfo, "certificate");
        JSONArray certificateArray = JSONArray.parseArray(certificatePicture);
        if (certificateArray.size() == 0) {
            honorInfo.put("certificate", "");
        } else {
            String[] certificates = JsonUtils.getJsonToStringArray(certificatePicture);
            String certificatesMerge = JsonUtils.stringArrayMerge(certificates);
            honorInfo.put("certificate", certificatesMerge);
        }

        honorInfo.put("qualification",initJson(honorInfo,"qualification"));

        upCommodityBasicInfoMapper.insertHonorInfo(honorInfo);
    }

    @Override
    public Map<String, Object> getHonorInfo(Map<String, Object> queryInfo) {
        Map<String,Object> honorInfo = upCommodityBasicInfoMapper.getHonorInfo(queryInfo);

        if (MapUtils.isNotEmpty(honorInfo)) {

            String certificatePicture = MapUtils.getString(honorInfo, "certificate");
            if (StringUtils.isBlank(certificatePicture)) {
                //没有无公害证书
                honorInfo.put("certificate", new String[0]);
            } else {
                String[] certificates = certificatePicture.split(";");
                honorInfo.put("certificate", certificates);
            }
            String qualifications = MapUtils.getString(honorInfo, "qualification");
            if (StringUtils.isBlank(qualifications)) {
                //没有无公害证书
                honorInfo.put("qualification", new String[0]);
            } else {
                String[] qualification = qualifications.split(",");
                honorInfo.put("qualification", qualification);
            }
        }

        return honorInfo;
    }

    @Override
    public void insertCustomContentInfo(Map<String, Object> customInfo) {
        if (StringUtils.isNotBlank(MapUtils.getString(customInfo, "detection_image"))) {

            customInfo.put("detection_image", initJson(customInfo,"detection_image"));
        } else {
            customInfo.put("detection_image", "");
        }
        upCommodityBasicInfoMapper.insertCustomContentInfo(customInfo);
    }

    @Override
    public Map<String, Object> getCustomContentInfo(Map<String, Object> queryInfo) {
        return upCommodityBasicInfoMapper.getCustomContentInfo(queryInfo);
    }

    @Override
    public Map<String,Object> findSafety(Map<String, Object> queryInfo){
        Map<String,Object> safetys = upCommodityBasicInfoMapper.findSafety(queryInfo);
        if(MapUtils.isNotEmpty(safetys)){
            String detection_image = MapUtils.getString(safetys,"detection_image");
            if(StringUtil.isNotBlank(detection_image)){
                String[] detectionImage = detection_image.split(",");
                safetys.put("detection_image",detectionImage);
            }else{
                safetys.put("detection_image",new String[]{});
            }
            if (StringUtils.isNotBlank(MapUtils.getString(safetys, "custom_content"))) {
                JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(safetys, "custom_content"));
                safetys.put("custom_content", qualityArray);
            } else {
                safetys.put("custom_content", new String[0]);
            }
        }

        return safetys;
    }

    private  String initJson(Map<String,Object> map,String key){
        String valueOfMap = MapUtils.getString(map,key);
        List<String> list = JSON.parseArray(valueOfMap,String.class);
        String values = StringUtils.join(list.toArray(),",");
        return values;
    }

    /**
     * 通过病害ID查询病害名称
     * @param varietyId
     * @return
     */
    @Override
    public String getDiseaseList(String diseaseId){
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(diseaseId)) {
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setFid(diseaseId);
            Object data = relatedServiceFeign.listSpeciesDisease(speciesQuery);
            JSONObject jsonObject = JSONObject.parseObject(data.toString());
            List<SpeciesResult> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), SpeciesResult.class);
            if (CollectionUtils.isNotEmpty(objectList)) {
                FName.append(objectList.get(0).getFname());
            }
        }
        return FName.toString();
    }

    /**
     * 通过虫害ID查询虫害名称
     * @param varietyId
     * @return
     */
    @Override
    public String getPestList(String pestId){
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(pestId)) {
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setFid(pestId);
            Object data = relatedServiceFeign.listSpeciesPest(speciesQuery);
            JSONObject jsonObject = JSONObject.parseObject(data.toString());
            List<SpeciesResult> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), SpeciesResult.class);
            if (CollectionUtils.isNotEmpty(objectList)) {
                FName.append(objectList.get(0).getFname());
            }
        }
        return FName.toString();
    }

}
