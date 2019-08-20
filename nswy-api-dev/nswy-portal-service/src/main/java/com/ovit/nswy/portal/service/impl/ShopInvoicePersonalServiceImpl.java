package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.portal.mapper.ShopAddressMapper;
import com.ovit.nswy.portal.mapper.ShopInvoicePersonalMapper;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopInvoicePersonal;
import com.ovit.nswy.portal.service.ShopAddressService;
import com.ovit.nswy.portal.service.ShopInvoicePersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by wangxy
 */
@Service
public class ShopInvoicePersonalServiceImpl extends BaseService<ShopInvoicePersonal> implements ShopInvoicePersonalService {

    @Override
    public Integer add(ShopInvoicePersonal entity) {
        Integer result = saveSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }

    @Override
    public ShopInvoicePersonal selectByAccount(String account) {
        ShopInvoicePersonal query = new ShopInvoicePersonal();
        query.setAccount(account);
        ShopInvoicePersonal entity = this.findOne(query);
        return entity;
    }

    @Override
    public Integer update(ShopInvoicePersonal entity) {
        entity.setUpdateTime(new Date());
        Integer result = updateSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }


}
