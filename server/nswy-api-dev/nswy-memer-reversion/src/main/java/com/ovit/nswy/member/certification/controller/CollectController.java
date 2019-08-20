package com.ovit.nswy.member.certification.controller;

import com.ovit.nswy.member.certification.service.CollectService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 查询分组列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCollectList", method = RequestMethod.POST)
    public Result findCollectList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = collectService.findCollectList(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("收藏分组初始化失败");
        }
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result query(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        params.put("login_account", params.get("account").toString());
        List<Map<String, Object>> list = collectService.queryList(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/queryNext", method = RequestMethod.POST)
    public Result queryNext(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        List<Map<String, Object>> list = collectService.queryNext(params);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public Result rename(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        collectService.rename(params);
        return result;
    }

    @RequestMapping(value = "/renameRemark", method = RequestMethod.POST)
    public Result renameRemark(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        collectService.renameRemark(params);
        return result;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody Map<String, Object> params) {
        logger.info("param->{}",params);
        Result result = new Result();
        List<Map<String, Object>> existData = collectService.isExistData(params);
        if (CollectionUtils.isNotEmpty(existData)) {
            result.setMsg("该收藏夹夹下有收藏数据");
            result.setCode(ResultCode.FAIL);
            return result;
        }
        boolean res = collectService.del(params);
        result.setData(res);
        result.setCode(ResultCode.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
//		String account = loginUser.getLoginAccount();
        params.put("login_account", params.get("account").toString());
        collectService.add(params);
        return result;
    }



    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    public Result queryAll(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String account = params.get("account").toString();
            if (StringUtils.isNotBlank(account)) {
                Map<String, Object> map = collectService.queryAll(params);
                result.setCode(ResultCode.SUCCESS);
                result.setData(map);
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("用户名不能为空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }

        return result;
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(value = "/addCollect",method = RequestMethod.POST)
    public Result addCollect(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            if (collectService.isExist(params)) {
                result.setCode(ResultCode.FAIL);
                result.setData("exist");
            } else {
                collectService.add(params);
                result.setData(params.get("id"));
                result.setCode(ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/user/addCollect", method = RequestMethod.POST)
    public Result addUser(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            if (collectService.isExist(params)) {
                result.setCode(ResultCode.FAIL);
                result.setData("exist");
            } else {
                collectService.addUser(params);
                result.setData(params.get("id"));
                result.setCode(ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 更新
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCollect",method = RequestMethod.POST)
    public Result updateCollect(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            collectService.updata(params);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }
    @RequestMapping(value = "/findByName",method = RequestMethod.POST)
    public Result findByName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list =collectService.findByName(params);
            result.setData(list);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 收藏管理-> 收藏内容管理-> 选择收藏夹
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryFavorite", method = RequestMethod.POST)
    public Result queryFavorite(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            if (StringUtils.isNotBlank(account)) {
                List<Map<String, Object>> list = collectService.queryFavorite(params);
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("收藏夹查询成功");
                result.setData(list);
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("用户名不能为空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }

        return result;
    }
}