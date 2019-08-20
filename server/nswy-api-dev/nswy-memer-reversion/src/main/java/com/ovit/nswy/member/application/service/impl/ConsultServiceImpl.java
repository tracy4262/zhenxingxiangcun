package com.ovit.nswy.member.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.application.mapper.ConsultMapper;
import com.ovit.nswy.member.application.mapper.EmployMapper;
import com.ovit.nswy.member.application.mapper.MyRecommendMapper;
import com.ovit.nswy.member.application.service.ConsultService;
import com.ovit.nswy.member.application.service.EmployService;
import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ConsultServiceImpl implements ConsultService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private ProductionBaseService productionBaseService;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private MyRecommendMapper myRecommendMapper;

    /**
     * 咨询服务发布第一步
     * @param params
     * @return
     */
    @Override
    public void publishStepOne(Map<String, Object> params) {

        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.put("id", null);
            consultMapper.publishStepOne(params);
        } else {
            consultMapper.updateStepOne(params);
        }
    }

    /**
     * 咨询服务发布第一步查询
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findStepOne(Integer id) {
        Map<String,Object> stepOneMap = consultMapper.findStepOne(id);
        if (MapUtils.isEmpty(stepOneMap)) {
            stepOneMap = new HashMap<>();
        }
        return stepOneMap;
    }

    @Override
    public List<Map<String, Object>> contactList(Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        params.put("templateId", MapUtils.getString(useTemplate, "templateId"));
        List<Map<String, Object>> list = consultMapper.contactList(params);
        return list;
    }

    /**
     * 咨询服务发布第二步
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void publishStepTwo(Map<String, Object> params) {

        //先更新咨询服务的数据
        //公交路线指引
        String busLead = JSON.toJSONString(params.get("busLead"));
        params.put("busLead", busLead);
        //自驾路线指引
        String carLead = JSON.toJSONString(params.get("carLead"));
        params.put("carLead", carLead);

        Map<String,Object> stepOneMap = consultMapper.getConsultById(params);

        if (MapUtils.isEmpty(stepOneMap)) {
            consultMapper.saveStepTwo(params);

        } else {
            consultMapper.updateStepTwo(params);
        }
        Map<String,Object> commonMap = new HashMap<>();
        commonMap.put("account", MapUtils.getString(params, "account"));
        commonMap.put("consultId", MapUtils.getString(params, "id"));
        //联系人的json
        String contactJson = JSON.toJSONString(params.get("contactInfo"));
        //保存联系人信息
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("contactInfo");
        if (CollectionUtils.isNotEmpty(list)) {
            List<Map<String, Object>> contact = new ArrayList<>();
            for (Map<String,Object> map : list) {
                Map<String,Object> contactMap = new HashMap<>();
                contactMap.putAll(commonMap);
                String images = MapUtils.getString(map, "image");
                JSONArray image = JSONArray.parseArray(images);
                if (image.size() == 0) {
                    map.put("image", "");
                } else {
                    String[] pictures = ImageUtil.getJsonToStringArray(images);
                    String pictureMerge = ImageUtil.stringArrayMerge(pictures);
                    map.put("image", pictureMerge);
                }
                contactMap.putAll(map);
                contact.add(contactMap);
            }
            //先删除原有数据
            consultMapper.deleteContactInfo(commonMap);
            //批量保存联系人信息
            consultMapper.batchSaveContact(contact);
        } else {
            //先删除原有数据
            consultMapper.deleteContactInfo(commonMap);
        }
        //更细联系人、专家个人照片到fishing_service表中
        String personalPicture = MapUtils.getString(params, "personalPicture");
        Map<String,Object> updateImgAndContact = new HashMap<>();
        updateImgAndContact.put("img", personalPicture);
        updateImgAndContact.put("contact", contactJson);
        updateImgAndContact.put("id", MapUtils.getString(params, "id"));
        updateImgAndContact.put("serviceName", MapUtils.getString(params, "serviceName"));
        consultMapper.updateImgAndContact(updateImgAndContact);

    }

    /**
     * 咨询服务发布第二步查询
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findStepTwo(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> commonMap = new HashMap<>();
        //查询该用户的专家认证信息，包含：专家名称、个人照片、专家分类、擅长领域、相关物种
        productionBaseService.getYearIdAndTemplateId(params);
        commonMap = consultMapper.getExpertInfo(params);
        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            resultMap.putAll(commonMap);
        } else {
            Map<String,Object> stepTwoMap = consultMapper.findStepTwo(params);
            if (MapUtils.isEmpty(stepTwoMap)) {
                resultMap.putAll(commonMap);
            } else {
                //公交路线指引
                JSONArray busLead = JSONArray.parseArray(MapUtils.getString(stepTwoMap, "busLead"));
                stepTwoMap.put("busLead", busLead);
                //自驾路线指引
                JSONArray carLead = JSONArray.parseArray(MapUtils.getString(stepTwoMap, "carLead"));
                stepTwoMap.put("carLead", carLead);
                resultMap.putAll(stepTwoMap);

                //联系人信息
                List<Map<String, Object>> contactList = consultMapper.findContactInfo(params);
                if (CollectionUtils.isNotEmpty(contactList)) {
                    for (Map<String,Object> map : contactList) {
                        String images = MapUtils.getString(map,"image");
                        if (StringUtil.isNotBlank(images)) {
                            String[] image = images.split(";");
                            map.put("image", image);
                        } else {
                            map.put("image", new String[]{});
                        }
                        //权限修改为布尔类型
                        for(Map.Entry<String, Object> entry : map.entrySet()) {
                            String key = entry.getKey();
                            if (key.endsWith("_status")) {
                                Integer status = MapUtils.getInteger(map, key);
                                map.put(key, (status == 1) ? true : false);
                            }
                        }
                    }
                    resultMap.put("contactInfo", contactList);

                } else {
                    resultMap.put("contactInfo", new ArrayList<>());
                }
            }
        }

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> networkStationList(Map<String, Object> params) {
        productionBaseService.getYearIdAndTemplateId(params);
        List<Map<String, Object>> list = consultMapper.networkStationList(params);
        for (Map<String,Object> map : list) {
            ConvertDataUtil.merge(map, "networkType");
            map.put("outletsId", MapUtils.getString(map, "outletsId"));

        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void publishStepThree(Map<String, Object> params) {

        //先更新咨询服务的数据
        //收费方式
        String chargeType = JSON.toJSONString(params.get("chargeType"));
        params.put("chargeType", chargeType);
        //上门服务
        String doorServiceData = JSON.toJSONString(params.get("doorServiceData"));
        params.put("doorServiceData", doorServiceData);
        //电话服务
        String telephoneServiceData = JSON.toJSONString(params.get("telephoneServiceData"));
        params.put("telephoneServiceData", telephoneServiceData);
        //网络服务
        String networkServiceData = JSON.toJSONString(params.get("networkServiceData"));
        params.put("networkServiceData", networkServiceData);

        Map<String,Object> locationServiceData = MapUtils.getMap(params, "locationServiceData");
        params.put("locationTime", MapUtils.getString(locationServiceData, "time"));
        params.put("locationTimeStatus", MapUtils.getString(locationServiceData, "timeStatus"));

        consultMapper.updateStepThree(params);

        Map<String,Object> commonMap = new HashMap<>();
        commonMap.put("account", MapUtils.getString(params, "account"));
        commonMap.put("consultId", MapUtils.getString(params, "id"));
        //保存营业网点信息
        List<Map<String, Object>> list = (List<Map<String, Object>>) locationServiceData.get("networkStationInfo");
        if (CollectionUtils.isNotEmpty(list)) {
            List<Map<String, Object>> networkList = new ArrayList<>();
            for (Map<String,Object> map : list) {
                Map<String,Object> networkMap = new HashMap<>();
                networkMap.putAll(commonMap);
                ConvertDataUtil.split(map, "networkType");
                networkMap.putAll(map);
                networkList.add(networkMap);
            }
            //先删除原有数据
            consultMapper.deleteNetworkInfo(commonMap);
            //批量保存联系人信息
            consultMapper.batchSaveNetwork(networkList);
        } else {
            //先删除原有数据
            consultMapper.deleteNetworkInfo(commonMap);
        }
    }

    @Override
    public Map<String, Object> findStepThree(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        if (StringUtils.isNotBlank(MapUtils.getString(params, "id"))) {
            Map<String, Object> stepThree = consultMapper.findStepThree(params);
            if (MapUtils.isNotEmpty(stepThree)) {
                JSONArray chargeType = JSONArray.parseArray(MapUtils.getString(stepThree, "chargeType"));
                stepThree.put("chargeType", chargeType);
                stepThree.put("doorService", (MapUtils.getInteger(stepThree, "doorService") == 1) ? true : false);
                stepThree.put("locationService", (MapUtils.getInteger(stepThree, "locationService") == 1) ? true : false);
                stepThree.put("telephoneService", (MapUtils.getInteger(stepThree, "telephoneService") == 1) ? true : false);
                stepThree.put("networkService", (MapUtils.getInteger(stepThree, "networkService") == 1) ? true : false);

                stepThree.put("doorServiceData", JSON.parseObject(MapUtils.getString(stepThree, "doorServiceData")));
                stepThree.put("telephoneServiceData", JSON.parseObject(MapUtils.getString(stepThree, "telephoneServiceData")));
                stepThree.put("networkServiceData", JSON.parseObject(MapUtils.getString(stepThree, "networkServiceData")));

                //营业网点
                //联系人信息
                List<Map<String, Object>> networkList = consultMapper.findNetworkInfo(params);
                Map<String,Object> locationServiceData = new HashMap<>();
                locationServiceData.put("timeStatus", MapUtils.getString(stepThree, "locationTimeStatus"));
                locationServiceData.put("time", MapUtils.getString(stepThree, "locationTime"));
                if (CollectionUtils.isNotEmpty(networkList)) {
                    for (Map<String,Object> map : networkList) {
                        ConvertDataUtil.merge(map, "networkType");
                    }
                    locationServiceData.put("networkStationInfo", networkList);

                } else {
                    locationServiceData.put("networkStationInfo", new ArrayList<>());
                }
                stepThree.put("locationServiceData", locationServiceData);
                resultMap.putAll(stepThree);
            }

        }
        return resultMap;
    }

    @Override
    public void publishStepFour(Map<String, Object> params) {
        consultMapper.updateStepFour(params);

        //发布完的服务, 默认为已推荐
        Map<String,Object> recommend = new HashMap<>();
        recommend.put("account", MapUtils.getString(params, "account"));
        recommend.put("id", MapUtils.getString(params, "id"));
        recommend.put("type", 1);
        //判断是否已推荐
        Map<String, Object> hadRecommend = myRecommendMapper.isHadRecommend(recommend);
        if (MapUtils.isEmpty(hadRecommend)) {
            myRecommendMapper.singleSaveRecommend(recommend);
        }


    }

    @Override
    public Map<String, Object> findStepFour(Map<String, Object> params) {

        Map<String,Object> stepFour = consultMapper.findStepFour(params);
        if (MapUtils.isEmpty(stepFour)) {
            stepFour = new HashMap<>();
        }
        return stepFour;
    }

    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        params.put("type", 5);
        return consultMapper.list(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> detail(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        //先查询服务名、专家等信息，baseData
        Map<String, Object> stepTwo = consultMapper.findStepTwo(params);
        Map<String,Object> baseData = new HashMap<>();
        if (MapUtils.isNotEmpty(stepTwo)) {
            baseData.put("personalPicture", MapUtils.getString(stepTwo, "personalPicture"));
            baseData.put("expertName", MapUtils.getString(stepTwo, "expert"));
            baseData.put("serviceName", MapUtils.getString(stepTwo, "serviceName"));
            baseData.put("adeptField", MapUtils.getString(stepTwo, "adeptField"));
            baseData.put("relatedSpecies", MapUtils.getString(stepTwo, "relatedSpecies"));
            baseData.put("relatedExperience", MapUtils.getString(stepTwo, "relatedExperience"));
        }
        resultMap.put("baseData", baseData);
        resultMap.put("serviceIntroduction", MapUtils.getString(stepTwo, "serviceDescribe"));


        //获取第二步的收费方式, chargeType
        Map<String, Object> stepThree = consultMapper.findStepThree(params);
        JSONArray chargeType = JSONArray.parseArray(MapUtils.getString(stepThree, "chargeType"));
        resultMap.put("chargeType", chargeType);

        resultMap.put("doorServiceData", JSON.parseObject(MapUtils.getString(stepThree, "doorServiceData")));
        resultMap.put("telephoneServiceData", JSON.parseObject(MapUtils.getString(stepThree, "telephoneServiceData")));
        resultMap.put("networkServiceData", JSON.parseObject(MapUtils.getString(stepThree, "networkServiceData")));

        resultMap.put("serviceType", MapUtils.getString(stepThree, "serviceType"));

        resultMap.put("doorService", (MapUtils.getInteger(stepThree, "doorService") == 1) ? true : false);
        resultMap.put("locationService", (MapUtils.getInteger(stepThree, "locationService") == 1) ? true : false);
        resultMap.put("telephoneService", (MapUtils.getInteger(stepThree, "telephoneService") == 1) ? true : false);
        resultMap.put("networkService", (MapUtils.getInteger(stepThree, "networkService") == 1) ? true : false);

        //营业网点
        //联系人信息
        List<Map<String, Object>> networkList = consultMapper.findNetworkInfo(params);
        Map<String,Object> locationServiceData = new HashMap<>();
        locationServiceData.put("timeStatus", MapUtils.getString(stepThree, "locationTimeStatus"));
        locationServiceData.put("time", MapUtils.getString(stepThree, "locationTime"));
        if (CollectionUtils.isNotEmpty(networkList)) {
            List<Map<String,Object>> netList = new ArrayList<>();
            for (Map<String,Object> map : networkList) {
                Map<String,Object> networkMap = new HashMap<>();
                networkMap.put("name", MapUtils.getString(map, "networkName"));
                netList.add(networkMap);
            }
            locationServiceData.put("networkStationInfo", netList);

        } else {
            locationServiceData.put("networkStationInfo", new ArrayList<>());
        }
        resultMap.put("locationServiceData", locationServiceData);

        int id = MapUtils.getInteger(params, "id");
        Map<String, Object> stepOne = consultMapper.findStepOne(id);
        resultMap.put("generalInfo", stepOne);
        resultMap.put("account", MapUtils.getString(stepOne, "account"));

        Map<String, Object> stepFour = consultMapper.findStepFour(params);
        resultMap.put("serviceInfo", stepFour);

        Map<String,Object> mapInfo = new HashMap<>();
        //公交路线指引
        JSONArray busLead = JSONArray.parseArray(MapUtils.getString(stepTwo, "busLead"));
        mapInfo.put("busLead", busLead);
        //自驾路线指引
        JSONArray carLead = JSONArray.parseArray(MapUtils.getString(stepTwo, "carLead"));
        mapInfo.put("carLead", carLead);
        List<Map<String,Object>> mapList = new ArrayList<>();
        //联系人信息
        List<Map<String, Object>> contactList = consultMapper.findContactInfo(params);
        if (CollectionUtils.isNotEmpty(contactList)) {
            for (Map<String,Object> map : contactList) {
                Map<String,Object> address = new HashMap<>();
                address.put("longitude", MapUtils.getString(map, "longitude"));
                address.put("latitude", MapUtils.getString(map, "latitude"));
                mapList.add(address);
            }
        }
        mapInfo.put("map", mapList);
        resultMap.put("mapInfo", mapInfo);

        //查看是否存在未到期的订单
        Map<String, Object> consultOrder = consultMapper.findConsultOrder(params);
        int status = 1;
        if (MapUtils.isNotEmpty(consultOrder)) {
            status = 0;
        }
        resultMap.put("status", status);

        return resultMap;
    }

    @Override
    public void delete(int id) {
        consultMapper.delete(id);
    }

    @Override
    public Integer isExpert(Map<String, Object> params) {
        productionBaseService.getYearIdAndTemplateId(params);
        List<Map<String,Object>> list = consultMapper.isExpert(params);
        Integer result = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            result = 1;
        }
        return result;
    }

    @Override
    public Map<String, Object> serviceList(Map<String, Object> params) {

        //PageUtil.initPage(params);
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;
        Integer number = MapUtils.getInteger(params, "pageNum");
        int pageNum = (number == null) ? 1 : number;
        params.remove("pageSize");
        params.remove("pageNum");
        List<Map<String, Object>> list = consultMapper.serviceList(params);
        String address = MapUtils.getString(params, "address");
        if (StringUtils.isNotBlank(address)) {
            //根据行政区划筛选，匹配发布咨询服务时选择的联系人的地址
            Iterator<Map<String,Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                int id = MapUtils.getInteger(map, "id");
                Map<String,Object> concact = new HashMap<>();
                concact.put("id", id);
                concact.put("address", address);
                List<Map<String, Object>> contactInfo = consultMapper.findContactInfo(concact);
                if (CollectionUtils.isEmpty(contactInfo)) {
                    iterator.remove();
                }
            }
        }
        int totalSize = list.size();
        List<Map<String, Object>> pageList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                pageList = PageUtil.pageForList(pageNum, pageSize, list);
            } catch (Exception e) {
                logger.error("查询数据异常:{]", e);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("list", pageList);
        resultMap.put("total", totalSize);

        return resultMap;
    }
}
