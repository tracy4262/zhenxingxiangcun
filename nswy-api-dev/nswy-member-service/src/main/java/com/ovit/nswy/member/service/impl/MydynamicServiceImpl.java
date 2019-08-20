package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.MyDyNamicMapper;
import com.ovit.nswy.member.mapper.MyFollowMapper;
import com.ovit.nswy.member.mapper.MydynamicCommentMapper;
import com.ovit.nswy.member.model.MyDyNamic;
import com.ovit.nswy.member.model.MyFollow;
import com.ovit.nswy.member.model.MydynamicComment;
import com.ovit.nswy.member.service.MydynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MydynamicServiceImpl implements MydynamicService {

    @Autowired
    private MyDyNamicMapper myDyNamicMapper;

    @Autowired
    private MydynamicCommentMapper mydynamicCommentMapper;

    @Autowired
    private MyFollowMapper myFollowMapper;

    @Override
    public void saveMyDyNamic(MyDyNamic info) {
        myDyNamicMapper.insert(info);
    }

    @Override
    public MyDyNamic updateMyDyNamic(Integer id, int thumbUpNum) {
        MyDyNamic info = myDyNamicMapper.findThumbUpNum(String.valueOf(id));
        if (info != null) {
            info.setThumbUpNum(thumbUpNum);
            myDyNamicMapper.updateThumbUpNum(info);
            return info;
        }
        return null;
    }

    /**
     * 回复
     *
     * @param info
     */
    @Override
    public void saveMyDyNamicComment(MydynamicComment info) {
        mydynamicCommentMapper.insert(info);
    }

    @Override
    public PageInfo<MyFollow> findFollowInforMation(int pageNum, int pageSize, String account) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyFollow> list = myFollowMapper.findFollowInformation(account);
        return new PageInfo<MyFollow>(list);
    }

    @Override
    public PageInfo<MyFollow> findFollowKnowLedge(int pageNum, int pageSize, String account) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyFollow> list = myFollowMapper.findFollowKnowledge(account);
        return new PageInfo<MyFollow>(list);
    }

    @Override
    public PageInfo<MyFollow> findFollowPolicy(int pageNum, int pageSize, String account) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyFollow> list = myFollowMapper.findFollowPolicy(account);
        return new PageInfo<MyFollow>(list);
    }

    @Override
    public PageInfo<MyDyNamic> findMyDyNamic(int pageNum, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyDyNamic> list = myDyNamicMapper.findMyDyNamic(map);
        return new PageInfo<MyDyNamic>(list);
    }

    @Override
    public PageInfo<MyDyNamic> findMyDyNamicAsc(int pageNum, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyDyNamic> list = myDyNamicMapper.findMyDyNamicAsc(map);
        return new PageInfo<MyDyNamic>(list);
    }

}
