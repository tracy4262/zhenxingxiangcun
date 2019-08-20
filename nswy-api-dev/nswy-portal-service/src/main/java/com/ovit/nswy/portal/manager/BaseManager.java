package com.ovit.nswy.portal.manager;


import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.portal.mapper.IndustrialMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by ${huipei.x} on 2018-2-5.
 */
@Service
public class BaseManager {
    @Autowired
    private IndustrialMapper industrialMapper;

    public String getIndustrialName(String name){
        String[] relatedIndustryArr=StringUtil.split(name, " ");
         String ids="";
        for(String relatedIndustry:relatedIndustryArr ){
            ids += "'"+relatedIndustry+"'"+",";
        }
        ids = ids.substring(0, ids.length()-1);
        List<String> stringList=industrialMapper.findByIds(ids);
        String industrialName=StringUtil.join(stringList, "、");
        if (StringUtils.isNotEmpty(industrialName)) {
            industrialName = industrialName.substring(0, industrialName.length()-1);
        }

         return industrialName;
    }

}
