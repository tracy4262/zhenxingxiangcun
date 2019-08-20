package com.ovit.nswy.member.userManage.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.manage.mapper.TemplateConfigMapper;
import com.ovit.nswy.member.userManage.mapper.UserTemplateManageMapper;
import com.ovit.nswy.member.userManage.service.UserTemplateManageService;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserTemplateManageServiceImpl implements UserTemplateManageService {

    @Autowired
    private UserTemplateManageMapper userTemplateManageMapper;

    @Autowired
    private TemplateConfigMapper templateConfigMapper;

    @Autowired
    private LoginUserService loginUserService;


    @Override
    public Map<String,Object> find(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> templateMap = new HashMap<>();
        templateMap.put("step","6");

        //查询得到所有模版
        List<Map<String,Object>> templateConfig =  templateConfigMapper.find(templateMap);

        Map<String,Object> currency = new HashMap<>();
        currency.put("id","0");
        currency.put("introduction","该模版为用户自定义模版。");
        currency.put("templateName","通用模版");
        currency.put("userType","自定义");
        templateConfig.add(currency);
        //查询得到用户所选定模版
        Map<String,Object> userTemplate = userTemplateManageMapper.find(params);
        for(Map<String,Object> template :templateConfig){
            String  id= MapUtils.getString(template,"id");
            if(MapUtils.isNotEmpty(userTemplate)){
                String templateId = MapUtils.getString(userTemplate,"templateId");
                if(id.equals(templateId)){
                    template.put("checked",true);
                }else{
                    template.put("checked",false);
                }
            }else{
                template.put("checked",false);
            }
        }

        map.put("templateConfig",templateConfig);
        map.put("userTemplate",userTemplate);

        return map;

    }

    @Override
    public void saveOrUpdate(Map<String,Object> params){
        String uuid  = UuidUtil.get32UUID();
        params.put("id",uuid);
        /**
         * 查询用户启用模版
         */
        Map<String,Object> userTemplate = userTemplateManageMapper.findTemplate(params);
        userTemplateManageMapper.updateTemplate(params);
        if(MapUtils.isNotEmpty(userTemplate)){
            userTemplateManageMapper.updateTemplateStatus(params);
        }else{
            userTemplateManageMapper.saveOrUpdate(params);
        }
        Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

        //保存用户步骤
        loginUserService.saveOrUpdateStep(loginMap);

    }


}
