package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductType;


@MyBatisDao
public interface DictionaryProductTypeDao extends CrudDao<DictionaryProductType> {

    void updateProductType(DictionaryProductType dictionaryProductType);

    void saveProductType(DictionaryProductType dictionaryProductType);

    void deleteProductType(String id);

}
