package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ProxyCorpInfoMapper;
import com.ovit.nswy.member.mapper.ProxyGovInfoMapper;
import com.ovit.nswy.member.mapper.ProxyMapper;
import com.ovit.nswy.member.model.ProxyCorpInfo;
import com.ovit.nswy.member.service.ProxyService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Autowired
    private ProxyMapper proxyMapper;

    @Autowired
    private ProxyCorpInfoMapper proxyCorpInfoMapper;

    @Autowired
    private ProxyGovInfoMapper proxyGovInfoMapper;

    private Logger logger = LogManager.getLogger(ProxyServiceImpl.class);

    @Override
    public PageInfo<List<Map<String,Object>>> queryList(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String,Object>> list = proxyMapper.queryList(params);
        return new PageInfo(list);
    }

    @Override
    public void delProxy(Map<String, Object> params) {
        try {
            //--flag 0 企业 1机关
            String flag  = MapUtils.getString(params,"flag");
            String TABLE_NAME;
            String INFO_NAME;
            if ("0".equals(flag)) {
                TABLE_NAME = "proxy_corp_info";
                INFO_NAME = "corp_info";
            } else {
                TABLE_NAME = "proxy_gov_info";
                INFO_NAME = "gov_info";
            }
            params.put("TABLE_NAME",TABLE_NAME);
            params.put("INFO_NAME",INFO_NAME);
            proxyMapper.updateInfos(params);
            Map<String,Object> map  = proxyMapper.findInfoAccount(params);
            String account  = MapUtils.getString(map,"login_account");
            params.put("login_account",account);
            params.put("login_passwd",RandomStringUtils.randomAlphanumeric(6).toLowerCase());
            proxyMapper.updateLoginPassWd(params);
            proxyMapper.delProxy(params);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delProxyInfo(Map<String, Object> params) {
        try {
            //--flag 0 企业 1机关
            String flag  = MapUtils.getString(params,"flag");
            String TABLE_NAME;
            if ("0".equals(flag)) {
                TABLE_NAME = "corp_info";
            } else {
                TABLE_NAME = "gov_info";
            }
            params.put("TABLE_NAME",TABLE_NAME);
            proxyMapper.delLoginUser(params);
            proxyMapper.delProxy(params);
            //取消代理
            this.delProxy(params);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> queryStatus(Map<String, Object> params) {
        PageHelper.offsetPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> list = null;
        try {
            list = proxyMapper.queryStatus(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> queryStatusDetail(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        try {
            // flag 0查询企业 1查询机关
            String flag = MapUtils.getString(params,"flag");
            if("0".equals(flag)){
                map = proxyMapper.queryStatusCorpDetail(params);
            }else{
                map = proxyMapper.queryStatusGovDetail(params);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }


    @Override
    public PageInfo<Map<String,Object>> queryInfo(Map<String, Object> params){
        PageHelper.offsetPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> list = null;
        try {
            list = proxyMapper.queryInfo(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> queryInfoDetail(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        try {
            // flag 0查询企业 1查询机关
            String flag = MapUtils.getString(params,"flag");
            if("0".equals(flag)){
                map = proxyMapper.queryInfoCorpDetail(params);
            }else{
                map = proxyMapper.queryInfoGovDetail(params);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    @Override
    public Map<String, Object> queryStatusById(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = proxyMapper.queryStatusById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    @Override
    public void delProxyById(Map<String, Object> params) {
        List<Integer> ids = JSONArray.parseArray(params.get("ids").toString(), Integer.class);
        try {
            for (Integer id : ids) {
                proxyMapper.delProxyById(id);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addProxy(Map<String, Object> params) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = String.valueOf(LocalDateTime.now().format(formatter));
            //flag 为0 企业 flag 为1机关 flag 2乡村
            String flag = MapUtils.getString(params,"flag");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",MapUtils.getString(params,"info_id"));
            if("0".equals(flag)){
                String jsonString = JSON.toJSONString(params);
                ProxyCorpInfo corpInfo = JSON.parseObject(jsonString,ProxyCorpInfo.class);
                //身份证照片正反面
                corpInfo.setIdentificationCardUrl(JsonToStringUtils.initJson(params,"identification_card_url"));
                //营业期限
                corpInfo.setBusnissTerm(JsonToStringUtils.initJson(params,"busniss_term"));
                //企业工商营业执照
                corpInfo.setBusinessLicenseUrl(JsonToStringUtils.initJson(params,"business_license_url"));
                proxyCorpInfoMapper.insert(corpInfo);

                map.put("TABLE_NAME","corp_info");

            }else if("1".equals(flag)){
                params.put("country_type","3");
                params.put("qualification_certificate_picture_list",JsonToStringUtils.initJson(params,"qualification_certificate_picture_list"));
                params.put("unit_person_picture_list",JsonToStringUtils.initJson(params,"unit_person_picture_list"));
                proxyGovInfoMapper.insert(params);
                map.put("TABLE_NAME","gov_info");
            }else if("2".equals(flag)){
                params.put("country_type","5");
                params.put("qualification_certificate_picture_list","");
                params.put("unit_person_picture_list",JsonToStringUtils.initJson(params,"unit_person_picture_list"));
                proxyGovInfoMapper.insert(params);
                map.put("TABLE_NAME","gov_info");
            }
            proxyMapper.updateInfo(map);
    }

    @Override
    public Map<String, Object> queryPassWord(Map<String, Object> params) {
        try {
            Map<String, Object> map = proxyMapper.queryPassWord(params);
            if (map.get("passWord") != null) {

            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public Map<String, Object> queryNameFromCorpOrGov(Map<String, Object> params) {
        return proxyMapper.queryNameFromCorpOrGov(params);
    }

    @Override
    public Map<String, Object> queryInfoById(Map<String, Object> params) {
        return proxyMapper.queryInfoById(params);
    }

    @Override
    public List<Map<String, Object>> queryProxyList(Map<String, Object> params) {
        return proxyMapper.queryList(params);
    }
}