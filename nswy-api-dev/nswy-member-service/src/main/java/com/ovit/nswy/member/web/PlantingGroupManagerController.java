package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.PlantingGroupManagerService;
import com.ovit.nswy.member.web.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/plantingGroupManager")
public class PlantingGroupManagerController {

    @Autowired
    private PlantingGroupManagerService plantingGroupManagerService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Result addUser(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        String user_account = null;
//		StringBuffer addr = new StringBuffer();
//		if (params.get("addr") != null){
//			List<String> list = (List)params.get("addr");
//			for (String addrs:list){
//				addr.append(addrs);
//			}
//			params.put("addr",addr.toString());
//		}
        user_account = plantingGroupManagerService.addUser(params);
        result.setData(user_account);
        return result;
    }

    @RequestMapping(value = "/addEducation", method = RequestMethod.POST)
    public Result addEducation(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.addEducation(params);
        return result;
    }

    @RequestMapping(value = "/addWork", method = RequestMethod.POST)
    public Result addWork(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.addWork(params);
        return result;
    }

    @RequestMapping(value = "/addPolicial", method = RequestMethod.POST)
    public Result addPolicial(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.addPolicial(params);
        return result;
    }

    @RequestMapping(value = "/addRreligion", method = RequestMethod.POST)
    public Result addRreligion(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.addRreligion(params);
        return result;
    }

    @RequestMapping(value = "/addHonor", method = RequestMethod.POST)
    public Result addHonor(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.addHonor(params);
        return result;
    }

    @RequestMapping(value = "/queryGroups", method = RequestMethod.POST)
    public Result queryGroups(HttpServletRequest request) {
        Result result = new Result();
        Map<String, Object> params = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        //LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        //String account = loginUser.getLoginAccount();
        params.put("login_account", params.get("account").toString());
        Map<String, Object> list = plantingGroupManagerService.queryGroups(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/removeUser/{id}", method = RequestMethod.POST)
    public Result removeUser(@PathVariable Integer id, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.removeUser(id);
        return result;
    }

    @RequestMapping(value = "/queryUserById", method = RequestMethod.POST)
    public Map<String, Object> queryUserById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = plantingGroupManagerService.queryUserById(params);
        return map;
    }

    @RequestMapping(value = "/queryMembersFamilyById", method = RequestMethod.POST)
    public Result queryMembersFamilyById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        if (params.get("user_account") == null) {
            HttpSession session = request.getSession();
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            params.put("user_account", account);
        }
        List<Map<String, Object>> list = plantingGroupManagerService.queryMembersFamilyById(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/queryProductOutputById", method = RequestMethod.POST)
    public Result queryProductOutputById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        if (params.get("user_account") == null) {
            HttpSession session = request.getSession();
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            params.put("user_account", account);
        }
        List<Map<String, Object>> list = plantingGroupManagerService.queryProductOutputById(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/queryProvideServicesById", method = RequestMethod.POST)
    public Result queryProvideServicesById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        if (params.get("user_account") == null) {
            HttpSession session = request.getSession();
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            params.put("user_account", account);
        }
        List<Map<String, Object>> list = plantingGroupManagerService.queryProvideServicesById(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/queryIntentionById", method = RequestMethod.POST)
    public Result queryIntentionById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        if (params.get("user_account") == null) {
            HttpSession session = request.getSession();
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            params.put("user_account", account);
        }
        List<Map<String, Object>> list = plantingGroupManagerService.queryIntentionById(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/addMembersFamily", method = RequestMethod.POST)
    public Result addMembersFamily(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
//		String account = loginUser.getLoginAccount();
//		params.put("login_account",account);
        plantingGroupManagerService.addMembersFamily(params);
        return result;
    }

    @RequestMapping(value = "/delMembersFamily", method = RequestMethod.POST)
    public Result delMembersFamily(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.delMembersFamily(params);
        return result;
    }

    @RequestMapping(value = "/updateMembersFamily", method = RequestMethod.POST)
    public Result updateMembersFamily(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.updateMembersFamily(params);
        return result;
    }

    @RequestMapping(value = "/addProductOutput", method = RequestMethod.POST)
    public Result addProductOutput(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
//		String account = loginUser.getLoginAccount();
//		params.put("login_account",account);
        plantingGroupManagerService.addProductOutput(params);
        return result;
    }

    @RequestMapping(value = "/delProductOutput", method = RequestMethod.POST)
    public Result delProductOutput(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.delProductOutput(params);
        return result;
    }

    @RequestMapping(value = "/updateProductOutput", method = RequestMethod.POST)
    public Result updateProductOutput(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.updateProductOutput(params);
        return result;
    }

    @RequestMapping(value = "/addProvideServices", method = RequestMethod.POST)
    public Result addProvideServices(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
//		String account = loginUser.getLoginAccount();
//		params.put("login_account",account);
        plantingGroupManagerService.addProvideServices(params);
        return result;
    }

    @RequestMapping(value = "/delProvideServices", method = RequestMethod.POST)
    public Result delProvideServices(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.delProvideServices(params);
        return result;
    }

    @RequestMapping(value = "/updateProvideServices", method = RequestMethod.POST)
    public Result updateProvideServices(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.updateProvideServices(params);
        return result;
    }

    @RequestMapping(value = "/addIntention", method = RequestMethod.POST)
    public Result addIntention(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
//		String account = loginUser.getLoginAccount();
//		params.put("login_account",account);
        plantingGroupManagerService.addIntention(params);
        return result;
    }

    @RequestMapping(value = "/delIntention", method = RequestMethod.POST)
    public Result delIntention(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.delIntention(params);
        return result;
    }

    @RequestMapping(value = "/updateIntention", method = RequestMethod.POST)
    public Result updateIntention(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.updateIntention(params);
        return result;
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result updateUserInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		StringBuffer addr = new StringBuffer();
//		if (params.get("addr") != null){
//			List<String> list = (List)params.get("addr");
//			for (String addrs:list){
//				addr.append(addrs);
//			}
//			params.put("addr",addr.toString());
//		}
        plantingGroupManagerService.updateUserInfo(params);
        return result;
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.POST)
    public Result queryDetail(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        PageInfo<Map<String, Object>> page = plantingGroupManagerService.queryDetail(params);
        result.setData(page);
        return result;
    }

    @RequestMapping(value = "/getSelectGroup", method = RequestMethod.POST)
    public Result getSelectGroup(HttpServletRequest request) {
        Result result = new Result();
        Map<String, Object> params = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        List<Map<String, Object>> list = plantingGroupManagerService.getSelectGroup(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public Result delUser(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupManagerService.delUser(params);
        return result;
    }

}
