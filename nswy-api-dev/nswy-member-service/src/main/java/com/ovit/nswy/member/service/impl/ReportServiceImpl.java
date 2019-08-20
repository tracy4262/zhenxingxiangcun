package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.*;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private JuBaoMapper juBaoMapper;
    @Autowired
    private MyNoTitleMapper myNoTitleMapper;
    @Autowired
    private MyFriendMapper myFriendMapper;
    @Autowired
    private MyFavoriteMapper myFavoriteMapper;
    @Autowired
    private MyFollowMapper myFollowMapper;

    private Logger logger = LogManager.getLogger(ReportServiceImpl.class);


    @Override
    public void insert(JuBao report) {
        juBaoMapper.insert(report);
    }

    @Override
    public JuBao findReportByLinkAndAccount(JuBao report) {
        return juBaoMapper.selectByLinkAndAccount(report);
    }

    @Override
    public MyNoTitle findMyTitle(MyNoTitle info) {
        return myNoTitleMapper.findMyTitle(info);
    }

    @Override
    public Integer findMyFriend(MyFriend info) {
        List<MyFriend> list = myFriendMapper.findMyFriend(info);
        return list.size();
    }

    @Override
    public List<MyFavorite> findCollect(MyFavorite info) {
        return myFavoriteMapper.findCollect(info);
    }

    @Override
    public PageInfo<Map<String,Object>> findCollects(Map<String, Object> params) {
        List<Map<String,Object>> list = null;
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        list = myFavoriteMapper.findCollectContent(params);
        return new PageInfo<>(list);
    }

    @Override
    public String saveCollect(MyFavorite info) {
        List<MyFavorite> myFavorite = myFavoriteMapper.findCollect(info);
        if (myFavorite.size() > 0) {
            return "message";
        } else {
            myFavoriteMapper.insert(info);
            return "success";
        }
    }

    @Override
    public String saveFollow(MyFollow info) {
        List<MyFollow> myFavorite = myFollowMapper.findMyFollow(info);
        if (myFavorite.size() > 0) {
            return "message";
        } else {
            myFollowMapper.insertMyFollow(info);
            return "success";
        }
    }

    public void delFavorite(MyFavorite info) {
        myFavoriteMapper.delMyFavorite(info);
    }

    public void updateMyFavorite(MyFavorite record) {
        myFavoriteMapper.updateMyFavorite(record);
    }

    @Override
    public void delFollow(Map<String, Object> params) {
        try {
            myFavoriteMapper.delFollow(Integer.parseInt(String.valueOf(params.get("id"))));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public boolean isExist(MyFavorite record) {
        List<MyFavorite> list = myFavoriteMapper.findCollect(record);
        if (list.isEmpty())
            return false;
        return true;
    }

    @Override
    public void updateCollect(MyFavorite myFavorite) {
        myFavoriteMapper.updateCollect(myFavorite);
    }
}
