package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryCategoryDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryCategoryService extends CrudService<DictionaryCategoryDao, DictionaryCategory> {

    @Autowired
    private DictionaryCategoryDao dictionaryCategoryDao;

    /*public Page<DictionaryCategory> findList(DictionaryCategory dictionaryCategory, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryCategory);
    }*/

    public List<DictionaryCategory> findList(DictionaryCategory dictionaryCategory){
        return dictionaryCategoryDao.findList(dictionaryCategory);
    }
    
    public List<DictionaryCategory> findCategoryList(DictionaryCategory dictionaryCategory){

        List<DictionaryCategory> dictionaryCategorys = dictionaryCategoryDao.findList(dictionaryCategory);

        return dictionaryCategorys ;
    }

    public void saveCategory(DictionaryCategory dictionaryCategory){
            dictionaryCategoryDao.saveCategory(dictionaryCategory);
    }

    public void updateCategory(DictionaryCategory dictionaryCategory){
            dictionaryCategoryDao.updateCategory(dictionaryCategory);
    }

    public void deleteCategory(String id){
            dictionaryCategoryDao.deleteCategory(id);
    }


}
