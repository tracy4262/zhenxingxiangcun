package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.portal.feign.RelatedServiceFeign;
import com.ovit.nswy.portal.mapper.ShopCommodityTypeInfoMapper;
import com.ovit.nswy.portal.model.SpeciesQuery;
import com.ovit.nswy.portal.model.SpeciesResult;
import com.ovit.nswy.portal.service.ShopCommodityTypeInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShopCommodityTypeInfoServiceImpl implements ShopCommodityTypeInfoService {

    @Autowired
    private ShopCommodityTypeInfoMapper shopCommodityTypeInfoMapper;

    @Autowired
    private RelatedServiceFeign relatedServiceFeign;

    @Override
    public List<Map<String,Object>> findCommodityImage(Map<String,Object> params){
        params.put("picture_product","1");
        return shopCommodityTypeInfoMapper.findCommodityImage(params);
    }

    @Override
    public List<Map<String,Object>> findCommodityInfo(Map<String,Object> params){
        List<Map<String,Object>> commodityInfo = shopCommodityTypeInfoMapper.findCommodityInfo(params);
        for(Map<String,Object> map : commodityInfo){
            String suitPeoples  =  MapUtils.getString(map,"suitPeople");
            String[] suitPeople = suitPeoples.split(",");
            map.put("suitPeople",suitPeople);
            //物种
            map.put("relatedSpeciesName",getSpecies(MapUtils.getString(map,"relatedSpeciesId")));
            //品种
            map.put("breedName",getSpeciesVarietey(MapUtils.getString(map,"breedId")));

            String notarizationCertificate = MapUtils.getString(map,"notarizationCertificate");
            String[] commodity_image = notarizationCertificate.split(",");
            if(StringUtil.isNotBlank(notarizationCertificate)){
                map.put("notarizationCertificate",commodity_image);
            }else{
                map.put("notarizationCertificate",new String[]{});
            }


        }

        return commodityInfo;
    }

    /**
     * 插入或更新商品分类信息
     * @param params
     * @return
     */
    @Override
    public String insertCommodityInfo(Map<String,Object> params){

        params.put("suitPeople",initJson(params,"suitPeople"));
        params.put("notarizationCertificate",initJson(params,"notarizationCertificate"));
        String id  = new String();
        try {
            shopCommodityTypeInfoMapper.insertCommodityInfo(params);
            id = shopCommodityTypeInfoMapper.findMaxId();
        }catch (Exception e){
            e.printStackTrace();
        }

        return id;

    }


    /**
     * Json 转 String
     * @param map
     * @param key
     * @return
     */
    private  String initJson(Map<String,Object> map,String key){
        String valueOfMap = MapUtils.getString(map,key);
        List<String> list = JSON.parseArray(valueOfMap,String.class);
        String values = StringUtils.join(list.toArray(),",");
        return values;
    }

    /**
     * 通过物种ID查询物种名称
     * @param name
     * @return
     */
    @Override
    public String getSpecies(String name){
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(name)) {
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setSpeciesid(name);
            Object data = relatedServiceFeign.listSpecies(speciesQuery);
            JSONObject jsonObject = JSONObject.parseObject(data.toString());
            List<SpeciesResult> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), SpeciesResult.class);
            if (CollectionUtils.isNotEmpty(objectList)) {
                FName.append(objectList.get(0).getFname());
            }
        }
        return FName.toString();
    }

    /**
     * 通过物种ID查询物种名称
     * @param varietyId
     * @return
     */
    private String getSpeciesVarietey(String varietyId){
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(varietyId)) {
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setFid(varietyId);
            Object data = relatedServiceFeign.listSpeciesVarietey(speciesQuery);
            JSONObject jsonObject = JSONObject.parseObject(data.toString());
            List<SpeciesResult> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), SpeciesResult.class);
            if (CollectionUtils.isNotEmpty(objectList)) {
                FName.append(objectList.get(0).getFname());
            }
        }
        return FName.toString();
    }


}
