package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMetalMinerals;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryMetalMineralsService;
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
@RequestMapping("${adminPath}/nswy/dictionaryMetalMinerals")
public class DictionaryMetalMineralsController extends BaseController {

    @Autowired
    private DictionaryMetalMineralsService dictionaryMetalMineralsService;

    @RequestMapping("/control")
    public String findList(DictionaryMetalMinerals dictionaryMetalMinerals, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryMetalMinerals> page = dictionaryMetalMineralsService.findList(dictionaryMetalMinerals,request,response);
        List<DictionaryMetalMinerals> page = dictionaryMetalMineralsService.findList(dictionaryMetalMinerals);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryMetalMineralsList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryMetalMineralsAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryMetalMineralsAdd";
    }


    @RequestMapping(value = "/save")
    public String saveMetalMinerals( HttpServletRequest request,DictionaryMetalMinerals dictionaryMetalMinerals){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryMetalMinerals.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryMetalMinerals.setName(name);
            dictionaryMetalMinerals.setValue(value);
            dictionaryMetalMinerals.setRemark(remark);
            dictionaryMetalMinerals.setPid(pid);
            dictionaryMetalMinerals.setLevel(level);

            dictionaryMetalMineralsService.saveMetalMinerals(dictionaryMetalMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryMetalMinerals/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryMetalMinerals dictionaryMetalMinerals = new DictionaryMetalMinerals();
        dictionaryMetalMinerals.setId(id);
        List<DictionaryMetalMinerals> dictionaryMetalMineralss = dictionaryMetalMineralsService.findMetalMineralsList(dictionaryMetalMinerals);
        model.addAttribute("dictionaryMetalMinerals",dictionaryMetalMineralss.get(0));

        return "modules/content/dictionaryManagement/dictionaryMetalMineralsFrom";
    }

    @RequestMapping(value = "/updateMetalMinerals")
    public String updateMetalMinerals( HttpServletRequest request,DictionaryMetalMinerals dictionaryMetalMinerals){

        try {
            dictionaryMetalMineralsService.updateMetalMinerals(dictionaryMetalMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryMetalMinerals/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteMetalMinerals(@RequestParam String id){

        dictionaryMetalMineralsService.deleteMetalMinerals(id);


        return "redirect:" + adminPath +"/nswy/dictionaryMetalMinerals/control/";
    }
}
