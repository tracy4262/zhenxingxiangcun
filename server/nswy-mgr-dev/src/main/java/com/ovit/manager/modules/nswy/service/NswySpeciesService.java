package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ExpertApplyDao;
import com.ovit.manager.modules.nswy.entity.ExpertApply;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**物种 service
 * Created by ${zhanlei} on 2017-8-16.
 */
@Service
@Transactional(readOnly = true)
public class NswySpeciesService extends CrudService<ExpertApplyDao, ExpertApply> {

    @Autowired
    private ExpertApplyDao ExpertApplyDao;

    public Page<Map<String,Object>> getSpeciesList(Map<String, Object> params) {
        String pageNo;
        String pageSize;
        String fname;
        if (params.get("pageNo") != null && params.get("pageNo") != ""){
            pageNo = String.valueOf(params.get("pageNo"));
        }else {
            pageNo = "1";
        }
        if (params.get("pageSize") != null && params.get("pageSize") != ""){
            pageSize = String.valueOf(params.get("pageSize"));
        }else {
            pageSize = "30";
        }


        String url = Global.getConfig("api.url")+"/wiki/api/species/listSpecies";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{pageNum:")
                .append(pageNo)
                .append(",pageSize:")
                .append(pageSize)
                .append(",auditstatus:")
                .append(MapUtils.getString(params,"auditstatus"));
        if (params.get("fname") != null && params.get("fname") != ""){
            fname = MapUtils.getString(params,"fname");
            jsonBody.append(",keywords:")
                    .append('"').append(fname).append('"');
        }
        jsonBody.append("}");
    	Page<Map<String,Object>> page = new Page<>();
        List<Map<String,Object>> list;
        try {
            String resul = HttpUtil.doPost(url,jsonBody.toString());
            JSONObject object = JSONObject.parseObject(resul);
            list = (List<Map<String,Object>>) object.get("data");
            Long total = Long.parseLong(String.valueOf(object.get("total")));
            for (Map<String,Object> map : list) {
                String fcreatorid = MapUtils.getString(map,"fcreatorid");
                String displayName = ExpertApplyDao.queryDisplayNameByAccount(fcreatorid);
                map.put("displayName",displayName);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String fcreatetime  = formatter.format(new Date(Long.parseLong(MapUtils.getString(map,"updatetime"))));
                map.put("updatetime",fcreatetime);
            }
            page.setList(list);
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize(Integer.parseInt(pageSize));
            page.setCount(total);
            //设置总页数
            Integer totals = Integer.valueOf(String.valueOf(total));
            Integer size  = Integer.valueOf(pageSize);
            page.setLast( totals % size == 0 ? totals / size : totals / size + 1);
            if (Integer.parseInt(pageNo) != 0){
                page.setPrev(Integer.parseInt(pageNo)-1);
            }
            page.setNext(Integer.parseInt(pageNo)+1);
        }catch (Exception e){

        }
        return page;
    }

    public Map<String,Object> getSpeciesById(String indexid){
        Map<String,Object> map = new HashMap<>();
        String url = Global.getConfig("api.url")+"/wiki/api/species/searchSpecies";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{indexid:").append("\"")
                .append(indexid).append("\"").append("}");
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
            JSONObject object = JSONObject.parseObject(result);
            map = (Map<String, Object>) object.get("data");
        }catch (Exception e){

        }
        return  map;
    }

    @Transactional()
    public void deleteExpertApply(Integer expertId){
    	ExpertApplyDao.deleteByPrimaryKey(expertId);
    }

