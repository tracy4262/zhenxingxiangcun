package com.ovit.manager.modules.nswy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyJuBao;
import com.ovit.manager.modules.nswy.service.NswyJuBaoService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-8-17.
 */

@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyJuBaoController  extends BaseController {

    @Autowired
    private NswyJuBaoService nswyJuBaoService;

    @ModelAttribute
    public NswyJuBao get(@RequestParam(required=false) Integer juBaoId) {
        if (juBaoId !=null){
            return nswyJuBaoService.getJuBaoById(juBaoId);
        }else{
            return new NswyJuBao();
        }
    }

    @RequestMapping(value = "juBaoForm")
    public String nswyJuBaoForm(NswyJuBao nswyJuBao, Model model) {
        model.addAttribute("nswyJuBao",nswyJuBao);
        return "modules/nswy/juBaoForm";
    }

    @RequestMapping("/getJuBaolist")
    public String getJuBaolist(NswyJuBao nswyJuBao,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyJuBao> page =nswyJuBaoService.findJuBaolist(nswyJuBao, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/juBaoList";
    }

    @RequestMapping("/deleteJuBao")
    public String deleteJuBao(Integer juBaoId){
        nswyJuBaoService.deleteJuBao(juBaoId);
        return  "redirect:" + adminPath +"/nswy/getJuBaolist/";
    }

    @RequestMapping("/updateJuBao")
    public String updateJuBao(NswyJuBao nswyJuBao){
        nswyJuBaoService.updateJuBao(nswyJuBao);
        return "redirect:" + adminPath +"/nswy/getJuBaolist/";
    }
}
