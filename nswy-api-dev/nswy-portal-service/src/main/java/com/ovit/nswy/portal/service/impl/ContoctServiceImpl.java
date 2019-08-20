package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.portal.mapper.ContoctMapper;
import com.ovit.nswy.portal.model.request.ContoctQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import com.ovit.nswy.portal.service.ContoctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Service
public class ContoctServiceImpl implements ContoctService {

    @Autowired
    private ContoctMapper contoctMapper;

    @Override
    public ContoctDTO getContoct(ContoctQuery contoctQuery){
        ContoctDTO contoctDTO=new ContoctDTO();
        Map params= Maps.newHashMap();
        params.put("account", contoctQuery.getAccount());
        if(Integers.isNotEmpty(contoctQuery.getQueryType())){
            params.put("queryType", contoctQuery.getQueryType());
        }
        contoctDTO= contoctMapper.getContoct(params);
        return contoctDTO;
    }
}
