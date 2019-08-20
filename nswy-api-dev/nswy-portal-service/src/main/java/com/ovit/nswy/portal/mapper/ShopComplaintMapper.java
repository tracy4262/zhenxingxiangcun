package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopComplaint;
import com.ovit.nswy.portal.model.response.ShopComplaintDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import org.springframework.stereotype.Repository;
import com.ovit.nswy.frame.base.BaseMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ShopComplaintMapper extends BaseMapper<ShopComplaint> {
    List<ShopComplaintDTO> findList(Map params);
}