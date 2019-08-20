package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.InformationBookMapper;
import com.ovit.nswy.member.mapper.MemberIntroduceDetailMapper;
import com.ovit.nswy.member.mapper.MemberIntroduceMapper;
import com.ovit.nswy.member.model.Article;
import com.ovit.nswy.member.model.MemberIntroduce;
import com.ovit.nswy.member.model.MemberIntroduceDetail;
import com.ovit.nswy.member.model.VideoImg;
import com.ovit.nswy.member.service.InformationBookDetailService;
import com.ovit.nswy.member.service.MemberIntroduceService;
import com.ovit.nswy.member.service.VideoImgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MemberIntroduceServiceImpl implements MemberIntroduceService {

    private static final Logger logger = LogManager.getLogger(MemberIntroduceServiceImpl.class);

    @Autowired
    private MemberIntroduceMapper memberIntroduceMapper;

    @Autowired
    private MemberIntroduceDetailMapper memberIntroduceDetailMapper;

    @Autowired
    private InformationBookMapper informationBookMapper;

    @Autowired
    private InformationBookDetailService informationBookDetailService;

    @Autowired
    private VideoImgService videoImgService;

    @Override
    public Map<String,Object> findNswyInfo( Map<String, Object> params){


        return memberIntroduceMapper.findLoginUserInfo(params);
    }

    @Override
    public void insertIntroduceDetail(MemberIntroduceDetail detail){
        memberIntroduceDetailMapper.insert(detail);
    }

    @Override
    public void insertInfo(MemberIntroduce info) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        info.setArticleId(uuid);
        memberIntroduceMapper.insertIntroduce(info);
        Article article = new Article(3);
        article.setId(uuid);
        article.setAuthor("1");
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setTitle(info.getTitle());
        article.setKeywords(info.getLabel());
        article.setDocType(info.getDocType());
        memberIntroduceMapper.insertArticle(article);
    }


    @Override
    public List<Map<String,Object>> findMediaLibraryInfo(Map<String,Object> params){
        List<Map<String,Object>> mediaList = memberIntroduceMapper.findMediaLibraryInfo(params);

        return mediaList;
    }

    @Override
    public List<Map<String,Object>> findMediaBookDetail(Map<String,Object> params) {
        List<Map<String, Object>> bookInfo = informationBookMapper.findMediaBookInfo(params);
        if(CollectionUtils.isNotEmpty(bookInfo)){
            for(Map<String, Object> map : bookInfo){
                String label=String.valueOf(map.get("label"));
                if(StringUtil.isNotBlank(label)){
                    if (String.valueOf(map.get("label")).contains("[")) {
                        map.put("label", JSONObject.parseArray(label));
                    }else{
                        String[] labels = label.split(",");
                        map.put("label",labels);
                    }
                }else{
                    map.put("label",new String[]{});

                }

                map.put("book_info_id",MapUtils.getString(map,"id"));
                List<Map<String,Object>> bookDeatil = informationBookMapper.findMediaBookDetailInfo(map);
                List<Map<String,Object>> bookDetailDealInfo = new ArrayList<>();
                for(int i=0; i< bookDeatil.size(); i++) {
                    try{
                        Map<String,Object> ibd = bookDeatil.get(i);
                        String parentId = String.valueOf(ibd.get("parent_id"));
                        List<Map<String,Object>> children = new ArrayList<>();
                        if ("0".equals(String.valueOf(ibd.get("parent_id")))) {
                            //若parent_id为0,则说明是一个章节节点
                            String nodeId = String.valueOf(ibd.get("node_id"));
                            for (int j=i+1; j< bookDeatil.size(); j++) {
                                if (nodeId.equals(String.valueOf(bookDeatil.get(j).get("parent_id")))) {
                                    //若该节点的parent_id与上面数据的node_id一样，则说明是上面章节节点的子节点，即为图书小节数据
                                    Map<String,Object> childrenMap = bookDeatil.get(j);
                                    if (!StringUtils.isEmpty(childrenMap.get("content"))) {

                                        if (childrenMap.get("content") instanceof byte[]) {
                                            byte[] content = (byte[])childrenMap.get("content");
                                            childrenMap.put("content", new String(content, "utf-8"));
                                        } else {
                                            childrenMap.put("content", childrenMap.get("content"));
                                        }

                                    }

                                    String file = MapUtils.getString(childrenMap,"file");
                                    if(StringUtil.isBlank(file)){
                                        childrenMap.put("file",new String[]{});
                                    }

                                    children.add(bookDeatil.get(j));
                                }
                            }
                            ibd.put("children", children);
                            bookDetailDealInfo.add(ibd);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                map.put("book_data",bookDetailDealInfo);
            }
        }


        return bookInfo;
    }


    @Override
    public Map<String,Object> findMemberIntroduceInfoById(Map<String,Object> params){
        Integer id = MapUtils.getInteger(params,"id");
        Map<String,Object> member = memberIntroduceDetailMapper.findMemberIntroduceDetailById(id);

        String typeList = MapUtils.getString(member,"typeList");

        if(StringUtil.isNotEmpty(typeList)){
            JSONArray type = JSONArray.parseArray(typeList);

            member.put("typeList",type);
        }else{
            member.put("typeList",new String[]{});
        }

        String atlasList = MapUtils.getString(member,"atlasList");

        if(StringUtil.isNotEmpty(atlasList)){
            JSONArray atlas = JSONArray.parseArray(atlasList);

            member.put("atlasList",atlas);
        }else{
            member.put("atlasList",new String[]{});
        }



        //遍历视频、音频
        Map<String,Object> vmap=new HashMap<>();
        vmap.put("column","会员介绍");
        vmap.put("relativeId",id);

        List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
        if(!videoImgs.isEmpty()){
            member.put("videoImgs",videoImgs);
        }
        if(MapUtils.isNotEmpty(member)){
            if(MapUtils.getString(member,"columnType").equals("图书")){
                Map<String,Object> book = new HashMap<>();
                book.put("memberIntroduceDetailId",id);
                List<Map<String, Object>> bookInfo = informationBookMapper.findMemberIntroduceBookInfo(book);
                if(CollectionUtils.isNotEmpty(bookInfo)){
                    for(Map<String, Object> map : bookInfo){
                        String label=String.valueOf(map.get("label"));
                        if(StringUtil.isNotBlank(label)){
                            if (String.valueOf(map.get("label")).contains("[")) {
                                map.put("label", JSONObject.parseArray(label));
                            }else{
                                String[] labels = label.split(",");
                                map.put("label",labels);
                            }
                        }else{
                            map.put("label",new String[]{});

                        }

                        map.put("book_info_id",MapUtils.getString(map,"id"));
                        List<Map<String,Object>> bookDeatil = informationBookMapper.findMemberIntroduceBookDetailInfo(map);
                        List<Map<String,Object>> bookDetailDealInfo = new ArrayList<>();
                        for(int i=0; i< bookDeatil.size(); i++) {
                            try{
                                Map<String,Object> ibd = bookDeatil.get(i);
                                String parentId = String.valueOf(ibd.get("parent_id"));
                                List<Map<String,Object>> children = new ArrayList<>();
                                if ("0".equals(String.valueOf(ibd.get("parent_id")))) {
                                    //若parent_id为0,则说明是一个章节节点
                                    String nodeId = String.valueOf(ibd.get("node_id"));
                                    for (int j=i+1; j< bookDeatil.size(); j++) {
                                        if (nodeId.equals(String.valueOf(bookDeatil.get(j).get("parent_id")))) {
                                            //若该节点的parent_id与上面数据的node_id一样，则说明是上面章节节点的子节点，即为图书小节数据
                                            Map<String,Object> childrenMap = bookDeatil.get(j);
                                            if (!StringUtils.isEmpty(childrenMap.get("content"))) {

                                                if (childrenMap.get("content") instanceof byte[]) {
                                                    byte[] content = (byte[])childrenMap.get("content");
                                                    childrenMap.put("content", new String(content, "utf-8"));
                                                } else {
                                                    childrenMap.put("content", childrenMap.get("content"));
                                                }

                                            }

                                            String file = MapUtils.getString(childrenMap,"file");
                                            if(StringUtil.isBlank(file)){
                                                childrenMap.put("file",new String[]{});
                                            }

                                            children.add(bookDeatil.get(j));
                                        }
                                    }
                                    ibd.put("children", children);
                                    bookDetailDealInfo.add(ibd);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        map.put("book_data",bookDetailDealInfo);
                    }
                    member.put("book_info",bookInfo);
                }

            }
        }


        return member;
    }

    @Override
    public Map<String,Object> findMemberIntroduceInfo(Map<String,Object> params){
        Map<String,Object> memberIntroduce = memberIntroduceMapper.findMemberIntroduceInfo(params);
        if(MapUtils.isNotEmpty(memberIntroduce)){
            Integer id = MapUtils.getInteger(memberIntroduce,"member_introduce_detail_id");
            Map<String,Object> member = memberIntroduceDetailMapper.findMemberIntroduceDetail(id);
            //遍历视频、音频

            Map<String,Object> vmap=new HashMap<>();
            vmap.put("column","会员介绍");
            vmap.put("relativeId",id);

            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
            if(!videoImgs.isEmpty()){
                member.put("videoImgs",videoImgs);
            }
            if(MapUtils.getString(member,"columnType").equals("图书")){
                Map<String,Object> book = new HashMap<>();
                book.put("memberIntroduceDetailId",id);
                List<Map<String, Object>> bookInfo = informationBookMapper.findMemberIntroduceBookInfo(book);
                if(CollectionUtils.isNotEmpty(bookInfo)){
                    for(Map<String, Object> map : bookInfo){
                        String label=String.valueOf(map.get("label"));
                        if(StringUtil.isNotBlank(label)){
                            if (String.valueOf(map.get("label")).contains("[")) {
                                map.put("label", JSONObject.parseArray(label));
                            }else{
                                String[] labels = label.split(",");
                                map.put("label",labels);
                            }
                        }else{
                            map.put("label",new String[]{});

                        }

                        map.put("book_info_id",MapUtils.getString(map,"id"));
                        List<Map<String,Object>> bookDeatil = informationBookMapper.findMemberIntroduceBookDetailInfo(map);
                        List<Map<String,Object>> bookDetailDealInfo = new ArrayList<>();
                        for(int i=0; i< bookDeatil.size(); i++) {
                            try{
                                Map<String,Object> ibd = bookDeatil.get(i);
                                String parentId = String.valueOf(ibd.get("parent_id"));
                                List<Map<String,Object>> children = new ArrayList<>();
                                if ("0".equals(String.valueOf(ibd.get("parent_id")))) {
                                    //若parent_id为0,则说明是一个章节节点
                                    String nodeId = String.valueOf(ibd.get("node_id"));
                                    for (int j=i+1; j< bookDeatil.size(); j++) {
                                        if (nodeId.equals(String.valueOf(bookDeatil.get(j).get("parent_id")))) {
                                            //若该节点的parent_id与上面数据的node_id一样，则说明是上面章节节点的子节点，即为图书小节数据
                                            Map<String,Object> childrenMap = bookDeatil.get(j);
                                            if (!StringUtils.isEmpty(childrenMap.get("content"))) {

                                                if (childrenMap.get("content") instanceof byte[]) {
                                                    byte[] content = (byte[])childrenMap.get("content");
                                                    childrenMap.put("content", new String(content, "utf-8"));
                                                } else {
                                                    childrenMap.put("content", childrenMap.get("content"));
                                                }

                                            }

                                            String file = MapUtils.getString(childrenMap,"file");
                                            if(StringUtil.isBlank(file)){
                                                childrenMap.put("file","");
                                            }else if(file.equals("[]")){
                                                childrenMap.put("file","");
                                            }



                                            children.add(bookDeatil.get(j));
                                        }
                                    }
                                    ibd.put("children", children);
                                    bookDetailDealInfo.add(ibd);
                                }
                            }catch (Exception e){
                                logger.error("处理异常：{}", e);
                            }


                        }
                        map.put("book_data",bookDetailDealInfo);
                    }
                    member.put("book_info",bookInfo);
                }

            }
            memberIntroduce.put("introduceDetail",member);
        }



        return memberIntroduce;
    }


    @Override
    public void findIntroduceDelete(Map<String,Object> params){

        Map<String,Object> memberIntroduce = memberIntroduceMapper.findMemberIntroduceInfo(params);
        if(MapUtils.isNotEmpty(memberIntroduce)){

            Map<String,Object> video = new HashMap<>();
            video.put("memberIntroduceDetailId",MapUtils.getString(memberIntroduce,"member_introduce_detail_id"));
            video.put("column","会员介绍");

            memberIntroduceMapper.deleteByPrimaryKey(video);

            //删除会员介绍，及明细
            memberIntroduceMapper.deleteMemberIntroduceInfo(memberIntroduce);
            memberIntroduceMapper.deleteMemberIntroduceDetailInfo(memberIntroduce);

            Map<String,Object> book = new HashMap<>();
            book.put("memberIntroduceDetailId",MapUtils.getString(memberIntroduce,"member_introduce_detail_id"));
            List<Map<String, Object>> bookInfo = informationBookMapper.findMemberIntroduceBookInfo(book);
            if(bookInfo !=null && bookInfo.size()>0){
                for(Map<String, Object> map : bookInfo){
                    memberIntroduceMapper.deleteMemberIntroduceBookInfo(map);
                    book.put("book_info_id",MapUtils.getString(map,"id"));
                    memberIntroduceMapper.deleteMemberIntroduceBookDetailInfo(book);
                }
            }
        }

    }

}
