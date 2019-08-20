package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRightType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryRightTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryRightType")
public class DictionaryRightTypeController extends BaseController {

    @Autowired
    private DictionaryRightTypeService dictionaryRightTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryRightType dictionaryRightType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryRightType> page = dictionaryRightTypeService.findList(dictionaryRightType,request,response);
        List<DictionaryRightType> page = dictionaryRightTypeService.findList(dictionaryRightType);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryRightTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryRightTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryRightTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveRightType( HttpServletRequest request,DictionaryRightType dictionaryRightType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryRightType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryRightType.setName(name);
            dictionaryRightType.setValue(value);
            dictionaryRightType.setRemark(remark);
            dictionaryRightType.setPid(pid);
            dictionaryRightType.setLevel(level);

            dictionaryRightTypeService.saveRightType(dictionaryRightType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryRightType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryRightType dictionaryRightType = new DictionaryRightType();
        dictionaryRightType.setId(id);
        List<DictionaryRightType> dictionaryRightTypes = dictionaryRightTypeService.findRightTypeList(dictionaryRightType);
        model.addAttribute("dictionaryRightType",dictionaryRightTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryRightTypeFrom";
    }

    @RequestMapping(value = "/updateRightType")
    public String updateRightType( HttpServletRequest request,DictionaryRightType dictionaryRightType){

        try {
            dictionaryRightTypeService.updateRightType(dictionaryRightType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryRightType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteRightType(@RequestParam String id){

        dictionaryRightTypeService.deleteRightType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryRightType/control/";
    }
}
