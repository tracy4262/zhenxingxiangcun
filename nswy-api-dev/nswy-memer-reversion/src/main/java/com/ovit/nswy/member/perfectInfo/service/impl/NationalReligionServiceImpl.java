package com.ovit.nswy.member.perfectInfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.NationalReligionMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.WantBuyMapper;
import com.ovit.nswy.member.perfectInfo.service.NationalReligionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.perfectInfo.service.WantBuyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NationalReligionServiceImpl implements NationalReligionService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private NationalReligionMapper nationalReligionMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 通过用户名查询用户的民族宗教信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> find(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "民族宗教信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> religionList = nationalReligionMapper.find(params);
        if (CollectionUtils.isEmpty(religionList)) {
            religionList = new ArrayList<>();
        }
        //resultMap.put("dataList", religionList);
        resultMap.put("dataList", new PageInfo<Map<String, Object>>(religionList));
        //派别分类统计
        params.remove("pageNum");
        params.remove("pageSize");
        List<Map<String, Object>> factionList = nationalReligionMapper.findFaction(params);
        if (CollectionUtils.isEmpty(factionList)) {
            factionList = new ArrayList<>();
            resultMap.put("typeList", factionList);
        } else {
            resultMap.put("typeList", factionList);
        }
        //获取预览信息、权限状态
        getPreviewInfo(resultMap, params);
        return resultMap;
    }


    /**
     * 修改用户的民族宗教信息
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Map<String, Object> params) {

        //保存宗教职务信息、并且更新到对应的员工花名册或者编外人员花名册中
        nationalReligionMapper.update(params);
        String type = MapUtils.getString(params, "type");
        if ("1".equals(type)) {
            //更新员工花名册
            nationalReligionMapper.updateStaff(params);
        } else if ("2".equals(type)) {
            //更新编外人员花名册
            nationalReligionMapper.updateOffStaff(params);
        }

    }

    /**
     * 更新民族宗教信息
     * @param params
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Map<String, Object> params) {

        //先删除民族宗教表的数据
        nationalReligionMapper.delete(params);
        //然后从员工花名册、编外人员花名册更新数据
        //1、获取员工花名册信息
        List<Map<String,Object>> staffList = nationalReligionMapper.findStaffInfo(params);
        if (CollectionUtils.isNotEmpty(staffList)) {
            //员工花名册存在有宗教信仰的员工，更新到民族宗教表中
            for (Map<String,Object> map : staffList) {
                map.put("type", "1");
            }
            nationalReligionMapper.save(staffList);
        }
        //2、获取编外人员花名册
        List<Map<String,Object>> offStaff = nationalReligionMapper.findOffStaff(params);
        if (CollectionUtils.isNotEmpty(offStaff)) {
            //员工花名册存在有宗教信仰的员工，更新到民族宗教表中
            for (Map<String,Object> map : offStaff) {
                map.put("type", "2");
            }
            nationalReligionMapper.save(offStaff);
        }

    }

    /**
     * 查询该用户、年度文件下所有的信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findAll(Map<String, Object> params) {
        List<Map<String, Object>> religionList = nationalReligionMapper.find(params);
        if (CollectionUtils.isEmpty(religionList)) {
            religionList = new ArrayList<>();
        }
        return religionList;
    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        Map<String,Object> textPreview = perfectBasicMapper.findTextPreview(params);
        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account",MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId",MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
            result.put("status", MapUtils.getInteger(textPreview, "status"));
        } else {
            result.put("preview", "");
            result.put("status", 1);
        }

        return result;
    }


}
