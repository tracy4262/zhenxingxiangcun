package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopOrder;
import com.ovit.nswy.portal.model.response.ShopCardDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDetailDTO;
import org.springframework.stereotype.Repository;
import com.ovit.nswy.frame.base.BaseMapper;

import java.util.List;
import java.util.Map;

@Repository
    public interface ShopOrderMapper  extends BaseMapper<ShopOrder> {

    ShopOrder findByCode(String code);

    List<ShopOrderDTO> findSellList(Map params);
    List<ShopOrderDTO> findAllSellList(Map params);
    List<ShopOrderDTO> findBuyList(Map params);
    List<ShopOrderDTO> findAllBuyList(Map params);

    ShopOrderDetailDTO findDetailById(Integer id);

    ShopOrderDetailDTO findDetailByCode(String code);

    String findUserId(String account);

    Integer findOrderCount(String code);

    Integer updateState(Map map);
    Integer updateSellState(Map map);
    Integer updateComplaintState(String code);

    Integer ClearShopCard(Map map);

    Integer RaiseProductVbep(String code);

    Integer ReduceProductVbep(String code);

    Integer UndercarriageProduct(String code);
}