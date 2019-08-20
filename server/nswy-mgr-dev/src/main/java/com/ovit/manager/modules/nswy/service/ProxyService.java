package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.ProxySettingDao;
import com.ovit.manager.modules.nswy.entity.ProxyAgency;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**运营管理service
 * Created by ${zhanlei} on 2017-8-24.
 */
@Service
@Transactional(readOnly = true)
public class ProxyService extends CrudService<ProxySettingDao, ProxyAgency> {
    @Autowired
    private ProxySettingDao proxySettingDao;

    public Page<ProxyAgency> findProxySettinglist(ProxyAgency proxySetting, HttpServletRequest request, HttpServletResponse response) {
        Page<ProxyAgency> page = this.findPage(new Page<ProxyAgency>(request, response), proxySetting);
        if(null !=page.getList()&&page.getList().size()>0){
            for(ProxyAgency proxy:page.getList()) {
                if (proxy.getCreateTime() != null) {
                    String time1 = DateUtils.formatDateTime(proxy.getCreateTime());
                    proxy.setCtime(time1);
                }
                if (proxy.getApproveTime() != null) {
                    String time2 = DateUtils.formatDateTime(proxy.getApproveTime());
                    proxy.setAtime(time2);
                }
                if(proxy.getApproveStatus() == 0)
                {
                	proxy.setStatusDesc("待审核");
                }
                else if(proxy.getApproveStatus() == 1)
                {
                	proxy.setStatusDesc("审核通过");
                }
                else if(proxy.getApproveStatus() == 2)
                {
                	proxy.setStatusDesc("未通过");
                }
                proxy.setExamStatus(String.valueOf(proxy.getApproveStatus()));
            }
        }
        return page;
    }

	/**保存代理运营信息*/
	@Transactional(readOnly = false)
	public void save(Map<String,Integer> proxySetting) {
        //保存
        proxySettingDao.updateByPrimaryKeySelective(proxySetting);
        String url = Global.getConfig("api.url")+"/member/proxy/smsSendUserInfo";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{id:").append("\"")
                .append(proxySetting.get("proxyId")).append("\"");
        jsonBody.append("}");
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
        }catch (Exception e){

        }
	}
    
    public ProxyAgency getProxySetting(Integer proxyId){
        return  proxySettingDao.selectByPrimaryKey(proxyId);
    }

    @Transactional(readOnly = false)
    public void deleteProxySetting(Integer proxyId){
    	proxySettingDao.deleteByPrimaryKey(proxyId);
    }

    public Page<Object> findProxyAgencyList(ProxyAgency proxyAgency, HttpServletRequest request, HttpServletResponse response) {
        Page<Object> page = new Page<Object>(request, response);
        String url = Global.getConfig("api.url")+"/member/proxy/queryAgency";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{pageNum:").append("\"")
                .append(page.getPageNo()).append("\"").append(",pageSize:").append("\"")
                .append(page.getPageSize()).append("\"");
        if(!StringUtils.isEmpty(proxyAgency.getCorpName())){
            jsonBody.append(",name:").append("\"")
                    .append(proxyAgency.getCorpName()).append("\"");
        }
        if(!StringUtils.isEmpty(proxyAgency.getLegalMan())){
            jsonBody.append(",legal_name:").append("\"")
                    .append(proxyAgency.getLegalMan()).append("\"");
        }
        if(!StringUtils.isEmpty(proxyAgency.getAccount())){
            jsonBody.append(",account:").append("\"")
                    .append(proxyAgency.getAccount()).append("\"");
        }
        if(!StringUtils.isEmpty(proxyAgency.getMyProxy())){
            jsonBody.append(",agencyer:").append("\"")
                    .append(proxyAgency.getMyProxy()).append("\"");
        }
        jsonBody.append("}");
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
            JSONObject object = JSONObject.parseObject(result);
            Map<String,Object> map = (Map<String, Object>) object.get("data");
            List<Object> list = (List<Object>) map.get("list");
            page.setList(list);
        }catch (Exception e){

        }
        return page;
    }

    public Object findProxyAgency(Integer proxyId) {
        String url = Global.getConfig("api.url")+"/member/proxy/queryAgency";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{pageNum:").append("\"")
                .append(1).append("\"").append(",pageSize:").append("\"")
                .append(10).append("\"");
        jsonBody.append(",id:").append("\"")
                .append(proxyId).append("\"").append("}");
        Object object = null;
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
            JSONObject json = JSONObject.parseObject(result);
            Map<String,Object> map = (Map<String, Object>) json.get("data");
            List<Object> list = (List<Object>) map.get("list");
            if(null != list && !list.isEmpty() ){
                object = list.get(0);
            }
        }catch (Exception e){

        }
        return object;
    }
}
