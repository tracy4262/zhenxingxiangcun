package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ProxyReversionMapper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.service.ProxyReversionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProxyReversionServiceImpl implements ProxyReversionService {

    @Autowired
    private ProxyReversionMapper proxyReversionMapper;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private PerfectInformationService perfectInformationService;

    private Logger logger = LogManager.getLogger(ProxyReversionServiceImpl.class);

    /**
     * 查询用户代理列表
     * @param params
     */
    @Override
    public Map<String, Object> proxyList(Map<String, Object> params) {

        params.put("status", 1);
        List<Map<String,Object>> proxyList = proxyReversionMapper.queryAccount(params);
        List<String> accountList = new ArrayList<>();
        String account = MapUtils.getString(params, "proxyAccount");
        accountList.add(account);
        for (Map<String,Object> map : proxyList) {
            accountList.add(MapUtils.getString(map, "account"));
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("account", account);
        resultMap.put("key", account);
        List<Map<String,Object>> proxy = new ArrayList<>();
        for (String s : accountList) {
            LoginUser loginUser = loginUserService.findByLoginName(s);
            Map<String,Object> map = new HashMap<>();
            map.put("account", s);
            map.put("session", loginUser);
            proxy.add(map);
        }
        resultMap.put("proxy", proxy);
        return resultMap;
    }

    /**
     * 用户注册一个账号
     * @param params
     */
    @Override
    public void insertProxy(Map<String, Object> params) {
        proxyReversionMapper.insertProxy(params);
    }

    /**
     * 查询账号
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> queryAccount(Map<String, Object> params) {

        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> accountList = proxyReversionMapper.queryAccount(params);
        //手动清理 ThreadLocal 存储的分页参数
        PageHelper.clearPage();
        //被操作多次的账号
        List<String> multiAccount = proxyReversionMapper.findMultiAccount(params);
        for (Map<String,Object> map : accountList) {
            //查正在使用的模板信息
            String account = MapUtils.getString(map, "account");
            //确认被操作多次的账号的最终的状态
            if (multiAccount.contains(account)) {
                List<Map<String, Object>> proxyInfo = proxyReversionMapper.findProxyInfo(account);
                for (Map<String,Object> proxy : proxyInfo) {
                    int status = MapUtils.getInteger(proxy, "status");
                    int type = MapUtils.getInteger(proxy, "type");
                    if (type == 1 && status == 1) {
                        map.put("status", status);
                        map.put("type", type);
                        break;
                    }
                    if (type == 0 && status == 2) {
                        map.put("status", 1);
                        map.put("type", 1);
                        break;
                    }
                    if (type == 1 && status == 2) {
                        map.put("status", status);
                        map.put("type", type);
                        continue;
                    }
                }
            }
            Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
            if (MapUtils.isEmpty(useTemplate)) {
                //map.put("name", "");
                map.put("avatar", "");
            } else {
                /*String memberName = "";
                List<Map<String,Object>> memberList = proxyReversionMapper.findMemberName(useTemplate);
                if (CollectionUtils.isNotEmpty(memberList)) {
                    memberName = MapUtils.getString(memberList.get(0), "memberName");
                }
                map.put("name", memberName);*/
                //查询用户最大的年度文件id
                if (MapUtils.isNotEmpty(useTemplate)) {
                    Map<String,Object> yearInfo = loginUserService.findYearInfo(account);
                    useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
                    Map<String,Object> privacyInfo = perfectInformationService.findPrivacyInfo(useTemplate);
                    String avatar = MapUtils.getString(privacyInfo, "image");
                    if (StringUtils.isBlank(avatar)) {
                        map.put("avatar", "");
                    } else {
                        map.put("avatar", avatar);
                    }
                } else {
                    map.put("avatar", "");
                }
            }
        }

        return new PageInfo(accountList);
    }

    /**
     * 代理或取消代理
     * @param params
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void proxyOrCancle(Map<String, Object> params) {

        int type = MapUtils.getInteger(params, "type");
        params.remove("type");
        /*if (type == 0) {
            params.remove("type");
        }*/
        //根据要代理的账号查询是否存在数据
        Map<String,Object> proxyMap = proxyReversionMapper.queryByAccount(params);
        //保存数据记录
        Map<String,Object> proxyRecord = proxyReversionMapper.queryProxyRecord(params);
        params.put("status", 2);
        params.put("type", type);
        if (MapUtils.isEmpty(proxyMap)) {
            //新增
            proxyReversionMapper.insertProxy(params);
        } else {
            //更新
            params.put("id", MapUtils.getString(proxyMap, "id"));
            proxyReversionMapper.updateProxy(params);
        }
        params.remove("id");
        if (MapUtils.isNotEmpty(proxyRecord)) {
            //有记录，获取id
            params.put("id", MapUtils.getString(proxyRecord, "id"));
        }
        proxyReversionMapper.saveProxyRecord(params);

    }

    /**
     * 查询代理模板或取消代理模板
     * @param params
     */
    @Override
    public Map<String, Object> proxyTemplate(Map<String, Object> params) {
        return proxyReversionMapper.proxyTemplate(params);
    }

    /**
     * 暂不代理
     * @param params
     */
    @Override
    public void noProxy(Map<String, Object> params) {
        params.put("status", 4);
        proxyReversionMapper.updateProxy(params);
    }

    /**
     * 查询待审数据
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> waitProxy(Map<String, Object> params) {
        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> waitProxy = proxyReversionMapper.waitProxy(params);
        return new PageInfo(waitProxy);
    }

    @Override
    public List<Map<String, Object>> findMemberName(Map<String, Object> params) {
        return proxyReversionMapper.findMemberName(params);
    }

    @Override
    public void toProxy(Map<String, Object> params) {
        Map<String, Object> completing = proxyReversionMapper.isCompleting(params);
        if (MapUtils.isEmpty(completing)) {
            params.put("type", 1);
            proxyReversionMapper.insertProxy(params);
        } else {
            //做更新操作，将type更新为1，status更新为0
            completing.put("status", 0);
            completing.put("type", 1);
            proxyReversionMapper.updateProxy(completing);
        }
    }

}