package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryGeneralServiceDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGeneralService;
import org.apache.commons.collections.MapUtils;
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
public class DictionaryGeneralServiceService extends CrudService<DictionaryGeneralServiceDao, DictionaryGeneralService> {

    @Autowired
    private DictionaryGeneralServiceDao dictionaryGeneralServiceDao;

    /*public Page<DictionaryGeneralService> findList(DictionaryGeneralService dictionaryGeneralService, HttpServletRequest request, HttpServletResponse response) {
        Page<DictionaryGeneralService> page = this.findPage(new Page<>(request, response), dictionaryGeneralService);
        return page;
    }*/

    public List<DictionaryGeneralService> findList(DictionaryGeneralService dictionaryGeneralService){
        return dictionaryGeneralServiceDao.findList(dictionaryGeneralService);
    }

    public List<DictionaryGeneralService> findGeneralServiceList(DictionaryGeneralService dictionaryGeneralService){

        List<DictionaryGeneralService> dictionaryGeneralServices = dictionaryGeneralServiceDao.findList(dictionaryGeneralService);

        return dictionaryGeneralServices ;
    }

    public List<Map<String,Object>> findIndustry(){
        return dictionaryGeneralServiceDao.findIndustry();
    }
    public List<Map<String,Object>> findSpecies(){
        return dictionaryGeneralServiceDao.findSpecies();
    }

    public List<Map<String,Object>> findServiceType(){
        return dictionaryGeneralServiceDao.findServiceType();
    }

    public List<Map<String,Object>> findService(){
        return dictionaryGeneralServiceDao.findService();
    }

    public List<Map<String,Object>> findProduct(){
        return dictionaryGeneralServiceDao.findProduct();
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

    public void saveGeneralService(DictionaryGeneralService dictionaryGeneralService){
            dictionaryGeneralServiceDao.saveGeneralService(dictionaryGeneralService);
    }

    public void updateGeneralService(DictionaryGeneralService dictionaryGeneralService){
            dictionaryGeneralServiceDao.updateGeneralService(dictionaryGeneralService);
    }

    public void deleteGeneralService(String id){
            dictionaryGeneralServiceDao.deleteGeneralService(id);
    }


}
