package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTechnical;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryTechnicalService;
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
@RequestMapping("${adminPath}/nswy/dictionaryTechnical")
public class DictionaryTechnicalController extends BaseController {

    @Autowired
    private DictionaryTechnicalService dictionaryTechnicalService;

    @RequestMapping("/control")
    public String findList(DictionaryTechnical dictionaryTechnical, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryTechnical> page = dictionaryTechnicalService.findList(dictionaryTechnical,request,response);
        List<DictionaryTechnical> page = dictionaryTechnicalService.findList(dictionaryTechnical);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryTechnicalList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryTechnicalAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryTechnicalAdd";
    }


    @RequestMapping(value = "/save")
    public String saveTechnical( HttpServletRequest request,DictionaryTechnical dictionaryTechnical){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryTechnical.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryTechnical.setName(name);
            dictionaryTechnical.setValue(value);
            dictionaryTechnical.setRemark(remark);
            dictionaryTechnical.setPid(pid);
            dictionaryTechnical.setLevel(level);

            dictionaryTechnicalService.saveTechnical(dictionaryTechnical);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTechnical/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryTechnical dictionaryTechnical = new DictionaryTechnical();
        dictionaryTechnical.setId(id);
        List<DictionaryTechnical> dictionaryTechnicals = dictionaryTechnicalService.findTechnicalList(dictionaryTechnical);
        model.addAttribute("dictionaryTechnical",dictionaryTechnicals.get(0));

        return "modules/content/dictionaryManagement/dictionaryTechnicalFrom";
    }

    @RequestMapping(value = "/updateTechnical")
    public String updateTechnical( HttpServletRequest request,DictionaryTechnical dictionaryTechnical){

        try {
            dictionaryTechnicalService.updateTechnical(dictionaryTechnical);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTechnical/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteTechnical(@RequestParam String id){

        dictionaryTechnicalService.deleteTechnical(id);


        return "redirect:" + adminPath +"/nswy/dictionaryTechnical/control/";
    }
}
