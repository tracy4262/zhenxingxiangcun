package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.cms.entity.Policy;
import com.ovit.manager.modules.nswy.dao.NswyPolicyExamineDao;
import com.ovit.manager.modules.nswy.entity.KnowledgeInfo;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import com.ovit.manager.modules.nswy.entity.PolicyInfo;
import com.ovit.manager.modules.nswy.service.NswyKnowledgeExamineService;
import com.ovit.manager.modules.nswy.service.NswyPolicyExamineService;
import org.apache.commons.lang3.StringUtils;
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
 * 知识审核
 *
 * @author haoWen
 * @create 2018-03-01 11:17
 **/
@Controller
@RequestMapping("${adminPath}/nswy/policy")
public class NswyPolicyExamineController extends BaseController {
    @Autowired
    private NswyPolicyExamineService nswyPolicyExamineService;
    @RequestMapping("/examine")
    public String policyExamine (PolicyInfo policyInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<PolicyInfo> page = nswyPolicyExamineService.findInfoList(policyInfo,request,response);
        model.addAttribute("page",page);

        return "modules/nswy/policyList";
    }

    @RequestMapping("/control")
    public String policyControl (PolicyInfo policyInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        policyInfo.setApproveStatus("2");
        Page<PolicyInfo> page = nswyPolicyExamineService.findInfoList(policyInfo,request,response);
        model.addAttribute("page",page);
        return "modules/content/policyList";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String policyDelete(@RequestParam Integer policyId){
        nswyPolicyExamineService.deletePolicy(policyId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/adopt")
    public String policyAdopt(@RequestParam Integer policyId){
        nswyPolicyExamineService.policyAdopt(policyId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public String policyRefuse(@RequestParam Integer policyId){
        nswyPolicyExamineService.policyRefuse(policyId);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update-home",method = RequestMethod.POST)
    public String knowledgeUpdateHome(HttpServletRequest request){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("status",request.getParameter("status"));
            map.put("id",request.getParameter("id"));
            logger.info("设置是否在无忧首页显示：{}",map);
            nswyPolicyExamineService.policyUpdateHome(map);
        } catch (Exception e) {
            logger.error("设置是否在无忧首页显示失败：{}",e);
        }
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update-nav-display",method = RequestMethod.POST)
    public String knowledgeUpdateNavDisplay(HttpServletRequest request){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("status",request.getParameter("status"));
            map.put("id",request.getParameter("id"));
            logger.info("设置是否在无忧导航显示：{}",map);
            nswyPolicyExamineService.policyUpdateNavDisplay(map);
        } catch (Exception e) {
            logger.error("设置是否在无忧导航显示失败：{}",e);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "set-order-num",method = RequestMethod.POST)
    public String policySetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        nswyPolicyExamineService.policySetOrderNum(map);
        return "success";
    }
}


