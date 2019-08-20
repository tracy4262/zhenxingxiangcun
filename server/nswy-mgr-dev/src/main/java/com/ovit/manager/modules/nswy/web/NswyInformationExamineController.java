package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.service.NswyInformationExamineService;
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
 * 资讯审核
 *
 * @author haoWen
 * @create 2018-03-01 09:57
 **/
@Controller
@RequestMapping("${adminPath}/nswy/information")
public class NswyInformationExamineController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(NswyInformationExamineController.class);

    @Autowired
    private NswyInformationExamineService nswyInformationExamineService;
    @RequestMapping("/examine")
    public String informationExamine (InformationInfo informationInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<InformationInfo> page = nswyInformationExamineService.findInfoList(informationInfo,request,response);
        model.addAttribute("page",page);

        return "modules/nswy/informationList";
    }

    @RequestMapping("/control")
    public String informationControl(InformationInfo informationInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        informationInfo.setApproveStatus("2");
        Page<InformationInfo> page = nswyInformationExamineService.findInfoList(informationInfo,request,response);
        model.addAttribute("page",page);

        return "modules/content/informationList";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String informationDelete(@RequestParam Integer informationInfoId){
        nswyInformationExamineService.deleteInformation(informationInfoId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/adopt")
    public String informationAdopt(@RequestParam Integer informationInfoId){
        nswyInformationExamineService.informationAdopt(informationInfoId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public String informationRefuse(@RequestParam Integer informationInfoId){
        nswyInformationExamineService.informationRefuse(informationInfoId);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-home",method = RequestMethod.POST)
    public String informationUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        nswyInformationExamineService.informationUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-nav-display",method = RequestMethod.POST)
    public String informationUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        nswyInformationExamineService.informationUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/set-order-num",method = RequestMethod.POST)
    public String informationSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        nswyInformationExamineService.informationSetOrderNum(map);
        return "success";
    }
}
