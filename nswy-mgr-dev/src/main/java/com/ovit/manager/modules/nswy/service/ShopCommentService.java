package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ShopCommentDao;
import com.ovit.manager.modules.nswy.dao.ShopCommodityDao;
import com.ovit.manager.modules.nswy.entity.ShopComment;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-01 10:30
 **/
@Service
@Transactional
public class ShopCommentService extends CrudService<ShopCommentDao, ShopComment> {
    @Autowired
    private ShopCommentDao shopCommentDao;

    public Page<ShopComment> findInfoList(ShopComment shopComment, HttpServletRequest request, HttpServletResponse response) {
        Page<ShopComment> page = this.findPage(new Page<>(request,response),shopComment);
        return  page;
    }


    /**
     * 审核
     * @param params
     */
    public void adopt(Map<String,Object> params) {
        shopCommentDao.adopt(params);
    }




}
