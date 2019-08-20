package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import com.ovit.manager.modules.nswy.entity.RealName;
import com.ovit.manager.modules.nswy.service.ExpertService;
import com.ovit.manager.modules.nswy.service.NswyCorpInfoService;
import com.ovit.manager.modules.nswy.service.RealNameService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("${adminPath}/nswy")
public class RealNameController extends BaseController{
    @Autowired
    RealNameService realNameService;

    @RequestMapping("/realName/control")
    public String pictureList(RealName realName, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<RealName> page = realNameService.findList(realName, request, response);

        model.addAttribute("page",page);

        return "modules/content/realNameList";

    }

    @RequestMapping(value = "/realName/add")
    public String add(@RequestParam String userId){
        List<RealName> list = realNameService.findByUserId(userId);
        RealName realName = list.get(0);
        realNameService.saveRealName(realName);

        return "redirect:" + adminPath +"/nswy/realName/control";
    }

    @RequestMapping(value = "/realName/from")
    public String from(@RequestParam String realId,Model model){
        List<RealName> list = realNameService.findById(realId);
        model.addAttribute("real",list.get(0));
        return "modules/content/realNameFrom";
    }

    @RequestMapping(value = "/realName/update")
    public String update(@RequestParam String userId){
        realNameService.updateRealName(userId);
        return "redirect:" + adminPath +"/nswy/realName/control";
    }

    @RequestMapping(value = "/realName/delete")
    public String delete(@RequestParam String userId){
        realNameService.deleteRealName(userId);
        return "redirect:" + adminPath +"/nswy/realName/control";
    }



}
