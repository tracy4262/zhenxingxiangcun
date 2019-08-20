package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.portal.mapper.ShopInvoicePersonalMapper;
import com.ovit.nswy.portal.mapper.ShopInvoiceTaxMapper;
import com.ovit.nswy.portal.model.ShopInvoicePersonal;
import com.ovit.nswy.portal.model.ShopInvoiceTax;
import com.ovit.nswy.portal.service.ShopInvoicePersonalService;
import com.ovit.nswy.portal.service.ShopInvoiceTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wangxy
 */
@Service
public class ShopInvoiceTaxServiceImpl extends BaseService<ShopInvoiceTax> implements ShopInvoiceTaxService {



    @Override
    public Integer add(ShopInvoiceTax entity) {
        Integer result = saveSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }
    
    @Override
    public ShopInvoiceTax selectByAccount(String account) {
        ShopInvoiceTax query = new ShopInvoiceTax();
        query.setAccount(account);
        ShopInvoiceTax entity = this.findOne(query);
        return entity;
    }

    @Override
    public Integer update(ShopInvoiceTax entity) {
        entity.setUpdateTime(new Date());
        Integer result = updateSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }


}
