package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPolicy;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryPolicy")
public class DictionaryPolicyController extends BaseController {

    @Autowired
    private DictionaryPolicyService dictionaryPolicyService;

    @RequestMapping("/control")
    public String findList(DictionaryPolicy dictionaryPolicy, Model model, HttpServletRequest request, HttpServletResponse response){
       Page<DictionaryPolicy> page = dictionaryPolicyService.findList(dictionaryPolicy,request,response);
       model.addAttribute("page",page);
       return "modules/content/dictionaryManagement/dictionaryPolicyList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryPolicyAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryPolicyAdd";
    }


    @RequestMapping(value = "/save")
    public String savePolicy( HttpServletRequest request,DictionaryPolicy dictionaryPolicy){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String type = "policy";

            dictionaryPolicy.setName(name);
            dictionaryPolicy.setValue(value);
            dictionaryPolicy.setRemark(remark);
            dictionaryPolicy.setPid(pid);
            dictionaryPolicy.setLevel(level);
            dictionaryPolicy.setType(type);

            dictionaryPolicyService.savePolicy(dictionaryPolicy);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPolicy/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryPolicy dictionaryPolicy = new DictionaryPolicy();
        dictionaryPolicy.setId(id);
        List<DictionaryPolicy> dictionaryPolicys = dictionaryPolicyService.findPolicyList(dictionaryPolicy);
        model.addAttribute("dictionaryPolicy",dictionaryPolicys.get(0));

        return "modules/content/dictionaryManagement/dictionaryPolicyFrom";
    }

    @RequestMapping(value = "/updatePolicy")
    public String updatePolicy( HttpServletRequest request,DictionaryPolicy dictionaryPolicy){

        try {
            dictionaryPolicyService.updatePolicy(dictionaryPolicy);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPolicy/control/";
    }

    @RequestMapping(value = "/delete")
    public String deletePolicy(@RequestParam String id){

        dictionaryPolicyService.deletePolicy(id);


        return "redirect:" + adminPath +"/nswy/dictionaryPolicy/control/";
    }
}
