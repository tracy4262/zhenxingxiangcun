package com.ovit.nswy.member.web.follow;



import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.FollowManageService;
import com.ovit.nswy.member.service.SysFollowDictService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/followManage")
public class FollowManageController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FollowManageService followManageService;

    @Autowired
    private SysFollowDictService sysFollowDictService;



    /**
     * 查询物种左侧列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSepciesList", method = RequestMethod.POST)
    public Result findSepciesList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = followManageService.findSepciesList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注管理初始化失败");
        }
        return result;
    }

    /**
     * 查询列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSpecByAccount", method = RequestMethod.POST)
    public Result findSpecByAccount(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = followManageService.findSpecByAccount(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注管理初始化失败");
        }
        return result;
    }

    /**
     * 关注
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertFollow", method = RequestMethod.POST)
    public Result insertFollow(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("account",MapUtils.getString(params,"account"));
                map.put("templateId",MapUtils.getString(params,"templateId"));
                map.put("type",MapUtils.getString(params,"type"));
                followManageService.insertFollow(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注失败");
        }
        return result;
    }


    /**
     * 取消关注
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFollowInfo", method = RequestMethod.POST)
    public Result deleteFollowInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                followManageService.deleteFollowInfo(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("取消关注失败");
        }
        return result;
    }

    /**
     * 资讯，知识，政策左侧列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = followManageService.findList(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注管理列表table查询失败");
        }
        return result;
    }

    /**
     * 资讯，知识，政策控件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSysFollowDictInfo", method = RequestMethod.POST)
    public Result findSysFollowDictInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = sysFollowDictService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注管理列表数据查询失败");
        }
        return result;
    }


    /**
     * 会员关注控件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLoginByMember", method = RequestMethod.POST)
    public Result findLoginByMember(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = followManageService.findLoginByMember(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("会员关注控件数据查询失败");
        }
        return result;
    }


    /**
     * 会员我关注的/关注我的
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLoginByMemberList", method = RequestMethod.POST)
    public Result findLoginByMemberList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = followManageService.findLoginByMemberList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("会员我关注的/关注我的列表数据查询失败");
        }
        return result;
    }

    /**
     * 会员关注
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertFollowMemberInfo", method = RequestMethod.POST)
    public Result insertFollowMemberInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("followAccount",MapUtils.getString(map,"account"));
                map.put("account",MapUtils.getString(params,"account"));
                followManageService.insertFollowMemberInfo(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("关注失败");
        }
        return result;
    }


    /**
     * 会员取消关注
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFollowMemberInfo", method = RequestMethod.POST)
    public Result deleteFollowMemberInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                followManageService.deleteFollowMemberInfo(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("取消关注失败");
        }
        return result;
    }

}
