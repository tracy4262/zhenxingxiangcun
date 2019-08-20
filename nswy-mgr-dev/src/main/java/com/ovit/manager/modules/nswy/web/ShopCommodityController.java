package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;

import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;
import com.ovit.manager.modules.nswy.service.ShopCommodityService;
import com.ovit.manager.modules.nswy.service.ShopCurrencyCommodityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品审核
 *
 * @author haoWen
 * @create 2018-03-01 09:57
 **/
@Controller
@RequestMapping("${adminPath}/nswy/shopCommodity")
public class ShopCommodityController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ShopCommodityController.class);

    @Autowired
    private ShopCommodityService shopCommodityService;

    /**
     * 查询已审核商品列表
     * @param
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findShopCommodityList")
    public String findShopCommodityList (@RequestParam Map<String,Object> params, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<Map<String, Object>> page = shopCommodityService.findInfoList(params);
        model.addAttribute("page",page);
        return "modules/content/shopCommodityList";
    }

    /**
     * 查询商品列表
     * @param
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findShopCommodityInfoList")
    public String findShopCommodityInfoList (ShopCommityInfo shopCommityInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        return "modules/nswy/shopCommodityInfoList";
    }

    @ResponseBody
    @RequestMapping("/adopt")
    public String shopCommityInfoAdopt(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",request.getParameter("approveStatus"));
        map.put("shopId",request.getParameter("shopId"));
        shopCommodityService.adopt(map);
        return "success";
    }


    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateHome",method = RequestMethod.POST)
    public String shopCommityInfoUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("isHomeDisplay",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        shopCommodityService.updateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateNavDisplay",method = RequestMethod.POST)
    public String shopCommityInfoUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("isNavDisplay",request.getParameter("status"));
        map.put("shopId",request.getParameter("shopId"));
        shopCommodityService.updateNavDisplay(map);
        return "success";
    }

    /**
     * 设置该条内容是否在商城列表显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateShopDisplay",method = RequestMethod.POST)
    public String shopCommityInfoUpdateShopDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("isShopDisplay",request.getParameter("status"));
        map.put("shopId",request.getParameter("shopId"));
        shopCommodityService.updateShopDisplay(map);
        return "success";
    }


    /**
     * 排序
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateOrderNum",method = RequestMethod.POST)
    public String shopCommityInfoSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("shopId"));
        shopCommodityService.updateOrderNum(map);
        return "success";
    }

}
