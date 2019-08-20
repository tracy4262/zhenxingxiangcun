package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface BusinessOutletsService {



    Map<String,Object> findBusinessOutletsInfo(Map<String, Object> params);

   void deleteBusinessOutlets(Map<String, Object> params);

    void saveOrUpdateBusinessOutlets(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);
}
