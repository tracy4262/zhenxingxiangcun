package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryInformation;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryInformation")
public class DictionaryInformationController extends BaseController {

    @Autowired
    private DictionaryInformationService dictionaryInformationService;

    @RequestMapping("/control")
    public String findList(DictionaryInformation dictionaryInformation, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryInformation> page = dictionaryInformationService.findList(dictionaryInformation,request,response);
        List<DictionaryInformation> page = dictionaryInformationService.findList(dictionaryInformation);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryInformationList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryInformationAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryInformationAdd";
    }


    @RequestMapping(value = "/save")
    public String saveInformation( HttpServletRequest request,DictionaryInformation dictionaryInformation){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String type = "Information";

            dictionaryInformation.setName(name);
            dictionaryInformation.setValue(value);
            dictionaryInformation.setRemark(remark);
            dictionaryInformation.setPid(pid);
            dictionaryInformation.setLevel(level);
            dictionaryInformation.setType(type);

            dictionaryInformationService.saveInformation(dictionaryInformation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryInformation/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryInformation dictionaryInformation = new DictionaryInformation();
        dictionaryInformation.setId(id);
        List<DictionaryInformation> dictionaryInformations = dictionaryInformationService.findInformationList(dictionaryInformation);
        model.addAttribute("dictionaryInformation",dictionaryInformations.get(0));

        return "modules/content/dictionaryManagement/dictionaryInformationFrom";
    }

    @RequestMapping(value = "/updateInformation")
    public String updateInformation( HttpServletRequest request,DictionaryInformation dictionaryInformation){

        try {
            dictionaryInformationService.updateInformation(dictionaryInformation);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryInformation/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteInformation(@RequestParam String id){

        dictionaryInformationService.deleteInformation(id);


        return "redirect:" + adminPath +"/nswy/dictionaryInformation/control/";
    }
}
