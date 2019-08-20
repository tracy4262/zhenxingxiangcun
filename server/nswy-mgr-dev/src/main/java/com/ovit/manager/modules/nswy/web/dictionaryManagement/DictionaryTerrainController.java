package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTerrain;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryTerrainService;
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
@RequestMapping("${adminPath}/nswy/dictionaryTerrain")
public class DictionaryTerrainController extends BaseController {

    @Autowired
    private DictionaryTerrainService dictionaryTerrainService;

    @RequestMapping("/control")
    public String findList(DictionaryTerrain dictionaryTerrain, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryTerrain> page = dictionaryTerrainService.findList(dictionaryTerrain,request,response);
        List<DictionaryTerrain> page = dictionaryTerrainService.findList(dictionaryTerrain);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryTerrainList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryTerrainAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryTerrainAdd";
    }


    @RequestMapping(value = "/save")
    public String saveTerrain( HttpServletRequest request,DictionaryTerrain dictionaryTerrain){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryTerrain.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryTerrain.setName(name);
            dictionaryTerrain.setValue(value);
            dictionaryTerrain.setRemark(remark);
            dictionaryTerrain.setPid(pid);
            dictionaryTerrain.setLevel(level);

            dictionaryTerrainService.saveTerrain(dictionaryTerrain);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTerrain/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryTerrain dictionaryTerrain = new DictionaryTerrain();
        dictionaryTerrain.setId(id);
        List<DictionaryTerrain> dictionaryTerrains = dictionaryTerrainService.findTerrainList(dictionaryTerrain);
        model.addAttribute("dictionaryTerrain",dictionaryTerrains.get(0));

        return "modules/content/dictionaryManagement/dictionaryTerrainFrom";
    }

    @RequestMapping(value = "/updateTerrain")
    public String updateTerrain( HttpServletRequest request,DictionaryTerrain dictionaryTerrain){

        try {
            dictionaryTerrainService.updateTerrain(dictionaryTerrain);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryTerrain/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteTerrain(@RequestParam String id){

        dictionaryTerrainService.deleteTerrain(id);


        return "redirect:" + adminPath +"/nswy/dictionaryTerrain/control/";
    }
}
