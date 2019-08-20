package com.ovit.nswy.member.application.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.application.mapper.MyRecommendMapper;
import com.ovit.nswy.member.application.service.MyRecommendService;
import com.ovit.nswy.member.feigen.ShopServiceFeign;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MyRecommendServiceImpl implements MyRecommendService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MyRecommendMapper myRecommendMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private ShopServiceFeign shopServiceFeign;

    @Override
    public Map<String, Object> serviceList(Map<String, Object> params) {
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;
        Integer number = MapUtils.getInteger(params, "pageNum");
        int pageNum = (number == null) ? 1 : number;
        params.remove("pageSize");
        params.remove("pageNum");

        List<Map<String, Object>> list = new ArrayList<>();
        //flag为0表示查询所有服务 flag为1表示查询已推荐服务
        String flag = MapUtils.getString(params, "flag");
        if ("0".equals(flag)) {
            //查询所有服务
            //所有服务:0 垂钓 1采摘 2景区 3餐饮 4 住宿
            List<Map<String, Object>> serviceList = myRecommendMapper.commonServiceList(params);
            if (CollectionUtils.isNotEmpty(serviceList)) {
                list.addAll(serviceList);
            }
            //所有服务:5 咨询
            List<Map<String, Object>> consultList = myRecommendMapper.consultList(params);
            if (CollectionUtils.isNotEmpty(consultList)) {
                list.addAll(consultList);
            }
        } else if ("1".equals(flag)) {
            //查询已推荐服务
            //已推荐服务:0 垂钓 1采摘 2景区 3餐饮 4 住宿
            List<Map<String, Object>> recommendServiceList = myRecommendMapper.recommendCommonServiceList(params);
            if (CollectionUtils.isNotEmpty(recommendServiceList)) {
                list.addAll(recommendServiceList);
            }
            //已推荐服务:5 咨询
            List<Map<String, Object>> recommendConsultList = myRecommendMapper.recommendConsultList(params);
            if (CollectionUtils.isNotEmpty(recommendConsultList)) {
                list.addAll(recommendConsultList);
            }
        }

        for (Map<String,Object> map : list) {
            String type = MapUtils.getString(map,"type");
            if("0".equals(type)) {
                Map<String,Object> timeMap = new HashMap<>();
                timeMap.put("fishServiceId", MapUtils.getString(map,"id"));
                //按时间收费
                List<Map<String,Object>> fishTimeChargeList = myRecommendMapper.findFishTimeChargeInfo(timeMap);
                if(CollectionUtils.isNotEmpty(fishTimeChargeList)) {
                    if(MapUtils.getString(fishTimeChargeList.get(0),"timeCharging").equals("1")) {
                        map.put("timeCharging", true);
                    }else{
                        map.put("timeCharging", false);
                    }
                } else {
                    map.put("timeCharging", false);
                }
            }
            if ("0".equals(type) || "1".equals(type)) {
                Map<String,Object> varietyMap = new HashMap<>();
                varietyMap.put("serviceId", MapUtils.getString(map,"id"));
                //品种
                List<Map<String,Object>> varietyList = myRecommendMapper.findServiceVarietyInfo(varietyMap);
                if(CollectionUtils.isNotEmpty(varietyList)) {
                    if(StringUtil.isNotBlank(MapUtils.getString(varietyList.get(0),"timeVariety"))) {
                        if (MapUtils.getString(varietyList.get(0),"timeVariety").equals("1")) {
                            map.put("timeVariety", true);
                        } else {
                            map.put("timeVariety", false);
                        }

                    } else {
                        map.put("timeVariety", false);
                    }
                } else {
                    map.put("timeVariety", false);
                }
            }
            //2景区 3餐饮 4 住宿
            if("2".equals(type) || "3".equals(type) || "4".equals(type)) {
                String fishServiceId = MapUtils.getString(map,"id");

                Map<String,Object> productMap = new HashMap<>();
                productMap.put("fishServiceId", fishServiceId);
                productMap.put("orderPrice", "0");

                //获取套餐详细
                List<Map<String,Object>> productManageList = myRecommendMapper.findProductManagementService(productMap);
                if(CollectionUtils.isNotEmpty(productManageList)) {
                    map.put("price", MapUtils.getString(productManageList.get(0),"setMealPrice"));
                }
            }

            //处理联系人信息
            if (!"5".equals(type)) {
                //联系人
                if (StringUtils.isNotBlank(MapUtils.getString(map, "contact"))) {
                    List<Map<String,Object>> contactList = new ArrayList<>();
                    JSONArray contactArray = JSONArray.parseArray(MapUtils.getString(map, "contact"));
                    for (Object contact : contactArray) {
                        String detailAddress = ((JSONObject) contact).getString("detailAddress");
                        Map<String,Object> contactMap = new HashMap<>();
                        contactMap.put("detailAddress", detailAddress);
                        contactList.add(contactMap);
                    }
                    map.put("contact", contactList);
                } else {
                    map.put("contact", new String[0]);
                }
            }

            String url = MapUtils.getString(map,"image_url");
            if(!"null".equals(url) && StringUtil.isNotBlank(url)) {
                String[] image_url = url.split(",");
                map.put("image_url", image_url);
            } else {
                map.put("image_url", new String[0]);
            }

            //关联服务
            List<Map<String,Object>> joinlist = myRecommendMapper.findServiceJoinInfo(map);
            map.put("joinService", joinlist);
        }

        //发布人的会员名称全称查询
        String memberName = MapUtils.getString(params, "memberName");
        if (StringUtils.isNotBlank(memberName)) {
            //根据行政区划筛选，匹配发布咨询服务时选择的联系人的地址
            Iterator<Map<String,Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                String account = MapUtils.getString(map, "account");
                Map<String,Object> memberMap = new HashMap<>();
                memberMap.put("account", account);
                memberMap.put("memberName", memberName);
                Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
                memberMap.put("templateId", MapUtils.getString(useTemplate, "templateId"));
                Map<String, Object> memberInfo = myRecommendMapper.memberInfo(memberMap);
                if (MapUtils.isEmpty(memberInfo)) {
                    iterator.remove();
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> b1, Map<String, Object> b2) {
                    //按时间降序
                    return MapUtils.getString(b2, "create_time").compareTo(MapUtils.getString(b1, "create_time"));
                }
            });
        }

        int totalSize = list.size();
        List<Map<String, Object>> pageList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                pageList = PageUtil.pageForList(pageNum, pageSize, list);
            } catch (Exception e) {
                logger.error("查询数据异常:{]", e);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("list", pageList);
        resultMap.put("total", totalSize);

        return resultMap;
    }

    @Override
    public void operation(Map<String, Object> params) {
        String flag = MapUtils.getString(params, "flag");
        String account = MapUtils.getString(params, "account");
        int type = MapUtils.getInteger(params, "type");
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                map.put("account", account);
                map.put("type", type);
            }
            if ("0".equals(flag)) {
                //取消推荐
                myRecommendMapper.batchCancelRecommend(list);
            } else if ("1".equals(flag)) {
                //推荐
                myRecommendMapper.batchSaveRecommend(list);
            }
        }
    }

    @Override
    public Map<String, Object> baseList(Map<String, Object> params) {

        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;
        Integer number = MapUtils.getInteger(params, "pageNum");
        int pageNum = (number == null) ? 1 : number;
        params.remove("pageSize");
        params.remove("pageNum");

        List<Map<String, Object>> list;
        //flag为0表示查询所有基地 flag为1表示查询已推荐基地
        String flag = MapUtils.getString(params, "flag");
        if ("0".equals(flag)) {
            list = myRecommendMapper.baseList(params);
        } else if ("1".equals(flag)) {
            list = myRecommendMapper.recommendBaseList(params);
        } else {
            list = new ArrayList<>();
        }
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                String coordinate = MapUtils.getString(map, "coordinate");
                if (StringUtils.isNotBlank(coordinate)) {
                    String[] split = coordinate.split(",");
                    map.put("longitude", split[0]);
                    map.put("latitude", split[1]);

                } else {
                    map.put("longitude", "");
                    map.put("latitude", "");
                }
                String account = MapUtils.getString(map, "account");
                Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
                if (MapUtils.isEmpty(useTemplate)) {
                    map.put("name", "暂无会员名称");
                } else {
                    Map<String,Object> memberInfo = perfectBasicService.findMemberName(useTemplate);
                    String memberName = MapUtils.getString(memberInfo, "memberName");
                    if (StringUtils.isBlank(memberName)) {
                        map.put("name", "暂无会员名称");
                    } else {
                        map.put("name", memberName);
                    }
                }
            }

        } else {
            list = new ArrayList<>();
        }

        //发布人的会员名称全称查询
        String memberName = MapUtils.getString(params, "memberName");
        if (StringUtils.isNotBlank(memberName)) {
            //根据行政区划筛选，匹配发布咨询服务时选择的联系人的地址
            Iterator<Map<String,Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                String account = MapUtils.getString(map, "account");
                Map<String,Object> memberMap = new HashMap<>();
                memberMap.put("account", account);
                memberMap.put("memberName", memberName);
                Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
                memberMap.put("templateId", MapUtils.getString(useTemplate, "templateId"));
                Map<String, Object> memberInfo = myRecommendMapper.memberInfo(memberMap);
                if (MapUtils.isEmpty(memberInfo)) {
                    iterator.remove();
                }
            }
        }

        if (CollectionUtils.isNotEmpty(list)) {
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> b1, Map<String, Object> b2) {
                    //按时间降序
                    return MapUtils.getString(b2, "updateTime").compareTo(MapUtils.getString(b1, "updateTime"));
                }
            });
        }
        int totalSize = list.size();
        List<Map<String, Object>> pageList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                pageList = PageUtil.pageForList(pageNum, pageSize, list);
            } catch (Exception e) {
                logger.error("查询数据异常:{]", e);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("list", pageList);
        resultMap.put("total", totalSize);


        return resultMap;
    }

    @Override
    public PageInfo<List<Map<String, Object>>> expertList(Map<String, Object> params) {

        //查询专家信息
        PageUtil.initPage(params);

        List<Map<String, Object>> list;
        //flag为0表示查询所有专家 flag为1表示查询已推荐专家
        String flag = MapUtils.getString(params, "flag");
        if ("0".equals(flag)) {
            list = myRecommendMapper.expertList(params);
        } else if ("1".equals(flag)) {
            list = myRecommendMapper.recommendExpertList(params);
        } else {
            list = new ArrayList<>();
        }
        return new PageInfo(list);
    }

    @Override
    public Map<String, Object> productList(Map<String, Object> params) {
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;
        Integer number = MapUtils.getInteger(params, "pageNum");
        int pageNum = (number == null) ? 1 : number;
        params.remove("pageSize");
        params.remove("pageNum");

        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        Object object = shopServiceFeign.findRecommendProduct(params);
        JSONObject jsonObject = JSONObject.parseObject(object.toString());
        Map<String,Object> map1 = (Map<String, Object>) jsonObject.get("data");
        List<Map<String, Object>> productlist = (List<Map<String, Object>>) map1.get("list");
        //flag为0表示查询所有 flag为1表示查询已推荐
        String flag = MapUtils.getString(params, "flag");

        if (StringUtils.isNotBlank(MapUtils.getString(params, "memberName"))) {
            String memberName = MapUtils.getString(params, "memberName");
        }
        int totalSize = 0;
        if(productlist!=null&&productlist.size()>0){
            if ("0".equals(flag)) {
                for (Map map : productlist) {
                    params.put("id",map.get("id"));
                    Integer count = myRecommendMapper.productList(params);
                    if (count > 0) {
                        map.put("isRecommend","已推荐");
                    }else {
                        map.put("isRecommend","未推荐");
                    }
                }
                list =  productlist;

            } else if ("1".equals(flag)) {
                for (Map map : productlist) {
                    params.put("id",map.get("id"));
                    Integer count = myRecommendMapper.productList(params);
                    if (count > 0) {
                        map.put("isRecommend","已推荐");
                        list.add(map);
                    }
                }

            }
        } else {
            list = new ArrayList<>();

        }

        //发布人的会员名称全称查询
        String memberName = MapUtils.getString(params, "memberName");
        if (StringUtils.isNotBlank(memberName)) {
            //根据行政区划筛选，匹配发布咨询服务时选择的联系人的地址
            Iterator<Map<String,Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                String account = MapUtils.getString(map, "account");
                Map<String,Object> memberMap = new HashMap<>();
                memberMap.put("account", account);
                memberMap.put("memberName", memberName);
                Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
                memberMap.put("templateId", MapUtils.getString(useTemplate, "templateId"));
                Map<String, Object> memberInfo = myRecommendMapper.memberInfo(memberMap);
                if (MapUtils.isEmpty(memberInfo)) {
                    iterator.remove();
                }
            }
        }

        totalSize = list.size();

        List<Map<String, Object>> pageList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                pageList = PageUtil.pageForList(pageNum, pageSize, list);
            } catch (Exception e) {
                logger.error("查询数据异常:{]", e);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("list", pageList);
        resultMap.put("total", totalSize);
        return resultMap;
    }


}
