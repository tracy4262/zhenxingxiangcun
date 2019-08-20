package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.PostDao;
import com.ovit.manager.modules.nswy.dao.UnitDao;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangrongf
 * @create 2018-02-09 10:35
 **/
@Service
@Transactional
public class PostService extends CrudService<PostDao, PostInfo> {

    @Autowired
    private PostDao postDao;

    /*public Page<PostInfo> findPostInfo(PostInfo postInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),postInfo);
    }*/

    public List<PostInfo> findPostInfo(PostInfo postInfo){
        return postDao.findList(postInfo);
    }

    public void savePost(PostInfo postInfo){
        postDao.savePost(postInfo);
    }



    public void updatePost(PostInfo postInfo){
        postDao.updatePost(postInfo);
    }



    public void deletePost(String id){
        postDao.deletePost(id);
    }

}
