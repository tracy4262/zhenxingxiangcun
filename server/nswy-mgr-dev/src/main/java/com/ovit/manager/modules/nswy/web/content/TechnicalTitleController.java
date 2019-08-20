package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.TechnicalTitleInfo;
import com.ovit.manager.modules.nswy.service.PostService;
import com.ovit.manager.modules.nswy.service.TechnicalTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * 职称管理
 *
 * @author wangrongf
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class TechnicalTitleController extends BaseController{
    @Autowired
    private TechnicalTitleService technicalTitleService;


    @RequestMapping(value = "/technical/control")
    public String pictureList(TechnicalTitleInfo technicalTitleInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        String technicalTitleName = request.getParameter("technicalTitleName");
        if(StringUtils.isNotBlank(technicalTitleName)){
            technicalTitleInfo.setTechnicalTitleName(technicalTitleName);
        }
        List<TechnicalTitleInfo> page = technicalTitleService.findTitleInfo(technicalTitleInfo);

        model.addAttribute("list",page);

        return "modules/content/technicalTitleList";

    }

    @RequestMapping(value = "/technical/add")
    public String add(@RequestParam String id, Model model){
        model.addAttribute("pid",id);
        return "modules/content/technicalTitleAdd";
    }

    @RequestMapping(value = "/technical/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);
        return "modules/content/technicalTitleAdd";
    }


    @RequestMapping(value = "/technical/save")
    public String saveUnit( HttpServletRequest request,TechnicalTitleInfo technicalTitleInfo){

        try {

            String technicalTitleName = request.getParameter("technicalTitleName");
            String pid = request.getParameter("pid");
            String titleType = request.getParameter("titleType");
            technicalTitleInfo.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            technicalTitleInfo.setTechnicalTitleName(technicalTitleName);
            technicalTitleInfo.setPid(pid);
            technicalTitleInfo.setTitleType(titleType);
            technicalTitleService.saveTitle(technicalTitleInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/technical/control/";
    }

    @RequestMapping(value = "/technical/form")
    public String update(@RequestParam String id,Model model){
        TechnicalTitleInfo technicalTitleInfo = new TechnicalTitleInfo();
        technicalTitleInfo.setId(id);
        List<TechnicalTitleInfo> postInfos = technicalTitleService.findTitleInfo(technicalTitleInfo);
        model.addAttribute("technicalTitleInfo",postInfos.get(0));

        return "modules/content/technicalTitleFrom";
    }

    @RequestMapping(value = "/technical/updateTitle")
    public String updateTitle( HttpServletRequest request,TechnicalTitleInfo technicalTitleInfo){

        try {
            technicalTitleService.updateTitle(technicalTitleInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/technical/control/";
    }

    @RequestMapping(value = "/technical/delete")
    public String deleteTitle(@RequestParam String id){

        technicalTitleService.deleteTitle(id);


        return "redirect:" + adminPath +"/nswy/technical/control/";
    }

}
