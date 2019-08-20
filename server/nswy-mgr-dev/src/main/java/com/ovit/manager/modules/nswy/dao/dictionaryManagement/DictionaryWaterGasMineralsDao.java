package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryWaterGasMinerals;


@MyBatisDao
public interface DictionaryWaterGasMineralsDao extends CrudDao<DictionaryWaterGasMinerals> {

    void updateWaterGasMinerals(DictionaryWaterGasMinerals dictionaryWaterGasMinerals);

    void saveWaterGasMinerals(DictionaryWaterGasMinerals dictionaryWaterGasMinerals);

    void deleteWaterGasMinerals(String id);

}
