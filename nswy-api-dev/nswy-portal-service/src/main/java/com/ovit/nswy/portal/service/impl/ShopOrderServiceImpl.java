package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.portal.mapper.*;
import com.ovit.nswy.portal.model.*;
import com.ovit.nswy.portal.model.response.ShopOrderAddDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDetailDTO;
import com.ovit.nswy.portal.model.response.ShopOrderSellDTO;
import com.ovit.nswy.portal.service.ShopOrderService;
import com.ovit.nswy.portal.util.Utils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxy
 */
@Service
public class ShopOrderServiceImpl extends BaseService<ShopOrder>  implements ShopOrderService {


    private static final Logger logger = LogManager.getLogger(ShopOrderServiceImpl.class);

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderProductMapper shopOrderProductMapper;

    @Autowired
    private ShopOrderLogMapper shopOrderLogMapper;


    @Override
    public Integer add(ShopOrderAddDTO dto, String account) {
        int result = 0;
        ShopOrder baseEntity = BeanByRefMapper.copyByRefMapper(dto, ShopOrder.class);
        List<ShopOrderSellDTO> selles = dto.getShopProducts();
        for (ShopOrderSellDTO sell : selles) {
            ShopOrder entity = baseEntity;
            String orderCode = ChargeOrderCode(account);
            if (orderCode != null) {
                entity.setAccount(account);//当前登录人信息
                entity.setOrderCode(orderCode);//订单号
                entity.setSellAccount(sell.getProductAccount());
                int i = saveSelective(entity);
                if (i > 0) {
                    List<ShopOrderProduct> products = sell.getData();
                    if (products != null) {
                        for (ShopOrderProduct item : products) {
                            item.setOrderCodeId(orderCode);//订单号
                        }
                        shopOrderProductMapper.insertList(products);//批量添加数据
                    }
                    AddOrderLog(orderCode, account, OrderStatusType.SendFor);
                    result += i;
                    ClearShopCard(orderCode,account,dto.getType());

                }
            }
        }
        return Integers.isEmpty(result) ? 0 : result;

    }


    @Override
    public PageInfo<ShopOrderDTO> list(Map<String, Object> map,Map<String, Object> page,String account) {

        Map<String, Object> query = new HashMap<>();
        query.put("account", account);
        if (StringUtils.isNotBlank(MapUtils.getString(map, "buyer"))) {
            query.put("buyer", MapUtils.getString(map, "buyer"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "seller"))) {
            query.put("seller", MapUtils.getString(map, "seller"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "startDate"))) {
            query.put("startDate", MapUtils.getString(map, "startDate"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "endDate"))) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");/*** 加一天*/
            try {
                Date dd = df.parse(MapUtils.getString(map, "endDate"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dd);
                calendar.add(Calendar.DAY_OF_MONTH, 1);//加一天
                query.put("endDate", df.format(calendar.getTime()));
            } catch (Exception ex) {

            }
        }
        String productName = MapUtils.getString(map, "productName");//商品信息
        if (StringUtils.isNotBlank(productName)) {
            query.put("productName", productName);
        }
        Integer from = MapUtils.getInteger(map, "from"); //是卖，还是买
        Integer state = MapUtils.getInteger(map, "state");//tab页状态
        Integer dealState = MapUtils.getInteger(map, "dealState", 0);
        Integer judgeState = MapUtils.getInteger(map, "judgeState", 0);
        if (state.equals(0)) {
            //目前设定，当交易状态为0的时候，
            query= getBasicQuery(query,from,dealState,judgeState);
        } else {
            query= getBasicQuery(query,state,from,dealState,judgeState);
        }
        PageHelper.startPage(Integer.parseInt(String.valueOf(page.get("pageNum"))), Integer.parseInt(String.valueOf(page.get("pageSize"))));
        List<ShopOrderDTO> list = null;
        if (StringUtils.isNotBlank(productName)) {
            if (from == ShopAccountType.Buy.ordinal()) {
                //买
                list = shopOrderMapper.findBuyList(query);
            } else {
                //卖
                list = shopOrderMapper.findSellList(query);
            }
            if (list != null && list.size() > 0) {
                query.clear();
                query.put("productName", productName);
                for (ShopOrderDTO item : list) {
                    query.put("code", item.getOrderCode());
                    item.setShopProducts(shopOrderProductMapper.selectExtendByOrderCode(query));
                }
            }
        } else {
            if (from == ShopAccountType.Buy.ordinal()) {
                //买
                list = shopOrderMapper.findAllBuyList(query);
            } else {
                //卖
                list = shopOrderMapper.findAllSellList(query);
            }
        }
        return new PageInfo<>(list);
    }

