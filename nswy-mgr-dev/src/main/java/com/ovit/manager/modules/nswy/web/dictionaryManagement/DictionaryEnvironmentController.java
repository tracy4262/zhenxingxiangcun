package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnvironment;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryEnvironmentService;
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
@RequestMapping("${adminPath}/nswy/dictionaryEnvironment")
public class DictionaryEnvironmentController extends BaseController {

    @Autowired
    private DictionaryEnvironmentService dictionaryEnvironmentService;

    @RequestMapping("/control")
    public String findList(DictionaryEnvironment dictionaryEnvironment, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryEnvironment> page = dictionaryEnvironmentService.findList(dictionaryEnvironment,request,response);
        List<DictionaryEnvironment> page = dictionaryEnvironmentService.findList(dictionaryEnvironment);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryEnvironmentList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryEnvironmentAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryEnvironmentAdd";
    }


    @RequestMapping(value = "/save")
    public String saveEnvironment( HttpServletRequest request,DictionaryEnvironment dictionaryEnvironment){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryEnvironment.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryEnvironment.setName(name);
            dictionaryEnvironment.setValue(value);
            dictionaryEnvironment.setRemark(remark);
            dictionaryEnvironment.setPid(pid);
            dictionaryEnvironment.setLevel(level);

            dictionaryEnvironmentService.saveEnvironment(dictionaryEnvironment);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEnvironment/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryEnvironment dictionaryEnvironment = new DictionaryEnvironment();
        dictionaryEnvironment.setId(id);
        List<DictionaryEnvironment> dictionaryEnvironments = dictionaryEnvironmentService.findEnvironmentList(dictionaryEnvironment);
        model.addAttribute("dictionaryEnvironment",dictionaryEnvironments.get(0));

        return "modules/content/dictionaryManagement/dictionaryEnvironmentFrom";
    }

    @RequestMapping(value = "/updateEnvironment")
    public String updateEnvironment( HttpServletRequest request,DictionaryEnvironment dictionaryEnvironment){

        try {
            dictionaryEnvironmentService.updateEnvironment(dictionaryEnvironment);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEnvironment/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteEnvironment(@RequestParam String id){

        dictionaryEnvironmentService.deleteEnvironment(id);


        return "redirect:" + adminPath +"/nswy/dictionaryEnvironment/control/";
    }
}
