package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ShopComment;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import com.ovit.manager.modules.nswy.service.ShopCommentService;
import com.ovit.manager.modules.nswy.service.ShopCommodityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("${adminPath}/nswy/shopComment")
public class ShopCommentController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ShopCommentController.class);

    @Autowired
    private ShopCommentService shopCommentService;

    /**
     * 查询商品评论列表
     * @param
     * @param modelfindCommentList
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findCommentList")
    public String findCommentList (ShopComment shopComment, Model model, HttpServletRequest request, HttpServletResponse response){
        shopComment.setSource("1");
        Page<ShopComment> page = shopCommentService.findInfoList(shopComment,request,response);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(shopComment.getCommodityName())) {
            model.addAttribute("commodityName", shopComment.getCommodityName());
        }

        if (StringUtils.isNotEmpty(shopComment.getApproveStatus())) {
            model.addAttribute("approveStatus", shopComment.getApproveStatus());
        }
        return "modules/nswy/shopCommentList";
    }


    @ResponseBody
    @RequestMapping("/adopt")
    public String shopCommentAdopt(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",request.getParameter("approveStatus"));
        map.put("commentId",request.getParameter("commentId"));
        shopCommentService.adopt(map);
        return "success";
    }



}
