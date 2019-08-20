package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ShopComment;
import com.ovit.manager.modules.nswy.entity.ShopComplaint;
import com.ovit.manager.modules.nswy.service.ShopCommentService;
import com.ovit.manager.modules.nswy.service.ShopComplaintService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("${adminPath}/nswy/shopComplaint")
public class ShopComplaintController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ShopComplaintController.class);

    @Autowired
    private ShopComplaintService shopComplaintService;

    /**
     * 查询商品评论列表
     * @param
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findComplaintList")
    public String findComplaintList (ShopComplaint shopComplaint, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ShopComplaint> page = shopComplaintService.findInfoList(shopComplaint,request,response);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(shopComplaint.getApproveStatus())) {
            model.addAttribute("approveStatus", shopComplaint.getApproveStatus());
        }
        if (StringUtils.isNotEmpty(shopComplaint.getSellerName())) {
            model.addAttribute("sellerName", shopComplaint.getSellerName());
        }
        if (StringUtils.isNotEmpty(shopComplaint.getBuyerName())) {
            model.addAttribute("buyerName", shopComplaint.getBuyerName());
        }
        return "modules/nswy/shopComplaintList";
    }


    @ResponseBody
    @RequestMapping("/adopt")
    public String shopComplaintAdopt(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",request.getParameter("approveStatus"));
        map.put("complaintId",request.getParameter("complaintId"));
        shopComplaintService.adopt(map);
        return "success";
    }



}
