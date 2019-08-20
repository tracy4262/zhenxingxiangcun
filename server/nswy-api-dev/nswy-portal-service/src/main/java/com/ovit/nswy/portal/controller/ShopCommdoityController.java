package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.*;
import com.ovit.nswy.portal.util.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/shopCommdoity")
public class ShopCommdoityController {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ShopCommodityTypeInfoService shopCommodityTypeInfoService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private UpCommodityBasicInfoService upCommodityBasicInfoService;

    @Autowired
    private UpMarketingBasicInfoService upMarketingBasicInfoService;

    @Autowired
    private ShopCommodityListService shopCommodityListService;

    @Autowired
    private RedisTemplate<String, List<Map<String,Object>>> ecoRedis;

    private static Cache<String, List<Map<String,Object>>> cache;

    @PostConstruct
    public void init() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, List<Map<String,Object>>> config = new MutableConfiguration();
        config.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.ONE_DAY))
                .setStatisticsEnabled(false);
        cache = cacheManager.createCache("mallClassification", config);
    }

    /**
     * 查询商品首页图片
     * @param params
     * @return
     */
    @RequestMapping("/findCommodityImage")
    public Result findCommodityImage(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> commodityImage = shopCommodityTypeInfoService.findCommodityImage(params);
            result.setData(commodityImage);
        }catch (Exception e){
            result.setCode(300);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    /**
     * 查询商品分类信息
     * @param params
     * @return
     */
    @RequestMapping("/findCommodityInfo")
    public Result findCommodityInfo(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> commodity = shopCommodityTypeInfoService.findCommodityInfo(params);
            result.setData(commodity);
        }catch (Exception e){
            result.setCode(300);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    /**
     * 插入或更新商品分类信息
     * @param params
     * @return
     */
    @RequestMapping("/insertCommodityInfo")
    public Result insertCommodityInfo(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
           String id = shopCommodityTypeInfoService.insertCommodityInfo(params);
            result.setData(id);
        }catch (Exception e){
            result.setCode(300);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询农药残留标准
     */
    @RequestMapping(value = "/findResidueStandard", method = RequestMethod.POST)
    public Result findResidueStandard(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String,Object>> standardList = standardService.findResidueStandard(params);
            if (standardList.size() > 0) {
                result.setData(standardList);
                result.setMsg("查询成功");
            } else {
                result.setMsg("查询无数据");
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询农药残留标准失败");
        }
        logger.info("查询农药残留标准为： {}", result.getData());
        return result;
    }

    /**
     * 查询单位信息
     */
    @RequestMapping(value = "/findUnitStandard/{parentId}", method = RequestMethod.POST)
    public Result findUnitStandard(@PathVariable String parentId) {
        Result result = new Result();
        try {

            List<Map<String,Object>> unitList = standardService.findUnitStandard(parentId);
            if (unitList.size() > 0) {
                result.setData(unitList);
                result.setMsg("查询成功");
            } else {
                result.setMsg("查询无数据");
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询单位信息失败");
        }
        logger.info("查询单位信息准为： {}", result.getData());
        return result;
    }

    /**
     * 查询单位分类表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBasicUnit", method = RequestMethod.POST)
    public Result findEcoClassify(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.info("查询单位分类表,参数：{}",params);
            String keywords = MapUtils.getString(params,"keywords");
            String fclassifiedid = MapUtils.getString(params,"fclassifiedid");
            int pageNum = MapUtils.getInteger(params,"pageNum");
            int pageSize = MapUtils.getInteger(params,"pageSize");

            params.remove("pageNum");
            params.remove("pageSize");

            List<String> queryList = JSON.parseArray(fclassifiedid).toJavaList(String.class);
            List<Map<String,Object>> resultList = new ArrayList<>();

            String redisKey = "unitClass";
            resultList = ecoRedis.opsForValue().get(redisKey);
            params.put("ids", queryList);

            resultList = standardService.findUnit(params);

            int totalSize = 0;
            if (CollectionUtils.isNotEmpty(resultList)) {
                totalSize = resultList.size();
            }
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
            List<Map<String,Object>> list = PageUtils.pageForList(pageNum,pageSize,resultList);
            pageInfo.setList(list);
            pageInfo.setTotal(totalSize);
            pageInfo.setPageSize(pageSize);
            pageInfo.setPageNum(pageNum);
            result.setData(pageInfo);
            result.setMsg("单位分类数据查询成功");
        } catch (Exception e) {
            logger.error("查询单位分类数据失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询单位分类数据失败");
        }
        return result;
    }


    /**
     * 查询产品分类数据
     */
    @RequestMapping(value = "/findProductClassification", method = RequestMethod.POST)
    public Result findProductClassification(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String,Object>> productList = standardService.findProductClassification(params);

            List<Map<String, Object>> productResult = PageUtils.pageForList(MapUtils.getInteger(params, "num"), MapUtils.getInteger(params, "size"), productList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", productResult);
            pageResult.put("total", productList.size());
            result.setData(pageResult);
            result.setMsg("查询成功,条数为：" + productList.size());

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询产品分类数据失败");
        }
        logger.info("查询产品分类数据为： {}", result.getData());
        return result;
    }

    /**
     * 查询商城首页九大分类数据
     */
    @RequestMapping(value = "/findMallClassification", method = RequestMethod.GET)
    public Result findMallClassification() {
        Result result = new Result();
        try {
            List<Map<String,Object>> productList = cache.get("mallClassification");
            if (productList == null) {
                logger.info("九大分类数据无缓存");
                productList = standardService.findMallClassification();
                cache.put("mallClassification", productList);
            }
            result.setData(productList);
            result.setMsg("查询九大分类数据成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询九大分类数据失败");
        }
        return result;
    }

    /**
     * 查询通用商品名称库
     */
    @RequestMapping(value = "/findCurrencyCommodity", method = RequestMethod.POST)
    public Result findCurrencyCommodity(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //在发布商品第一步选择通用商品名时，只能查看审核通过的通用商品
            params.put("aduitStatus", "1");
            //type:1,查通用商品， type:2，查通用服务
            params.put("type", 1);
            List<Map<String,Object>> commodityList = standardService.findCurrencyCommodity(params);
            for (Map<String, Object> commodity : commodityList) {
                String productCode = MapUtils.getString(commodity, "productCode");
                if (StringUtils.isNotBlank(productCode)) {
                    Map<String, Object> productMap = new HashMap<>();
                    productMap.put("productCode", productCode);
                    List<Map<String, Object>> classificationInfo = standardService.findProductClassification(productMap);
                    if (classificationInfo.size() > 0) {
                        //添加产品分类名称
                        commodity.put("productName", MapUtils.getString(classificationInfo.get(0), "className"));
                    } else {
                        commodity.put("productName", "");
                    }

                } else {
                    commodity.put("productCode", "");
                    commodity.put("productName", "");
                }

                String speciesId = MapUtils.getString(commodity, "relatedspecies");
                if (StringUtils.isNotBlank(speciesId)) {
                    //根据物种id获取物种名称
                    String speciesName = shopCommodityTypeInfoService.getSpecies(speciesId);
                    commodity.put("relatedspeciesName", speciesName);
                } else {
                    commodity.put("relatedspecies", "");
                    commodity.put("relatedspeciesName", "");
                }

                //自定义表单之商品质量信息
                if (StringUtils.isNotBlank(MapUtils.getString(commodity, "qualityCustomContent"))) {
                    JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(commodity, "qualityCustomContent"));
                    commodity.put("qualityCustomContent", qualityArray);
                } else {
                    commodity.put("qualityCustomContent", new String[0]);
                }
                //自定义表单之商品安全标准
                if (StringUtils.isNotBlank(MapUtils.getString(commodity, "safetyCustomContent"))) {
                    JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(commodity, "safetyCustomContent"));
                    commodity.put("safetyCustomContent", safetyArray);
                } else {
                    commodity.put("safetyCustomContent", new String[0]);
                }
            }
            if (commodityList.size() > 0) {
                result.setData(commodityList);
                result.setMsg("查询成功,条数为：" + commodityList.size());
            } else {
                result.setCode(300);
                result.setMsg("查询无数据");
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询通用商品名称库失败");
        }
        logger.info("查询通用商品名称库为： {}", result.getData());
        return result;
    }

    /**
     * 上传商品基本信息
     */
    @RequestMapping(value = "/upCommodityBasicInfo", method = RequestMethod.POST)
    public Result upCommodityBasicInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            String commodityId = MapUtils.getString(params, "commodityId");
            Map<String, Object> queryInfo = new HashMap<>();
            queryInfo.put("account", account);
            queryInfo.put("commodityId", commodityId);
            //保存商品信息
            Map<String, Object> commodityInfo = (Map<String, Object>) params.get("product");
            commodityInfo.putAll(queryInfo);
            Map<String, Object> hisCommodityInfo = upCommodityBasicInfoService.getCommodityInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisCommodityInfo)) {
                commodityInfo.put("id", MapUtils.getInteger(hisCommodityInfo, "id"));
                commodityInfo.put("createTime", hisCommodityInfo.get("create_time"));
            } else {
                commodityInfo.put("createTime", new Date());
            }
            upCommodityBasicInfoService.insertCommodityInfo(commodityInfo);

            //保存商品生产信息
            Map<String, Object> productionInfo = (Map<String, Object>) params.get("Production");
            productionInfo.putAll(queryInfo);
            Map<String, Object> hisProductionInfo = upCommodityBasicInfoService.getProductionInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisProductionInfo)) {
                productionInfo.put("id", MapUtils.getInteger(hisProductionInfo, "id"));
                productionInfo.put("createTime", hisProductionInfo.get("create_time"));
            } else {
                productionInfo.put("createTime", new Date());
            }
            upCommodityBasicInfoService.insertProductionInfo(productionInfo);

            //保存商品产地信息
            Map<String, Object> placeInfo = (Map<String, Object>) params.get("origin");
            placeInfo.putAll(queryInfo);
            Map<String, Object> hisPlaceInfo = upCommodityBasicInfoService.getProductionInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisPlaceInfo)) {
                placeInfo.put("id", MapUtils.getInteger(hisPlaceInfo, "id"));
                placeInfo.put("createTime", hisPlaceInfo.get("create_time"));
            } else {
                placeInfo.put("createTime", new Date());
            }
            upCommodityBasicInfoService.insertPlaceInfo(placeInfo);

            //保存商品资质信息
            Map<String, Object> qualificationInfo = (Map<String, Object>) params.get("qualification");
            qualificationInfo.putAll(queryInfo);
            Map<String, Object> hisQualificationInfo = upCommodityBasicInfoService.getQualificationInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisQualificationInfo)) {
                qualificationInfo.put("id", MapUtils.getInteger(hisQualificationInfo, "id"));
                qualificationInfo.put("createTime", hisQualificationInfo.get("create_time"));
            } else {
                qualificationInfo.put("createTime", new Date());
            }
            upCommodityBasicInfoService.insertQualificationInfo(qualificationInfo);

            //保存商品荣誉信息
            Map<String, Object> honorInfo = (Map<String, Object>) params.get("honor");
            honorInfo.putAll(queryInfo);
            Map<String, Object> hisHonorInfo = upCommodityBasicInfoService.getHonorInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisHonorInfo)) {
                honorInfo.put("id", MapUtils.getInteger(hisHonorInfo, "id"));
                honorInfo.put("createTime", hisHonorInfo.get("create_time"));
            } else {
                honorInfo.put("createTime", new Date());
            }

            upCommodityBasicInfoService.insertHonorInfo(honorInfo);

            //保存自定义表单之商品质量信息
            Map<String, Object> commodityQuality = new HashMap<>();
            String qualityInfo = JSON.toJSONString(params.get("qualityFormData"));
            Map<String, Object> qualityInformation = (Map<String, Object>) params.get("qualityInformation");
            qualityInformation.putAll(queryInfo);
            qualityInformation.put("customContent", qualityInfo);
            qualityInformation.put("type", "0");
            upCommodityBasicInfoService.insertCustomContentInfo(qualityInformation);

            //保存自定义表单之商品安全标准
            Map<String, Object> commoditySafety = new HashMap<>();
            String safetyInfo = JSON.toJSONString(params.get("safeFormData"));
            Map<String, Object> securityInformation = (Map<String, Object>) params.get("securityInformation");
            securityInformation.putAll(queryInfo);
            securityInformation.put("customContent", safetyInfo);
            securityInformation.put("type", "1");
            upCommodityBasicInfoService.insertCustomContentInfo(securityInformation);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("上传商品基本信息失败");
        }
        return result;
    }

    /**
     * 获取商品基本信息
     */
    @RequestMapping(value = "/getCommodityBasicInfo", method = RequestMethod.POST)
    public Result getCommodityBasicInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            String commodityId = MapUtils.getString(params, "commodityId");

            Map<String, Object> queryInfo = new HashMap<>();
            queryInfo.put("account", account);
            queryInfo.put("commodityId", commodityId);
            params.put("id", commodityId);

            Map<String, Object> resultMap = new HashMap<>();

            //查询商品信息
            Map<String, Object> commodityResult = upCommodityBasicInfoService.getCommodityInfo(queryInfo);
            if(StringUtil.isEmpty(MapUtils.getString(commodityResult,"productName"))){
                List<Map<String,Object>> commodity = shopCommodityTypeInfoService.findCommodityInfo(params);
                Map<String,Object> commodityMap =  commodity.get(0);
                String productName = MapUtils.getString(commodityMap,"breedName") + MapUtils.getString(commodityMap,"commonProductName");
                if(commodityResult==null){
                    commodityResult = new HashMap<>();
                    commodityResult.put("productName",productName);
                }
            }
            resultMap.put("product", commodityResult);

            //查询商品生产信息
            Map<String, Object> productionResult = upCommodityBasicInfoService.getProductionInfo(queryInfo);
            resultMap.put("Production", productionResult);

            //查询商品产地信息
            Map<String, Object> placeResult = upCommodityBasicInfoService.getPlaceInfo(queryInfo);
            resultMap.put("origin", placeResult);

            //查询商品资质信息
            Map<String, Object> qualificationResult = upCommodityBasicInfoService.getQualificationInfo(queryInfo);
            resultMap.put("qualification", qualificationResult);

            //查询商品荣誉信息
            Map<String, Object> honorResult = upCommodityBasicInfoService.getHonorInfo(queryInfo);
            resultMap.put("honor", honorResult);

            Map<String, Object> qualityResult = upCommodityBasicInfoService.getCustomContentInfo(queryInfo);
            //查询自定义表单之商品质量信息
            if (StringUtils.isNotBlank(MapUtils.getString(qualityResult, "qualityCustomContent"))) {
                JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(qualityResult, "qualityCustomContent"));
                resultMap.put("qualityFormData", qualityArray);
            } else {
                resultMap.put("qualityFormData", new String[0]);
            }
            //查询自定义表单之商品安全标准
            if (StringUtils.isNotBlank(MapUtils.getString(qualityResult, "safetyCustomContent"))) {
                JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(qualityResult, "safetyCustomContent"));
                resultMap.put("safeFormData", safetyArray);
            } else {
                resultMap.put("safeFormData", new String[0]);
            }

            queryInfo.put("type","0");
            Map<String, Object> safetyMap = upCommodityBasicInfoService.findSafety(queryInfo);
            //查询商品质量信息
            resultMap.put("qualityInformation", safetyMap);

            queryInfo.put("type","1");
            Map<String, Object> safety = upCommodityBasicInfoService.findSafety(queryInfo);
            //查询商品安全标准
            resultMap.put("securityInformation", safety);


            result.setData(resultMap);

            /*Map<String, Object> qualityMap = new HashMap<>();
            qualityMap.putAll(queryInfo);
            qualityMap.put("type", "0");
            Map<String, Object> qualityResult = upCommodityBasicInfoService.getCustomContentInfo(qualityMap);
            if (StringUtils.isNotBlank(MapUtils.getString(qualityResult, "custom_content"))) {
                JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(qualityResult, "custom_content"));
                resultMap.put("qualityFormData", qualityArray);
            } else {
                resultMap.put("qualityFormData", new String[0]);
            }*/

            //查询自定义表单之商品安全标准
            /*Map<String, Object> safetyMap = new HashMap<>();
            safetyMap.putAll(queryInfo);
            safetyMap.put("type", "1");
            Map<String, Object> safetyResult = upCommodityBasicInfoService.getCustomContentInfo(safetyMap);
            if (StringUtils.isNotBlank(MapUtils.getString(safetyResult, "custom_content"))) {
                JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(safetyResult, "custom_content"));
                resultMap.put("safeFormData", safetyArray);
            } else {
                resultMap.put("safeFormData", new String[0]);
            }*/

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品基本信息失败");
        }
        logger.info("查询商品基本信息为： {}", result.getData());
        return result;
    }

    /**
     * 上传商品营销基本信息
     */
    @RequestMapping(value = "/upMarketingBasicInfo", method = RequestMethod.POST)
    public Result upMarketingBasicInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            String commodityId = MapUtils.getString(params, "commodityId");
            Map<String, Object> queryInfo = new HashMap<>();
            queryInfo.put("account", account);
            queryInfo.put("commodityId", commodityId);

            //保存商品销售信息
            Map<String, Object> marketingInfo = (Map<String, Object>) params.get("sales");
            marketingInfo.putAll(queryInfo);
            Map<String, Object> hisMarketingInfo = upMarketingBasicInfoService.getCommodityMarketingInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisMarketingInfo)) {
                marketingInfo.put("id", MapUtils.getInteger(hisMarketingInfo, "id"));
                marketingInfo.put("createTime", hisMarketingInfo.get("create_time"));
            } else {
                marketingInfo.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertCommodityMarketingInfo(marketingInfo);

            //保存商品质保信息
            Map<String, Object> assuranceInfo = (Map<String, Object>) params.get("warranty");
            assuranceInfo.putAll(queryInfo);
            Map<String, Object> hisAssuranceInfo = upMarketingBasicInfoService.getAssuranceInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisAssuranceInfo)) {
                assuranceInfo.put("id", MapUtils.getInteger(hisAssuranceInfo, "id"));
                assuranceInfo.put("createTime", hisAssuranceInfo.get("create_time"));
            } else {
                assuranceInfo.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertAssuranceInfo(assuranceInfo);

            //保存商品定价信息
            Map<String, Object> pricingInfo = (Map<String, Object>) params.get("pricing");
            pricingInfo.putAll(queryInfo);
            Map<String, Object> hisPricingInfo = upMarketingBasicInfoService.getPricingInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisPricingInfo)) {
                pricingInfo.put("id", MapUtils.getInteger(hisPricingInfo, "id"));
                pricingInfo.put("createTime", hisPricingInfo.get("create_time"));
            } else {
                pricingInfo.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertPricingInfo(pricingInfo);

            //保存产品发货信息
            Map<String, Object> deliveryInfo = (Map<String, Object>) params.get("delivery");
            deliveryInfo.putAll(queryInfo);
            Map<String, Object> hisDeliveryInfo = upMarketingBasicInfoService.getDeliveryInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisDeliveryInfo)) {
                deliveryInfo.put("id", MapUtils.getInteger(hisDeliveryInfo, "id"));
                deliveryInfo.put("createTime", hisDeliveryInfo.get("create_time"));
            } else {
                deliveryInfo.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertDeliveryInfo(deliveryInfo);

            //保存产品售后信息
            Map<String, Object> afterSaleInfo = (Map<String, Object>) params.get("afterSales");
            afterSaleInfo.putAll(queryInfo);
            Map<String, Object> hisAfterSaleInfo = upMarketingBasicInfoService.getAfterSalesInfo(queryInfo);
            if (MapUtils.isNotEmpty(hisAfterSaleInfo)) {
                afterSaleInfo.put("id", MapUtils.getInteger(hisAfterSaleInfo, "id"));
                afterSaleInfo.put("createTime", hisAfterSaleInfo.get("create_time"));
            } else {
                afterSaleInfo.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertAfterSalesInfo(afterSaleInfo);


        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("上传商品营销基本信息失败");
        }
        return result;
    }

    /**
     * 获取商品营销基本信息
     */
    @RequestMapping(value = "/getMarketingBasicInfo", method = RequestMethod.POST)
    public Result getMarketingBasicInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            String commodityId = MapUtils.getString(params, "commodityId");

            Map<String, Object> queryInfo = new HashMap<>();
            queryInfo.put("account", account);
            queryInfo.put("commodityId", commodityId);

            Map<String, Object> resultMap = new HashMap<>();

            //查询商品销售信息
            Map<String, Object> marketingResult = upMarketingBasicInfoService.getCommodityMarketingInfo(queryInfo);
            resultMap.put("sales", marketingResult);

            //查询商品质保信息
            Map<String, Object> assuranceResult = upMarketingBasicInfoService.getAssuranceInfo(queryInfo);
            resultMap.put("warranty", assuranceResult);

            //查询商品定价信息
            Map<String, Object> pricingResult = upMarketingBasicInfoService.getPricingInfo(queryInfo);
            resultMap.put("pricing", pricingResult);

            //查询产品发货信息
            Map<String, Object> deliveryResult = upMarketingBasicInfoService.getDeliveryInfo(queryInfo);
            resultMap.put("delivery", deliveryResult);

            //查询产品售后信息
            Map<String, Object> afterSaleResult = upMarketingBasicInfoService.getAfterSalesInfo(queryInfo);
            resultMap.put("afterSales", afterSaleResult);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品基本信息失败");
        }
        logger.info("查询商品基本信息为： {}", result.getData());
        return result;
    }

    /**
     * 上传商品追溯与防伪信息
     */
    @RequestMapping(value = "/upTraceableInfo", method = RequestMethod.POST)
    public Result upTraceableInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //保存商品追溯与防伪信息
            Map<String, Object> hisTraceableInfo = upMarketingBasicInfoService.getTraceableInfo(params);
            if (MapUtils.isNotEmpty(hisTraceableInfo)) {
                params.put("id", MapUtils.getInteger(hisTraceableInfo, "id"));
                params.put("createTime", hisTraceableInfo.get("create_time"));
            } else {
                params.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertTraceableInfo(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("上传商品追溯与防伪信息失败");
        }
        return result;
    }

    /**
     * 获取商品追溯与防伪信息
     */
    @RequestMapping(value = "/getTraceableInfo", method = RequestMethod.POST)
    public Result getTraceableInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品追溯与防伪信息
            Map<String, Object> traceableResult = upMarketingBasicInfoService.getTraceableInfo(params);
            result.setData(traceableResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品追溯与防伪信息失败");
        }
        logger.info("查询商品追溯与防伪信息为： {}", result.getData());
        return result;
    }

    /**
     * 上传商品承诺信息
     */
    @RequestMapping(value = "/upCommitmentInfo", method = RequestMethod.POST)
    public Result upCommitmentInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //保存商品承诺信息
            Map<String, Object> hisCommitmentInfo = upMarketingBasicInfoService.getCommitmentInfo(params);
            if (MapUtils.isNotEmpty(hisCommitmentInfo)) {
                params.put("id", MapUtils.getInteger(hisCommitmentInfo, "id"));
                params.put("createTime", hisCommitmentInfo.get("create_time"));
            } else {
                params.put("createTime", new Date());
            }
            upMarketingBasicInfoService.insertCommitmentInfo(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("上传商品承诺信息失败");
        }
        return result;
    }

    /**
     * 获取商品承诺信息
     */
    @RequestMapping(value = "/getCommitmentInfo", method = RequestMethod.POST)
    public Result getCommitmentInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品承诺信息
            Map<String, Object> commitmentResult = upMarketingBasicInfoService.getCommitmentInfo(params);
            result.setData(commitmentResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品承诺信息失败");
        }
        logger.info("查询商品承诺信息为： {}", result.getData());
        return result;
    }

    /**
     * 查询商品列表
     */
    @RequestMapping(value = "/findShopCommodityList", method = RequestMethod.POST)
    public Result findShopCommodityList(@RequestBody(required=false) Map<String,Object> params){
        Result result = new Result();
        try {
            //查询商品列表
            PageInfo<Map<String,Object>>  commodityList = shopCommodityListService.findShopCommodityList(params);
            result.setData(commodityList);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品列表信息失败");
        }
        logger.info("查询商品列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 商品详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCommodityDetail", method = RequestMethod.POST)
    public Result findCommodityDetail(@RequestBody(required=false) Map<String,Object> params){
        Result result = new Result();
        try {
            //查询商品详情
            Map<String,Object>  commodityDetail = shopCommodityListService.findCommodityDetail(params);
            result.setData(commodityDetail);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品详情失败");
        }
        logger.info("查询商品详情为： {}", result.getData());
        return result;
    }

    /**
     * 评论数量
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCommentNum", method = RequestMethod.POST)
    public Result findCommentNum(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            //查询评论数
            Map<String,Object>  commentNum = shopCommodityListService.findCommentNum(params);
            result.setData(commentNum);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询评论失败");
        }
        logger.info("查询评论为： {}", result.getData());
        return result;
    }

    /**
     * 评论
     * @param params
     * @return
     */
    @RequestMapping(value = "/findComment", method = RequestMethod.POST)
    public Result findComment(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            //查询评论
            PageInfo<Map<String,Object>>  commentMap = shopCommodityListService.findComment(params);
            result.setData(commentMap);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询评论失败");
        }
        logger.info("查询评论为： {}", result.getData());
        return result;
    }

    /**
     * 月成交记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMonthList", method = RequestMethod.POST)
    public Result findMonthList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            //月成交记录
            PageInfo<Map<String,Object>>  monthList = shopCommodityListService.findMonthList(params);
            result.setData(monthList);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询月成交记录失败");
        }
        logger.info("查询月成交记录为： {}", result.getData());
        return result;
    }

    /**
     * 卖家信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLoginUser", method = RequestMethod.POST)
    public Result findLoginUser(@RequestBody Map<String,Object> params){
        Result result = new Result();
        Map<String,Object>  loginUser = new HashMap<>();
        try {
            //卖家信息
            loginUser = shopCommodityListService.findLoginUser(params);
            if(loginUser!=null){
                result.setData(loginUser);
            }else{
                result.setCode(300);
                result.setMsg("请先实名认证！");
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询卖家信息");
        }
        logger.info("查询卖家信息为： {}", result.getData());
        return result;
    }

}
