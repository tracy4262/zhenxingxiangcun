package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryKnowledge;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryKnowledge")
public class DictionaryKnowledgeController extends BaseController {

    @Autowired
    private DictionaryKnowledgeService dictionaryKnowledgeService;

    @RequestMapping("/control")
    public String findList(DictionaryKnowledge dictionaryKnowledge, Model model, HttpServletRequest request, HttpServletResponse response){
       Page<DictionaryKnowledge> page = dictionaryKnowledgeService.findList(dictionaryKnowledge,request,response);
       model.addAttribute("page",page);
       return "modules/content/dictionaryManagement/dictionaryKnowledgeList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryKnowledgeAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryKnowledgeAdd";
    }


    @RequestMapping(value = "/save")
    public String saveKnowledge( HttpServletRequest request,DictionaryKnowledge dictionaryKnowledge){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String type = "Knowledge";

            dictionaryKnowledge.setName(name);
            dictionaryKnowledge.setValue(value);
            dictionaryKnowledge.setRemark(remark);
            dictionaryKnowledge.setPid(pid);
            dictionaryKnowledge.setLevel(level);
            dictionaryKnowledge.setType(type);

            dictionaryKnowledgeService.saveKnowledge(dictionaryKnowledge);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryKnowledge/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryKnowledge dictionaryKnowledge = new DictionaryKnowledge();
        dictionaryKnowledge.setId(id);
        List<DictionaryKnowledge> dictionaryKnowledges = dictionaryKnowledgeService.findKnowledgeList(dictionaryKnowledge);
        model.addAttribute("dictionaryKnowledge",dictionaryKnowledges.get(0));

        return "modules/content/dictionaryManagement/dictionaryKnowledgeFrom";
    }

    @RequestMapping(value = "/updateKnowledge")
    public String updateKnowledge( HttpServletRequest request,DictionaryKnowledge dictionaryKnowledge){

        try {
            dictionaryKnowledgeService.updateKnowledge(dictionaryKnowledge);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryKnowledge/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteKnowledge(@RequestParam String id){

        dictionaryKnowledgeService.deleteKnowledge(id);


        return "redirect:" + adminPath +"/nswy/dictionaryKnowledge/control/";
    }
}
