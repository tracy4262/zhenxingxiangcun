package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ProductTypeDao;
import com.ovit.manager.modules.nswy.entity.ProductTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductTypeService extends CrudService<ProductTypeDao, ProductTypeInfo> {

    @Autowired
    private ProductTypeDao productTypeDao;

    public List<ProductTypeInfo> findList(ProductTypeInfo productTypeInfo){

        return productTypeDao.findList(productTypeInfo);
    }

    public List<ProductTypeInfo> findByPidDesc(ProductTypeInfo productTypeInfo){

        return productTypeDao.findByPidDesc(productTypeInfo);
    }

    public List<ProductTypeInfo> findByIdDesc(ProductTypeInfo productTypeInfo){

        return productTypeDao.findByIdDesc(productTypeInfo);
    }



    public void saveProduct(ProductTypeInfo productTypeInfo){
        productTypeDao.saveProduct(productTypeInfo);
    }



    public void updateProduct(ProductTypeInfo productTypeInfo){
        productTypeDao.updateProduct(productTypeInfo);
    }



    public void deleteProduct(String id){
        productTypeDao.deleteProduct(id);
    }

}
