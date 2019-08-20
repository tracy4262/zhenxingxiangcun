package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyUser;
import com.ovit.manager.modules.nswy.entity.UserTypeInfo;
import com.ovit.manager.modules.nswy.service.UserTypeService;
import com.ovit.manager.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ovit.manager.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("${adminPath}/nswy/userType")
public class UserTypeController extends BaseController{

    @Autowired
    private UserTypeService userTypeService;

    @RequestMapping("/findList")
    public String findList(UserTypeInfo userTypeInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        String userTypeName = request.getParameter("userTypeName");
        if(StringUtils.isNotBlank(userTypeName)){
            userTypeInfo.setUserTypeName(userTypeName);
        }

        Page<UserTypeInfo> page =userTypeService.findList(userTypeInfo, request, response);
        model.addAttribute("page",page);
        return "modules/content/userTypeList";
    }

    @RequestMapping("findFrom")
    public String findFrom(UserTypeInfo userTypeInfo,Model model){
        model.addAttribute("userTypeInfo",userTypeInfo);
        return "modules/content/userTypeform";
    }

    @RequestMapping("/save")
    public String insertUserTypeInfo(UserTypeInfo userTypeInfo, RedirectAttributes redirectAttributes){
        Principal principal = UserUtils.getPrincipal();
        userTypeInfo.setAccount(principal.getLoginName());
        userTypeService.insertUserTypeInfo(userTypeInfo);
        addMessage(redirectAttributes, "添加成功！");
        return "redirect:" + adminPath +"/nswy/userType/findList";
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam String infoId){

        userTypeService.deleteUserType(Integer.valueOf(infoId));


        return "redirect:" + adminPath +"/nswy/userType/findList";
    }




}
