package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.wiki.model.request.SpeciesCommentAddQuery;
import com.ovit.nswy.wiki.model.request.SpeciesCommentListQuery;


import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
public interface SpeciesCommentService {
    public Page<Map<String, Object>> listSpeciesComment(SpeciesCommentListQuery speciesCommentListQuery);
    public Map<String, Object> getSpeciesComment(String indexid);
    public void deleteSpeciesComment(String indexid);
    public void updateSpeciesComment(Map params);
    public void saveSpeciesComment(SpeciesCommentAddQuery speciesCommentAddQuery);
}
