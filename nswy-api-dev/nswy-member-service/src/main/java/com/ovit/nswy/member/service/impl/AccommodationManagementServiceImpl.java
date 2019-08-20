package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.AccommodationManagementMapper;
import com.ovit.nswy.member.service.AccommodationManagementService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

/**
 * 住宿
 */
@Service
public class AccommodationManagementServiceImpl implements AccommodationManagementService {

    @Autowired
    private AccommodationManagementMapper accommodationManagementMapper;


    @Override
    public PageInfo<Map<String,Object>> findRoomClass(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  accommodationManagementMapper.findRoomClass(params);
        return new PageInfo<>(list);
    }

    @Override
    public String insertRoomClass(Map<String,Object> params){
        List<Map<String,Object>> list  =  accommodationManagementMapper.findRoomClass(params);
        if(list.size()>0){
           return "error";
        }else{
            accommodationManagementMapper.insertRoomClass(params);
            return "success";
        }

    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public String updateRoomClass(Map<String,Object> params){

        try{
            accommodationManagementMapper.deleteRoomClass(params);
            Integer count   =  accommodationManagementMapper.findRoomClassCount(params);
            if(count>0){
                throw new Exception("error");

            }else{
                accommodationManagementMapper.insertRoomClass(params);
                return "success";
            }

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }


        return "error";

    }

    @Override
    public void deleteRoomClass(Map<String,Object> params){
        accommodationManagementMapper.deleteRoomClass(params);
    }


    @Override
    public PageInfo<Map<String,Object>> findRoomList(Map<String,Object> params){
         PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  accommodationManagementMapper.findRoomListManagementList(params);
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
                if(!type.equals("4")){
                    map.put("flag",0);
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public String insertRoomList(Map<String,Object> params){
        String image_url  = MapUtils.getString(params,"roomImage");
        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
            params.put("roomImage",JsonToStringUtils.initJson(params,"roomImage"));

        }
        Integer count  = accommodationManagementMapper.findRoomListCount(params);
        if(count>0){
            return "error";
        }else{
            accommodationManagementMapper.insertRoomList(params);
            return "success";
        }

    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public String updateRoomList(Map<String,Object> params){
        params.put("type","0");
        String image_url  = MapUtils.getString(params,"roomImage");
        if(!"null".equals(image_url)&&StringUtil.isNotBlank(image_url)){
            params.put("roomImage",JsonToStringUtils.initJson(params,"roomImage"));

        }
        try{
            accommodationManagementMapper.deleteRoomList(params);
            Integer count   =  accommodationManagementMapper.findRoomListCount(params);
            if(count>0){
                throw new Exception("error");

            }else{
                accommodationManagementMapper.insertRoomList(params);
                return "success";
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }


        return "error";
    }

    @Override
    public void deleteRoomList(Map<String,Object> params){
        accommodationManagementMapper.deleteRoomList(params);
    }




}
