package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NswyExpertDao;
import com.ovit.manager.modules.nswy.entity.NswyExpert;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**专家 service
 * Created by ${huipei.x} on 2017-8-16.
 */
@Service
@Transactional(readOnly = true)
public class NswyExpertService extends CrudService<NswyExpertDao, NswyExpert> {

    @Autowired
    private NswyExpertDao nswyExpertDao;

    private static Map<String,Object> species;

    public Page<NswyExpert> findExpertList(NswyExpert nswyExpert, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyExpert> page = this.findPage(new Page<>(request, response), nswyExpert);
        /*if(null !=page.getList()&&page.getList().size()>0){
            if(species ==  null || species.keySet().isEmpty()){
                initial();
            }
            for(NswyExpert expert : page.getList()){
                String str = expert.getAdeptSpecies();
                String[] array = str.split(" ");
                String name="";
                for(String stt : array){
                    if(ObjectUtil.isNotEmpty(species.get(stt))){
                        name += species.get(stt)+" ";
                    }else{
                        name += stt+" ";
                    }
                }
                if(StringUtil.isNotBlank(name)){
                    name = name.substring(0, name.length()-1);
                    expert.setAdeptSpecies(name);
                }else{
                    expert.setAdeptSpecies("");
                }
            }
        }*/
        return page;
    }

    /*@PostConstruct
    public void initial(){
        String url = Global.getConfig("api.url")+"/wiki/api/species/listSpecies";
        species = new HashMap<>();
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{pageNo:")
                .append(1)
                .append(",pageSize:")
                .append(10000)
                .append("}");
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
            JSONObject object = JSONObject.parseObject(result);
            List<Object> list = (List<Object>) object.get("data");
            for(Object obj : list){
                JSONObject json = (JSONObject) obj;
                String classId = json.getString("fcode");
                String className = json.getString("fname");
                species.put(classId,className);
            }
        }catch (Exception e){

        }
    }*/

    public NswyExpert getExpertById(Integer expertId){
        NswyExpert expert = new NswyExpert();
        expert.setExpertId(expertId);
        return  nswyExpertDao.get(expert);
    }

    @Transactional()
    public void deleteExpert(Integer expertId){
        nswyExpertDao.deleteByPrimaryKey(expertId);
    }

    @Transactional()
    public void updateExpert(NswyExpert nswyExpert){
        nswyExpertDao.updateByPrimaryKeySelective(nswyExpert);
    }

    @Transactional()
    public void updateApproveStatus(Integer expertId,Integer approveStatus){
        nswyExpertDao.updateApproveStatus(expertId,approveStatus);
    }
}
