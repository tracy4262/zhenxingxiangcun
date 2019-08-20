package com.ovit.manager.modules.nswy.web;

import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.persistence.PageList;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ProxyGovInfo;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping("${adminPath}/nswy/shopCurrencyCommodity")
public class ShopCurrencyCommodityController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ShopCurrencyCommodityController.class);
    @Autowired
    private ShopCurrencyCommodityService shopCurrencyCommodityService;

    @ModelAttribute
    public ShopCurrencyCommityInfo get(@RequestParam(required=false) Integer shopId) {
        if (shopId !=null){
            Map<String,Object> params = new HashMap<>();
            params.put("shopId",shopId);
            return shopCurrencyCommodityService.findList(params);
        }else{
            return new ShopCurrencyCommityInfo();
        }
    }

    /**
     * 查询通用商品名列表
     * @param
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findShopCurrencyCommodityInfoList")
    public String findShopCurrencyCommodityInfoList (ShopCurrencyCommityInfo shopCurrencyCommityInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ShopCurrencyCommityInfo> page = shopCurrencyCommodityService.findInfoList(shopCurrencyCommityInfo,request,response);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(shopCurrencyCommityInfo.getCommonProductName())) {
            model.addAttribute("commonProductName", shopCurrencyCommityInfo.getCommonProductName());
        }
        if (StringUtils.isNotEmpty(shopCurrencyCommityInfo.getApproveStatus())) {
            model.addAttribute("approveStatus", shopCurrencyCommityInfo.getApproveStatus());
        }
        return "modules/nswy/shopCurrencyCommodityInfoList";
    }


    @RequestMapping("/shopCurrencyCommodityInfoFrom")
    public String shopCurrencyCommodityInfoFrom(ShopCurrencyCommityInfo shopCurrencyCommityInfo, Model model) {
        model.addAttribute("shopCurrencyCommityInfo",shopCurrencyCommityInfo);
        return "modules/nswy/shopCurrencyCommodityInfoForm";
    }

    @RequestMapping("/currencyAdopt")
    public String currencyAdopt(ShopCurrencyCommityInfo shopCurrencyCommityInfo,RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",shopCurrencyCommityInfo.getApproveStatus());
        map.put("shopId",shopCurrencyCommityInfo.getShopId());
        if(StringUtil.isNotEmpty(shopCurrencyCommityInfo.getRefuseReason())){
            map.put("refuse_reason",shopCurrencyCommityInfo.getRefuseReason());
        }
        shopCurrencyCommodityService.adopt(map);
        addMessage(redirectAttributes, "审核成功");
        return "redirect:" + adminPath +"/nswy/shopCurrencyCommodity/findShopCurrencyCommodityInfoList";
    }
}
