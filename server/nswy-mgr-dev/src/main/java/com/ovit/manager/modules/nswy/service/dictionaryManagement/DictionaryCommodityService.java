package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryCommodityDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictionaryCommodityService extends CrudService<DictionaryCommodityDao, DictionaryCommodity> {

    @Autowired
    private DictionaryCommodityDao dictionaryCommodityDao;

//    public Page<DictionaryCommodity> findList(DictionaryCommodity dictionaryCommodity, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryCommodity);
//    }

    public List<DictionaryCommodity> findList(DictionaryCommodity dictionaryCommodity){
        return dictionaryCommodityDao.findList(dictionaryCommodity);
    }

    public List<DictionaryCommodity> findCommodityList(DictionaryCommodity dictionaryCommodity){

        List<DictionaryCommodity> dictionaryCommoditys = dictionaryCommodityDao.findList(dictionaryCommodity);

        return dictionaryCommoditys ;
    }

    public List<Map<String,Object>> findIndustry(){
        return dictionaryCommodityDao.findIndustry();
    }

    public List<Map<String,Object>> findProductType(){
        return dictionaryCommodityDao.findProductType();
    }

    public List<Map<String,Object>> findSpeciesList() {

        String url = Global.getConfig("api.url")+"/wiki/api/species/findBySpeciesName";

        List<Map<String,Object>> list = new ArrayList<>();
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{fname:")
                .append("''")
                .append(",auditstatus:")
                .append(1)
                .append("}");
        try {
            String resul = HttpUtil.doPost(url,jsonBody.toString());
            JSONObject object = JSONObject.parseObject(resul);
            list = (List<Map<String,Object>>) object.get("data");

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void saveCommodity(DictionaryCommodity dictionaryCommodity){
            dictionaryCommodityDao.saveCommodity(dictionaryCommodity);
    }

    public void updateCommodity(DictionaryCommodity dictionaryCommodity){
            dictionaryCommodityDao.updateCommodity(dictionaryCommodity);
    }

    public void deleteCommodity(String id){
            dictionaryCommodityDao.deleteCommodity(id);
    }


}
