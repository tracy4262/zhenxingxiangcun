package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.member.mapper.InformationBookMapper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.MediaLibraryDetail;
import com.ovit.nswy.member.model.request.MediaLibraryDetailListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryDetailQuery;
import com.ovit.nswy.member.model.response.MediaDetailDTO;
import com.ovit.nswy.member.model.response.MediaLibraryDetaiDTO;
import com.ovit.nswy.member.service.InformationBookDetailService;
import com.ovit.nswy.member.service.InformationBookService;
import com.ovit.nswy.member.service.MediaLibraryDetailService;
import com.ovit.nswy.member.util.Image2Binary;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MediaLibraryDetailServiceImpl extends BaseService<MediaLibraryDetail> implements MediaLibraryDetailService {

    @Autowired
    private InformationBookMapper informationBookMapper;

    @Autowired
    private InformationBookDetailService informationBookDetailService;

    private Logger logger = LogManager.getLogger(MediaLibraryDetailServiceImpl.class);

    @Override
    public Integer saveMediaLibraryDetail(MediaLibraryDetailQuery mediaLibraryDetailQuery) {
        LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        List<MediaLibraryDetail> mdetailList = Lists.newArrayList();
        if (StringUtil.isNotBlank(mediaLibraryDetailQuery.getMediaUrl())) {
            List<MediaDetailDTO> stringList = JSONHelper.parseArray(mediaLibraryDetailQuery.getMediaUrl(), MediaDetailDTO.class);
            if(Lists.isNotEmpty(stringList)){
                mdetailList=stringList.stream().map(mediaDetailDTO->{
                    MediaLibraryDetail mediaLibraryDetail = BeanByRefMapper.copyByRefMapper(mediaLibraryDetailQuery, MediaLibraryDetail.class);
                    mediaLibraryDetail.setCreateTime(new Date());
                    if (ObjectUtil.isNotEmpty(loginUser)) {
                        mediaLibraryDetail.setAccount(loginUser.getLoginAccount());
                    }
                    mediaLibraryDetail.setMediaUrl(mediaDetailDTO.getUrl());
                    mediaLibraryDetail.setName(mediaDetailDTO.getName());
                    return mediaLibraryDetail;
                }).collect(Collectors.toList());
            }
        }
        Integer result= save(mdetailList);
        return Integers.isEmpty(result)?0:result;
    }


    @Override
    public void saveBookInfo(Map<String,Object> params){
        //先删除，在更新
        if(MapUtils.getString(params,"flag").equals("1")){
            Map<String,Object> map = new HashMap<>();
            map.put("media_id",MapUtils.getString(params,"media_id"));
            map.put("book_info_id",MapUtils.getString(params,"book_info_id"));
            //informationBookMapper.deleteMedia(map);
            informationBookMapper.deleteMediaBookInfo(map);
            informationBookMapper.deleteMediaBookDetail(map);
        }

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("mediaId", MapUtils.getString(params, "media_id"));
        bookMap.put("coverPhoto", MapUtils.getString(params, "book_cover_photo"));
        bookMap.put("bookFullPhoto", MapUtils.getString(params, "book_full_photo"));
        bookMap.put("author", MapUtils.getString(params, "author"));
        bookMap.put("summary", MapUtils.getString(params, "summary"));
        bookMap.put("source", MapUtils.getString(params, "source"));
        bookMap.put("edition", MapUtils.getString(params, "book_edition"));
        bookMap.put("publish", MapUtils.getString(params, "book_publish"));
        bookMap.put("sheet", MapUtils.getString(params, "book_sheet"));
        bookMap.put("distribution", MapUtils.getString(params, "book_distribution"));
        bookMap.put("broadsheet", MapUtils.getString(params, "book_broadsheet"));
        bookMap.put("printTime", MapUtils.getString(params, "book_print_time"));
        bookMap.put("wordCount", MapUtils.getString(params, "book_word_count"));
        bookMap.put("pubDate", MapUtils.getString(params, "book_pub_date"));
        bookMap.put("paper", MapUtils.getString(params, "book_paper"));
        bookMap.put("label", MapUtils.getString(params, "book_label"));
        bookMap.put("title", MapUtils.getString(params, "title"));
        bookMap.put("speciesId", MapUtils.getString(params, "speciesId"));
        bookMap.put("species", MapUtils.getString(params, "species"));
        bookMap.put("products", MapUtils.getString(params, "products"));
        bookMap.put("productsId", MapUtils.getString(params, "productsId"));
        bookMap.put("service", MapUtils.getString(params, "service"));
        bookMap.put("serviceId", MapUtils.getString(params, "serviceId"));
        bookMap.put("industryName", MapUtils.getString(params, "industryName"));
        bookMap.put("industryId", MapUtils.getString(params, "industryId"));
        bookMap.put("district", MapUtils.getString(params, "district"));

        informationBookMapper.insertMediaBook(bookMap);

        //存储图书详情
        int book_info_id = Integer.parseInt(String.valueOf(bookMap.get("ID")));
        List<Map<Object,Object>> bookDetails = JSONHelper.parseObject(params.get("book_data").toString(), List.class);

        int count = 0;
        if (bookDetails != null && bookDetails.size()>0) {
            //图书的章节数量
            int ChapterSize = bookDetails.size();
            List<Map<String,Object>> saveBookDetailList = new ArrayList<>();

            for (int i = 0; i< ChapterSize ; i++ ) {
                //每章图书的信息
                Map<Object,Object> chapterInfo = bookDetails.get(i);

                JSONArray jsonArray = (JSONArray) chapterInfo.get("children");

                //添加每章的信息
                String expand = String.valueOf(chapterInfo.get("expand"));
                String title = String.valueOf(chapterInfo.get("title"));
                String edit = String.valueOf(chapterInfo.get("edit"));
                String checked = String.valueOf(chapterInfo.get("checked"));
                int node_id = ++count;
                int order_id = 0;
                int parent_id = 0;

                //保存图书每章的信息
                Map<String,Object> saveChapterInfo = new HashMap<>();
                saveChapterInfo.put("media_id",MapUtils.getString(params, "media_id"));
                saveChapterInfo.put("book_info_id",book_info_id);
                saveChapterInfo.put("title",title);
                saveChapterInfo.put("edit",edit);
                saveChapterInfo.put("checked",checked);
                saveChapterInfo.put("expand",expand);
                saveChapterInfo.put("node_id",node_id);
                saveChapterInfo.put("parent_id",parent_id);
                saveChapterInfo.put("order_id",order_id);
                saveChapterInfo.put("content","");
                saveChapterInfo.put("file","");
                saveChapterInfo.put("file_name","");

                saveBookDetailList.add(saveChapterInfo);

                //章节的node_id
                int chapter_id = count;
                //每章的小节数量
                int partSize = jsonArray.size();
                for (int j=0; j<partSize; j++) {

                    //每章图书的信息
                    JSONObject partInfo = (JSONObject) jsonArray.get(j);

                    String bookContent = "";
                    String file = "";

                    //图书每节的信息
                    title = String.valueOf(partInfo.get("title"));
                    edit = String.valueOf(partInfo.get("edit"));
                    checked = String.valueOf(partInfo.get("checked"));
                    node_id = ++count;
                    parent_id = chapter_id;
                    order_id = j+1;
                    bookContent =  String.valueOf(partInfo.get("content"));
                    String file_name = "";
                    String file_url = "";
                    JSONArray bookFile = (JSONArray) partInfo.get("file");
                    if (bookFile.size() > 0) {
                        JSONObject jsonObject = (JSONObject) bookFile.get(0);
                        file_name = jsonObject.getString("name");
                        file_url = jsonObject.getString("src");
                    }


                    //保存图书信息
                    Map<String,Object> savePartInfo = new HashMap<>();
                    savePartInfo.put("media_id",MapUtils.getString(params, "media_id"));
                    savePartInfo.put("book_info_id",book_info_id);
                    savePartInfo.put("title",title);
                    savePartInfo.put("edit",edit);
                    savePartInfo.put("checked",checked);
                    savePartInfo.put("expand",expand);
                    savePartInfo.put("node_id",node_id);
                    savePartInfo.put("parent_id",parent_id);
                    savePartInfo.put("order_id",order_id);
                    savePartInfo.put("content",bookContent.getBytes(StandardCharsets.UTF_8));
                    savePartInfo.put("file",file_url);
                    savePartInfo.put("file_name",file_name);

                    saveBookDetailList.add(savePartInfo);

                }
            }
            //批量插入图书信息,先删除，后插入
            informationBookDetailService.batchInsertMediaBookInfo(saveBookDetailList, MapUtils.getInteger(params, "media_id"), book_info_id);

        }
    }

    @Override
    public PageInfo<Map<String,Object>> findMediaBookList(Map<String,Object> params) {
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
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

                /*String district=String.valueOf(map.get("district"));
                if(StringUtil.isNotBlank(district)){
                    if (String.valueOf(map.get("district")).contains("[")) {
                        map.put("district", JSONObject.parseArray(label));
                    }else{
                        String[] districts = label.split(",");
                        map.put("district",districts);
                    }
                }else{
                    map.put("district",new String[]{});

                }*/
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


        return new PageInfo<>(bookInfo);
    }


    public static File getFileByUrl(String url) throws UnknownHostException, IOException {
        File tmpFile = File.createTempFile("temp", ".tmp");//创建临时文件
        Image2Binary.toBDFile(url, tmpFile.getCanonicalPath());
        return tmpFile;
    }

    /**
     * 获取时长
     */
/*    public static long getDuration(File file) throws EncoderException{
        MultimediaInfo m = new Encoder().getInfo(file);
        return m.getDuration();
    }*/




    @Override
    public PageInfo<MediaLibraryDetaiDTO> listMediaLibraryDetail(MediaLibraryDetailListQuery mediaLibraryDetailListQuery) {
        Example example = new Example(MediaLibraryDetail.class);
        example.setOrderByClause("create_time DESC");
        LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        if (StringUtil.isNotBlank(mediaLibraryDetailListQuery.getAccount())) {
            example.createCriteria().andEqualTo("account", mediaLibraryDetailListQuery.getAccount());
        }
        if (Integers.isNotEmpty(mediaLibraryDetailListQuery.getMediaId())) {
            example.createCriteria().andEqualTo("mediaId", mediaLibraryDetailListQuery.getMediaId());
        }
        PageHelper.startPage(mediaLibraryDetailListQuery.getPageNum(), mediaLibraryDetailListQuery.getPageSize());
        List<MediaLibraryDetail> rowsList = this.findByExample(example);
        PageInfo<MediaLibraryDetail> origPageInfo = new PageInfo(rowsList);
        List<MediaLibraryDetaiDTO> list = Lists.newArrayList();
        list = BeanByRefMapper.copyByRefListMapper(rowsList, MediaLibraryDetaiDTO.class);
        /*for(MediaLibraryDetaiDTO mdeia:list){
            String name = mdeia.getName();

            if(name.indexOf(".avi")!=-1||name.indexOf(".mp4")!=-1||name.indexOf(".mkv")!=-1||name.indexOf(".rmvb")!=-1||name.indexOf(".kux")!=-1){
                String vtime;
                try{
                    File source = new File(mdeia.getMediaUrl());
                    MultimediaInfo m = new Encoder().getInfo(source);
                    long ls = m.getDuration();
                    if ((ls / 60000) < 10) {
                        vtime = "0" + ls / 60000 + ":" + ((ls / 1000) - ((ls / 60000) * 60));

                    } else if ((ls / 60000) < 10 && ((ls / 1000) - ((ls / 60000) * 60)) < 10) {
                        vtime = "0" + ls / 60000 + ":" + "0" + ((ls / 1000) - ((ls / 60000) * 60));

                    } else if ((ls / 60000) > 9 && ((ls / 1000) - ((ls / 60000) * 60)) < 10) {
                        vtime = ls / 60000 + ":" + "0" + ((ls / 1000) - ((ls / 60000) * 60));

                    } else {
                        vtime = +ls / 60000 + ":" + ((ls / 1000) - ((ls / 60000) * 60));
                    }
                    mdeia.setTime(vtime);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }



        }*/
        PageInfo<MediaLibraryDetaiDTO> pageInfo = new PageInfo(list);
        pageInfo = PageInfoHelper.getPageInfo(origPageInfo, pageInfo);
        return pageInfo;
    }

    @Override
    public Integer countMediaLibraryDetail(Integer mediaId) {
        Example example = new Example(MediaLibraryDetail.class);
        if (Integers.isNotEmpty(mediaId)) {
            example.createCriteria().andEqualTo("mediaId", mediaId);
        }
        Integer result= this.count(example);
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    public MediaLibraryDetaiDTO gettMediaDetail(Integer id) {
        MediaLibraryDetail mediaLibraryDetail=null;
        MediaLibraryDetaiDTO mediaLibraryDetaiDTO=new MediaLibraryDetaiDTO();
        if(Integers.isNotEmpty(id)){
             mediaLibraryDetail = this.findById(id);
            mediaLibraryDetaiDTO=BeanByRefMapper.copyByRefMapper(mediaLibraryDetail, MediaLibraryDetaiDTO.class);
        }

        return mediaLibraryDetaiDTO;
    }

    @Override
    public Integer deleteMediaLibraryDetail(Integer id) {
        Integer result=null;
        if(Integers.isNotEmpty(id)){
            result= deleteById(id);
        }
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    public Integer updateMediaLibraryDetail(MediaLibraryDetailQuery mediaLibraryDetailQuery) {
        MediaLibraryDetail mediaLibraryDetail = new MediaLibraryDetail();
        mediaLibraryDetail = BeanByRefMapper.copyByRefMapper(mediaLibraryDetailQuery, MediaLibraryDetail.class);
        mediaLibraryDetail.setUpdateTime(new Date());
        Integer result=updateSelective(mediaLibraryDetail);
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMediaBook(Integer id) {

        //删除单个图书，media_library_book_info中数据删除，通过id
        informationBookMapper.deleteBookById(id);
        //删除图书内容，media_library_book_detail中数据删除，通过book_info_id
        informationBookMapper.deleteBookDetailById(id);

    }

    /**
     * 对象转Byte数组
     * @param obj
     * @return
     */
    private byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            logger.error("objectToByteArray failed, " + e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    logger.error("close objectOutputStream failed, " + e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    logger.error("close byteArrayOutputStream failed, " + e);
                }
            }

        }
        return bytes;
    }

}
