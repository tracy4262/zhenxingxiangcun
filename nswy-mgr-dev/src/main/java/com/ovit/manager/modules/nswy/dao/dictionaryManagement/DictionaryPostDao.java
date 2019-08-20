package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPost;


@MyBatisDao
public interface DictionaryPostDao extends CrudDao<DictionaryPost> {

    void updatePost(DictionaryPost dictionaryPost);

    void savePost(DictionaryPost dictionaryPost);

    void deletePost(String id);

}
