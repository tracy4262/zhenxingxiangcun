package com.ovit.nswy.relationship.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.RandomUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.relationship.enums.AgencyTypeEnum;
import com.ovit.nswy.relationship.enums.AuditStatusEnum;
import com.ovit.nswy.relationship.mapper.AgencyAuditMapper;
import com.ovit.nswy.relationship.model.AgencyAudit;
import com.ovit.nswy.relationship.model.request.AgencyAuditListQuery;
import com.ovit.nswy.relationship.model.request.AgencyAuditQuery;
import com.ovit.nswy.relationship.model.response.AgencyAuditDTO;
import com.ovit.nswy.relationship.service.AgencyAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AgencyAuditServiceImpl implements AgencyAuditService {

    @Autowired
    private AgencyAuditMapper agencyAuditMapper;


    @Override
    public Integer  saveAgencyAudit(AgencyAuditQuery agencyAuditQuery){
        AgencyAudit agencyAudit=new AgencyAudit();
        agencyAudit=BeanByRefMapper.copyByRefMapper(agencyAuditQuery,AgencyAudit.class);
        agencyAudit.setAuditId(Integer.valueOf(RandomUtil.randomNumbers(10)));
        agencyAudit.setCreateTime(new Date());
        agencyAudit.setAuditStatus(0);
        Integer rows=agencyAuditMapper.insert(agencyAudit);
        return rows;
    }


    @Override
    public PageInfo<AgencyAuditDTO> listAgencyAudit(AgencyAuditListQuery agencyAuditListQuery){
       Map params= Maps.newHashMap();
        if(StringUtil.isNotBlank(agencyAuditListQuery.getUserId())){
            params.put("userId",agencyAuditListQuery.getUserId());
        }

        if(Integers.isNotEmpty(agencyAuditListQuery.getAgencyId())){
            params.put("agencyId",agencyAuditListQuery.getAgencyId());
        }
        PageHelper.startPage(agencyAuditListQuery.getPageNum(), agencyAuditListQuery.getPageSize());
        List<AgencyAuditDTO> list= agencyAuditMapper.findAgencyAuditDetail(params);
        PageInfo<AgencyAuditDTO> PageInfo= new PageInfo(list);
        for(AgencyAuditDTO agencyAuditDTO:PageInfo.getList()){
            agencyAuditDTO.getAgencyTypeInfo().put("id",agencyAuditDTO.getAgencyType());
            agencyAuditDTO.getAgencyTypeInfo().put("val", AgencyTypeEnum.agencyTypeEscape(agencyAuditDTO.getAgencyType()));
            agencyAuditDTO.getAuditStatusInfo().put("id", agencyAuditDTO.getAuditStatus());
            agencyAuditDTO.getAuditStatusInfo().put("val", AuditStatusEnum.auditStatusEscape(agencyAuditDTO.getAgencyType()));
        }
        return PageInfo;
    }


    @Override
    public Integer updateAuditStatus(Integer auditId, Integer auditStatus){
        AgencyAudit agencyAudit=new AgencyAudit();
        if(Integers.isEmpty(auditId)){

        }
        if (Integers.isNotEmpty(auditStatus)){
            if(auditStatus==1){
                agencyAudit.setAuditStatus(1);
            }else{
                agencyAudit.setAuditStatus(2);
            }
        }
        agencyAudit.setAuditTime(new Date());

        Integer rows= agencyAuditMapper.updateByPrimaryKey(agencyAudit);
        return rows;
    }
}
