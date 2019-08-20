package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNationality;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryNationalityService;
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
@RequestMapping("${adminPath}/nswy/dictionaryNationality")
public class DictionaryNationalityController extends BaseController {

    @Autowired
    private DictionaryNationalityService dictionaryNationalityService;

    @RequestMapping("/control")
    public String findList(DictionaryNationality dictionaryNationality, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryNationality> page = dictionaryNationalityService.findList(dictionaryNationality,request,response);
        List<DictionaryNationality> page = dictionaryNationalityService.findList(dictionaryNationality);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryNationalityList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryNationalityAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryNationalityAdd";
    }


    @RequestMapping(value = "/save")
    public String saveNationality( HttpServletRequest request,DictionaryNationality dictionaryNationality){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryNationality.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryNationality.setName(name);
            dictionaryNationality.setValue(value);
            dictionaryNationality.setRemark(remark);
            dictionaryNationality.setPid(pid);
            dictionaryNationality.setLevel(level);

            dictionaryNationalityService.saveNationality(dictionaryNationality);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryNationality/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryNationality dictionaryNationality = new DictionaryNationality();
        dictionaryNationality.setId(id);
        List<DictionaryNationality> dictionaryNationalitys = dictionaryNationalityService.findNationalityList(dictionaryNationality);
        model.addAttribute("dictionaryNationality",dictionaryNationalitys.get(0));

        return "modules/content/dictionaryManagement/dictionaryNationalityFrom";
    }

    @RequestMapping(value = "/updateNationality")
    public String updateNationality( HttpServletRequest request,DictionaryNationality dictionaryNationality){

        try {
            dictionaryNationalityService.updateNationality(dictionaryNationality);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryNationality/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteNationality(@RequestParam String id){

        dictionaryNationalityService.deleteNationality(id);


        return "redirect:" + adminPath +"/nswy/dictionaryNationality/control/";
    }
}
