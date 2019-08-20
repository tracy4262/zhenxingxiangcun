package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;
import org.springframework.stereotype.Repository;
import com.ovit.nswy.frame.base.BaseMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ShopOrderCommentMapper  extends BaseMapper<ShopOrderComment> {


    List<ShopOrderCommentDTO> findList(Map params);
}
