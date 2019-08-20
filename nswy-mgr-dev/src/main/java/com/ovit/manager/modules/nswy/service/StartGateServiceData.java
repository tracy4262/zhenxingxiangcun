package com.ovit.manager.modules.nswy.service;



import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.utils.CacheUtils;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyCommodityDao;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class StartGateServiceData implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log LOGGER = LogFactory.getLog(StartGateServiceData.class);

    @Autowired
    private ShopCurrencyCommodityDao shopCurrencyCommodityDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
             try {
                   // 在web项目中（spring mvc），系统会存在两个容器，一个是root application context
                   // ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。
                  // 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免这个问题，我们可以只在root
                  // application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理。
                  if (event.getApplicationContext().getParent() == null) {
                      /*CacheUtils.removeAll("currencyCommityInfo");
                        // 需要实现的功能
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
                      CacheUtils.put("currencyCommityInfo",list);*/
                  }
                } catch (Exception e) {
                   LOGGER.error("StartGateServiceData", e);
             }
    }
}
