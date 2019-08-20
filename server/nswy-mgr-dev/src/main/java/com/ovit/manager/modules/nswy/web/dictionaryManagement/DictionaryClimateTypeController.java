package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryClimateType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryClimateTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryClimateType")
public class DictionaryClimateTypeController extends BaseController {

    @Autowired
    private DictionaryClimateTypeService dictionaryClimateTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryClimateType dictionaryClimateType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryClimateType> page = dictionaryClimateTypeService.findList(dictionaryClimateType,request,response);
        List<DictionaryClimateType> page = dictionaryClimateTypeService.findList(dictionaryClimateType);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryClimateTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryClimateTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryClimateTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveClimateType( HttpServletRequest request,DictionaryClimateType dictionaryClimateType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryClimateType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryClimateType.setName(name);
            dictionaryClimateType.setValue(value);
            dictionaryClimateType.setRemark(remark);
            dictionaryClimateType.setPid(pid);
            dictionaryClimateType.setLevel(level);

            dictionaryClimateTypeService.saveClimateType(dictionaryClimateType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryClimateType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryClimateType dictionaryClimateType = new DictionaryClimateType();
        dictionaryClimateType.setId(id);
        List<DictionaryClimateType> dictionaryClimateTypes = dictionaryClimateTypeService.findClimateTypeList(dictionaryClimateType);
        model.addAttribute("dictionaryClimateType",dictionaryClimateTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryClimateTypeFrom";
    }

    @RequestMapping(value = "/updateClimateType")
    public String updateClimateType( HttpServletRequest request,DictionaryClimateType dictionaryClimateType){

        try {
            dictionaryClimateTypeService.updateClimateType(dictionaryClimateType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryClimateType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteClimateType(@RequestParam String id){

        dictionaryClimateTypeService.deleteClimateType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryClimateType/control/";
    }
}
