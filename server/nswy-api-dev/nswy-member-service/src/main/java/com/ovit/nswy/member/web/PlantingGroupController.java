package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.PlantingGroupService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plantingGroup")
public class PlantingGroupController {
    @Autowired
    private PlantingGroupService plantingGroupService;
    private Logger logger = LogManager.getLogger(this.getClass());


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        if (params.get("group_name") == null) {
            result.setCode(ResultCode.FAIL);
            result.setMsg("添加失败");
        } else {
            plantingGroupService.add(params);
        }
        return result;
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public Result del(@PathVariable Integer id, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupService.del(id);
        return result;
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public Result rename(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        plantingGroupService.rename(params);
        return result;
    }

    @RequestMapping(value = "/sorting", method = RequestMethod.POST)
    public Result sorting(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
        plantingGroupService.sorting(list);
        return result;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Result getList(HttpServletRequest request) {
        Result result = new Result();
        Map<String, Object> params = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        List<Map<String, Object>> list = plantingGroupService.getList(params);
        result.setData(list);
        return result;
    }
}
