package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.portal.mapper.StandardMapper;
import com.ovit.nswy.portal.service.StandardService;
import com.ovit.nswy.portal.util.PageUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.util.*;

/**
 * 门户标准service
 *
 * @author haoWen
 * @create 2018-03-15 15:00
 **/

@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardMapper standardMapper;

    @Override
    public PageInfo<Map<String, Object>> queryStandardList(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String,Object>> list = standardMapper.queryStandardList(params);
        return new PageInfo<>(list);
    }

    /**
     * 查询农药残留标准
     * @return
     */
    @Override
    public List<Map<String, Object>> findResidueStandard(Map<String, Object> params) {
        List<Map<String,Object>> standardList = standardMapper.findResidueStandard(params);
        return standardList;
    }

    /**
     * 查询通用商品名库
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findCurrencyCommodity(Map<String, Object> params) {
        List<Map<String,Object>> commodityList = standardMapper.findCurrencyCommodity(params);
        return commodityList;
    }

    /**
     * 查询单位标准库
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findUnitStandard(String params) {
        List<Map<String,Object>> unitList = standardMapper.findUnit(params);
        for (Map<String,Object> map : unitList) {
            String id = MapUtils.getString(map, "classId");
            List<Map<String, Object>> subList = standardMapper.findUnit(id);
            if (CollectionUtils.isNotEmpty(subList)) {
                map.put("children", new String[0]);
            }
        }
        return unitList;
    }

    @Override
    public List<Map<String, Object>> findUnit(Map<String, Object> params) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<String> idList = (List<String>) params.get("ids");
        if(idList.size()>0&&idList!=null) {
            for (String id : idList) {
                params.put("id", id);
                resultList.addAll(standardMapper.findBasicUnit(params));
            }
        }else {
            resultList.addAll(standardMapper.findBasicUnit(params));
        }
        String keywords = MapUtils.getString(params, "keywords");
        List<Map<String,Object>> loopList = new ArrayList<>();
        if (StringUtils.isNotBlank(keywords)) {
            for (Map<String,Object> map : resultList) {
                //通过关键字、拼音筛选
                String label = MapUtils.getString(map, "label");
                if (StringUtils.isNotBlank(keywords)) {
                    if (!label.contains(keywords)) {
                        continue;
                    }
                }
                loopList.add(map);
            }
            return loopList;
        }

        return resultList;

    }

    /**
     * 查询产品分类数据
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findProductClassification(Map<String, Object> params) {

        List<Map<String,Object>> productList = standardMapper.findProductClassification(params);

        if(StringUtils.isNotBlank(MapUtils.getString(params, "pinyin"))) {
            //若包含通过首字母搜索的字段
            List<Map<String,Object>> filterList = new ArrayList<>();
            String pinyin = MapUtils.getString(params, "pinyin");
            for (Map<String, Object> product : productList) {
                if (pinyin.equals(RegexUtil.getPinYinHeadChar(MapUtils.getString(product, "className")).substring(0, 1).toUpperCase())) {
                    filterList.add(product);
                }
            }
            return filterList;

        } else {
            return productList;
        }
    }

    @Override
    public List<Map<String, Object>> findMallClassification() {
        Map<String, Object> params = new HashMap<>();
        //九大类数据
        List<Map<String, Object>> classList = new ArrayList<>();

        //农资产品
        params.put("code", "CP01");
        Map<String, Object> agriProduct = new HashMap<>();
        agriProduct.put("icon", "icon-rice");
        agriProduct.put("label", "农资产品");
        List<Map<String,Object>> agriProductList = standardMapper.findMallClassification(params);
        agriProduct.put("children", getChildData(agriProductList));
        classList.add(agriProduct);

        //生产工具
        params.put("code", "CP02");
        Map<String, Object> toolProduct = new HashMap<>();
        toolProduct.put("icon", "icon-produce");
        toolProduct.put("label", "生产工具");
        List<Map<String,Object>> toolProductList = standardMapper.findMallClassification(params);
        toolProduct.put("children", getChildData(toolProductList));
        classList.add(toolProduct);

        //机械设备
        params.put("code", "CP03");
        Map<String, Object> mechanicalEqu = new HashMap<>();
        mechanicalEqu.put("icon", "icon-machine");
        mechanicalEqu.put("label", "机械设备");
        List<Map<String,Object>> mechanicalEquList = standardMapper.findMallClassification(params);
        mechanicalEqu.put("children", getChildData(mechanicalEquList));
        classList.add(mechanicalEqu);

        //仪器仪表
        params.put("code", "CP04");
        Map<String, Object> instrument = new HashMap<>();
        instrument.put("icon", "icon-instr");
        instrument.put("label", "仪器仪表");
        List<Map<String,Object>> instrumentList = standardMapper.findMallClassification(params);
        instrument.put("children", getChildData(instrumentList));
        classList.add(instrument);

        //农事产品
        params.put("code", "CP05");
        Map<String, Object> farmProduct = new HashMap<>();
        farmProduct.put("icon", "icon-farming");
        farmProduct.put("label", "农事产品");
        List<Map<String,Object>> farmProductList = standardMapper.findMallClassification(params);
        farmProduct.put("children", getChildData(farmProductList));
        classList.add(farmProduct);

        //食用产品
        params.put("code", "CP06");
        Map<String, Object> edibleProduct = new HashMap<>();
        edibleProduct.put("icon", "icon-holl-team");
        edibleProduct.put("label", "食用产品");
        List<Map<String,Object>> edibleProductList = standardMapper.findMallClassification(params);
        edibleProduct.put("children", getChildData(edibleProductList));
        classList.add(edibleProduct);

        //图书音像
        params.put("code", "CP07");
        Map<String, Object> bookVideo = new HashMap<>();
        bookVideo.put("icon", "icon-record");
        bookVideo.put("label", "图书音像");
        List<Map<String,Object>> bookVideoList = standardMapper.findMallClassification(params);
        bookVideo.put("children", getChildData(bookVideoList));
        classList.add(bookVideo);

        //日用产品
        params.put("code", "CP08");
        Map<String, Object> dailyProduct = new HashMap<>();
        dailyProduct.put("icon", "icon-spade");
        dailyProduct.put("label", "日用产品");
        List<Map<String,Object>> dailyProductList = standardMapper.findMallClassification(params);
        dailyProduct.put("children", getChildData(dailyProductList));
        classList.add(dailyProduct);

        //无形产品
        params.put("code", "CP09");
        Map<String, Object> intangibleProduct = new HashMap<>();
        intangibleProduct.put("icon", "icon-field");
        intangibleProduct.put("label", "无形产品");
        List<Map<String,Object>> intangibleProductList = standardMapper.findMallClassification(params);
        intangibleProduct.put("children", getChildData(intangibleProductList));
        classList.add(intangibleProduct);

        return classList;
    }

    List<Map<String, Object>> getChildData(List<Map<String, Object>> classificationList) {
        Map<String,Object> resultTemp =null;
        List<Map<String,Object>> nodeList = new ArrayList<>();
        for (Map<String,Object> map : classificationList) {
            resultTemp = new HashMap<>();
            resultTemp.put("label",map.get("className"));
            resultTemp.put("value",map.get("productCode"));
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", map.get("id"));
            if(!standardMapper.findMallClassification(params).isEmpty()){
                resultTemp.put("children",getChildNode(MapUtils.getString(map, "id")));
            }
            nodeList.add(resultTemp);

        }
        return nodeList;
    }

    List<Map<String, Object>> getChildNode(String id) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", id);
        List<Map<String, Object>> tempList = standardMapper.findMallClassification(params);
        Map<String, Object> map = null;
        if (!tempList.isEmpty()) {
            for (Map tempMap : tempList) {
                map = new HashMap<>();
                map.put("label",tempMap.get("className"));
                map.put("value",tempMap.get("productCode"));
                params.put("parentId", tempMap.get("id"));
                if (!standardMapper.findMallClassification(params).isEmpty()) {
                    map.put("children", getChildNode(tempMap.get("id").toString()));
                }
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public Boolean isExist(Map<String, Object> params) {
        List<Map<String, Object>> list = standardMapper.isExist(params);
        Boolean isExist = false;
        if (CollectionUtils.isNotEmpty(list)) {
            isExist = true;
        }
        return isExist;
    }
}
