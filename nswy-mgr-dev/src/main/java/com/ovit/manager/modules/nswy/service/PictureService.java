package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.PictureDao;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 图片管理service
 *
 * @author haoWen
 * @create 2018-02-09 10:35
 **/
@Service
@Transactional
public class PictureService extends CrudService<PictureDao, PictureInfo> {

    @Autowired
    private PictureDao pictureDao;

    public Page<PictureInfo> findKnowledgeList(PictureInfo pictureInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),pictureInfo);
    }

    public void savePicture(Map<String, Object> map) {
        pictureDao.savePicture(map);
    }

    public void pictureSetOrderNum(Map<String, Object> map) {
        pictureDao.pictureSetOrderNum(map);
    }

    public void deletePicture(int pictureId) {
        pictureDao.deletePicture(pictureId);
    }

    public void pictureUpdateDisplay(Map<String, Object> map) {
        pictureDao.pictureUpdateDisplay(map);
    }

    public void pictureUseHome(Map<String, Object> map) {
        pictureDao.pictureUseHome(map);
    }

    public void pictureUseInformation(Map<String, Object> map) {
        pictureDao.pictureUseInformation(map);
    }

    public void pictureProduct(Map<String, Object> map) {
        pictureDao.pictureProduct(map);
    }
}
