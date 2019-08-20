package com.ovit.nswy.member.perfectInfo.service.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.ovit.nswy.member.config.MongoTemplate;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.LandInformationMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.LandInformationService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.BaiDuUtil;
import com.ovit.nswy.member.util.ConvertDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ovit.nswy.member.util.BaiDuUtil.googleConvertBaidu;

@Service
public class LandInformationServiceImpl implements LandInformationService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private LandInformationMapper landInformationMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Override
    public void updateNkyLand(Map<String, Object> params) {

        //查询农科院对应用户的地块信息
        Bson filter = new Document("account", MapUtils.getString(params, "account")).append("year", MapUtils.getString(params, "yearId"));
        List<Map<String,Object>> landList = mongoTemplate.query("marker", filter, Sorts.descending("id"));
        if (CollectionUtils.isNotEmpty(landList)) {

            for (Map<String,Object> map : landList) {
                Map<String,Object> coordinate = (Map<String, Object>) map.get("position");
                //东经
                String longitude = "";
                //北纬
                String latitude = "";
                if (MapUtils.isNotEmpty(coordinate)) {
                    String lng = MapUtils.getString(coordinate, "lng");
                    String lat = MapUtils.getString(coordinate, "lat");
                    Map<String,Object> goolgleMap = new HashMap<>();
                    goolgleMap.put("lng", lng);
                    goolgleMap.put("lat", lat);
                    Map<String,Object> baiduMap = BaiDuUtil.googleConvertBaidu(goolgleMap);
                    longitude = MapUtils.getString(baiduMap, "convertLng");
                    latitude = MapUtils.getString(baiduMap, "convertLat");
                }
                //mongo主键，唯一标识
                String landId = MapUtils.getString(map, "_id");
                //土地编码, 需要平台安装行政编码+5位顺序码生成
                //String landCode = MapUtils.getString(map, "id");
                String landCode = generateLandCode(params);
                //土地名称
                String landName = MapUtils.getString(map, "name");
                //权利人
                String landUser = MapUtils.getString(map, "owner");
                //实测面积
                //String factArea = MapUtils.getString(map, "factArea");
                //航测面积
                //String airArea = MapUtils.getString(map, "airArea");

                params.put("longitude", longitude);
                params.put("latitude", latitude);
                params.put("landId", landId);
                params.put("landName", landName);
                params.put("landCode", landCode);
                params.put("landUser", landUser);
                //params.put("factArea", factArea);
                //params.put("airArea", airArea);

                //判断该地块是否保存到了平台对应的地块信息表中
                List<Map<String,Object>> userLand = landInformationMapper.findLandInfo(params);
                if (CollectionUtils.isEmpty(userLand)) {
                    //系统不存在该地块，进行新增操作
                    landInformationMapper.addNkyLandInfo(params);
                    landInformationMapper.addNkySoilInfo(params);
                    landInformationMapper.addNkySoilQuality(params);
                    landInformationMapper.addNkyWaterQuality(params);
                } else {
                    //预留：更新农科院获取的字段到平台中
                    //landInformationMapper.updateLandInfo(params);

                }
            }
        }
    }

    @Override
    public Map<String, Object> findLandInfo(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> landList = landInformationMapper.findLandInfo(params);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(landList)) {
            landList = new ArrayList<>();
            resultMap.put("list", landList);
        } else {
            resultMap.put("list", landList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateLandInfo(Map<String, Object> params) {

        //更新地块信息
        landInformationMapper.updateLandInfo(params);

    }

    @Override
    public void deleteLandInfo(Map<String, Object> params) {

        //删除地块信息，并且还要删除地块土壤含量、地块土壤质量、水质对应的数据
        landInformationMapper.deleteLandInfo(params);
        //删除地块土壤含量、地块土壤质量、水质对应的数据, 通过landId、account、yearId联合删除
        landInformationMapper.deleteSoilContent(params);
        landInformationMapper.deleteSoilQuality(params);
        landInformationMapper.deleteWaterQuality(params);

        //同时需要将农科院的对应数据的用户名、年度文件ID、权利人置空
        Bson filter = Filters.eq("_id", new ObjectId(MapUtils.getString(params, "landId")));
        Bson update = new Document("$set", new Document("account", "").append("year", "").append("owner", ""));
        mongoTemplate.updateOne("marker", filter, update);

    }

    @Override
    public Map<String, Object> findSoilContent(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> soilList = landInformationMapper.findSoilContent(params);
        for (Map<String, Object> map : soilList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块土壤含量信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(soilList)) {
            soilList = new ArrayList<>();
            resultMap.put("list", soilList);
        } else {
            resultMap.put("list", soilList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public Map<String, Object> findSoilQuality(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> qualityList = landInformationMapper.findSoilQuality(params);
        for (Map<String, Object> map : qualityList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块土壤质量信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(qualityList)) {
            qualityList = new ArrayList<>();
            resultMap.put("list", qualityList);
        } else {
            resultMap.put("list", qualityList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public Map<String, Object> findWaterQuality(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> waterList = landInformationMapper.findWaterQuality(params);
        for (Map<String, Object> map : waterList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块水质信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(waterList)) {
            waterList = new ArrayList<>();
            resultMap.put("list", waterList);
        } else {
            resultMap.put("list", waterList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public Map<String, Object> findLandUse(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        //农用地
        params.put("type", "1");
        List<Map<String, Object>> agricultural = landInformationMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(agricultural)) {
            agricultural = new ArrayList<>();
        }
        resultMap.put("agricultural", agricultural);
        //建设用地
        params.put("type", "2");
        List<Map<String, Object>> construction = landInformationMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(construction)) {
            construction = new ArrayList<>();
        }
        resultMap.put("construction", construction);
        //未利用地
        params.put("type", "3");
        List<Map<String, Object>> future = landInformationMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(future)) {
            future = new ArrayList<>();
        }
        resultMap.put("future", future);

        //获取属性名称
        Map<String, Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "土地利用现状信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;

    }

    @Override
    public void saveLandUse(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }

        landInformationMapper.saveLandUse(list);
    }

    @Override
    public void deleteLandUse(Map<String, Object> params) {

        landInformationMapper.deleteLandUse(params);

    }

    @Override
    public void updateSoilContent(Map<String, Object> params) {

        //更新地块土壤含量信息
        ConvertDataUtil.split(params, "pictureList");
        landInformationMapper.updateSoilContent(params);

    }

    @Override
    public void updateSoilQuality(Map<String, Object> params) {

        //更新地块土壤质量信息
        ConvertDataUtil.split(params, "pictureList");
        landInformationMapper.updateSoilQuality(params);

    }

    @Override
    public void updateWaterQuality(Map<String, Object> params) {

        //更新地块水质信息
        ConvertDataUtil.split(params, "pictureList");
        landInformationMapper.updateWaterQuality(params);

    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        Map<String,Object> textPreview = perfectBasicMapper.findTextPreview(params);
        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account",MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));

            }
            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
            result.put("status", MapUtils.getInteger(textPreview, "status"));
        } else {
            result.put("preview", "");
            result.put("status", 1);
        }

        return result;
    }

    /**
     * 生成地块编码
     * @param params
     * @return
     */
    private String generateLandCode(Map<String, Object> params) {

        //获取会员认证时填写的地址信息
        List<Map<String,Object>> address = landInformationMapper.findAddressInfo(params);
        //通过地址查询到行政区划编码
        Map<String,Object> addressMap = address.get(0);
        String[] addrArr = MapUtils.getString(addressMap,"locationId").split("/");
        String cutAddr = addrArr[addrArr.length-1];

        String adminCode = landInformationMapper.findAdminCode(cutAddr);

        Map<String,Object> codeMap = new HashMap<>();
        codeMap.put("code", adminCode);
        Map<String,Object> landCodeMap = landInformationMapper.findLandCode(codeMap);
        String landCode = "";
        String orderNext = "";
        Map<String,Object> updateOrder = new HashMap<>();
        updateOrder.put("code", adminCode);
        if (MapUtils.isNotEmpty(landCodeMap)) {
            String order = MapUtils.getString(landCodeMap, "order");
            orderNext = String.format("%05d", Integer.parseInt(order) + 1);
            //更新顺序码
            updateOrder.put("order", orderNext);
            landInformationMapper.updateLandCode(updateOrder);

        } else {
            //没有该行政区划相关顺序码
            orderNext = "00001";
            //插入顺序码
            updateOrder.put("order", orderNext);
            landInformationMapper.addLandCode(updateOrder);
        }
        landCode = adminCode.concat(orderNext);

        return landCode;
    }

}
