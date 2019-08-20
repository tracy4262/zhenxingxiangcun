package com.ovit.manager.modules.nswy.web;

import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyServiceInfo;
import com.ovit.manager.modules.nswy.service.ShopCurrencyServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("${adminPath}/nswy/shopCurrencyService")
public class ShopCurrencyServiceController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ShopCurrencyServiceController.class);
    @Autowired
    private ShopCurrencyServiceService shopCurrencyServiceService;

    @ModelAttribute
    public ShopCurrencyServiceInfo get(@RequestParam(required=false) Integer shopId) {
        if (shopId !=null){
            Map<String,Object> params = new HashMap<>();
            params.put("shopId",shopId);
            return shopCurrencyServiceService.findList(params);
        }else{
            return new ShopCurrencyServiceInfo();
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
    @RequestMapping("/findShopCurrencyServiceInfoList")
    public String findShopCurrencyServiceInfoList (ShopCurrencyServiceInfo shopCurrencyServiceInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ShopCurrencyServiceInfo> page = shopCurrencyServiceService.findInfoList(shopCurrencyServiceInfo,request,response);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(shopCurrencyServiceInfo.getCommonProductName())) {
            model.addAttribute("commonProductName", shopCurrencyServiceInfo.getCommonProductName());
        }
        if (StringUtils.isNotEmpty(shopCurrencyServiceInfo.getApproveStatus())) {
            model.addAttribute("approveStatus", shopCurrencyServiceInfo.getApproveStatus());
        }
        return "modules/nswy/shopCurrencyServiceInfoList";
    }


    @RequestMapping("/shopCurrencyServiceInfoFrom")
    public String shopCurrencyServiceInfoFrom(ShopCurrencyServiceInfo shopCurrencyServiceInfo, Model model) {
        model.addAttribute("shopCurrencyServiceInfo",shopCurrencyServiceInfo);
        return "modules/nswy/shopCurrencyServiceInfoForm";
    }

    @RequestMapping("/currencyAdopt")
    public String currencyAdopt(ShopCurrencyServiceInfo shopCurrencyServiceInfo,RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",shopCurrencyServiceInfo.getApproveStatus());
        map.put("shopId",shopCurrencyServiceInfo.getShopId());
        if(StringUtil.isNotEmpty(shopCurrencyServiceInfo.getRefuseReason())){
            map.put("refuse_reason",shopCurrencyServiceInfo.getRefuseReason());
        }
        shopCurrencyServiceService.adopt(map);
        return "redirect:" + adminPath +"/nswy/shopCurrencyService/findShopCurrencyServiceInfoList";
    }
}
