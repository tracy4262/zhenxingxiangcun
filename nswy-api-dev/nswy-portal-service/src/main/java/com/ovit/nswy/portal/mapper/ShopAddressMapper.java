package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.frame.base.BaseMapper;
import com.ovit.nswy.portal.model.ShopOrder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShopAddressMapper  extends BaseMapper<ShopAddress> {

    Integer clearDefault(ShopAddress entity);

}