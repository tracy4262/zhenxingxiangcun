package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.AgencyAuditDao;
import com.ovit.manager.modules.nswy.entity.AgencyAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Service
@Transactional(readOnly = true)
public class AgencyAuditService extends CrudService<AgencyAuditDao, AgencyAudit> {

    private static Logger logger = LoggerFactory.getLogger(AgencyAuditService.class);
    @Autowired
    private AgencyAuditDao agencyAuditDao;

    public Page<AgencyAudit> findAgencyList(AgencyAudit agencyAudit, HttpServletRequest request, HttpServletResponse response) {
        Page<AgencyAudit> page = this.findPage(new Page<AgencyAudit>(request, response), agencyAudit);
        logger.info("代理审核列表查询：{}"+page.getList());
        return page;
    }

   public  AgencyAudit getAgencyAuditById(Integer agencyAuditId){
       AgencyAudit agencyAudit=null;
       if(Integers.isNotEmpty(agencyAuditId)){
           agencyAudit=agencyAuditDao.selectByPrimaryKey(agencyAuditId);
       }
       logger.info("通过审核Id查询代理数据{}"+agencyAudit);
      return agencyAudit;
   }
    @Transactional()
     public int  updateAgencyAudit(AgencyAudit agencyAudit){
        Integer result=0;
        if(Integers.isNotEmpty(agencyAudit.getAgencyAuditId())){
            if(agencyAudit.getAuditStatus().equals("1")){
                String url = Global.getConfig("api.url")+"/member/proxy/smsSendUserInfo";
                JSONObject jsonBody=new JSONObject();
                jsonBody.put("id",agencyAudit.getAgencyAuditId());
                try {
                   String rows=   HttpUtil.doPost(url, jsonBody.toString());
                    JSONObject object = JSONObject.parseObject(rows);
                    if(object.get("code").toString().equals("500")){
                        logger.info("发送短信失败{}"+ rows);
                        System.out.println("发送短信失败{}"+rows);
                        result=0;
                    }else{
                        result= agencyAuditDao.updateByPrimaryKeySelective(agencyAudit);
                    }
                    System.out.println("发送短信状态{}"+ rows);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                result= agencyAuditDao.updateByPrimaryKeySelective(agencyAudit);
            }

        }
        logger.info("通过审核Id修改代理数据{}"+result);
        return result;
    }

    @Transactional()
    public int  deleteAgencyAudit(Integer agencyAuditId){
        Integer result=null;
        if(Integers.isNotEmpty(agencyAuditId)){
            result= agencyAuditDao.deleteByPrimaryKey(agencyAuditId);
        }
        logger.info("通过审核Id删除代理数据{}"+result);
        return result;
    }



}
