package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.request.ShopOrderCommentQuery;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;

import java.util.List;
import java.util.Map;

public interface ShopOrderCommentService {


    Integer add(List<ShopOrderComment> entitys,String account,int from);

    List<ShopOrderCommentDTO> list(Map<String, Object> map);
    PageInfo<ShopOrderCommentDTO> page(Map<String, Object> map, Map<String, Object> page, String account);


    Integer update(ShopOrderComment entity);


}
