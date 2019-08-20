package com.ovit.nswy.member.certification.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.certification.mapper.SysFollowDictMapper;
import com.ovit.nswy.member.certification.model.FollowInfo;
import com.ovit.nswy.member.certification.model.SysFollowDict;
import com.ovit.nswy.member.certification.service.SysFollowDictService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemFollowDictServiceImpl implements SysFollowDictService {

   @Autowired
    private SysFollowDictMapper sysFollowDictMapper;

    @Override
    public void insert(SysFollowDict sysFollowDict) {
        sysFollowDictMapper.insert(sysFollowDict);
    }


    @Override
    public SysFollowDict findQuery(Map<String,Object> map){
        return sysFollowDictMapper.findQuery(map);

    }

    @Override
    public List<Map<String,Object>>  findList(Map<String,Object> map){
        List<Map<String,Object>>  maps = sysFollowDictMapper.findList(map);
        if(maps.size()>0){
            isChidren(maps);
        }
        return maps;
    }

    private void isChidren(List<Map<String,Object>>  followDictList){

        for(Map<String,Object> follow : followDictList){
            follow.put("checked",false);
            if(!MapUtils.getString(follow,"parent_id").equals("0")){
                follow.put("disabled",false);
            }

            //查询数据
            List<Map<String,Object>>  list = sysFollowDictMapper.findSysFollowDict(follow);
            if(list!= null&&list.size()>0){
                follow.put("children",list);
                if(!MapUtils.getString(follow,"parent_id").equals("0")){
                    follow.put("flod",true);
                }
                isChidren((List<Map<String,Object>>)follow.get("children"));

            }

        }


    }
/*
    @Override
    public List<Map<String,Object>> findList(Map<String,Object> map){
        List<Map<String,Object>> maps = sysFollowDictMapper.findList(map);
        if(maps.size()>0){
            for(int i=0;i<maps.size();i++){
                Map<String,Object> follow  = maps.get(i);
                follow.put("checked",false);
                List<Map<String,Object>>  list = sysFollowDictMapper.findSysFollowDict(follow);
                if(list!= null&&list.size()>0){
                    follow.put("children",list);
                    for(int j=0;j<list.size();j++){
                        Map<String,Object> parent  = list.get(j);
                        parent.put("flod",true);
                        parent.put("checked",false);
                        parent.put("disabled",false);
                        List<Map<String,Object>>  listParent = sysFollowDictMapper.findSysFollowDict(parent);
                        if(listParent!=null&&listParent.size()>0){
                            for(Map<String,Object> followDict:listParent){

                                followDict.put("checked",false);
                                followDict.put("disabled",false);
                            }
                            parent.put("children",listParent);
                        }
                    }
                }

            }
        }
        return maps;
    }*/

    @Override
    @Transactional
    public void followInsert(List<Map<String,Object>> followList, Map<String,Object> accountInfo){

        if(MapUtils.isNotEmpty(accountInfo)) {
            //删除已存在的数据
            sysFollowDictMapper.deleteFollowDict(accountInfo);
            List<List<Map<String,Object>>> list = (List<List<Map<String,Object>>>) followList.get(0).get("follow");
            for(int i=0;i<list.size();i++){
                List<Map<String,Object>> params = list.get(i);
                for(int j=0;j<params.size();j++){
                    Map<String,Object> param = params.get(j);
                    FollowInfo info = new FollowInfo();
                    info.setAccount(MapUtils.getString(accountInfo, "account"));
                    info.setSysFollowDictId(Integer.valueOf(param.get("id").toString()));
                    info.setFollowLabel(String.valueOf(param.get("name")));
                    info.setFollowType(String.valueOf(i));
                    info.setTemplateId(MapUtils.getString(accountInfo, "templateId"));
                    sysFollowDictMapper.insertFollow(info);
                }
            }
        }

    }

    @Override
    public  List<List<Map<String,Object>>>  findFllowDict(Map<String,Object> map){
        List<List<Map<String,Object>>> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            map.put("followType",String.valueOf(i));
            List<Map<String,Object>> lists = sysFollowDictMapper.findFllowDict(map);
            if (CollectionUtils.isEmpty(lists)) {
                map.put("account", MapUtils.getString(map, "adminAccount"));
                lists = sysFollowDictMapper.findFllowDict(map);
            }
            list.add(lists);
        }

        return list;
    }

    @Override
    public Map<String, Object> findPushStatus(Map<String, Object> accountInfo) {
        return sysFollowDictMapper.findPushStatus(accountInfo);
    }

    @Override
    public void savePushStatus(Map<String, Object> accountInfo) {

        Map<String,Object> push = sysFollowDictMapper.findPushStatus(accountInfo);
        if (MapUtils.isEmpty(push)) {
            //新增
            sysFollowDictMapper.addPushStatus(accountInfo);
        } else {
            //更新
            sysFollowDictMapper.updatePushStatus(accountInfo);
        }
    }

    @Override
    public void deletePushStatus(Map<String, Object> accountInfo) {
        sysFollowDictMapper.deletePushStatus(accountInfo);
    }

    /**
     * 删除关注信息
     * @param accountInfo
     */
    @Override
    public void followDelete(Map<String, Object> accountInfo) {

        if(MapUtils.isNotEmpty(accountInfo)) {
            //删除已存在的数据
            sysFollowDictMapper.deleteFollowDict(accountInfo);
        }
    }

}
