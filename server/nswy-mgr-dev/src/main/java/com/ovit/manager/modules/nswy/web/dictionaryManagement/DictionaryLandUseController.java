package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandUse;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryLandUseService;
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
@RequestMapping("${adminPath}/nswy/dictionaryLandUse")
public class DictionaryLandUseController extends BaseController {

    @Autowired
    private DictionaryLandUseService dictionaryLandUseService;

    @RequestMapping("/control")
    public String findList(DictionaryLandUse dictionaryLandUse, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryLandUse> page = dictionaryLandUseService.findList(dictionaryLandUse,request,response);
        List<DictionaryLandUse> page = dictionaryLandUseService.findList(dictionaryLandUse);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryLandUseList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryLandUseAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryLandUseAdd";
    }


    @RequestMapping(value = "/save")
    public String saveLandUse( HttpServletRequest request,DictionaryLandUse dictionaryLandUse){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryLandUse.setName(name);
            dictionaryLandUse.setValue(value);
            dictionaryLandUse.setRemark(remark);
            dictionaryLandUse.setPid(pid);
            dictionaryLandUse.setLevel(level);

            dictionaryLandUseService.saveLandUse(dictionaryLandUse);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryLandUse/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryLandUse dictionaryLandUse = new DictionaryLandUse();
        dictionaryLandUse.setId(id);
        List<DictionaryLandUse> dictionaryLandUses = dictionaryLandUseService.findLandUseList(dictionaryLandUse);
        model.addAttribute("dictionaryLandUse",dictionaryLandUses.get(0));

        return "modules/content/dictionaryManagement/dictionaryLandUseFrom";
    }

    @RequestMapping(value = "/updateLandUse")
    public String updateLandUse( HttpServletRequest request,DictionaryLandUse dictionaryLandUse){

        try {
            dictionaryLandUseService.updateLandUse(dictionaryLandUse);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryLandUse/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteLandUse(@RequestParam String id){

        dictionaryLandUseService.deleteLandUse(id);


        return "redirect:" + adminPath +"/nswy/dictionaryLandUse/control/";
    }
}
