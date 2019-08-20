package com.ovit.nswy.relationship.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.relationship.mapper.AgencyMapper;
import com.ovit.nswy.relationship.model.Agency;
import com.ovit.nswy.relationship.model.request.AgencyListQuery;
import com.ovit.nswy.relationship.model.request.AgencyQuery;
import com.ovit.nswy.relationship.model.response.AgencyDTO;
import com.ovit.nswy.relationship.service.AgencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

     @Override
     public PageInfo<AgencyDTO> listAgency(AgencyListQuery agencyListQuery){
         List<AgencyDTO>  list= Lists.newArrayList();
         PageHelper.startPage(agencyListQuery.getPageNum(), agencyListQuery.getPageSize());
         Example example=new Example(Agency.class);
         if(Integers.isNotEmpty( agencyListQuery.getAgencyType())){
             example.createCriteria().andEqualTo("agencyType", agencyListQuery.getAgencyType());
         }
         if(StringUtil.isNotBlank(agencyListQuery.getAgencyName())){
             example.createCriteria().andEqualTo("agencyName", agencyListQuery.getAgencyName());
         }

         List<Agency> agencyList= agencyMapper.selectByExample(example);
         PageInfo<Agency> origPageInfo= new PageInfo(agencyList);
         list=BeanByRefMapper.copyByRefListMapper(agencyList,AgencyDTO.class);
         PageInfo<AgencyDTO> pageInfo= new PageInfo(list);
         pageInfo= PageInfoHelper.getPageInfo(origPageInfo, pageInfo);
         return pageInfo;

    }


    @Override
    public Object saveAgency(AgencyQuery agencyQuery){
        Agency agency=new Agency();
        agency=BeanByRefMapper.copyByRefMapper(agencyQuery,Agency.class);
        agency.setCreateTime(new Date());
       Object rows= agencyMapper.insert(agency);
        return rows;
    }

    @Override
    public Object updateAgency(AgencyQuery agencyQuery){
        Agency agency=new Agency();
        agency=BeanByRefMapper.copyByRefMapper(agencyQuery, Agency.class);
        agency.setUpdateTime(new Date());
        Example example=new Example(Agency.class);
        Object rows= agencyMapper.updateByExampleSelective(agency,example);
        return rows;
    }


}
