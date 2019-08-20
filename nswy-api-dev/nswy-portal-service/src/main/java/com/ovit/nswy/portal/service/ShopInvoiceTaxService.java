package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopInvoiceTax;

import java.util.List;

public interface ShopInvoiceTaxService {

    Integer add(ShopInvoiceTax entity);

    Integer update(ShopInvoiceTax entity);

    ShopInvoiceTax selectByAccount(String account);
}
