package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProtectionLevel;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryProtectionLevelService;
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
@RequestMapping("${adminPath}/nswy/dictionaryProtectionLevel")
public class DictionaryProtectionLevelController extends BaseController {

    @Autowired
    private DictionaryProtectionLevelService dictionaryProtectionLevelService;

    @RequestMapping("/control")
    public String findList(DictionaryProtectionLevel dictionaryProtectionLevel, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryProtectionLevel> page = dictionaryProtectionLevelService.findList(dictionaryProtectionLevel,request,response);
        List<DictionaryProtectionLevel> page = dictionaryProtectionLevelService.findList(dictionaryProtectionLevel);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryProtectionLevelList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryProtectionLevelAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryProtectionLevelAdd";
    }


    @RequestMapping(value = "/save")
    public String saveProtectionLevel( HttpServletRequest request,DictionaryProtectionLevel dictionaryProtectionLevel){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryProtectionLevel.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryProtectionLevel.setName(name);
            dictionaryProtectionLevel.setValue(value);
            dictionaryProtectionLevel.setRemark(remark);
            dictionaryProtectionLevel.setPid(pid);
            dictionaryProtectionLevel.setLevel(level);

            dictionaryProtectionLevelService.saveProtectionLevel(dictionaryProtectionLevel);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProtectionLevel/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryProtectionLevel dictionaryProtectionLevel = new DictionaryProtectionLevel();
        dictionaryProtectionLevel.setId(id);
        List<DictionaryProtectionLevel> dictionaryProtectionLevels = dictionaryProtectionLevelService.findProtectionLevelList(dictionaryProtectionLevel);
        model.addAttribute("dictionaryProtectionLevel",dictionaryProtectionLevels.get(0));

        return "modules/content/dictionaryManagement/dictionaryProtectionLevelFrom";
    }

    @RequestMapping(value = "/updateProtectionLevel")
    public String updateProtectionLevel( HttpServletRequest request,DictionaryProtectionLevel dictionaryProtectionLevel){

        try {
            dictionaryProtectionLevelService.updateProtectionLevel(dictionaryProtectionLevel);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProtectionLevel/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteProtectionLevel(@RequestParam String id){

        dictionaryProtectionLevelService.deleteProtectionLevel(id);


        return "redirect:" + adminPath +"/nswy/dictionaryProtectionLevel/control/";
    }
}
