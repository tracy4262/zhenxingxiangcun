package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.core.enums.NormalEnum.InvoiceType;
import com.ovit.nswy.portal.core.enums.NormalEnum.OrderStatusType;
import com.ovit.nswy.portal.core.enums.NormalEnum.ShopAccountType;
import com.ovit.nswy.portal.mapper.*;
import com.ovit.nswy.portal.model.*;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDetailDTO;
import com.ovit.nswy.portal.service.ShopOrderProductService;
import com.ovit.nswy.portal.service.ShopOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopOrderProductServiceImpl extends BaseService< ShopOrderProduct>  implements ShopOrderProductService {


    private static final Logger logger = LogManager.getLogger(ShopOrderProductServiceImpl.class);


    @Autowired
    private ShopOrderProductMapper shopOrderProductMapper;


    @Override
    public List<ShopOrderProduct> findByOrder(String code) {

        return shopOrderProductMapper.findByOrderCode(code);
    }
}
