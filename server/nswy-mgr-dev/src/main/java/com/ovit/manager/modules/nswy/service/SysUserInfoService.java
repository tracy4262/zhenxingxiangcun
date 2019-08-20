package com.ovit.manager.modules.nswy.service;


import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.CacheUtils;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyCommodityDao;
import com.ovit.manager.modules.nswy.dao.SysUserInfoDao;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;
import com.ovit.manager.modules.nswy.entity.SysUserInfo;


import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class SysUserInfoService extends CrudService<SysUserInfoDao,SysUserInfo> {


    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Autowired
    private ShopCurrencyCommodityDao shopCurrencyCommodityDao;

    /**
     * 定时查询，将数据加入缓存中
     */
    @Transactional
    public void updateLoginOfUser(){
        CacheUtils.removeAll("currencyCommityInfo");
        List<ShopCurrencyCommityInfo> list  = shopCurrencyCommodityDao.findListCache();
        for(ShopCurrencyCommityInfo info:list){
            String url = Global.getConfig("api.url")+"/wiki/api/species/listSpecies";
            StringBuffer jsonBody = new StringBuffer();
            jsonBody.append("{speciesid:").append("\"").append(info.getRelatedSpeciesId()).append("\"").append("}");
            List<Map<String,Object>> lists;
            try{
                String resul = HttpUtil.doPost(url,jsonBody.toString());
                JSONObject object = JSONObject.parseObject(resul);
                lists = (List<Map<String,Object>>) object.get("data");
                Map<String,Object> map  = lists.get(0);
                info.setRelatedSpeciesName(String.valueOf(map.get("fname")));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        CacheUtils.put("currencyCommityInfo",list);

    }

    private void insert(List<Map<String,Object>> list){
        for(Map<String,Object> map:list){
            String userType = MapUtils.getString(map,"user_type");
            map.put("privacy_information","0");
            map.put("network_information","0");
            map.put("education_live","0");
            map.put("work_live","0");
            map.put("political_status","0");
            map.put("religion_faith","0");
            map.put("specialty_information","0");
            map.put("honour_style","0");
            map.put("houses_condition","0");
            map.put("home_condition","0");
            map.put("organization","0");
            map.put("management_team","0");
            map.put("survey_operation","0");
            map.put("finance_information","0");
            map.put("property_information","0");
            map.put("product_serve","0");
            map.put("specialty_credentials","0");
            sysUserInfoDao.insertSysUserInfo(map);

                    /*if("0".equals(userType)){

                    }else if("1".equals(userType)){

                    }else if("3".equals(userType)){

                    }else if("4".equals(userType)){

                    }else if("5".equals(userType)){

                    }*/

        }
    }

    /**
     * 查询sys_user_info
     * @param sysUserInfo
     * @param request
     * @param response
     * @return
     */
    public Page<SysUserInfo> findList(SysUserInfo sysUserInfo, HttpServletRequest request, HttpServletResponse response){
        Page<SysUserInfo> page = this.findPage(new Page<SysUserInfo>(request, response), sysUserInfo);
        logger.info("代理审核列表查询：{}"+page.getList());
        return page;
    }

    @Transactional(readOnly = false)
    public Integer updateUserInfo(Map<String,Object> params){
        Integer result = 0;
        try{
            result =sysUserInfoDao.updateUserInfo(params);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

}
