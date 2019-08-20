package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.member.adapter.ShopItfService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopProductController {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ShopItfService shopItfService;

    @RequestMapping(value = "/getRecommend/{type}", method = RequestMethod.GET)
    public Result getRecommend(@PathVariable int type, HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        String str = shopItfService.getRecommendProduct(type, currentPage, pageSize);
        JSONObject json = (JSONObject) JSONObject.parse(str);
        Result result = new Result();
        result.setData(json);
        return result;
    }

    @RequestMapping(value = "/getAllProduct", method = RequestMethod.GET)
    public Result getAllProduct(HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        Map<String, JSONArray> result = new HashMap<String, JSONArray>();
        String hotGroupBuy = shopItfService.getFullProduct(1, currentPage, pageSize);
        String fixPriceProduct = shopItfService.getFullProduct(0, currentPage, pageSize);
        String ypAuction = shopItfService.getFullProduct(2, currentPage, pageSize);
        String newPresell = shopItfService.getFullProduct(3, currentPage, pageSize);
        String spot = shopItfService.getSpotProduct(currentPage, pageSize);
        String ascend = shopItfService.getTraceabilityProduct(currentPage, pageSize);
        JSONObject json = (JSONObject) JSONObject.parse(hotGroupBuy);
        result.put("hotGroupBuy", json.getJSONArray("list"));
        json = (JSONObject) JSONObject.parse(fixPriceProduct);
        result.put("fixPriceProduct", json.getJSONArray("list"));
        json = (JSONObject) JSONObject.parse(ypAuction);
        result.put("ypAuction", json.getJSONArray("list"));
        json = (JSONObject) JSONObject.parse(newPresell);
        result.put("newPresell", json.getJSONArray("list"));
        json = (JSONObject) JSONObject.parse(spot);
        result.put("spot", json.getJSONArray("list"));
        json = (JSONObject) JSONObject.parse(ascend);
        result.put("ascend", json.getJSONArray("list"));
        Result ret = new Result();
        ret.setData(result);
        return ret;
    }

    @RequestMapping(value = "/getFullProduct/{type}", method = RequestMethod.GET)
    public Result getFullProduct(@PathVariable int type, HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        String str = shopItfService.getFullProduct(type, currentPage, pageSize);
        JSONObject json = rebuild(str);
        Result result = new Result();
        result.setData(json);
        return result;
    }

    @RequestMapping(value = "/getAscendProduct", method = RequestMethod.GET)
    public Result getAscendProduct(HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        String str = shopItfService.getTraceabilityProduct(currentPage, pageSize);
        JSONObject json = rebuild(str);
        Result result = new Result();
        result.setData(json);
        return result;
    }

    @RequestMapping(value = "/getSpotProduct", method = RequestMethod.GET)
    public Result getSpotProduct(HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        String str = shopItfService.getSpotProduct(currentPage, pageSize);
        JSONObject json = rebuild(str);
        Result result = new Result();
        result.setData(json);
        return result;
    }

    private JSONObject rebuild(String result) {
        JSONObject json = (JSONObject) JSONObject.parse(result);
        JSONArray array = json.getJSONArray("list");
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            JSONObject addr = obj.getJSONObject("addr");
            String add = "";
            if (addr.containsKey("province")) {
                add = addr.get("province").toString();
                if (addr.containsKey("city")) {
                    String city = addr.get("city").toString();
                    if (!add.equals(city)) {
                        if (!"省直辖县级行政单位".equals(city)) {
                            add += city;
                        }
                    }
                    if (addr.containsKey("county")) {
                        add += addr.get("county").toString();
                    }
                }
            }
            obj.put("addr", add);
            if (obj.containsKey("last_time") && null != obj.get("last_time")) {
                long last_time = obj.getLong("last_time");
                Date date = new Date(last_time);
                String lastTime = DATE_FORMAT.format(date);
                obj.put("last_time", lastTime);
            }
        }
        json.put("list", array);
        return json;
    }
}
