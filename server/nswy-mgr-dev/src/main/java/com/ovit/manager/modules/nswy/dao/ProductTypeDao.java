package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ProductTypeInfo;

import java.util.List;

@MyBatisDao
public interface ProductTypeDao extends CrudDao<ProductTypeInfo> {

    List<ProductTypeInfo> findByPidDesc(ProductTypeInfo productTypeInfo);

    List<ProductTypeInfo> findByIdDesc(ProductTypeInfo productTypeInfo);

    void updateProduct(ProductTypeInfo productTypeInfo);

    void saveProduct(ProductTypeInfo productTypeInfo);

    void deleteProduct(String id);
}
