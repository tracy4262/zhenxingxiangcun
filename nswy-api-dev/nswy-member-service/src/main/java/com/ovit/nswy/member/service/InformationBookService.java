package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface InformationBookService {

    public void insert(Map<String, Object> map);

    public Map<String, Object> getInformationBookInfoByDetailId(int detaild);

    public List<Map<String, Object>> getInformationBookInfoList();

    void insertMemberIntroduceBook(Map<String, Object> map);


}
