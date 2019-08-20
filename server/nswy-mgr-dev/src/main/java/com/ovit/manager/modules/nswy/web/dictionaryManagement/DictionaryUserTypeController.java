package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUserType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryUserType")
public class DictionaryUserTypeController extends BaseController {

    @Autowired
    private DictionaryUserTypeService dictionaryUserTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryUserType dictionaryUserType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryUserType> page = dictionaryUserTypeService.findList(dictionaryUserType,request,response);
        List<DictionaryUserType> page = dictionaryUserTypeService.findList(dictionaryUserType);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryUserTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryUserTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryUserTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveUserType( HttpServletRequest request,DictionaryUserType dictionaryUserType){

        try {
            String account = "administrator";
            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryUserType.setAccount(account);
            dictionaryUserType.setName(name);
            dictionaryUserType.setValue(value);
            dictionaryUserType.setRemark(remark);
            dictionaryUserType.setPid(pid);
            dictionaryUserType.setLevel(level);


            dictionaryUserTypeService.saveUserType(dictionaryUserType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryUserType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryUserType dictionaryUserType = new DictionaryUserType();
        dictionaryUserType.setId(id);
        List<DictionaryUserType> dictionaryUserTypes = dictionaryUserTypeService.findUserTypeList(dictionaryUserType);
        model.addAttribute("dictionaryUserType",dictionaryUserTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryUserTypeFrom";
    }

    @RequestMapping(value = "/updateUserType")
    public String updateUserType( HttpServletRequest request,DictionaryUserType dictionaryUserType){

        try {
            dictionaryUserTypeService.updateUserType(dictionaryUserType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryUserType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteUserType(@RequestParam String id){

        dictionaryUserTypeService.deleteUserType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryUserType/control/";
    }
}
