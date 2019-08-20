package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;

import com.ovit.manager.modules.nswy.entity.ReportManageTypeInfo;
import com.ovit.manager.modules.nswy.service.ReportManageTypeService;
import com.ovit.manager.modules.nswy.service.UserTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * 报表类型管理
 *
 * @author haoWen
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class ReportManageTypeController extends BaseController{
    @Autowired
    private ReportManageTypeService reportManageTypeService;

    @Autowired
    private UserTypeService userTypeService;





    @RequestMapping(value = "/reportManageType/control")
    public String pictureList(ReportManageTypeInfo reportManageTypeInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ReportManageTypeInfo> page = reportManageTypeService.findKnowledgeList(reportManageTypeInfo, request, response);
        model.addAttribute("page",page);
        return "modules/content/reportManageTypeList";
    }
    @RequestMapping(value = "/reportManageType/add")
    public String pictureAdd(Model model){

        return "modules/content/reportManageTypeAdd";
    }
    @RequestMapping(value = "/reportManageType/delete")
    public String reportDelete(@RequestParam Integer reportId){
        reportManageTypeService.delete(reportId);
        return "redirect:" + adminPath +"/nswy/reportManageType/control/";

    }

    @RequestMapping(value = "/reportManageType/update")
    public String update(@RequestParam Integer reportId,Model model){
        ReportManageTypeInfo reportManageTypeInfo = reportManageTypeService.findReportType(reportId);
        model.addAttribute("reportManageTypeInfo",reportManageTypeInfo);
        return "modules/content/reportManageTypeFrom";
    }

    @RequestMapping(value = "/reportManageType/updateType")
    public String updateType( HttpServletRequest request,ReportManageTypeInfo reportManageTypeInfo){
        try {
            reportManageTypeService.updateType(reportManageTypeInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/reportManageType/control/";
    }

    @RequestMapping(value = "/reportManageType/save")
    public String savePicture( HttpServletRequest request){
        try {

            //描述
            String reportTypeName = request.getParameter("reportTypeName");
            Map<String,Object> map = new HashMap<>();
            map.put("reportTypeName",reportTypeName);
            reportManageTypeService.savePicture(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/reportManageType/control/";
    }







}
