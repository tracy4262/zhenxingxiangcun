package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NswyInformationExamineDao;
import com.ovit.manager.modules.nswy.dao.ShopCommodityDao;

import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ShopCommodityService extends CrudService<ShopCommodityDao, ShopCommityInfo> {
    @Autowired
    private ShopCommodityDao shopCommodityDao;

    public Page<Map<String,Object>> findInfoList(Map<String, Object> params) {
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

        String url = Global.getConfig("api.url")+"/shop/pushShopCommodity/findProductList";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{\"pageNum\":")
                .append(Integer.valueOf(pageNo))
                .append(",\"pageSize\":")
                .append(Integer.valueOf(pageSize));
        if(params.get("commodityName") != null && params.get("commodityName") != ""){
            jsonBody.append(",\"keyword\":")
                    .append("\"").append(params.get("commodityName")).append("\"");
        }

        jsonBody.append("}");

        Page<Map<String, Object>> page =new Page<>();
        List<Map<String,Object>> list;
        try{
            String resul = HttpUtil.doPost(url,jsonBody.toString());
            JSONObject object = JSONObject.parseObject(resul);
            Map<String,Object> map  = (Map<String,Object>)object.get("data");
            list = (List<Map<String,Object>>)map.get("list");

            Long total = Long.parseLong(String.valueOf(map.get("total")));
            page.setList(list);
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize(Integer.parseInt(pageSize));
            page.setCount(total);
            //设置总页数
            Integer totals = Integer.valueOf(String.valueOf(total));
            Integer size  = Integer.valueOf(pageSize);
            page.setLast( totals % size == 0 ? totals / size : totals / size + 1);
            if (Integer.parseInt(pageNo) != 0){
                page.setPrev(Integer.parseInt(pageNo)-1);
            }
            page.setNext(Integer.parseInt(pageNo)+1);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  page;
    }


    /**
     * 审核
     * @param params
     */
    public void adopt(Map<String,Object> params) {
        shopCommodityDao.adopt(params);
    }

    /**
     * 是否显示在首页
     * @param map
     */
    public void updateHome(Map<String, Object> map) {
        String url = Global.getConfig("api.url")+"/shop/pushShopCommodity/updateHomeDisplay";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{\"isHomeDisplay\":")
                .append(map.get("isHomeDisplay"))
                .append(",\"id\":")
                .append("\"").append(map.get("id")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsonBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否显示在导航
     * @param map
     */
    public void updateNavDisplay(Map<String, Object> map) {
        String url = Global.getConfig("api.url")+"/shop/pushShopCommodity/updateHomeDisplay";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{\"isNavDisplay\":")
                .append(map.get("isNavDisplay"))
                .append(",\"id\":")
                .append("\"").append(map.get("id")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsonBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否显示在商城
     * @param map
     */
    public void updateShopDisplay(Map<String, Object> map) {
        String url = Global.getConfig("api.url")+"/shop/pushShopCommodity/updateHomeDisplay";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{\"isShopDisplay\":")
                .append(map.get("isShopDisplay"))
                .append(",\"id\":")
                .append("\"").append(map.get("id")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsonBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 排序
     * @param map
     */
    public void updateOrderNum(Map<String, Object> map) {
        String url = Global.getConfig("api.url")+"/shop/pushShopCommodity/updateHomeDisplay";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{\"orderNum\":")
                .append(map.get("orderNum"))
                .append(",\"id\":")
                .append("\"").append(map.get("id")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsonBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
