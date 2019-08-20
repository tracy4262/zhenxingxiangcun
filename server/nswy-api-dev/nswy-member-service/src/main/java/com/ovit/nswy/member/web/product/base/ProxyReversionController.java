package com.ovit.nswy.member.web.product.base;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.ProxyReversionService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/reversionProxy")
public class ProxyReversionController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ProxyReversionService proxyReversionService;

    /**
     * 代理列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/proxyList", method = RequestMethod.POST)
    public Result proxyList(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> proxyList = proxyReversionService.proxyList(params);
            logger.debug("proxyList: {}", proxyList);
            result.setData(proxyList);
            result.setMsg("代理列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询账户
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryAccount", method = RequestMethod.POST)
    public Result queryAccount(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> accountInfo = proxyReversionService.queryAccount(params);
            logger.debug("accountInfo: {}", accountInfo);
            result.setData(accountInfo);
            result.setMsg("账号查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询代理模板/取消代理模板
     * @param params
     * @return
     */
    @RequestMapping(value = "/proxyTemplate", method = RequestMethod.POST)
    public Result proxyTemplate(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> proxyTemplate = proxyReversionService.proxyTemplate(params);
            logger.debug("proxyTemplate: {}", proxyTemplate);
            result.setData(proxyTemplate);
            result.setMsg("查询模板成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 代理/取消代理
     * @param params
     * @return
     */
    @RequestMapping(value = "/proxyOrCancle", method = RequestMethod.POST)
    public Result proxyOrCancle(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            proxyReversionService.proxyOrCancle(params);
            result.setMsg("操作成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 暂不代理
     * @param params
     * @return
     */
    @RequestMapping(value = "/noProxy", method = RequestMethod.POST)
    public Result noProxy(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            proxyReversionService.noProxy(params);
            result.setMsg("操作成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询待审数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/waitProxy", method = RequestMethod.POST)
    public Result waitProxy(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> waitProxy = proxyReversionService.waitProxy(params);
            result.setData(waitProxy);
            result.setMsg("查询待审数据成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 我要代理
     * @param params
     * @return
     */
    @RequestMapping(value = "/toProxy", method = RequestMethod.POST)
    public Result toProxy(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            proxyReversionService.toProxy(params);
            result.setMsg("我要代理成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setMsg("服务器异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
