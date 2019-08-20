package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandform;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryLandformService;
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
@RequestMapping("${adminPath}/nswy/dictionaryLandform")
public class DictionaryLandformController extends BaseController {

    @Autowired
    private DictionaryLandformService dictionaryLandformService;

    @RequestMapping("/control")
    public String findList(DictionaryLandform dictionaryLandform, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryLandform> page = dictionaryLandformService.findList(dictionaryLandform,request,response);
        List<DictionaryLandform> page = dictionaryLandformService.findList(dictionaryLandform);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryLandformList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryLandformAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryLandformAdd";
    }


    @RequestMapping(value = "/save")
    public String saveLandform( HttpServletRequest request,DictionaryLandform dictionaryLandform){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryLandform.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryLandform.setName(name);
            dictionaryLandform.setValue(value);
            dictionaryLandform.setRemark(remark);
            dictionaryLandform.setPid(pid);
            dictionaryLandform.setLevel(level);

            dictionaryLandformService.saveLandform(dictionaryLandform);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryLandform/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryLandform dictionaryLandform = new DictionaryLandform();
        dictionaryLandform.setId(id);
        List<DictionaryLandform> dictionaryLandforms = dictionaryLandformService.findLandformList(dictionaryLandform);
        model.addAttribute("dictionaryLandform",dictionaryLandforms.get(0));

        return "modules/content/dictionaryManagement/dictionaryLandformFrom";
    }

    @RequestMapping(value = "/updateLandform")
    public String updateLandform( HttpServletRequest request,DictionaryLandform dictionaryLandform){

        try {
            dictionaryLandformService.updateLandform(dictionaryLandform);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryLandform/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteLandform(@RequestParam String id){

        dictionaryLandformService.deleteLandform(id);


        return "redirect:" + adminPath +"/nswy/dictionaryLandform/control/";
    }
}
