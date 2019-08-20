package com.ovit.nswy.member.manage.service.impl;

import com.ovit.nswy.member.manage.mapper.TemplateConfigMapper;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TemplateConfigServiceImpl implements TemplateConfigService {

    @Autowired
    private TemplateConfigMapper templateConfigMapper;


    @Override
    public void save(Map<String, Object> params) {

        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.put("id", UuidUtil.get32UUID());
        }
        templateConfigMapper.save(params);

    }

    @Override
    public List<Map<String, Object>> find(Map<String, Object> params) {
        return templateConfigMapper.find(params);
    }

    @Override
    public void delete(Map<String, Object> params) {

        templateConfigMapper.delete(params);

    }

    @Override
    public List<Map<String, Object>> getUserType() {
        return templateConfigMapper.getUserType();
    }

    @Override
    public List<Map<String, Object>> isExist(Map<String, Object> params) {
        return templateConfigMapper.isExist(params);
    }
}
