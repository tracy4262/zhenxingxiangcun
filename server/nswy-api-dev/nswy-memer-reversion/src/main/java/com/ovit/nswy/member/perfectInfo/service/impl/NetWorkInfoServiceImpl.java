package com.ovit.nswy.member.perfectInfo.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.certification.mapper.RealCertificationMapper;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.NetWorkInfoService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络信息
 */
@Service
public class NetWorkInfoServiceImpl implements NetWorkInfoService {

    @Autowired
    private NetWorkInfoMapper netWorkInfoMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private RealCertificationMapper realCertificationMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;


    @Autowired
    private TemplateConfigService templateConfigService;




    @Override
    public Map<String, Object> getNetworkInfo(Map<String,Object> params) {
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("netWorkInfo_name", "网络信息");
        } else {
            map.put("netWorkInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        Map<String,Object> networkInfo = netWorkInfoMapper.getNetworkInfoByAccount(params);

        if(MapUtils.isEmpty(networkInfo)){
            networkInfo = netWorkInfoMapper.findNetworkInfo(params);
        }


        //Map<String,Object> real = realCertificationMapper.findList(params);

        Map<String, Object> networkMap = new LinkedHashMap<>();
        if (MapUtils.isNotEmpty(networkInfo)) {
            map.put("status", "1".equals(MapUtils.getString(networkInfo,"authority")) ? true : false);
            networkMap.put("id",MapUtils.getString(networkInfo,"id"));
            networkMap.put("ID", modelMap(MapUtils.getString(networkInfo,"nswy_id_model"),
                    MapUtils.getString(networkInfo,"nswy_id")));

            networkMap.put("QQ", modelMap(MapUtils.getString(networkInfo,"qq_number_model"),
                    "QQ号码"));

            networkMap.put("weChat", modelMap(MapUtils.getString(networkInfo,"weChat_model"),
                    "微信号码"));

            networkMap.put("Email", modelMap(MapUtils.getString(networkInfo,"email_model"),
                    "邮箱"));

            networkMap.put("domainName", modelMap(MapUtils.getString(networkInfo,"apply_domain_model"),
                    MapUtils.getString(networkInfo,"apply_domain")));

            Map<String,Object> loginUser = realCertificationMapper.findByLoginUser(params);

            networkMap.put("realname",modelMap(MapUtils.getString(loginUser,"realname"),
                    "昵称"));

            Map<String,Object> newWorkMap = new HashMap<>();
            map.put("networkInformation",networkMap);

            if(MapUtils.isEmpty(textPreview)){
                Map<String, Object> historyInfo = new HashMap<>();
                historyInfo.put("id", MapUtils.getString(params,"templateId"));
                List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
                historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
                if(templatelist.size()>0){
                    historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
                }else{
                    historyInfo.put("user_id",MapUtils.getString(params,"user_id"));
                    historyInfo.put("year_id", MapUtils.getString(params,"year_id"));
                }

                historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
                textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
            }

            if(MapUtils.isNotEmpty(textPreview)){
                map.put("textPreview",textPreview);
            }else{
                Map<String,Object> text = new HashMap<>();
                text.put("text_preview","");
                text.put("sys_dict_id","");
                text.put("user_id","");
                text.put("year_id","");
                text.put("is_complete",0);
                map.put("textPreview",text);
            }

        }

        return map;
    }

    @Override
    public String insertNetworkInfo(Map<String,Object> params){
        String user_id = MapUtils.getString(params,"user_id");

        Map<String,Object> networkInformation = (Map<String, Object>) params.get("networkInformation");
        if(MapUtils.isNotEmpty(networkInformation)){
            networkInformation.put("netWorkInfo_name",MapUtils.getString(params,"netWorkInfo_name"));
            networkInformation.put("user_id",user_id);
            networkInformation.put("year_id",MapUtils.getString(params,"yearId"));
            networkInformation.put("templateId",MapUtils.getString(params,"templateId"));
            if(StringUtil.isNotBlank(MapUtils.getString(networkInformation,"password"))){

                String password = new SimpleHash("SHA-1", Constants.SESSION_SALT, MapUtils.getString(networkInformation,"password")).toString();

                //Map<String,Object> real = realCertificationMapper.findList(params);

                Map<String,Object> loginUser = realCertificationMapper.findByLoginUser(params);

                String pass_word = MapUtils.getString(loginUser,"login_passwd");

                if(password.equals(pass_word)){

                    networkInformation.put("new_password",new SimpleHash("SHA-1", Constants.SESSION_SALT, MapUtils.getString(networkInformation,"new_password")).toString());

                    //修改密码
                    loginUserMapper.updatePassWord(networkInformation);

                }else{
                    return "301";
                }

            }

            netWorkInfoMapper.insertNetworkInfo(networkInformation);

            //修改昵称
            netWorkInfoMapper.updateLoginUserName(networkInformation);

            Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

            if(MapUtils.isNotEmpty(textPreview)){
                textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
                textPreview.put("user_id",MapUtils.getString(params,"user_id"));
                textPreview.put("year_id",MapUtils.getString(params,"yearId"));
                textPreview.put("templateId",MapUtils.getString(params,"templateId"));
                physicalGeographyMapper.saveTextPreview(textPreview);
            }


        }



        return "200";

    }


    @Override
    public void updateNetworkInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("netWorkInfo_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> privacyInfo = netWorkInfoMapper.getNetworkInfoByAccount(map);

        if(MapUtils.isNotEmpty(privacyInfo)){
            netWorkInfoMapper.updateNetworkInfoName(params);
        }else{
            netWorkInfoMapper.insertNetworkInfo(params);
        }


    }



    private Map<String,Object> modelMap(String model,String name){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", model);
        maps.put("name", name);
        return maps;
    }

}
