package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryTerrainDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTerrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryTerrainService extends CrudService<DictionaryTerrainDao, DictionaryTerrain> {

    @Autowired
    private DictionaryTerrainDao dictionaryTerrainDao;

//    public Page<DictionaryTerrain> findList(DictionaryTerrain dictionaryTerrain, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryTerrain);
//    }

    public List<DictionaryTerrain> findList(DictionaryTerrain dictionaryTerrain){
        return dictionaryTerrainDao.findList(dictionaryTerrain);
    }

    public List<DictionaryTerrain> findTerrainList(DictionaryTerrain dictionaryTerrain){

        List<DictionaryTerrain> dictionaryTerrains = dictionaryTerrainDao.findList(dictionaryTerrain);

        return dictionaryTerrains ;
    }

    public void saveTerrain(DictionaryTerrain dictionaryTerrain){
            dictionaryTerrainDao.saveTerrain(dictionaryTerrain);
    }

    public void updateTerrain(DictionaryTerrain dictionaryTerrain){
            dictionaryTerrainDao.updateTerrain(dictionaryTerrain);
    }

    public void deleteTerrain(String id){
            dictionaryTerrainDao.deleteTerrain(id);
    }


}
