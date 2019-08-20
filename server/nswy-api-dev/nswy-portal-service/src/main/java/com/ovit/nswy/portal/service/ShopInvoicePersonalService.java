package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopInvoicePersonal;

public interface ShopInvoicePersonalService {

    Integer add(ShopInvoicePersonal entity);

    Integer update(ShopInvoicePersonal entity);

    ShopInvoicePersonal selectByAccount(String account);
}
