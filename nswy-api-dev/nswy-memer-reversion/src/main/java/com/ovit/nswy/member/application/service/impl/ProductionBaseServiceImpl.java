package com.ovit.nswy.member.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.application.mapper.MyRecommendMapper;
import com.ovit.nswy.member.application.mapper.ProductionBaseMapper;
import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.perfectInfo.mapper.LandInformationMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.relaxng.datatype.DatatypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductionBaseServiceImpl implements ProductionBaseService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private LandInformationMapper landInformationMapper;

    @Autowired
    private ProductionBaseMapper productionBaseMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private MyRecommendMapper myRecommendMapper;

    /**
     * 查询地块信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> landInfo(Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        List<Map<String, Object>> landList = new ArrayList<>();
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        //查询用户最大的年度文件id
        if (MapUtils.isNotEmpty(useTemplate)) {
            Map<String,Object> yearInfo = perfectBasicService.findNewYearInfo(account);
            useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
            //查询地块信息
            List<Map<String, Object>> landInfo = landInformationMapper.findLandInfo(useTemplate);
            if (CollectionUtils.isNotEmpty(landInfo)) {
                for (Map<String,Object> land : landInfo) {
                    Map<String,Object> map = new HashMap<>();
                    StringBuilder landSb = new StringBuilder("");
                    String landCode = MapUtils.getString(land, "landCode");
                    if (StringUtils.isNotBlank(landCode)) {
                        landSb.append(landCode);
                    } else {
                        landSb.append("");
                    }
                    String landName = MapUtils.getString(land, "landName");
                    if (StringUtils.isNotBlank(landName)) {
                        landSb.append(landName);
                    } else {
                        landSb.append("");
                    }
                    map.put("land", landSb.toString());
                    String location = MapUtils.getString(land, "location");
                    map.put("location", (StringUtils.isNotBlank(location) ? location : ""));
                    String landUser = MapUtils.getString(land, "landUser");
                    map.put("contactName", (StringUtils.isNotBlank(landUser) ? landUser : ""));

                    StringBuilder coordinateSb = new StringBuilder("");
                    String longitude = MapUtils.getString(land, "longitude");
                    String latitude = MapUtils.getString(land, "latitude");
                    if (StringUtils.isNotBlank(longitude)) {
                        coordinateSb.append(longitude).append(",");
                    } else {
                        coordinateSb.append(",");
                    }
                    if (StringUtils.isNotBlank(latitude)) {
                        coordinateSb.append(latitude);
                    } else {
                        coordinateSb.append("");
                    }

                    map.put("coordinate", coordinateSb.toString());
                    map.put("landId", MapUtils.getString(land, "landId"));
                    landList.add(map);
                }

            } else {
                landList = new ArrayList<>();
            }
        }
        return landList;
    }

    /**
     * 新增基地
     * @param params
     */
    @Override
    public void saveOrUpdate(Map<String, Object> params) {

        Integer id = MapUtils.getInteger(params, "id");
        if (id != null) {
            //基地编辑
            productionBaseMapper.update(params);

        } else {
            //基地新增
            productionBaseMapper.save(params);
            syncPerfectInfo(params);
        }
    }

    /**
     * 删除基地
     * @param id
     */
    @Override
    public void delete(int id) {
        productionBaseMapper.delete(id);
    }

    /**
     * 生产基地列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> list(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        //查询基地列表
        List<Map<String, Object>> list = productionBaseMapper.list(params);

        return new PageInfo<>(list);
    }

    /**
     * 物联设施类别字典
     * @return
     */
    @Override
    public List<Map<String, Object>> facilitiesType() {
        return productionBaseMapper.facilitiesType();
    }

    /**
     * 生产基地-查询基础信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findBaseInfo(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        //基础信息
        Map<String, Object> baseInfo = productionBaseMapper.find(params);
        int complete = 0;
        if (MapUtils.isNotEmpty(baseInfo)) {
            resultMap.put("baseInfo", baseInfo);
            complete = MapUtils.getInteger(baseInfo, "complete");
        } else {
            resultMap.put("baseInfo", "");
        }
        resultMap.put("complete", complete);
        //联系人信息
        List<Map<String, Object>> contactList = productionBaseMapper.findContactInfo(params);
        if (CollectionUtils.isNotEmpty(contactList)) {
            for (Map<String,Object> map : contactList) {
                String images = MapUtils.getString(map,"image");
                if (StringUtil.isNotBlank(images)) {
                    String[] image = images.split(";");
                    map.put("image", image);
                } else {
                    map.put("image", new String[]{});
                }
                //权限修改为布尔类型
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key.endsWith("_status")) {
                        Integer status = MapUtils.getInteger(map, key);
                        map.put(key, (status == 1) ? true : false);
                    }
                }
            }

            resultMap.put("contactInfo", contactList);

        } else {
            resultMap.put("contactInfo", new ArrayList<>());
        }

        return resultMap;

        //物联设施信息
        /*List<Map<String, Object>> facilities = productionBaseMapper.findFacilities(params);
        if (CollectionUtils.isNotEmpty(facilities)) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Map<String,Object> map : facilities) {
                //设备id
                Integer facilitiesId = MapUtils.getInteger(map, "facilitiesId");
                //获取设备名称
                String facilitiesName = "";
                if (facilitiesId != null) {
                    facilitiesName = productionBaseMapper.getFacilitiesType(facilitiesId);
                }
                Map<String,Object> deal = new HashMap<>();
                deal.put("facilitiesId", (facilitiesId == null ? "" : facilitiesId));
                deal.put("facilitiesName", (StringUtils.isNotBlank(facilitiesName) ? facilitiesName : ""));
                JSONArray facilitiesArray = JSONArray.parseArray(MapUtils.getString(map, "facilitiesInfo"));
                if (facilitiesArray == null) {
                    deal.put("facilities", new ArrayList<>());
                } else {
                    deal.put("facilities", facilitiesArray);
                }
                list.add(deal);
            }
            resultMap.put("facilitiesInfo", list);

        } else {
            resultMap.put("facilitiesInfo", new ArrayList<>());
        }*/

    }

    /**
     * 生产基地-保存基本信息
     * @param params
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveBaseInfo(Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        //先更新基础信息
        Map<String,Object> baseInfo = MapUtils.getMap(params, "baseInfo");
        if (MapUtils.isNotEmpty(baseInfo)) {
            productionBaseMapper.update(baseInfo);
        }
        String baseId = MapUtils.getString(baseInfo, "id");
        Map<String,Object> commonMap = new HashMap<>();
        commonMap.put("account", account);
        commonMap.put("baseId", baseId);
        //保存联系人信息
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("contactInfo");
        if (CollectionUtils.isNotEmpty(list)) {
            List<Map<String, Object>> contact = new ArrayList<>();
            for (Map<String,Object> map : list) {
                Map<String,Object> contactMap = new HashMap<>();
                contactMap.putAll(commonMap);
                String images = MapUtils.getString(map, "image");
                JSONArray image = JSONArray.parseArray(images);
                if (image.size() == 0) {
                    map.put("image", "");
                } else {
                    String[] pictures = ImageUtil.getJsonToStringArray(images);
                    String pictureMerge = ImageUtil.stringArrayMerge(pictures);
                    map.put("image", pictureMerge);
                }
                contactMap.putAll(map);
                contact.add(contactMap);
            }
            //先删除原有数据
            productionBaseMapper.deleteContactInfo(commonMap);
            //批量保存联系人信息
            productionBaseMapper.batchSaveContact(contact);
        } else {
            //先删除原有数据
            productionBaseMapper.deleteContactInfo(commonMap);
        }

    }

    /**
     * 查询生产基地详细信息的模块列表
     * @return
     */
    @Override
    public List<Map<String, Object>> findModuleInfo(Map<String,Object> params) {
        getYearIdAndTemplateId(params);
        List<String> appList = new ArrayList<>();
        appList.add("1b32f1bd09e74391bc11bad85538477c");
        appList.add("68b7664512974805b84c13e9cf37b7df");
        appList.add("00e1a625065d481bbf418bcf7ff102be");
        appList.add("e605d78bd67c47eeb0a444b243a338e4");
        appList.add("0e662d6e5872432088131c6e6bc3b967");
        params.put("appList", appList);
        return productionBaseMapper.findModuleInfo(params);
    }

    /**
     * 查询用户某个大模块下的子模块
     * 通过app_id查询reversion_sys_dict得到
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findSubModule(Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        getYearIdAndTemplateId(params);
        List<Map<String,Object>> moduleList = perfectBasicMapper.findDynamicSubModule(params);
        return moduleList;
    }

    /**
     * 新增基地时同步完善信息里面的数据
     * @param params
     */
    private void syncPerfectInfo(Map<String,Object> params) {

        Thread syncThread = new Thread("syncThread") {
            public void run() {
                String account = MapUtils.getString(params, "account");
                Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
                //查询用户最大的年度文件id
                if (MapUtils.isNotEmpty(useTemplate)) {
                    String baseId = MapUtils.getString(params, "id");
                    Map<String,Object> yearInfo = perfectBasicService.findNewYearInfo(account);
                    useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
                    useTemplate.put("baseId", baseId);
                    useTemplate.put("landId", MapUtils.getString(params, "landId"));
                    //同步数据
                    productionBaseMapper.syncMemberLocation(useTemplate);
                    productionBaseMapper.syncMemberLatitudeLongitude(useTemplate);
                    productionBaseMapper.syncMemberNeighbor(useTemplate);
                    productionBaseMapper.syncMemberLiveAddress(useTemplate);
                    productionBaseMapper.syncLandAreaInfo(useTemplate);
                    productionBaseMapper.syncTopographicFeature(useTemplate);
                    productionBaseMapper.syncClimateInfo(useTemplate);
                    productionBaseMapper.syncBiologicalResources(useTemplate);
                    productionBaseMapper.syncMineralsInfo(useTemplate);
                    productionBaseMapper.syncLandInfo(useTemplate);
                    productionBaseMapper.syncSoilContent(useTemplate);
                    productionBaseMapper.syncSoilQuality(useTemplate);
                    productionBaseMapper.syncWaterQuality(useTemplate);
                    productionBaseMapper.syncLandUse(useTemplate);
                    productionBaseMapper.syncAirQuaByAccount(useTemplate);
                    productionBaseMapper.syncEcologyEnvQua(useTemplate);
                    productionBaseMapper.syncSurfaceWaterQua(useTemplate);
                    productionBaseMapper.syncSecurityFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncEducationFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncOldAgeWelfareFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncOtherFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncGarbageDisposaFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncWaterConservancyFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncCultureFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncTapWaternFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncSportsFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncCommunicationFacilitiesInfo(useTemplate);
                    productionBaseMapper.syncTrafficInfo(useTemplate);
                    productionBaseMapper.syncIndustryInfo(useTemplate);
                    productionBaseMapper.syncBreedInfo(useTemplate);
                    productionBaseMapper.syncFarmProduct(useTemplate);
                    productionBaseMapper.syncTechnicalProduct(useTemplate);
                    productionBaseMapper.syncServiceProduct(useTemplate);
                    productionBaseMapper.syncIotdeviceInfo(useTemplate);

                    //保存文字预览数据
                    List<Map<String, Object>> moduleList = findModuleInfo(useTemplate);
                    for (Map<String,Object> module : moduleList) {
                        String appId = MapUtils.getString(module, "appId");
                        useTemplate.put("appId", appId);
                        List<Map<String, Object>> subModuleList = findSubModule(useTemplate);
                        for (Map<String,Object> subModule : subModuleList) {
                            useTemplate.put("dictId", MapUtils.getString(subModule, "dictId"));
                            productionBaseMapper.syncTextPreview(useTemplate);
                        }
                    }

                }

            }
        };
        syncThread.start();

    }

    /**
     * 通过用户名查询用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findAirQuaByAccount(Map<String, Object> params) {

        Map<String, Object> envMap = productionBaseMapper.findAirQuaByAccount(params);
        if (MapUtils.isEmpty(envMap)) {
            envMap = new HashMap<>();
        }
        ConvertDataUtil.merge(envMap, "detectReport");
        //获取预览信息
        getPreviewInfo(envMap, params);
        return envMap;
    }

    /**
     * 通过account查到yearId和templateId
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getYearIdAndTemplateId(Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        //查询用户最大的年度文件id
        if (MapUtils.isNotEmpty(useTemplate)) {
            Map<String, Object> yearInfo = perfectBasicService.findNewYearInfo(account);
            params.put("templateId", MapUtils.getString(useTemplate, "templateId"));
            params.put("yearId", MapUtils.getString(yearInfo, "id"));
        }
        return params;
    }

    @Override
    public Map<String, Object> findAirQuaByAccountList(Map<String, Object> params) {
        Map<String, Object> envMap = productionBaseMapper.findAirQuaByAccount(params);
        return envMap;
    }

    /**
     * 新增用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public void insertAirQuaByAccount(Map<String, Object> params) {

        ConvertDataUtil.split(params, "detectReport");
        productionBaseMapper.insertAirQuaByAccount(params);
        //保存文字预览信息
        productionBaseMapper.saveTextPreview(params);

    }

    /**
     * 修改用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public void modifyAirQuaByAccount(Map<String, Object> params) {

        ConvertDataUtil.split(params, "detectReport");
        productionBaseMapper.modifyAirQuaByAccount(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = productionBaseMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            productionBaseMapper.saveTextPreview(params);
        } else {
            productionBaseMapper.updateTextPreview(params);
        }

    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        if (MapUtils.isEmpty(result)) {
            result = new HashMap<>();
        }
        Map<String,Object> textPreview = productionBaseMapper.findTextPreview(params);
        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("textPreview", MapUtils.getString(textPreview, "textPreview"));
        }else {
            result.put("textPreview", "");
        }

        return result;
    }

    /**
     * 通过用户名查询用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findSurfaceWaterQua(Map<String, Object> params) {

        Map<String, Object> surfaceMap = productionBaseMapper.findSurfaceWaterQua(params);
        if (MapUtils.isEmpty(surfaceMap)) {
            surfaceMap = new HashMap<>();
        }
        ConvertDataUtil.merge(surfaceMap, "detectReport");
        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            surfaceMap.put("propertyName", "");
        } else {
            surfaceMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(surfaceMap, params);
        return surfaceMap;
    }

    @Override
    public Map<String, Object> findSurfaceWaterQuaList(Map<String, Object> params) {
        Map<String, Object> surfaceMap = productionBaseMapper.findSurfaceWaterQua(params);
        return surfaceMap;
    }

    /**
     * 新增用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public void insertSurfaceWaterQua(Map<String, Object> params) {

        ConvertDataUtil.split(params, "detectReport");
        ConvertDataUtil.split(params, "waterQuality");
        productionBaseMapper.insertSurfaceWaterQua(params);
        productionBaseMapper.saveTextPreview(params);

    }

    /**
     * 修改用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public void modifySurfaceWaterQua(Map<String, Object> params) {

        ConvertDataUtil.split(params, "detectReport");
        ConvertDataUtil.split(params, "waterQuality");
        productionBaseMapper.modifySurfaceWaterQua(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = productionBaseMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            productionBaseMapper.saveTextPreview(params);
        } else {
            productionBaseMapper.updateTextPreview(params);
        }
    }

    /**
     * 通过用户名查询用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findEcologyEnvQua(Map<String, Object> params) {

        Map<String, Object> ecologyMap = productionBaseMapper.findEcologyEnvQua(params);
        if (MapUtils.isEmpty(ecologyMap)) {
            ecologyMap = new HashMap<>();
        }
        ConvertDataUtil.merge(ecologyMap, "detectReport");
        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            ecologyMap.put("propertyName", "");
        } else {
            ecologyMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(ecologyMap, params);
        return ecologyMap;
    }

    @Override
    public Map<String, Object> findEcologyEnvQuaList(Map<String, Object> params) {
        Map<String, Object> ecologyMap = productionBaseMapper.findEcologyEnvQua(params);
        return ecologyMap;
    }

    /**
     * 新增用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public void insertEcologyEnvQua(Map<String, Object> params) {
        ConvertDataUtil.split(params, "detectReport");
        ConvertDataUtil.split(params, "ecologyEnv");
        productionBaseMapper.insertEcologyEnvQua(params);
        productionBaseMapper.saveTextPreview(params);

    }

    /**
     * 修改用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public void modifyEcologyEnvQua(Map<String, Object> params) {

        ConvertDataUtil.split(params, "detectReport");
        ConvertDataUtil.split(params, "ecologyEnv");
        productionBaseMapper.modifyEcologyEnvQua(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = productionBaseMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            productionBaseMapper.saveTextPreview(params);
        } else {
            productionBaseMapper.updateTextPreview(params);
        }

    }

    /**
     * 通过用户名、基地ID查询地块信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findLandInfo(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> landList = productionBaseMapper.findLandInfo(params);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        if (CollectionUtils.isEmpty(landList)) {
            landList = new ArrayList<>();
            resultMap.put("list", landList);
        } else {
            resultMap.put("list", landList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateLandInfo(Map<String, Object> params) {

        //更新地块信息
        productionBaseMapper.updateLandInfo(params);

    }

    @Override
    public Map<String, Object> findSoilContent(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> soilList = productionBaseMapper.findSoilContent(params);
        for (Map<String, Object> map : soilList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块土壤含量信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        if (CollectionUtils.isEmpty(soilList)) {
            soilList = new ArrayList<>();
            resultMap.put("list", soilList);
        } else {
            resultMap.put("list", soilList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateSoilContent(Map<String, Object> params) {

        //更新地块土壤含量信息
        ConvertDataUtil.split(params, "pictureList");
        productionBaseMapper.updateSoilContent(params);

    }

    @Override
    public Map<String, Object> findSoilQuality(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> qualityList = productionBaseMapper.findSoilQuality(params);
        for (Map<String, Object> map : qualityList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块土壤质量信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        if (CollectionUtils.isEmpty(qualityList)) {
            qualityList = new ArrayList<>();
            resultMap.put("list", qualityList);
        } else {
            resultMap.put("list", qualityList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateSoilQuality(Map<String, Object> params) {

        //更新地块土壤质量信息
        ConvertDataUtil.split(params, "pictureList");
        productionBaseMapper.updateSoilQuality(params);

    }

    @Override
    public Map<String, Object> findWaterQuality(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> waterList = productionBaseMapper.findWaterQuality(params);
        for (Map<String, Object> map : waterList) {
            ConvertDataUtil.merge(map, "pictureList");
        }
        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "地块水质信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        if (CollectionUtils.isEmpty(waterList)) {
            waterList = new ArrayList<>();
            resultMap.put("list", waterList);
        } else {
            resultMap.put("list", waterList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateWaterQuality(Map<String, Object> params) {

        //更新地块水质信息
        ConvertDataUtil.split(params, "pictureList");
        productionBaseMapper.updateWaterQuality(params);

    }

    @Override
    public Map<String, Object> findLandUse(Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        //农用地
        params.put("type", "1");
        List<Map<String, Object>> agricultural = productionBaseMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(agricultural)) {
            agricultural = new ArrayList<>();
        }
        resultMap.put("agricultural", agricultural);
        //建设用地
        params.put("type", "2");
        List<Map<String, Object>> construction = productionBaseMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(construction)) {
            construction = new ArrayList<>();
        }
        resultMap.put("construction", construction);
        //未利用地
        params.put("type", "3");
        List<Map<String, Object>> future = productionBaseMapper.findLandUse(params);
        if (CollectionUtils.isEmpty(future)) {
            future = new ArrayList<>();
        }
        resultMap.put("future", future);

        //获取属性名称
        /*Map<String, Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "土地利用现状信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;

    }

    @Override
    public void saveLandUse(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }

        productionBaseMapper.saveLandUse(list);
    }

    @Override
    public void deleteLandUse(Map<String, Object> params) {

        productionBaseMapper.deleteLandUse(params);

    }

    @Override
    public Map<String, Object> getLandType(Map<String, Object> params) {
        getYearIdAndTemplateId(params);
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> landList = productionBaseMapper.findLandInfo(params);
        if (CollectionUtils.isNotEmpty(landList)) {
            String landId = MapUtils.getString(landList.get(0), "landId");
            params.put("landId", landId);
        }
        List<Map<String, Object>> landInfo = landInformationMapper.findLandInfo(params);
        if (CollectionUtils.isNotEmpty(landInfo)) {
            resultMap = landInfo.get(0);
        }

        return resultMap;
    }

    @Override
    public void saveTextPreview(Map<String, Object> params) {

        //更新预览、完善程度等信息
        Map<String,Object> previewMap = productionBaseMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            productionBaseMapper.saveTextPreview(params);
        } else {
            productionBaseMapper.updateTextPreview(params);
        }
    }

    @Override
    public Map<String,Object> findGeographicalPosition(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> textPreview =  productionBaseMapper.physicalTextPreview(params);

        Map<String,Object> member = new HashMap<>();
        member.put("dictId", MapUtils.getString(params,"parent_id"));
        member.put("account", MapUtils.getString(params,"user_id"));
        member.put("baseId", MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);

        String[] string = new String[]{"会员所在地","会员所在地经纬度","会员四邻","查看实况地址"};
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", string);
        } else {
            String pictureList =MapUtils.getString(propertyInfo, "propertyName");
            if(StringUtil.isNotEmpty(pictureList)) {
                String[] members = pictureList.split(";");
                map.put("propertyName", members);
            } else {
                map.put("propertyName", new String[]{});
            }
        }
        List<Map<String,Object>> memberLocation =  productionBaseMapper.findMemberLocation(params);
        if(CollectionUtils.isNotEmpty(memberLocation)){
            map.put("memberLocation", memberLocation.get(0));
        }else{
            map.put("memberLocation", new HashMap<>());
        }

        List<Map<String,Object>> memberLatitudeLongitude = productionBaseMapper.findMemberLatitudeLongitude(params);
        if(CollectionUtils.isNotEmpty(memberLatitudeLongitude)) {
            map.put("memberLatitudeLongitude", memberLatitudeLongitude.get(0));
        } else {
            map.put("memberLatitudeLongitude", new HashMap<>());
        }

        List<Map<String,Object>> memberNeighbor = productionBaseMapper.findMemberNeighbor(params);
        if(CollectionUtils.isNotEmpty(memberNeighbor)) {
            map.put("memberNeighbor",memberNeighbor);
        } else {
            List<Map<String,Object>> neighborList = new ArrayList<>();
            Map<String,Object> nameMap = new HashMap<>();
            nameMap.put("name","最东点经纬度");
            neighborList.add(nameMap);

            Map<String,Object> nameMap1 = new HashMap<>();
            nameMap1.put("name","最南点经纬度");
            neighborList.add(nameMap1);

            Map<String,Object> nameMap2 = new HashMap<>();
            nameMap2.put("name","最西点经纬度");
            neighborList.add(nameMap2);

            Map<String,Object> nameMap3 = new HashMap<>();
            nameMap3.put("name","最北点经纬度");
            neighborList.add(nameMap3);
            map.put("memberNeighbor",neighborList);
        }

        List<Map<String,Object>> memberLiveAddress = productionBaseMapper.findMemberLiveAddress(params);
        if(CollectionUtils.isNotEmpty(memberLiveAddress)) {
            map.put("memberLiveAddress", memberLiveAddress);
        } else {
            map.put("memberLiveAddress", new ArrayList<>());
        }

        if(MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview",textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveGeographicalPosition(Map<String,Object> params){
        String user_id = MapUtils.getString(params,"user_id");
        //会员所在地
        Map<String,Object> memberLocations = MapUtils.getMap(params,"memberLocation");
        Map<String,Object> memberLocation = (Map<String,Object>)memberLocations.get("memberLocation");
        memberLocation.put("user_id", user_id);
        memberLocation.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveMemberLocation(memberLocation);
        //会员所在地经纬度
        Map<String,Object> memberLatitudeLongitudes = MapUtils.getMap(params,"memberLatitudeLongitude");
        Map<String,Object> memberLatitudeLongitude = (Map<String,Object>)memberLatitudeLongitudes.get("memberLatitudeLongitude");
        memberLatitudeLongitude.put("user_id",user_id);
        memberLatitudeLongitude.put("baseId",MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveMemberLatitudeLongitude(memberLatitudeLongitude);
        //会员四邻
        Map<String,Object> memberNeighbors =(Map<String, Object>) params.get("memberNeighbor");
        List<Map<String,Object>> memberNeighborList = (List<Map<String,Object>>)memberNeighbors.get("memberNeighbor");
        if(CollectionUtils.isNotEmpty(memberNeighborList)) {
            for (Map<String,Object> map : memberNeighborList) {
                map.put("user_id", user_id);
                map.put("baseId", MapUtils.getString(params,"baseId"));
                productionBaseMapper.saveMemberNeighbor(map);
            }
        } else {
            Map<String,Object> map = new HashMap<>();
            map.put("user_id", user_id);
            map.put("baseId", MapUtils.getString(params,"baseId"));
            productionBaseMapper.saveMemberNeighbor(map);
        }
        //查看实况地址
        Map<String,Object> memberLiveAddres =(Map<String, Object>) params.get("memberLiveAddress");
        List<Map<String,Object>> memberLiveAddressList = (List<Map<String, Object>>) memberLiveAddres.get("memberLiveAddress");
        if(CollectionUtils.isNotEmpty(memberLiveAddressList)){
            for(Map<String,Object> map :memberLiveAddressList ){
                map.put("user_id", user_id);
                map.put("baseId", MapUtils.getString(params,"baseId"));
                productionBaseMapper.saveMemberLiveAddress(map);
            }
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("user_id",user_id);
            map.put("baseId", MapUtils.getString(params,"baseId"));
            productionBaseMapper.saveMemberLiveAddress(map);
        }
        saveBaseTextPreview(params);
    }

    @Override
    public void deleteMemberNeighbor(Map<String,Object> params){
        productionBaseMapper.deleteMemberNeighbor(params);
    }

    @Override
    public void deleteMemberLiveAddress(Map<String,Object> params){
        productionBaseMapper.deleteMemberLiveAddress(params);
    }

    @Override
    public Map<String,Object> findLandAreaInfo(Map<String,Object> params){
        /**
         *
         判断reversion_land_area_info表中是否存在数据， 如果存在数据取reversion_land_area_info表中land_area_name为模块属性 反之查询sys_dict表
         */
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId", MapUtils.getString(params,"parent_id"));
        member.put("account", MapUtils.getString(params,"user_id"));
        member.put("baseId", MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("landAreaInfo_name", "国土面积信息");
        } else {
            map.put("landAreaInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview = productionBaseMapper.physicalTextPreview(params);
        List<Map<String,Object>> list = productionBaseMapper.findLandAreaInfo(params);
        if (CollectionUtils.isNotEmpty(list)) {
            map.put("landAreaInfo",list);
        } else{
            List<Map<String,Object>> landList = new ArrayList<>();
            Map<String,Object> nameMap = new HashMap<>();
            nameMap.put("land_area","国土总面积");
            landList.add(nameMap);

            Map<String,Object> nameMap1 = new HashMap<>();
            nameMap1.put("land_area","山地");
            landList.add(nameMap1);

            Map<String,Object> nameMap2 = new HashMap<>();
            nameMap2.put("land_area","盆地");
            landList.add(nameMap2);

            Map<String,Object> nameMap3 = new HashMap<>();
            nameMap3.put("land_area","丘陵");
            landList.add(nameMap3);

            Map<String,Object> nameMap4 = new HashMap<>();
            nameMap4.put("land_area","平原");
            landList.add(nameMap4);

            Map<String,Object> nameMap5 = new HashMap<>();
            nameMap5.put("land_area","高原");
            landList.add(nameMap5);

            map.put("landAreaInfo",landList);
        }
        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview", "");
            text.put("sys_dict_id", "");
            text.put("user_id", "");
            map.put("textPreview", text);
        }
        return map;
    }

    private void saveBaseTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");
        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id", MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id", MapUtils.getString(params,"user_id"));
            textPreview.put("baseId", MapUtils.getString(params,"baseId"));
            productionBaseMapper.saveBaseTextPreview(textPreview);
        }

    }

    @Override
    public void saveLandAreaInfo(Map<String,Object> params){

        Map<String,Object> landAreaInfos = (Map<String, Object>) params.get("landAreaInfo");
        List<Map<String,Object>> landAreaInfo = (List<Map<String, Object>>) landAreaInfos.get("landAreaInfo");
        String user_id = MapUtils.getString(params,"user_id");
        for(Map<String,Object> map : landAreaInfo){
            map.put("user_id", user_id);
            map.put("dictId", MapUtils.getString(params,"dictId"));
            map.put("baseId", MapUtils.getString(params,"baseId"));
            productionBaseMapper.saveLandAreaInfo(map);
        }
        saveBaseTextPreview(params);

    }

    @Override
    public void deleteLandAreaInfo(Map<String,Object> params){
        productionBaseMapper.deleteLandAreaInfo(params);
    }

    @Override
    public Map<String,Object> findTopographicFeature(Map<String,Object> params){
        /**
         *
         判断reversion_topographic_features_info表中是否存在数据， 如果存在数据取reversion_topographic_features_info表中topographic_features_name为模块属性 反之查询sys_dict表
         */
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("baseId",MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("topographicFeature_name", "地形地貌信息");
        } else {
            map.put("topographicFeature_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview = productionBaseMapper.physicalTextPreview(params);
        Map<String,Object> list = productionBaseMapper.findTopographicFeature(params);
        if (MapUtils.isNotEmpty(list)) {
            String topographic = MapUtils.getString(list,"topographic");
            if (StringUtil.isNotEmpty(topographic)) {
                String[] members = topographic.split(";");
                list.put("topographic",members);
            } else {
                list.put("topographic", new String[]{});
            }
            String features = MapUtils.getString(list,"features");
            if (StringUtil.isNotEmpty(features)) {
                String[] members = features.split(";");
                list.put("features",members);
            } else {
                list.put("features", new String[]{});
            }
            map.put("topographicFeature",list);
        } else {
            map.put("topographicFeature",new HashMap<>());
        }
        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview", "");
            text.put("sys_dict_id", "");
            text.put("user_id", "");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveTopographicFeature(Map<String,Object> params){
        Map<String,Object> topographicFeature = (Map<String, Object>) params.get("topographicFeature");
        String topographic = MapUtils.getString(topographicFeature,"topographic");
        if(StringUtil.isNotEmpty(topographic)) {
            JSONArray topographic_model = JSONArray.parseArray(topographic);
            if (topographic_model.size() == 0) {
                topographicFeature.put("topographic", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(topographic);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                topographicFeature.put("topographic", memberMerge);
            }
        } else {
            topographicFeature.put("topographic", "");
        }
        String features = MapUtils.getString(topographicFeature,"features");
        if (StringUtil.isNotEmpty(features)) {
            JSONArray features_model = JSONArray.parseArray(features);
            if (features_model.size() == 0) {
                topographicFeature.put("features", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(features);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                topographicFeature.put("features", memberMerge);
            }
        } else {
            topographicFeature.put("features", "");
        }

        String user_id = MapUtils.getString(params,"user_id");
        topographicFeature.put("user_id", user_id);
        topographicFeature.put("baseId",MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveTopographicFeature(topographicFeature);
        saveBaseTextPreview(params);

    }

    @Override
    public Map<String,Object> findClimateInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("baseId",MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("climateInfo_name", "气候信息");
        } else {
            map.put("climateInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview = productionBaseMapper.physicalTextPreview(params);

        Map<String,Object> list = productionBaseMapper.findClimateInfo(params);
        if(MapUtils.isNotEmpty(list)){
            String climate_class = MapUtils.getString(list,"climate_class");
            if(StringUtil.isNotEmpty(climate_class)){
                String[] members = climate_class.split(";");
                list.put("climate_class",members);
            }else{
                list.put("climate_class", new String[]{});
            }
            String sunshine_time = MapUtils.getString(list,"sunshine_time");
            if(StringUtil.isNotEmpty(sunshine_time)){
                String[] members = sunshine_time.split(";");
                list.put("sunshine_time",members);
            }else{
                list.put("sunshine_time", new String[]{});
            }
            String average_temperature = MapUtils.getString(list,"average_temperature");
            if(StringUtil.isNotEmpty(average_temperature)){
                String[] members = average_temperature.split(";");
                list.put("average_temperature",members);
            }else{
                list.put("average_temperature", new String[]{});
            }
            String accumulated_temperature = MapUtils.getString(list,"accumulated_temperature");
            if(StringUtil.isNotEmpty(accumulated_temperature)){
                String[] members = accumulated_temperature.split(";");
                list.put("accumulated_temperature",members);
            }else{
                list.put("accumulated_temperature", new String[]{});
            }
            String diurnal_temperature_difference = MapUtils.getString(list,"diurnal_temperature_difference");
            if(StringUtil.isNotEmpty(diurnal_temperature_difference)){
                String[] members = diurnal_temperature_difference.split(";");
                list.put("diurnal_temperature_difference",members);
            }else{
                list.put("diurnal_temperature_difference", new String[]{});
            }
            String no_frost_date = MapUtils.getString(list,"no_frost_date");
            if(StringUtil.isNotEmpty(no_frost_date)){
                String[] members = no_frost_date.split(";");
                list.put("no_frost_date",members);
            }else{
                list.put("no_frost_date", new String[]{});
            }
            String avg_precipitation = MapUtils.getString(list,"avg_precipitation");
            if(StringUtil.isNotEmpty(avg_precipitation)){
                String[] members = avg_precipitation.split(";");
                list.put("avg_precipitation",members);
            }else{
                list.put("avg_precipitation", new String[]{});
            }
            String avg_vaporization = MapUtils.getString(list,"avg_vaporization");
            if(StringUtil.isNotEmpty(avg_vaporization)){
                String[] members = avg_vaporization.split(";");
                list.put("avg_vaporization",members);
            }else{
                list.put("avg_vaporization", new String[]{});
            }
            String avg_precipitation_day = MapUtils.getString(list,"avg_precipitation_day");
            if(StringUtil.isNotEmpty(avg_precipitation_day)){
                String[] members = avg_precipitation_day.split(";");
                list.put("avg_precipitation_day",members);
            }else{
                list.put("avg_precipitation_day", new String[]{});
            }
            String precipitation_period = MapUtils.getString(list,"precipitation_period");
            if(StringUtil.isNotEmpty(precipitation_period)){
                String[] members = precipitation_period.split(";");
                list.put("precipitation_period",members);
            }else{
                list.put("precipitation_period", new String[]{});
            }
            map.put("climateInfo",list);
        } else {
            map.put("climateInfo",new HashMap<>());
        }
        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview", "");
            text.put("sys_dict_id", "");
            text.put("user_id", "");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveClimateInfo(Map<String,Object> params){
        Map<String,Object> climateInfo = (Map<String, Object>) params.get("climateInfo");

        String climate_class = MapUtils.getString(climateInfo,"climate_class");
        if(StringUtil.isNotEmpty(climate_class)) {
            JSONArray topographic_model = JSONArray.parseArray(climate_class);
            if (topographic_model.size() == 0) {
                climateInfo.put("climate_class", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(climate_class);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("climate_class", memberMerge);
            }
        }else{
            climateInfo.put("climate_class", "");
        }

        String sunshine_time = MapUtils.getString(climateInfo,"sunshine_time");
        if(StringUtil.isNotEmpty(sunshine_time)) {
            JSONArray topographic_model = JSONArray.parseArray(sunshine_time);
            if (topographic_model.size() == 0) {
                climateInfo.put("sunshine_time", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(sunshine_time);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("sunshine_time", memberMerge);
            }
        }else{
            climateInfo.put("sunshine_time", "");
        }

        String average_temperature = MapUtils.getString(climateInfo,"average_temperature");
        if(StringUtil.isNotEmpty(average_temperature)) {
            JSONArray topographic_model = JSONArray.parseArray(average_temperature);
            if (topographic_model.size() == 0) {
                climateInfo.put("average_temperature", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(average_temperature);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("average_temperature", memberMerge);
            }
        }else{
            climateInfo.put("average_temperature", "");
        }

        String accumulated_temperature = MapUtils.getString(climateInfo,"accumulated_temperature");
        if(StringUtil.isNotEmpty(accumulated_temperature)) {
            JSONArray topographic_model = JSONArray.parseArray(accumulated_temperature);
            if (topographic_model.size() == 0) {
                climateInfo.put("accumulated_temperature", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(accumulated_temperature);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("accumulated_temperature", memberMerge);
            }
        }else{
            climateInfo.put("accumulated_temperature", "");
        }

        String diurnal_temperature_difference = MapUtils.getString(climateInfo,"diurnal_temperature_difference");
        if(StringUtil.isNotEmpty(diurnal_temperature_difference)) {
            JSONArray topographic_model = JSONArray.parseArray(diurnal_temperature_difference);
            if (topographic_model.size() == 0) {
                climateInfo.put("diurnal_temperature_difference", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(diurnal_temperature_difference);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("diurnal_temperature_difference", memberMerge);
            }
        }else{
            climateInfo.put("diurnal_temperature_difference", "");
        }


        String no_frost_date = MapUtils.getString(climateInfo,"no_frost_date");
        if(StringUtil.isNotEmpty(no_frost_date)) {
            JSONArray topographic_model = JSONArray.parseArray(no_frost_date);
            if (topographic_model.size() == 0) {
                climateInfo.put("no_frost_date", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(no_frost_date);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("no_frost_date", memberMerge);
            }
        }else{
            climateInfo.put("no_frost_date", "");
        }

        String avg_precipitation = MapUtils.getString(climateInfo,"avg_precipitation");
        if(StringUtil.isNotEmpty(avg_precipitation)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_precipitation);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_precipitation", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_precipitation);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_precipitation", memberMerge);
            }
        }else{
            climateInfo.put("avg_precipitation", "");
        }

        String avg_vaporization = MapUtils.getString(climateInfo,"avg_vaporization");
        if(StringUtil.isNotEmpty(avg_vaporization)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_vaporization);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_vaporization", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_vaporization);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_vaporization", memberMerge);
            }
        }else{
            climateInfo.put("avg_vaporization", "");
        }

        String avg_precipitation_day = MapUtils.getString(climateInfo,"avg_precipitation_day");
        if(StringUtil.isNotEmpty(avg_precipitation_day)) {
            JSONArray topographic_model = JSONArray.parseArray(avg_precipitation_day);
            if (topographic_model.size() == 0) {
                climateInfo.put("avg_precipitation_day", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(avg_precipitation_day);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("avg_precipitation_day", memberMerge);
            }
        }else{
            climateInfo.put("avg_precipitation_day", "");
        }

        String precipitation_period = MapUtils.getString(climateInfo,"precipitation_period");
        if(StringUtil.isNotEmpty(precipitation_period)) {
            JSONArray topographic_model = JSONArray.parseArray(precipitation_period);
            if (topographic_model.size() == 0) {
                climateInfo.put("precipitation_period", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(precipitation_period);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                climateInfo.put("precipitation_period", memberMerge);
            }
        }else{
            climateInfo.put("precipitation_period", "");
        }

        String user_id = MapUtils.getString(params,"user_id");
        climateInfo.put("user_id",user_id);
        climateInfo.put("baseId",MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveClimateInfo(climateInfo);

        saveBaseTextPreview(params);

    }

    @Override
    public Map<String,Object> findBiologicalResources(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId", MapUtils.getString(params,"parent_id"));
        member.put("account", MapUtils.getString(params,"user_id"));
        member.put("baseId", MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("biologicalResources_name", "生物资源信息");
        } else {
            map.put("biologicalResources_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview =  productionBaseMapper.physicalTextPreview(params);
        Map<String,Object> list = productionBaseMapper.findBiologicalResources(params);
        if (MapUtils.isNotEmpty(list)) {
            JSONArray json = JSONArray.parseArray(MapUtils.getString(list,"animalList"));
            list.put("animalList",json);

            JSONArray animalData = JSONArray.parseArray(MapUtils.getString(list,"animalData"));
            list.put("animalData",animalData);

            JSONArray jsons = JSONArray.parseArray(MapUtils.getString(list,"plantList"));
            list.put("plantList",jsons);

            JSONArray plantData = JSONArray.parseArray(MapUtils.getString(list,"plantData"));
            list.put("plantData",plantData);
            map.put("biologicalResources",list);
        } else {
            Map<String,Object> biologicalMap = new HashMap();
            String plantList = " [{\"plantType\":\"国家Ⅰ级濒危植物\",\"plantName\":[]},{\"plantType\":\"国家Ⅱ级濒危植物\",\"plantName\":[]}]";
            JSONArray json = JSONArray.parseArray(plantList);
            biologicalMap.put("plantList",json);

            String animalList = " [{\"animalType\": \"国家Ⅰ级濒危动物\",\"animalName\": []},{\"animalType\": \"国家Ⅱ级濒危动物\",\"animalName\": []}]";
            JSONArray jsons = JSONArray.parseArray(animalList);
            biologicalMap.put("animalList",jsons);
            map.put("biologicalResources",biologicalMap);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview", "");
            text.put("sys_dict_id", "");
            text.put("user_id", "");
            map.put("textPreview", text);
        }

        return map;
    }

    @Override
    public void saveBiologicalResources(Map<String,Object> params){
        Map<String,Object> biologicalResource = (Map<String, Object>) params.get("biologicalResources");

        String user_id = MapUtils.getString(params,"user_id");
        biologicalResource.put("user_id", user_id);
        biologicalResource.put("baseId", MapUtils.getString(params,"baseId"));

        String animalData  = JSON.toJSONString(biologicalResource.get("animalData"));
        biologicalResource.put("animalData", animalData);

        String animalList  = JSON.toJSONString(biologicalResource.get("animalList"));
        biologicalResource.put("animalList",animalList);

        String plantData  = JSON.toJSONString(biologicalResource.get("plantData"));
        biologicalResource.put("plantData", plantData);

        String plantList  = JSON.toJSONString(biologicalResource.get("plantList"));
        biologicalResource.put("plantList",plantList);

        productionBaseMapper.saveBiologicalResources(biologicalResource);

        saveBaseTextPreview(params);

    }

    @Override
    public void deleteBiologicalResources(Map<String,Object> params){
        productionBaseMapper.deleteBiologicalResources(params);
    }

    @Override
    public Map<String,Object> findMineralsInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId", MapUtils.getString(params,"parent_id"));
        member.put("account", MapUtils.getString(params,"user_id"));
        member.put("baseId", MapUtils.getString(params,"baseId"));
        getYearIdAndTemplateId(member);
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("minerals_name", "地址矿产信息");
        } else {
            map.put("minerals_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview =  productionBaseMapper.physicalTextPreview(params);

        List<Map<String,Object>> list = productionBaseMapper.findMineralsInfo(params);
        if(CollectionUtils.isNotEmpty(list)){
            for(Map<String,Object> areaInfo:list){
                String minerals_name = MapUtils.getString(areaInfo,"minerals_name");
                if(StringUtil.isNotEmpty(minerals_name)){
                    String[] members = minerals_name.split(";");
                    areaInfo.put("minerals_name",members);
                }else{
                    areaInfo.put("minerals_name", new String[]{});
                }
            }
            map.put("mineralsInfo",list);
        } else {
            map.put("mineralsInfo",new ArrayList<>());
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview", "");
            text.put("sys_dict_id", "");
            text.put("user_id", "");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveMineralsInfo(Map<String,Object> params){

        List<Map<String,Object>> mineralsInfo = (List<Map<String, Object>>) params.get("mineralsInfo");
        String user_id = MapUtils.getString(params,"user_id");
        for(Map<String,Object> map : mineralsInfo){
            String minerals_name = MapUtils.getString(map,"minerals_name");
            if(StringUtil.isNotEmpty(minerals_name)) {
                JSONArray topographic_model = JSONArray.parseArray(minerals_name);
                if (topographic_model.size() == 0) {
                    map.put("minerals_name", "");
                } else {
                    String[] members = ImageUtil.getJsonToStringArray(minerals_name);
                    String memberMerge = ImageUtil.stringArrayMerge(members);
                    map.put("minerals_name", memberMerge);
                }
            } else{
                map.put("minerals_name", "");
            }
            map.put("user_id",user_id);
            map.put("baseId",MapUtils.getString(params,"baseId"));
            productionBaseMapper.saveMineralsInfo(map);
        }
        saveBaseTextPreview(params);
    }

    /**
     * 通信设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findCommunicationFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("communicationFacilitiesInfoName", "通信设施信息");
        } else {
            map.put("communicationFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //通信设施信息
        List<Map<String,Object>> list = productionBaseMapper.findCommunicationFacilitiesInfo(params);
        if(list.size()>0) {
            for(Map<String,Object> communication:list ){
                String pictureList = MapUtils.getString(communication,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    communication.put("pictureList",members);
                }else{
                    communication.put("pictureList", new String[]{});
                }
            }
            map.put("communicationFacilitiesInfo",list);
        } else {
            map.put("communicationFacilitiesInfo",new String[]{});
        }
        if (MapUtils.isNotEmpty(textPreview)) {
            map.put("textPreview", textPreview);
        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview", "");
            text.put("parentId", "");
            text.put("account", "");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveCommunicationFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> communication = (Map<String, Object>) params.get("communicationFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        communication.put("account", user_id);
        String pictureList = MapUtils.getString(communication,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                communication.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                communication.put("pictureList", memberMerge);
            }
        }else{
            communication.put("pictureList", "");
        }
        communication.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveCommunicationFacilitiesInfo(communication);
    }

    @Override
    public void deleteCommunicationFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteCommunicationFacilitiesInfo(params);
    }

    /**
     * 自来水设施及用户信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findTapWaternFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //自来水设施及用户信息
        List<Map<String,Object>> list = productionBaseMapper.findTapWaternFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> communication:list ){
                String pictureList = MapUtils.getString(communication,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    communication.put("pictureList",members);
                }else{
                    communication.put("pictureList", new String[]{});
                }
            }
            map.put("tapWaternFacilitiesInfo",list);

        } else {
            map.put("tapWaternFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("tapWaternFacilitiesInfoName", "自来水设施及用户信息");
        } else {
            map.put("tapWaternFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveTapWaternFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> tapWatern = (Map<String, Object>) params.get("tapWaternFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        tapWatern.put("account", user_id);
        String pictureList = MapUtils.getString(tapWatern,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                tapWatern.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                tapWatern.put("pictureList", memberMerge);
            }
        } else {
            tapWatern.put("pictureList", "");
        }
        tapWatern.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveTapWaternFacilitiesInfo(tapWatern);
    }

    @Override
    public void deleteTapWaternFacilitiesInfo(Map<String,Object> params) {
        productionBaseMapper.deleteTapWaternFacilitiesInfo(params);
    }

    /**
     * 水利设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findWaterConservancyFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //水利设施信息
        List<Map<String,Object>> list = productionBaseMapper.findWaterConservancyFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> water:list ){
                String pictureList = MapUtils.getString(water,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    water.put("pictureList",members);
                } else {
                    water.put("pictureList", new String[]{});
                }
            }
            map.put("waterConservancyFacilitiesInfo",list);

        } else {
            map.put("waterConservancyFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("waterConservancyFacilitiesInfoName", "自来水设施及用户信息");
        } else {
            map.put("waterConservancyFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview",text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveWaterConservancyFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> water = (Map<String, Object>) params.get("waterConservancyFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        water.put("account", user_id);
        String pictureList = MapUtils.getString(water,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                water.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                water.put("pictureList", memberMerge);
            }
        } else {
            water.put("pictureList", "");
        }
        water.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveWaterConservancyFacilitiesInfo(water);
    }

    @Override
    public void deleteWaterConservancyFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteWaterConservancyFacilitiesInfo(params);
    }

    /**
     * 垃圾处理设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findGarbageDisposaFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //垃圾处理设施信息
        List<Map<String,Object>> list = productionBaseMapper.findGarbageDisposaFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> garbage:list ){
                String pictureList = MapUtils.getString(garbage,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    garbage.put("pictureList",members);
                } else {
                    garbage.put("pictureList", new String[]{});
                }
            }
            map.put("garbageDisposaFacilitiesInfo",list);
        } else {
            map.put("garbageDisposaFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("garbageDisposaFacilitiesInfoName", "垃圾处理设施信息");
        } else {
            map.put("garbageDisposaFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveGarbageDisposaFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> garbage = (Map<String, Object>) params.get("garbageDisposaFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        garbage.put("account",user_id);
        String pictureList = MapUtils.getString(garbage,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                garbage.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                garbage.put("pictureList", memberMerge);
            }
        } else {
            garbage.put("pictureList", "");
        }
        garbage.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveGarbageDisposaFacilitiesInfo(garbage);
    }

    @Override
    public void deleteGarbageDisposaFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteGarbageDisposaFacilitiesInfo(params);
    }

    /**
     * 安保设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findSecurityFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //安保设施信息
        List<Map<String,Object>> list = productionBaseMapper.findSecurityFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> security:list ){
                String pictureList = MapUtils.getString(security,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    security.put("pictureList",members);
                } else {
                    security.put("pictureList", new String[]{});
                }
            }
            map.put("securityFacilitiesInfo",list);
        } else {
            map.put("securityFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("securityFacilitiesInfoName", "安保设施信息");
        } else {
            map.put("securityFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveSecurityFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> security = (Map<String, Object>) params.get("securityFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        security.put("account",user_id);
        String pictureList = MapUtils.getString(security,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                security.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                security.put("pictureList", memberMerge);
            }
        } else {
            security.put("pictureList", "");
        }
        security.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveSecurityFacilitiesInfo(security);
    }

    @Override
    public void deleteSecurityFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteSecurityFacilitiesInfo(params);
    }

    /**
     * 教育设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findEducationFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //教育设施信息
        List<Map<String,Object>> list = productionBaseMapper.findEducationFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> education:list ){
                String pictureList = MapUtils.getString(education,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    education.put("pictureList",members);
                } else {
                    education.put("pictureList", new String[]{});
                }
            }
            map.put("educationFacilitiesInfo",list);
        } else {
            map.put("educationFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("educationFacilitiesInfoName", "教育设施信息");
        } else {
            map.put("educationFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveEducationFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> education = (Map<String, Object>) params.get("educationFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        education.put("account", user_id);
        String pictureList = MapUtils.getString(education,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                education.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                education.put("pictureList", memberMerge);
            }
        }else{
            education.put("pictureList", "");
        }
        education.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveEducationFacilitiesInfo(education);
    }

    @Override
    public void deleteEducationFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteEducationFacilitiesInfo(params);
    }

    /**
     * 文化设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findCultureFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview = productionBaseMapper.communalTextPreview(params);
        //文化设施信息
        List<Map<String,Object>> list = productionBaseMapper.findCultureFacilitiesInfo(params);
        if(list.size()>0) {
            for(Map<String,Object> culture:list ) {
                String pictureList = MapUtils.getString(culture,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)) {
                    String[] members = pictureList.split(";");
                    culture.put("pictureList",members);
                } else {
                    culture.put("pictureList", new String[]{});
                }
            }
            map.put("cultureFacilitiesInfo",list);

        } else {
            map.put("cultureFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("cultureFacilitiesInfoName", "文化设施信息");
        } else {
            map.put("cultureFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveCultureFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> culture = (Map<String, Object>) params.get("cultureFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        culture.put("account", user_id);
        String pictureList = MapUtils.getString(culture,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                culture.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                culture.put("pictureList", memberMerge);
            }
        } else {
            culture.put("pictureList", "");
        }
        culture.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveCultureFacilitiesInfo(culture);
    }

    @Override
    public void deleteCultureFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteCultureFacilitiesInfo(params);
    }

    /**
     * 体育设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findSportsFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id", MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //体育设施信息
        List<Map<String,Object>> list = productionBaseMapper.findSportsFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> sports:list ){
                String pictureList = MapUtils.getString(sports,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    sports.put("pictureList",members);
                } else {
                    sports.put("pictureList", new String[]{});
                }
            }
            map.put("sportsFacilitiesInfo",list);
        } else {
            map.put("sportsFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("sportsFacilitiesInfoName", "体育设施信息");
        } else {
            map.put("sportsFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveSportsFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> sports = (Map<String, Object>) params.get("sportsFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        sports.put("account",user_id);
        String pictureList = MapUtils.getString(sports,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                sports.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                sports.put("pictureList", memberMerge);
            }
        } else {
            sports.put("pictureList", "");
        }
        sports.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveSportsFacilitiesInfo(sports);

    }

    @Override
    public void deleteSportsFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteSportsFacilitiesInfo(params);
    }

    /**
     * 养老福利设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findOldAgeWelfareFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview = productionBaseMapper.communalTextPreview(params);
        //养老福利设施信息
        List<Map<String,Object>> list = productionBaseMapper.findOldAgeWelfareFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> oldAge:list ){
                String pictureList = MapUtils.getString(oldAge,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    oldAge.put("pictureList",members);
                }else{
                    oldAge.put("pictureList", new String[]{});
                }
            }
            map.put("oldAgeWelfareFacilitiesInfo",list);
        } else {
            map.put("oldAgeWelfareFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("oldAgeWelfareFacilitiesInfoName", "体育设施信息");
        } else {
            map.put("oldAgeWelfareFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        } else {

            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveOldAgeWelfareFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> oldAge = (Map<String, Object>) params.get("oldAgeWelfareFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        oldAge.put("account",user_id);
        String pictureList = MapUtils.getString(oldAge,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                oldAge.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                oldAge.put("pictureList", memberMerge);
            }
        }else{
            oldAge.put("pictureList", "");
        }
        oldAge.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveOldAgeWelfareFacilitiesInfo(oldAge);
    }

    @Override
    public void deleteOldAgeWelfareFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteOldAgeWelfareFacilitiesInfo(params);
    }

    /**
     * 其它便民设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findOtherFacilitiesInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //其它便民设施信息
        List<Map<String,Object>> list = productionBaseMapper.findOtherFacilitiesInfo(params);
        if(list.size()>0){
            for(Map<String,Object> other:list ){
                String pictureList = MapUtils.getString(other,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)){
                    String[] members = pictureList.split(";");
                    other.put("pictureList",members);
                }else{
                    other.put("pictureList", new String[]{});
                }
            }
            map.put("otherFacilitiesInfo",list);
        } else {
            map.put("otherFacilitiesInfo",new String[]{});
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("otherFacilitiesInfoName", "其它便民设施信息");
        } else {
            map.put("otherFacilitiesInfoName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(MapUtils.isEmpty(textPreview)) {
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview",text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    public void saveOtherFacilitiesInfo(Map<String,Object> params){
        Map<String,Object> other = (Map<String, Object>) params.get("otherFacilitiesInfo");
        String user_id = MapUtils.getString(params,"account");
        other.put("account",user_id);
        String pictureList = MapUtils.getString(other,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                other.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                other.put("pictureList", memberMerge);
            }
        }else{
            other.put("pictureList", "");
        }
        other.put("baseId", MapUtils.getString(params,"yearId"));
        productionBaseMapper.saveOtherFacilitiesInfo(other);
    }

    @Override
    public void deleteOtherFacilitiesInfo(Map<String,Object> params){
        productionBaseMapper.deleteOtherFacilitiesInfo(params);
    }

    @Override
    public void saveIotDeviceInfo(Map<String,Object> params) {
        Map<String,Object> iotDevice = (Map<String, Object>) params.get("iotDeviceInfo");
        String account = MapUtils.getString(params,"account");
        iotDevice.put("account", account);
        String pictureList = MapUtils.getString(iotDevice,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                iotDevice.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                iotDevice.put("pictureList", memberMerge);
            }
        }else{
            iotDevice.put("pictureList", "");
        }
        iotDevice.put("baseId", MapUtils.getString(params,"baseId"));
        productionBaseMapper.saveIotDeviceInfo(iotDevice);

    }

    /**
     * 物联设施信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findIotDeviceInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id", MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //其它便民设施信息
        List<Map<String,Object>> list = productionBaseMapper.findIotDeviceInfo(params);
        if(list.size()>0) {

            for (Map<String,Object> other:list) {
                other.put("status","1".equals(MapUtils.getString(other,"status"))? true : false);
                String pictureList = MapUtils.getString(other,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)) {
                    String[] members = pictureList.split(";");
                    other.put("pictureList",members);
                } else {
                    other.put("pictureList", new String[]{});
                }
            }
            map.put("iotDeviceInfo", list);

        } else {
            map.put("iotDeviceInfo", new String[]{});
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview",text);
        } else {
            map.put("textPreview",textPreview);
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteIotDeviceInfo(Map<String,Object> params){
        //删除base_iot_device_info表中数据
        productionBaseMapper.deleteIotDeviceInfo(params);
        //删除base_iot_mapping表中数据,通过iotId
        productionBaseMapper.deleteIotMappingByIotId(params);

    }

    @Override
    public List<Map<String,Object>> findAllTextPreviewList(Map<String,Object> params){

        List<Map<String,Object>> allTextPreviewList = new ArrayList<>();
        String account = MapUtils.getString(params, "account");
        //根据account查询得到该用户下所有的模块
        List<Map<String,Object>> list  = findModuleInfo(params);
        for(Map<String,Object> modelInfo : list) {
            //获取 模块id 模块名字
            Map<String,Object> previewList = new HashMap<>();

            previewList.put("parentId",MapUtils.getString(modelInfo,"appId"));
            previewList.put("appName",MapUtils.getString(modelInfo,"appName"));
            previewList.put("url",MapUtils.getString(modelInfo,"url"));
            params.put("appId",MapUtils.getString(modelInfo,"appId"));

            Map<String,Object> previewListMap = productionBaseMapper.findAllTextPreviewList(params);
            if(MapUtils.isNotEmpty(previewListMap)) {
                List<Map<String,Object>> allTextList = new LinkedList<>();
                allTextList.add(previewListMap);
                previewList.put("textPreview", allTextList);
            } else {
                //查询获取所有子模块id
                List<Map<String,Object>> moduleList = perfectBasicMapper.findSubModule(params);
                List<Map<String,Object>> textList = new LinkedList<>();
                for (Map<String,Object> map : moduleList) {
                    params.put("dictId",MapUtils.getString(map,"dictId"));
                    Map<String,Object> previewMap = productionBaseMapper.findTextPreview(params);
                    if(MapUtils.isNotEmpty(previewMap)) {
                        textList.add(previewMap);
                    }
                }
                previewList.put("textPreview",textList);
            }
            allTextPreviewList.add(previewList);
        }
        return allTextPreviewList;
    }

    @Override
    public void saveAllTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");
        if(MapUtils.isNotEmpty(textPreview)){
            productionBaseMapper.saveAllTextPreview(textPreview);
        }
    }

    /**
     * 交通设施
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findTrafficOfUserInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> textPreview =  productionBaseMapper.communalTextPreview(params);
        //交通设施及用户信息
        List<Map<String,Object>> list = productionBaseMapper.findTrafficOfUserInfo(params);
        if(list.size()>0){
            for(Map<String,Object> traffic : list ){
                String pictureList = MapUtils.getString(traffic,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)) {
                    String[] members = pictureList.split(";");
                    traffic.put("pictureList",members);
                } else {
                    traffic.put("pictureList", new String[]{});
                }
                JSONArray siteInfoArray = JSONArray.parseArray(MapUtils.getString(traffic, "siteInfo"));
                if (siteInfoArray == null) {
                    traffic.put("siteInfo", new ArrayList<>());
                } else {
                    traffic.put("siteInfo", siteInfoArray);
                }
            }
            map.put("trafficOfUser", list);
        } else {
            map.put("trafficOfUser", new ArrayList<>());
        }
        getYearIdAndTemplateId(params);
        Map<String,Object> propertyInfo = productionBaseMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", "交通设施及用户信息");
        } else {
            String propertyName = MapUtils.getString(propertyInfo, "propertyName");
            map.put("propertyName", propertyName);
        }

        if(MapUtils.isNotEmpty(textPreview)) {
            String textPreviews = MapUtils.getString(textPreview,"textPreview");
            if(StringUtil.isNotEmpty(textPreviews)) {
                String[] members = textPreviews.split("&");
                textPreview.put("textPreview", members);
            } else {
                textPreview.put("textPreview", new String[]{});
            }
            map.put("textPreview",textPreview);

        } else {
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview",new String[]{});
            text.put("parentId","");
            text.put("account","");
            map.put("textPreview", text);
        }
        return map;
    }

    @Override
    public void saveTrafficOfUserInfo(Map<String,Object> params){
        Map<String,Object> trafficOfUser = (Map<String, Object>) params.get("trafficOfUser");
        String user_id = MapUtils.getString(params,"account");
        trafficOfUser.put("account", user_id);
        trafficOfUser.put("baseId", MapUtils.getString(params,"baseId"));
        String pictureList = MapUtils.getString(trafficOfUser,"pictureList");
        if(StringUtil.isNotEmpty(pictureList)) {
            JSONArray pictureListModel = JSONArray.parseArray(pictureList);
            if (pictureListModel.size() == 0) {
                trafficOfUser.put("pictureList", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(pictureList);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                trafficOfUser.put("pictureList", memberMerge);
            }
        }else{
            trafficOfUser.put("pictureList", "");
        }
        String json = JSON.toJSONString(trafficOfUser.get("siteInfo"));
        trafficOfUser.put("siteInfo", json);

        productionBaseMapper.saveTrafficOfUserInfo(trafficOfUser);

    }

    @Override
    public void deleteTrafficOfUserInfo(Map<String,Object> params){
        productionBaseMapper.deleteTrafficOfUserInfo(params);
    }

    @Override
    public Map<String, Object> baseIntroduction(Map<String, Object> params) {

        Map<String,Object> resultMap = new HashMap<>();
        params.put("id", MapUtils.getString(params, "baseId"));
        Map<String, Object> baseInfo = findBaseInfo(params);
        List<Map<String, Object>> landInfo = productionBaseMapper.findLandInfo(params);
        String factArea = "";
        if (CollectionUtils.isNotEmpty(landInfo)) {
            factArea = MapUtils.getString(landInfo.get(0), "factArea");
            if (StringUtils.isBlank(factArea)) {
                factArea = "";
            }
        }
        MapUtils.getMap(baseInfo, "baseInfo").put("factArea", factArea);
        //添加物联设施
        params.put("status", 1);
        List<Map<String, Object>> baseIotDeviceInfo = productionBaseMapper.findBaseIotDeviceInfo(params);
        if (CollectionUtils.isEmpty(baseIotDeviceInfo)) {
            baseIotDeviceInfo = new ArrayList<>();
        } else {
            for (Map<String,Object> map : baseIotDeviceInfo) {
                map.put("status","1".equals(MapUtils.getString(map,"status")) ? true : false);
                String pictureList = MapUtils.getString(map,"pictureList");
                if(StringUtil.isNotEmpty(pictureList)) {
                    String[] members = pictureList.split(";");
                    map.put("pictureList", members);
                } else {
                    map.put("pictureList", new String[]{});
                }
            }
        }
        baseInfo.put("iotDeviceInfo", baseIotDeviceInfo);
        //基地照片
        List<Map<String, Object>> photoList = productionBaseMapper.photoList(params);
        if (CollectionUtils.isEmpty(baseIotDeviceInfo)) {
            baseIotDeviceInfo = new ArrayList<>();
        }
        baseInfo.put("photoList", photoList);

        resultMap.put("baseIntroduction", baseInfo);

        List<Map<String, Object>> detail = findAllTextPreviewList(params);
        resultMap.put("detailInformation", detail);

        return resultMap;
    }

    /**
     * 通过用户名查询用户的产业信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findIndustry(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //第一产业
        params.put("type", "1");
        List<Map<String, Object>> primaryIndustry = productionBaseMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(primaryIndustry)) {
            primaryIndustry = new ArrayList<>();
        }
        resultMap.put("primaryIndustry", primaryIndustry);
        //第二产业
        params.put("type", "2");
        List<Map<String, Object>> secondaryIndustry = productionBaseMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(secondaryIndustry)) {
            secondaryIndustry = new ArrayList<>();
        }
        resultMap.put("secondaryIndustry", secondaryIndustry);
        //第三产业
        params.put("type", "3");
        List<Map<String, Object>> tertiaryIndustry = productionBaseMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(tertiaryIndustry)) {
            tertiaryIndustry = new ArrayList<>();
        }
        resultMap.put("tertiaryIndustry", tertiaryIndustry);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "产业信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    /**
     * 修改用户的产业信息
     * @param params
     * @return
     */
    @Override
    public void saveIndustry(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            map.put("type", MapUtils.getString(params, "type"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }

        productionBaseMapper.saveIndustry(list);

    }

    /**
     * 删除产业信息
     * @param params
     */
    @Override
    public void deleteIndustry(Map<String, Object> params) {

        productionBaseMapper.deleteIndustry(params);

    }

    @Override
    public Map<String, Object> findBreed(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //1:农业,2:林业，3:畜牧业,4:水产业
        //农业 agriculture
        params.put("type", "1");
        List<Map<String, Object>> agriculture = productionBaseMapper.findBreed(params);
        if (CollectionUtils.isEmpty(agriculture)) {
            agriculture = new ArrayList<>();
        }
        resultMap.put("agriculture", agriculture);
        //林业 forestry
        params.put("type", "2");
        List<Map<String, Object>> forestry = productionBaseMapper.findBreed(params);
        if (CollectionUtils.isEmpty(forestry)) {
            forestry = new ArrayList<>();
        }
        resultMap.put("forestry", forestry);
        //畜牧业  animalHusbandry
        params.put("type", "3");
        List<Map<String, Object>> animalHusbandry = productionBaseMapper.findBreed(params);
        if (CollectionUtils.isEmpty(animalHusbandry)) {
            animalHusbandry = new ArrayList<>();
        }
        resultMap.put("animalHusbandry", animalHusbandry);
        //水产业  waterIndustry
        params.put("type", "4");
        List<Map<String, Object>> waterIndustry = productionBaseMapper.findBreed(params);
        if (CollectionUtils.isEmpty(waterIndustry)) {
            waterIndustry = new ArrayList<>();
        }
        resultMap.put("waterIndustry", waterIndustry);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "种养信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveBreed(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            map.put("type", MapUtils.getString(params, "type"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        productionBaseMapper.saveBreed(params);

    }

    @Override
    public void deleteBreed(Map<String, Object> params) {

        productionBaseMapper.deleteBreed(params);

    }

    @Override
    public Map<String, Object> findFarmProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //1:农产品,2:林业产品，3:畜牧业产品,4:水产品
        //农产品 agriculture
        params.put("type", "1");
        List<Map<String, Object>> agriculture = productionBaseMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(agriculture)) {
            agriculture = new ArrayList<>();
        }
        resultMap.put("agriculture", agriculture);
        //林业产品 forestry
        params.put("type", "2");
        List<Map<String, Object>> forestry = productionBaseMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(forestry)) {
            forestry = new ArrayList<>();
        }
        resultMap.put("forestry", forestry);
        //畜牧业产品  animalHusbandry
        params.put("type", "3");
        List<Map<String, Object>> animalHusbandry = productionBaseMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(animalHusbandry)) {
            animalHusbandry = new ArrayList<>();
        }
        resultMap.put("animalHusbandry", animalHusbandry);
        //水产品  waterIndustry
        params.put("type", "4");
        List<Map<String, Object>> waterIndustry = productionBaseMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(waterIndustry)) {
            waterIndustry = new ArrayList<>();
        }
        resultMap.put("waterIndustry", waterIndustry);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "农产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveFarmProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            map.put("type", MapUtils.getString(params, "type"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        productionBaseMapper.saveFarmProduct(params);

    }

    @Override
    public void deleteFarmProduct(Map<String, Object> params) {

        productionBaseMapper.deleteFarmProduct(params);

    }

    @Override
    public Map<String, Object> findTechnicalProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //手工业产品
        params.put("type", "1");
        List<Map<String, Object>> handicraftProducts = productionBaseMapper.findTechnicalProduct(params);
        if (CollectionUtils.isEmpty(handicraftProducts)) {
            handicraftProducts = new ArrayList<>();
        }
        resultMap.put("handicraftProducts", handicraftProducts);
        //工业产品
        params.put("type", "2");
        List<Map<String, Object>> technicalProduct = productionBaseMapper.findTechnicalProduct(params);
        if (CollectionUtils.isEmpty(technicalProduct)) {
            technicalProduct = new ArrayList<>();
        }
        resultMap.put("technicalProduct", technicalProduct);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "工业产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveTechnicalProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            map.put("type", MapUtils.getString(params, "type"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        productionBaseMapper.saveTechnicalProduct(params);

    }

    @Override
    public void deleteTechnicalProduct(Map<String, Object> params) {

        productionBaseMapper.deleteTechnicalProduct(params);

    }

    @Override
    public Map<String, Object> findServiceProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //agriculturalService
        params.put("type", "1");
        List<Map<String, Object>> agriculturalService = productionBaseMapper.findServiceProduct(params);
        if (CollectionUtils.isEmpty(agriculturalService)) {
            agriculturalService = new ArrayList<>();
        }
        resultMap.put("agriculturalProduct", agriculturalService);
        //其他服务业
        params.put("type", "2");
        List<Map<String, Object>> otherService = productionBaseMapper.findServiceProduct(params);
        if (CollectionUtils.isEmpty(otherService)) {
            otherService = new ArrayList<>();
        }
        resultMap.put("otherService", otherService);

        //获取属性名称
        /*Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "服务产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }*/
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveServiceProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            map.put("type", MapUtils.getString(params, "type"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        productionBaseMapper.saveServiceProduct(params);

    }

    @Override
    public void deleteServiceProduct(Map<String, Object> params) {

        productionBaseMapper.deleteServiceProduct(params);

    }

    @Override
    public List<Map<String, Object>> contactList(Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        params.put("templateId", MapUtils.getString(useTemplate, "templateId"));
        List<Map<String, Object>> list = productionBaseMapper.contactList(params);
        return list;
    }

    @Override
    public List<Map<String, Object>> iotList(Map<String, Object> params) {
        return productionBaseMapper.iotList(params);
    }

    @Override
    public List<Map<String, Object>> findBaseIotDeviceInfo(Map<String, Object> params) {
        List<Map<String, Object>> list = productionBaseMapper.findBaseIotDeviceInfo(params);
        for (Map<String,Object> map : list) {
            map.put("status","1".equals(MapUtils.getString(map,"status"))? true : false);
            String pictureList = MapUtils.getString(map,"pictureList");
            if(StringUtil.isNotEmpty(pictureList)) {
                String[] members = pictureList.split(";");
                map.put("pictureList",members);
            } else {
                map.put("pictureList", new String[]{});
            }
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveBaseIotDeviceInfo(Map<String, Object> params) {
        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
            String pictureList = MapUtils.getString(map,"pictureList");
            if(StringUtil.isNotEmpty(pictureList)) {
                JSONArray pictureListModel = JSONArray.parseArray(pictureList);
                if (pictureListModel.size() == 0) {
                    map.put("pictureList", "");
                } else {
                    String[] members = ImageUtil.getJsonToStringArray(pictureList);
                    String memberMerge = ImageUtil.stringArrayMerge(members);
                    map.put("pictureList", memberMerge);
                }
            } else {
                map.put("pictureList", "");
            }
            if (StringUtils.isBlank(MapUtils.getString(map, "iotId"))) {
                //为空说明，第四步中的物联设施对应数据没有，需要同步过去
                //保存至base_iot_device_info表中
                productionBaseMapper.saveIotDeviceInfo(map);

            } else {
                //第四步中的物联设施对应数据存在，进行更新
                productionBaseMapper.updateIotDeviceInfo(map);
            }
        }
        productionBaseMapper.saveBaseIotDeviceInfo(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBaseIotDeviceInfo(Map<String, Object> params) {
        //删除base_iot_mapping表中数据
        if (StringUtils.isNotBlank(MapUtils.getString(params, "id"))) {
            productionBaseMapper.deleteIotMapping(params);
        }
        //删除base_iot_device_info表中数据
        if (StringUtils.isNotBlank(MapUtils.getString(params, "iotId"))) {
            productionBaseMapper.deleteIotDevice(params);
        }
    }

    @Override
    public void revokeBaseIotDeviceInfo(Map<String, Object> params) {
        //删除base_iot_mapping表中数据
        productionBaseMapper.deleteIotMapping(params);
    }

    /**
     * 生产基地相册列表
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> photoList(Map<String, Object> params) {
        //分页查询数据
        //PageUtil.initPage(params);
        Map<String,Object> resultMap = new HashMap<>();
        //查询基地列表
        List<Map<String, Object>> list = productionBaseMapper.photoList(params);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        resultMap.put("list", list);
        Map<String, Object> map = productionBaseMapper.findNickName(params);
        resultMap.put("nickName", map.get("nickName"));

        return resultMap;
    }

    /**
     * 保存基地相册照片
     * @param params
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveBasePhoto(Map<String, Object> params) {

        //先删除该基地之前的照片
        productionBaseMapper.deleteBasePhoto(params);
        //然后再保存
        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("baseId", MapUtils.getString(params, "baseId"));
        }
        if (CollectionUtils.isNotEmpty(list)) {
            productionBaseMapper.batchSavePhoto(list);
        }

    }

    /**
     * 完成生产基地的编辑
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void editComplete(Map<String, Object> params) {
        productionBaseMapper.editComplete(params);
        //发布的基地, 默认为已推荐
        params.put("id", MapUtils.getString(params, "baseId"));
        params.put("type", 2);
        //判断是否已推荐
        Map<String, Object> hadRecommend = myRecommendMapper.isHadRecommend(params);
        if (MapUtils.isEmpty(hadRecommend)) {
            myRecommendMapper.singleSaveRecommend(params);
        }
    }
}
