package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryAgencyTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryAgencyType")
public class DictionaryAgencyTypeController extends BaseController {

    @Autowired
    private DictionaryAgencyTypeService dictionaryAgencyTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryAgencyType dictionaryAgencyType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryAgencyType> page = dictionaryAgencyTypeService.findList(dictionaryAgencyType,request,response);
        List<DictionaryAgencyType> page = dictionaryAgencyTypeService.findList(dictionaryAgencyType);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryAgencyTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryAgencyTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryAgencyTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveAgencyType( HttpServletRequest request,DictionaryAgencyType dictionaryAgencyType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryAgencyType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryAgencyType.setName(name);
            dictionaryAgencyType.setValue(value);
            dictionaryAgencyType.setRemark(remark);
            dictionaryAgencyType.setPid(pid);
            dictionaryAgencyType.setLevel(level);

            dictionaryAgencyTypeService.saveAgencyType(dictionaryAgencyType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryAgencyType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryAgencyType dictionaryAgencyType = new DictionaryAgencyType();
        dictionaryAgencyType.setId(id);
        List<DictionaryAgencyType> dictionaryAgencyTypes = dictionaryAgencyTypeService.findAgencyTypeList(dictionaryAgencyType);
        model.addAttribute("dictionaryAgencyType",dictionaryAgencyTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryAgencyTypeFrom";
    }

    @RequestMapping(value = "/updateAgencyType")
    public String updateAgencyType( HttpServletRequest request,DictionaryAgencyType dictionaryAgencyType){

        try {
            dictionaryAgencyTypeService.updateAgencyType(dictionaryAgencyType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryAgencyType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteAgencyType(@RequestParam String id){

        dictionaryAgencyTypeService.deleteAgencyType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryAgencyType/control/";
    }
}
