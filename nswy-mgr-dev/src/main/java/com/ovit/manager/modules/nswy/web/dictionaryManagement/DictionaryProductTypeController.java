package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryProductTypeService;
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
@RequestMapping("${adminPath}/nswy/dictionaryProductType")
public class DictionaryProductTypeController extends BaseController {

    @Autowired
    private DictionaryProductTypeService dictionaryProductTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryProductType dictionaryProductType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryProductType> page = dictionaryProductTypeService.findList(dictionaryProductType,request,response);
        List<DictionaryProductType> page = dictionaryProductTypeService.findList(dictionaryProductType);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryProductTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryProductTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryProductTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveProductType( HttpServletRequest request,DictionaryProductType dictionaryProductType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryProductType.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryProductType.setName(name);
            dictionaryProductType.setValue(value);
            dictionaryProductType.setRemark(remark);
            dictionaryProductType.setPid(pid);
            dictionaryProductType.setLevel(level);

            dictionaryProductTypeService.saveProductType(dictionaryProductType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProductType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryProductType dictionaryProductType = new DictionaryProductType();
        dictionaryProductType.setId(id);
        List<DictionaryProductType> dictionaryProductTypes = dictionaryProductTypeService.findProductTypeList(dictionaryProductType);
        model.addAttribute("dictionaryProductType",dictionaryProductTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryProductTypeFrom";
    }

    @RequestMapping(value = "/updateProductType")
    public String updateProductType( HttpServletRequest request,DictionaryProductType dictionaryProductType){

        try {
            dictionaryProductTypeService.updateProductType(dictionaryProductType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProductType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteProductType(@RequestParam String id){

        dictionaryProductTypeService.deleteProductType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryProductType/control/";
    }
}
