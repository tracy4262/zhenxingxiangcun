package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPoliticalStatus;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryPoliticalStatusService;
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
@RequestMapping("${adminPath}/nswy/dictionaryPoliticalStatus")
public class DictionaryPoliticalStatusController extends BaseController {

    @Autowired
    private DictionaryPoliticalStatusService dictionaryPoliticalStatusService;

    @RequestMapping("/control")
    public String findList(DictionaryPoliticalStatus dictionaryPoliticalStatus, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryPoliticalStatus> page = dictionaryPoliticalStatusService.findList(dictionaryPoliticalStatus,request,response);
        List<DictionaryPoliticalStatus> page = dictionaryPoliticalStatusService.findList(dictionaryPoliticalStatus);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryPoliticalStatusList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryPoliticalStatusAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryPoliticalStatusAdd";
    }


    @RequestMapping(value = "/save")
    public String savePoliticalStatus( HttpServletRequest request,DictionaryPoliticalStatus dictionaryPoliticalStatus){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryPoliticalStatus.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryPoliticalStatus.setName(name);
            dictionaryPoliticalStatus.setValue(value);
            dictionaryPoliticalStatus.setRemark(remark);
            dictionaryPoliticalStatus.setPid(pid);
            dictionaryPoliticalStatus.setLevel(level);

            dictionaryPoliticalStatusService.savePoliticalStatus(dictionaryPoliticalStatus);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPoliticalStatus/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryPoliticalStatus dictionaryPoliticalStatus = new DictionaryPoliticalStatus();
        dictionaryPoliticalStatus.setId(id);
        List<DictionaryPoliticalStatus> dictionaryPoliticalStatuss = dictionaryPoliticalStatusService.findPoliticalStatusList(dictionaryPoliticalStatus);
        model.addAttribute("dictionaryPoliticalStatus",dictionaryPoliticalStatuss.get(0));

        return "modules/content/dictionaryManagement/dictionaryPoliticalStatusFrom";
    }

    @RequestMapping(value = "/updatePoliticalStatus")
    public String updatePoliticalStatus( HttpServletRequest request,DictionaryPoliticalStatus dictionaryPoliticalStatus){

        try {
            dictionaryPoliticalStatusService.updatePoliticalStatus(dictionaryPoliticalStatus);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPoliticalStatus/control/";
    }

    @RequestMapping(value = "/delete")
    public String deletePoliticalStatus(@RequestParam String id){

        dictionaryPoliticalStatusService.deletePoliticalStatus(id);


        return "redirect:" + adminPath +"/nswy/dictionaryPoliticalStatus/control/";
    }
}
