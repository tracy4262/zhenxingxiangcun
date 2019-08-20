package com.ovit.manager.modules.nswy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import com.ovit.manager.modules.nswy.service.NswyCorpInfoService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-8-15.
 */

@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyCorpInfoController extends BaseController {

    @Autowired
    private NswyCorpInfoService nswyCorpInfoService;

    @ModelAttribute
    public NswyCorpInfo get(@RequestParam(required=false) Integer corpInfoId) {
        if (corpInfoId !=null){
            return nswyCorpInfoService.getCorpInfo(corpInfoId);
        }else{
            return new NswyCorpInfo();
        }
    }

    @RequestMapping(value = "corpInfoForm")
    public String form(NswyCorpInfo nswyCorpInfo, Model model) {
        model.addAttribute("nswyCorpInfo",nswyCorpInfo);
        return "modules/nswy/corpInfoForm";
    }

    @RequestMapping(value = "corpInfoAuditForm")
    public String corpInfoAuditForm(NswyCorpInfo nswyCorpInfo, Model model) {
        model.addAttribute("nswyCorpInfo",nswyCorpInfo);
        return "modules/nswy/corpInfoAuditForm";
    }


    @RequestMapping("/getCorpInfolist")
    public String getNswyCorpInfolist(NswyCorpInfo nswyCorpInfo,Model model,HttpServletRequest request, HttpServletResponse response) {
        Page<NswyCorpInfo> page =nswyCorpInfoService.findNswyCorpInfolist(nswyCorpInfo,request,response);
        model.addAttribute("page",page);
        return "modules/nswy/corpInfoList";
    }

    /*用于资质文件审核返回*/
    @RequestMapping(value = "enterpriseForm")
    public String enterpriseForm(NswyCorpInfo nswyCorpInfo, Model model) {
        model.addAttribute("nswyCorpInfo",nswyCorpInfo);
        return "modules/nswy/enterpriseFileExam";
    }
    
    /*用于资质文件审核返回*/
    @RequestMapping("/getEnterpriseList")
    public String getEnterpriseList(NswyCorpInfo nswyCorpInfo,Model model,HttpServletRequest request, HttpServletResponse response) {
        Page<NswyCorpInfo> page =nswyCorpInfoService.findNswyEnterpriselist(nswyCorpInfo,request,response);
        model.addAttribute("page",page);
        return "modules/nswy/enterpriseFileList";
    }


    @RequestMapping("/deleteCorpInfo")
    public String deleteCorpInfo(Integer corpInfoId){
        nswyCorpInfoService.deleteCorpInfo(corpInfoId);
        return  "redirect:" + adminPath +"/nswy/getCorpInfolist/";
    }

    @RequestMapping("/updateCorpInfo")
    public String updateCorpInfo(NswyCorpInfo corpInfo){
        nswyCorpInfoService.updateCorpInfo(corpInfo);
        return "redirect:" + adminPath +"/nswy/getCorpInfolist/";
    }
    
    /*资质文件审核时更新*/
    @RequestMapping("/updateEnterprise")
    public String updateEnterprise(NswyCorpInfo corpInfo){
        nswyCorpInfoService.updateEnterprise(corpInfo);
        return "redirect:" + adminPath +"/nswy/getEnterpriseList/";
    }
}
