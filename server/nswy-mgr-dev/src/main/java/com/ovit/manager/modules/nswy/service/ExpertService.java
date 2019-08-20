package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.cms.utils.ServiceUtils;
import com.ovit.manager.modules.nswy.dao.ExpertDao;
import com.ovit.manager.modules.nswy.dao.SystemDictDao;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExpertService extends CrudService<ExpertDao, LoginUser> {

    @Autowired
    private ExpertDao expertDao;

    @Autowired
    private SystemDictDao systemDictDao;
    private static Map<String,Object> speciesMap;

    public Page<LoginUser> findList(LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {
        Page<LoginUser> page = this.findPage(new Page<>(request,response),loginUser);
        List<LoginUser> pageList = page.getList();
        if (CollectionUtils.isNotEmpty(pageList)) {
            if (MapUtils.isEmpty(speciesMap)){
                initial();
            }
            for (LoginUser loginUser1 : pageList) {
                //物种ID查询物种名称
                String[] adeptSpecies = loginUser1.getAdeptSpecies().split(" ");
                StringBuffer speciesName = new StringBuffer();
                for (String speciesId : adeptSpecies) {
                    String name = MapUtils.getString(speciesMap,speciesId);
                    if (StringUtils.isNotEmpty(name)) {
                        speciesName.append(name).append(" ");
                    }
                }
                loginUser1.setAdeptSpecies(speciesName.toString());
                //服务ID查询服务名称
//                String serviceId = loginUser1.getRelatedService();
//                if (StringUtils.isNotEmpty(serviceId)) {
//                    String serviceName = ServiceUtils.getServiceNameByIdArray(serviceId);
//                    loginUser1.setRelatedService(serviceName);
//                }

                String[] relatedIndustryId = loginUser1.getRelatedIndustry().split(" ");
                List<String> dictNameList = systemDictDao.queryByDictId(Arrays.asList(relatedIndustryId));
                if (CollectionUtils.isNotEmpty(dictNameList)) {
                    loginUser1.setRelatedIndustry(StringUtil.join(dictNameList, " "));
                }
            }
        }
        return page;
    }


    public void deleteByPrimaryKey(Integer id){
        expertDao.deleteByPrimaryKey(id);
    }

    @Transactional()
    public void expertUpdateNavDisplay(Map<String, Object> map) {
        expertDao.expertUpdateNavDisplay(map);
    }
    @Transactional()
    public void expertSetOrderNum(Map<String, Object> map) {
        expertDao.expertSetOrderNum(map);
    }
    @Transactional()
    public void expertUpdateHome(Map<String, Object> map) {
        expertDao.expertUpdateHome(map);
    }

    @PostConstruct
    public void initial(){
        String url = Global.getConfig("api.url")+"/wiki/api/species/listSpecies";
        speciesMap = new HashMap<>();
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
                String classId = json.getString("speciesid");
                String className = json.getString("fname");
                speciesMap.put(classId,className);
            }
        }catch (Exception e){

        }
    }

}
