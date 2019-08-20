package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.ovit.nswy.member.mapper.FarmFamilyDetailMapper;
import com.ovit.nswy.member.service.FarmFamilyDetailService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
public class FarmFamilyDeatilServiceImpl implements FarmFamilyDetailService {

    @Autowired
    private FarmFamilyDetailMapper farmFamilyDetailMapper;


    @Override
    public List<Map<String,Object>> findFamilyMember(Map<String,Object> params){

        List<Map<String,Object>> family_meber  = farmFamilyDetailMapper.findFamilyMember(params);
        if(family_meber.size()>0&&family_meber!=null){
            for(Map<String,Object> map:family_meber){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"family_status"))){
                    if(MapUtils.getString(map,"family_status").equals("1")){
                        map.put("family_status",true);
                    }else{
                        map.put("family_status",false);
                    }
                }else{
                    map.put("family_status",false);
                }

            }
        }
        return family_meber;

    }

    @Override
    public List<Map<String,Object>> findOutputProduct(Map<String,Object> params){
        List<Map<String,Object>>   output_product= farmFamilyDetailMapper.findOutputProduct(params);
        if(output_product.size()>0&&output_product!=null){
            for(Map<String,Object> map:output_product){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"product_status"))){
                    if(MapUtils.getString(map,"product_status").equals("1")){
                        map.put("product_status",true);
                    }else{
                        map.put("product_status",false);
                    }
                }else{
                    map.put("product_status",false);
                }

            }
        }

        return output_product;

    }

    @Override
    public List<Map<String,Object>> findProvideServices(Map<String,Object> params){
        List<Map<String,Object>> provide_services  = farmFamilyDetailMapper.findProvideServices(params);
        if(provide_services.size()>0&&provide_services!=null){
            for(Map<String,Object> map:provide_services){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"provide_status"))){
                    if(MapUtils.getString(map,"provide_status").equals("1")){
                        map.put("provide_status",true);
                    }else{
                        map.put("provide_status",false);
                    }
                }else{
                    map.put("provide_status",false);
                }
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"pictureList"))){
                    String pictureLists  = MapUtils.getString(map,"pictureList");
                    String[] pictureList = pictureLists.split(",");
                    map.put("pictureList",pictureList);
                }else{
                    String[] pictureList = new String[]{};
                    map.put("pictureList",pictureList);
                }

            }

        }
        return provide_services;

    }

    @Override
    public List<Map<String,Object>> findPurchaseIntention(Map<String,Object> params){
        List<Map<String,Object>> purchase_intention  = farmFamilyDetailMapper.findPurchaseIntention(params);
        if(purchase_intention.size()>0&&purchase_intention!=null){
            for(Map<String,Object> map:purchase_intention){
                if(com.github.xphsc.util.StringUtil.isNotEmpty(MapUtils.getString(map,"purchase_status"))){
                    if(MapUtils.getString(map,"purchase_status").equals("1")){
                        map.put("purchase_status",true);
                    }else{
                        map.put("purchase_status",false);
                    }
                }else{
                    map.put("purchase_status",false);
                }

            }
        }
        return purchase_intention;

    }

    @Override
    public List<Map<String,Object>> findHouseSituation(Map<String,Object> params){
        List<Map<String,Object>> house_situation  = farmFamilyDetailMapper.findHouseSituation(params);
        if(house_situation.size()>0&&house_situation!=null){
            for(Map<String,Object> map:house_situation){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"status"))){
                    if(MapUtils.getString(map,"status").equals("1")){
                        map.put("status",true);
                    }else{
                        map.put("status",false);
                    }
                }else{
                    map.put("status",false);
                }

            }
        }

        return house_situation;

    }

    @Override
    public List<Map<String,Object>> findFamilySituation(Map<String,Object> params){
        List<Map<String,Object>> family_situation  = farmFamilyDetailMapper.findFamilySituation(params);
        if(family_situation.size()>0&&family_situation!=null){
            for(Map<String,Object> map:family_situation){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"family_modern_status"))){
                    if(MapUtils.getString(map,"family_modern_status").equals("1")){
                        map.put("family_modern_status",true);
                    }else{
                        map.put("family_modern_status",false);
                    }
                }else{
                    map.put("family_modern_status",false);
                }

            }
        }
        return family_situation;

    }


    @Override
    @Transactional
    public void insertFamilyMember(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deleteFamilyMember(params);
            //家庭成员
            List<Map<String,Object>> family_Member = (List<Map<String,Object>>)params.get("family_Member");
            for(Map<String,Object> familyMember :family_Member){
                familyMember.put("farm_family_id",farm_family_id);
                familyMember.put("account","");
                farmFamilyDetailMapper.insertFamilyMember(familyMember);

            }
        }else{
            farmFamilyDetailMapper.deleteFamilyMember(params);
            //家庭成员
            List<Map<String,Object>> family_Member = (List<Map<String,Object>>)params.get("member");
            for(Map<String,Object> familyMember :family_Member){
                familyMember.put("farm_family_id",farm_family_id);
                familyMember.put("account",MapUtils.getString(params,"account"));
                farmFamilyDetailMapper.insertFamilyMember(familyMember);

            }
        }



    };

    @Override
    @Transactional
    public void insertOutputProduct(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deleteOutputProduct(params);
            //产出产品
            List<Map<String,Object>> output_product = (List<Map<String,Object>>) params.get("output_product");
            for(Map<String,Object> outputProduct :output_product){
                outputProduct.put("farm_family_id",farm_family_id);
                outputProduct.put("account","");
                farmFamilyDetailMapper.insertOutputProduct(outputProduct);
            }
        }else {
            farmFamilyDetailMapper.deleteOutputProduct(params);
            //产出产品
            List<Map<String,Object>> output_product = (List<Map<String,Object>>) params.get("product");
            for(Map<String,Object> outputProduct :output_product){
                outputProduct.put("farm_family_id",farm_family_id);
                outputProduct.put("account",MapUtils.getString(params,"account"));
                farmFamilyDetailMapper.insertOutputProduct(outputProduct);
            }
        }



    };

    @Override
    @Transactional
    public void insertProvideServices(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deleteProvideServices(params);
            //提供服务
            List<Map<String,Object>> provide_services = (List<Map<String,Object>>) params.get("provide_services");
            for(Map<String,Object> provideServices :provide_services){
                provideServices.put("farm_family_id",farm_family_id);
                provideServices.put("account","");
                List<Map<String,Object>> listPicture = (List<Map<String,Object>>) provideServices.get("pictureList");
                if(listPicture.size()>0){
                    String pictureList  = JsonToStringUtils.initJson(provideServices,"pictureList");
                    provideServices.put("pictureList",pictureList);
                }else{
                    provideServices.put("pictureList","");
                }
                farmFamilyDetailMapper.insertProvideServices(provideServices);
            }
        }else {
            farmFamilyDetailMapper.deleteProvideServices(params);
            //提供服务
            List<Map<String,Object>> provide_services = (List<Map<String,Object>>) params.get("provide");
            for(Map<String,Object> provideServices :provide_services){
                provideServices.put("farm_family_id",farm_family_id);
                provideServices.put("account",MapUtils.getString(params,"account"));
                List<Map<String,Object>> listPicture = (List<Map<String,Object>>) provideServices.get("pictureList");
                if(listPicture.size()>0){
                    String pictureList  = JsonToStringUtils.initJson(provideServices,"pictureList");
                    provideServices.put("pictureList",pictureList);
                }else{
                    provideServices.put("pictureList","");
                }
                farmFamilyDetailMapper.insertProvideServices(provideServices);
            }
        }

    };

    @Override
    @Transactional
    public void insertPurchaseIntention(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deletePurchaseIntention(params);
            //求购意向
            List<Map<String,Object>> purchase_intention = (List<Map<String,Object>>) params.get("purchase_intention");
            for(Map<String,Object> purchaseIntention :purchase_intention){
                purchaseIntention.put("farm_family_id",farm_family_id);
                purchaseIntention.put("account","");
                farmFamilyDetailMapper.insertPurchaseIntention(purchaseIntention);
            }
        }else {
            farmFamilyDetailMapper.deletePurchaseIntention(params);
            //求购意向
            List<Map<String,Object>> purchase_intention = (List<Map<String,Object>>) params.get("purchase");
            for(Map<String,Object> purchaseIntention :purchase_intention){
                purchaseIntention.put("farm_family_id",farm_family_id);
                purchaseIntention.put("account",MapUtils.getString(params,"account"));
                farmFamilyDetailMapper.insertPurchaseIntention(purchaseIntention);
            }
        }


    };

    @Override
    @Transactional
    public void insertHouseSituation(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deleteHouseSituation(params);
            //房屋生活情况house_situation
            List<Map<String,Object>> house_situation = (List<Map<String,Object>>) params.get("house_situation");
            for(Map<String,Object> houseSituation :house_situation){
                houseSituation.put("farm_family_id",farm_family_id);
                houseSituation.put("account","");
                farmFamilyDetailMapper.insertHouseSituation(houseSituation);
            }
        }else{
            farmFamilyDetailMapper.deleteHouseSituation(params);
            //房屋生活情况house_situation
            List<Map<String,Object>> house_situation = (List<Map<String,Object>>) params.get("house");
            for(Map<String,Object> houseSituation :house_situation){
                houseSituation.put("farm_family_id","");
                houseSituation.put("account",MapUtils.getString(params,"account"));
                farmFamilyDetailMapper.insertHouseSituation(houseSituation);
            }
        }

    };

    @Override
    @Transactional
    public void insertFamilySituation(Map<String,Object> params){
        String farm_family_id= MapUtils.getString(params,"farm_family_id");
        //通过ID来判断是否为种养户还是认证
        if(StringUtil.isNotEmpty(farm_family_id)){
            farmFamilyDetailMapper.deleteFamilySituation(params);
            //家庭现代化情况
            List<Map<String,Object>> family_situation = (List<Map<String,Object>>) params.get("family_situation");
            for(Map<String,Object> familySituation :family_situation){
                familySituation.put("farm_family_id",farm_family_id);
                familySituation.put("account", "");
                farmFamilyDetailMapper.insertFamilySituation(familySituation);
            }
        }else{
            farmFamilyDetailMapper.deleteFamilySituation(params);
            List<Map<String,Object>> family_situation = (List<Map<String,Object>>) params.get("modern");
            for(Map<String,Object> familySituation :family_situation){
                familySituation.put("farm_family_id",farm_family_id);
                familySituation.put("account", MapUtils.getString(params,"account"));
                farmFamilyDetailMapper.insertFamilySituation(familySituation);
            }
        }

    };


}
