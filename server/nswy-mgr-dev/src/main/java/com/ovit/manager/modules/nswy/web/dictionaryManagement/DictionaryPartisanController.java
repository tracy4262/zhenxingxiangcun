package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPartisan;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryPartisanService;
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
@RequestMapping("${adminPath}/nswy/dictionaryPartisan")
public class DictionaryPartisanController extends BaseController {

    @Autowired
    private DictionaryPartisanService dictionaryPartisanService;

    @RequestMapping("/control")
    public String findList(DictionaryPartisan dictionaryPartisan, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryPartisan> page = dictionaryPartisanService.findList(dictionaryPartisan,request,response);
        List<DictionaryPartisan> page = dictionaryPartisanService.findList(dictionaryPartisan);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryPartisanList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryPartisanAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryPartisanAdd";
    }


    @RequestMapping(value = "/save")
    public String savePartisan( HttpServletRequest request,DictionaryPartisan dictionaryPartisan){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryPartisan.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryPartisan.setName(name);
            dictionaryPartisan.setValue(value);
            dictionaryPartisan.setRemark(remark);
            dictionaryPartisan.setPid(pid);
            dictionaryPartisan.setLevel(level);

            dictionaryPartisanService.savePartisan(dictionaryPartisan);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPartisan/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryPartisan dictionaryPartisan = new DictionaryPartisan();
        dictionaryPartisan.setId(id);
        List<DictionaryPartisan> dictionaryPartisans = dictionaryPartisanService.findPartisanList(dictionaryPartisan);
        model.addAttribute("dictionaryPartisan",dictionaryPartisans.get(0));

        return "modules/content/dictionaryManagement/dictionaryPartisanFrom";
    }

    @RequestMapping(value = "/updatePartisan")
    public String updatePartisan( HttpServletRequest request,DictionaryPartisan dictionaryPartisan){

        try {
            dictionaryPartisanService.updatePartisan(dictionaryPartisan);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPartisan/control/";
    }

    @RequestMapping(value = "/delete")
    public String deletePartisan(@RequestParam String id){

        dictionaryPartisanService.deletePartisan(id);


        return "redirect:" + adminPath +"/nswy/dictionaryPartisan/control/";
    }
}