    @Transactional()
    public void updateSpecies(Map<String,Object> params){
        List<Map<String,Object>> list;
        String url = new String();
        if (params.get("auditstatus") != null){
            try {

                String auditstatus  = String.valueOf(params.get("auditstatus"));
                String auditstatu = new String();
                url = Global.getConfig("api.url")+"/wiki/api/species/updateSpeciesAudit";
                if(auditstatus.equals("0")){//更新待审核
                    auditstatu = "5";
                }else if(auditstatus.equals("2")){//新增待审核
                    auditstatu = "1";
                }else if(auditstatus.equals("3")){//删除待审核
                    auditstatu = "5";
                }else{
                    auditstatu = auditstatus;
                }
                StringBuffer json = new StringBuffer();
                json.append("{indexid:")
                    .append("\"")
                    .append(String.valueOf(params.get("indexid")))
                    .append("\"")
                    .append(",")
                    .append("auditstatus:")
                    .append(auditstatu)
                    .append("}");
                HttpUtil.doPost(url,json.toString());
                if(auditstatus.equals("2")||auditstatus.equals("0")){
                    url = Global.getConfig("api.url")+"/wiki/api/species/findBySpeciesName";
                    String resul = HttpUtil.doPost(url,json.toString());
                    JSONObject object = JSONObject.parseObject(resul);
                    list = (List<Map<String,Object>>) object.get("data");
                    Map<String,Object> map = new HashMap<>();
                    map.put("image", MapUtils.getString(list.get(0),"ficon"));
                    map.put("account",MapUtils.getString(list.get(0),"fcreatorid"));
                    map.put("libraryId",MapUtils.getString(list.get(0),"speciesid"));
                    map.put("name",MapUtils.getString(list.get(0),"fname"));
                    map.put("classId",MapUtils.getString(list.get(0),"fclassifiedid"));
                    map.put("indexid",MapUtils.getString(list.get(0),"indexid"));

                    url = Global.getConfig("api.url")+"/wiki/speciesclass/listSpeciesclass";
                    json = new StringBuffer();
                    json.append("{classId:")
                            .append("\"")
                            .append(MapUtils.getString(list.get(0),"fclassifiedid"))
                            .append("\"")
                            .append("}");
                    resul = HttpUtil.doPost(url,json.toString());
                    object = JSONObject.parseObject(resul);
                    list = (List<Map<String,Object>>) object.get("data");
                    if( MapUtils.getString(list.get(0),"parentId").equals("0")){
                        map.put("className","动物/"+ MapUtils.getString(list.get(0),"className"));
                    }else{
                        map.put("className","植物/"+ MapUtils.getString(list.get(0),"className"));
                    }
                    Map<String,Object> wiki = ExpertApplyDao.findWikiConllectionList(map);
                    if(MapUtils.isEmpty(wiki)){
                        ExpertApplyDao.saveWikiLibraryInfo(map);
                    }

                }
                if(auditstatus.equals("0")){
                    url = Global.getConfig("api.url")+"/wiki/api/species/updateSpeciesPassAudit";
                    StringBuffer jsons = new StringBuffer();
                    jsons.append("{indexid:")
                            .append("\"")
                            .append(String.valueOf(params.get("indexid")))
                            .append("\"")
                            .append("}");
                    HttpUtil.doPost(url,jsons.toString());

                    url = Global.getConfig("api.url")+"/wiki/api/species/findBySpeciesName";
                    String resul = HttpUtil.doPost(url,jsons.toString());
                    JSONObject object = JSONObject.parseObject(resul);
                    list = (List<Map<String,Object>>) object.get("data");
                    Map<String,Object> map = new HashMap<>();
                    map.put("speciesid",MapUtils.getString(list.get(0),"speciesid"));
                    map.put("image", MapUtils.getString(list.get(0),"ficon"));
                    map.put("classId",MapUtils.getString(list.get(0),"fclassifiedid"));
                    url = Global.getConfig("api.url")+"/wiki/speciesclass/listSpeciesclass";
                    json = new StringBuffer();
                    json.append("{classId:")
                            .append("\"")
                            .append(MapUtils.getString(list.get(0),"fclassifiedid"))
                            .append("\"")
                            .append("}");
                    resul = HttpUtil.doPost(url,json.toString());
                    object = JSONObject.parseObject(resul);
                    list = (List<Map<String,Object>>) object.get("data");
                    if( MapUtils.getString(list.get(0),"parentId").equals("0")){
                        map.put("className","动物/"+ MapUtils.getString(list.get(0),"className"));
                    }else{
                        map.put("className","植物/"+ MapUtils.getString(list.get(0),"className"));
                    }
                    ExpertApplyDao.updateWikiLibraryInfo(map);

                }else if(auditstatus.equals("3")){
                    url = Global.getConfig("api.url")+"/wiki/api/species/listSpecies";
                    StringBuffer jsonBody = new StringBuffer();
                    jsonBody.append("{speciesid:")
                            .append("\"")
                            .append(MapUtils.getString(params,"speciesid"))
                            .append("\"")
                            .append(",auditstatus:")
                            .append("1").append("}");

                    String resul = HttpUtil.doPost(url,jsonBody.toString());
                    JSONObject object = JSONObject.parseObject(resul);
                    list = (List<Map<String,Object>>) object.get("data");
                    String indexids  = String.valueOf(list.get(0).get("indexid"));
                    url = Global.getConfig("api.url")+"/wiki/api/species/updateSpeciesAudit";
                    StringBuffer jsons = new StringBuffer();
                    jsons.append("{indexid:")
                            .append("\"")
                            .append(indexids)
                            .append("\"")
                            .append(",fisdelete:")
                            .append("1").append("}");
                    HttpUtil.doPost(url,jsons.toString());


                }

            }catch (Exception e){

            }
        }
    }

