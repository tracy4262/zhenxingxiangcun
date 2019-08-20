package com.ovit.nswy.member.perfectInfo.service.impl;

import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.ExpertAuthMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.ExpertAuthService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专业信息
 */
@Service
public class ExperAuthServiceImpl implements ExpertAuthService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private ExpertAuthMapper expertAuthMapper;

    /**
     * 查询专业认证信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> find(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> authList = expertAuthMapper.find(params);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "专家认证");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(authList)) {
            authList = new ArrayList<>();
            resultMap.put("list", authList);
        } else {
            for (Map<String,Object> auth : authList) {
                String personalPicture = MapUtils.getString(auth, "personalPhoto");
                if (StringUtils.isBlank(personalPicture)) {
                    //没有个人照片信息
                    auth.put("personalPhoto", new String[0]);
                } else {
                    String[] pictures = personalPicture.split(";");
                    auth.put("personalPhoto", pictures);
                }
                String qualificationCertificate = MapUtils.getString(auth, "quaCertificate");
                if (StringUtils.isBlank(qualificationCertificate)) {
                    //没有资质证书信息
                    auth.put("quaCertificate", new String[0]);
                } else {
                    String[] certificates = qualificationCertificate.split(";");
                    auth.put("quaCertificate", certificates);

                }
                auth.put("status","1".equals(MapUtils.getString(auth,"status")) ? true : false);
            }
            resultMap.put("list", authList);
        }
        //获取用户姓名
        String account = MapUtils.getString(params, "account");
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        //查询用户最大的年度文件id
        if (MapUtils.isEmpty(useTemplate)) {
            resultMap.put("expertName", "");
        } else {
            Map<String,Object> memberInfo = perfectBasicService.findMemberName(useTemplate);
            String memberName = MapUtils.getString(memberInfo, "memberName");
            if (StringUtils.isBlank(memberName)) {
                resultMap.put("expertName", "");
            } else {
                resultMap.put("expertName", memberName);
            }
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    /**
     * 保存-专家认证信息
     * @param params
     * @return
     */
    @Override
    public void save(Map<String, Object> params) {

        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.remove("id");
        }
        params.put("approveStatus", 0);
        ConvertDataUtil.split(params, "personalPhoto");
        ConvertDataUtil.split(params, "quaCertificate");
        expertAuthMapper.save(params);

    }

    /**
     * 删除专家认证信息
     * @param params
     */
    @Override
    public void delete(Map<String, Object> params) {

        expertAuthMapper.delete(params);

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
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
        } else {
            result.put("preview", "");
        }

        return result;
    }

    public Map<String,Object> findExpertInfo(Map<String,Object> params){

        return expertAuthMapper.findExpertInfo(params);
    }



}
