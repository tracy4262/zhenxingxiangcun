package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryNationalityDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNationality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryNationalityService extends CrudService<DictionaryNationalityDao, DictionaryNationality> {

    @Autowired
    private DictionaryNationalityDao dictionaryNationalityDao;

//    public Page<DictionaryNationality> findList(DictionaryNationality dictionaryNationality, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryNationality);
//    }

    public List<DictionaryNationality> findList(DictionaryNationality dictionaryNationality){
        return dictionaryNationalityDao.findList(dictionaryNationality);
    }

    public List<DictionaryNationality> findNationalityList(DictionaryNationality dictionaryNationality){

        List<DictionaryNationality> dictionaryNationalitys = dictionaryNationalityDao.findList(dictionaryNationality);

        return dictionaryNationalitys ;
    }

    public void saveNationality(DictionaryNationality dictionaryNationality){
            dictionaryNationalityDao.saveNationality(dictionaryNationality);
    }

    public void updateNationality(DictionaryNationality dictionaryNationality){
            dictionaryNationalityDao.updateNationality(dictionaryNationality);
    }

    public void deleteNationality(String id){
            dictionaryNationalityDao.deleteNationality(id);
    }


}
