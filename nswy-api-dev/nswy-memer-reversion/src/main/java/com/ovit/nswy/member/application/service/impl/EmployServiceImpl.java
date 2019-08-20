package com.ovit.nswy.member.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.application.mapper.EmployMapper;
import com.ovit.nswy.member.application.service.EmployService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.PageUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployServiceImpl implements EmployService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private EmployMapper employMapper;

    /**
     * 查询专家信息
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> findExpert(Map<String, Object> params) {

        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);
        //查询专家信息
        List<Map<String, Object>> expertList = employMapper.findConsultExpert(params);
        return new PageInfo(expertList);
    }

    /**
     * 聘请管理
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> manage(Map<String, Object> params) {

        //查询专家信息
        PageUtil.initPage(params);
        List<Map<String, Object>> list= new ArrayList<>();

        if(StringUtils.isNotBlank(MapUtils.getString(params,"type"))){
            list= employMapper.consultManage(params);
        }else{
            list = employMapper.consultManage(params);
            params.put("type","0");
            List<Map<String, Object>> list1 = employMapper.consultManage(params);
            for(Map map:list1){
                if("0".equals(MapUtils.getString(map,"status"))){
                    for(Map map1:list){
                        if(MapUtils.getString(map,"account").equals(MapUtils.getString(map1,"account"))){
                            map1.put("employStatus",0);
                        }
                    }
                }else {
                    for(Map map1:list){
                        if(MapUtils.getString(map,"account").equals(MapUtils.getString(map1,"account"))){
                            map1.put("employStatus",1);
                        }
                    }
                }
            }
        }

        return new PageInfo(list);
    }

    /**
     * 邀请专家
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void invite(Map<String, Object> params) {

        //查询邀请数据中是否有数据
        Map<String, Object> invite = employMapper.findInvite(params);
        if (MapUtils.isEmpty(invite)) {
            //记录邀请的数据，用于受邀管理展示
            employMapper.saveInvite(params);
        } else {
            //更新
            params.put("status", 0);
            employMapper.updateInvite(params);
        }
        //保存聘请的数据
        employMapper.saveNotice(params);

    }

    /**
     * 取消邀请
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cacelInvite(Map<String, Object> params) {

        //取消邀请：聘请某专家，在那个专家没有处理时可以取消邀请
        employMapper.deleteNotice(params);
        employMapper.deleteInvite(params);

    }

    /**
     * 解除关系
     * @param params
     * @return
     */
    @Override
    public void relieve(Map<String, Object> params) {
        int type = MapUtils.getInteger(params, "type");
        String activeAccount = MapUtils.getString(params, "activeAccount");
        String passiveAccount = MapUtils.getString(params, "passiveAccount");
        //type为0表示发起方为聘请方, type为1表示发起方为受聘方
        if (type == 0) {
            params.put("sendAccount", activeAccount);
            params.put("beInvitedAccount", passiveAccount);
        } else {
            params.put("sendAccount", passiveAccount);
            params.put("beInvitedAccount", activeAccount);
        }
        employMapper.deleteInvite(params);
    }

    /**
     * 受邀管理
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> beInvited(Map<String, Object> params) {
        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);

        //邀请方名字为实名认证里面的资质认证第一条数据的会员名称全称
        List<Map<String, Object>> list = employMapper.beInvited(params);
        for (Map<String,Object> map : list) {
            String account = MapUtils.getString(map, "sendAccount");
            Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
            if (MapUtils.isEmpty(useTemplate)) {
                map.put("inviteName", "暂无会员名称");
            } else {
                Map<String,Object> memberInfo = perfectBasicService.findMemberName(useTemplate);
                String memberName = MapUtils.getString(memberInfo, "memberName");
                if (StringUtils.isBlank(memberName)) {
                    map.put("inviteName", "暂无会员名称");
                } else {
                    map.put("inviteName", memberName);
                }
            }
        }
        return new PageInfo(list);
    }

    /**
     * 处理邀请
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void dealInvite(Map<String, Object> params) {
        //status:1接收、2拒绝
        //更新reversion_employ_manage表状态
        employMapper.updateInvite(params);
        //删除邀请数据
        employMapper.deleteNotice(params);

    }

    /**
     * 受聘管理
     * @param params
     * @return
     */
    @Override
    public PageInfo<List<Map<String, Object>>> beEmployed(Map<String, Object> params) {
        //分页查询数据
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(pageNum, pageSize);

        //邀请方名字为实名认证里面的资质认证第一条数据的会员名称全称
        List<Map<String, Object>> list = employMapper.beEmployed(params);
        for (Map<String,Object> map : list) {
            String account = MapUtils.getString(map, "sendAccount");
            Map<String,Object> useTemplate = perfectBasicService.findUseTemplate(account);
            if (MapUtils.isEmpty(useTemplate)) {
                map.put("inviteName", "暂无会员名称");
            } else {
                Map<String,Object> memberInfo = perfectBasicService.findMemberName(useTemplate);
                String memberName = MapUtils.getString(memberInfo, "memberName");
                if (StringUtils.isBlank(memberName)) {
                    map.put("inviteName", "暂无会员名称");
                } else {
                    map.put("inviteName", memberName);
                }
            }
        }
        return new PageInfo(list);
    }

    @Override
    public Map<String, Object> orderDetail(Integer id) {
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> detail = employMapper.orderDetail(id);
        if (MapUtils.isNotEmpty(detail)) {
            resultMap.put("data", JSON.parseObject(MapUtils.getString(detail, "data")));
            Map<String,Object> order = new HashMap<>();
            order.put("account", MapUtils.getString(detail, "account"));
            order.put("count", MapUtils.getInteger(detail, "count"));
            order.put("name", MapUtils.getString(detail, "name"));
            order.put("phone", MapUtils.getString(detail, "phone"));
            order.put("location", MapUtils.getString(detail, "location"));
            order.put("money", MapUtils.getString(detail, "money"));
            order.put("endTime", MapUtils.getString(detail, "endTime"));
            order.put("status", MapUtils.getString(detail, "status"));
            order.put("employTime", MapUtils.getString(detail, "employTime"));
            resultMap.put("order", order);

        }
        return resultMap;
    }
}
