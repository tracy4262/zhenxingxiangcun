package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryColumnDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryColumnService extends CrudService<DictionaryColumnDao, DictionaryColumn> {

    @Autowired
    private DictionaryColumnDao dictionaryColumnDao;

//    public Page<DictionaryColumn> findList(DictionaryColumn dictionaryColumn, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryColumn);
//    }

    public List<DictionaryColumn> findList(DictionaryColumn dictionaryColumn){
        return dictionaryColumnDao.findList(dictionaryColumn);
    }

    public List<DictionaryColumn> findColumnList(DictionaryColumn dictionaryColumn){

        List<DictionaryColumn> dictionaryColumns = dictionaryColumnDao.findList(dictionaryColumn);

        return dictionaryColumns ;
    }

    public void saveColumn(DictionaryColumn dictionaryColumn){
            dictionaryColumnDao.saveColumn(dictionaryColumn);
    }

    public void updateColumn(DictionaryColumn dictionaryColumn){
            dictionaryColumnDao.updateColumn(dictionaryColumn);
    }

    public void deleteColumn(String id){
            dictionaryColumnDao.deleteColumn(id);
    }


}
