package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMemberType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryMemberTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryMemberType")
public class DictionaryMemberTypeController extends BaseController {

    @Autowired
    private DictionaryMemberTypeService dictionaryMemberTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryMemberType dictionaryMemberType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryMemberType> page = dictionaryMemberTypeService.findList(dictionaryMemberType,request,response);
        List<DictionaryMemberType> page = dictionaryMemberTypeService.findList(dictionaryMemberType);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryMemberTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryMemberTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryMemberTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveMemberType( HttpServletRequest request,DictionaryMemberType dictionaryMemberType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryMemberType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryMemberType.setName(name);
            dictionaryMemberType.setValue(value);
            dictionaryMemberType.setRemark(remark);
            dictionaryMemberType.setPid(pid);
            dictionaryMemberType.setLevel(level);

            dictionaryMemberTypeService.saveMemberType(dictionaryMemberType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryMemberType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryMemberType dictionaryMemberType = new DictionaryMemberType();
        dictionaryMemberType.setId(id);
        List<DictionaryMemberType> dictionaryMemberTypes = dictionaryMemberTypeService.findMemberTypeList(dictionaryMemberType);
        model.addAttribute("dictionaryMemberType",dictionaryMemberTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryMemberTypeFrom";
    }

    @RequestMapping(value = "/updateMemberType")
    public String updateMemberType( HttpServletRequest request,DictionaryMemberType dictionaryMemberType){

        try {
            dictionaryMemberTypeService.updateMemberType(dictionaryMemberType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryMemberType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteMemberType(@RequestParam String id){

        dictionaryMemberTypeService.deleteMemberType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryMemberType/control/";
    }
}
