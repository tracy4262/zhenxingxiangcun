package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFacility;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryFacilityService;
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
@RequestMapping("${adminPath}/nswy/dictionaryFacility")
public class DictionaryFacilityController extends BaseController {

    @Autowired
    private DictionaryFacilityService dictionaryFacilityService;

    @RequestMapping("/control")
    public String findList(DictionaryFacility dictionaryFacility, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryFacility> page = dictionaryFacilityService.findList(dictionaryFacility,request,response);
        List<DictionaryFacility> page = dictionaryFacilityService.findList(dictionaryFacility);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryFacilityList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryFacilityAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryFacilityAdd";
    }


    @RequestMapping(value = "/save")
    public String saveFacility( HttpServletRequest request,DictionaryFacility dictionaryFacility){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryFacility.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryFacility.setName(name);
            dictionaryFacility.setValue(value);
            dictionaryFacility.setRemark(remark);
            dictionaryFacility.setPid(pid);
            dictionaryFacility.setLevel(level);

            dictionaryFacilityService.saveFacility(dictionaryFacility);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryFacility/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryFacility dictionaryFacility = new DictionaryFacility();
        dictionaryFacility.setId(id);
        List<DictionaryFacility> dictionaryFacilitys = dictionaryFacilityService.findFacilityList(dictionaryFacility);
        model.addAttribute("dictionaryFacility",dictionaryFacilitys.get(0));

        return "modules/content/dictionaryManagement/dictionaryFacilityFrom";
    }

    @RequestMapping(value = "/updateFacility")
    public String updateFacility( HttpServletRequest request,DictionaryFacility dictionaryFacility){

        try {
            dictionaryFacilityService.updateFacility(dictionaryFacility);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryFacility/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteFacility(@RequestParam String id){

        dictionaryFacilityService.deleteFacility(id);


        return "redirect:" + adminPath +"/nswy/dictionaryFacility/control/";
    }
}
