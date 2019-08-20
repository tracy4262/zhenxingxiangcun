package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyLevel;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryAgencyLevelService;
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
@RequestMapping("${adminPath}/nswy/dictionaryAgencyLevel")
public class DictionaryAgencyLevelController extends BaseController {

    @Autowired
    private DictionaryAgencyLevelService dictionaryAgencyLevelService;

    @RequestMapping("/control")
    public String findList(DictionaryAgencyLevel dictionaryAgencyLevel, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryAgencyLevel> page = dictionaryAgencyLevelService.findList(dictionaryAgencyLevel,request,response);
        List<DictionaryAgencyLevel> page = dictionaryAgencyLevelService.findList(dictionaryAgencyLevel);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryAgencyLevelList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryAgencyLevelAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryAgencyLevelAdd";
    }


    @RequestMapping(value = "/save")
    public String saveAgencyLevel( HttpServletRequest request,DictionaryAgencyLevel dictionaryAgencyLevel){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryAgencyLevel.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryAgencyLevel.setName(name);
            dictionaryAgencyLevel.setValue(value);
            dictionaryAgencyLevel.setRemark(remark);
            dictionaryAgencyLevel.setPid(pid);
            dictionaryAgencyLevel.setLevel(level);

            dictionaryAgencyLevelService.saveAgencyLevel(dictionaryAgencyLevel);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryAgencyLevel/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryAgencyLevel dictionaryAgencyLevel = new DictionaryAgencyLevel();
        dictionaryAgencyLevel.setId(id);
        List<DictionaryAgencyLevel> dictionaryAgencyLevels = dictionaryAgencyLevelService.findAgencyLevelList(dictionaryAgencyLevel);
        model.addAttribute("dictionaryAgencyLevel",dictionaryAgencyLevels.get(0));

        return "modules/content/dictionaryManagement/dictionaryAgencyLevelFrom";
    }

    @RequestMapping(value = "/updateAgencyLevel")
    public String updateAgencyLevel( HttpServletRequest request,DictionaryAgencyLevel dictionaryAgencyLevel){

        try {
            dictionaryAgencyLevelService.updateAgencyLevel(dictionaryAgencyLevel);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryAgencyLevel/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteAgencyLevel(@RequestParam String id){

        dictionaryAgencyLevelService.deleteAgencyLevel(id);


        return "redirect:" + adminPath +"/nswy/dictionaryAgencyLevel/control/";
    }
}
