package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyJuBao;

/**
 * Created by ${huipei.x} on 2017-8-17.
 */
@MyBatisDao
public interface NswyJuBaoDao extends CrudDao<NswyJuBao> {

    NswyJuBao getByPrimaryKey(Integer juBaoId);
    void  deleteByPrimaryKey(Integer juBaoId);
    void updateByPrimaryKeySelective(NswyJuBao nswyJuBao);
}
