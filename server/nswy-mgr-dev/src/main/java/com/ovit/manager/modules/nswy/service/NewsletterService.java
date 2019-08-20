package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NewsletterDao;
import com.ovit.manager.modules.nswy.entity.NewsletterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 简讯
 *
 * @author haoWen
 * @create 2018-02-08 10:48
 **/
@Service
@Transactional
public class NewsletterService extends CrudService<NewsletterDao, NewsletterInfo> {
    @Autowired
    private NewsletterDao newsletterDao;

    public Page<NewsletterInfo> findKnowledgeList(NewsletterInfo newsletterInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),newsletterInfo);
    }

    public void newsletterUpdateNavDisplay(Map<String, Object> map) {
        newsletterDao.newsletterUpdateNavDisplay(map);
    }

    public void deleteNewsletter(Map<String, Object> map) {
        newsletterDao.deleteNewsletter(map);
    }

    public void newsletterSetOrderNum(Map<String, Object> map) {
        newsletterDao.newsletterSetOrderNum(map);
    }
}
