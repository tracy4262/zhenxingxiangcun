package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryQualification;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryQualificationService;
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
@RequestMapping("${adminPath}/nswy/dictionaryQualification")
public class DictionaryQualificationController extends BaseController {

    @Autowired
    private DictionaryQualificationService dictionaryQualificationService;

    @RequestMapping("/control")
    public String findList(DictionaryQualification dictionaryQualification, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryQualification> page = dictionaryQualificationService.findList(dictionaryQualification,request,response);
        List<DictionaryQualification> page = dictionaryQualificationService.findList(dictionaryQualification);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryQualificationList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryQualificationAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryQualificationAdd";
    }


    @RequestMapping(value = "/save")
    public String saveQualification( HttpServletRequest request,DictionaryQualification dictionaryQualification){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryQualification.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryQualification.setName(name);
            dictionaryQualification.setValue(value);
            dictionaryQualification.setRemark(remark);
            dictionaryQualification.setPid(pid);
            dictionaryQualification.setLevel(level);

            dictionaryQualificationService.saveQualification(dictionaryQualification);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryQualification/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryQualification dictionaryQualification = new DictionaryQualification();
        dictionaryQualification.setId(id);
        List<DictionaryQualification> dictionaryQualifications = dictionaryQualificationService.findQualificationList(dictionaryQualification);
        model.addAttribute("dictionaryQualification",dictionaryQualifications.get(0));

        return "modules/content/dictionaryManagement/dictionaryQualificationFrom";
    }

    @RequestMapping(value = "/updateQualification")
    public String updateQualification( HttpServletRequest request,DictionaryQualification dictionaryQualification){

        try {
            dictionaryQualificationService.updateQualification(dictionaryQualification);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryQualification/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteQualification(@RequestParam String id){

        dictionaryQualificationService.deleteQualification(id);


        return "redirect:" + adminPath +"/nswy/dictionaryQualification/control/";
    }
}
