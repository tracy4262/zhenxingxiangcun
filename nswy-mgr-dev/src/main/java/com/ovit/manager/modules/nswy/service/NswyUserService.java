package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.NswyUserDao;
import com.ovit.manager.modules.nswy.entity.NswyUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**用户service
 * Created by ${huipei.x} on 2017-8-14.
 */
@Service
@Transactional(readOnly = true)
public class NswyUserService extends CrudService<NswyUserDao, NswyUser>{

    @Autowired
    private NswyUserDao nswyUserDao;

    public Page<NswyUser> findUserList(NswyUser userDTO, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyUser> page = this.findPage(new Page<NswyUser>(request, response), userDTO);
        if(null !=page.getList()&&page.getList().size()>0) {
            for (NswyUser nswyUser : page.getList()) {
                if(nswyUser.getRegisterTime() != null){
                    String time = DateUtils.formatDate(nswyUser.getRegisterTime());
                    nswyUser.setTime(time);
                }
            }
        }
        return page;
    }

    public NswyUser getUser(Integer userId){
       return  nswyUserDao.getUser(userId);
    }

    @Transactional()
    public void saveOrUpdateUser(NswyUser nswyUser){
        if(nswyUser.getUserId()!=null){
            nswyUserDao.updateUser(nswyUser);
        }
    }

    @Transactional()
    public void deleteUser(Integer userId){
          nswyUserDao.delete(userId);
    }
}
