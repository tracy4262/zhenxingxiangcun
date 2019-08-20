package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.mapper.ShopOrderCommentMapper;
import com.ovit.nswy.portal.mapper.ShopOrderLogMapper;
import com.ovit.nswy.portal.mapper.ShopOrderMapper;
import com.ovit.nswy.portal.model.*;
import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.service.ShopOrderCommentService;
import com.ovit.nswy.portal.service.ShopOrderCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopOrderCommentServiceImpl extends BaseService<ShopOrderComment> implements ShopOrderCommentService {


    @Autowired
    private ShopOrderCommentMapper shopOrderCommentMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderLogMapper shopOrderLogMapper;

    @Override
    public Integer add(List<ShopOrderComment> entitys,String account,int from) {
       // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
      //  String account=loginUser.getLoginAccount();
        for (ShopOrderComment entity : entitys) {
            entity.setAccount(account);//当前登录人信息
            entity.setFromAccount(from);
            entity.setApproveStatus(0);
        }
        Integer result= save(entitys);
        if(result>0){
            ShopOrderComment entity=entitys.get(0);//获取一个
            String orderCode=entity.getOrderCodeId();
           if(from==ShopAccountType.Buy.ordinal()){
                //买，更新订单状态，更新日志信息
               UpdateOrderStatus(orderCode,from,OrderStatusType.BuyerComment);
               AddOrderLog(orderCode, account, OrderStatusType.BuyerComment);

            }else{
                //卖
               UpdateOrderStatus(orderCode,from,OrderStatusType.SellerComment);
               AddOrderLog(orderCode, account, OrderStatusType.SellerComment);
            }
        }
        return Integers.isEmpty(result)?0:result;
    }


    @Override
    public List<ShopOrderCommentDTO> list(Map<String, Object> map) {

        List<ShopOrderCommentDTO> list = shopOrderCommentMapper.findList(map);
        return list;
    }

    @Override
    public PageInfo<ShopOrderCommentDTO> page(Map<String, Object> query, Map<String, Object> page, String account) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(page.get("pageNum"))), Integer.parseInt(String.valueOf(page.get("pageSize"))));
        List<ShopOrderCommentDTO> list = shopOrderCommentMapper.findList(query);
        return new PageInfo<>(list);
    }



    @Override
    public Integer update(ShopOrderComment entity) {
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

    private void UpdateOrderStatus(String orderId,Integer from,OrderStatusType type){
        ShopOrder entity=shopOrderMapper.findByCode(orderId);//当前订单信息
        if(from==ShopAccountType.Buy.ordinal()){
            //买，更新订单状态，更新日志信息
            entity.setStatus(type.GetCode());//更新状态值

        }else{
            //卖
            entity.setSellStatus(type.GetCode());//更新状态值
        }
        entity.setUpdateTime(new Date());
        shopOrderMapper.updateByPrimaryKeySelective(entity);
    }
}
