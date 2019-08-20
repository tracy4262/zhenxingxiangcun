package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.TechnicalTitleInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface TechnicalTitleDao extends CrudDao<TechnicalTitleInfo> {


   void updateTitle(TechnicalTitleInfo technicalTitleInfo);

   void saveTitle(TechnicalTitleInfo technicalTitleInfo);

   void deleteTitle(String id);






}
