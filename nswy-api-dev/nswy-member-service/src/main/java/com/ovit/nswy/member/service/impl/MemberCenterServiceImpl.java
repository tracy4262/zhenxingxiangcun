package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.MyFavoriteMapper;
import com.ovit.nswy.member.mapper.SignInMapper;
import com.ovit.nswy.member.model.SignIn;
import com.ovit.nswy.member.service.MemberCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCenterServiceImpl implements MemberCenterService {

    @Autowired
    private MyFavoriteMapper myfavoriteMapper;

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public Integer findMyMessage(String userId) throws Exception {
        Integer count = myfavoriteMapper.findMyMessage(userId);
        return count;
    }

    @Override
    public Integer insertSignIn(String account) {
        SignIn signIn = new SignIn();
        signIn.setAccount(account);
        Integer count = signInMapper.insert(signIn);
        return count;
    }

    @Override
    public Integer findCountSignIn(String account) {
        Integer signNum = signInMapper.findCountSignIn(account);
        return signNum;
    }
}
