package com.ovit.nswy.member.userManage.controller;

import com.ovit.nswy.member.certification.service.AppSettingsService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.userManage.service.UserAppSettingsService;
import com.ovit.nswy.member.userManage.service.UserPerfectBasicService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/perfect")
public class UserPerfectBasicController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserPerfectBasicService perfectBasicService;

    @Autowired
    private UserAppSettingsService appSettingsService;



    @RequestMapping(value = "/findModuleInfo", method = RequestMethod.POST)
    public Result findModuleInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> moduleList = perfectBasicService.findModuleInfo(params);
            result.setData(moduleList);
            result.setMsg("应用模块信息查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("应用模块信息查询失败");
        }
        return result;
    }

    /**
     * 更改完善信息模块的名称
     * params: account、appId、appName
     */
    @RequestMapping(value = "/modifyModule", method = RequestMethod.POST)
    public Result modifyModule(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            perfectBasicService.modifyModule(params);
            result.setMsg("名称更改成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更改完善信息失败");
        }
        return result;
    }


    @RequestMapping(value = "/saveYearInfo", method = RequestMethod.POST)
    public Result saveYearInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> fileList = perfectBasicService.findYearInfo(params);
            if (CollectionUtils.isNotEmpty(fileList)) {
                //文件名已存在
                result.setCode(300);
                result.setMsg("文件名已存在");

            } else {
                String id = UuidUtil.get32UUID();
                params.put("id", id);
                perfectBasicService.saveYearInfo(params);
                result.setData(id);
                result.setMsg("年度文件添加成功");
            }

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("年度文件添加失败");
        }
        return result;
    }

    @RequestMapping(value = "/findYearInfo", method = RequestMethod.POST)
    public Result findYearInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> yearList = perfectBasicService.findYearInfo(params);
            if (CollectionUtils.isEmpty(yearList)) {
                //查询数据为空，则需要生成去年、今年、明天这3个年度文件
                String sdf = new SimpleDateFormat("YYYY").format(new Date());
                String thisYear = sdf + "年度";
                Map<String,Object> thisYearMap = new HashMap<>();
                thisYearMap.put("fileName", thisYear);
                thisYearMap.put("id", UuidUtil.get32UUID());
                thisYearMap.put("account", MapUtils.getString(params, "account"));
                perfectBasicService.saveYearInfo(thisYearMap);

                String lastYear = (Integer.parseInt(sdf) - 1) + "年度";
                Map<String,Object> lastYearMap = new HashMap<>();
                lastYearMap.put("fileName", lastYear);
                lastYearMap.put("id", UuidUtil.get32UUID());
                lastYearMap.put("account", MapUtils.getString(params, "account"));
                perfectBasicService.saveYearInfo(lastYearMap);

                String nextYear = (Integer.parseInt(sdf) + 1) + "年度";
                Map<String,Object> nextYearMap = new HashMap<>();
                nextYearMap.put("fileName", nextYear);
                nextYearMap.put("id", UuidUtil.get32UUID());
                nextYearMap.put("account", MapUtils.getString(params, "account"));
                perfectBasicService.saveYearInfo(nextYearMap);

                yearList = perfectBasicService.findYearInfo(params);

            }
            result.setData(yearList);
            result.setMsg("年度文件查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("年度文件查询失败");
        }
        return result;
    }

    @RequestMapping(value = "/deleteYearInfo", method = RequestMethod.POST)
    public Result deleteYearInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            perfectBasicService.deleteYearInfo(params);
            result.setMsg("年度文件删除成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("年度文件删除失败");
        }
        return result;
    }

    /**
     * 完善信息模块初始化查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/initData", method = RequestMethod.POST)
    public Result initData(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> resultMap = new HashMap<>();
            List<Map<String,Object>> subModule = perfectBasicService.findSubModule(params);
            resultMap.put("subModule", subModule);
            //获取应用名称
            Map<String, Object> appMap = appSettingsService.findAppSetting(params);
            resultMap.put("moduleName", MapUtils.getString(appMap, "appName"));
            result.setMsg("模块数据初始化");
            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新或保存文字预览信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            perfectBasicService.saveTextPreview(params);
            result.setMsg("更新或保存文字预览信息成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改属性名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/updatePropertyInfo",method = RequestMethod.POST)
    public Result updatePropertyInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            perfectBasicService.updatePropertyInfo(params);
            result.setMsg("标题名称修改成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改属性名称（数组）
     * @param params
     * @return
     */
    @RequestMapping(value = "/updatePropertyStringInfo",method = RequestMethod.POST)
    public Result updatePropertyStringInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            perfectBasicService.updatePropertyStringInfo(params);
            result.setMsg("标题名称修改成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询文字预览全部
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAllTextPreviewList", method = RequestMethod.POST)
    public Result findAllTextPreviewList(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> moduleList = perfectBasicService.findAllTextPreviewList(params);
            result.setData(moduleList);
            result.setMsg("应用模块信息查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("应用模块信息查询失败");
        }
        return result;
    }


    /**
     * 文字预览保存/修改成功
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAllTextPreview",method = RequestMethod.POST)
    public Result saveAllTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            perfectBasicService.saveAllTextPreview(params);
            result.setMsg("文字预览保存/修改成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findTableHead", method = RequestMethod.POST)
    public Result findTableHead(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> head = perfectBasicService.findTableHead(params);
            result.setData(head);
            result.setMsg("模块数据初始化");


        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}
