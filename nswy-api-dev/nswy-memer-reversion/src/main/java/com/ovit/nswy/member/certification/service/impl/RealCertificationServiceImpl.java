package com.ovit.nswy.member.certification.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.certification.mapper.RealCertificationMapper;
import com.ovit.nswy.member.certification.service.RealCertificationService;
import com.ovit.nswy.member.perfectInfo.mapper.LandInformationMapper;
import com.ovit.nswy.member.perfectInfo.mapper.LoginUserMapper;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
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
public class RealCertificationServiceImpl implements RealCertificationService{

   @Autowired
   private RealCertificationMapper realCertificationMapper;

   @Autowired
   private LoginUserMapper loginUserMapper;

   @Autowired
   private PerfectBasicService perfectBasicService;

   @Autowired
   private LandInformationMapper landInformationMapper;

   @Override
   public Map<String,Object> findList(Map<String,Object> params){

       Map<String,Object> map = realCertificationMapper.findList(params);

       if(MapUtils.isEmpty(map)){
           map = new HashMap<>();
           Map<String,Object> loginUser = realCertificationMapper.findByLoginUser(params);

           map.put("user_nswy_id",MapUtils.getString(loginUser,"nswy_id_model"));

           map.put("user_id",MapUtils.getString(loginUser,"login_account"));

           //map.put("user_abbreviation",MapUtils.getString(loginUser,"realname"));

           map.put("pass_word",MapUtils.getString(loginUser,"login_passwd"));

           map.put("image",MapUtils.getString(loginUser,"avatar"));

           map.put("location",MapUtils.getString(loginUser,"city"));

           map.put("user_status",true);
           map.put("user_abbreviation_status",true);
           map.put("user_name_remark_status",true);
           map.put("seat_phone_status",true);
           map.put("phone_status",true);
           map.put("qq_number_status",true);
           map.put("wechat_number_status",true);
           map.put("email_status",true);
           map.put("website_url_status",true);
           map.put("location_status",true);

       }else{
           map.put("user_status","1".equals(MapUtils.getString(map,"user_status"))?true:false);
           map.put("user_abbreviation_status","1".equals(MapUtils.getString(map,"user_abbreviation_status"))?true:false);
           map.put("user_name_remark_status","1".equals(MapUtils.getString(map,"user_name_remark_status"))?true:false);
           map.put("seat_phone_status","1".equals(MapUtils.getString(map,"seat_phone_status"))?true:false);
           map.put("phone_status","1".equals(MapUtils.getString(map,"phone_status"))?true:false);
           map.put("qq_number_status","1".equals(MapUtils.getString(map,"qq_number_status"))?true:false);
           map.put("wechat_number_status","1".equals(MapUtils.getString(map,"wechat_number_status"))?true:false);
           map.put("email_status","1".equals(MapUtils.getString(map,"email_status"))?true:false);
           map.put("website_url_status","1".equals(MapUtils.getString(map,"website_url_status"))?true:false);
           map.put("location_status","1".equals(MapUtils.getString(map,"location_status"))?true:false);
       }

       String image = MapUtils.getString(map,"image");
       if (StringUtils.isNotBlank(image)) {
           if (image.startsWith("http:")) {
               image = image.substring(5);
           }
       } else {
           image = "";
       }


       map.put("image",image);

       return map;
   }


   @Override
   public void saveReal(Map<String,Object> params){
       try{
           Map<String,Object> map = MapUtils.getMap(params,"data");
           //查询数据是否存在
           Integer count  = realCertificationMapper.findCount(map);
           if(count>0){
               realCertificationMapper.deleteRealCertification(map);
           }
           String uuid = UuidUtil.get32UUID();
           map.put("member_virtual_info_id",uuid);
           realCertificationMapper.saveRealCertification(map);

           //将图像保存进 login_user
           if(StringUtil.isNotBlank(MapUtils.getString(map,"image"))){
               realCertificationMapper.updateLoginUser(map);
           }

       }catch (Exception e){
           e.printStackTrace();
       }

   }

    /**
     * 会员资质信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberAptitude(Map<String,Object> params){

        List<Map<String,Object>> list = realCertificationMapper.findMemberAptitude(params);
        for(Map<String,Object> map :list){
            String images = MapUtils.getString(map,"aptitude_image");
            if(StringUtil.isNotBlank(images)){
                String[] image = images.split(";");
                map.put("aptitude_image",image);
            }else{
                map.put("aptitude_image",new String[]{});
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
        realCertificationMapper.updateMemberAptitude(map);
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

        realCertificationMapper.saveMemberAptitude(map);
    }

    @Override
    public void deleteMemberAptitude(Map<String,Object> params){
        realCertificationMapper.deleteMemberAptitude(params);
    }


    /**
     * 会员联系方式
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberContact(Map<String,Object> params){
        List<Map<String,Object>> list = realCertificationMapper.findMemberContact(params);
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
    }

    @Override
    public void deleteMemberIdentity(Map<String,Object> params){
        realCertificationMapper.deleteMemberIdentity(params);
    }



    /**
     * 法人/个人身份（管理员）
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findMemberAdministrator(Map<String,Object> params){
        List<Map<String,Object>> list = realCertificationMapper.findMemberAdministrator(params);
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

    @Override
    public List<Map<String, Object>> neighborVillageStyle(Map<String, Object> params) {

        List<Map<String, Object>> list = new ArrayList<>();
        String account = MapUtils.getString(params, "account");
        Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
        params.put("templateId", MapUtils.getString(useTemplate, "templateId"));
        //查询该用户行政区域上级，根据实名认证的联系人信息第一条中的地址信息获取
        List<Map<String,Object>> address = landInformationMapper.findAddressInfo(params);
        //通过地址查询到行政区划编码
        if (CollectionUtils.isNotEmpty(address)) {
            Map<String,Object> addressMap = address.get(0);
            String[] addrArr = MapUtils.getString(addressMap,"locationId").split("/");
            String thisAddr = addrArr[addrArr.length-1];
            if (addrArr.length < 2) {
                return list;
            }
            String upAddr = addrArr[addrArr.length-2];
            Map<String,Object> map = new HashMap<>();
            map.put("thisAddr", thisAddr);
            map.put("upAddr", upAddr);
            map.put("account", account);
            List<Map<String, Object>> neighborList = realCertificationMapper.neighborList(map);
            if (CollectionUtils.isNotEmpty(neighborList)) {
                for (Map<String,Object> neighbor : neighborList) {

                    String[] location = MapUtils.getString(neighbor,"location").split("/");
                    neighbor.put("location", location[location.length-1]);
                }
                list.addAll(neighborList);
            }
        }

        return list;
    }


    @Override
    public List<Map<String, Object>> getMemberType(Map<String,Object> params){
        List<Map<String, Object>> list = realCertificationMapper.getMemberType(params);
        if(list!=null&&list.size()>0){
            for(Map map:list){
                params.put("pid",MapUtils.getString(map,"value"));
                List<Map<String, Object>> list1 = getMemberType(params);
                if (list1!=null&&list1.size()>0) {
                    map.put("children",list1);
                }
            }
        }
        return list;
    }
}
