package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.*;
import com.ovit.nswy.member.model.InformationDetail;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.FishingProductService;
import com.ovit.nswy.member.service.FishingServicesService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.util.Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.rmi.CORBA.Util;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FishingServicesServiceImpl implements FishingServicesService {

    @Autowired
    private FishingServiceMapper fishingServiceMapper;

    @Autowired
    private ScenicSpotManagementMapper scenicSpotManagementMapper;

    @Autowired
    private AccommodationManagementMapper accommodationManagementMapper;

    @Autowired
    private RestaurantManagementMapper restaurantManagementMapper;

    @Override
    public PageInfo<Map<String,Object>> findFishingService(Map<String,Object> params){

        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), 10);
        List<Map<String,Object>> list =fishingServiceMapper.findFishingService(params);

        for(Map<String,Object> map:list){
            //景区
           if(StringUtil.isNotBlank(MapUtils.getString(params,"type"))){
               String type = MapUtils.getString(params,"type");
               String fishServiceId = MapUtils.getString(map,"id");

               Map<String,Object> productMap = new HashMap<>();
               productMap.put("fishServiceId",fishServiceId);
               productMap.put("setMealId",MapUtils.getString(map,"setMealId"));
               //获取套餐详细
               List<Map<String,Object>> productManageList  =  fishingServiceMapper.findProductManagementService(productMap);
               if(productManageList.size()>0){
                   for(Map<String,Object> product : productManageList){
                       List<Map<String,Object>> productList = new ArrayList<>();
                       String productId =  MapUtils.getString(product,"productId");
                       String[] productIds = productId.split(",");
                        for(int i=0;i<productIds.length;i++){
                           Map<String,Object> lists = new HashMap<>();
                           //根据id查询数据
                           if(type.equals("2")){

                               lists= fishingServiceMapper.findScenicList(productIds[i]);


                           }else if(type.equals("4")){
                               Map<String,Object> productsMap = new HashMap<>();
                               productsMap.put("class_name","room_class_name");
                               productsMap.put("table_name","room_class_management");
                               productsMap.put("id",productIds[i]);
                               lists  = fishingServiceMapper.findProductList(productsMap);
                           }else if(type.equals("3")){
                               Map<String,Object> productsMap = new HashMap<>();
                               productsMap.put("class_name","food_class_name");
                               productsMap.put("table_name","food_class_management");
                               productsMap.put("id",productIds[i]);
                               lists  = fishingServiceMapper.findProductList(productsMap);
                           }
                           productList.add(lists);

                       }
                       map.put("productList",productList);

                       if(type.equals("3")){

                           String rooms = MapUtils.getString(product,"roomId");
                           String[] roomIds = rooms.split(",");
                           List<Map<String,Object>> roomList = new ArrayList<>();
                           for(int i=0;i<roomIds.length;i++){
                               String roomId =  roomIds[i];
                               Map<String,Object> roomlist = fishingServiceMapper.findRoomList(roomId);

                               roomList.add(roomlist);
                           }
                           //已选包房
                           map.put("selectedRoom",roomList);
                       }
                   }
               }


           }

            if(MapUtils.getString(map,"type").equals("0")){
                map.put("fishServiceId",MapUtils.getString(map,"id"));
                //按时间收费
                List<Map<String,Object>> fishTimeChargeList  =  fishingServiceMapper.findFishTimeChargeInfo(map);
                map.put("fishTimeCharge",fishTimeChargeList);
                if(fishTimeChargeList.size()>0){
                    if(MapUtils.getString(fishTimeChargeList.get(0),"timeCharging").equals("1")){
                        map.put("timeCharging",true);
                    }else{
                        map.put("timeCharging",false);
                    }
                }else{
                    map.put("timeCharging",false);
                }

                /*//按品种收费
                List<Map<String,Object>> fishVarietyChargeList  =  fishingServiceMapper.findFishVarietyChargeInfo(map);
                for(Map<String,Object> var: fishVarietyChargeList){
                    String image = MapUtils.getString(var,"image");
                    if(!"null".equals(image)&&StringUtil.isNotBlank(image)){
                        String[] images = image.split(",");
                        var.put("image",images);
                    }else{
                        var.put("image",new String[]{});
                    }
                }
                map.put("fishVarietyCharge",fishVarietyChargeList);
                if(fishVarietyChargeList.size()>0){
                    if(MapUtils.getString(fishVarietyChargeList.get(0),"timeVariety").equals("1")){
                        map.put("timeVariety",true);
                    }else{
                        map.put("timeVariety",false);
                    }
                }else{
                    map.put("timeVariety",false);
                }*/
            }

            /*if(MapUtils.getString(map,"type").equals("1")){
                map.put("fishServiceId",MapUtils.getString(map,"id"));

                //按品种收费
                List<Map<String,Object>> fishVarietyChargeList  =  fishingServiceMapper.findFishVarietyChargeInfo(map);
                for(Map<String,Object> var: fishVarietyChargeList){
                    String image = MapUtils.getString(var,"image");
                    if(!"null".equals(image)&&StringUtil.isNotBlank(image)){
                        String[] images = image.split(",");
                        var.put("image",images);
                    }else{
                        var.put("image",new String[]{});
                    }
                }
                map.put("fishVarietyCharge",fishVarietyChargeList);
                if(fishVarietyChargeList.size()>0){
                    if(MapUtils.getString(fishVarietyChargeList.get(0),"timeVariety").equals("1")){
                        map.put("timeVariety",true);
                    }else{
                        map.put("timeVariety",false);
                    }
                }else{
                    map.put("timeVariety",false);
                }
            }*/

           map.put("serviceId",MapUtils.getString(params,"id"));
           //品种
            List<Map<String,Object>> varietyList  =  fishingServiceMapper.findServiceVarietyInfo(map);
            for(Map<String,Object> variety:varietyList){
                String image = MapUtils.getString(variety,"image");
                if(!"null".equals(image)&&StringUtil.isNotBlank(image)){
                    String[] images = image.split(",");
                    variety.put("image",images);
                }else{
                    variety.put("image",new String[]{});
                }

            }
            if(MapUtils.getString(map,"type").equals("1") ||MapUtils.getString(map,"type").equals("0")){
                if(varietyList.size()>0){
                    if(StringUtil.isNotBlank(MapUtils.getString(varietyList.get(0),"timeVariety"))){
                        if(MapUtils.getString(varietyList.get(0),"timeVariety").equals("1")){
                            map.put("timeVariety",true);
                        }else{
                            map.put("timeVariety",false);
                        }

                    }else{
                        map.put("timeVariety",false);
                    }
                }else{
                    map.put("timeVariety",false);
                }
            }

            map.put("variety",varietyList);

            //联系人
            if (StringUtils.isNotBlank(MapUtils.getString(map, "contact"))) {
                JSONArray contact = JSONArray.parseArray(MapUtils.getString(map, "contact"));
                map.put("contact", contact);
            } else {
                map.put("contact", new String[0]);
            }
            //营业网点
            if (StringUtils.isNotBlank(MapUtils.getString(map, "serviceOutlet"))) {
                JSONArray serviceOutlet = JSONArray.parseArray(MapUtils.getString(map, "serviceOutlet"));
                map.put("serviceOutlet", serviceOutlet);
            } else {
                map.put("serviceOutlet", new String[0]);
            }

            //公交车
            if (StringUtils.isNotBlank(MapUtils.getString(map, "busLead"))) {
                JSONArray busLead = JSONArray.parseArray(MapUtils.getString(map, "busLead"));
                map.put("busLead", busLead);
            } else {
                map.put("busLead", new String[0]);
            }
            //自驾
            if (StringUtils.isNotBlank(MapUtils.getString(map, "traffic_lead"))) {
                JSONArray traffic_lead = JSONArray.parseArray(MapUtils.getString(map, "traffic_lead"));
                map.put("traffic_lead", traffic_lead);
            } else {
                map.put("traffic_lead", new String[0]);
            }

            String url = MapUtils.getString(map,"image_url");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("image_url",image_url);
            }else{
                continue;
            }

        }
        return new PageInfo<>(list);
    }


    @Override
    public String insertFishingService(Map<String,Object> params){
        fishingServiceMapper.insertFishingService(params);
        return MapUtils.getString(params, "id");
    }

    @Override
    public void updateFishingService(Map<String,Object> params){
        String image_url  = MapUtils.getString(params,"image_url");
        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
            params.put("image_url",JsonToStringUtils.initJson(params,"image_url"));

        }
        String busLead  = JSON.toJSONString(params.get("busLead"));
        if(!"null".equals(busLead)&&StringUtil.isNotBlank(busLead)){
            params.put("busLead",busLead);

        }
        String traffic_lead  = JSON.toJSONString(params.get("traffic_lead"));
        if(!"null".equals(traffic_lead)&&StringUtil.isNotBlank(traffic_lead)){
            params.put("traffic_lead",traffic_lead);

        }
        //联系人
        String contact  = JSON.toJSONString(params.get("contact"));
        if(!"null".equals(contact)&&StringUtil.isNotBlank(contact)){

            params.put("contact", contact);

        }
        //服务网点
        String serviceOutlet  = JSON.toJSONString(params.get("serviceOutlet"));
        if(!"null".equals(serviceOutlet)&&StringUtil.isNotBlank(serviceOutlet)){
            params.put("serviceOutlet",serviceOutlet);

        }

        //品种
        List<Map<String,Object>> varietyList  = (List<Map<String,Object>>)params.get("variety");
        if(varietyList !=null&&varietyList.size()>0){
            params.put("serviceId",MapUtils.getString(params,"id"));
            //查询品种是否存在， 存在就更新
            List<Map<String,Object>> varietyLists = fishingServiceMapper.findServiceVarietyInfo(params);
            if(varietyLists.size()>0){
                fishingServiceMapper.deleteServiceVarietyInfo(params);
            }
            for(Map<String,Object> variety: varietyList){
                variety.put("serviceId",MapUtils.getString(params,"id"));
                String image  = MapUtils.getString(variety,"image");
                if(!"null".equals(image)&&StringUtil.isNotBlank(image)){
                    variety.put("image",JsonToStringUtils.initJson(variety,"image"));

                }
                if(StringUtil.isBlank(MapUtils.getString(params,"timeVariety"))){
                    variety.put("timeVariety",false);
                }
                fishingServiceMapper.saveServiceVarietyInfo(variety);


            }


        }

        //按照时间收费
        String timeCharging  =  MapUtils.getString(params,"timeCharging");
        if(!"null".equals(timeCharging)&&StringUtil.isNotBlank(timeCharging)){
            List<Map<String,Object>> fishTimeCharge  = (List<Map<String,Object>>)params.get("fishTimeCharge");
            params.put("fishServiceId",MapUtils.getString(params,"id"));
            //查询时间收费是否存在， 存在就删除，在插入
            List<Map<String,Object>> chargeLists = fishingServiceMapper.findFishTimeChargeInfo(params);
            if(chargeLists.size()>0){

                fishingServiceMapper.deleteFishTimeChargeInfo(params);
            }
            if(MapUtils.getBoolean(params,"timeCharging")){

                if(fishTimeCharge.size()>0){
                    for(Map<String,Object> charge: fishTimeCharge){
                        charge.put("fishServiceId",MapUtils.getString(params,"id"));
                        charge.put("timeCharging",MapUtils.getBoolean(params,"timeCharging"));
                        fishingServiceMapper.saveFishTimeChargeInfo(charge);
                    }

                }
            }
        }


        //按照品种收费
        String timeVariety  =  MapUtils.getString(params,"timeVariety");
        if(!"null".equals(timeVariety)&&StringUtil.isNotBlank(timeVariety)){
            List<Map<String,Object>> fishVarietyCharge  = (List<Map<String,Object>>)params.get("fishVarietyCharge");
            if(MapUtils.getBoolean(params,"timeVariety")){
                if(fishVarietyCharge !=null&&fishVarietyCharge.size()>0){
                    for(Map<String,Object> charge: fishVarietyCharge){
                        String image  = MapUtils.getString(charge,"image");
                        if(!"null".equals(image)&&StringUtil.isNotBlank(image)){
                            charge.put("image",JsonToStringUtils.initJson(charge,"image"));

                        }
                        charge.put("timeVariety",MapUtils.getBoolean(params,"timeVariety"));
                        fishingServiceMapper.updateServiceVarietyInfo(charge);
                    }

                }
            }
        }



        fishingServiceMapper.updateFishingService(params);

        if (StringUtils.isNotBlank(MapUtils.getString(params, "mattres_need_attention"))) {
            //说明是保存发布服务的第四步
            String account = fishingServiceMapper.findServiceById(params);
            Map<String,Object> recommend = new HashMap<>();
            recommend.put("account", account);
            recommend.put("id", MapUtils.getString(params, "id"));
            recommend.put("type", 1);
            //判断是否已推荐
            Map<String, Object> hadRecommend = fishingServiceMapper.isHadRecommend(recommend);
            if (MapUtils.isEmpty(hadRecommend)) {
                fishingServiceMapper.singleSaveRecommend(recommend);
            }
        }

    }



    @Override
    public void deleteFishingService(Map<String,Object> params){
        fishingServiceMapper.updateFishingServiceStatus(params);
    }




    /**
     * 查询已关联，未关联
     * @param params
     * @return
     */
    @Override
    public PageList findJoinServiceList(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("type",MapUtils.getString(params,"type"));
        map.put("account",MapUtils.getString(params,"account"));

        //查询得到所有的服务
        List<Map<String,Object>> serviceList = fishingServiceMapper.findAllServiceList(map);
        if(StringUtil.isNotBlank(MapUtils.getString(params,"service_name"))){
            map.put("service_name",MapUtils.getString(params,"service_name"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"currency_service_name"))){
            map.put("currency_service_name",MapUtils.getString(params,"currency_service_name"));

        }
        Integer pageSize = 10;
        if(StringUtil.isNotBlank(MapUtils.getString(params,"pageSize"))){
            pageSize = Integer.valueOf(MapUtils.getString(params,"pageSize"));
        }


        if(MapUtils.getString(params,"joinService").equals("0")){
            if(serviceList.size()>0){
                //查询得到已关联的服务
                map.put("serviceId",MapUtils.getString(params,"id"));
                List<Map<String,Object>> lists = fishingServiceMapper.findServiceJoinInfo(map);
                List<Map<String,Object>> serviceIdList = new ArrayList<>();
                Map<String,Object> joinMap = new HashMap<>();
                joinMap.put("id",MapUtils.getString(params,"id"));
                serviceIdList.add(joinMap);
                if(lists!=null && lists.size()>0){
                    for(Map<String,Object> join : lists){
                        joinMap = new HashMap<>();
                        joinMap.put("id",MapUtils.getString(join,"id"));
                        serviceIdList.add(joinMap);
                    }
                }
                map.put("list",serviceIdList);
                map.put("account",MapUtils.getString(params,"account"));

                //查询得到已关联之外的服务
                list = fishingServiceMapper.findJoinServiceList(map);
                for(Map<String,Object> maps : list){
                    if (StringUtils.isNotBlank(MapUtils.getString(maps, "contact"))) {
                        JSONArray contact = JSONArray.parseArray(MapUtils.getString(maps, "contact"));
                        maps.put("contact", contact);
                    } else {
                        maps.put("contact", new String[0]);
                    }
                    String url = MapUtils.getString(maps,"image_url");
                    if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                        String[] image_url = url.split(",");
                        maps.put("image_url",image_url);
                    }else{
                        maps.put("image_url",new String[]{});
                    }
                }
            }
        }else{

            map.put("serviceId",MapUtils.getString(params,"id"));
            list = fishingServiceMapper.findServiceJoinInfo(map);
            for(Map<String,Object> maps : list){
                if (StringUtils.isNotBlank(MapUtils.getString(maps, "contact"))) {
                    JSONArray contact = JSONArray.parseArray(MapUtils.getString(maps, "contact"));
                    maps.put("contact", contact);
                } else {
                    maps.put("contact", new String[0]);
                }
                String url = MapUtils.getString(maps,"image_url");
                if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                    String[] image_url = url.split(",");
                    maps.put("image_url",image_url);
                }else{
                    maps.put("image_url",new String[]{});
                }
            }
        }


        return PageList.pageInfoList(params,list);
    }

    @Override
    public void saveJoinServiceInfo(Map<String,Object> params){
        fishingServiceMapper.saveJoinServiceInfo(params);

    }

    @Override
    public void deleteJoinServiceInfo(Map<String,Object> params){
        fishingServiceMapper.deleteJoinServiceInfo(params);

    }

    @Override
    public void saveProduct(Map<String,Object> params){


        if(MapUtils.getString(params,"flag").equals("1")){
            //先删除已存在的数据根据fishServiceId
            List<Map<String,Object>> productManageList  =  fishingServiceMapper.findProductManagementService(params);
            if(productManageList.size()>0){
                Map<String,Object> map = productManageList.get(0);
                String productId =  MapUtils.getString(map,"productId");
                String[] productIds = productId.split(",");
                for(int i=0;i<productIds.length;i++){
                    fishingServiceMapper.deleteProduct(productIds[i]);
                }
                if(StringUtil.isNotBlank(MapUtils.getString(map,"roomId"))){
                    String roomId = MapUtils.getString(map,"roomId");
                    String[] roomIds = roomId.split(",");
                    for(int i=0;i<roomIds.length;i++){
                        fishingServiceMapper.deleteRoom(roomIds[i]);
                    }
                }

            }
        }

       //获取已选产品， 将其存入表product_management中
        List<Map<String,Object>> list = (List<Map<String,Object>>)params.get("selectedProduct");
        String uuid = UUID.randomUUID().toString();
        for(Map<String,Object> map:list){
            map.put("communalId",uuid);
            map.put("account",MapUtils.getString(params,"account"));
            map.put("type",MapUtils.getString(params,"type"));
            fishingServiceMapper.saveProduct(map);
        }

        //选择包房存入
        if(StringUtil.isNotBlank(MapUtils.getString(params,"type"))){
            if(MapUtils.getString(params,"type").equals("3")){
                List<Map<String,Object>> roomlist = (List<Map<String,Object>>)params.get("selectedRoom");
                for(Map<String,Object> map:roomlist){
                    map.put("communalId",uuid);
                    map.put("account",MapUtils.getString(params,"account"));
                    fishingServiceMapper.saveRoom(map);
                }
            }
        }

        //查询存入的已选产品
        List<Map<String,Object>> productIdList = fishingServiceMapper.findProductId(uuid);
        String productId = "";
        for(Map<String,Object> map :productIdList){
            productId += MapUtils.getString(map,"id") +",";
        }
        productId = productId.substring(0,productId.length() - 1);


        if(StringUtil.isNotBlank(MapUtils.getString(params,"type"))){
            if(MapUtils.getString(params,"type").equals("3")){
                List<Map<String,Object>> roomIdList = fishingServiceMapper.findRoomId(uuid);
                if(roomIdList.size()>0){
                    String roomId = "";
                    for(Map<String,Object> map :roomIdList){
                        roomId += MapUtils.getString(map,"id") +",";
                    }
                    roomId = roomId.substring(0,roomId.length() - 1);

                    params.put("roomId",roomId);
                }else{
                    params.put("roomId","");
                }


            }
        }

        params.put("productId",productId);

        //将数据存入product_management_service
        fishingServiceMapper.insertProductManagementService(params);



    }


    /**
     * 景区 ， 餐饮 ，住宿
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findProductService(Map<String,Object> params){
        List<Map<String,Object>> scenic = new ArrayList<>();
        String type = MapUtils.getString(params,"type");
        Map<String,Object> map = new HashMap<>();
        //根据服务id获取数据
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtil.isNotBlank(MapUtils.getString(params,"setMealId"))){
            list =  fishingServiceMapper.findProductManagementService(params);
        }
        Map<String,Object> manage = new HashMap<>();
        if(StringUtil.isNotBlank(MapUtils.getString(params,"status"))){
            manage.put("status",MapUtils.getString(params,"status"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"account"))){
            manage.put("account",MapUtils.getString(params,"account"));
        }
        if(list.size()>0){
            map=  list.get(0);
            //获取productId
            String products = MapUtils.getString(map,"productId");
            if(type.equals("2")){
                scenic=  scenicSpotManagementMapper.findList(manage);
            }else if(type.equals("4")){

                scenic=  accommodationManagementMapper.findRoomList(manage);
            }else if(type.equals("3")){
                scenic=  restaurantManagementMapper.findFood(manage);
            }

            for(Map<String,Object> maps : scenic){
                maps.put("checked",false);
            }

            //选择包房
            if(type.equals("3")){
                if(StringUtil.isNotBlank(MapUtils.getString(params,"roomStatus"))){
                    manage.put("status",MapUtils.getString(params,"roomStatus"));
                }
                List<Map<String,Object>> roomMap =  restaurantManagementMapper.findRoom(manage);
                for(Map<String,Object> maps : roomMap){
                    maps.put("checked",false);
                }
                String rooms = MapUtils.getString(map,"roomId");
                String[] roomIds = rooms.split(",");

                for(int i=0;i<roomIds.length;i++){
                 String roomId =  roomIds[i];
                 Map<String,Object> roomlist = fishingServiceMapper.findRoomList(roomId);
                    for(Map<String,Object> maps : roomMap){
                        if(StringUtil.isNotBlank(MapUtils.getString(roomlist,"management_id"))){
                            if(MapUtils.getString(roomlist,"management_id").equals(MapUtils.getString(maps,"id"))){
                                maps.put("checked",true);
                            }
                        }

                    }

                }
                //已选产品
                map.put("selectedRoom",roomMap);
            }

            //已选产品
            if(StringUtil.isNotBlank(products)){
                String[] productIds = products.split(",");
                List<Map<String,Object>> productList = new ArrayList<>();
                for(int i=0;i<productIds.length;i++){
                    String productId = productIds[i];
                    Map<String,Object> lists = new HashMap<>();
                    if(type.equals("2")){
                        lists = fishingServiceMapper.findScenicList(productId);

                    }else if(type.equals("4")){
                        Map<String,Object> productsMap = new HashMap<>();
                        productsMap.put("class_name","room_class_name");
                        productsMap.put("table_name","room_class_management");
                        productsMap.put("id",productId);
                        lists  = fishingServiceMapper.findProductList(productsMap);

                    }else if(type.equals("3")){
                        Map<String,Object> productsMap = new HashMap<>();
                        productsMap.put("class_name","food_class_name");
                        productsMap.put("table_name","food_class_management");
                        productsMap.put("id",productId);
                        lists  = fishingServiceMapper.findProductList(productsMap);

                    }
                    for(Map<String,Object> maps : scenic){
                        if(StringUtil.isNotBlank(MapUtils.getString(lists,"management_id"))){
                            if(MapUtils.getString(lists,"management_id").equals(MapUtils.getString(maps,"id"))){
                                maps.put("checked",true);
                            }
                        }

                    }
                    productList.add(lists);

                }
                //已选产品
                map.put("selectedProduct",productList);

            }

            map.put("leftProduct",scenic);


        }else{

            if(type.equals("2")){
                scenic=  scenicSpotManagementMapper.findList(manage);
            }else if(type.equals("4")){
                scenic=  accommodationManagementMapper.findRoomList(manage);
            }else if(type.equals("3")){
                scenic=  restaurantManagementMapper.findFood(manage);
                if(StringUtil.isNotBlank(MapUtils.getString(params,"roomStatus"))){
                    manage.put("status",MapUtils.getString(params,"roomStatus"));
                }
                List<Map<String,Object>> roomMap =  restaurantManagementMapper.findRoom(manage);
                for(Map<String,Object> maps : roomMap){
                    maps.put("checked",false);
                }
                map.put("selectedRoom",roomMap);
            }
            for(Map<String,Object> maps : scenic){
                maps.put("checked",false);
            }
            map.put("leftProduct",scenic);

            list.add(map);
        }

        return list;
    }


    @Override
    public void deleteProductManagementService(Map<String,Object> params){
        //获取套餐详细
        List<Map<String,Object>> productManageList  =  fishingServiceMapper.findProductManagementService(params);

        for(Map<String,Object> product : productManageList){
            List<Map<String,Object>> productList = new ArrayList<>();
            String productId =  MapUtils.getString(product,"productId");
            String[] productIds = productId.split(",");
            for(int i=0;i<productIds.length;i++){
                //根据id查询数据
                fishingServiceMapper.deleteProduct(productIds[i]);
            }
        }

        fishingServiceMapper.deleteProductManagementService(params);
    }



    @Override
    public PageList findProductServiceList(Map<String,Object> params){
        Map<String,Object> serviceMap = new HashMap<>();

        if(StringUtil.isNotBlank(MapUtils.getString(params,"type"))){
            serviceMap.put("type",MapUtils.getString(params,"type"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"account"))){
            serviceMap.put("account",MapUtils.getString(params,"account"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"id"))){
            serviceMap.put("id",MapUtils.getString(params,"id"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"service_name"))){
            serviceMap.put("service_name",MapUtils.getString(params,"service_name"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"currency_service_name"))){
            serviceMap.put("currency_service_name",MapUtils.getString(params,"currency_service_name"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"isToPage"))){
            serviceMap.put("isToPage",MapUtils.getString(params,"isToPage"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"address"))){
            serviceMap.put("address",MapUtils.getString(params,"address"));
        }
        if(StringUtil.isNotBlank(MapUtils.getString(params,"industry"))){
            serviceMap.put("industry",MapUtils.getString(params,"industry"));
        }
        List<Map<String,Object>> list =fishingServiceMapper.findProductServiceList(serviceMap);

        Integer pageSize = 10;
        if(StringUtil.isNotBlank(MapUtils.getString(params,"pageSize"))){
            pageSize = Integer.valueOf(MapUtils.getString(params,"pageSize"));
        }else{
            params.put("pageSize",pageSize);
        }

        for(Map<String,Object> map:list){
            if(MapUtils.getString(map,"type").equals("0")){
                map.put("fishServiceId",MapUtils.getString(map,"id"));
                //按时间收费
                List<Map<String,Object>> fishTimeChargeList  =  fishingServiceMapper.findFishTimeChargeInfo(map);
                map.put("fishTimeCharge",fishTimeChargeList);
                if(fishTimeChargeList.size()>0){
                    if(MapUtils.getString(fishTimeChargeList.get(0),"timeCharging").equals("1")){
                        map.put("timeCharging",true);
                    }else{
                        map.put("timeCharging",false);
                    }
                }else{
                    map.put("timeCharging",false);
                }


            }
            map.put("serviceId",MapUtils.getString(map,"id"));
            //品种
            List<Map<String,Object>> varietyList  =  fishingServiceMapper.findServiceVarietyInfo(map);

            if(MapUtils.getString(map,"type").equals("1") ||MapUtils.getString(map,"type").equals("0")){
                if(varietyList.size()>0){
                    if(StringUtil.isNotBlank(MapUtils.getString(varietyList.get(0),"timeVariety"))){
                        if(MapUtils.getString(varietyList.get(0),"timeVariety").equals("1")){
                            map.put("timeVariety",true);
                        }else{
                            map.put("timeVariety",false);
                        }

                    }else{
                        map.put("timeVariety",false);
                    }
                }else{
                    map.put("timeVariety",false);
                }
            }

            //2景区 3餐饮 4 住宿
            if(StringUtil.isNotBlank(MapUtils.getString(map,"type"))){
                String type = MapUtils.getString(map,"type");
                String fishServiceId = MapUtils.getString(map,"id");

                Map<String,Object> productMap = new HashMap<>();
                productMap.put("fishServiceId",fishServiceId);
                productMap.put("orderPrice","0");

                //获取套餐详细
                List<Map<String,Object>> productManageList  =  fishingServiceMapper.findProductManagementService(productMap);
                if(productManageList.size()>0){

                    map.put("price",MapUtils.getString(productManageList.get(0),"setMealPrice"));


                }

                if ("5".equals(MapUtils.getString(map,"type"))) {
                    //查询咨询服务相关的字段
                    Map<String,Object> consultMap = fishingServiceMapper.findConsultService(map);
                    map.putAll(consultMap);
                }
            }

            //关联服务

            List<Map<String,Object>> joinlist = fishingServiceMapper.findServiceJoinInfo(map);
            map.put("joinService",joinlist);


            //联系人
            if (StringUtils.isNotBlank(MapUtils.getString(map, "contact"))) {
                JSONArray contact = JSONArray.parseArray(MapUtils.getString(map, "contact"));
                map.put("contact", contact);
            } else {
                map.put("contact", new String[0]);
            }
            //营业网点
            if (StringUtils.isNotBlank(MapUtils.getString(map, "serviceOutlet"))) {
                JSONArray serviceOutlet = JSONArray.parseArray(MapUtils.getString(map, "serviceOutlet"));
                map.put("serviceOutlet", serviceOutlet);
            } else {
                map.put("serviceOutlet", new String[0]);
            }

            //公交车
            if (StringUtils.isNotBlank(MapUtils.getString(map, "busLead"))) {
                JSONArray busLead = JSONArray.parseArray(MapUtils.getString(map, "busLead"));
                map.put("busLead", busLead);
            } else {
                map.put("busLead", new String[0]);
            }
            //自驾
            if (StringUtils.isNotBlank(MapUtils.getString(map, "traffic_lead"))) {
                JSONArray traffic_lead = JSONArray.parseArray(MapUtils.getString(map, "traffic_lead"));
                map.put("traffic_lead", traffic_lead);
            } else {
                map.put("traffic_lead", new String[0]);
            }

            String url = MapUtils.getString(map,"image_url");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("image_url",image_url);
            }else{
                map.put("image_url", new String[0]);
            }

            if(StringUtil.isNotBlank(MapUtils.getString(params,"gradeFlag"))){
                Map<String,Object> star=fishingServiceMapper.findCommentProbabilityNum(map);
                if(MapUtils.isNotEmpty(star)){
                    map.put("star",MapUtils.getString(star,"star"));
                }else{
                    map.put("star","0");
                }
            }

        }

        if(StringUtil.isNotBlank(MapUtils.getString(params,"pageSize"))){
            pageSize = Integer.valueOf(MapUtils.getString(params,"pageSize"));
        }

        //根据查询得到的star 排序

        if(StringUtil.isNotBlank(MapUtils.getString(params,"gradeFlag"))){
            //正序
            if(MapUtils.getString(params,"gradeFlag").equals("0")){
                Collections.sort(list, new Comparator<Map<String,Object>>(){
                    @Override
                    public int compare(Map<String,Object> arg0, Map<String,Object> arg1) {
                        try {
                            Double dt1 = MapUtils.getDouble(arg0,"star");
                            Double dt2 = MapUtils.getDouble(arg1,"star");
                            if (dt1 > dt2) {
                                return 1;
                            } else if (dt1 < dt2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }else{
                Collections.sort(list, new Comparator<Map<String,Object>>(){
                    @Override
                    public int compare(Map<String,Object> arg0, Map<String,Object> arg1) {
                        try {
                            Double dt1 = MapUtils.getDouble(arg0,"star");
                            Double dt2 = MapUtils.getDouble(arg1,"star");
                            if (dt1 < dt2) {
                                return 1;
                            } else if (dt1 > dt2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }
        }

        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            List<Map<String,Object>> list1 = new ArrayList<>();
            String species = MapUtils.getString(params,"species");
            for(Map map:list){
                if(StringUtils.isNotBlank(MapUtils.getString(map,"adeptSpecies"))){
                    if(species.equals(MapUtils.getString(map,"adeptSpecies"))){
                        list1.add(map);

                    }
                }
            }
            return PageList.pageInfoList(params,list1);
        }

        return PageList.pageInfoList(params,list);
    }

    @Override
    public List<Map<String,Object>> findProductServiceById(Map<String,Object> params){

        List<Map<String,Object>> list =fishingServiceMapper.findFishingService(params);

        for(Map<String,Object> map:list){
            String fishServiceId = MapUtils.getString(map,"id");
            Map<String,Object> productMap = new HashMap<>();
            //景区
            if(StringUtil.isNotBlank(MapUtils.getString(params,"type"))){
                String type = MapUtils.getString(params,"type");
                productMap.put("fishServiceId",fishServiceId);
                productMap.put("setMealId",MapUtils.getString(map,"setMealId"));
                //获取套餐详细
                List<Map<String,Object>> productManageList  =  fishingServiceMapper.findProductManagementService(productMap);
                if(productManageList.size()>0){
                    for(Map<String,Object> product : productManageList){
                        List<Map<String,Object>> productList = new ArrayList<>();
                        String productId =  MapUtils.getString(product,"productId");
                        String[] productIds = productId.split(",");
                        for(int i=0;i<productIds.length;i++){
                            Map<String,Object> lists = new HashMap<>();
                            //根据id查询数据
                            if(type.equals("2")){

                                lists= fishingServiceMapper.findScenicDetailById(productIds[i]);


                            }else if(type.equals("4")){
                                Map<String,Object> productsMap = new HashMap<>();
                                productsMap.put("class_name","room_class_name");
                                productsMap.put("table_name","room_class_management");
                                productsMap.put("id",productIds[i]);
                                lists  = fishingServiceMapper.findRoomListDetailById(productsMap);
                            }else if(type.equals("3")){
                                Map<String,Object> productsMap = new HashMap<>();
                                productsMap.put("class_name","food_class_name");
                                productsMap.put("table_name","food_class_management");

                                productsMap.put("id",productIds[i]);
                                lists  = fishingServiceMapper.findFoodDetailById(productsMap);
                            }
                            productList.add(lists);

                        }
                        map.put("productList",productList);

                        if(type.equals("3")){

                            String rooms = MapUtils.getString(product,"roomId");
                            String[] roomIds = rooms.split(",");
                            List<Map<String,Object>> roomList = new ArrayList<>();
                            if(StringUtil.isNotBlank(rooms)){
                                for(int i=0;i<roomIds.length;i++){
                                    String roomId =  roomIds[i];
                                    Map<String,Object> roomlist = fishingServiceMapper.findRoomList(roomId);
                                    String management_id  = MapUtils.getString(roomlist,"management_id");
                                    List<Map<String,Object>> tableList = fishingServiceMapper.findTableRoomList(management_id);
                                    roomlist.put("tableList",tableList);
                                    roomList.add(roomlist);
                                }
                                //已选包房
                                map.put("selectedRoom",roomList);
                            }

                        }
                    }
                }

            }

            productMap.put("serviceId",fishServiceId);

            //评论概率
            Map<String,Object> commentProbability = fishingServiceMapper.findCommentProbability(productMap);

            map.put("commentProbability",commentProbability);


            Map<String,Object> commentProbabilityNum = fishingServiceMapper.findCommentProbabilityNum(productMap);
            map.put("commentProbabilityNum",commentProbabilityNum);
            map.put("countNum",MapUtils.getString(commentProbabilityNum,"countNum"));

            if(StringUtil.isNotBlank(MapUtils.getString(map,"reputation"))){
                productMap.put("reputation",MapUtils.getString(map,"reputation"));
            }

            List<Map<String,Object>> commentList = fishingServiceMapper.findCommentList(productMap);
            map.put("commentList",commentList);

            //查询团购数
            Integer orderCount  = fishingServiceMapper.findOrderCount(productMap);
            map.put("orderCount",orderCount);


            //联系人
            if (StringUtils.isNotBlank(MapUtils.getString(map, "contact"))) {
                JSONArray contact = JSONArray.parseArray(MapUtils.getString(map, "contact"));
                map.put("contact", contact);
            } else {
                map.put("contact", new String[0]);
            }
            //营业网点
            if (StringUtils.isNotBlank(MapUtils.getString(map, "serviceOutlet"))) {
                JSONArray serviceOutlet = JSONArray.parseArray(MapUtils.getString(map, "serviceOutlet"));
                map.put("serviceOutlet", serviceOutlet);
            } else {
                map.put("serviceOutlet", new String[0]);
            }

            //公交车
            if (StringUtils.isNotBlank(MapUtils.getString(map, "busLead"))) {
                JSONArray busLead = JSONArray.parseArray(MapUtils.getString(map, "busLead"));
                map.put("busLead", busLead);
            } else {
                map.put("busLead", new String[0]);
            }
            //自驾
            if (StringUtils.isNotBlank(MapUtils.getString(map, "traffic_lead"))) {
                JSONArray traffic_lead = JSONArray.parseArray(MapUtils.getString(map, "traffic_lead"));
                map.put("traffic_lead", traffic_lead);
            } else {
                map.put("traffic_lead", new String[0]);
            }

            String url = MapUtils.getString(map,"image_url");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("image_url",image_url);
            }else{
                continue;
            }
        }
        return list;
    }

    //保存服务评论
    @Override
    public void saveComment(Map<String,Object> params){
        Integer  star = MapUtils.getInteger(params,"star");
        if(star==10){
            params.put("reputation","3");
        }else if(star<10&&star>=6){
            params.put("reputation","2");
        }else{
            params.put("reputation","1");
        }

        fishingServiceMapper.saveComment(params);
    }


    //查询订单列表
    @Override
    public PageInfo<Map<String,Object>> findOrderList(Map<String,Object> params){
        Integer pageSize = 10;
        if(StringUtil.isNotBlank(MapUtils.getString(params,"pageSize"))){
            pageSize = Integer.valueOf(MapUtils.getString(params,"pageSize"));
        }
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), pageSize);
        List<Map<String,Object>> list = fishingServiceMapper.findOrderList(params);
        for(Map<String,Object> map :list){
            //联系人
            if (StringUtils.isNotBlank(MapUtils.getString(map, "contact"))) {
                JSONArray contact = JSONArray.parseArray(MapUtils.getString(map, "contact"));
                map.put("contact", contact);
            } else {
                map.put("contact", new String[0]);
            }
            if (StringUtils.isNotBlank(MapUtils.getString(map, "setMeal"))) {
                JSONArray setMeal = JSONArray.parseArray(MapUtils.getString(map, "setMeal"));
                map.put("setMeal", setMeal);
            } else {
                map.put("setMeal", new String[0]);
            }
            String url = MapUtils.getString(map,"imageUrl");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("imageUrl",image_url);
            }else{
                map.put("imageUrl",new String[]{});
            }
            String account = MapUtils.getString(map,"sellAccount");//买家
            Map<String,Object> sellMap = new HashMap<>();
            sellMap.put("account",account);
            //获取买家信息
            Map<String,Object> nameList  = fishingServiceMapper.findAvatar(sellMap);
            map.put("nameDetail",nameList);

            //若为咨询服务订单，查询consult_order_manage的ID
            if("5".equals(MapUtils.getString(map, "type"))) {
                String orderCode = MapUtils.getString(map, "orderCode");
                int consultDetailId = fishingServiceMapper.findCousultOrderId(orderCode);
                map.put("consultDetailId", consultDetailId);
            }
        }

        return new PageInfo<>(list);
    }

    //生成订单
    @Override
    public void saveOrder(Map<String,Object> params){
        String orderCode  = ChargeOrderCode(MapUtils.getString(params,"serviceId"));
        params.put("orderCode",orderCode);
        String type = MapUtils.getString(params,"type");
        //农家乐
        if(type.equals("3")){

            List<Map<String,Object>> parentList = (List<Map<String,Object>>)params.get("foodData");
            String parent_id ="";
            for(Map<String,Object> map:parentList){
                parent_id += MapUtils.getString(map,"foodId") +","+MapUtils.getString(map,"foodClassId")+";";
            }
            params.put("parentId",parent_id);


        }else if(type.equals("2")){
            List<Map<String,Object>> parentList = (List<Map<String,Object>>)params.get("scenicSpotData");
            String parent_id ="";
            if(parentList.size()>0){
                for(Map<String,Object> map:parentList){
                    parent_id += MapUtils.getString(map,"scenicSpotId") +";";
                }
            }
            params.put("parentId",parent_id);
        }else if(type.equals("4")){
            List<Map<String,Object>> parentList = (List<Map<String,Object>>)params.get("roomData");
            String parent_id ="";
            for(Map<String,Object> map:parentList){
                parent_id += MapUtils.getString(map,"roomId") +","+MapUtils.getString(map,"roomClassId")+";";
            }
            params.put("parentId",parent_id);
        } else if (type.equals("5")) {
            //将咨询服务保存在service_order_management中，供订单管理使用
            //计算订单结束时间
            String employTime = MapUtils.getString(params, "employTime");
            Calendar calendar = Calendar.getInstance();
            int count = MapUtils.getInteger(params, "count");

            if ("按小时".equals(employTime)) {
                calendar.add(Calendar.HOUR, count);

            } else if ("按天".equals(employTime)) {
                calendar.add(Calendar.DAY_OF_YEAR, count);

            } else if ("按月".equals(employTime)) {
                calendar.add(Calendar.MONTH, count);
            }
            String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
            params.put("endTime", endTime);
            params.put("status", 2);
            fishingServiceMapper.saveConsultOrder(params);
            //保存咨询服务下单的详细信息
            String data = JSON.toJSONString(params.get("data"));
            params.put("data", data);
            fishingServiceMapper.saveConsultDetail(params);
            return;
        }

        String setMeal  = JSON.toJSONString(params.get("setMeal"));
        if(!"null".equals(setMeal)&&StringUtil.isNotBlank(setMeal)){

            params.put("setMeal", setMeal);

        }




        fishingServiceMapper.saveServiceOrderManagement(params);
    }

    @Override
    public void updateOrderStatus(Map<String,Object> params){
        if(StringUtil.isNotBlank(MapUtils.getString(params,"status"))){
            if(MapUtils.getString(params,"status").equals("1")){//存储付款时间
                params.put("paymentTime","date");
            }

        }
        fishingServiceMapper.updateOrderStatus(params);
    }

    @Override
    public void updateOrderRefund(Map<String,Object> params){
        fishingServiceMapper.updateOrderRefund(params);
    }



    //生成订单号
    private String ChargeOrderCode(String id) {

        String str = Utils.builderRandomString(16);// + id;//12位随机数
        //这里需要判断随机码是否已经存在
        int count = fishingServiceMapper.findIsNotOrderId(str);
        if (count > 0) {
            return ChargeOrderCode(id);
        } else {
            return str;
        }
    }

    @Override
    public Map<String,Object> findAvatar(Map<String,Object> params){
        return fishingServiceMapper.findAvatar(params);
    }



}

