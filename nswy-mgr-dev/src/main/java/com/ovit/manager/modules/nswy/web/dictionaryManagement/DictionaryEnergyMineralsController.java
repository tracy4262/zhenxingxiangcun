package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnergyMinerals;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryEnergyMineralsService;
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
@RequestMapping("${adminPath}/nswy/dictionaryEnergyMinerals")
public class DictionaryEnergyMineralsController extends BaseController {

    @Autowired
    private DictionaryEnergyMineralsService dictionaryEnergyMineralsService;

    @RequestMapping("/control")
    public String findList(DictionaryEnergyMinerals dictionaryEnergyMinerals, Model model, HttpServletRequest request, HttpServletResponse response){
       Page<DictionaryEnergyMinerals> page = dictionaryEnergyMineralsService.findList(dictionaryEnergyMinerals,request,response);
       model.addAttribute("page",page);
       return "modules/content/dictionaryManagement/dictionaryEnergyMineralsList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryEnergyMineralsAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryEnergyMineralsAdd";
    }


    @RequestMapping(value = "/save")
    public String saveEnergyMinerals( HttpServletRequest request,DictionaryEnergyMinerals dictionaryEnergyMinerals){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryEnergyMinerals.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryEnergyMinerals.setName(name);
            dictionaryEnergyMinerals.setValue(value);
            dictionaryEnergyMinerals.setRemark(remark);
            dictionaryEnergyMinerals.setPid(pid);
            dictionaryEnergyMinerals.setLevel(level);

            dictionaryEnergyMineralsService.saveEnergyMinerals(dictionaryEnergyMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEnergyMinerals/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryEnergyMinerals dictionaryEnergyMinerals = new DictionaryEnergyMinerals();
        dictionaryEnergyMinerals.setId(id);
        List<DictionaryEnergyMinerals> dictionaryEnergyMineralss = dictionaryEnergyMineralsService.findEnergyMineralsList(dictionaryEnergyMinerals);
        model.addAttribute("dictionaryEnergyMinerals",dictionaryEnergyMineralss.get(0));

        return "modules/content/dictionaryManagement/dictionaryEnergyMineralsFrom";
    }

    @RequestMapping(value = "/updateEnergyMinerals")
    public String updateEnergyMinerals( HttpServletRequest request,DictionaryEnergyMinerals dictionaryEnergyMinerals){

        try {
            dictionaryEnergyMineralsService.updateEnergyMinerals(dictionaryEnergyMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryEnergyMinerals/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteEnergyMinerals(@RequestParam String id){

        dictionaryEnergyMineralsService.deleteEnergyMinerals(id);


        return "redirect:" + adminPath +"/nswy/dictionaryEnergyMinerals/control/";
    }
}
