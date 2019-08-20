package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryProductionWayDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProductionWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryProductionWayService extends CrudService<DictionaryProductionWayDao, DictionaryProductionWay> {

    @Autowired
    private DictionaryProductionWayDao dictionaryProductionWayDao;

//    public Page<DictionaryProductionWay> findList(DictionaryProductionWay dictionaryProductionWay, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryProductionWay);
//    }

    public List<DictionaryProductionWay> findList(DictionaryProductionWay dictionaryProductionWay){
        return dictionaryProductionWayDao.findList(dictionaryProductionWay);
    }

    public List<DictionaryProductionWay> findProductionWayList(DictionaryProductionWay dictionaryProductionWay){

        List<DictionaryProductionWay> dictionaryProductionWays = dictionaryProductionWayDao.findList(dictionaryProductionWay);

        return dictionaryProductionWays ;
    }

    public void saveProductionWay(DictionaryProductionWay dictionaryProductionWay){
            dictionaryProductionWayDao.saveProductionWay(dictionaryProductionWay);
    }

    public void updateProductionWay(DictionaryProductionWay dictionaryProductionWay){
            dictionaryProductionWayDao.updateProductionWay(dictionaryProductionWay);
    }

    public void deleteProductionWay(String id){
            dictionaryProductionWayDao.deleteProductionWay(id);
    }


}
