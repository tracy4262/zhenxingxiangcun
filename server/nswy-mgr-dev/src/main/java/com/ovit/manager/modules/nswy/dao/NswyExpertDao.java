package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyExpert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ${huipei.x} on 2017-8-16.
 */
@MyBatisDao
public interface NswyExpertDao  extends CrudDao<NswyExpert> {

    int insert(NswyExpert record);

    int insertSelective(NswyExpert record);

    NswyExpert selectByPrimaryKey(Integer id);

    int updateApproveStatus(@Param("expertId")Integer expertId,@Param("approveStatus")Integer approveStatus);

    int updateByPrimaryKey(NswyExpert record);
    void  updateByPrimaryKeySelective(NswyExpert record);
    Integer getApproveStatus(String Account);
    void deleteByPrimaryKey(Integer id);
}
