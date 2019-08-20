package com.ovit.manager.modules.nswy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.NswyGovInfoDao;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 机构 service
 * Created by ${huipei.x} on 2017-8-16.
 */
@Service
@Transactional(readOnly = true)
public class NswyGovInfoService extends CrudService<NswyGovInfoDao, NswyGovInfo> {

    @Autowired
    private NswyGovInfoDao nswyGovInfoDao;

    public Page<NswyGovInfo> findGovInfolist(NswyGovInfo nswyGovInfo, HttpServletRequest request, HttpServletResponse response) {

    	Page<NswyGovInfo> page = this.findPage(new Page<>(request, response), nswyGovInfo);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyGovInfo govInfo:page.getList()){
                if(govInfo.getCreateTime() !=null) {
                    String time = DateUtils.formatDate(govInfo.getCreateTime());
                    govInfo.setTime(time);
                }
            }
        }
        return page;
    }

    public Page<NswyGovInfo> findCountrylist(NswyGovInfo nswyGovInfo, HttpServletRequest request, HttpServletResponse response) {
        nswyGovInfo.setUserType(5);
        Page<NswyGovInfo> page = this.findPage(new Page<>(request, response), nswyGovInfo);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyGovInfo govInfo:page.getList()){
                if(govInfo.getCreateTime() !=null) {
                    String time = DateUtils.formatDate(govInfo.getCreateTime());
                    govInfo.setTime(time);
                }
            }
        }
        return page;
    }
    /*审核资质文件时查询*/
    public Page<NswyGovInfo> findGovFilelist(NswyGovInfo nswyGovInfo, HttpServletRequest request, HttpServletResponse response) {
    	Page<NswyGovInfo> page = this.findPage(new Page<>(request, response), nswyGovInfo);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyGovInfo GovInfo:page.getList()) {
                if (GovInfo.getCreateTime() != null) {
                    String time = DateUtils.formatDate(GovInfo.getCreateTime());
                    GovInfo.setTime(time);
                }
            }
        }
        return page;
    }
    
    public NswyGovInfo getGovInfoById(Integer govInfoId){
        return  nswyGovInfoDao.getByPrimaryKey(govInfoId);
    }

    @Transactional()
    public void deleteGovInfo(Integer govInfoId){
        nswyGovInfoDao.deleteByPrimaryKey(govInfoId);
    }

    @Transactional()
    public void updateGovInfo(NswyGovInfo nswyGovInfo){
        nswyGovInfoDao.updateByPrimaryKeySelective(nswyGovInfo);
    }

    /*资质文件审核时更新*/
    @Transactional()
    public void updateGovFile(NswyGovInfo nswyGovInfo){
        if(nswyGovInfo.getGovInfoId()!=null){
            nswyGovInfoDao.updateByPrimaryKeySelective(nswyGovInfo);
        }
    }

    //列表页
    public Page<NswyGovInfo> findList(NswyGovInfo nswyGovInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyGovInfo> page = this.findPage(new Page<>(request,response),nswyGovInfo);
        return page;
    }
}
