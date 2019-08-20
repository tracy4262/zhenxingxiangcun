package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ProxyCorpInfoDao;
import com.ovit.manager.modules.nswy.entity.ProxyCropInfo;
import com.ovit.manager.modules.nswy.entity.ProxyCropInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Service
@Transactional(readOnly = true)
public class ProxyCorpInfoService extends CrudService<ProxyCorpInfoDao, ProxyCropInfo> {

    private static Logger logger = LoggerFactory.getLogger(ProxyCorpInfoService.class);
    @Autowired
    private ProxyCorpInfoDao proxyCorpInfoDao;

    public Page<ProxyCropInfo> findList(ProxyCropInfo ProxyCropInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<ProxyCropInfo> page = this.findPage(new Page<ProxyCropInfo>(request, response), ProxyCropInfo);
        logger.info("代理审核列表查询：{}"+page.getList());
        return page;
    }

   public ProxyCropInfo getProxyCropInfoById(Integer proxyCropInfoId){
       ProxyCropInfo proxyCropInfo = new ProxyCropInfo();
       proxyCropInfo.setProxyCorpInfoId(proxyCropInfoId);
       if(Integers.isNotEmpty(proxyCropInfoId)){
           proxyCropInfo=proxyCorpInfoDao.selectByPrimaryKey(proxyCropInfo);

       }
       logger.info("通过审核Id查询代理数据{}"+proxyCropInfo);
      return proxyCropInfo;
   }
    @Transactional()
     public int  updateProxyCropInfo(ProxyCropInfo proxyCropInfo){
        Integer result=0;
        if(Integers.isNotEmpty(proxyCropInfo.getProxyCorpInfoId())){
            String status = String.valueOf(proxyCropInfo.getApproveStatus());
            if(status.equals("1")){
                String url = Global.getConfig("api.url")+"/member/proxy/smsSendUserInfo";
                JSONObject jsonBody=new JSONObject();
                jsonBody.put("id",proxyCropInfo.getProxyCorpInfoId());
                jsonBody.put("TABLE_NAME","proxy_corp_info");
                jsonBody.put("INFO_NAME","corp_info");
                jsonBody.put("name","corp_name");
                try {
                    String rows=   HttpUtil.doPost(url, jsonBody.toString());
                    JSONObject object = JSONObject.parseObject(rows);
                    if(object.get("code").toString().equals("500")){
                        logger.info("发送短信失败{}"+ rows);
                        System.out.println("发送短信失败{}"+rows);
                        result=0;
                    }else{
                        if(proxyCropInfo.getRemark().equals("")){
                            proxyCropInfo.setRemark("同意");
                        }
                        result= proxyCorpInfoDao.updateByPrimaryKeySelective(proxyCropInfo);
                        proxyCorpInfoDao.updateLoginUser(proxyCropInfo);
                    }

                    System.out.println("发送短信状态{}"+ rows);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(proxyCropInfo.getRemark().equals("")){
                    proxyCropInfo.setRemark("不同意");
                }
                result= proxyCorpInfoDao.updateByPrimaryKeySelective(proxyCropInfo);
                proxyCorpInfoDao.updateCorpInfo(proxyCropInfo);
            }

        }
        logger.info("通过审核Id修改代理数据{}"+result);
        return result;
    }

    @Transactional()
    public int  deleteProxyCropInfo(Integer proxyCropInfoId){
        Integer result=null;
        ProxyCropInfo proxyCropInfo = new ProxyCropInfo();
        proxyCropInfo.setProxyCorpInfoId(proxyCropInfoId);
        if(Integers.isNotEmpty(proxyCropInfoId)){
            result= proxyCorpInfoDao.deleteByPrimaryKey(proxyCropInfo);
        }
        logger.info("通过审核Id删除代理数据{}"+result);
        return result;
    }



}
