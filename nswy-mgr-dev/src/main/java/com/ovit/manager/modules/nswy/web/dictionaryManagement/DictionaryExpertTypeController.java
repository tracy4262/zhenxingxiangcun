package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryExpertType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryExpertTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryExpertType")
public class DictionaryExpertTypeController extends BaseController {

    @Autowired
    private DictionaryExpertTypeService dictionaryExpertTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryExpertType dictionaryExpertType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryExpertType> page = dictionaryExpertTypeService.findList(dictionaryExpertType,request,response);
        List<DictionaryExpertType> page = dictionaryExpertTypeService.findList(dictionaryExpertType);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryExpertTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryExpertTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryExpertTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveExpertType( HttpServletRequest request,DictionaryExpertType dictionaryExpertType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryExpertType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryExpertType.setName(name);
            dictionaryExpertType.setValue(value);
            dictionaryExpertType.setRemark(remark);
            dictionaryExpertType.setPid(pid);
            dictionaryExpertType.setLevel(level);

            dictionaryExpertTypeService.saveExpertType(dictionaryExpertType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryExpertType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryExpertType dictionaryExpertType = new DictionaryExpertType();
        dictionaryExpertType.setId(id);
        List<DictionaryExpertType> dictionaryExpertTypes = dictionaryExpertTypeService.findExpertTypeList(dictionaryExpertType);
        model.addAttribute("dictionaryExpertType",dictionaryExpertTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryExpertTypeFrom";
    }

    @RequestMapping(value = "/updateExpertType")
    public String updateExpertType( HttpServletRequest request,DictionaryExpertType dictionaryExpertType){

        try {
            dictionaryExpertTypeService.updateExpertType(dictionaryExpertType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryExpertType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteExpertType(@RequestParam String id){

        dictionaryExpertTypeService.deleteExpertType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryExpertType/control/";
    }
}
