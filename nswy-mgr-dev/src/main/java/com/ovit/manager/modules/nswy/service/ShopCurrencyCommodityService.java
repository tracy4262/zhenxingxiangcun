package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.persistence.PageList;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.CacheUtils;
import com.ovit.manager.modules.nswy.dao.ShopCommodityDao;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyCommodityDao;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-01 10:30
 **/
@Service
@Transactional
public class ShopCurrencyCommodityService extends CrudService<ShopCurrencyCommodityDao, ShopCurrencyCommityInfo>{
    @Autowired
    private ShopCurrencyCommodityDao shopCurrencyCommodityDao;

    public Page<ShopCurrencyCommityInfo> findInfoList(ShopCurrencyCommityInfo shopCurrencyCommityInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<ShopCurrencyCommityInfo> page = this.findPage(new Page<>(request,response),shopCurrencyCommityInfo);
        List<ShopCurrencyCommityInfo> list  = page.getList();
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

        return  page;
    }

    /**
     * 审核
     * @param params
     */
    public void adopt(Map<String,Object> params) {

        Map<String,Object> shop = shopCurrencyCommodityDao.findShopCurrencyCommodity(params);

        shopCurrencyCommodityDao.adopt(params);

        if(!MapUtils.getString(params,"approveStatus").equals("2")){
            Map<String,Object> shopList = shopCurrencyCommodityDao.findCurrencyCommodityList(shop);

            if(MapUtils.isEmpty(shopList)){
                shopCurrencyCommodityDao.saveCurrencyCommodityInfo(shop);
            }else{
                shopCurrencyCommodityDao.updateCurrencyCommodityInfo(shop);

            }
        }
    }


    public ShopCurrencyCommityInfo findList(Map<String,Object> params) {
        return  shopCurrencyCommodityDao.findList(params);
    }
}
