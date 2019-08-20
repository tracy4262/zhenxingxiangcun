package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.EmployMapper;
import com.ovit.nswy.member.mapper.IndustrialMapper;
import com.ovit.nswy.member.model.request.ExpertQuery;
import com.ovit.nswy.member.service.EmployService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployServiceImpl implements EmployService {
    @Autowired
    private EmployMapper employDao;
    @Autowired
    private IndustrialMapper industrialMapper;

    @Override
    public int removeEmploy(int id) {
        return employDao.removeEmploy(id);
    }

    @Override
    public int addExpert(Map<String, Object> map) {
        return employDao.addExpert(map);
    }

    @Override
    public Map<String, Object> findByid(int id) {
        return employDao.findByid(id);
    }

    @Override
    public int acceptInvitation(Map<String, Object> map) {
        return employDao.acceptInvitation(map);
    }

    @Override
    public int declineInvitation(Map<String, Object> map) {
        return employDao.declineInvitation(map);
    }

    @Override
    public PageInfo<Map<String, Object>> selectAll(Map<String, Object> map) {
        PageUtils.initPage(map);
        List<Map<String, Object>> list = employDao.selectAll(map);
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        SpeciesUtils speciesUtils = new SpeciesUtils();
//        if (CollectionUtils.isNotEmpty(list)) {
//            list.stream().forEach(maps -> {
//                //相关物种
//                maps.put("relatedSpecies",speciesUtils.getSpecies(MapUtils.getString(maps,"relatedSpecies")));
//                //相关行业
//                String relatedIndustryId = MapUtils.getString(maps,"relatedIndustry");
//                String [] relatedIndustryIdArray = StringUtils.splitByWholeSeparator(relatedIndustryId,"");
//                List<String> relatedIndustryName = industrialMapper.getIndustryByIdArray(Arrays.asList(relatedIndustryIdArray));
//                maps.put("relatedIndustry",StringUtils.join(relatedIndustryName,","));
//                mapList.add(maps);
//            });
//        }
        return new PageInfo(list);
    }

    @Override
    public PageInfo<Map<String, Object>> expertAllTable(Map<String, Object> map) {
        PageUtils.initPage(map);
        List<Map<String, Object>> list = employDao.expertAllTable(map);
        return new PageInfo(list);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> selectAllInform(Map<String, Object> map) {
        PageUtils.initPage(map);
        String userType = MapUtils.getString(map,"userType");
        List<Map<String, Object>> list;
        switch (userType){
            case "1"://企业
                list = employDao.queryCorpUserNotice(map);
                break;
            case "3"://机关
                list = employDao.queryGovUserNotice(map);
                break;
            case "4"://专家
                list = employDao.queryExpertUserNotice(map);
                break;
            case "5"://乡村
                list = employDao.queryGovUserNotice(map);
                break;
            default://默认为个人
                list = employDao.queryCommonUserNotice(map);
                break;
        }
        return new PageInfo(list);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> selectAllCompany(Map<String, Object> map) {
        PageUtils.initPage(map);
        List<Map<String, Object>> list = employDao.selectAllCompany(map);
        return new PageInfo(list);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> findExpert(ExpertQuery expertQuery) {
        PageHelper.startPage(expertQuery.getCurrentPage(), expertQuery.getPageSize());
        List<Map<String, Object>> list = employDao.selectExpert(expertQuery);
        return new PageInfo(list);
    }

    @Override
    public int resignation(Map<String, Object> map) {
        return employDao.resignation(map);
    }

    @Override
    public Map<String, Object> queryIsExist(Map<String, Object> params) {
        return employDao.queryIsExist(params);
    }

    @Override
    public Map<String, Object> queryById(Map<String, Object> params) {
        return employDao.queryById(params);
    }

    @Override
    public void updateExpert(Map<String, Object> params) {
        employDao.updateExpert(params);
    }


}
