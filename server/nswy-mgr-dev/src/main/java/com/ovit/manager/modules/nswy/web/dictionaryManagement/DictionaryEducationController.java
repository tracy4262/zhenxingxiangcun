package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEducation;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryEducationService;
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
@RequestMapping("${adminPath}/nswy/dictionaryEducation")
public class DictionaryEducationController extends BaseController {

    @Autowired
    private DictionaryEducationService dictionaryEducationService;

    @RequestMapping("/control")
    public String findList(DictionaryEducation dictionaryEducation, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryEducation> page = dictionaryEducationService.findList(dictionaryEducation,request,response);
        List<DictionaryEducation> page = dictionaryEducationService.findList(dictionaryEducation);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryEducationList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryEducationAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryEducationAdd";
    }


    @RequestMapping(value = "/save")
    public String saveEducation( HttpServletRequest request,DictionaryEducation dictionaryEducation){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryEducation.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryEducation.setName(name);
            dictionaryEducation.setValue(value);
            dictionaryEducation.setRemark(remark);
            dictionaryEducation.setPid(pid);
            dictionaryEducation.setLevel(level);

            dictionaryEducationService.saveEducation(dictionaryEducation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEducation/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryEducation dictionaryEducation = new DictionaryEducation();
        dictionaryEducation.setId(id);
        List<DictionaryEducation> dictionaryEducations = dictionaryEducationService.findEducationList(dictionaryEducation);
        model.addAttribute("dictionaryEducation",dictionaryEducations.get(0));

        return "modules/content/dictionaryManagement/dictionaryEducationFrom";
    }

    @RequestMapping(value = "/updateEducation")
    public String updateEducation( HttpServletRequest request,DictionaryEducation dictionaryEducation){

        try {
            dictionaryEducationService.updateEducation(dictionaryEducation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEducation/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteEducation(@RequestParam String id){

        dictionaryEducationService.deleteEducation(id);


        return "redirect:" + adminPath +"/nswy/dictionaryEducation/control/";
    }
}
