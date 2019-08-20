package com.ovit.nswy.member.userManage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.certification.mapper.RealCertificationMapper;
import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.certification.service.RealCertificationService;
import com.ovit.nswy.member.perfectInfo.mapper.LoginUserMapper;
import com.ovit.nswy.member.userManage.service.UserRealCertificationService;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRealCertificationServiceImpl implements UserRealCertificationService {

   @Autowired
   private RealCertificationMapper realCertificationMapper;

   @Autowired
   private LoginUserMapper loginUserMapper;

    @Autowired
    private LoginUserService loginUserService;



    /**
     * 会员资质信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberAptitude(Map<String,Object> params){

        List<Map<String,Object>> list = realCertificationMapper.findMemberAptitude(params);

        // 判断是否是代理账号进行实名认证，通过params中的入参isProxy来判断
        // 如果isProxy为1则表示此账号是被代理的账号
        if ("1".equals(MapUtils.getString(params, "isProxy"))) {
            //操作代理账号在代理管理里面的数据显示
            //do nothing

        } else {
            //用户向导进入的实名认证
            if (CollectionUtils.isEmpty(list)) {
                //查看是否存在代理管理时插入的数据，通过account、is_proxy=1来查询。
                params.put("isProxy", 0);
                list = realCertificationMapper.findMemberAptitude(params);
            }
        }
        for(Map<String,Object> map :list){
            String images = MapUtils.getString(map,"aptitude_image");
            if(StringUtil.isNotBlank(images)){
                String[] image = images.split(";");
                map.put("aptitude_image",image);
            }else{
                map.put("aptitude_image",new String[]{});
            }
            String memberClass = MapUtils.getString(map,"member_class");
            if(StringUtil.isNotBlank(memberClass)){
                String[] image = memberClass.split(";");
                map.put("member_class",image);
            }else{
                map.put("member_class",new String[]{});
            }
            map.put("status","1".equals(MapUtils.getString(map,"status"))?true:false);
        }


        return list;
    }

    @Override
    public void updateMemberAptitude(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String images = MapUtils.getString(map, "aptitude_image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("aptitude_image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("aptitude_image", pictureMerge);
        }

        String memberClass = MapUtils.getString(map, "member_class");
        JSONArray member = JSONArray.parseArray(memberClass);
        if (member.size() == 0) {
            map.put("member_class", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(memberClass);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("member_class", pictureMerge);
        }
        realCertificationMapper.updateMemberAptitude(map);

        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }

    }

    @Override
    public void saveMemberAptitude(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String uuid = UuidUtil.get32UUID();
        map.put("member_aptitude_real_info_id",uuid);

        String images = MapUtils.getString(map, "aptitude_image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("aptitude_image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("aptitude_image", pictureMerge);
        }

        String memberClass = MapUtils.getString(map, "member_class");
        JSONArray member = JSONArray.parseArray(memberClass);
        if (member.size() == 0) {
            map.put("member_class", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(memberClass);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("member_class", pictureMerge);
        }
        realCertificationMapper.saveMemberAptitude(map);

        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public String deleteMemberAptitude(Map<String,Object> params){
        params.put("member_name_id",MapUtils.getString(params,"member_aptitude_real_info_id"));

        List<Map<String,Object>> list = realCertificationMapper.findMemberContact(params);
        if(list.size()>0){
            return "301";
        }else{
            realCertificationMapper.deleteMemberAptitude(params);
            return "200";
        }



    }


    /**
     * 会员联系方式
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberContact(Map<String,Object> params){

        List<Map<String,Object>> list = realCertificationMapper.findMemberContact(params);

        // 判断是否是代理账号进行实名认证，通过params中的入参isProxy来判断
        // 如果isProxy为1则表示此账号是被代理的账号
        if ("1".equals(MapUtils.getString(params, "isProxy"))) {
            //操作代理账号在代理管理里面的数据显示
            //do nothing

        } else {
            //用户向导进入的实名认证
            if (CollectionUtils.isEmpty(list)) {
                //查看是否存在代理管理时插入的数据，通过account、is_proxy=1来查询。
                params.put("isProxy", 0);
                list = realCertificationMapper.findMemberContact(params);
            }
        }

        if(list.size()>0){
            for(Map<String,Object> map :list){
                String images = MapUtils.getString(map,"image");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    map.put("image",image);
                }else{
                    map.put("image",new String[]{});
                }
                map.put("image_status","1".equals(MapUtils.getString(map,"image_status"))?true:false);
                map.put("location_status","1".equals(MapUtils.getString(map,"location_status"))?true:false);
                map.put("address_status","1".equals(MapUtils.getString(map,"address_status"))?true:false);
                map.put("status","1".equals(MapUtils.getString(map,"status"))?true:false);

            }
        }

        return list;
    }

    @Override
    public  List<Map<String,Object>> findMemberContactOfByAptitude(Map<String,Object> params){

        return  realCertificationMapper.findMemberContactOfByAptitude(params);
    }

    @Override
    public void updateMemberContact(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String images = MapUtils.getString(map, "image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("image", pictureMerge);
        }
        realCertificationMapper.updateMemberContact(map);

        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public void saveMemberContact(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String uuid = UuidUtil.get32UUID();
        map.put("member_contact_info_id",uuid);
        String images = MapUtils.getString(map, "image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("image", pictureMerge);
        }
        realCertificationMapper.saveMemberContact(map);

        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public void deleteMemberContact(Map<String,Object> params){
        realCertificationMapper.deleteMemberContact(params);
    }

    /**
     * 法人/个人身份
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberIdentity(Map<String,Object> params){

        List<Map<String,Object>> list = realCertificationMapper.findMemberIdentity(params);

        // 判断是否是代理账号进行实名认证，通过params中的入参isProxy来判断
        // 如果isProxy为1则表示此账号是被代理的账号
        if ("1".equals(MapUtils.getString(params, "isProxy"))) {
            //操作代理账号在代理管理里面的数据显示
            //do nothing

        } else {
            //用户向导进入的实名认证
            if (CollectionUtils.isEmpty(list)) {
                //查看是否存在代理管理时插入的数据，通过account、is_proxy=1来查询。
                params.put("isProxy", 0);
                list = realCertificationMapper.findMemberIdentity(params);
            }
        }
        for(Map<String,Object> map :list){
            String images = MapUtils.getString(map,"card_image");
            if(StringUtil.isNotBlank(images)){
                String[] image = images.split(";");
                map.put("card_image",image);
            }else{
                map.put("card_image",new String[]{});
            }

            map.put("status","1".equals(MapUtils.getString(map,"status"))?true:false);

        }

        return list;
    }


    @Override
    public void updateMemberIdentity(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String images = MapUtils.getString(map, "card_image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("card_image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("card_image", pictureMerge);
        }
        realCertificationMapper.updateMemberIdentity(map);
        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public void saveMemberIdentity(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String uuid = UuidUtil.get32UUID();
        map.put("member_identity_info_id",uuid);
        String images = MapUtils.getString(map, "card_image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("card_image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("card_image", pictureMerge);
        }
        realCertificationMapper.saveMemberIdentity(map);
        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }

    }

    @Override
    public String deleteMemberIdentity(Map<String,Object> params){
        params.put("member_name_id",MapUtils.getString(params,"member_identity_info_id"));

        List<Map<String,Object>> list = realCertificationMapper.findMemberAdministrator(params);
        if(list.size()>0){
            return "301";
        }else{
            realCertificationMapper.deleteMemberIdentity(params);
            return "200";
        }


    }



    /**
     * 法人/个人身份（管理员）
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberAdministrator(Map<String,Object> params){

        List<Map<String,Object>> list = realCertificationMapper.findMemberAdministrator(params);

        // 判断是否是代理账号进行实名认证，通过params中的入参isProxy来判断
        // 如果isProxy为1则表示此账号是被代理的账号
        if ("1".equals(MapUtils.getString(params, "isProxy"))) {
            //操作代理账号在代理管理里面的数据显示
            //do nothing

        } else {
            //用户向导进入的实名认证
            if (CollectionUtils.isEmpty(list)) {
                //查看是否存在代理管理时插入的数据，通过account、is_proxy=1来查询。
                params.put("isProxy", 0);
                list = realCertificationMapper.findMemberAdministrator(params);
            }
        }
        for(Map<String,Object> map :list){
            String images = MapUtils.getString(map,"card_image");
            if(StringUtil.isNotBlank(images)){
                String[] image = images.split(";");
                map.put("card_image",image);
            }else{
                map.put("card_image",new String[]{});
            }

            String empower_images = MapUtils.getString(map,"empower_image");
            if(StringUtil.isNotBlank(empower_images)){
                String[] empower_image = empower_images.split(";");
                map.put("empower_image",empower_image);
            }else{
                map.put("empower_image",new String[]{});
            }

            map.put("status","1".equals(MapUtils.getString(map,"status"))?true:false);

        }

        return list;
    }

    @Override
    public List<Map<String,Object>> findMemberAdministratorOfByIdentity(Map<String,Object> params){
        List<Map<String,Object>> list = realCertificationMapper.findMemberAdministratorOfByIdentity(params);

        return list;
    }

    @Override
    public void updateMemberAdministrator(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String images = MapUtils.getString(map, "card_image");

        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("card_image", "");
        } else {
            String[] pictures = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(pictures);
            map.put("card_image", pictureMerge);
        }
        String empower_images = MapUtils.getString(map,"empower_image");
        JSONArray empower_image = JSONArray.parseArray(empower_images);
        if (empower_image.size() == 0) {
            map.put("empower_image", "");
        } else {
            String[] empower = ImageUtil.getJsonToStringArray(empower_images);
            String pictureMerge = ImageUtil.stringArrayMerge(empower);
            map.put("empower_image", pictureMerge);
        }
        realCertificationMapper.updateMemberAdministrator(map);
        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public void saveMemberAdministrator(Map<String,Object> params){
        Map<String,Object> map = MapUtils.getMap(params,"data");
        String uuid = UuidUtil.get32UUID();
        map.put("member_administrator_info_id",uuid);
        String images = MapUtils.getString(map, "card_image");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            map.put("card_image", "");
        } else {
            String[] card = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(card);
            map.put("card_image", pictureMerge);
        }

        String empower_images = MapUtils.getString(map,"empower_image");
        JSONArray empower_image = JSONArray.parseArray(empower_images);
        if (empower_image.size() == 0) {
            map.put("empower_image", "");
        } else {
            String[] empower = ImageUtil.getJsonToStringArray(empower_images);
            String pictureMerge = ImageUtil.stringArrayMerge(empower);
            map.put("empower_image", pictureMerge);
        }
        realCertificationMapper.saveMemberAdministrator(map);
        if (!"1".equals(MapUtils.getString(params, "isProxy"))) {
            //用户向导填写实名认证才保存步骤
            //保存用户步骤
            Map<String,Object> loginMap = MapUtils.getMap(map,"loginStep");
            loginUserService.saveOrUpdateStep(loginMap);
        }
    }

    @Override
    public void deleteMemberAdministrator(Map<String,Object> params){
        realCertificationMapper.deleteMemberAdministrator(params);
    }

    @Override
    public String findRealCertificationEnd(Map<String,Object> params){

        List<Map<String,Object>> memberAptitude = realCertificationMapper.findMemberAptitude(params);

        List<Map<String,Object>> memberIdentity = realCertificationMapper.findMemberIdentity(params);
        if(memberAptitude.size()>0 && memberIdentity.size()>0){
            String userType = MapUtils.getString(params,"user_type");
            if(userType.equals("0")||userType.equals("4")){
                params.put("flag","3");
                loginUserMapper.updateIdentityFlag(params);
                loginUserMapper.updateIsRealIdentity(params);
                return "200";
            }else{
                return "300";
            }

        }else{
            return "300";
        }

    }


}
