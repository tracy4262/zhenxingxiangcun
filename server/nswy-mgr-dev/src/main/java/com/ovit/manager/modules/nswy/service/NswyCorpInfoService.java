package com.ovit.manager.modules.nswy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.NswyCorpInfoDao;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**企业service
 * Created by ${huipei.x} on 2017-8-15.
 */
@Service
@Transactional(readOnly = true)
public class NswyCorpInfoService extends CrudService<NswyCorpInfoDao, NswyCorpInfo> {
    @Autowired
    private NswyCorpInfoDao nswyCorpInfoDao;

    public Page<NswyCorpInfo> findNswyCorpInfolist(NswyCorpInfo nswyCorpInfo, HttpServletRequest request, HttpServletResponse response) {
      //  nswyCorpInfo.setApproveStatus(1);/*审核通过的*/
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

    /*审核资质文件时查询*/
    public Page<NswyCorpInfo> findNswyEnterpriselist(NswyCorpInfo nswyCorpInfo, HttpServletRequest request, HttpServletResponse response) {
    	//nswyCorpInfo.setApproveStatus(0);/*未审核*/
    	Page<NswyCorpInfo> page = this.findPage(new Page<NswyCorpInfo>(request, response), nswyCorpInfo);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyCorpInfo CorpInfo:page.getList()) {
                if (CorpInfo.getCreateTime() != null) {
                    String time = DateUtils.formatDate(CorpInfo.getCreateTime());
                    CorpInfo.setTime(time);
                }
              /*  if(CorpInfo.getApproveStatus() == 0)
                {
                	CorpInfo.setStatusDesc("待审核");
                }
                else if(CorpInfo.getApproveStatus() == 1)
                {
                	CorpInfo.setStatusDesc("审核通过");
                }
                else if(CorpInfo.getApproveStatus() == 2)
                {
                	CorpInfo.setStatusDesc("未通过");
                }
                CorpInfo.setExamStatus(String.valueOf(CorpInfo.getApproveStatus()));*/
            }
        }
        return page;
    }
    
    public NswyCorpInfo getCorpInfo(Integer corpInfoId){
        return  nswyCorpInfoDao.getCorpInfo(corpInfoId);
    }

    @Transactional()
    public void deleteCorpInfo(Integer corpInfoId){
        nswyCorpInfoDao.delete(corpInfoId);
    }

    @Transactional()
    public void updateCorpInfo(NswyCorpInfo nswyCorpInfo){
        nswyCorpInfoDao.updateByPrimaryKeySelective(nswyCorpInfo);
    }
    
    /*资质文件审核时更新*/
    @Transactional()
    public void updateEnterprise(NswyCorpInfo nswyCorpInfo){
        if(nswyCorpInfo.getCorpInfoId()!=null){
            nswyCorpInfoDao.updateByPrimaryKeySelective(nswyCorpInfo);
            String login_account = nswyCorpInfo.getLoginAccount();
            nswyCorpInfoDao.updateUserType(login_account);
        }
    }
    @Transactional()
    public void updateHomeDisplay(Map<String,Object> map) {
        nswyCorpInfoDao.updateHomeDisplay(map);
    }
}
