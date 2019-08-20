package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.NetworkCommMapper;
import com.ovit.nswy.member.service.NetworkCommService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 网络通信条件
 *
 * @author haoWen
 * @create 2017-12-27 10:20
 **/
@Service
public class NetworkCommServiceImpl implements NetworkCommService{
    @Autowired
    private NetworkCommMapper networkCommMapper;
    @Override
    public int save(Map<String, Object> params) {
        return networkCommMapper.save(params);
    }

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String, Object> networkMap = networkCommMapper.query(params);
        if (MapUtils.isNotEmpty(networkMap)) {
            StringBuffer describe = new StringBuffer();
            describe.append("可得到");
            if (MapUtils.getString(networkMap,"postTelephoneService").equals("Y")){
                describe.append("邮电");
            }
            if (MapUtils.getString(networkMap,"fixedTelephone").equals("Y")){
                describe.append("/固话");
            }
            if (MapUtils.getString(networkMap,"broadbandPort").equals("Y")){
                describe.append("/宽带");
            }
            if (MapUtils.getString(networkMap,"tvPort").equals("Y")){
                describe.append("/TV");
            }
            describe.append("等方面的服务。");
            networkMap.put("describe",describe.toString().replace("null",""));
        }
        return networkMap;
    }
}
