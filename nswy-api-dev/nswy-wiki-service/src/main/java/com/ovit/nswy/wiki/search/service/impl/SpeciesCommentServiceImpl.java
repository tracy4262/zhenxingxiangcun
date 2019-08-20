package com.ovit.nswy.wiki.search.service.impl;

import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.model.request.SpeciesCommentAddQuery;
import com.ovit.nswy.wiki.model.request.SpeciesCommentListQuery;
import com.ovit.nswy.wiki.search.dao.SpeciesCommentDao;
import com.ovit.nswy.wiki.search.service.SpeciesCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
@Service
public class SpeciesCommentServiceImpl implements SpeciesCommentService {

    @Autowired
    private SpeciesCommentDao speciesCommentDao;


    @Override
    public Page<Map<String, Object>> listSpeciesComment(SpeciesCommentListQuery speciesCommentListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor.setSize(speciesCommentListQuery.getPageSize());
        constructor.setFrom(speciesCommentListQuery.getPageNum());
        if(Integers.isNotEmpty(speciesCommentListQuery.getSortType())){
            if(speciesCommentListQuery.getSortType()==1){
                constructor.setAsc("update_time");
            }
            if(speciesCommentListQuery.getSortType()==2){
                constructor.setDesc("update_time");
            }
        }

        Page<Map<String, Object>> mapPage= speciesCommentDao.search(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, constructor);
        return mapPage;
    }




    @Override
    public Map<String, Object> getSpeciesComment(String indexid){
        return speciesCommentDao.getIndexOfMap(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, indexid);
    }

    @Override
    public void deleteSpeciesComment(String indexid){
        speciesCommentDao.deleteIndex(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE,indexid);
    }

    @Override
    public void updateSpeciesComment(Map params){
        if(params.get("indexid")!=null){
            params.put("update_time",System.currentTimeMillis());
            speciesCommentDao.update(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, params.get("indexid").toString(),JSONHelper.toJSON(params));
        }
    }

    @Override
    public void saveSpeciesComment(SpeciesCommentAddQuery speciesCommentAddQuery){
        speciesCommentAddQuery.setComment_time(System.currentTimeMillis());
        speciesCommentDao.createIndex(Constants.IndexConstant.SPECIESCOMMENT_INDEX,Constants.IndexConstant.SPECIESCOMMENT_TYPE,JSONHelper.toJSON(speciesCommentAddQuery));
    }



}
