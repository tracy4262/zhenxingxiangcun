package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.StandardMapper;
import com.ovit.nswy.member.model.StandardComment;
import com.ovit.nswy.member.service.StandardService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标准service层
 *
 * @author haoWen
 * @create 2018-03-12 15:38
 **/
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardMapper standardMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public int insertStandard(Map<String, Object> params) {
        int index = -1;
        int detail = standardMapper.insertStandardDetail(params);
        int base = standardMapper.insertStandardBase(params);
        if (detail != -1 && base != -1) {
            index = detail;
        }
        return index;

    }

    @Override
    public PageInfo<Map<String, Object>> queryForMemberCenter(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String,Object>> list = standardMapper.queryForMemberCenter(params);
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> queryStandardDetail(Map<String, Object> params) {
        Map<String, Object> detailMap = standardMapper.queryStandardDetail(params);
        //查询当前ID的下一条数据
        Map<String, Object> nextStandardInfo = standardMapper.queryNext(params);
        //查询当前ID的上一条数据
        Map<String, Object> previousStandardInfo = standardMapper.queryPrevious(params);
        detailMap.put("nextMap",nextStandardInfo);
        detailMap.put("previousMap",previousStandardInfo);
        return detailMap;
    }

    @Override
    public PageInfo<Map<String,Object>> queryStandardComment(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), 10);
        String account = MapUtils.getString(params,"account");
        Map<String,Object> maps = new HashMap<String,Object>();
        List<Map<String,Object>> list = standardMapper.queryStandardComment(params);
        for(int i=0;i<list.size();i++){
            Map<String,Object> info = list.get(i);
            String userAccount  = String.valueOf(info.get("user_account"));
            if(StringUtil.isNotEmpty(account)){
                if(account.equals(userAccount)){
                    info.put("isReply",true);
                }else{
                    info.put("isReply",false);
                }
            }else{
                info.put("isReply",false);
            }
            info.put("replyBoxShow",false);
            maps.put("account",userAccount);
            List<Map<String, Object>> loginusers= loginUserMapper.listLoginUser(maps);
            info.put("author",loginusers.get(0));
            Map<String,Object> replyfindmap = new HashMap<String,Object>();
            replyfindmap.put("id",info.get("id").toString());
            List<Map<String, Object>> replyAuthor  = standardMapper.findCommentHF(replyfindmap);
            if(replyAuthor.size()>0){
                for(int j=0;j<replyAuthor.size();j++){
                    Map<String,Object> replymap = new HashMap<String,Object>();
                    Map<String,Object> reply = replyAuthor.get(j);
                    String accounts = String.valueOf(reply.get("user_account"));
                    if(StringUtil.isNotEmpty(account)){
                        if(account.equals(accounts)){
                            info.put("isReply",true);
                        }else{
                            info.put("isReply",false);
                        }
                    }else{
                        info.put("isReply",false);
                    }
                    reply.put("replyBoxShow",false);
                    replymap.put("account",accounts);
                    List<Map<String, Object>> loginuser= loginUserMapper.listLoginUser(replymap);
                    reply.put("author",loginuser.get(0));
                    Map<String,Object> replyfindmaps = new HashMap<String,Object>();
                    replyfindmaps.put("id",info.get("id").toString());
                    List<Map<String, Object>> replyAuthors  = standardMapper.findCommentHFDetail(replyfindmaps);
                    reply.put("replyAuthor",replyAuthors.get(0));
                    list.add(reply);
                }
            }

        }

        return new PageInfo<Map<String,Object>>(list);
    }

    @Override
    public void deleteMyComment(Map<String, Object> params){
        standardMapper.deleteMyComment(params);
    }

    @Override
    public int queryStandardCommentCount(Map<String, Object> params) {
        return standardMapper.queryStandardCommentCount(params);
    }

    @Override
    public int publishComment(Map<String, Object> params) {
        return standardMapper.publishComment(params);
    }

    @Override
    public int updateBrowseNumber(Map<String, Object> params) {
        return standardMapper.updateBrowseNumber(params);
    }

    @Override
    public int queryThumbNumByDetailId(Map<String, Object> params) {
        return standardMapper.queryThumbNumByDetailId(params);
    }

    @Override
    public PageInfo<Map<String, Object>> queryDetailCommentReply(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = standardMapper.queryDetailCommentReply(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> queryForNswyNavigation(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = standardMapper.queryForNswyNavigation(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> queryForNswyHome(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = standardMapper.queryForNswyHome(params);
        return new PageInfo<>(list);
    }
}
