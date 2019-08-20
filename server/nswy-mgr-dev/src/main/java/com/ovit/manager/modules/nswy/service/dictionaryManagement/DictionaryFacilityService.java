package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryFacilityDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryFacilityService extends CrudService<DictionaryFacilityDao, DictionaryFacility> {

    @Autowired
    private DictionaryFacilityDao dictionaryFacilityDao;

//    public Page<DictionaryFacility> findList(DictionaryFacility dictionaryFacility, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryFacility);
//    }

    public List<DictionaryFacility> findList(DictionaryFacility dictionaryFacility){
        return dictionaryFacilityDao.findList(dictionaryFacility);
    }

    public List<DictionaryFacility> findFacilityList(DictionaryFacility dictionaryFacility){

        List<DictionaryFacility> dictionaryFacilitys = dictionaryFacilityDao.findList(dictionaryFacility);

        return dictionaryFacilitys ;
    }

    public void saveFacility(DictionaryFacility dictionaryFacility){
            dictionaryFacilityDao.saveFacility(dictionaryFacility);
    }

    public void updateFacility(DictionaryFacility dictionaryFacility){
            dictionaryFacilityDao.updateFacility(dictionaryFacility);
    }

    public void deleteFacility(String id){
            dictionaryFacilityDao.deleteFacility(id);
    }


}
