package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.DictionaryManageInfo;

import com.ovit.manager.modules.nswy.service.DictionaryManageService;
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
@RequestMapping("${adminPath}/nswy")
public class DictionaryManageController extends BaseController {

    @Autowired
    private DictionaryManageService dictionaryManageService;


    @RequestMapping(value = "/dictionary/control")
    public String findDictList(DictionaryManageInfo dictionaryManageInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<DictionaryManageInfo> page = dictionaryManageService.findDictList(dictionaryManageInfo, request, response);

        model.addAttribute("page",page);

        return "modules/dict/dictList";

    }

    @RequestMapping(value = "/dictionary/update")
    public String update(@RequestParam String dictId,Model model){
        DictionaryManageInfo dictionaryManageInfo = new DictionaryManageInfo();
        dictionaryManageInfo.setDictId(dictId);
        List<DictionaryManageInfo> dictionaryManageInfos = dictionaryManageService.findList(dictionaryManageInfo);
        model.addAttribute("dictionaryManageInfo",dictionaryManageInfos.get(0));

        return "modules/dict/dictFrom";
    }

    @RequestMapping(value = "/dictionary/updateDictionary")
    public String updateDictionaryManage( HttpServletRequest request,DictionaryManageInfo dictionaryManageInfo){

        try {
            dictionaryManageService.updateDictionaryManage(dictionaryManageInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionary/control/";
    }

    @RequestMapping(value = "/dictionary/delete")
    public String deletePost(@RequestParam String dictId){

        dictionaryManageService.deleteDictionaryManage(dictId);

        return "redirect:" + adminPath +"/nswy/dictionary/control/";
    }
}
