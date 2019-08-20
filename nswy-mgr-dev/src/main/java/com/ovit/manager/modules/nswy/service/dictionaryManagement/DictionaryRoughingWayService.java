package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryRoughingWayDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRoughingWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryRoughingWayService extends CrudService<DictionaryRoughingWayDao, DictionaryRoughingWay> {

    @Autowired
    private DictionaryRoughingWayDao dictionaryRoughingWayDao;

//    public Page<DictionaryRoughingWay> findList(DictionaryRoughingWay dictionaryRoughingWay, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryRoughingWay);
//    }

    public List<DictionaryRoughingWay> findList(DictionaryRoughingWay dictionaryRoughingWay){
        return dictionaryRoughingWayDao.findList(dictionaryRoughingWay);
    }

    public List<DictionaryRoughingWay> findRoughingWayList(DictionaryRoughingWay dictionaryRoughingWay){

        List<DictionaryRoughingWay> dictionaryRoughingWays = dictionaryRoughingWayDao.findList(dictionaryRoughingWay);

        return dictionaryRoughingWays ;
    }

    public void saveRoughingWay(DictionaryRoughingWay dictionaryRoughingWay){
            dictionaryRoughingWayDao.saveRoughingWay(dictionaryRoughingWay);
    }

    public void updateRoughingWay(DictionaryRoughingWay dictionaryRoughingWay){
            dictionaryRoughingWayDao.updateRoughingWay(dictionaryRoughingWay);
    }

    public void deleteRoughingWay(String id){
            dictionaryRoughingWayDao.deleteRoughingWay(id);
    }


}
