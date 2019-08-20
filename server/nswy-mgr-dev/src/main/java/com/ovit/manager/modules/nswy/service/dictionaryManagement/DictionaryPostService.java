package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryPostDao;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryUnitDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPost;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictionaryPostService extends CrudService<DictionaryPostDao, DictionaryPost> {

    @Autowired
    private DictionaryPostDao dictionaryPostDao;

//    public Page<DictionaryPost> findList(DictionaryPost dictionaryPost, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryPost);
//    }

    public List<DictionaryPost> findList(DictionaryPost dictionaryPost){
        return dictionaryPostDao.findList(dictionaryPost);
    }

    public List<DictionaryPost> findPostList(DictionaryPost dictionaryPost){

        List<DictionaryPost> dictionaryPosts = dictionaryPostDao.findList(dictionaryPost);

        return dictionaryPosts ;
    }

    public void savePost(DictionaryPost dictionaryPost){
            dictionaryPostDao.savePost(dictionaryPost);
    }

    public void updatePost(DictionaryPost dictionaryPost){
            dictionaryPostDao.updatePost(dictionaryPost);
    }

    public void deletePost(String id){
            dictionaryPostDao.deletePost(id);
    }


}
