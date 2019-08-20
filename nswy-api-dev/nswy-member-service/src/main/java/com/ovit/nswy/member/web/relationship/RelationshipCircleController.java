package com.ovit.nswy.member.web.relationship;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.model.request.GetUserQuery;
import com.ovit.nswy.member.model.response.UserDTO;
import com.ovit.nswy.member.service.RelationshipCircleService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relationshipCircle")
public class RelationshipCircleController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RelationshipCircleService relationshipCircleService;

    /**
     * 查询分组列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGroupList", method = RequestMethod.POST)
    public Result findGroupList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = relationshipCircleService.findGroupList(params);
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
            relationshipCircleService.saveOrUpdateGroup(params);
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
            relationshipCircleService.deleteGroup(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除分组失败");
        }
        return result;
    }

    /**
     * 关注控件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLoginByCircle", method = RequestMethod.POST)
    public Result findLoginByCircle(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = relationshipCircleService.findLoginByCircle(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注控件数据查询失败");
        }
        return result;
    }


    /**
     * 邀请
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertGroupFriendInfo", method = RequestMethod.POST)
    public Result insertGroupFriendInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("invite", MapUtils.getString(params,"invite"));
                map.put("inviteGroupId",MapUtils.getString(map,"groupId"));//邀请人
                map.put("friendAccount", MapUtils.getString(map,"account"));
                map.put("account",MapUtils.getString(params,"account"));
                map.put("beInvitedGroupId",MapUtils.getString(params,"groupId"));//被邀请人
                map.put("groupId",MapUtils.getString(params,"groupId"));
                relationshipCircleService.insertGroupFriendInfo(map);
            }

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
            PageList list = relationshipCircleService.findGroupFriendList(params);
            result.setData(list);
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
                relationshipCircleService.deleteGroupFriendInfo(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("拒绝好友失败");
        }
        return result;
    }


    /**
     * 拒绝好友
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFriendInfo", method = RequestMethod.POST)
    public Result deleteFriendInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            relationshipCircleService.deleteFriendInfo(params);

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
            relationshipCircleService.moveGroupFriendInfo(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("移动好友失败");
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
            relationshipCircleService.insertStaff(params);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注失败");
        }
        return result;
    }

    @PostMapping("/findGroupFriendOfIm")
    @ApiOperation(value = "im用户列表",response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response findGroupFriendOfIm(@RequestBody Map<String,Object> params) {
        List<Map<String,Object>> list = relationshipCircleService.findGroupFriendOfIm(params);
        return Response.successResult(list);
    }

}
