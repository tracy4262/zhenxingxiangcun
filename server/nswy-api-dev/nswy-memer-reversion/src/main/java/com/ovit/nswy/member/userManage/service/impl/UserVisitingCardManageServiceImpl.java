package com.ovit.nswy.member.userManage.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.userManage.mapper.UserVisitingCardManageMapper;
import com.ovit.nswy.member.userManage.service.UserRealCertificationService;
import com.ovit.nswy.member.userManage.service.UserVisitingCardManageService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserVisitingCardManageServiceImpl implements UserVisitingCardManageService {

    @Autowired
    private UserVisitingCardManageMapper userVisitingCardManageMapper;


    @Override
    public Map<String,Object> findVisitingCard(Map<String,Object> params){
        Map<String,Object> registerInfo = new HashMap<>();

        Map<String,Object> registerMap = userVisitingCardManageMapper.findRegisterInfoByaccount(params);
        if(MapUtils.isEmpty(registerMap)){
            Map<String,Object> loginMap = userVisitingCardManageMapper.findByLoginUser(params);

            loginMap.put("templateId",MapUtils.getString(params,"templateId"));

            userVisitingCardManageMapper.saveRegisterInfo(loginMap);

            registerMap = userVisitingCardManageMapper.findRegisterInfoByaccount(params);

        }
        registerInfo.put("registerInfo",registerMap); //注册信息

        //会员资质信息
        List<Map<String,Object>> memberAptitude  =  userVisitingCardManageMapper.findMemberAptitude(params);
        if(memberAptitude.size()>0){
            for(Map<String,Object> map :memberAptitude){
                String images = MapUtils.getString(map,"aptitudeImage");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("aptitudeImage",image);
                }else{
                    map.put("aptitudeImage",new String[]{});
                }

            }
        }else{
            memberAptitude = new ArrayList<>();
        }
        registerInfo.put("memberAptitude",memberAptitude);

        //联系人
        List<Map<String,Object>> memberContact  =  userVisitingCardManageMapper.findMemberContact(params);
        if(memberContact.size()>0){
            for(Map<String,Object> map :memberContact){
                String images = MapUtils.getString(map,"image");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("image",image);
                }else{
                    map.put("image",new String[]{});
                }
            }
        }else{
            memberContact = new ArrayList<>();
        }
        registerInfo.put("memberContact",memberContact);

        //法人/个人身份
        List<Map<String,Object>> memberIdentity  =  userVisitingCardManageMapper.findMemberIdentity(params);
        if(memberIdentity.size()>0){
            for(Map<String,Object> map :memberIdentity){
                String images = MapUtils.getString(map,"cardImage");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("cardImage",image);
                }else{
                    map.put("cardImage",new String[]{});
                }

            }
        }else{
            memberIdentity = new ArrayList<>();
        }

        registerInfo.put("memberIdentity",memberIdentity);

        //法人/个人身份(管理员)
        List<Map<String,Object>> memberAdmin  =  userVisitingCardManageMapper.findMemberAdministrator(params);
        if(memberAdmin.size()>0){

            for(Map<String,Object> map :memberAdmin){
                String images = MapUtils.getString(map,"cardImage");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("cardImage",image);
                }else{
                    map.put("cardImage",new String[]{});
                }

                String empower_images = MapUtils.getString(map,"empowerImage");
                if(StringUtil.isNotBlank(empower_images)){
                    String[] empower_image = empower_images.split(";");
                    map.put("empowerImage",empower_image);
                }else{
                    map.put("empowerImage",new String[]{});
                }
            }
        }else{
            memberIdentity = new ArrayList<>();
        }

        registerInfo.put("memberAdmin",memberAdmin);

        return registerInfo;

    }

    @Override
    public void updateVisitingCard(Map<String,Object> params){
        Map<String,Object> registerMap = (Map<String,Object>)params.get("registerInfo");
        if(MapUtils.isNotEmpty(registerMap)){
            userVisitingCardManageMapper.updateRegisterInfo(registerMap);
        }

        List<Map<String,Object>> memberAptitude = (List<Map<String,Object>>)params.get("memberAptitude");
        if(memberAptitude.size()>0){
            for(Map<String,Object> member:memberAptitude){
                userVisitingCardManageMapper.updateMemberAptitude(member);
            }
        }

        List<Map<String,Object>> memberContact = (List<Map<String,Object>>)params.get("memberContact");
        if(memberContact.size()>0){
            for(Map<String,Object> member:memberContact){
                userVisitingCardManageMapper.updateMemberContact(member);
            }
        }

        List<Map<String,Object>> memberIdentity = (List<Map<String,Object>>)params.get("memberIdentity");
        if(memberIdentity.size()>0){
            for(Map<String,Object> member:memberIdentity){
                userVisitingCardManageMapper.updateMemberIdentity(member);
            }
        }

        List<Map<String,Object>> memberAdmin = (List<Map<String,Object>>)params.get("memberAdmin");
        if(memberAdmin.size()>0){
            for(Map<String,Object> member:memberAdmin){
                userVisitingCardManageMapper.updateMemberAdministrator(member);
            }
        }

    }


    @Override
    public Map<String,Object> findPhoneVisitingCard(Map<String,Object> params){
        Map<String,Object> registerInfo = new HashMap<>();

        //会员资质信息
        List<Map<String,Object>> memberAptitude  =  userVisitingCardManageMapper.findMemberAptitude(params);
        if(memberAptitude.size()>0){
            for(Map<String,Object> map :memberAptitude){
                String images = MapUtils.getString(map,"aptitudeImage");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("aptitudeImage",image);
                }else{
                    map.put("aptitudeImage",new String[]{});
                }

            }
        }else{
            memberAptitude = new ArrayList<>();
        }
        registerInfo.put("memberAptitude",memberAptitude);

        //联系人
        List<Map<String,Object>> memberContact  =  userVisitingCardManageMapper.findMemberContact(params);
        if(memberContact.size()>0){
            for(Map<String,Object> map :memberContact){
                String images = MapUtils.getString(map,"image");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("image",image);
                }else{
                    map.put("image",new String[]{});
                }
            }
        }else{
            memberContact = new ArrayList<>();
        }
        registerInfo.put("memberContact",memberContact);

        //法人/个人身份
        List<Map<String,Object>> memberIdentity  =  userVisitingCardManageMapper.findMemberIdentity(params);
        if(memberIdentity.size()>0){
            for(Map<String,Object> map :memberIdentity){
                String images = MapUtils.getString(map,"cardImage");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("cardImage",image);
                }else{
                    map.put("cardImage",new String[]{});
                }

            }
        }else{
            memberIdentity = new ArrayList<>();
        }

        registerInfo.put("memberIdentity",memberIdentity);

        List<Map<String,Object>> memberAdmin = new ArrayList<>();

        if(memberIdentity.size()>0){
            for(Map<String,Object> map :memberIdentity){
                params.put("memberNameId",MapUtils.getString(map,"id"));
                //法人/个人身份(管理员)
                List<Map<String,Object>> memberAdmins  =  userVisitingCardManageMapper.findMemberAdministrator(params);
                if(memberAdmins.size()>0){

                    for(Map<String,Object> admin :memberAdmins){
                        String images = MapUtils.getString(admin,"cardImage");
                        if(StringUtil.isNotBlank(images)){
                            String[] image = images.split(";");
                            admin.put("cardImage",image);
                        }else{
                            admin.put("cardImage",new String[]{});
                        }

                        String empower_images = MapUtils.getString(admin,"empowerImage");
                        if(StringUtil.isNotBlank(empower_images)){
                            String[] empower_image = empower_images.split(";");
                            admin.put("empowerImage",empower_image);
                        }else{
                            admin.put("empowerImage",new String[]{});
                        }
                        memberAdmin.add(admin);
                    }
                }
            }

        }
        registerInfo.put("memberAdmin",memberAdmin);





        return registerInfo;

    }



}
