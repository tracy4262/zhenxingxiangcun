package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryProductTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryProductTypeService extends CrudService<DictionaryProductTypeDao, DictionaryProductType> {

    @Autowired
    private DictionaryProductTypeDao dictionaryProductTypeDao;

    /*public Page<DictionaryProductType> findList(DictionaryProductType dictionaryProductType, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryProductType);
    }*/

    public List<DictionaryProductType> findList(DictionaryProductType dictionaryProductType){
        return dictionaryProductTypeDao.findList(dictionaryProductType);
    }
    
    public List<DictionaryProductType> findProductTypeList(DictionaryProductType dictionaryProductType){

        List<DictionaryProductType> dictionaryProductTypes = dictionaryProductTypeDao.findList(dictionaryProductType);

        return dictionaryProductTypes ;
    }

    public void saveProductType(DictionaryProductType dictionaryProductType){
            dictionaryProductTypeDao.saveProductType(dictionaryProductType);
    }

    public void updateProductType(DictionaryProductType dictionaryProductType){
            dictionaryProductTypeDao.updateProductType(dictionaryProductType);
    }

    public void deleteProductType(String id){
            dictionaryProductTypeDao.deleteProductType(id);
    }


}
