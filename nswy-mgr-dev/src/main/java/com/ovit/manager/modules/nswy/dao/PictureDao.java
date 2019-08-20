package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import java.util.Map;

@MyBatisDao
public interface PictureDao extends CrudDao<PictureInfo> {
    void savePicture(Map<String, Object> map);

    void pictureSetOrderNum(Map<String, Object> map);

    void deletePicture(int pictureId);

    void pictureUpdateDisplay(Map<String, Object> map);

    void pictureUseInformation(Map<String, Object> map);

    void pictureUseHome(Map<String, Object> map);

    void pictureProduct(Map<String, Object> map);
}
