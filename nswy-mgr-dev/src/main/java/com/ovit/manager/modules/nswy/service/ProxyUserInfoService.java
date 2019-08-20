package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.modules.nswy.dao.ProxyCorpInfoDao;
import com.ovit.manager.modules.nswy.dao.ProxyUserInfoDao;
import com.ovit.manager.modules.nswy.entity.ProxyCropInfo;
import com.ovit.manager.modules.nswy.entity.ProxyUserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Service
@Transactional(readOnly = true)
public class ProxyUserInfoService extends CrudService<ProxyUserInfoDao, ProxyUserInfo> {

    private static Logger logger = LoggerFactory.getLogger(ProxyUserInfoService.class);
    @Autowired
    private ProxyUserInfoDao proxyUserInfoDao;

    public Page<ProxyUserInfo> findList(ProxyUserInfo proxyUserInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<ProxyUserInfo> page = this.findPage(new Page<ProxyUserInfo>(request, response), proxyUserInfo);
        logger.info("代理审核列表查询：{}"+page.getList());
        return page;
    }

    public ProxyUserInfo getProxyUserInfoById(Integer proxyUserInfoId){
            ProxyUserInfo proxyUserInfo = null;
            if(Integers.isNotEmpty(proxyUserInfoId)){
                proxyUserInfo=proxyUserInfoDao.selectByPrimaryKey(proxyUserInfoId);
            }

        logger.info("通过审核Id查询代理数据{}"+proxyUserInfo);
        return proxyUserInfo;
    }

    @Transactional()
    public int  updateProxyUserInfo(ProxyUserInfo proxyUserInfo){
        if(StringUtil.isNotBlank(proxyUserInfo.getAuditOpinion())){
            proxyUserInfo.setAuditOpinion(StringUtils.Html(proxyUserInfo.getAuditOpinion()));
        }
        Integer result=0;
        if(Integers.isNotEmpty(proxyUserInfo.getProxyUserInfoId())){
            String status = String.valueOf(proxyUserInfo.getStatus());
            if(status.equals("1")){
                if(proxyUserInfo.getAuditOpinion().equals("")){
                    proxyUserInfo.setAuditOpinion("同意");
                }
                result= proxyUserInfoDao.updateByPrimaryKeySelective(proxyUserInfo);
                proxyUserInfoDao.updateAuditUser(proxyUserInfo);
            }else{
                if(proxyUserInfo.getAuditOpinion().equals("")){
                    proxyUserInfo.setAuditOpinion("不同意");
                }
                result= proxyUserInfoDao.updateByPrimaryKeySelective(proxyUserInfo);
                proxyUserInfoDao.updateAuditUser(proxyUserInfo);
            }
        }
        logger.info("通过审核Id修改代理数据{}"+result);
        return result;
    }

    @Transactional()
    public int  deleteProxyUserInfo(Integer proxyUserInfoId){
        Integer result=null;
        if(Integers.isNotEmpty(proxyUserInfoId)){
            proxyUserInfoDao.deleteAuditByPrimaryKey(proxyUserInfoId);
            result= proxyUserInfoDao.deleteByPrimaryKey(proxyUserInfoId);
        }
        logger.info("通过审核Id删除代理数据{}"+result);
        return result;
    }


}
