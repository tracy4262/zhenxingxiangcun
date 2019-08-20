package com.ovit.shop.pushTemplate.mapper;



import java.util.List;
import java.util.Map;


public interface ShopOrderMapper  {

/*    ShopOrder findByCode(String code);



    ShopOrderDetailDTO findDetailById(Integer id);

   */



    List<Map<String,Object>> findShopOrderListByPay();

    List<Map<String,Object>> findBuyList(Map params);

    List<Map<String,Object>> findSellList(Map params);
    List<Map<String,Object>> findAllSellList(Map params);

    List<Map<String,Object>> findAllBuyList(Map params);

    Map<String,Object> findDetailByCode(Map params);

    String findUserId(String account);

    Integer findOrderCount(String code);

    void saveShopOrder(Map<String,Object> params);

    List<Map<String,Object>> findShopCodeByCode(String orderCode);

    Integer updateState(Map map);

    void updateBuyerState(Map map);

    Integer updateSellState(Map map);
    Integer updateComplaintState(String code);

    Integer ClearShopCard(Map map);


    Integer ReduceProductVbep(String code);

    Integer UndercarriageProduct(String code);

    String getShopTypeByOrderCode(Map map);

    String getMargin(Map map);

    void updateBidStatus(Map map);
}