package com.ovit.manager.modules.nswy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyUser;
import com.ovit.manager.modules.nswy.service.NswyUserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswyUserController extends BaseController{

    @Autowired
    private NswyUserService nswyUserService;

    @ModelAttribute
    public NswyUser get(@RequestParam(required=false) Integer userId) {
        if (userId !=null){
            return nswyUserService.getUser(userId);
        }else{
            return new NswyUser();
        }
    }

    @RequestMapping(value = "form")
    public String form(NswyUser nswyUser, Model model) {
        model.addAttribute("nswyUser",nswyUser);
        return "modules/nswy/userForm";
    }

    @RequestMapping("/getUserlist")
    public String getUserlist(NswyUser userDTO,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<NswyUser> page =nswyUserService.findUserList(userDTO,request,response);
        model.addAttribute("page",page);
        return "modules/nswy/userViewList";
    }

    @RequestMapping("/saveOrUpdateUser")
    public String saveOrUpdateUser(NswyUser nswyUser){
        nswyUserService.saveOrUpdateUser(nswyUser);
        return "redirect:" + adminPath +"/nswy/getUserlist/";
    }

    @RequestMapping("/delete")
    public String delete(Integer userId){
        nswyUserService.deleteUser(userId);
        return  "redirect:" + adminPath +"/nswy/getUserlist/";
    }
}
