package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("${adminPath}/nswy")
public class MemberController extends BaseController {
    @Autowired
    MemberService memberService;

    @RequestMapping("/member/control")
    public String findList(@RequestParam Map<String,Object> params, Model model){
        /*Page<Map<String,Object>> page = memberService.findRealMember(params);
        model.addAttribute("page",page);*/
        Page<Map<String, Object>> list = memberService.findRealMember(params);
        model.addAttribute("page",list);
        return "modules/content/memberList";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/member/update-home",method = RequestMethod.POST)
    public String memberUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        memberService.memberUpdateHome(map);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/member/set-order-num",method = RequestMethod.POST)
    public String memberSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        memberService.memberSetOrderNum(map);
        return "success";
    }


}
