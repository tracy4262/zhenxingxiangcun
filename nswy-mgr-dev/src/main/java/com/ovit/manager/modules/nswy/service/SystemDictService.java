package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.SystemDictDao;
import com.ovit.manager.modules.nswy.entity.SystemDictInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

/**
 * 关键字
 *
 * @author haoWen
 * @create 2018-02-11 14:04
 **/

@Service
public class SystemDictService extends CrudService<SystemDictDao,SystemDictInfo> {

    @Autowired
    private SystemDictDao systemDictDao;
    public List<String> queryByDictId(String industrialId) {
        String [] relatedIndustryIdArray = StringUtils.splitByWholeSeparator(industrialId,"");
        return systemDictDao.queryByDictId(Arrays.asList(relatedIndustryIdArray));
    }
}
