package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryColumn;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryColumnService;
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
@RequestMapping("${adminPath}/nswy/dictionaryColumn")
public class DictionaryColumnController extends BaseController {

    @Autowired
    private DictionaryColumnService dictionaryColumnService;

    @RequestMapping("/control")
    public String findList(DictionaryColumn dictionaryColumn, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryColumn> page = dictionaryColumnService.findList(dictionaryColumn,request,response);
        List<DictionaryColumn> page = dictionaryColumnService.findList(dictionaryColumn);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryColumnList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,Model model){
        model.addAttribute("pid",id);
        return "modules/content/dictionaryManagement/dictionaryColumnAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,Model model){
        model.addAttribute("pid",pid);
        return "modules/content/dictionaryManagement/dictionaryColumnAdd";
    }


    @RequestMapping(value = "/save")
    public String saveColumn( HttpServletRequest request,DictionaryColumn dictionaryColumn){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");

            dictionaryColumn.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryColumn.setName(name);
            dictionaryColumn.setValue(value);
            dictionaryColumn.setPid(pid);

            dictionaryColumnService.saveColumn(dictionaryColumn);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryColumn/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryColumn dictionaryColumn = new DictionaryColumn();
        dictionaryColumn.setId(id);
        List<DictionaryColumn> dictionaryColumns = dictionaryColumnService.findColumnList(dictionaryColumn);
        model.addAttribute("dictionaryColumn",dictionaryColumns.get(0));

        return "modules/content/dictionaryManagement/dictionaryColumnFrom";
    }

    @RequestMapping(value = "/updateColumn")
    public String updateColumn( HttpServletRequest request,DictionaryColumn dictionaryColumn){

        try {
            dictionaryColumnService.updateColumn(dictionaryColumn);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryColumn/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteColumn(@RequestParam String id){

        dictionaryColumnService.deleteColumn(id);


        return "redirect:" + adminPath +"/nswy/dictionaryColumn/control/";
    }
}
