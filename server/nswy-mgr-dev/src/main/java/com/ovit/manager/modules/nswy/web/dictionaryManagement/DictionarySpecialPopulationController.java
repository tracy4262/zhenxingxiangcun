package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionarySpecialPopulation;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionarySpecialPopulationService;
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
@RequestMapping("${adminPath}/nswy/dictionarySpecialPopulation")
public class DictionarySpecialPopulationController extends BaseController {

    @Autowired
    private DictionarySpecialPopulationService dictionarySpecialPopulationService;

    @RequestMapping("/control")
    public String findList(DictionarySpecialPopulation dictionarySpecialPopulation, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionarySpecialPopulation> page = dictionarySpecialPopulationService.findList(dictionarySpecialPopulation,request,response);
        List<DictionarySpecialPopulation> page = dictionarySpecialPopulationService.findList(dictionarySpecialPopulation);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionarySpecialPopulationList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionarySpecialPopulationAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionarySpecialPopulationAdd";
    }


    @RequestMapping(value = "/save")
    public String saveSpecialPopulation( HttpServletRequest request,DictionarySpecialPopulation dictionarySpecialPopulation){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionarySpecialPopulation.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionarySpecialPopulation.setName(name);
            dictionarySpecialPopulation.setValue(value);
            dictionarySpecialPopulation.setRemark(remark);
            dictionarySpecialPopulation.setPid(pid);
            dictionarySpecialPopulation.setLevel(level);

            dictionarySpecialPopulationService.saveSpecialPopulation(dictionarySpecialPopulation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionarySpecialPopulation/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionarySpecialPopulation dictionarySpecialPopulation = new DictionarySpecialPopulation();
        dictionarySpecialPopulation.setId(id);
        List<DictionarySpecialPopulation> dictionarySpecialPopulations = dictionarySpecialPopulationService.findSpecialPopulationList(dictionarySpecialPopulation);
        model.addAttribute("dictionarySpecialPopulation",dictionarySpecialPopulations.get(0));

        return "modules/content/dictionaryManagement/dictionarySpecialPopulationFrom";
    }

    @RequestMapping(value = "/updateSpecialPopulation")
    public String updateSpecialPopulation( HttpServletRequest request,DictionarySpecialPopulation dictionarySpecialPopulation){

        try {
            dictionarySpecialPopulationService.updateSpecialPopulation(dictionarySpecialPopulation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionarySpecialPopulation/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteSpecialPopulation(@RequestParam String id){

        dictionarySpecialPopulationService.deleteSpecialPopulation(id);


        return "redirect:" + adminPath +"/nswy/dictionarySpecialPopulation/control/";
    }
}
