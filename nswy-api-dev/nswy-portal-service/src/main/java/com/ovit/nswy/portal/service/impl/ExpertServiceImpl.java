package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.portal.mapper.ContoctMapper;
import com.ovit.nswy.portal.mapper.ExpertMapper;
import com.ovit.nswy.portal.model.request.ExpertQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import com.ovit.nswy.portal.model.response.ExpertDTO;
import com.ovit.nswy.portal.service.ExpertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Service
@Slf4j
public class ExpertServiceImpl implements ExpertService {

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private ContoctMapper contoctMapper;

    @Override
    public PageInfo<ExpertDTO> listExpert(ExpertQuery ExpertQuery){
        Map params= Maps.newHashMap();
        PageHelper.startPage(ExpertQuery.getPageNum(), ExpertQuery.getPageSize());
        Integer userType=Maps.getInteger(params,"userType");
        params.put("account",ExpertQuery.getAccount());
        if(Integers.isNotEmpty(ExpertQuery.getQueryType())){
            params.put("queryType",ExpertQuery.getQueryType());
        }

        List<ExpertDTO> list= expertMapper.getlist(params);
        log.info("获取专家团队数据列表：{}",list);
        PageInfo<ExpertDTO> pageInfo= new PageInfo(list);
        return pageInfo;
    }
}
