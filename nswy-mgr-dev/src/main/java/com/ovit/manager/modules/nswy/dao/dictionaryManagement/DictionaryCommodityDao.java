package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCommodity;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface DictionaryCommodityDao extends CrudDao<DictionaryCommodity> {

    List<Map<String,Object>> findIndustry();

    List<Map<String,Object>> findProductType();

    void updateCommodity(DictionaryCommodity dictionaryCommodity);

    void saveCommodity(DictionaryCommodity dictionaryCommodity);

    void deleteCommodity(String id);

}
