package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFaith;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryFaithService;
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
@RequestMapping("${adminPath}/nswy/dictionaryFaith")
public class DictionaryFaithController extends BaseController {

    @Autowired
    private DictionaryFaithService dictionaryFaithService;

    @RequestMapping("/control")
    public String findList(DictionaryFaith dictionaryFaith, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryFaith> page = dictionaryFaithService.findList(dictionaryFaith,request,response);
        List<DictionaryFaith> page = dictionaryFaithService.findList(dictionaryFaith);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryFaithList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryFaithAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryFaithAdd";
    }


    @RequestMapping(value = "/save")
    public String saveFaith( HttpServletRequest request,DictionaryFaith dictionaryFaith){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryFaith.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryFaith.setName(name);
            dictionaryFaith.setValue(value);
            dictionaryFaith.setRemark(remark);
            dictionaryFaith.setPid(pid);
            dictionaryFaith.setLevel(level);

            dictionaryFaithService.saveFaith(dictionaryFaith);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryFaith/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryFaith dictionaryFaith = new DictionaryFaith();
        dictionaryFaith.setId(id);
        List<DictionaryFaith> dictionaryFaiths = dictionaryFaithService.findFaithList(dictionaryFaith);
        model.addAttribute("dictionaryFaith",dictionaryFaiths.get(0));

        return "modules/content/dictionaryManagement/dictionaryFaithFrom";
    }

    @RequestMapping(value = "/updateFaith")
    public String updateFaith( HttpServletRequest request,DictionaryFaith dictionaryFaith){

        try {
            dictionaryFaithService.updateFaith(dictionaryFaith);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryFaith/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteFaith(@RequestParam String id){

        dictionaryFaithService.deleteFaith(id);


        return "redirect:" + adminPath +"/nswy/dictionaryFaith/control/";
    }
}
