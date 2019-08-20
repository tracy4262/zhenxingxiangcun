package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyServiceDao;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyServiceDao;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyServiceInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ShopCurrencyServiceService extends CrudService<ShopCurrencyServiceDao, ShopCurrencyServiceInfo>{
    @Autowired
    private ShopCurrencyServiceDao shopCurrencyServiceDao;

    public Page<ShopCurrencyServiceInfo> findInfoList(ShopCurrencyServiceInfo shopCurrencyServiceInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<ShopCurrencyServiceInfo> page = this.findPage(new Page<>(request,response),shopCurrencyServiceInfo);
        List<ShopCurrencyServiceInfo> list  = page.getList();
        for(ShopCurrencyServiceInfo info:list){
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

        Map<String,Object> shop = shopCurrencyServiceDao.findShopCurrencyService(params);

        shopCurrencyServiceDao.adopt(params);
        if(!MapUtils.getString(params,"approveStatus").equals("2")){
            Map<String,Object> shopList = shopCurrencyServiceDao.findCurrencyServiceList(shop);
            shopCurrencyServiceDao.saveCurrencyServiceInfo(shop);

            /*if(MapUtils.isEmpty(shopList)){
                shopCurrencyServiceDao.saveCurrencyServiceInfo(shop);
            }else{
                shopCurrencyServiceDao.updateCurrencyServiceInfo(shop);

            }*/
        }


    }


    public ShopCurrencyServiceInfo findList(Map<String,Object> params) {
        return  shopCurrencyServiceDao.findList(params);
    }


}
