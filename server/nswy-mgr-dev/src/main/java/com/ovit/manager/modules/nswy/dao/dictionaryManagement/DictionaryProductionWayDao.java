package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductionWay;


@MyBatisDao
public interface DictionaryProductionWayDao extends CrudDao<DictionaryProductionWay> {

    void updateProductionWay(DictionaryProductionWay dictionaryProductionWay);

    void saveProductionWay(DictionaryProductionWay dictionaryProductionWay);

    void deleteProductionWay(String id);

}
