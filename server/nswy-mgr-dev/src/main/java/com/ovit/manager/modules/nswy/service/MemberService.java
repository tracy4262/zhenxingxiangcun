package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.modules.nswy.dao.MemberDao;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MemberService{
    @Autowired
    private MemberDao memberDao;

    /*public List<Map<String,Object>> findList(Map<String,Object> params){
        List<Map<String,Object>> memberType = memberDao.findMemberType();
        List<Map<String,Object>> member = memberDao.findRealMember(params);
        for(Map map: member){
            Map map1 = new HashMap();
            map1.put("account",MapUtils.getString(map,"account"));

            map.put("speciesName",memberDao.findRelatedSpecies(map1));
            map.put("productName",memberDao.findRelatedProduct(map1));
            map.put("serviceName",memberDao.findRelatedService(map1));
            map.put("industryName",memberDao.findRelatedIndustry(map1));

            String[] strs = MapUtils.getString(map,"memberClass").split(";");
            if(strs.length>1){
                map.put("pid",strs[strs.length-2]);
            }else {
                map.put("pid",strs[0]);
            }

        }

        member.addAll(memberType);
        return member;
    }
*/
    public Page<Map<String,Object>> findRealMember(Map<String,Object> params){
        String pageNo;
        String pageSize;
        if (params.get("pageNo") != null && params.get("pageNo") != ""){
            pageNo = String.valueOf(params.get("pageNo"));
        }else {
            pageNo = "1";
        }
        if (params.get("pageSize") != null && params.get("pageSize") != ""){
            pageSize = String.valueOf(params.get("pageSize"));
        }else {
            pageSize = "30";
        }
        params.remove("pageSize");
        params.remove("pageNo");

        List<Map<String,Object>> member = memberDao.findRealMember(params);
        for(Map map: member){
            Map map1 = new HashMap();
            map1.put("account",MapUtils.getString(map,"account"));

            map.put("speciesName",memberDao.findRelatedSpecies(map1));
            map.put("productName",memberDao.findRelatedProduct(map1));
            map.put("serviceName",memberDao.findRelatedService(map1));
            map.put("industryName",memberDao.findRelatedIndustry(map1));

            String memberClass = "";
            String[] strs = MapUtils.getString(map,"memberClass").split(";");
            for(String str: strs){
                String memberType = memberDao.findMemberType(str);
                if(StringUtils.isNotBlank(memberType)){
                    memberClass = memberClass + memberType+"/";

                }
            }
            map.put("memberClass",memberClass);


        }

        Page<Map<String,Object>> page = new Page<>();
        page.setList(member);
        page.setCount(member.size());
        page.setPageNo(Integer.parseInt(pageNo));
        page.setPageSize(Integer.parseInt(pageSize));
        //设置总页数
        Integer totals = member.size();
        Integer size  = Integer.valueOf(pageSize);
        page.setLast( totals % size == 0 ? totals / size : totals / size + 1);
        if (Integer.parseInt(pageNo) != 0){
            page.setPrev(Integer.parseInt(pageNo)-1);
        }
        page.setNext(Integer.parseInt(pageNo)+1);
        return page;
    }

    public void memberSetOrderNum(Map<String, Object> map) {
        memberDao.memberSetOrderNum(map);
    }
    @Transactional()
    public void memberUpdateHome(Map<String, Object> map) {
        memberDao.memberUpdateHome(map);
    }



}
