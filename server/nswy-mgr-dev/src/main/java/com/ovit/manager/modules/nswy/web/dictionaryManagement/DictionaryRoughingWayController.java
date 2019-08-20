package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRoughingWay;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryRoughingWayService;
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
@RequestMapping("${adminPath}/nswy/dictionaryRoughingWay")
public class DictionaryRoughingWayController extends BaseController {

    @Autowired
    private DictionaryRoughingWayService dictionaryRoughingWayService;

    @RequestMapping("/control")
    public String findList(DictionaryRoughingWay dictionaryRoughingWay, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryRoughingWay> page = dictionaryRoughingWayService.findList(dictionaryRoughingWay,request,response);
        List<DictionaryRoughingWay> page = dictionaryRoughingWayService.findList(dictionaryRoughingWay);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryRoughingWayList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryRoughingWayAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryRoughingWayAdd";
    }


    @RequestMapping(value = "/save")
    public String saveRoughingWay( HttpServletRequest request,DictionaryRoughingWay dictionaryRoughingWay){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryRoughingWay.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryRoughingWay.setName(name);
            dictionaryRoughingWay.setValue(value);
            dictionaryRoughingWay.setRemark(remark);
            dictionaryRoughingWay.setPid(pid);
            dictionaryRoughingWay.setLevel(level);

            dictionaryRoughingWayService.saveRoughingWay(dictionaryRoughingWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryRoughingWay/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryRoughingWay dictionaryRoughingWay = new DictionaryRoughingWay();
        dictionaryRoughingWay.setId(id);
        List<DictionaryRoughingWay> dictionaryRoughingWays = dictionaryRoughingWayService.findRoughingWayList(dictionaryRoughingWay);
        model.addAttribute("dictionaryRoughingWay",dictionaryRoughingWays.get(0));

        return "modules/content/dictionaryManagement/dictionaryRoughingWayFrom";
    }

    @RequestMapping(value = "/updateRoughingWay")
    public String updateRoughingWay( HttpServletRequest request,DictionaryRoughingWay dictionaryRoughingWay){

        try {
            dictionaryRoughingWayService.updateRoughingWay(dictionaryRoughingWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryRoughingWay/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteRoughingWay(@RequestParam String id){

        dictionaryRoughingWayService.deleteRoughingWay(id);


        return "redirect:" + adminPath +"/nswy/dictionaryRoughingWay/control/";
    }
}
