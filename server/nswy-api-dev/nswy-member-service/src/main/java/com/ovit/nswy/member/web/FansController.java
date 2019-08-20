package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.ExistsFriendService;
import com.ovit.nswy.member.service.FansService;
import com.ovit.nswy.member.web.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/fans")
public class FansController {

    @Autowired
    private FansService fansService;

    @RequestMapping(value = "/getFans", method = RequestMethod.POST)
    public Result getFans(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        PageInfo<Map<String, Object>> page = fansService.getFans(params);
        result.setData(page);
        return result;
    }

    @RequestMapping(value = "/getLike", method = RequestMethod.POST)
    public Result getLike(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        PageInfo<Map<String, Object>> page = fansService.getLike(params);
        result.setData(page);
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        fansService.add(params);
        return result;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        fansService.del(params);
        return result;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Result status(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        Boolean status = fansService.status(params);
        result.setData(status);
        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Result cancel(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        fansService.cancel(params);
        return result;
    }

    @RequestMapping(value = "/batchLike",method = RequestMethod.POST)
    public Result batchLike(@RequestBody Map<String,Object> params){
        Result result = new Result();
        fansService.batchLike(params);
        return result;
    }


}
