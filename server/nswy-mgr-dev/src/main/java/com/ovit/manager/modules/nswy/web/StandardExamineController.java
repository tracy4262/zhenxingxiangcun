package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.StandardInfo;
import com.ovit.manager.modules.nswy.service.StandardService;
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
 * 标准审核
 *
 * @author haoWen
 * @create 2018-03-14 15:08
 **/
@Controller
@RequestMapping("${adminPath}/nswy/standard")
public class StandardExamineController  extends BaseController {

    @Autowired
    private StandardService standardService;

    @RequestMapping(value = "/list")
    public String getStandardList(StandardInfo standardInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<StandardInfo> page = standardService.findProxyAgencyList(standardInfo,request,response);
        model.addAttribute("page",page);
        return "modules/nswy/standardList";
    }

    @RequestMapping(value = "/control")
    public String getStandardControl(StandardInfo standardInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        standardInfo.setApproveStatus("2");
        Page<StandardInfo> page = standardService.findProxyAgencyList(standardInfo,request,response);
        model.addAttribute("page",page);
        return "modules/content/standardList";
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public String deleteStandard(@RequestParam Integer standardId){
        standardService.deleteStandard(standardId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/adopt")
    public String standardAdopt(@RequestParam Integer standardId){
        standardService.standardAdopt(standardId);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public String standardRefuse(@RequestParam Integer standardId){
        standardService.standardRefuse(standardId);
        return "success";
    }


    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-home",method = RequestMethod.POST)
    public String standardUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        standardService.standardUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-nav-display",method = RequestMethod.POST)
    public String standardUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        standardService.standardUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/set-order-num",method = RequestMethod.POST)
    public String standardSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        standardService.standardSetOrderNum(map);
        return "success";
    }
}
