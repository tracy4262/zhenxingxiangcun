package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NewsletterInfo;
import java.util.Map;

@MyBatisDao
public interface NewsletterDao extends CrudDao<NewsletterInfo> {
    void newsletterUpdateNavDisplay(Map<String, Object> map);

    void deleteNewsletter(Map<String, Object> map);

    void newsletterSetOrderNum(Map<String, Object> map);
}
