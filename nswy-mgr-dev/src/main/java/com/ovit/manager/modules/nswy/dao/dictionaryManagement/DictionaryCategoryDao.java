package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCategory;


@MyBatisDao
public interface DictionaryCategoryDao extends CrudDao<DictionaryCategory> {

    void updateCategory(DictionaryCategory dictionaryCategory);

    void saveCategory(DictionaryCategory dictionaryCategory);

    void deleteCategory(String id);

}
