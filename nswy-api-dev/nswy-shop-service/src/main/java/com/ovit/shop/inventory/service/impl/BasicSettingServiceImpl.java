package com.ovit.shop.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.ovit.shop.inventory.mapper.BasicSettingMapper;
import com.ovit.shop.inventory.service.BasicSettingService;
import com.ovit.shop.species.service.SpeciesClassService;
import com.ovit.shop.util.ConvertDataUtil;
import com.ovit.shop.util.PageUtil;
import com.ovit.shop.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class BasicSettingServiceImpl implements BasicSettingService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private SpeciesClassService speciesClassService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Map<String, Object>> customFind(Map<String,Object> params) {
        return basicSettingMapper.customFind(params);
    }

    @Override
    public void customSave(Map<String, Object> params) {
        //生成主键
        params.put("id", UuidUtil.get32UUID());
        basicSettingMapper.customSave(params);
    }

    @Override
    public List<Map<String, Object>> productCodeIsUse(Map<String, Object> params) {
        return null;
    }

    @Override
    public void productSave(Map<String, Object> params) {
        basicSettingMapper.productSave(params);
    }

    @Override
    public PageInfo<Map<String, Object>> productFind(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = basicSettingMapper.productFind(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);
        }

        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> productList(Map<String, Object> params) {
        List<Map<String, Object>> list = basicSettingMapper.productFind(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);

            //查询得到产品大类
            Map<String,Object> param = new HashMap<>();
            param.put("productTypeId",MapUtils.getString(map,"productClassify"));
            findMallClass(param,map);

            Map<String,Object> commodity = new HashMap<>();
            commodity.put("id",MapUtils.getString(map,"commodityId"));

            //查询得到行业分类
            Map<String, Object> commodityMap = basicSettingMapper.findCommodityById(commodity);

            if (MapUtils.isNotEmpty(commodityMap)) {
                map.put("industryTypeId",MapUtils.getString(commodityMap,"industryTypeId"));
                map.put("industryType",MapUtils.getString(commodityMap,"industryType"));
                map.put("relatedSpeciesId",MapUtils.getString(commodityMap,"relatedSpeciesId"));
                //根据物种id获取物种名称
                Object result = findBySpeciesName(MapUtils.getString(commodityMap, "relatedSpeciesId"),1);

                Map<String,Object> maps = new ObjectMapper().convertValue(result, Map.class);

                List<Map<String, Object>> jsonList = (List<Map<String, Object>>) maps.get("data");

                if(jsonList !=null && jsonList.size()>0){
                    String speciesName = MapUtils.getString(jsonList.get(0),"fname");

                    map.put("relatedSpecies",speciesName);
                }

            } else {
                map.put("industryTypeId","");
                map.put("industryType","");
                map.put("relatedSpeciesId","");
                map.put("relatedSpecies","");
            }

            Query query = new Query();

            Criteria criteria =  Criteria.where("account").is(MapUtils.getString(params,"account"))
                    .and("productNameId").is(MapUtils.getInteger(map,"id"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> shopList = mongoTemplate.find(query, Map.class, "push_shop_commodity_info");
            if(CollectionUtils.isNotEmpty(shopList)){

                map.put("goodsId", MapUtils.getString(shopList.get(0),"id"));

                query = new Query();

                criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(shopList.get(0),"id"));

                Criteria criterias = new Criteria();
                criterias.and("storageStatus").is("3");

                Criteria crite = new Criteria();
                crite.and("isDelete").is("1");

                criteria.orOperator(criterias,crite);

                //查询
                query.addCriteria(criteria);
                query.fields().exclude("_id");

                List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");

                if(CollectionUtils.isNotEmpty(product))
                    map.put("status","0");
                else
                    map.put("status","1");

            } else {
                map.put("status","1");
                map.put("goodsId", "");
            }
        }

        return list;
    }

    private Object findBySpeciesName(String speciesid, Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("speciesid", speciesid);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return speciesClassService.findBySpeciesName(JSON.toJSON(map));

    }

    private void findMallClass(Map<String,Object> params,Map<String,Object> map){

        Map<String,Object> maps = basicSettingMapper.findMallClass(params);
        if(StringUtils.isNotBlank(MapUtils.getString(maps,"pid"))){
            findMallClass(maps,map);
        }else{

            map.put("productCategory",MapUtils.getString(maps,"productCategory"));
            map.put("productCategoryId",MapUtils.getString(maps,"classification_code"));
        }

    }



    @Override
    public void productDelete(Map<String, Object> params) {
        basicSettingMapper.productDelete(params);
    }

    @Override
    public void customDelete(Map<String, Object> params) {
        basicSettingMapper.customDelete(params);
    }

    @Override
    public Integer getStoreOrder(String account) {
        Integer order = basicSettingMapper.getStoreOrder(account);
        return (order == null) ? 1 : order+1;
    }

    @Override
    public void storeSave(Map<String, Object> params) {
        basicSettingMapper.storeSave(params);
    }

    @Override
    public PageInfo<Map<String, Object>> storeFind(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);

        Integer num = MapUtils.getInteger(params, "pageNum");
        int pageNum = (num == null) ? 1 : num;
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;

        List<Map<String, Object>> list = basicSettingMapper.storeFind(params);
        for (int i=0; i<list.size(); i++) {
            int order = (pageNum-1) * pageSize + i + 1;
            list.get(i).put("order", order);
        }
        return new PageInfo<>(list);
    }

    @Override
    public void storeDelete(Map<String, Object> params) {
        basicSettingMapper.storeDelete(params);
    }

    @Override
    public void billSave(Map<String, Object> params) {
        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.remove("id");
        }
        basicSettingMapper.billSave(params);
    }

    @Override
    public Map<String, Object> getProductName(Map<String, Object> params) {
        return basicSettingMapper.findProductClassify(params);
    }

    @Override
    public Map<String, Object> billFind(Map<String, Object> params) {
        return basicSettingMapper.billFind(params);
    }

    @Override
    public Integer getInStoreOrder(String account) {
        Integer order = basicSettingMapper.getInStoreOrder(account);
        return (order == null) ? 1 : order+1;
    }

    @Override
    public void inStoreSave(Map<String, Object> params) {
        basicSettingMapper.inStoreSave(params);
    }

    @Override
    public PageInfo<Map<String, Object>> inStoreFind(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);

        Integer num = MapUtils.getInteger(params, "pageNum");
        int pageNum = (num == null) ? 1 : num;
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;

        List<Map<String, Object>> list = basicSettingMapper.inStoreWithSystem(params);
        for (int i=0; i<list.size(); i++) {
            int order = (pageNum-1) * pageSize + i + 1;
            list.get(i).put("order", order);
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> inStoreList(Map<String, Object> params) {
        return basicSettingMapper.inStoreFind(params);
    }

    @Override
    public void inStoreDelete(Map<String, Object> params) {
        basicSettingMapper.inStoreDelete(params);
    }

    @Override
    public Integer getOutStoreOrder(String account) {
        Integer order = basicSettingMapper.getOutStoreOrder(account);
        return (order == null) ? 1 : order+1;
    }

    @Override
    public void outStoreSave(Map<String, Object> params) {
        basicSettingMapper.outStoreSave(params);
    }

    @Override
    public PageInfo<Map<String, Object>> outStoreFind(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);

        Integer num = MapUtils.getInteger(params, "pageNum");
        int pageNum = (num == null) ? 1 : num;
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;

        List<Map<String, Object>> list = basicSettingMapper.outStoreWithSystem(params);
        for (int i=0; i<list.size(); i++) {
            int order = (pageNum-1) * pageSize + i + 1;
            list.get(i).put("order", order);
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> outStoreList(Map<String, Object> params) {
        return basicSettingMapper.outStoreFind(params);
    }

    @Override
    public void outStoreDelete(Map<String, Object> params) {
        basicSettingMapper.outStoreDelete(params);
    }

    @Override
    public List<Map<String, Object>> productCodeList(Map<String, Object> params) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        String account = MapUtils.getString(params, "account");
        if (StringUtils.isBlank(account)) {
            return resultList;
        }
        String memberName = basicSettingMapper.getMemberName(account);
        if (StringUtils.isBlank(memberName)) {
            memberName = "";
        }
        List<Map<String, Object>> list = basicSettingMapper.productFind(params);
        for (Map<String,Object> map : list) {
            Map<String,Object> result = new HashMap<>();
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            result.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            result.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            result.put("commodityName", commodityName);
            result.put("productCode", MapUtils.getString(map, "productCode"));
            result.put("productName", MapUtils.getString(map, "productName"));
            ConvertDataUtil.merge(map, "unit");
            result.put("unit", map.get("unit"));
            result.put("operatorAccount", memberName);
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void enterStoreSave(Map<String, Object> params) {
        //保存至入库记录表
        basicSettingMapper.enterStoreSave(params);
        //自动合并同一产品、同一计量单位、同一仓库、同一单价的数据到库存管理表inventory_store_manage中
        Map<String, Object> manage = basicSettingMapper.storeManageFind(params);
        String inStoreType = MapUtils.getString(params, "inStoreType");
        if ("d6ac4bcfdfa1416c9532fedbcf304e55".equals(inStoreType)) {
            //代表入库类型为库存初始化
            params.put("initialStore", MapUtils.getString(params, "number"));
        } else {
            params.put("initialStore", "");
        }
        if (MapUtils.isNotEmpty(manage)) {
            //可合并,即进行更新
            basicSettingMapper.storeManageUpdate(params);

        } else {
            //不可合并，新增一条数据
            basicSettingMapper.overrideStoreManageSave(params);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> storeList(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = basicSettingMapper.manageFind(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return new PageInfo<>(list);
    }

    @Override
    public void warningSave(Map<String, Object> params) {
        basicSettingMapper.warningSave(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void exitStoreSave(Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        String operatorAccount = MapUtils.getString(params, "operatorAccount");
        String outStoreType = MapUtils.getString(params, "outStoreType");
        String exitOrder = MapUtils.getString(params, "exitOrder");
        List<Map<String,Object>> list = new ArrayList<>();
        if (params.get("list") instanceof List) {
            list = (List<Map<String, Object>>) params.get("list");
            if (CollectionUtils.isNotEmpty(list)) {
                for (Map<String,Object> map : list) {
                    map.put("account", account);
                    map.put("operatorAccount", operatorAccount);
                    map.put("outStoreType", outStoreType);
                    map.put("createTime", params.get("createTime"));
                    map.put("exitOrder", exitOrder);
                }
                //批量保存至出库记录表inventory_out_store_record中
                basicSettingMapper.exitStoreSave(list);
                //出库时，需要更新库存管理表(inventory_store_manage)的数据, 库存量相应减少
                basicSettingMapper.outStoreManageUpdate(list);
            }
        } else {
            logger.info("数据格式:{}, 不正确, 出库失败", params);
        }


    }

    @Override
    public String generateOrder(int type) {
        String order = "";
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int num =(int)(Math.random()*900)+100;
        if (type == 1) {
            //生成入库单号
            order = "RK" + currentTime + String.valueOf(num);
        } else if (type ==2) {
            //生成出库单号
            order = "CK" + currentTime + String.valueOf(num);
        }
        return order;
    }

    @Override
    public PageInfo<Map<String, Object>> enterRecord(Map<String, Object> params) {

        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = basicSettingMapper.enterRecord(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> exitRecord(Map<String, Object> params) {

        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = basicSettingMapper.exitRecord(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }

        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> enterOrder(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        Map<String, Object> map = basicSettingMapper.enterOrder(params);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> detail = new HashMap<>();
        if (MapUtils.isNotEmpty(map)) {
            resultMap.put("companyName", "入库单");
            resultMap.put("order", MapUtils.getString(map, "order"));
            resultMap.put("operatorAccount", MapUtils.getString(map, "operatorAccount"));
            resultMap.put("storeName", MapUtils.getString(map, "storeName"));
            resultMap.put("createTime", map.get("createTime"));

            detail.put("productCode", MapUtils.getString(map, "productCode"));
            detail.put("productName", MapUtils.getString(map, "productName"));
            detail.put("batchNumber", MapUtils.getString(map, "batchNumber"));
            detail.put("unit", MapUtils.getString(map, "unit"));
            detail.put("number", MapUtils.getString(map, "number"));
            detail.put("price", MapUtils.getString(map, "price"));
            detail.put("totalPrice", MapUtils.getString(map, "totalPrice"));
            list.add(detail);
        }
        resultMap.put("list", list);
        return resultMap;
    }

    @Override
    public Map<String, Object> exitOrder(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> orderList = basicSettingMapper.exitOrder(params);
        List<Map<String,Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderList)) {
            resultMap.put("companyName", "出库单");
            resultMap.put("order", MapUtils.getString(orderList.get(0), "order"));
            resultMap.put("operatorAccount", MapUtils.getString(orderList.get(0), "operatorAccount"));
            resultMap.put("createTime", orderList.get(0).get("createTime"));
        }
        for (Map<String,Object> order : orderList) {
            Map<String,Object> detail = new HashMap<>();
            detail.put("productCode", MapUtils.getString(order, "productCode"));
            detail.put("productName", MapUtils.getString(order, "productName"));
            detail.put("unit", MapUtils.getString(order, "unit"));
            detail.put("number", MapUtils.getString(order, "number"));
            detail.put("price", MapUtils.getString(order, "price"));
            detail.put("totalPrice", MapUtils.getString(order, "totalPrice"));
            detail.put("storeName", MapUtils.getString(order, "storeName"));
            list.add(detail);
        }
        resultMap.put("list", list);
        return resultMap;
    }

    @Override
    public PageInfo<Map<String, Object>> storeCombineList(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = basicSettingMapper.storeCombineList(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void storeTransfer(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        String account = MapUtils.getString(params, "account");
        String operatorAccount = MapUtils.getString(params, "operatorAccount");
        String targetStore = MapUtils.getString(params, "targetStore");
        //先调拨入库，调拨到的目的仓库，对应的就是调拨入库，即数据增加的仓库
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                map.put("transferInStore", MapUtils.getString(map, "inStore"));
                map.put("account", account);
                map.put("operatorAccount", operatorAccount);
                map.put("isDisplay", 0);
                map.put("inStore", targetStore);
                //443e15f57c584e55b1fc6a575fe1ad1d对应调拨入库
                map.put("inStoreType", "443e15f57c584e55b1fc6a575fe1ad1d");
                //443e15f57c584e55b1fc6a575fe1ad1d对应调拨出库
                map.put("outStoreType", "ba7fd7f1bdaa41648e7c00bdc3d64c6a");
                //totalPrice需要计算，等于number * price
                map.put("createTime", params.get("createTime"));
                //更新库存管理表(inventory_store_manage)的数据, 库存量相应增加
                //自动合并同一产品、同一计量单位、同一仓库、同一单价的数据到库存管理表inventory_store_manage中
                Map<String, Object> manage = basicSettingMapper.storeManageFind(map);
                map.put("initialStore", "");
                if (MapUtils.isNotEmpty(manage)) {
                    //可合并,即进行更新
                    basicSettingMapper.storeManageUpdate(map);
                } else {
                    //不可合并，新增一条数据 -- 不需要返回id
                    basicSettingMapper.overrideStoreManageSave(map);
                }
            }
            //批量保存至入库记录表inventory_in_store_record中
            basicSettingMapper.transferInSave(list);
            //批量保存至出库记录表inventory_out_store_record中
            basicSettingMapper.transferOutSave(list);
            //调拨出库，从仓库调拨出去，对应的就是调拨出库，即数据减少的仓库
            //出库时，需要更新库存管理表(inventory_store_manage)的数据, 库存量相应减少
            basicSettingMapper.outStoreManageUpdate(list);

        }
    }

    @Override
    public List<Map<String, Object>> storeTypeList(Map<String, Object> params) {
        List<Map<String, Object>> list = basicSettingMapper.storeTypeList(params);
        for (Map<String,Object> map : list) {
            map.put("id", MapUtils.getString(map, "id"));
        }
        return list;
    }

    @Override
    public PageInfo<Map<String, Object>> storeDetail(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);

        Integer num = MapUtils.getInteger(params, "pageNum");
        int pageNum = (num == null) ? 1 : num;
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;

        List<Map<String, Object>> list = basicSettingMapper.storeDetail(params);
        for (int i=0; i<list.size(); i++) {
            int order = (pageNum-1) * pageSize + i + 1;
            list.get(i).put("orderNumber", order);
            Map<String,Object> map = list.get(i);
            //通过account、storeType、type(1:入库,2:出库)查询出出入库类型名称
            String storeTypeName = basicSettingMapper.getStoreTypeName(map);
            map.put("storeTypeName", storeTypeName);
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }

        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> productStoreDetail(Map<String, Object> params) {
        Map<String,Object> resultMap = new HashMap<>();
        //产品信息
        Map<String, Object> productMap = basicSettingMapper.productDetail(params);
        resultMap.putAll(productMap);
        //获取当前库存量和期初库存量
        List<Map<String, Object>> list = basicSettingMapper.storeCombineList(params);
        if (CollectionUtils.isNotEmpty(list)) {
            resultMap.put("initialStore", list.get(0).get("initialStore"));
            resultMap.put("totalStore", list.get(0).get("totalStore"));
        }
        //获取入库量, 排除库存初始化的量
        params.put("inStoreType", "d6ac4bcfdfa1416c9532fedbcf304e55");
        String inNumber = basicSettingMapper.inStoreNumber(params);
        resultMap.put("inNumber", (StringUtils.isNotBlank(inNumber) ? inNumber : ""));
        //获取出库量
        String outNumber = basicSettingMapper.outStoreNumber(params);
        resultMap.put("outNumber", (StringUtils.isNotBlank(outNumber) ? outNumber : ""));

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> inStoreIsUse(Map<String, Object> params) {
        return basicSettingMapper.inStoreIsUse(params);
    }

    @Override
    public List<Map<String, Object>> outStoreIsUse(Map<String, Object> params) {
        return basicSettingMapper.outStoreIsUse(params);
    }

    @Override
    public List<Map<String, Object>> storeIsUse(Map<String, Object> params) {
        return basicSettingMapper.storeIsUse(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void storeSplit(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        String account = MapUtils.getString(params, "account");
        Map<String,Object> splitMap = MapUtils.getMap(params, "map");
        String productCode = MapUtils.getString(splitMap, "productCode");
        String unit = MapUtils.getString(splitMap, "unit");
        String price = MapUtils.getString(splitMap, "price");
        String operatorAccount = MapUtils.getString(params, "operatorAccount");

        //先拆分入库，拆分到的目的仓库，即数据增加的仓库
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                map.put("account", account);
                map.put("productCode", productCode);
                map.put("unit", unit);
                map.put("price", price);
                map.put("operatorAccount", operatorAccount);
                map.put("isDisplay", 0);
                //bcf0eaa82f454a9281eb14a4b0875c32对应拆分入库
                map.put("inStoreType", "bcf0eaa82f454a9281eb14a4b0875c32");
                //e9aec4d5610242f79a78a35ae3342c4c对应拆分出库
                //map.put("outStoreType", "e9aec4d5610242f79a78a35ae3342c4c");
                //totalPrice需要计算，等于number * price
                map.put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                //更新库存管理表(inventory_store_manage)的数据, 库存量相应增加
                //自动合并同一产品、同一计量单位、同一仓库、同一单价的数据到库存管理表inventory_store_manage中
                Map<String, Object> manage = basicSettingMapper.storeManageFind(map);
                map.put("initialStore", "");
                if (MapUtils.isNotEmpty(manage)) {
                    //可合并,即进行更新
                    basicSettingMapper.storeManageUpdate(map);
                } else {
                    //不可合并，新增一条数据
                    basicSettingMapper.overrideStoreManageSave(map);
                }
            }
            //批量保存至入库记录表inventory_in_store_record中
            basicSettingMapper.transferInSave(list);

        }

        //先拆分出库，保存至出库记录表inventory_out_store_record中
        splitMap.put("account", account);
        splitMap.put("isDisplay", 0);
        splitMap.put("operatorAccount", operatorAccount);
        splitMap.put("outStoreType", "e9aec4d5610242f79a78a35ae3342c4c");
        splitMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        basicSettingMapper.splitOutSave(splitMap);
        //拆分出库，从仓库拆分出去，对应的就是拆分出库，即数据减少的仓库
        //出库时，需要更新库存管理表(inventory_store_manage)的数据, 库存量相应减少
        List<Map<String,Object>> splitList = new ArrayList<>();
        splitList.add(splitMap);
        basicSettingMapper.outStoreManageUpdate(splitList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void storeMerge(Map<String, Object> params) {
        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        String account = MapUtils.getString(params, "account");
        //合并后的单价
        String price = MapUtils.getString(params, "price");
        //合并总数量
        String number = MapUtils.getString(params, "number");
        //多个商品可以进行合并，合并条件：只有同一产品名称、同一计量单位且在同一仓库中的产品
        Map<String,Object> mergeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String,Object> m = list.get(0);
            mergeMap.put("account", account);
            mergeMap.put("productCode", m.get("productCode"));
            mergeMap.put("unit", m.get("unit"));
            mergeMap.put("price", price);
            mergeMap.put("inStore", m.get("inStore"));
            mergeMap.put("number", number);
        }
        //先合并到库存管理表inventory_store_manage中
        Map<String, Object> manage = basicSettingMapper.storeManageFind(mergeMap);
        mergeMap.put("initialStore", "");
        int manageId = 0;
        if (MapUtils.isNotEmpty(manage)) {
            //库存管理表中对应数据的ID
            manageId = MapUtils.getInteger(manage, "id");
            //可合并,即进行更新
            basicSettingMapper.storeManageUpdate(mergeMap);
        } else {
            //不可合并，新增一条数据
            basicSettingMapper.storeManageSave(mergeMap);
            manageId = MapUtils.getInteger(mergeMap, "id");

        }
        //合并的多条数据类似于出库操作，要相应的减少库存量
        //出库时，需要更新库存管理表(inventory_store_manage)的数据, 库存量相应减少
        basicSettingMapper.outStoreManageUpdate(list);

        //使用isMerge字段标识该条数据是否是合并项，0表示不是合并项，1表示是合并项
        for (Map<String,Object> map : list) {
            map.put("manageId", manageId);
            map.put("account", account);
            map.put("isMerge", 1);
            //如果合并的数据中，有数据已经是经过合并的，那么需要将合并记录数据的manage_id更新为最新的合并数据的id
            basicSettingMapper.mergeRecordUpdate(map);
        }
        if (MapUtils.isNotEmpty(manage)) {
            manage.put("manageId", manageId);
            manage.put("isMerge", 0);
            manage.put("number", MapUtils.getString(manage, "totalStore"));
            list.add(manage);
        }
        //保存合并记录到inventory_merge_record中
        basicSettingMapper.mergeRecordSave(list);
    }

    @Override
    public PageInfo<Map<String, Object>> mergeList(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        String account = MapUtils.getString(params, "account");
        List<Map<String, Object>> list = basicSettingMapper.manageFind(params);
        for (Map<String,Object> map : list) {
            //获取自定义分类
            String customName = "";
            Map<String,Object> customMap = new HashMap<>();
            customMap.put("customId", MapUtils.getString(map, "customId"));
            List<Map<String, Object>> customList = basicSettingMapper.customFind(customMap);
            if (CollectionUtils.isNotEmpty(customList)) {
                customName = MapUtils.getString(customList.get(0), "customName");
            }
            map.put("customName", customName);
            //获取产品分类
            String classifyName = "";
            Map<String, Object> productClassify = basicSettingMapper.findProductClassify(map);
            if (MapUtils.isNotEmpty(productClassify)) {
                classifyName = MapUtils.getString(productClassify, "classifyName");
            }
            map.put("classifyName", classifyName);
            //获取通用商品名
            String commodityName = "";
            Map<String, Object> commonCommodity = basicSettingMapper.findCommonCommodity(map);
            if (MapUtils.isNotEmpty(commonCommodity)) {
                commodityName = MapUtils.getString(commonCommodity, "commodityName");
            }
            map.put("commodityName", commodityName);

            //查看该条数据是否是合并来的
            Map<String,Object> mergeMap = new HashMap<>();
            mergeMap.put("manageId", map.get("id"));
            mergeMap.put("account", account);
            List<Map<String, Object>> mergeList = basicSettingMapper.mergeList(mergeMap);
            if (CollectionUtils.isEmpty(mergeList)) {
                map.put("children", new String[0]);
            } else {
                for (Map<String,Object> merge : mergeList) {
                    merge.put("customName", customName);
                    merge.put("classifyName", classifyName);
                    merge.put("commodityName", commodityName);
                }
                map.put("children", mergeList);
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, Object>> productIsUse(Map<String, Object> params) {
        return basicSettingMapper.productIsUse(params);
    }

    @Override
    public List<Map<String, Object>> customIsUse(Map<String, Object> params) {
        return basicSettingMapper.customIsUse(params);
    }

    @Override
    public Map<String, Object> findStoreInfo(int id) {
        return basicSettingMapper.findStoreInfo(id);
    }
}
