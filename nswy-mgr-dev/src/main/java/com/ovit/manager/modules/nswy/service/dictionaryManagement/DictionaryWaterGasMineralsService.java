package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryWaterGasMineralsDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryWaterGasMinerals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryWaterGasMineralsService extends CrudService<DictionaryWaterGasMineralsDao, DictionaryWaterGasMinerals> {

    @Autowired
    private DictionaryWaterGasMineralsDao dictionaryWaterGasMineralsDao;

    public Page<DictionaryWaterGasMinerals> findList(DictionaryWaterGasMinerals dictionaryWaterGasMinerals, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryWaterGasMinerals);
    }

    public List<DictionaryWaterGasMinerals> findWaterGasMineralsList(DictionaryWaterGasMinerals dictionaryWaterGasMinerals){

        List<DictionaryWaterGasMinerals> dictionaryWaterGasMineralss = dictionaryWaterGasMineralsDao.findList(dictionaryWaterGasMinerals);

        return dictionaryWaterGasMineralss ;
    }

    public void saveWaterGasMinerals(DictionaryWaterGasMinerals dictionaryWaterGasMinerals){
            dictionaryWaterGasMineralsDao.saveWaterGasMinerals(dictionaryWaterGasMinerals);
    }

    public void updateWaterGasMinerals(DictionaryWaterGasMinerals dictionaryWaterGasMinerals){
            dictionaryWaterGasMineralsDao.updateWaterGasMinerals(dictionaryWaterGasMinerals);
    }

    public void deleteWaterGasMinerals(String id){
            dictionaryWaterGasMineralsDao.deleteWaterGasMinerals(id);
    }


}
