package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.member.mapper.ScenicSpotManagementMapper;
import com.ovit.nswy.member.service.ScenicSpotManagementService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

/**
 * 景区
 */
@Service
public class ScenicSpotManagementServiceImpl implements ScenicSpotManagementService {

    @Autowired
    private ScenicSpotManagementMapper scenicSpotManagementMapper;

    @Override
    public PageInfo<Map<String,Object>> findScenicSpotList(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  scenicSpotManagementMapper.findScenicSpotList(params);
        for(Map<String,Object> map:list){

            String flag = MapUtils.getString(map,"flag");
            if(!flag.equals("0")){
                String type  =  MapUtils.getString(map,"type");
                if(!type.equals("2")){
                    map.put("flag",0);
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public String insertScenicSpot(Map<String,Object> params){
        List<Map<String,Object>> list  =  scenicSpotManagementMapper.findList(params);
        if(list.size()>0){
            return "error";
        }else{
            scenicSpotManagementMapper.insertScenicSpot(params);
            return "success";
        }

    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public String updateScenicSpot(Map<String,Object> params){
        try{
            scenicSpotManagementMapper.deleteScenicSpot(params);
            Integer count = scenicSpotManagementMapper.findListCount(params);
            if(count>0){
                throw new Exception("error");
            }else{

                scenicSpotManagementMapper.insertScenicSpot(params);
                return "success";
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return "error";

    }

    @Override
    public void deleteScenicSpot(Map<String,Object> params){
        scenicSpotManagementMapper.deleteScenicSpot(params);
    }

}
