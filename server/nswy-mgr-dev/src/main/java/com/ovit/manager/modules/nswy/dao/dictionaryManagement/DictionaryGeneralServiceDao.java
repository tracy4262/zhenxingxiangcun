package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGeneralService;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface DictionaryGeneralServiceDao extends CrudDao<DictionaryGeneralService> {

    List<Map<String,Object>> findIndustry();//行业分类

    List<Map<String,Object>> findSpecies();

    List<Map<String,Object>> findServiceType();//服务分类

    List<Map<String,Object>> findService();//关联服务

    List<Map<String,Object>> findProduct();//关联产品

    void updateGeneralService(DictionaryGeneralService dictionaryGeneralService);

    void saveGeneralService(DictionaryGeneralService dictionaryGeneralService);

    void deleteGeneralService(String id);

}
