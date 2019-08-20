package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryReportType;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryReportType")
public class DictionaryReportTypeController extends BaseController {

    @Autowired
    private DictionaryReportTypeService dictionaryReportTypeService;

    @RequestMapping("/control")
    public String findList(DictionaryReportType dictionaryReportType, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryReportType> page = dictionaryReportTypeService.findList(dictionaryReportType,request,response);
        List<DictionaryReportType> page = dictionaryReportTypeService.findList(dictionaryReportType);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryReportTypeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryReportTypeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryReportTypeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveReportType( HttpServletRequest request,DictionaryReportType dictionaryReportType){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryReportType.setName(name);
            dictionaryReportType.setValue(value);
            dictionaryReportType.setRemark(remark);
            dictionaryReportType.setPid(pid);
            dictionaryReportType.setLevel(level);


            dictionaryReportTypeService.saveReportType(dictionaryReportType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryReportType/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryReportType dictionaryReportType = new DictionaryReportType();
        dictionaryReportType.setId(id);
        List<DictionaryReportType> dictionaryReportTypes = dictionaryReportTypeService.findReportTypeList(dictionaryReportType);
        model.addAttribute("dictionaryReportType",dictionaryReportTypes.get(0));

        return "modules/content/dictionaryManagement/dictionaryReportTypeFrom";
    }

    @RequestMapping(value = "/updateReportType")
    public String updateReportType( HttpServletRequest request,DictionaryReportType dictionaryReportType){

        try {
            dictionaryReportTypeService.updateReportType(dictionaryReportType);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryReportType/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteReportType(@RequestParam String id){

        dictionaryReportTypeService.deleteReportType(id);


        return "redirect:" + adminPath +"/nswy/dictionaryReportType/control/";
    }
}
