package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.SysFollowDictMapper;
import com.ovit.nswy.member.mapper.SystemDictMapper;
import com.ovit.nswy.member.model.FollowInfo;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.SysFollowDict;
import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.service.SysFollowDictService;
import com.ovit.nswy.member.service.SystemDictService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author haoWen
 * @create 2018-01-31 15:50
 **/
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

    @Override
    @Transactional
    public void followInsert(List<Map<String,Object>> followList, String account){
        //String account = MapUtils.getString(map,"account");
        if(StringUtil.isNotEmpty(account)){
            //删除已存在的数据
            Map<String,Object> map = new HashMap<>();
            map.put("account", account);
            sysFollowDictMapper.deleteFollowDict(map);
            List<List<Map<String,Object>>> list = (List<List<Map<String,Object>>>) followList.get(0).get("follow");
            for(int i=0;i<list.size();i++){
                List<Map<String,Object>> params = list.get(i);
                for(int j=0;j<params.size();j++){
                    Map<String,Object> param = params.get(j);
                    FollowInfo info = new FollowInfo();
                    info.setAccount(account);
                    info.setSysFollowDictId(Integer.valueOf(param.get("id").toString()));
                    info.setFollowLabel(String.valueOf(param.get("name")));
                    info.setFollowType(String.valueOf(i));
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
            list.add(lists);
        }

        return list;
    }

}
