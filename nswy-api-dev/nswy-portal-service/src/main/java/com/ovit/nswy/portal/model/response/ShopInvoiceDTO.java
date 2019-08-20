package com.ovit.nswy.portal.model.response;

import com.ovit.nswy.portal.model.ShopInvoicePersonal;
import com.ovit.nswy.portal.model.ShopInvoiceTax;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class ShopInvoiceDTO {
    private ShopInvoicePersonal invoicePersonal;
    //税收信息
    private ShopInvoiceTax invoiceTax;
    public ShopInvoicePersonal getInvoicePersonal() {
        return invoicePersonal;
    }

    public void setInvoicePersonal(ShopInvoicePersonal invoicePersonal) {
        this.invoicePersonal = invoicePersonal;
    }

    public ShopInvoiceTax getInvoiceTax() {
        return invoiceTax;
    }

    public void setInvoiceTax(ShopInvoiceTax invoiceTax) {
        this.invoiceTax = invoiceTax;
    }
}