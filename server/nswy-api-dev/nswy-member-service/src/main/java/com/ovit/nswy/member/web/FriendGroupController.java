package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.FriendGroup;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.FriendGroupService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendGroup")
public class FriendGroupController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private FriendGroupService service;
	@Autowired
	private LoginUserService loginUserService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        HttpSession session = request.getSession();
        try {
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            List<FriendGroup> list = new ArrayList<FriendGroup>();
            String username = loginUser.getLoginAccount();
            List<Map<String, String>> object = (List<Map<String, String>>) params.get("friend");
            int num = 0;
            if (!object.isEmpty()) {
                service.deleteByAccount(username);
                for (int i = 0; i < object.size(); i++) {
                    Map<String, String> map = object.get(i);
                    FriendGroup friendGroup = new FriendGroup();
                    friendGroup.setGroupName(map.get("name"));
                    friendGroup.setLoginAccount(username);
                    list.add(friendGroup);
                }
                num = service.insert(list);
            }
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(username, step);
                }
            }
            result.setData(num);
        }catch (Exception e){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/find")
    public Result find(HttpServletRequest request) {
        Result result = new Result();
        HttpSession session = request.getSession();
        try {
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String username = loginUser.getLoginAccount();
            List<FriendGroup> list=service.selectByAccount(username);
            result.setData(list);
        }catch (Exception e){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }
}