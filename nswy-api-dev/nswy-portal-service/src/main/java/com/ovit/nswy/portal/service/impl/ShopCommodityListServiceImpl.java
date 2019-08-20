package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.portal.feign.RelatedServiceFeign;
import com.ovit.nswy.portal.mapper.ShopCommodityListMapper;
import com.ovit.nswy.portal.mapper.ShopCommodityTypeInfoMapper;
import com.ovit.nswy.portal.model.SpeciesQuery;
import com.ovit.nswy.portal.model.SpeciesResult;
import com.ovit.nswy.portal.service.ShopCommodityListService;
import com.ovit.nswy.portal.service.ShopCommodityTypeInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ShopCommodityListServiceImpl implements ShopCommodityListService {

    @Autowired
    private ShopCommodityListMapper shopCommodityListMapper;

    @Autowired
    private RelatedServiceFeign relatedServiceFeign;



    /**
     * 查询商品列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String,Object>> findShopCommodityList(Map<String,Object> params){
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String,Object>> commodityInfo = shopCommodityListMapper.findShopCommodityList(params);
        /*CopyOnWriteArrayList<Map<String,Object>> cowList = new CopyOnWriteArrayList<Map<String,Object>>(commodityInfo);*/
        for(Map<String,Object> map : commodityInfo){
            String productVbep = MapUtils.getString(map,"productVbep");//商品库存
            String startNum = MapUtils.getString(map,"startNum");//商品起售量
            if(Integer.valueOf(productVbep)<Integer.valueOf(startNum)){
                //商品下架，商品列表中移除
                map.put("storageStatus","2");
                shopCommodityListMapper.updateStorage(map);

                commodityInfo.remove(map);
            }else{
                String commodityImage = MapUtils.getString(map,"src");
                String[] commodity_image = new String[]{};
                if(StringUtil.isNotEmpty(commodityImage)){
                    commodity_image = commodityImage.split(",");

                }
                map.put("src",commodity_image);
                String discount_date = MapUtils.getString(map,"time");
                if(StringUtil.isNotEmpty(discount_date)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try{
                        Date discountDate = sdf.parse(discount_date);
                        Boolean discount  = discountDate.after(new Date());
                        if(discount){
                            map.put("finish",true);
                        }else{
                            map.put("finish",false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }else{
                    map.put("finish",false);
                }
            }
        }


        return new PageInfo<>(commodityInfo);
    }

    /**
     * 查询商品详情
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findCommodityDetail(Map<String,Object> params){

        String id = MapUtils.getString(params,"commodityId");
        params.put("id",id);

        Map<String,Object> commodityInfo = new HashMap<>();

        Map<String,Object> commodityTypeInfos= shopCommodityListMapper.findCommodityTypeInfo(params);
        String commodityImage = MapUtils.getString(commodityTypeInfos,"commodityImage");
        String[] commodity_images = commodityImage.split(",");
        commodityTypeInfos.put("commodityImage",commodity_images);
        commodityInfo.put("commodityTypeInfos",commodityTypeInfos);

        //商品简介
        List<Map<String,Object>> commodityAbstract = shopCommodityListMapper.findCommodityAbstractDetail(params);
        for(Map<String,Object> map : commodityAbstract){
            String notarizationCertificate = MapUtils.getString(map,"commodityImage");
            String[] commodity_image = notarizationCertificate.split(",");
            map.put("commodityImage",commodity_image);
        }
        commodityInfo.put("commodityAbstract",commodityAbstract.get(0));

        //商品分类
        List<Map<String,Object>> commodityTypeInfo = shopCommodityListMapper.findCommodityTypeInfoDetail(params);
        for(Map<String,Object> map : commodityTypeInfo){
            //物种
            map.put("relatedSpeciesName",getSpecies(MapUtils.getString(map,"relatedSpecies")));
            //品种
            map.put("breedName",getSpeciesVarietey(MapUtils.getString(map,"varietyId")));

            String suitPeoples  =  MapUtils.getString(map,"suitPeople");
            String[] suitPeople = suitPeoples.split(",");
            map.put("suitPeople",suitPeople);

        }
        commodityInfo.put("commodityTypeInfo",commodityTypeInfo.get(0));

        //产地信息
        List<Map<String,Object>> commoditySupplyInfo = shopCommodityListMapper.findCommoditySupplyInfoDetail(params);
        commodityInfo.put("commoditySupplyInfo",commoditySupplyInfo.get(0));

        return commodityInfo;
    }

    @Override
    public Map<String,Object> findSupplyInfo(Map<String,Object> params){
        Map<String, Object> resultMap = new HashMap<>();

        //查询商品信息
        Map<String, Object> commodityResult = shopCommodityListMapper.findCommodityInfo(params);
        resultMap.put("product", commodityResult);

        //查询商品生产信息
        Map<String, Object> productionResult = shopCommodityListMapper.findProductionInfo(params);
        resultMap.put("Production", productionResult);

        //查询商品产地信息
        Map<String, Object> placeResult = shopCommodityListMapper.findPlaceInfo(params);
        resultMap.put("origin", placeResult);

        //查询商品资质信息
        Map<String, Object> qualificationResult = shopCommodityListMapper.findQualificationInfo(params);
        resultMap.put("qualification", qualificationResult);

        //查询商品荣誉信息
        Map<String, Object> honorResult = shopCommodityListMapper.findHonorInfo(params);
        String certificates  =  MapUtils.getString(honorResult,"certificate");
        String[] certificate = certificates.split(",");
        honorResult.put("certificate",certificate);
        resultMap.put("honor", honorResult);

        //查询自定义表单之商品质量信息
        Map<String, Object> qualityMap = new HashMap<>();
        qualityMap.putAll(params);
        qualityMap.put("type", "0");
        Map<String, Object> qualityResult = shopCommodityListMapper.findCustomContentInfo(qualityMap);
        if (StringUtils.isNotBlank(MapUtils.getString(qualityResult, "custom_content"))) {
            JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(qualityResult, "custom_content"));
            resultMap.put("qualityFormData", qualityArray);
        } else {
            resultMap.put("qualityFormData", new String[0]);
        }

        //查询自定义表单之商品安全标准
        Map<String, Object> safetyMap = new HashMap<>();
        safetyMap.putAll(params);
        safetyMap.put("type", "1");
        Map<String, Object> safetyResult = shopCommodityListMapper.findCustomContentInfo(safetyMap);
        if (StringUtils.isNotBlank(MapUtils.getString(safetyResult, "custom_content"))) {
            JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(safetyResult, "custom_content"));
            resultMap.put("safeFormData", safetyArray);
        } else {
            resultMap.put("safeFormData", new String[0]);
        }

        return resultMap;
    }

    @Override
    public Map<String,Object> findCommentNum(Map<String,Object> params){
        Map<String, Object> resultMap = new HashMap<>();
        params.put("fromAccount","0");
        Map<String, Object> commentNum = shopCommodityListMapper.findCommentCount(params);
        resultMap.put("commentNum", MapUtils.getString(commentNum,"commentNum"));
        params.put("reputation","3");
        Map<String, Object> praise = shopCommodityListMapper.findCommentCount(params);
        resultMap.put("praise", MapUtils.getString(praise,"commentNum"));
        params.put("reputation","2");
        Map<String, Object> review = shopCommodityListMapper.findCommentCount(params);
        resultMap.put("review", MapUtils.getString(review,"commentNum"));
        params.put("reputation","1");
        Map<String, Object> negative = shopCommodityListMapper.findCommentCount(params);
        resultMap.put("negative", MapUtils.getString(negative,"commentNum"));

        return resultMap;
    }

    @Override
    public PageInfo<Map<String,Object>> findComment(Map<String,Object> params){

        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        params.put("fromAccount","0");
        //买家列表
        List<Map<String,Object>> commentList  = shopCommodityListMapper.findCommentList(params);
        for(Map<String,Object> comment :commentList){
            params.put("fromAccount","1");
            params.put("order_code_id",MapUtils.getString(comment,"order_code_id"));
            List<Map<String,Object>> sellerList  = shopCommodityListMapper.findCommentList(params);
            if(sellerList!=null){
                comment.put("list",sellerList);
            }else{
                comment.put("list",new String[]{});
            }

        }
        return new PageInfo<>(commentList);
    }

    @Override
    public PageInfo<Map<String,Object>> findMonthList(Map<String,Object> params){
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String,Object>> monthList  = shopCommodityListMapper.findMonthList(params);
        return new PageInfo<>(monthList);
    }

    @Override
    public Map<String,Object> findLoginUser(Map<String,Object> params){
        Map<String, Object> resultMap = new HashMap<>();
        Map<String,Object> avatarMap = shopCommodityListMapper.findAvatar(params);
        if(avatarMap!=null&&avatarMap.size()>0){
            resultMap = shopCommodityListMapper.findLoginUser(params);
            if(resultMap!=null&&resultMap.size()>0){

            }else{
                resultMap = new HashMap<>();
                resultMap.put("name",MapUtils.getString(avatarMap,"name"));
                if(MapUtils.getString(avatarMap,"userType").equals("1")){
                    params.put("table_name","corp_info");

                }else if(MapUtils.getString(avatarMap,"userType").equals("3")||MapUtils.getString(avatarMap,"userType").equals("5")){
                    params.put("table_name","gov_info");
                }
                Map<String,Object> corp  = shopCommodityListMapper.findProxyMode(params);
                if(StringUtils.isNotBlank(MapUtils.getString(corp,"email"))){
                    resultMap.put("email",MapUtils.getString(corp,"email"));
                }else{
                    resultMap.put("email","");
                }
                if(MapUtils.getString(avatarMap,"userType").equals("1")){
                    if(StringUtils.isNotBlank(MapUtils.getString(corp,"mobile"))){
                        resultMap.put("phone",MapUtils.getString(corp,"mobile"));
                    }else{
                        resultMap.put("phone","");
                    }
                }else{
                    if(StringUtils.isNotBlank(MapUtils.getString(corp,"phone"))){
                        resultMap.put("phone",MapUtils.getString(corp,"phone"));
                    }else{
                        resultMap.put("phone","");
                    }
                }



            }
            resultMap.put("avatar",MapUtils.getString(avatarMap,"avatar"));
            resultMap.put("userId",MapUtils.getString(avatarMap,"userId"));
        }

        return resultMap;
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
    private String getSpecies(String name){
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
