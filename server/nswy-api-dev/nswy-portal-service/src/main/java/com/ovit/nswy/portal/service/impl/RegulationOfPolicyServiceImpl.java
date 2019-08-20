package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.google.common.collect.Maps;
import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.portal.core.enums.DocTypeEnum;
import com.ovit.nswy.portal.mapper.InformationBookMapper;
import com.ovit.nswy.portal.mapper.RegulationOfPolicyMapper;
import com.ovit.nswy.portal.model.request.PolicyQuery;
import com.ovit.nswy.portal.model.response.PolicyClass;
import com.ovit.nswy.portal.model.response.PolicyDTO;
import com.ovit.nswy.portal.service.RegulationOfPolicyService;
import com.ovit.nswy.portal.util.ConstantPool;
import com.ovit.nswy.portal.util.SortUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Service
@Slf4j
public class RegulationOfPolicyServiceImpl implements RegulationOfPolicyService {

    @Autowired
    private RegulationOfPolicyMapper regulationOfPolicyMapper;
    @Autowired
    private InformationBookMapper informationBookMapper;
    @Override
    public PageInfo<PolicyDTO> listPolicy(PolicyQuery policyQuery){
        Map params= Maps.newHashMap();
        PageHelper.startPage(policyQuery.getPageNum(), policyQuery.getPageSize());
        params.put("account",policyQuery.getAccount());
        params.put("policyType", policyQuery.getPolicyType());
        if(Integers.isNotEmpty(policyQuery.getPolicyQueryType())){
            params.put("policyQueryType",policyQuery.getPolicyQueryType());
        }
        if(StringUtil.isNotBlank(policyQuery.getKeywords())){
            params.put("keywords",policyQuery.getKeywords());
        }
        if(StringUtil.isNotBlank(policyQuery.getYear())){
            params.put("year",policyQuery.getYear());
        }
        if(Integers.isNotEmpty(policyQuery.getQueryVideoType())){
            params.put("queryVideoType",policyQuery.getQueryVideoType());
        }
        if(StringUtil.isNotBlank(policyQuery.getLabel())){
            params.put("label",policyQuery.getLabel());
        }
        List<PolicyDTO> policyDTOList= regulationOfPolicyMapper.getlist(params);
        log.info("获取政策数据列表：{}",policyDTOList);
        PageInfo<PolicyDTO> pageInfo= new PageInfo(policyDTOList);
        pageInfo.getList().stream().forEach(policyDTO->{
            String content= RegexUtil.StringFilterHtml(policyDTO.getContent());
            policyDTO.setContent(content);
            String classifiedName= SortUtils.getSortName(ConstantPool.POLICY_CLASS_DATA,policyDTO.getClassifiedName());
                policyDTO.setClassifiedName(classifiedName);
            policyDTO.setDocType(DocTypeEnum.DocTypeEscape(policyDTO.getDocType()));
            if ("图书".equals(policyDTO.getColumnType())){
                String bookFullPhoto = informationBookMapper.queryBookInfo(policyDTO.getInformationDetailId());
                if (StringUtils.isNotEmpty(bookFullPhoto)) {
                    bookFullPhoto = bookFullPhoto.substring(5,bookFullPhoto.length());
                }
                policyDTO.setAddr(bookFullPhoto);
            }
        });
        return pageInfo;
    }


}
