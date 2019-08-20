package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductionWay;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryProductionWayService;
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
@RequestMapping("${adminPath}/nswy/dictionaryProductionWay")
public class DictionaryProductionWayController extends BaseController {

    @Autowired
    private DictionaryProductionWayService dictionaryProductionWayService;

    @RequestMapping("/control")
    public String findList(DictionaryProductionWay dictionaryProductionWay, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryProductionWay> page = dictionaryProductionWayService.findList(dictionaryProductionWay,request,response);
        List<DictionaryProductionWay> page = dictionaryProductionWayService.findList(dictionaryProductionWay);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryProductionWayList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryProductionWayAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryProductionWayAdd";
    }


    @RequestMapping(value = "/save")
    public String saveProductionWay( HttpServletRequest request,DictionaryProductionWay dictionaryProductionWay){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryProductionWay.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryProductionWay.setName(name);
            dictionaryProductionWay.setValue(value);
            dictionaryProductionWay.setRemark(remark);
            dictionaryProductionWay.setPid(pid);
            dictionaryProductionWay.setLevel(level);

            dictionaryProductionWayService.saveProductionWay(dictionaryProductionWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProductionWay/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryProductionWay dictionaryProductionWay = new DictionaryProductionWay();
        dictionaryProductionWay.setId(id);
        List<DictionaryProductionWay> dictionaryProductionWays = dictionaryProductionWayService.findProductionWayList(dictionaryProductionWay);
        model.addAttribute("dictionaryProductionWay",dictionaryProductionWays.get(0));

        return "modules/content/dictionaryManagement/dictionaryProductionWayFrom";
    }

    @RequestMapping(value = "/updateProductionWay")
    public String updateProductionWay( HttpServletRequest request,DictionaryProductionWay dictionaryProductionWay){

        try {
            dictionaryProductionWayService.updateProductionWay(dictionaryProductionWay);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryProductionWay/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteProductionWay(@RequestParam String id){

        dictionaryProductionWayService.deleteProductionWay(id);


        return "redirect:" + adminPath +"/nswy/dictionaryProductionWay/control/";
    }
}
