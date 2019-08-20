package com.ovit.manager.modules.nswy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.NswyJuBaoDao;
import com.ovit.manager.modules.nswy.entity.NswyJuBao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 举报 service
 * Created by ${huipei.x} on 2017-8-17.
 */
@Service
@Transactional(readOnly = true)
public class NswyJuBaoService extends CrudService<NswyJuBaoDao, NswyJuBao> {

    @Autowired
    private NswyJuBaoDao nswyJuBaoDao;

    public Page<NswyJuBao> findJuBaolist(NswyJuBao nswyJuBao, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyJuBao> page = this.findPage(new Page<>(request, response), nswyJuBao);
        if(null !=page.getList()&&page.getList().size()>0){
            for(NswyJuBao juBao:page.getList()){
                if(juBao.getCreateTime() !=null){
                    String time= DateUtils.formatDate(juBao.getCreateTime());
                    juBao.setTime(time);
                }
            }
        }
        return page;
    }

    public NswyJuBao getJuBaoById(Integer expertId){
        return  nswyJuBaoDao.getByPrimaryKey(expertId);
    }

    @Transactional()
    public void deleteJuBao(Integer expertId){
        nswyJuBaoDao.deleteByPrimaryKey(expertId);
    }

    @Transactional()
    public void updateJuBao(NswyJuBao nswyJuBao){
        if(nswyJuBao.getJuBaoId()!=null){
            nswyJuBaoDao.updateByPrimaryKeySelective(nswyJuBao);
        }
    }
}
