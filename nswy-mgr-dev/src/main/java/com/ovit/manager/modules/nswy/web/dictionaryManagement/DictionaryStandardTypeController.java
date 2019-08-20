package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryStandardType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryStandardTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryStandardType")
public class DictionaryStandardTypeController extends BaseController {

    @Autowired
    private DictionaryStandardTypeService dictionaryStandardTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryStandardType dictionaryStandardType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryStandardType> page = dictionaryStandardTypeService.findList(dictionaryStandardType,request,response);
        List<DictionaryStandardType> page = dictionaryStandardTypeService.findList(dictionaryStandardType);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryStandardTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryStandardTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryStandardTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveStandardType( HttpServletRequest request,DictionaryStandardType dictionaryStandardType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryStandardType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryStandardType.setName(name);
            dictionaryStandardType.setValue(value);
            dictionaryStandardType.setRemark(remark);
            dictionaryStandardType.setPid(pid);
            dictionaryStandardType.setLevel(level);

            dictionaryStandardTypeService.saveStandardType(dictionaryStandardType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryStandardType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryStandardType dictionaryStandardType = new DictionaryStandardType();
        dictionaryStandardType.setId(id);
        List<DictionaryStandardType> dictionaryStandardTypes = dictionaryStandardTypeService.findStandardTypeList(dictionaryStandardType);
        model.addAttribute("dictionaryStandardType",dictionaryStandardTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryStandardTypeFrom";
    }

    @RequestMapping(value = "/updateStandardType")
    public String updateStandardType( HttpServletRequest request,DictionaryStandardType dictionaryStandardType){

        try {
            dictionaryStandardTypeService.updateStandardType(dictionaryStandardType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryStandardType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteStandardType(@RequestParam String id){

        dictionaryStandardTypeService.deleteStandardType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryStandardType/control/";
    }
}
