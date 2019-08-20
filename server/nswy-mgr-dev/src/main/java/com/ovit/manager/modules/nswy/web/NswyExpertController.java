package com.ovit.manager.modules.nswy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyExpert;
import com.ovit.manager.modules.nswy.service.NswyExpertService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-8-16.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyExpertController extends BaseController {

    @Autowired
    private NswyExpertService nswyExpertService;

    @ModelAttribute
    public NswyExpert get(@RequestParam(required=false) Integer expertId) {
        if (expertId !=null){
            return nswyExpertService.getExpertById(expertId);
        }else{
            return new NswyExpert();
        }
    }

    @RequestMapping(value = "expertForm")
    public String expertForm(NswyExpert nswyExpert, Model model) {
        model.addAttribute("nswyExpert",nswyExpert);
        return "modules/nswy/expertForm";
    }

    @RequestMapping("/getExpertlist")
    public String getExpertlist(NswyExpert nswyExpert,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyExpert> page =nswyExpertService.findExpertList(nswyExpert, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/expertList";
    }

    @RequestMapping("/deleteExpert")
    public String deleteExpert(Integer expertId){
        nswyExpertService.deleteExpert(expertId);
        return  "redirect:" + adminPath +"/nswy/getExpertlist/";
    }

    @RequestMapping("/updateExpert")
    public String updateExpert(NswyExpert nswyExpert){
        nswyExpertService.updateExpert(nswyExpert);
        return "redirect:" + adminPath +"/nswy/getExpertlist/";
    }

}
