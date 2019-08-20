package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.FollowManageMapper;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.FollowManageService;
import com.ovit.nswy.member.service.SpeciesClassService;
import com.ovit.nswy.member.util.PageUtils;
import com.ovit.nswy.member.web.base.Result;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowManageServiceImpl implements FollowManageService {

    @Autowired
    private FollowManageMapper followManageMapper;

    @Autowired
    private SpeciesClassService service;

    @Override
    public List<Map<String,Object>> findSepciesList(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();
        params.put("status","0");
        List<Map<String,Object>> followList  = followManageMapper.findFollowList(params);
        int animal=0;
        int plant=0;
        Map<String,Object> speciesMap = new HashMap<>();
        speciesMap.put("animalTotal",animal);
        speciesMap.put("plantTotal",plant);
        for(Map<String,Object> map:followList){
            if(MapUtils.getString(map,"followType").equals("animal")){
                animal++;
                speciesMap.put("animalTotal",animal);
            }
            if(MapUtils.getString(map,"followType").equals("plant")){
                plant++;
                speciesMap.put("plantTotal",plant);
            }
        }
        Map<String,Object> animalMap = new HashMap<>();
        animalMap.put("name","动物");
        animalMap.put("type","animal");
        animalMap.put("total",StringUtil.isBlank(MapUtils.getInteger(speciesMap,"animalTotal").toString()) ? 0 : MapUtils.getInteger(speciesMap,"animalTotal"));
        list.add(animalMap);
        Map<String,Object> plantMap = new HashMap<>();
        plantMap.put("name","植物");
        plantMap.put("type","plant");
        plantMap.put("total", StringUtil.isBlank(MapUtils.getInteger(speciesMap,"plantTotal").toString()) ? 0 : MapUtils.getInteger(speciesMap,"plantTotal"));
        list.add(plantMap);
        return list;
    }


    @Override
    public  PageInfo<Map<String,Object>> findSpecByAccount(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        Map<String,Object> map = new HashMap<>();
        map.put("account",MapUtils.getString(params,"account"));
        map.put("templateId",MapUtils.getString(params,"templateId"));
        map.put("type",MapUtils.getString(params,"type"));
        List<Map<String,Object>> followList  = followManageMapper.findFollowList(map);
        if(followList.size() ==0 || followList == null){
            findAllList(params,map);
        }
        followList = new ArrayList<>();
        params.put("status","0");
        followList  = followManageMapper.findFollowList(params);


       return new PageInfo<>(followList);
    }

    @Override
    public void insertFollow(Map<String,Object> params){
        List<Map<String,Object>> followList = new ArrayList<>();
        params.put("status","0");
        if(MapUtils.getString(params,"type").equals("1")){

            Result result = findBySpeciesName(MapUtils.getString(params,"label"));
            JSONArray jsonData = (JSONArray)result.getData();
            List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);

            result = findByClassId(MapUtils.getString(jsonList.get(0),"fclassifiedid"));
            jsonData = (JSONArray)result.getData();

            jsonList  = jsonArrayToList(jsonData);
            if( MapUtils.getString(jsonList.get(0),"parentId").equals("0")){
                params.put("followType","animal");
            }else{
                params.put("followType","plant");
            }
            followList= followManageMapper.findFollowList(params);
        }else if(MapUtils.getString(params,"type").equals("6")||MapUtils.getString(params,"type").equals("7")||MapUtils.getString(params,"type").equals("8")){
            followList= followManageMapper.findFollowList(params);
            params.put("followType",MapUtils.getString(params,"parentId"));

        }


        if(followList.size() == 0 || followList == null){
            followManageMapper.saveFollowInfo(params);
        }


    }

    @Override
    public void deleteFollowInfo(Map<String,Object> params){


        followManageMapper.deleteFollowInfo(params);
    }

    @Override
    public List<Map<String,Object>> findList(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("account",MapUtils.getString(params,"account"));
        map.put("templateId",MapUtils.getString(params,"templateId"));
        if(MapUtils.getString(params,"follow_type").equals("information")){
            params.put("type","6");
            map.put("type","6");
        }else if(MapUtils.getString(params,"follow_type").equals("knowledge")){//知识
            params.put("type","7");
            map.put("type","7");
        }else if(MapUtils.getString(params,"follow_type").equals("policy")){//政策
            params.put("type","8");
            map.put("type","8");
        }
        //判断是否有数据吗，无数据，从个性化中初始化数据进来
        List<Map<String,Object>> followList  = followManageMapper.findFollowList(map);
        if(followList.size() ==0 || followList == null){
            findAllList(params,map);
        }

        List<Map<String,Object>> list  = followManageMapper.findList(params);
        int number = 0;
        for(Map<String,Object> maps :list){

            params.put("followType",MapUtils.getString(maps,"id"));
            params.put("status","0");

            Integer total = followManageMapper.findCountFollowManageInfo(params);

            number += total;

            maps.put("total",total);
        }

        return list;
    }


    @Override
    public PageInfo<Map<String,Object>> findLoginByMember(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));

        List<Map<String,Object>> list = new ArrayList<>();

        if(MapUtils.getString(params,"memberClass").equals("专家")){
            list =  followManageMapper.findExpertInfo(params);
        }else{

            list = followManageMapper.findLoginByMember(params);
        }

        Map<String,Object> memebr = new HashMap<>();

        memebr.put("account",MapUtils.getString(params,"account"));

        List<Map<String,Object>> memberInfo  = followManageMapper.findFollowMemberInfo(memebr);

        for(Map<String,Object> follow :list){
            for(Map<String,Object> map : memberInfo){
                if(MapUtils.getString(follow,"account").equals(MapUtils.getString(map,"followAccount"))){
                    follow.put("followType","1");
                    follow.put("id",MapUtils.getString(map,"id"));
                }

            }

        }

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String,Object>> findLoginByMemberList(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));

        List<Map<String,Object>> memberInfo  = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        //我关注的
        if(MapUtils.getString(params,"type").equals("0")){
            memberInfo  = followManageMapper.findLoginByMemberList(params);
            for( Map<String,Object> info :memberInfo){
                info.put("followType","1");
            }
        }else{
            map.put("followAccount",MapUtils.getString(params,"account"));
            //关注我的
            memberInfo  = followManageMapper.findLoginByMemberList(map);

            Map<String,Object> memberMap = new HashMap<>();
            memberMap.put("account",MapUtils.getString(params,"account"));
            //我关注的
            List<Map<String,Object>> memberList = followManageMapper.findLoginByMemberList(memberMap);
            if(memberList.size()>0 && memberList !=null){
                for( Map<String,Object> info :memberInfo){
                    for( Map<String,Object> member :memberList){
                        if(MapUtils.getString(info,"account").equals(MapUtils.getString(member,"followAccount"))){
                            info.put("followType","1");
                            info.put("id",MapUtils.getString(member,"id"));
                            break;
                        }else{
                            info.put("followType","0");
                        }
                    }
                }
            }else{
                for( Map<String,Object> info :memberInfo){
                    info.put("followType","0");
                }

            }



        }


        return new PageInfo<>(memberInfo);
    }


    @Override
    public void insertFollowMemberInfo(Map<String,Object> params){
        followManageMapper.insertFollowMemberInfo(params);
    }

    @Override
    public void deleteFollowMemberInfo(Map<String,Object> params){
        followManageMapper.deleteFollowMemberInfo(params);
    }

    @Override
    public void updateFollowMemberInfo(Map<String,Object> params){
        followManageMapper.updateFollowMemberInfo(params);
    }



    private void findAllList(Map<String,Object> params,Map<String,Object> map){
        //物种
        if(MapUtils.getString(params,"type").equals("1")){
            Map<String,Object> speciesMap = followManageMapper.findSpecByAccount(params);
            JSONArray species = JSONArray.parseArray(MapUtils.getString(speciesMap,"speciesName"));
            List<Map<String,Object>> speciesList = jsonArrayToList(species);
            for(Map<String,Object> sp:speciesList){
                Map<String,Object> followMap = new HashMap<>();
                followMap.put("account",MapUtils.getString(params,"account"));
                followMap.put("templateId",MapUtils.getString(params,"templateId"));
                /**
                 * 根据物种名称去查询物种id
                 */
                Result result = findBySpeciesName(MapUtils.getString(sp,"name"));
                JSONArray jsonData = (JSONArray)result.getData();
                List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
                followMap.put("value",MapUtils.getString(jsonList.get(0),"speciesid"));
                followMap.put("label",MapUtils.getString(sp,"name"));
                followMap.put("type",MapUtils.getString(params,"type"));
                result = findByClassId(MapUtils.getString(sp,"id"));
                jsonData = (JSONArray)result.getData();

                /**
                 * 判断植物，动物
                 */
                jsonList  = jsonArrayToList(jsonData);
                if( MapUtils.getString(jsonList.get(0),"parentId").equals("0")){
                    followMap.put("followType","animal");
                }else{
                    followMap.put("followType","plant");
                }
                List<Map<String,Object>> followList  = followManageMapper.findFollowList(followMap);
                followManageMapper.saveFollowInfo(followMap);

            }


        } else if(MapUtils.getString(params,"type").equals("6")||MapUtils.getString(params,"type").equals("7")||MapUtils.getString(params,"type").equals("8")){//资讯，知识,政策
            List<Map<String,Object>> list = new ArrayList<>();
            //资讯
            if(MapUtils.getString(params,"type").equals("6")){
                map.put("infoType","1");
            }else if(MapUtils.getString(params,"type").equals("7")){//知识
                map.put("infoType","0");
            }else if(MapUtils.getString(params,"type").equals("8")){//政策
                map.put("infoType","2");
            }

            list = followManageMapper.findFllowDict(map);

            for(Map<String,Object> info:list){
                Map<String,Object> followMap = new HashMap<>();

                followMap.put("account",MapUtils.getString(params,"account"));
                followMap.put("templateId",MapUtils.getString(params,"templateId"));
                followMap.put("value",MapUtils.getString(info,"id"));
                followMap.put("label",MapUtils.getString(info,"name"));
                followMap.put("type",MapUtils.getString(params,"type"));
                isParentId(info,followMap);
                followManageMapper.saveFollowInfo(followMap);
            }


        }

    }



    private Result findByClassId(String classId) {

        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);

        return service.findByClassId(JSON.toJSON(map));
    }


    private Result findBySpeciesName(String fname) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        return service.findBySpeciesName(JSON.toJSON(map));
    }
    /**
     * JSONArray转为List
     */
    private List<Map<String,Object>> jsonArrayToList(JSONArray jsonArray) {

        List<Map<String,Object>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Map<String,Object> t = JSONObject.toJavaObject(jsonObject, Map.class);
            list.add(t);
        }
        return list;
    }


    private void isParentId(Map<String,Object> params,Map<String,Object> followMap){
        Map<String,Object> manage = followManageMapper.findFollowInfoByParentId(params);
        if(!MapUtils.getString(manage,"pid").equals("0")){
            isParentId(manage,followMap);
        }else{
            followMap.put("followType",MapUtils.getString(manage,"id"));
        }

    }

}
