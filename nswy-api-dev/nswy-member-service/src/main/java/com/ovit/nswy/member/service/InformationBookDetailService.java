package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface InformationBookDetailService {

    /**
     * 批量插入资讯-图书信息
     * @param map
     */
    void batchInsert(List<Map<String,Object>> map, int informationDetailId, String bookType);

    void batchInsertMediaBookInfo(List<Map<String,Object>> list, int media_id, int book_info_id);

    void batchMemberIntroduceInsert(List<Map<String,Object>> list, int memberIntroduceDetailId,int book_info_id);

    /**
     *根据information_id查询information表的数据
     * @param informationMap
     */
    Map<String,Object> getInformation(Map<String,Object> informationMap);

    /**
     * 根据information_detail_id查询information表的数据
     * @param detailMap
     */
    Map<String, Object> findInformationByDetailID(Map<String,Object> detailMap);

    /**
     * 根据information_detail_id查询information_detail表的数据
     * @param informationDetailMap
     */
    Map<String,Object> getInformationDetail(Map<String,Object> informationDetailMap);

    /**
     *根据information_detail_id查询information_book_info表的数据
     * @param informationDetailMap
     */
    Map<String,Object> getInformationBookInfo(Map<String,Object> informationDetailMap);

    /**
     *根据information_detail_id和node_id查询图书小节相关信息
     * @param bookPartInfo
     */
    Map<String, Object> getBookPartInfo(Map<String,Object> bookPartInfo);

    /**
     *根据information_detail_id查询information_book_detail表的数据
     * @param informationDetailMap
     */
    List<Map<String,Object>> getInformationBookDetail(Map<String,Object> informationDetailMap);

    /**
     * 根据information_id删除information中相关信息
     * @param informationMap
     */
    void deleteInformation(Map<String,Object> informationMap);

    /**
     * 根据information_detail_id删除information_detail中相关信息
     * @param informationDeleteMap
     */
    void deleteInformationDetail(Map<String,Object> informationDeleteMap);

    /**
     * 根据information_detail_id删除information_book_info中相关信息
     * @param informationDeleteMap
     */
    void deleteInformationBookInfo(Map<String,Object> informationDeleteMap);

    /**
     * 根据information_detail_id删除information_book_detail中相关信息
     * @param informationDeleteMap
     */
    void deleteInformationBookDetail(Map<String,Object> informationDeleteMap);




}
