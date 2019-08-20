package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ExpertApply;

import java.util.List;
import java.util.Map;

/**
 * Created by ${zhanlei} on 2017-10-18.
 */
@MyBatisDao
public interface SpeciesDao extends CrudDao<ExpertApply> {

	List<Map<String,Object>> getSpeciesList(Map<String,Object> params);
}