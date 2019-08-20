package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNotMetalMinerals;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryNotMetalMineralsService;
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
@RequestMapping("${adminPath}/nswy/dictionaryNotMetalMinerals")
public class DictionaryNotMetalMineralsController extends BaseController {

    @Autowired
    private DictionaryNotMetalMineralsService dictionaryNotMetalMineralsService;

    @RequestMapping("/control")
    public String findList(DictionaryNotMetalMinerals dictionaryNotMetalMinerals, Model model, HttpServletRequest request, HttpServletResponse response){
       Page<DictionaryNotMetalMinerals> page = dictionaryNotMetalMineralsService.findList(dictionaryNotMetalMinerals,request,response);
       model.addAttribute("page",page);
       return "modules/content/dictionaryManagement/dictionaryNotMetalMineralsList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryNotMetalMineralsAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryNotMetalMineralsAdd";
    }


    @RequestMapping(value = "/save")
    public String saveNotMetalMinerals( HttpServletRequest request,DictionaryNotMetalMinerals dictionaryNotMetalMinerals){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryNotMetalMinerals.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryNotMetalMinerals.setName(name);
            dictionaryNotMetalMinerals.setValue(value);
            dictionaryNotMetalMinerals.setRemark(remark);
            dictionaryNotMetalMinerals.setPid(pid);
            dictionaryNotMetalMinerals.setLevel(level);

            dictionaryNotMetalMineralsService.saveNotMetalMinerals(dictionaryNotMetalMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryNotMetalMinerals/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryNotMetalMinerals dictionaryNotMetalMinerals = new DictionaryNotMetalMinerals();
        dictionaryNotMetalMinerals.setId(id);
        List<DictionaryNotMetalMinerals> dictionaryNotMetalMineralss = dictionaryNotMetalMineralsService.findNotMetalMineralsList(dictionaryNotMetalMinerals);
        model.addAttribute("dictionaryNotMetalMinerals",dictionaryNotMetalMineralss.get(0));

        return "modules/content/dictionaryManagement/dictionaryNotMetalMineralsFrom";
    }

    @RequestMapping(value = "/updateNotMetalMinerals")
    public String updateNotMetalMinerals( HttpServletRequest request,DictionaryNotMetalMinerals dictionaryNotMetalMinerals){

        try {
            dictionaryNotMetalMineralsService.updateNotMetalMinerals(dictionaryNotMetalMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryNotMetalMinerals/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteNotMetalMinerals(@RequestParam String id){

        dictionaryNotMetalMineralsService.deleteNotMetalMinerals(id);


        return "redirect:" + adminPath +"/nswy/dictionaryNotMetalMinerals/control/";
    }
}
