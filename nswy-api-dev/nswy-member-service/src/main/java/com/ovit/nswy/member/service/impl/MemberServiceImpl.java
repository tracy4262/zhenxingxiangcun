package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CorpInfoMapper;
import com.ovit.nswy.member.mapper.MemberMapper;
import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.model.Member;
import com.ovit.nswy.member.service.MemberService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public PageInfo<Map<String, Object>> selectByName(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = new ArrayList<>();
        if(MapUtils.getString(params,"status")!=null) {
            if (MapUtils.getString(params, "status").equals("0")) {
                List<Map<String, Object>> list1 = memberMapper.findRealMember(params);
                //根据行政区划筛选，匹配发布咨询服务时选择的联系人的地址
                Iterator<Map<String, Object>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> map = iterator.next();
                    String account = MapUtils.getString(map, "account");
                    Map<String, Object> memberMap = new HashMap<>();
                    memberMap.put("account", account);
                   Integer count = memberMapper.findNotExpert(memberMap);
                    if (count>0) {
                        iterator.remove();
                    }
                }
                list = list1;
            } else {
                list = memberMapper.findExpertlist(params);//专家
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> findMemberType(Map<String,Object> params){
        List<Map<String, Object>> list = memberMapper.findMemberType(params);
        if(list!=null&&list.size()>0){
            for(Map map:list){
                params.put("pid", MapUtils.getString(map,"value"));
                List<Map<String, Object>> list1 = findMemberType(params);
                if (list1!=null&&list1.size()>0) {
                    map.put("children",list1);
                }
            }
        }
        return list;
    }
}
