package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopComplaint;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopComplaintDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;

import java.util.List;
import java.util.Map;

public interface ShopComplaintService {


    Integer add(ShopComplaint entity);

    List<ShopComplaintDTO> list(String code);
    List<ShopComplaintDTO> list(Map<String, Object> map);

    Integer update(ShopComplaint entity);

}
