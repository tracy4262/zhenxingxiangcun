package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.modules.nswy.entity.NswyExpert;
import com.ovit.manager.modules.nswy.service.NswyExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-10-18.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyExpertApplyController extends BaseController {


    @Autowired
    private NswyExpertService nswyExpertService;

    @ModelAttribute
    public NswyExpert get(@RequestParam(required=false) Integer applyId) {
        if (applyId !=null){
            return nswyExpertService.getExpertById(applyId);
        }else{
            return new NswyExpert();
        }
    }

    @RequestMapping(value = "expertApprove")
    public String expertApprove(NswyExpert nswyExpert, Model model) {
        model.addAttribute("nswyExpert", nswyExpert);
        return "modules/nswy/expertApprove";
    }

    @RequestMapping("/getExpertApplylist")
    public String getExpertApplylist(NswyExpert nswyExpert,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyExpert> page =nswyExpertService.findExpertList(nswyExpert, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/expertApplyList";
    }

    @RequestMapping("/deleteExpertApply")
    public String deleteExpertApply(Integer applyId){
        nswyExpertService.deleteExpert(applyId);
        return  "redirect:" + adminPath +"/nswy/getExpertApplylist/";
    }

    @RequestMapping("/updateApproveStatus")
    public String updateApproveStatus(NswyExpert nswyExpert){
        nswyExpertService.updateApproveStatus(nswyExpert.getExpertId(),nswyExpert.getApproveStatus());
        return "redirect:" + adminPath +"/nswy/getExpertApplylist/";
    }


}
