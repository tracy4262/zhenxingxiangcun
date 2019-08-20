package com.ovit.nswy.member.perfectInfo.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.HistoryEvolutionMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.HistoryEvolutionService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史沿革
 */
@Service
public class HistoryEvolutionServiceImpl implements HistoryEvolutionService {

    @Autowired
    private HistoryEvolutionMapper historyEvolutionMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Override
    public Map<String,Object> findHistoryEvolution(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("historyEvolution_name", "历史沿革");
        } else {
            map.put("historyEvolution_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> lists = historyEvolutionMapper.findHistoryEvolution(params);
        if(lists.size()>0){

            for(Map<String,Object> history:lists ){
                history.put("status","1".equals(MapUtils.getString(history,"status"))? true : false);
            }

            map.put("historyEvolution",lists);


        }else{

            map.put("historyEvolution",new ArrayList<>());

        }


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

        return map;
    }

    @Override
    public void updateHistoryEvolutionName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("history_evolution_info_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = historyEvolutionMapper.findHistoryEvolution(map);
        if(list.size()>0){
            historyEvolutionMapper.updateHistoryEvolutionName(params);
        }else{
            historyEvolutionMapper.saveHistoryEvolution(params);
        }

    }



    @Override
    public void saveHistoryEvolution(Map<String,Object> params){
        Map<String,Object> employeeRoster = (Map<String, Object>) params.get("historyEvolution");
        String user_id = MapUtils.getString(params,"user_id");
        employeeRoster.put("user_id",user_id);
        employeeRoster.put("history_evolution_info_name",MapUtils.getString(params,"historyEvolution_name"));
        employeeRoster.put("year_id",MapUtils.getString(params,"yearId"));
        employeeRoster.put("flag",MapUtils.getString(params,"flag"));
        employeeRoster.put("templateId",MapUtils.getString(params,"templateId"));
        historyEvolutionMapper.saveHistoryEvolution(employeeRoster);


    }

    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));

            physicalGeographyMapper.saveTextPreview(textPreview);
        }
    }

    @Override
    public void deleteHistoryEvolution(Map<String,Object> params){
        historyEvolutionMapper.deleteHistoryEvolution(params);
    }


}
