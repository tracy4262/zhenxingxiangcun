package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.RestaurantManagementMapper;
import com.ovit.nswy.member.service.RestaurantManagementService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.beans.Transient;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * 餐饮
 */
@Service
public class RestaurantManagementServiceImpl implements RestaurantManagementService {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    @Autowired
    private RestaurantManagementMapper restaurantManagementMapper;

    @Override
    public PageInfo<Map<String,Object>> findRestaurant(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  restaurantManagementMapper.findRestaurant(params);
        return new PageInfo<>(list);
    }

    @Override
    public String insertRestaurant(Map<String,Object> params){
        List<Map<String,Object>> list  =  restaurantManagementMapper.findRestaurant(params);
        if(list.size()>0){
            return "error";
        }else{
            restaurantManagementMapper.insertRestaurant(params);
            return "success";
        }
    }

    @Override
    public String updateRestaurant(Map<String,Object> params){
        Integer count  = restaurantManagementMapper.findRestaurantCount(params);
        if(count>0){
            return "error";
        }else{
            restaurantManagementMapper.updateRestaurant(params);
            return "success";
        }


    }

    @Override
    public void deleteRestaurant(Map<String,Object> params){
        restaurantManagementMapper.deleteRestaurant(params);
    }


    @Override
    public PageInfo<Map<String,Object>> findFood(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  restaurantManagementMapper.findFoodManagementList(params);
        for(Map<String,Object> map:list){
            String url = MapUtils.getString(map,"foodImage");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("foodImage",image_url);
            }else{
                continue;
            }
            String flag = MapUtils.getString(map,"flag");
            if(!flag.equals("0")){
               String type  =  MapUtils.getString(map,"type");
               if(!type.equals("3")){
                   map.put("flag",0);
               }
            }

        }
        return new PageInfo<>(list);
    }

    @Override
    public String insertFood(Map<String,Object> params){
//        String image_url  = MapUtils.getString(params,"foodImage");
//        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
//            params.put("foodImage",JsonToStringUtils.initJson(params,"foodImage"));
//
//        }
        Integer count   =  restaurantManagementMapper.findFoodCount(params);
        if(count>0){
            return "error";
        }else{
            restaurantManagementMapper.insertFood(params);
            return "success";
        }

    }

    @Override
    public String updateFood(Map<String,Object> params){
        params.put("type","0");
//        String image_url  = MapUtils.getString(params,"foodImage");
//        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
//            params.put("foodImage",JsonToStringUtils.initJson(params,"foodImage"));
//
//        }
        Integer count   =  restaurantManagementMapper.findFoodCount(params);
        if(count>0){
            return "error";
        }else{
            restaurantManagementMapper.updateFood(params);
            return "success";
        }


    }

    @Override
    public void deleteFood(Map<String,Object> params){
        restaurantManagementMapper.deleteFood(params);
    }


    @Override
    public PageInfo<Map<String,Object>> findRoom(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  restaurantManagementMapper.findRoomManagementList(params);
        for(Map<String,Object> map:list){
            String url = MapUtils.getString(map,"roomImage");
            if(!"null".equals(url)&&StringUtil.isNotBlank(url)){
                String[] image_url = url.split(",");
                map.put("roomImage",image_url);
            }else{
                continue;
            }
            String flag = MapUtils.getString(map,"flag");
            if(!flag.equals("0")){
                String type  =  MapUtils.getString(map,"type");
                if(StringUtil.isNotBlank(type)){
                    if(!type.equals("3")){
                        map.put("flag",0);
                    }
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public String insertRoom(Map<String,Object> params){
//        String image_url  = MapUtils.getString(params,"roomImage");
//        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
//            params.put("roomImage",JsonToStringUtils.initJson(params,"roomImage"));
//
//        }
        Integer count   =  restaurantManagementMapper.findRoomCount(params);
        if(count>0){
            return "error";
        }else{
            restaurantManagementMapper.insertRoom(params);
            return "success";
        }

    }

    @Override
    public String updateRoom(Map<String,Object> params){
//        String image_url  = MapUtils.getString(params,"roomImage");
//        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
//            params.put("roomImage",JsonToStringUtils.initJson(params,"roomImage"));
//
//        }
        params.put("type","0");
        Integer count   =  restaurantManagementMapper.findRoomCount(params);
        if(count>0){
            return "error";
        }else{
            restaurantManagementMapper.updateRoom(params);
            return "success";
        }



    }

    @Override
    public void deleteRoom(Map<String,Object> params){
        restaurantManagementMapper.deleteRoom(params);
    }


    @Override
    public PageInfo<Map<String,Object>> findTable(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  restaurantManagementMapper.findTable(params);
        return new PageInfo<>(list);
    }

    @Override
    public String insertTable(Map<String,Object> params){
        List<Map<String,Object>> list  =  restaurantManagementMapper.findTable(params);
        if(list.size()>0){
            return "error";
        }else{
            restaurantManagementMapper.insertTable(params);
            return "success";
        }

    }


/*    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            reader = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }*/

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public String updateTable(Map<String,Object> params){

       // SqlSession session = sqlSessionFactory.openSession(false); // 打开会话，事务开始
        try{
            restaurantManagementMapper.deleteTable(params);
            Integer count  = restaurantManagementMapper.findTableCount(params);
            if(count>0){
                throw new Exception("error");
            }else{
                restaurantManagementMapper.insertTable(params);
                //session.commit(); // 提交会话，即事务提交
                return "success";
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //throw new RuntimeException();
        }
        return "error";

    }

    @Override
    public void deleteTable(Map<String,Object> params){
        restaurantManagementMapper.deleteTable(params);
    }



    //服务详情
    @Override
    public List<Map<String,Object>> findTableDetail(Map<String,Object> params){

        List<Map<String,Object>> list  =  restaurantManagementMapper.findTable(params);
        return list;
    }

}
