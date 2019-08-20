package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserFullInfoMapper;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.service.UserFullInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFullInfoServiceImpl implements UserFullInfoService {

    @Autowired
    private UserFullInfoMapper userFullInfoMapper;

    @Override
    public UserFullInfo findUserFullInfo(UserFullInfo user) {
        return userFullInfoMapper.findUserFullInfo(user);
    }

    @Transactional
    @Override
    public int updateUserFullInfo(UserFullInfo user) {
        if(!(user.getBasic() == null &&
                user.getBasic1() == null &&
                user.getContract() == null &&
                user.getContract1() == null &&
                user.getFarmlan() == null &&
                user.getFarmlan1() == null &&
                user.getEducation() == null &&
                user.getEducation1() == null &&
                user.getHonor() == null &&
                user.getHonor1() == null &&
                user.getPolicial() == null &&
                user.getPolicial1() == null &&
                user.getReligion() == null &&
                user.getReligion1() == null &&
                user.getWork() == null &&
                user.getWork() == null)
                ){
            return userFullInfoMapper.updateUserFullInfo(user);
        }

        return 0;
    }

    @Transactional
    @Override
    public int insert(UserFullInfo user) {
        return userFullInfoMapper.insert(user);
    }
}
