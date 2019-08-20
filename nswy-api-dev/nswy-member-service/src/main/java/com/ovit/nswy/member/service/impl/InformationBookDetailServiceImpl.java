package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.InformationBookDetailMapper;
import com.ovit.nswy.member.mapper.InformationBookMapper;
import com.ovit.nswy.member.service.InformationBookDetailService;
import com.ovit.nswy.member.service.InformationBookService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationBookDetailServiceImpl implements InformationBookDetailService {

    private static final Logger logger = LogManager.getLogger(InformationBookDetailServiceImpl.class);

    @Autowired
    private InformationBookDetailMapper informationBookDetailMapper;

    @Override
    public void batchInsert(List<Map<String,Object>> list, int informationDetailId, String bookType) {
        logger.trace("insert informationBookDetail params:{}", list);
        //插入之前先删除
        Map<String,Object> map = new HashMap<>();
        map.put("information_detail_id", informationDetailId);
        map.put("book_type", bookType);
        informationBookDetailMapper.deleteInformationBookDetail(map);
        informationBookDetailMapper.batchInsert(list);
    }


    @Override
    public void batchMemberIntroduceInsert(List<Map<String,Object>> list, int memberIntroduceDetailId,int book_info_id) {

        //插入之前先删除
        Map<String,Object> map = new HashMap<>();
        map.put("member_introduce_detail_id", memberIntroduceDetailId);
        map.put("book_info_id", book_info_id);
        informationBookDetailMapper.deleteMemberIntroduceBookDetail(map);
        informationBookDetailMapper.batchInsertMemberIntroduceBookInfo(list);
    }

    @Override
    public void batchInsertMediaBookInfo(List<Map<String,Object>> list, int media_id, int book_info_id) {

        //插入之前先删除
        Map<String,Object> map = new HashMap<>();
        map.put("media_id", media_id);
        map.put("book_info_id", book_info_id);

        informationBookDetailMapper.deleteMediaBookDetail(map);
        informationBookDetailMapper.batchInsertMediaBookInfo(list);
    }

    @Override
    public Map<String,Object> getInformation(Map<String,Object> informationMap) {

        logger.trace("getInformation params:{}", informationMap);
        int id = Integer.parseInt(String.valueOf(informationMap.get("information_id")));
        String bookType = String.valueOf(informationMap.get("bookType"));
        String flag = String.valueOf(informationMap.get("flag"));
        if ("information".equals(bookType)) {
            if("1".equals(flag)){
                //由于前台没有传递information_detail_id，获取information_detail_id
                Map<String,Object> information = informationBookDetailMapper.getInformation(id);
                int informationDatailId =  Integer.parseInt(String.valueOf(information.get("information_detail_id")));
                informationBookDetailMapper.updateInformationNumber(informationDatailId);
            }
            //资讯
            return informationBookDetailMapper.getInformation(id);


        } else if ("knowledge".equals(bookType)) {
            if("1".equals(flag)){
                //由于前台没有传递information_detail_id，获取information_detail_id
                Map<String,Object> information = informationBookDetailMapper.getKnowledge(id);
                int informationDatailId =  Integer.parseInt(String.valueOf(information.get("information_detail_id")));
                informationBookDetailMapper.updateKnowledgeNumber(informationDatailId);
            }
            //知识
            return informationBookDetailMapper.getKnowledge(id);

        } else if ("policy".equals(bookType)) {
            if("1".equals(flag)){
                //由于前台没有传递information_detail_id，获取information_detail_id
                Map<String,Object> information = informationBookDetailMapper.getPolicy(id);
                int informationDatailId =  Integer.parseInt(String.valueOf(information.get("information_detail_id")));
                informationBookDetailMapper.updatePolicyNumber(informationDatailId);
            }
            //政策
            return informationBookDetailMapper.getPolicy(id);

        } else {
            return null;
        }

    }

    @Override
    public Map<String, Object> findInformationByDetailID(Map<String,Object> detailMap) {
        logger.trace("findInformationByDetailID params:{}", detailMap);
        String bookType = String.valueOf(detailMap.get("bookType"));
        int informationDetailId = Integer.parseInt(String.valueOf(detailMap.get("information_detail_id")));
        if ("information".equals(bookType)) {
            //资讯
            return informationBookDetailMapper.findInformationByDetailID(informationDetailId);

        } else if ("knowledge".equals(bookType)) {
            //知识
            return informationBookDetailMapper.findKnowledgeByDetailID(informationDetailId);

        } else if ("policy".equals(bookType)) {
            //政策
            return informationBookDetailMapper.findPolicyByDetailID(informationDetailId);

        } else {
            return null;
        }
    }

    @Override
    public Map<String,Object> getInformationDetail(Map<String,Object> informationDetailMap) {
        logger.trace("getInformationDetail params:{}", informationDetailMap);
        int informationDetailId = Integer.parseInt(String.valueOf(informationDetailMap.get("information_detail_id")));
        String bookType = String.valueOf(informationDetailMap.get("bookType"));
        if ("information".equals(bookType)) {
            //资讯
            return informationBookDetailMapper.getInformationDetail(informationDetailId);

        } else if ("knowledge".equals(bookType)) {
            //知识
            return informationBookDetailMapper.getKnowledgeDetail(informationDetailId);

        } else if ("policy".equals(bookType)) {
            //政策
            return informationBookDetailMapper.getPolicyDetail(informationDetailId);

        } else {
            return null;
        }
    }

    @Override
    public Map<String,Object> getInformationBookInfo(Map<String,Object> informationDetailMap) {
        logger.trace("getInformationBookInfo params:{}", informationDetailMap);
        return informationBookDetailMapper.getInformationBookInfo(informationDetailMap);
    }

    @Override
    public Map<String, Object> getBookPartInfo(Map<String, Object> bookPartInfo) {
        logger.trace("getBookPartInfo params:{}", bookPartInfo);
        return informationBookDetailMapper.getBookPartInfo(bookPartInfo);
    }

    @Override
    public List<Map<String,Object>> getInformationBookDetail(Map<String,Object> informationDetailMap) {
        logger.trace("getInformationBookDetail params:{}", informationDetailMap);
        return informationBookDetailMapper.getInformationBookDetail(informationDetailMap);
    }

    @Override
    public void deleteInformation(Map<String,Object> informationMap) {
        logger.trace("deleteInformation params:{}", informationMap);
        int id = Integer.parseInt(String.valueOf(informationMap.get("information_id")));
        String bookType = String.valueOf(informationMap.get("bookType"));
        if ("information".equals(bookType)) {
            //资讯
            informationBookDetailMapper.deleteInformation(id);

        } else if ("knowledge".equals(bookType)) {
            //知识
            informationBookDetailMapper.deleteKnowledge(id);

        } else if ("policy".equals(bookType)) {
            //政策
            informationBookDetailMapper.deletePolicy(id);

        }

    }

    @Override
    public void deleteInformationDetail(Map<String,Object> informationDeleteMap) {
        logger.trace("deleteInformationDetail params:{}", informationDeleteMap);
        int informationDetailId = Integer.parseInt(String.valueOf(informationDeleteMap.get("information_detail_id")));
        String bookType = String.valueOf(informationDeleteMap.get("bookType"));
        if ("information".equals(bookType)) {
            //资讯
            informationBookDetailMapper.deleteInformationDetail(informationDetailId);

        } else if ("knowledge".equals(bookType)) {
            //知识
            informationBookDetailMapper.deleteKnowledgeDetail(informationDetailId);

        } else if ("policy".equals(bookType)) {
            //政策
            informationBookDetailMapper.deletePolicyDetail(informationDetailId);

        }

    }

    @Override
    public void deleteInformationBookInfo(Map<String,Object> informationDeleteMap) {
        logger.trace("deleteInformationBookInfo params:{}", informationDeleteMap);
        informationBookDetailMapper.deleteInformationBookInfo(informationDeleteMap);
    }

    @Override
    public void deleteInformationBookDetail(Map<String,Object> informationDeleteMap) {
        logger.trace("deleteInformationBookDetail params:{}", informationDeleteMap);
        informationBookDetailMapper.deleteInformationBookDetail(informationDeleteMap);
    }

}
