package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.http.HttpUtil;
import com.ovit.nswy.member.mapper.FansMapper;
import com.ovit.nswy.member.service.ExistsFriendService;
import com.ovit.nswy.member.service.FansService;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FansServiceImpl implements FansService {

    @Autowired
    private FansMapper fansMapper;
    private Logger logger = LogManager.getLogger(FansServiceImpl.class);

    @Autowired
    private ExistsFriendService existsFriendService;

    @Override
    public PageInfo<Map<String, Object>> getFans(Map<String, Object> params) {
        PageHelper.offsetPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> list = null;
        List<Map<String,Object>> listEnd = new ArrayList<Map<String,Object>>();
        Boolean isFriend = true;
        try {
            list = fansMapper.getFans(params);
            for (Map<String,Object> map:list){
                //判断是否互相关注
                Integer isStatus = fansMapper.status(map);
                if (isStatus != null){
                    map.put("isStatus",true);
                }else {
                    map.put("isStatus",false);
                }
                //判断是否是好友
                String account = String.valueOf(map.get("account"));
                String fromAccount = String.valueOf(map.get("fans_account"));
                Object object = existsFriendService.getExistsFriend(account,fromAccount);
                JSONObject jsonObject = JSON.parseObject(object.toString());
                isFriend = (Boolean) jsonObject.get("data");
//                String url = "http://localhost:8091/friend/getExistsFriend"+"/"+account+"/"+fromAccount;
//                String resul = HttpUtil.doGet(url);
//                JSONObject object = JSONObject.parseObject(resul);
//                isFriend = (Boolean) object.get("data");
                if (isFriend){
                    map.put("isFriend",true);
                }else {
                    map.put("isFriend",false);
                }
                listEnd.add(map);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        PageInfo<Map<String, Object>> page = new PageInfo<>(listEnd);
        Integer total = fansMapper.getFansTotal(params);
        page.setTotal(total);
        return page;
    }

    @Override
    public PageInfo<Map<String, Object>> getLike(Map<String, Object> params) {
        PageHelper.offsetPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> list;
        List<Map<String,Object>> listEnd = new ArrayList<Map<String,Object>>();
        Boolean isFriend = true;
        try {
            list = fansMapper.getLike(params);
            for (Map<String,Object> map:list){
                //判断是否互相关注
                Integer isStatus = fansMapper.status(map);
                if (isStatus != null){
                    map.put("isStatus",true);
                }else {
                    map.put("isStatus",false);
                }
                //判断是否是好友
                String account = String.valueOf(map.get("account"));
                String fromAccount = String.valueOf(map.get("fans_account"));
                Object object = existsFriendService.getExistsFriend(fromAccount,account);
                JSONObject jsonObject = JSON.parseObject(object.toString());
                isFriend = (Boolean) jsonObject.get("data");
//                String url = "http://localhost:8091/friend/getExistsFriend"+"/"+account+"/"+fromAccount;
//                String resul = HttpUtil.doGet(url);
//                JSONObject object = JSONObject.parseObject(resul);
//                isFriend = (Boolean) object.get("data");
                if (isFriend){
                    map.put("isFriend",true);
                }else {
                    map.put("isFriend",false);
                }
                listEnd.add(map);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        PageInfo<Map<String, Object>> page = new PageInfo<>(listEnd);
        Integer total = fansMapper.getLikeTotal(params);
        page.setTotal(total);
        return page;
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            fansMapper.add(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void del(Map<String, Object> params) {
        try {
            fansMapper.del(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public Boolean status(Map<String, Object> params) {
        Boolean status = true;
        try {
            Integer count = fansMapper.status(params);
            if (count == null) {
                status = false;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return status;
    }

    @Override
    public void cancel(Map<String, Object> params) {
        try {
            fansMapper.cancel(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void batchLike(Map<String, Object> params) {
        try {
            List<String> list = (List)(params.get("list"));
            for (String account:list){
                params.put("fans_account",account);
                fansMapper.add(params);
            }
        }catch (Exception e){
            logger.error(e);
        }
    }
}