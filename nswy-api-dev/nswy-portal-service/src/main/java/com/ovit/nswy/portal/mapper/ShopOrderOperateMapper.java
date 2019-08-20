package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import org.springframework.stereotype.Repository;
import com.ovit.nswy.frame.base.BaseMapper;

import java.util.List;
import java.util.Map;

@Repository
    public interface ShopOrderOperateMapper  extends BaseMapper<ShopOrderOperate> {

    List<ShopOrderOperateDTO> findList(Map params);

    }