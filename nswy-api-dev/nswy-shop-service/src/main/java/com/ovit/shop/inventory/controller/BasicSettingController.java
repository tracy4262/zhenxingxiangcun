package com.ovit.shop.inventory.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.shop.inventory.mapper.BasicSettingMapper;
import com.ovit.shop.inventory.service.BasicSettingService;
import com.ovit.shop.test.service.TestService;
import com.ovit.shop.util.ConvertAmountUtil;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存管理-基础设置
 */
@RestController
@RequestMapping("/inventory/basicSetting")
public class BasicSettingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BasicSettingService basicSettingService;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    /**
     * 新增或编辑自定义分类
     * @param params
     * @return
     */
    @RequestMapping(value = "/customSave", method = RequestMethod.POST)
    public Result customSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.customFind(params);
            if (CollectionUtils.isNotEmpty(list)) {
                result.setMsg("此分类名称已存在，请修改");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.customSave(params);
                result.setMsg("新增成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 自定义分类列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/customFind", method = RequestMethod.POST)
    public Result customFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.customFind(params);
            if (CollectionUtils.isEmpty(list)) {
                list = new ArrayList<>();
            }
            if (StringUtils.isBlank(MapUtils.getString(params, "key")) && !params.containsKey("productClassify")) {
                Map<String,Object> map = new HashMap<>();
                map.put("customName", "全部商品信息");
                map.put("id", "");
                list.add(0, map);
            }
            result.setData(list);
            result.setMsg("查询自定义分类数据成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 新增或编辑产品
     * @param params
     * @return
     */
    @RequestMapping(value = "/productSave", method = RequestMethod.POST)
    public Result productSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            //查看新增的产品编码是否存在
            List<Map<String, Object>> list = basicSettingService.productCodeIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //证明产品编码已存在，请更换
                result.setMsg("产品编码已存在，请更换");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.productSave(params);
                result.setMsg("保存商品成功");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 根据产品分类获得产品分类名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/getProductName", method = RequestMethod.POST)
    public Result getProductName(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> productName = basicSettingService.getProductName(params);
            result.setData(productName);
            result.setMsg("根据产品分类获得名称查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询商品列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/productFind", method = RequestMethod.POST)
    public Result productFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> product = basicSettingService.productFind(params);
            result.setData(product);
            result.setMsg("商品信息查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/productList", method = RequestMethod.POST)
    public Result productList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> product = basicSettingService.productList(params);
            result.setData(product);
            result.setMsg("商品信息查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/customDelete",method = RequestMethod.POST)
    public Result customDelete(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.customIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //说明该自定义分类下有商品，不能进行删除
                result.setMsg("该自定义分类有商品，不能进行删除");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.customDelete(params);
                result.setMsg("自定义分类删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/productDelete", method = RequestMethod.POST)
    public Result productDelete(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.productIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //说明该商品有出入库记录，不能进行删除
                result.setMsg("该商品有出入库记录，不能进行删除");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.productDelete(params);
                result.setMsg("商品信息删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /*@RequestMapping(value = "/getStoreOrder", method = RequestMethod.GET)
    public Result getStoreOrder(@RequestParam String account) {
        Result result = null;
        try {
            result = new Result();
            Integer order = basicSettingService.getStoreOrder(account);
            result.setData(order);
            result.setMsg("新增仓库时序号查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }*/

    /**
     * 新增或编辑仓库
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeSave", method = RequestMethod.POST)
    public Result storeSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingMapper.storeFind(params);
            if (CollectionUtils.isNotEmpty(list)) {
                result.setMsg("此仓库名称已存在，请修改");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.storeSave(params);
                result.setMsg("新增仓库成功");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询仓库列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeFind", method = RequestMethod.POST)
    public Result storeFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> store = basicSettingService.storeFind(params);
            result.setData(store);
            result.setMsg("仓库信息查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除仓库
     * @param id
     * @return
     */
    @RequestMapping(value = "/storeDelete", method = RequestMethod.POST)
    public Result storeDelete(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.storeIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //说明该仓库已被使用，不能进行删除
                result.setMsg("该仓库已被使用，不能进行删除");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.storeDelete(params);
                result.setMsg("仓库删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 保存单据设置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/billSave", method = RequestMethod.POST)
    public Result billSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            basicSettingService.billSave(params);
            result.setMsg("单据设置保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询单据设置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/billFind", method = RequestMethod.POST)
    public Result billFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> store = basicSettingService.billFind(params);
            result.setData(store);
            result.setMsg("单据设置查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /*@RequestMapping(value = "/getInStoreOrder", method = RequestMethod.GET)
    public Result getInStoreOrder(@RequestParam String account) {
        Result result = null;
        try {
            result = new Result();
            Integer order = basicSettingService.getInStoreOrder(account);
            result.setData(order);
            result.setMsg("新增入库设置时序号查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }*/

    /**
     * 新增或编辑入库类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/inStoreSave", method = RequestMethod.POST)
    public Result inStoreSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingMapper.inStoreFind(params);
            if (CollectionUtils.isNotEmpty(list)) {
                result.setMsg("此入库类型已存在，请修改");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.inStoreSave(params);
                result.setMsg("入库类型设置保存成功");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询入库类型列表,分页
     * @param params
     * @return
     */
    @RequestMapping(value = "/inStoreFind", method = RequestMethod.POST)
    public Result inStoreFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> inStore = basicSettingService.inStoreFind(params);
            result.setData(inStore);
            result.setMsg("入库类型设置查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询入库类型列表, 不分页
     * @param params
     * @return
     */
    @RequestMapping(value = "/inStoreList", method = RequestMethod.POST)
    public Result inStoreList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> inStore = basicSettingService.inStoreList(params);
            result.setData(inStore);
            result.setMsg("入库类型列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除入库类型
     * @param id
     * @return
     */
    @RequestMapping(value = "/inStoreDelete", method = RequestMethod.POST)
    public Result inStoreDelete(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.inStoreIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //说明该入库类型已被使用，不能进行删除
                result.setMsg("该入库类型已被使用，不能进行删除");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.inStoreDelete(params);
                result.setMsg("入库设置信息删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /*@RequestMapping(value = "/getOutStoreOrder", method = RequestMethod.GET)
    public Result getOutStoreOrder(@RequestParam String account) {
        Result result = null;
        try {
            result = new Result();
            Integer order = basicSettingService.getOutStoreOrder(account);
            result.setData(order);
            result.setMsg("新增出库设置时序号查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }*/

    /**
     * 新增或编辑出库类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/outStoreSave", method = RequestMethod.POST)
    public Result outStoreSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingMapper.outStoreFind(params);
            if (CollectionUtils.isNotEmpty(list)) {
                result.setMsg("此出库类型已存在，请修改");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.outStoreSave(params);
                result.setMsg("出库类型设置保存成功");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询出库类型列表, 分页
     * @param params
     * @return
     */
    @RequestMapping(value = "/outStoreFind", method = RequestMethod.POST)
    public Result outStoreFind(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> inStore = basicSettingService.outStoreFind(params);
            result.setData(inStore);
            result.setMsg("出库类型设置查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询出库类型列表, 不分页
     * @param params
     * @return
     */
    @RequestMapping(value = "/outStoreList", method = RequestMethod.POST)
    public Result outStoreList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> inStore = basicSettingService.outStoreList(params);
            result.setData(inStore);
            result.setMsg("出库类型列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除出库类型
     * @param id
     * @return
     */
    @RequestMapping(value = "/outStoreDelete", method = RequestMethod.POST)
    public Result outStoreDelete(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.outStoreIsUse(params);
            if (CollectionUtils.isNotEmpty(list)) {
                //说明该出库类型已被使用，不能进行删除
                result.setMsg("该出库类型已被使用，不能进行删除");
                result.setCode(ResultCode.FAIL);
            } else {
                basicSettingService.outStoreDelete(params);
                result.setMsg("出库设置信息删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询用户的产品编码列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/productCodeList", method = RequestMethod.POST)
    public Result productCodeList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> productCodeList = basicSettingService.productCodeList(params);
            result.setData(productCodeList);
            result.setMsg("产品编码列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 入库操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/enterStoreSave", method = RequestMethod.POST)
    public Result enterStoreSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            //查看用户是否需要入库单
            String order = "";
            Map<String, Object> billMap = basicSettingService.billFind(params);
            if (MapUtils.isNotEmpty(billMap) && MapUtils.getInteger(billMap, "inBill") == 1) {
                //获取入库单号
                order = basicSettingService.generateOrder(1);
            }
            params.put("enterOrder", order);
            basicSettingService.enterStoreSave(params);
            result.setData(order);
            result.setMsg("入库成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询库存列表信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeList", method = RequestMethod.POST)
    public Result storeList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> storeList = basicSettingService.storeList(params);
            result.setData(storeList);
            result.setMsg("库存列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 新增或编辑库存预警设置
     * @param params
     * @return
     */
    @RequestMapping(value = "/warningSave", method = RequestMethod.POST)
    public Result warningSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            basicSettingService.warningSave(params);
            result.setMsg("库存预警设置成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 出库操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/exitStoreSave", method = RequestMethod.POST)
    public Result exitStoreSave(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            //查看用户是否需要出库单
            String order = "";
            Map<String, Object> billMap = basicSettingService.billFind(params);
            if (MapUtils.isNotEmpty(billMap) && MapUtils.getInteger(billMap, "outBill") == 1) {
                //获取出库单号
                order = basicSettingService.generateOrder(2);
            }
            params.put("exitOrder", order);
            basicSettingService.exitStoreSave(params);
            result.setData(order);
            result.setMsg("出库成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询入库记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/enterRecord", method = RequestMethod.POST)
    public Result enterRecord(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> enterRecord = basicSettingService.enterRecord(params);
            result.setData(enterRecord);
            result.setMsg("入库记录查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询出库记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/exitRecord", method = RequestMethod.POST)
    public Result exitRecord(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> enterRecord = basicSettingService.exitRecord(params);
            result.setData(enterRecord);
            result.setMsg("出库记录查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 数字金额转换为大写金额
     * @param amount
     * @return
     */
    @RequestMapping(value = "/convertAmount", method = RequestMethod.GET)
    public Result convertAmount(@RequestParam Double amount) {
        Result result = null;
        try {
            result = new Result();
            if (Double.isNaN(amount)) {
                result.setCode(ResultCode.FAIL);
                result.setMsg("传参不正确");
            } else {
                String upperAmount = ConvertAmountUtil.digitUppercase(amount);
                result.setData(upperAmount);
                result.setMsg("数字金额转大写金额成功");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 入库单据查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/enterOrder", method = RequestMethod.POST)
    public Result enterOrder(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> enterOrder = basicSettingService.enterOrder(params);
            result.setData(enterOrder);
            result.setMsg("入库单据查询查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 出库单据查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/exitOrder", method = RequestMethod.POST)
    public Result exitOrder(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> enterOrder = basicSettingService.exitOrder(params);
            result.setData(enterOrder);
            result.setMsg("出库单据查询查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询库存列表信息,将同一产品的数据整合展示
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeCombineList", method = RequestMethod.POST)
    public Result storeCombineList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> storeCombineList = basicSettingService.storeCombineList(params);
            result.setData(storeCombineList);
            result.setMsg("库存整合列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 调拨库存
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeTransfer", method = RequestMethod.POST)
    public Result storeTransfer(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            if (params.get("list") instanceof List) {
                basicSettingService.storeTransfer(params);
                result.setMsg("库存调拨成功");
            } else {
                logger.info("数据格式:{}, 不正确, 调拨失败", params);
                result.setMsg("库存调拨失败");
                result.setCode(ResultCode.FAIL);
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 库存详情里面的出入库类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeTypeList", method = RequestMethod.POST)
    public Result storeTypeList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            List<Map<String, Object>> list = basicSettingService.storeTypeList(params);
            result.setData(list);
            result.setMsg("出入库类型查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查看某产品的库存详情:库存流水明细列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeDetail", method = RequestMethod.POST)
    public Result storeDetail(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> storeDetail = basicSettingService.storeDetail(params);
            result.setData(storeDetail);
            result.setMsg("库存流水明细查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查看某产品的库存详情:库存流水明细上方的产品与库存量算法数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/productStoreDetail", method = RequestMethod.POST)
    public Result productStoreDetail(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> productStoreDetail = basicSettingService.productStoreDetail(params);
            result.setData(productStoreDetail);
            result.setMsg("产品与库存量算法数据查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 拆装库存 -- 拆分产品
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeSplit", method = RequestMethod.POST)
    public Result storeSplit(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            if (params.get("list") instanceof List) {
                basicSettingService.storeSplit(params);
                result.setMsg("拆分产品成功");
            } else {
                logger.info("数据格式:{}, 不正确, 拆分失败", params);
                result.setMsg("拆分产品失败");
                result.setCode(ResultCode.FAIL);
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 拆装库存 -- 合并产品
     * @param params
     * @return
     */
    @RequestMapping(value = "/storeMerge", method = RequestMethod.POST)
    public Result storeMerge(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            if (params.get("list") instanceof List) {
                basicSettingService.storeMerge(params);
                result.setMsg("合并产品成功");
            } else {
                logger.info("数据格式:{}, 不正确, 合并失败", params);
                result.setMsg("合并产品失败");
                result.setCode(ResultCode.FAIL);
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询库存拆装库存--合并列表信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/mergeList", method = RequestMethod.POST)
    public Result mergeList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> storeList = basicSettingService.mergeList(params);
            result.setData(storeList);
            result.setMsg("合并列表信息查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
