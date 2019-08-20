package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.FarmFamilyService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 种养户
 */
@RestController
@RequestMapping("/farmFamily")
public class FarmFamilyController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FarmFamilyService farmFamilyService;

    /**
     * 添加分组
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertGroup", method = RequestMethod.POST)
    public Result insertGroup(@RequestBody Map<String,Object> params){

        Result result = new Result();

        String farm  = farmFamilyService.insertGroup(params);
        if(!"success".equals(farm)){
            result.setCode(300);
            result.setMsg("组别已存在，请不要重复添加！");
        }
        return result;
    }

    /**
     * 修改分组
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public Result updateGroup(@RequestBody Map<String,Object> params){

        Result result = new Result();
        farmFamilyService.updateGroup(params);
        return result;
    }




    /**
     * 查询分组
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGroup", method = RequestMethod.POST)
    public Result findGroup(@RequestBody Map<String,Object> params){
        Result result = new Result();
        List<Map<String,Object>> list = farmFamilyService.findGroup(params);
        result.setData(list);
        return result;
    }

    /**
     * 添加种养户合作社名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertTitle", method = RequestMethod.POST)
    public Result insertTitle(@RequestBody Map<String,Object> params){

        Result result = new Result();

        String farm  = farmFamilyService.insertTitle(params);
        if(!"success".equals(farm)){
            result.setCode(300);
            result.setMsg("组别已存在，请不要重复添加！");
        }
        return result;
    }

    /**
     * 修改种养户合作社名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
    public Result updateTitle(@RequestBody Map<String,Object> params){

        Result result = new Result();
        farmFamilyService.updateTitle(params);
        return result;
    }



    /**
     * 查询种养户合作社名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTitle", method = RequestMethod.POST)
    public Result findTitle(@RequestBody Map<String,Object> params){
        Result result = new Result();
        List<Map<String,Object>> list = farmFamilyService.findTitle(params);
        result.setData(list);
        return result;
    }


    /**
     * 添加成员
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertFarm", method = RequestMethod.POST)
    public Result insertFarm(@RequestBody Map<String,Object> params){
        Result result = new Result();
        String string  = farmFamilyService.insertFarm(params);
        if(string.equals("error")){
            result.setCode(300);
            result.setMsg("此账号存在！请不要重复添加!");
        }else if(string.equals("errors")){
            result.setCode(301);
            result.setMsg("账号或密码错误！请重新输入!");
        }else if(string.equals("error1")){
            result.setCode(302);
            result.setMsg("账号已存在！请不要重复添加!");
        }
        return result;
    }

    /**
     * 查询种养户列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFarmFamilyList", method = RequestMethod.POST)
    public Result findFarmFamilyList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        PageInfo<Map<String,Object>> page = farmFamilyService.findFarmFamilyList(params);
        result.setData(page);
        return result;
    }


    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFarmFamily", method = RequestMethod.POST)
    public Result deleteFarmFamily(@RequestBody Map<String,Object> params){
        Result result = new Result();
        farmFamilyService.deleteFarmFamily(params);
        return result;
    }

    /**
     * 查询总人数
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCountFarm", method = RequestMethod.POST)
    public Result findCountFarm(@RequestBody Map<String,Object> params){

        Result result = new Result();
        Integer count  = farmFamilyService.findCountFarm(params);
        result.setData(count);
        return result;

    }


}
