package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.core.enums.NormalEnum.OrderStatusType;
import com.ovit.nswy.portal.core.enums.NormalEnum.ShopAccountType;
import com.ovit.nswy.portal.mapper.ShopOrderCommentMapper;
import com.ovit.nswy.portal.mapper.ShopOrderLogMapper;
import com.ovit.nswy.portal.mapper.ShopOrderMapper;
import com.ovit.nswy.portal.mapper.ShopOrderOperateMapper;
import com.ovit.nswy.portal.model.ShopOrder;
import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.ShopOrderLog;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import com.ovit.nswy.portal.service.ShopOrderCommentService;
import com.ovit.nswy.portal.service.ShopOrderOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopOrderOperateServiceImpl extends BaseService<ShopOrderOperate> implements ShopOrderOperateService {


    @Autowired
    private ShopOrderOperateMapper shopOrderOperateMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderLogMapper shopOrderLogMapper;

    @Override
    public Integer add(ShopOrderOperate entity) {
       // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
       // String account=loginUser.getLoginAccount();
       // entity.setAccount(account);//当前登录人信息
        String account=entity.getAccount();
        entity.setApproveStatus(0);
      //  entity.setCreateTime(new Date());
        Integer result= saveSelective(entity);
        if(result>0){
            String orderCode=entity.getOrderCodeId();
            int from =entity.getFromAccount();//
            int type=entity.getType();
           if(from==ShopAccountType.Buy.ordinal()){
                //买，更新订单状态，更新日志信息
               if(type==1) {
                   UpdateOrderStatus(orderCode, account, OrderStatusType.BuyerCancel);
                   AddOrderLog(orderCode, account, OrderStatusType.BuyerCancel);
               }else{
                   UpdateOrderStatus(orderCode, account, OrderStatusType.BuyerRetreat);
                   AddOrderLog(orderCode, account, OrderStatusType.BuyerRetreat);
               }

            }else{
                //卖
               UpdateOrderStatus(orderCode, account, OrderStatusType.SellerCancel);
               AddOrderLog(orderCode, account, OrderStatusType.SellerCancel);
               shopOrderMapper.ReduceProductVbep(orderCode);

            }
        }
        return Integers.isEmpty(result)?0:result;
    }


    @Override
    public List<ShopOrderOperateDTO> list(Map<String, Object> map) {
        int type=(Integer)(map.get("type"));//是卖，还是买
        List<ShopOrderOperateDTO> list = shopOrderOperateMapper.findList(map);
        return list;
    }




    @Override
    public Integer update(ShopOrderOperate entity) {
        entity.setUpdateTime(new Date());
        Integer result=updateSelective(entity);
        return Integers.isEmpty(result)?0:result;
    }
    /**  添加操作日志
     * @param orderId
     * @param account
     * @param type
     */
    private void AddOrderLog(String orderId,String account,OrderStatusType type){
        ShopOrderLog entity=new ShopOrderLog();
        entity.setAccount(account);
        entity.setOrderCodeId(orderId);
        entity.setType(type.GetCode());
        entity.setLog(type.GetDesc());
        shopOrderLogMapper.insert(entity);
    }

    private void UpdateOrderStatus(String orderId,String account,OrderStatusType type){
        ShopOrder entity=shopOrderMapper.findByCode(orderId);//当前订单信息
        entity.setStatus(type.GetCode());//更新状态值
        entity.setUpdateTime(new Date());
        shopOrderMapper.updateByPrimaryKeySelective(entity);
    }
}
