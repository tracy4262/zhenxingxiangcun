package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.entity.NswyServiceInfo;
import com.ovit.manager.modules.nswy.service.NswyInformationExamineService;
import com.ovit.manager.modules.nswy.service.NswyServiceExamineService;
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
 * 服务
 *
 * @author haoWen
 * @create 2018-03-01 09:57
 **/
@Controller
@RequestMapping("${adminPath}/nswy/nswyService")
public class NswyServiceExamineController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(NswyServiceExamineController.class);

    @Autowired
    private NswyServiceExamineService nswyServiceExamineService;


    @RequestMapping("/control")
    public String informationControl(NswyServiceInfo nswyServiceInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<NswyServiceInfo> page = nswyServiceExamineService.findInfoList(nswyServiceInfo,request,response);
        model.addAttribute("page",page);

        return "modules/content/serviceList";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String informationDelete(@RequestParam Integer id){
        nswyServiceExamineService.serviceDel(id);
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
        nswyServiceExamineService.serviceUpdateHome(map);
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
        nswyServiceExamineService.serviceUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/set-order-num",method = RequestMethod.POST)
    public String informationSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        nswyServiceExamineService.serviceSetOrderNum(map);
        return "success";
    }
}
