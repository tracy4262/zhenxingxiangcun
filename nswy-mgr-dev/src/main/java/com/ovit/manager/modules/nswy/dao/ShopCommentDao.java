package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ShopComment;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;

import java.util.Map;

@MyBatisDao
public interface ShopCommentDao extends CrudDao<ShopComment> {

    void adopt(Map<String, Object> params);




}
