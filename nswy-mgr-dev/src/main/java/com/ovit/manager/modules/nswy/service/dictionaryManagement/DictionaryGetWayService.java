package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryGetWayDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGetWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryGetWayService extends CrudService<DictionaryGetWayDao, DictionaryGetWay> {

    @Autowired
    private DictionaryGetWayDao dictionaryGetWayDao;

//    public Page<DictionaryGetWay> findList(DictionaryGetWay dictionaryGetWay, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryGetWay);
//    }

    public List<DictionaryGetWay> findList(DictionaryGetWay dictionaryGetWay){
        return dictionaryGetWayDao.findList(dictionaryGetWay);
    }

    public List<DictionaryGetWay> findGetWayList(DictionaryGetWay dictionaryGetWay){

        List<DictionaryGetWay> dictionaryGetWays = dictionaryGetWayDao.findList(dictionaryGetWay);

        return dictionaryGetWays ;
    }

    public void saveGetWay(DictionaryGetWay dictionaryGetWay){
            dictionaryGetWayDao.saveGetWay(dictionaryGetWay);
    }

    public void updateGetWay(DictionaryGetWay dictionaryGetWay){
            dictionaryGetWayDao.updateGetWay(dictionaryGetWay);
    }

    public void deleteGetWay(String id){
            dictionaryGetWayDao.deleteGetWay(id);
    }


}
