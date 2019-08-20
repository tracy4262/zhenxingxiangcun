package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;
import com.ovit.nswy.portal.mapper.ShopComplaintMapper;
import com.ovit.nswy.portal.mapper.ShopOrderMapper;
import com.ovit.nswy.portal.model.ShopComplaint;
import com.ovit.nswy.portal.model.response.ShopComplaintDTO;
import com.ovit.nswy.portal.service.ShopComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopComplaintServiceImpl extends BaseService<ShopComplaint> implements ShopComplaintService {


    @Autowired
    private ShopComplaintMapper shopComplaintMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;


    @Override
    public Integer add(ShopComplaint entity) {
        //LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        //String account=loginUser.getLoginAccount();
        entity.setCreateTime(new Date());
        entity.setApproveStatus(0);
        Integer result= saveSelective(entity);
        if(result>0){
            shopOrderMapper.updateComplaintState(entity.getOrderCodeId());
        }
        return Integers.isEmpty(result)?0:result;
    }


    @Override
    public List<ShopComplaintDTO> list(Map<String, Object> map) {
        int type = (Integer) (map.containsKey("type") ? map.get("type") : -1);//是卖，还是买
        if (type >= 0) {
           // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
            String account=map.get("account").toString();
            if (type == ShopAccountType.Buy.ordinal()) {
                map.put("buyerAccount",account);//当前登录人信息
            }else{
                map.put("sellerAccount",account);//当前登录人信息
                map.put("approveStatus",1);//卖家这里只能查看审批通过的
            }
        }
        List<ShopComplaintDTO> list = shopComplaintMapper.findList(map);
        return list;
    }

    @Override
    public List<ShopComplaintDTO> list(String code) {
        Map<String,Object> map=new HashMap<>();
        map.put("orderCode",code);//当前登录人信息
        List<ShopComplaintDTO> list = shopComplaintMapper.findList(map);
        return list;
    }


    @Override
    public Integer update(ShopComplaint entity) {
        entity.setUpdateTime(new Date());
        Integer result=updateSelective(entity);
        return Integers.isEmpty(result)?0:result;
    }

}
