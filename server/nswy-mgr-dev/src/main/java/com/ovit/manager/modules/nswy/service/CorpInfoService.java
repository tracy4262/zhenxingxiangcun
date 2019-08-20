package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.CorpInfoDao;
import com.ovit.manager.modules.nswy.dao.NswyCorpInfoDao;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**企业service
 * Created by ${huipei.x} on 2017-8-15.
 */
@Service
@Transactional(readOnly = true)
public class CorpInfoService extends CrudService<CorpInfoDao, NswyCorpInfo> {
    @Autowired
    private CorpInfoDao corpInfoDao;

    public Page<NswyCorpInfo> findNswyCorpInfolist(NswyCorpInfo nswyCorpInfo, HttpServletRequest request, HttpServletResponse response) {
    	Page<NswyCorpInfo> page = this.findPage(new Page<NswyCorpInfo>(request, response), nswyCorpInfo);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyCorpInfo CorpInfo:page.getList()) {
                if (CorpInfo.getCreateTime() != null) {
                    String time = DateUtils.formatDate(CorpInfo.getCreateTime());
                    CorpInfo.setTime(time);
                }
            }
        }
        return page;
    }

    @Transactional()
    public void deleteCorpInfo(Integer corpInfoId){
        corpInfoDao.deleteCorpInfo(corpInfoId);
    }
    @Transactional()
    public void corpInfoUpdateNavDisplay(Map<String, Object> map) {
        corpInfoDao.corpInfoUpdateNavDisplay(map);
    }
    @Transactional()
    public void corpInfoSetOrderNum(Map<String, Object> map) {
        corpInfoDao.corpInfoSetOrderNum(map);
    }
    @Transactional()
    public void corpInfoUpdateHome(Map<String, Object> map) {
        corpInfoDao.corpInfoUpdateHome(map);
    }


    

}
