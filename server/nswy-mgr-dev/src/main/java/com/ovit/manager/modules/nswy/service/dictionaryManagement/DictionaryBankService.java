package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryBankDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryBankService extends CrudService<DictionaryBankDao, DictionaryBank> {

    @Autowired
    private DictionaryBankDao dictionaryBankDao;

//    public Page<DictionaryBank> findList(DictionaryBank dictionaryBank, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryBank);
//    }

    public List<DictionaryBank> findList(DictionaryBank dictionaryBank){
        return dictionaryBankDao.findList(dictionaryBank);
    }

    public List<DictionaryBank> findBankList(DictionaryBank dictionaryBank){

        List<DictionaryBank> dictionaryBanks = dictionaryBankDao.findList(dictionaryBank);

        return dictionaryBanks ;
    }

    public void saveBank(DictionaryBank dictionaryBank){
            dictionaryBankDao.saveBank(dictionaryBank);
    }

    public void updateBank(DictionaryBank dictionaryBank){
            dictionaryBankDao.updateBank(dictionaryBank);
    }

    public void deleteBank(String id){
            dictionaryBankDao.deleteBank(id);
    }


}
