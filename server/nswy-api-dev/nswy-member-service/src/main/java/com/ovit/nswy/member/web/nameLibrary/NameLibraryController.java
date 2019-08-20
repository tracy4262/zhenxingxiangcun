package com.ovit.nswy.member.web.nameLibrary;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.NameLibraryService;
import com.ovit.nswy.member.service.StaffGatewayService;
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
@RequestMapping("/nameLibrary")
public class NameLibraryController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private NameLibraryService libraryService;

    /**
     * 查询名称库列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = libraryService.findList(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("名称库收藏查询失败");
        }
        return result;
    }

    /**
     * 品种/病害/虫害控件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findVarietyList", method = RequestMethod.POST)
    public Result findVarietyList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageList<Map<String,Object>> list = libraryService.findVarietyList(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("品种/病害/虫害查询失败");
        }
        return result;
    }

    /**
     * 通用商品名/通用服务名控件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findShopCurrencyCommodity", method = RequestMethod.POST)
    public Result findShopCurrencyCommodity(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageList<Map<String,Object>> list = libraryService.findShopCurrencyCommodity(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("通用商品名查询失败");
        }
        return result;
    }





    /**
     * 新增物种
     * @param params
     * @return
     */

    @RequestMapping(value = "/saveLibrary", method = RequestMethod.POST)
    public Result saveLibrary(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("account",MapUtils.getString(params,"account"));
                map.put("type",MapUtils.getString(params,"type"));
                libraryService.saveLibrary(map);
            }

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增/修改分组失败");
        }
        return result;
    }


/**
     * 删除收藏
     * @param params
     * @return
     */

    @RequestMapping(value = "/deleteLibrary", method = RequestMethod.POST)
    public Result deleteLibrary(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("type",MapUtils.getString(params,"type"));
                libraryService.deleteLibrary(map);
            }
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除分组失败");
        }
        return result;
    }







}
