package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUnit;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface DictionaryUnitDao extends CrudDao<DictionaryUnit> {

    List<Map<String,Object>> findUnitType();

    List<DictionaryUnit> findUnit(DictionaryUnit dictionaryUnit);

    List<DictionaryUnit> findTopUnit(DictionaryUnit dictionaryUnit);

    void updateUnit(DictionaryUnit dictionaryUnit);

    void updateTopUnit(DictionaryUnit dictionaryUnit);

    void saveUnit(DictionaryUnit dictionaryUnit);

    void saveTopUnit(DictionaryUnit dictionaryUnit);

    void deleteUnit(String id);

    void deleteTopUnit(String id);

    Integer findMaxUnitType();
}
