package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.VideoImgMapper;
import com.ovit.nswy.member.model.VideoImg;
import com.ovit.nswy.member.service.VideoImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoImgServiceImpl implements VideoImgService {
    @Autowired
    private VideoImgMapper mapper;
    @Override
    public int insert(List<VideoImg> list) {
        return mapper.insert(list);
    }

    @Override
    public List<VideoImg> selectByRelativeId(Map<String ,Object> map) {
        return mapper.selectByRelativeId(map);
    }

    @Override
    public int update(VideoImg record) {
        return mapper.update(record);
    }

    @Override
    public void deleteByAccount(String account) {
        mapper.deleteByAccount(account);
    }

    @Override
    public List<String> selectByAccount(VideoImg record) {
        return mapper.selectByAccount(record);
    }

    @Override
    public void saveHonorAptitude(String name, String account, List<String> list) {
        Map<String,Object> map = new HashMap<>();
        map.put("account",account);
        map.put("column",name);
        map.put("type",2);
        for (String picture : list) {
            map.put("addr",picture);
           mapper.saveHonorAptitude(map);
        }
    }
}
