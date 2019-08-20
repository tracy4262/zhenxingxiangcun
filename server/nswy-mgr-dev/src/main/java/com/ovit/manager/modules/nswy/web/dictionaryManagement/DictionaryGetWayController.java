package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGetWay;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryGetWayService;
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
@RequestMapping("${adminPath}/nswy/dictionaryGetWay")
public class DictionaryGetWayController extends BaseController {

    @Autowired
    private DictionaryGetWayService dictionaryGetWayService;

    @RequestMapping("/control")
    public String findList(DictionaryGetWay dictionaryGetWay, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryGetWay> page = dictionaryGetWayService.findList(dictionaryGetWay,request,response);
        List<DictionaryGetWay> page = dictionaryGetWayService.findList(dictionaryGetWay);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryGetWayList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryGetWayAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryGetWayAdd";
    }


    @RequestMapping(value = "/save")
    public String saveGetWay( HttpServletRequest request,DictionaryGetWay dictionaryGetWay){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryGetWay.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryGetWay.setName(name);
            dictionaryGetWay.setValue(value);
            dictionaryGetWay.setRemark(remark);
            dictionaryGetWay.setPid(pid);
            dictionaryGetWay.setLevel(level);

            dictionaryGetWayService.saveGetWay(dictionaryGetWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryGetWay/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryGetWay dictionaryGetWay = new DictionaryGetWay();
        dictionaryGetWay.setId(id);
        List<DictionaryGetWay> dictionaryGetWays = dictionaryGetWayService.findGetWayList(dictionaryGetWay);
        model.addAttribute("dictionaryGetWay",dictionaryGetWays.get(0));

        return "modules/content/dictionaryManagement/dictionaryGetWayFrom";
    }

    @RequestMapping(value = "/updateGetWay")
    public String updateGetWay( HttpServletRequest request,DictionaryGetWay dictionaryGetWay){

        try {
            dictionaryGetWayService.updateGetWay(dictionaryGetWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryGetWay/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteGetWay(@RequestParam String id){

        dictionaryGetWayService.deleteGetWay(id);


        return "redirect:" + adminPath +"/nswy/dictionaryGetWay/control/";
    }
}
