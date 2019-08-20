package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.entity.KnowledgeInfo;
import com.ovit.manager.modules.nswy.service.NswyKnowledgeExamineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 知识审核
 *
 * @author haoWen
 * @create 2018-03-01 11:17
 **/
@Controller
@RequestMapping("${adminPath}/nswy/knowledge")
public class NswyKnowledgeExamineController extends BaseController {
    @Autowired
    private NswyKnowledgeExamineService nswyKnowledgeExamineService;
    @RequestMapping("/examine")
    public String informationExamine (KnowledgeInfo knowledgeInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<KnowledgeInfo> page = nswyKnowledgeExamineService.findInfoList(knowledgeInfo,request,response);
        model.addAttribute("page",page);

        return "modules/nswy/knowledgeList";
    }

    @RequestMapping("/control")
    public String knowledgeControl (KnowledgeInfo knowledgeInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        knowledgeInfo.setApproveStatus("2");
        Page<KnowledgeInfo> page = nswyKnowledgeExamineService.findInfoList(knowledgeInfo,request,response);
        model.addAttribute("page",page);

        return "modules/content/knowledgeList";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String knowledgeDelete(@RequestParam Integer knowledgeInfoId){
        nswyKnowledgeExamineService.knowledgeDelete(knowledgeInfoId);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/adopt",method = RequestMethod.POST)
    public String knowledgeAdopt(@RequestParam Integer knowledgeInfoId){
        nswyKnowledgeExamineService.knowledgeAdopt(knowledgeInfoId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public String knowledgeRefuse(@RequestParam Integer knowledgeInfoId){
        nswyKnowledgeExamineService.knowledgeRefuse(knowledgeInfoId);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-home",method = RequestMethod.POST)
    public String knowledgeUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        logger.info("设置是否在无忧首页显示：{}",map);
        nswyKnowledgeExamineService.knowledgeUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-nav-display",method = RequestMethod.POST)
    public String knowledgeUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        logger.info("设置是否在无忧导航显示：{}",map);
        nswyKnowledgeExamineService.knowledgeUpdateNavDisplay(map);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/set-order-num",method = RequestMethod.POST)
    public String knowledgeSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        nswyKnowledgeExamineService.knowledgeSetOrderNum(map);
        return "success";
    }

}
