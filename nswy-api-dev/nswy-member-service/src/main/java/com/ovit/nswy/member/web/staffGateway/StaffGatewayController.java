package com.ovit.nswy.member.web.staffGateway;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.StaffGatewayService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staffGateway")
public class StaffGatewayController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private StaffGatewayService staffGatewayService;

    /**
     * 查询分组列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGroupList", method = RequestMethod.POST)
    public Result findGroupList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = staffGatewayService.findGroupList(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关系圈分组初始化失败");
        }
        return result;
    }


    /**
     * 修改/新增分组
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateGroup", method = RequestMethod.POST)
    public Result saveOrUpdateGroup(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            staffGatewayService.saveOrUpdateGroup(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增/修改分组失败");
        }
        return result;
    }

    /**
     * 删除分组
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
    public Result deleteGroup(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            staffGatewayService.deleteGroup(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除分组失败");
        }
        return result;
    }



    /**
     *获取员工列表的数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertStaff", method = RequestMethod.POST)
    public Result insertStaff(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            staffGatewayService.insertStaff(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注失败");
        }
        return result;
    }

    /**
     * 根据分组查询好友列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGroupFriendList", method = RequestMethod.POST)
    public Result findGroupFriendList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> resultMap = new HashMap<>();
            String account = MapUtils.getString(params, "account");
            resultMap.put("account", account);
            resultMap.put("key", account);

            PageList list = staffGatewayService.findGroupFriendList(params);
            resultMap.put("list", list);
            result.setData(resultMap);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注控件数据查询失败");
        }
        return result;
    }


    /**
     * 删除好友
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteGroupFriendInfo", method = RequestMethod.POST)
    public Result deleteGroupFriendInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                staffGatewayService.deleteGroupFriendInfo(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("拒绝好友失败");
        }
        return result;
    }



    /**
     * 移动好友
     * @param params
     * @return
     */
    @RequestMapping(value = "/moveGroupFriendInfo", method = RequestMethod.POST)
    public Result moveGroupFriendInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            staffGatewayService.moveGroupFriendInfo(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("移动好友失败");
        }
        return result;
    }

    /**
     * 添加成员
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertNewStaff", method = RequestMethod.POST)
    public Result insertNewStaff(@RequestBody Map<String,Object> params){
        Result result = new Result();
        String string  = staffGatewayService.insertNewStaff(params);
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
     * 更新员工信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateStaffOfIdentity", method = RequestMethod.POST)
    public Result updateStaffOfIdentity(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            staffGatewayService.updateStaffOfIdentity(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更新员工信息失败");
        }
        return result;
    }



}