    //全部订单下，查询信息
    private  Map<String, Object>  getBasicQuery(Map<String, Object> query,Integer from,Integer dealState,Integer judgeState) {
        int status = 0;//等待发货
        int sellStatus = 0;//默认为null
        List<Integer> inStatus = null;
        if (dealState.equals(0) || (from == ShopAccountType.Sell.ordinal() && dealState.equals(4))) {
            if (judgeState.equals(1)) {
                //待评价
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.TakeOver.GetCode();//等待评价
                } else {
                    sellStatus = 1;//没有评价
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                }
            } else if (judgeState.equals(2)) {
                //已评价
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.BuyerComment.GetCode();//评价
                } else {
                    sellStatus = OrderStatusType.SellerComment.GetCode();//评价
                }
            } else if (judgeState.equals(3)) {
                //双方已评
                status = OrderStatusType.BuyerComment.GetCode();//评价
                sellStatus = OrderStatusType.SellerComment.GetCode();//评价
            } else {
                if (dealState.equals(4)) {
                    //卖家，成功的订单
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                }
            }
        } else {
            if (judgeState.equals(0)) {
                //代发货
                if (dealState.equals(1)) {
                    status = OrderStatusType.SendFor.GetCode();//等待发货
                } else if (dealState.equals(2)) {
                    //已发货
                    status = OrderStatusType.SendOut.GetCode();//等待收货
                } else if (dealState.equals(3)) {
                    //等待评价
                    if (from == ShopAccountType.Buy.ordinal()) {
                        status = OrderStatusType.TakeOver.GetCode();//等待评价
                    } else {
                        sellStatus = 1;//卖家没有评价
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                    }
                } else if (dealState.equals(4)) {
                    //买家，已取消，无卖家
                    inStatus = new ArrayList<>();
                  //  inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                    inStatus.add(OrderStatusType.CancelOver.GetCode());
                    inStatus.add(OrderStatusType.SellerCancel.GetCode());
                } else if (dealState.equals(5)) {
                    //买家，已退货，卖家，关闭的订单
                    inStatus = new ArrayList<>();
                   // inStatus.add(OrderStatusType.BuyerRetreat.GetCode());
                    inStatus.add(OrderStatusType.RetreatOver.GetCode());
                    if (from == ShopAccountType.Sell.ordinal()) {
                       // inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                        inStatus.add(OrderStatusType.CancelOver.GetCode());
                        inStatus.add(OrderStatusType.SellerCancel.GetCode());
                    }
                }else if (dealState.equals(6)) {
                    //买家， 卖家，取消中
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.BuyerCancel.GetCode());//买家取消
                }
                else if (dealState.equals(7)) {
                    //买家，卖家，退货中
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.BuyerRetreat.GetCode());//买家退货

                }
                else{
                    status = 100;
                }
            }else if (judgeState.equals(1)) {
                if (dealState.equals(3)) {
                    //等待评价
                    if (from == ShopAccountType.Buy.ordinal()) {
                        status = OrderStatusType.TakeOver.GetCode();//等待评价
                    } else {
                        sellStatus = 1;//卖家没有评价
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                    }
                }else{
                    status = 100;
                }

            }else {
                status = 100;
            }
        }
        if (status > 0) {
            query.put("status", status);
        }
        if (sellStatus > 0) {
            query.put("sellStatus", sellStatus);
        }
        if (inStatus != null && inStatus.size() > 0) {
            query.put("inStatus", inStatus);
        }
        return query;
    }

    private  Map<String, Object>  getBasicQuery(Map<String, Object> query,Integer state,Integer from,Integer dealState,Integer judgeState) {
        int status = 100;//这里自动过滤所有状态信息
        int sellStatus = 0;//默认为null
        List<Integer> inStatus = null;
        if (state.equals(1)) {
            //等待发货，只有交易状态为等待发货生效
            if (judgeState.equals(0) && (dealState.equals(0) ||dealState.equals(1))) {
                status = OrderStatusType.SendFor.GetCode();//等待发货
            }
        } else if (state.equals(2)) {
            //等待发货，只有交易状态为等待发货生效
            if (judgeState.equals(0) && (dealState.equals(0) ||dealState.equals(2))) {
                status = OrderStatusType.SendOut.GetCode();//等待收货
            }
        } else if (state.equals(3)) {
            //等待评价，交易状态位等待评价，与成功的订单
            if ((judgeState.equals(0)||judgeState.equals(1))&&(dealState.equals(0) || dealState.equals(3))) {
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.TakeOver.GetCode();//等待评价
                } else {
                    status = 0;
                    sellStatus = 1;//没有评价
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                }
            }
        } else if (state.equals(4)) {
            //买家，成功的订单
            if (from == ShopAccountType.Sell.ordinal()) {
                //不成功的订单，分为交易状态是取消或者不取消
                if (dealState.equals(0) || dealState.equals(4)) {
                    status = 0;
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                    if (judgeState.equals(1)) {
                        sellStatus = 1;//评价
                    } else if (judgeState.equals(2)) {
                        sellStatus = OrderStatusType.BuyerComment.GetCode();//评价
                    } else if (judgeState.equals(3)) {
                        inStatus = null;
                        status = OrderStatusType.BuyerComment.GetCode();//评价
                        sellStatus = OrderStatusType.SellerComment.GetCode();//评价
                    }
                } else if (dealState.equals(3)) {
                    //等待评价
                    if (judgeState.equals(1)) {
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                        sellStatus = 1;//评价
                        status = 0;
                    }
                }
            }
        } else if (state.equals(5)) {
            //买家，关闭的订单
            if (from == ShopAccountType.Sell.ordinal()) {
                //不成功的订单，分为交易状态是取消或者不取消
                if (judgeState.equals(0)&&(dealState.equals(0) || dealState.equals(5))) {
                    status = 0;
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                    inStatus.add(OrderStatusType.CancelOver.GetCode());
                    inStatus.add(OrderStatusType.SellerCancel.GetCode());
                    inStatus.add(OrderStatusType.BuyerRetreat.GetCode());
                    inStatus.add(OrderStatusType.RetreatOver.GetCode());
                }
            }
        }
        if (status > 0) {
            query.put("status", status);
        }
        if (sellStatus > 0) {
            query.put("sellStatus", sellStatus);
        }
        if (inStatus != null && inStatus.size() > 0) {
            query.put("inStatus", inStatus);
        }
        return query;
    }
    @Override
    public ShopOrderDetailDTO selectOne(Integer id) {
        ShopOrderDetailDTO entity = null;
        if (Integers.isNotEmpty(id)) {
            entity = shopOrderMapper.findDetailById(id);
        }
        return entity;
    }

    @Override
    public ShopOrderDetailDTO selectOne(String code) {
        ShopOrderDetailDTO entity = null;
        if (code != null && code.length() > 0) {
            entity = shopOrderMapper.findDetailByCode(code);
        }
        return entity;
    }

    @Override
    public Integer update(ShopOrderDetailDTO dto, String account) {

        ShopOrder entity = BeanByRefMapper.copyByRefMapper(dto, ShopOrder.class);
        entity.setAccount(account);
        entity.setUpdateTime(new Date());
        Integer result = updateSelective(entity);
        if (result > 0) {
            List<ShopOrderProduct> products = dto.getShopProducts();
            if (products != null) {
                for (ShopOrderProduct item : products) {
                    shopOrderProductMapper.updateByPrimaryKeySelective(item);//批量添加数据
                }
            }
            shopOrderProductMapper.insertList(products);//批量添加数据
        }
        return Integers.isEmpty(result) ? 0 : result;
    }

    @Override
    public Integer updateState(String orderCode, OrderStatusType state, String account) {
        Map<String,Object> map=new HashMap<>();
        map.put("code",orderCode);
        map.put("state",state.GetCode());
        int result = shopOrderMapper.updateState(map);//批量添加数据
        AddOrderLog(orderCode, account, state);
        /*
        if(state==OrderStatusType.SendOut) {
            AddOrderLog(orderCode, account, OrderStatusType.SendOut);
        }else  if(state==OrderStatusType.TakeOver){
            AddOrderLog(orderCode, account, OrderStatusType.TakeOver);
        }
        */
        if(result>0) {
            if (state == OrderStatusType.CancelOver || state == OrderStatusType.SellerCancel || state == OrderStatusType.RetreatOver) {
                //卖卖家取消及卖家同意退货，还原库存
                shopOrderMapper.ReduceProductVbep(orderCode);
            }
        }
        return Integers.isEmpty(result) ? 0 : result;
    }

    /**
     * 添加操作日志
     *
     * @param orderId
     * @param account
     * @param type
     */
    protected void AddOrderLog(String orderId, String account, OrderStatusType type) {
        ShopOrderLog entity = new ShopOrderLog();
        entity.setAccount(account);
        entity.setOrderCodeId(orderId);
        entity.setType(type.GetCode());
        entity.setLog(type.GetDesc());
        shopOrderLogMapper.insert(entity);
    }

    private void ClearShopCard(String orderCode, String account,Integer type) {
        try {
            if (type != null && type.intValue() == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("code", orderCode);
                map.put("account", account);
                shopOrderMapper.ClearShopCard(map);//清理购物车
            }
            shopOrderMapper.RaiseProductVbep(orderCode);//这里减少库存
            shopOrderMapper.UndercarriageProduct(orderCode);//这里下架库存为0的商品


        } catch (Exception ex) {
            logger.debug(ex);
        }
    }

    private String GetOrderCode(String account) {
        String id = shopOrderMapper.findUserId(account);
        if (id != null) {
            String str = id.substring(id.length() - 4);
            return ChargeOrderCode(str);
        } else {
            return null;
        }
    }

    private String ChargeOrderCode(String id) {

        String str = Utils.builderRandomString(16);// + id;//12位随机数
        //这里需要判断随机码是否已经存在
        int count = shopOrderMapper.findOrderCount(str);
        if (count > 0) {
            return ChargeOrderCode(id);
        } else {
            return str;
        }
    }
}
