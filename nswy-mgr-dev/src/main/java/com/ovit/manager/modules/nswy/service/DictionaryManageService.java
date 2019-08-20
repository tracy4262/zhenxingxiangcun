package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.DictionaryManageDao;
import com.ovit.manager.modules.nswy.entity.DictionaryManageInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class DictionaryManageService extends CrudService<DictionaryManageDao, DictionaryManageInfo> {
    @Autowired
    private DictionaryManageDao dictionaryManageDao;

    public Page<DictionaryManageInfo> findDictList(DictionaryManageInfo dictionaryManageInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),dictionaryManageInfo);
    }

    public void saveDictionaryManage(DictionaryManageInfo dictionaryManageInfo){
        dictionaryManageDao.saveDictionaryManage(dictionaryManageInfo);
    }

    public void updateDictionaryManage(DictionaryManageInfo dictionaryManageInfo){
        dictionaryManageDao.updateDictionaryManage(dictionaryManageInfo);
    }

    public void deleteDictionaryManage(String id){
        dictionaryManageDao.deleteDictionaryManage(id);
    }
}
