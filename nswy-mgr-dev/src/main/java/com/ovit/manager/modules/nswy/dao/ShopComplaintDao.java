package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ShopComment;
import com.ovit.manager.modules.nswy.entity.ShopComplaint;

import java.util.Map;

@MyBatisDao
public interface ShopComplaintDao extends CrudDao<ShopComplaint> {

    void adopt(Map<String, Object> params);




}