    private void pingJ(Map<String,Object> params,StringBuffer jsons){
        if (params.get("fid") != null && params.get("fid") != ""){
            String fid = MapUtils.getString(params,"fid");
            jsons.append(",fid:")
                    .append('"').append(fid).append('"');
        }
        if (params.get("finitialproductid") != null && params.get("finitialproductid") != ""){
            String finitialproductid = MapUtils.getString(params,"finitialproductid");
            jsons.append(",finitialproductid:")
                    .append('"').append(finitialproductid).append('"');
        }
        if (params.get("fpestsid") != null && params.get("fpestsid") != ""){
            String fpestsid = MapUtils.getString(params,"fpestsid");
            jsons.append(",fpestsid:")
                    .append('"').append(fpestsid).append('"');
        }
        if (params.get("findustriaclassifiedid") != null && params.get("findustriaclassifiedid") != ""){
            String findustriaclassifiedid = MapUtils.getString(params,"findustriaclassifiedid");
            jsons.append(",findustriaclassifiedid:")
                    .append('"').append(findustriaclassifiedid).append('"');
        }
        if (params.get("fremarks") != null && params.get("fremarks") != ""){
            String fremarks = MapUtils.getString(params,"fremarks");
            jsons.append(",fremarks:")
                    .append('"').append(fremarks).append('"');
        }
        if (params.get("fisdelete") != null && params.get("fisdelete") != ""){
            String fisdelete = MapUtils.getString(params,"fisdelete");
            jsons.append(",fisdelete:")
                    .append('"').append(fisdelete).append('"');
        }
        if (params.get("fisprotection") != null && params.get("fisprotection") != ""){
            String fisprotection = MapUtils.getString(params,"fisprotection");
            jsons.append(",fisprotection:")
                    .append('"').append(fisprotection).append('"');
        }
        if (params.get("fdeleteid") != null && params.get("fdeleteid") != ""){
            String fdeleteid = MapUtils.getString(params,"fdeleteid");
            jsons.append(",fdeleteid:")
                    .append('"').append(fdeleteid).append('"');
        }
        if (params.get("likedcount") != null && params.get("likedcount") != ""){
            String likedcount = MapUtils.getString(params,"likedcount");
            jsons.append(",likedcount:")
                    .append('"').append(likedcount).append('"');
        }
        if (params.get("ficon") != null && params.get("ficon") != ""){
            String ficon = MapUtils.getString(params,"ficon");
            jsons.append(",ficon:")
                    .append('"').append(ficon).append('"');
        }
        if (params.get("fcreatorid") != null && params.get("fcreatorid") != ""){
            String fcreatorid = MapUtils.getString(params,"fcreatorid");
            jsons.append(",fcreatorid:")
                    .append('"').append(fcreatorid).append('"');
        }
        if (params.get("fpinyin") != null && params.get("fpinyin") != ""){
            String fpinyin = MapUtils.getString(params,"fpinyin");
            jsons.append(",fpinyin:")
                    .append('"').append(fpinyin).append('"');
        }
        if (params.get("fclassifiedid") != null && params.get("fclassifiedid") != ""){
            String fclassifiedid = MapUtils.getString(params,"fclassifiedid");
            jsons.append(",fclassifiedid:")
                    .append('"').append(fclassifiedid).append('"');
        }
        if (params.get("fdiseaseid") != null && params.get("fdiseaseid") != ""){
            String fdiseaseid = MapUtils.getString(params,"fdiseaseid");
            jsons.append(",fdiseaseid:")
                    .append('"').append(fdiseaseid).append('"');
        }

        if (params.get("fcode") != null && params.get("fcode") != ""){
            String fcode = MapUtils.getString(params,"fcode");
            jsons.append(",fcode:")
                    .append('"').append(fcode).append('"');
        }
        if (params.get("fname") != null && params.get("fname") != ""){
            String fname = MapUtils.getString(params,"fname");
            jsons.append(",fname:")
                    .append('"').append(fname).append('"');
        }


        if (params.get("image") != null && params.get("image") != ""){
            String image = MapUtils.getString(params,"image");
            jsons.append(",image:")
                    .append('"').append(image).append('"');
        }

        if (params.get("fouthclassifiedid") != null && params.get("fouthclassifiedid") != ""){
            String fouthclassifiedid = MapUtils.getString(params,"fouthclassifiedid");
            jsons.append(",fouthclassifiedid:")
                    .append('"').append(fouthclassifiedid).append('"');
        }
        if (params.get("fupdatorid") != null && params.get("fupdatorid") != ""){
            String fupdatorid = MapUtils.getString(params,"fupdatorid");
            jsons.append(",fupdatorid:")
                    .append('"').append(fupdatorid).append('"');
        }

        if (params.get("fstate") != null && params.get("fstate") != ""){
            String fstate = MapUtils.getString(params,"fstate");
            jsons.append(",fstate:")
                    .append('"').append(fstate).append('"');
        }
        if (params.get("fisattention") != null && params.get("fisattention") != ""){
            String fisattention = MapUtils.getString(params,"fisattention");
            jsons.append(",fisattention:")
                    .append('"').append(fisattention).append('"');
        }
        if (params.get("ftrivialid") != null && params.get("ftrivialid") != ""){
            String ftrivialid = MapUtils.getString(params,"ftrivialid");
            jsons.append(",ftrivialid:")
                    .append('"').append(ftrivialid).append('"');
        }
        if (params.get("fshapefeatureid") != null && params.get("fshapefeatureid") != ""){
            String fshapefeatureid = MapUtils.getString(params,"fshapefeatureid");
            jsons.append(",fshapefeatureid:")
                    .append('"').append(fshapefeatureid).append('"');
        }
        if (params.get("indexid") != null && params.get("indexid") != ""){
            String indexid = MapUtils.getString(params,"fstate");
            jsons.append(",indexid:")
                    .append('"').append(indexid).append('"');
        }

        if (params.get("describe") != null && params.get("describe") != ""){
            String describe = MapUtils.getString(params,"describe");
            jsons.append(",describe:")
                    .append('"').append(describe).append('"');
        }
        if (params.get("fimage") != null && params.get("fimage") != ""){
            String fimage = MapUtils.getString(params,"fimage");
            jsons.append(",fimage:")
                    .append('"').append(fimage).append('"');
        }
    }

}
