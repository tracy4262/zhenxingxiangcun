package com.ovit.manager.modules.nswy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import com.ovit.manager.modules.nswy.service.NswyGovInfoService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-8-16.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyGovInfoController extends BaseController {

    @Autowired
    private NswyGovInfoService nswyGovInfoService;

    @ModelAttribute
    public NswyGovInfo get(@RequestParam(required=false) Integer govInfoId) {
        if (govInfoId !=null){
            return nswyGovInfoService.getGovInfoById(govInfoId);
        }else{
            return new NswyGovInfo();
        }
    }

    @RequestMapping(value = "govInfoForm")
    public String govInfoForm(NswyGovInfo nswyGovInfo, Model model) {
        model.addAttribute("nswyGovInfo",nswyGovInfo);
        return "modules/nswy/govInfoForm";
    }

    @RequestMapping(value = "govInfoAuditForm")
    public String govInfoAuditForm(NswyGovInfo nswyGovInfo, Model model) {
        model.addAttribute("nswyGovInfo",nswyGovInfo);
        return "modules/nswy/govInfoAudit";
    }

    @RequestMapping("/getGovInfolist")
    public String getGovInfolist(NswyGovInfo nswyGovInfo,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyGovInfo> page =nswyGovInfoService.findGovInfolist(nswyGovInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/govInfoList";
    }
    @RequestMapping("/getCountrytList")
    public String getCountrytList(NswyGovInfo nswyGovInfo,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyGovInfo> page =nswyGovInfoService.findCountrylist(nswyGovInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/countrytList";
    }


    /*资质文件审核*/
    @RequestMapping(value = "govFileForm")
    public String govFileForm(NswyGovInfo nswyGovInfo, Model model) {
        model.addAttribute("nswyGovInfo",nswyGovInfo);
        return "modules/nswy/govFileExam";
    }

    /*资质文件审核时返回*/
    @RequestMapping("/getGovFilelist")
    public String getGovFilelist(NswyGovInfo nswyGovInfo,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyGovInfo> page =nswyGovInfoService.findGovFilelist(nswyGovInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/govFileList";
    }
    
    @RequestMapping("/deleteGovInfo")
    public String deleteGovInfo(Integer govInfoId){
        nswyGovInfoService.deleteGovInfo(govInfoId);
        return  "redirect:" + adminPath +"/nswy/getGovInfolist/";
    }

    @RequestMapping("/updateGovInfo")
    public String updateGovInfo(NswyGovInfo nswyGovInfo){
        nswyGovInfoService.updateGovInfo(nswyGovInfo);
        return "redirect:" + adminPath +"/nswy/getGovInfolist/";
    }



    
    /*资质文件审核时更新*/
    @RequestMapping("/updateGovFile")
    public String updateGovFile(NswyGovInfo nswyGovInfo){
    	nswyGovInfoService.updateGovFile(nswyGovInfo);
        return "redirect:" + adminPath +"/nswy/getGovFilelist/";
    }
}
