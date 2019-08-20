package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTown;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryTownService;
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
@RequestMapping("${adminPath}/nswy/dictionaryTown")
public class DictionaryTownController extends BaseController {

    @Autowired
    private DictionaryTownService dictionaryTownService;

    @RequestMapping("/control")
    public String findList(DictionaryTown dictionaryTown, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryTown> page = dictionaryTownService.findList(dictionaryTown,request,response);
        List<DictionaryTown> page = dictionaryTownService.findList(dictionaryTown);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryTownList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryTownAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryTownAdd";
    }


    @RequestMapping(value = "/save")
    public String saveTown( HttpServletRequest request,DictionaryTown dictionaryTown){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryTown.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryTown.setName(name);
            dictionaryTown.setValue(value);
            dictionaryTown.setRemark(remark);
            dictionaryTown.setPid(pid);
            dictionaryTown.setLevel(level);

            dictionaryTownService.saveTown(dictionaryTown);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTown/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryTown dictionaryTown = new DictionaryTown();
        dictionaryTown.setId(id);
        List<DictionaryTown> dictionaryTowns = dictionaryTownService.findTownList(dictionaryTown);
        model.addAttribute("dictionaryTown",dictionaryTowns.get(0));

        return "modules/content/dictionaryManagement/dictionaryTownFrom";
    }

    @RequestMapping(value = "/updateTown")
    public String updateTown( HttpServletRequest request,DictionaryTown dictionaryTown){

        try {
            dictionaryTownService.updateTown(dictionaryTown);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTown/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteTown(@RequestParam String id){

        dictionaryTownService.deleteTown(id);


        return "redirect:" + adminPath +"/nswy/dictionaryTown/control/";
    }
}
