package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.AgencyAudit;
import com.ovit.manager.modules.nswy.service.AgencyAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class AgencyAuditController  extends BaseController {

    @Autowired
    private AgencyAuditService agencyAuditService;

    @ModelAttribute
    public AgencyAudit get(@RequestParam(required=false) Integer agencyAuditId) {
        if (agencyAuditId !=null){
            return agencyAuditService.getAgencyAuditById(agencyAuditId);
        }else{
            return new AgencyAudit();
        }
    }

    @RequestMapping(value = "agencyForm")
    public String agencyForm(AgencyAudit agencyAudit, Model model) {
        model.addAttribute("agencyAudit",agencyAudit);
        return "modules/nswy/agencyForm";
    }

    @RequestMapping(value = "agencyAuditForm")
    public String agencyAuditForm(AgencyAudit agencyAudit, Model model) {
        model.addAttribute("agencyAudit",agencyAudit);
        return "modules/nswy/agencyAuditForm";
    }


    @RequestMapping("/getAgencylist")
    public String getAgencylist(AgencyAudit agencyAudit,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<AgencyAudit> page =agencyAuditService.findAgencyList(agencyAudit, request, response);
        model.addAttribute("page",page);
        model.addAttribute("userType",agencyAudit.getUserType());
            return "modules/nswy/agencyList";
    }

    @RequestMapping("/updateAgencyAudit")
    public String updateAgencyAudit(AgencyAudit agencyAudit, RedirectAttributes redirectAttributes){
        Integer rows=agencyAuditService.updateAgencyAudit(agencyAudit);
        if(rows.equals(0)){
            addMessage(redirectAttributes, "审核失败");
            return "redirect:" + adminPath +"/nswy/agencyAuditForm?agencyAuditId="+agencyAudit.getAgencyAuditId();
        }else{
            addMessage(redirectAttributes, "审核成功");
            return "redirect:" + adminPath +"/nswy/getAgencylist?userType="+agencyAudit.getUserType();
        }

    }
    @RequestMapping("/deleteAgencyAudit")
    public String deleteAgencyAudit(Integer agencyAuditId,Integer userType){
        agencyAuditService.deleteAgencyAudit(agencyAuditId);
        return "redirect:" + adminPath +"/nswy/getAgencylist?userType="+userType;
    }

}
