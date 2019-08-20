package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.portal.mapper.ShopStorageInfoMapper;
import com.ovit.nswy.portal.mapper.UpMarketingBasicInfoMapper;
import com.ovit.nswy.portal.service.ShopStorageInfoService;
import com.ovit.nswy.portal.service.UpMarketingBasicInfoService;
import com.ovit.nswy.portal.util.JsonUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShopStorageInfoServiceImpl implements ShopStorageInfoService {

    @Autowired
    private ShopStorageInfoMapper shopStorageInfoMapper;

    /**
     * 获取商品货架信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getShopStorageList(Map<String, Object> params) {

        List<Map<String, Object>> storageList = shopStorageInfoMapper.getShopStorageList(params);
        //最小价格
        int startPrice = 0;
        //最大价格
        int endPrice = 0;
        if (StringUtils.isNotBlank(MapUtils.getString(params, "startPrice"))) {
            startPrice = MapUtils.getInteger(params, "startPrice");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(params, "endPrice"))) {
            endPrice = MapUtils.getInteger(params, "endPrice");
        }
        List<Map<String, Object>> storageResult = new ArrayList<>();
        for (Map<String, Object> storage : storageList) {
            String discountPrice = MapUtils.getString(storage, "discountPrice");
            /*if (startPrice == 0 && endPrice == 0) {
                //不需要查询价格区间内的数据
            } else */
            if (startPrice > 0 && endPrice == 0){
                //有最小价格限制
                if (StringUtils.isBlank(discountPrice)) {
                    //若折扣价为空，则按照时价判断
                    int timePrice = MapUtils.getInteger(storage, "timePrice");
                    if (timePrice < startPrice) {
                        //不满足要求
                        continue;
                    }
                } else {
                    //若折扣价不为空，则按照折扣价判断
                    if (Integer.parseInt(discountPrice) < startPrice) {
                        //不满足要求
                        continue;
                    }
                }
            } else if (startPrice == 0 && endPrice > 0) {
                //有最大价格限制
                if (StringUtils.isBlank(discountPrice)) {
                    //若折扣价为空，则按照时价判断
                    int timePrice = MapUtils.getInteger(storage, "timePrice");
                    if (timePrice > endPrice) {
                        //不满足要求
                        continue;
                    }
                } else {
                    //若折扣价不为空，则按照折扣价判断
                    if (Integer.parseInt(discountPrice) > endPrice) {
                        //不满足要求
                        continue;
                    }
                }
            } else if (startPrice > 0 && endPrice > 0) {
                //有最小价格和最大价格的限制
                if (StringUtils.isBlank(discountPrice)) {
                    //若折扣价为空，则按照时价判断
                    int timePrice = MapUtils.getInteger(storage, "timePrice");
                    if (timePrice < startPrice || timePrice > endPrice) {
                        //不满足要求
                        continue;
                    }
                } else {
                    //若折扣价不为空，则按照折扣价判断
                    if (Integer.parseInt(discountPrice) < startPrice || Integer.parseInt(discountPrice) > endPrice) {
                        //不满足要求
                        continue;
                    }
                }
            }
            String commodityImage = MapUtils.getString(storage, "commodityImage");
            if (StringUtils.isBlank(commodityImage)) {
                //没有上传商品预览图片
                storage.put("commodityImage", new String[0]);
            } else {
                String[] images = commodityImage.split(",");
                storage.put("commodityImage", images);
            }
            storageResult.add(storage);
        }

        return storageResult;
    }

    /**
     * 批量更新商品库存
     * @param params
     * @return
     */
    @Override
    public void batchUpdateStorage(List<Map<String, Object>> params) {
        shopStorageInfoMapper.batchUpdateStorage(params);
    }

    /**
     * 批量更新货架商品价格
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStoragePrice(Map<String, Object> params) {
        //更新价格
        shopStorageInfoMapper.updateStoragePrice(params);
        //更新该商品的审核状态为待审核、更新该商品的上架状态为未上架
        params.put("approveStatus", "0");
        params.put("storageStatus", "0");
        updateAuditStatus(params);
    }

    /**
     * 批量更新商品审核状态
     * @param params
     * @return
     */
    @Override
    public void updateAuditStatus(Map<String, Object> params) {
        //更新该商品的审核状态为待审核
        shopStorageInfoMapper.updateAuditStatus(params);
        //更新该商品的上架状态为未上架
        shopStorageInfoMapper.updateStorageStatus(params);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchDeleteCommodity(List<Map<String, Object>> params) {
        //删除商品相关信息(包含商品发布五个步骤中存储的表)
        //发布第一步相关表：
        //shop_commodity_type_info(商品分类信息)
        shopStorageInfoMapper.delTypeInfo(params);

        //发布第二步相关表：
        //shop_commodity_info(商品信息)、shop_commodity_production(商品生产信息)、shop_production_place_info(商品产地信息)
        //shop_commodity_qualification(商品资质信息)、shop_commodity_honor(商品荣誉信息)、shop_commodity_quality_safety(商品质量/安全信息、自定义表单)
        shopStorageInfoMapper.delCommodityInfo(params);
        shopStorageInfoMapper.delCommodityProduction(params);
        shopStorageInfoMapper.delPlaceInfo(params);
        shopStorageInfoMapper.delCommodityQualification(params);
        shopStorageInfoMapper.delCommodityHonor(params);
        shopStorageInfoMapper.delQualitySafety(params);

        //发布第三步相关表：
        //shop_commodity_supply_info(商品销售信息)、shop_commodity_assurance_info(商品质保信息)、shop_commodity_price_info(商品定价信息)
        //shop_product_delivery_info(产品发货信息)、shop_product_after_info(产品售后信息)
        shopStorageInfoMapper.delSupplyInfo(params);
        shopStorageInfoMapper.delAssuranceInfo(params);
        shopStorageInfoMapper.delPriceInfo(params);
        shopStorageInfoMapper.delDeliveryInfo(params);
        shopStorageInfoMapper.delAfterInfo(params);

        //发布第四步相关表：
        //shop_product_traceable_info(产品追溯信息)
        shopStorageInfoMapper.delTraceableInfo(params);

        //发布第五步相关表：
        //shop_product_commitment_info(商品承诺信息)
        shopStorageInfoMapper.delCommitmentInfo(params);

    }

}
