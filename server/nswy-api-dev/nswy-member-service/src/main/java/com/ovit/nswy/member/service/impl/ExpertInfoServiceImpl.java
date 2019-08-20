package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ExpertInfoMapper;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.VideoImgMapper;
import com.ovit.nswy.member.model.ExpertInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.VideoImg;
import com.ovit.nswy.member.service.ExpertInfoService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class ExpertInfoServiceImpl implements ExpertInfoService {
    @Autowired
    private ExpertInfoMapper expertInfoMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private VideoImgMapper videoImgMapper;

    @Override
    public void insert(ExpertInfo expertInfo) {
        expertInfoMapper.insert(expertInfo);
    }

    @Override
    public Integer getApproveStatus(String loginAccount) {
        return expertInfoMapper.getApproveStatus(loginAccount);
    }

    @Override
    public ExpertInfo getExpertInfo(String loginAccount) {
        return expertInfoMapper.getExpertInfo(loginAccount);
    }

    @Override
    public void updateByPrimaryKeySelective(ExpertInfo expertInfo) {
        expertInfoMapper.update(expertInfo);
    }

    @Override
    public PageInfo<Map<String, Object>> query(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> lists = expertInfoMapper.findAll();
        return new PageInfo<>(lists);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return expertInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Map<String, Object>> findExpertTitle(Map<String, Object> map) {
        PageUtils.initPage(map);
        List<Map<String, Object>> lists = expertInfoMapper.findExpertTitle(map);
        return new PageInfo<>(lists);
    }

    @Override
    public void saveOrUpdate(LoginUser loginUser, Map<String, Object> params) {
        String account = loginUser.getLoginAccount();
        ExpertInfo expertInfo = getExpertInfoByMap(params);
        expertInfo.setLoginAccount(account);
        List<VideoImg> videoImages = new ArrayList<>();
        videoImages.addAll(objToVideoImgList(params,"honor_certificate_picture_list",account));

        //保存资质证书
        videoImages.addAll(objToVideoImgList(params,"qualification_certificate_picture_list",account));

        //保存个人图片
        videoImages.addAll(objToVideoImgList(params,"personal_picture_list",account));
        ExpertInfo isExist = expertInfoMapper.getExpertInfo(account);
        if(isExist != null){
            expertInfoMapper.deleteByPrimaryKey(isExist.getId());
            videoImgMapper.deleteByAccount(account);
        }
        //机关、企业、乡村不能转化为专家
        if(0 == loginUser.getUserType()) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", 4);
            map.put("account", account);
            loginUserMapper.updateType(map);
        }
        expertInfoMapper.insert(expertInfo);
        videoImgMapper.insert(videoImages);
    }

    private ExpertInfo getExpertInfoByMap(Map<String, Object> params) {
        ExpertInfo expertInfo = new ExpertInfo();
        expertInfo.setExpertType(MapUtils.getString(params,"expert_type"));
        expertInfo.setAdeptField(MapUtils.getString(params,"adept_field"));
        expertInfo.setAdeptSpecies(MapUtils.getString(params,"adept_species"));
        expertInfo.setRelatedSpecies(MapUtils.getString(params,"speci"));
        expertInfo.setRelatedIndustry(MapUtils.getString(params,"trade"));
        expertInfo.setRelatedProduct(MapUtils.getString(params,"product"));
        expertInfo.setRelatedService(MapUtils.getString(params,"service"));
        expertInfo.setApproveStatus(0);
        return expertInfo;
    }

    private List<VideoImg> objToVideoImgList(Map<String,Object> params,String key,String account){
        List<VideoImg> list = new ArrayList<>();
        VideoImg videoImg = new VideoImg();
        Object object = params.get(key);
        if (null != object) {
            List<Map<String, Object>> objectList = (List<Map<String, Object>>)object;
            for (Map<String,Object> map : objectList) {
                videoImg.setAccount(account);
                videoImg.setAddr(MapUtils.getString(map,"picName"));
                videoImg.setType(MapUtils.getInteger(map,"type"));
                videoImg.setColumn(MapUtils.getString(map,"column"));
                list.add(videoImg);
            }
        }
        return list;
    }
}
