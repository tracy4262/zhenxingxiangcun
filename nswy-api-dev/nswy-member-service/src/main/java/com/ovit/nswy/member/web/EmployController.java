package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


/**
 * 聘请管理
 * @author haoWen
 */
@RestController
@RequestMapping("/Employ")
public class EmployController {
private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private EmployService employService;
	@Autowired
	private SpecieService specieService;
	@Autowired
    private SystemDictService systemDictService;

	@Autowired
    private EmployOprService employOprService;

    /**
     * 查询所有聘请专家
     * @param params
     * @return
     */
	@RequestMapping(value = "/selectAll",method = RequestMethod.POST )
	public Result selectAll(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
            result = new Result();
			logger.debug("查询所有聘请的专家，参数：{}",params);
			//相关行业
			String relatedIndustry = MapUtils.getString(params,"relatedIndustry");

			if (StringUtils.isNotBlank(relatedIndustry)) {
                String [] relatedIndustryArray = relatedIndustry.split(" ");
                params.put("relatedIndustryArray", relatedIndustryArray);
            }
			//相关物种
			String relatedSpecies = MapUtils.getString(params,"relatedSpecies");
			if (StringUtils.isNotBlank(relatedSpecies)) {
                String [] relatedSpeciesArray = relatedSpecies.split(" ");
                params.put("relatedSpeciesArray", relatedSpeciesArray);
            }
			PageInfo<Map<String, Object>> list = getList(employService.selectAll(params));
            logger.info("所有已发送聘请邀请的专家成功：{}",list);
			result.setData(list);
		} catch (NumberFormatException e) {
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			logger.error("查询所有已聘请的专家失败：{}",e);
		}
		return result;
	}

    /**
     * 查询所有受聘的企业
     * @param params
     * @return
     */
	@RequestMapping(value = "/selectAllCompany",method = RequestMethod.POST)
	public Result selectAllCompany(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
            result = new Result();
			logger.debug("查询所有受聘请的企业，参数：{}",params);
			PageInfo<List<Map<String, Object>>> list = employService.selectAllCompany(params);
			logger.info("所有受聘方：{}",list);
			result.setData(list);
		} catch (NumberFormatException e) {
			logger.error("查询所有受聘企业失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 查询所有未聘请专家
     * @param params
     * @return
     */
	@RequestMapping(value = "/expertAllTable",method = RequestMethod.POST)
	public Result expertAllTable(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
            result = new Result();
			logger.debug("查询所有未聘请的专家，参数：{}",params);
			String relatedIndustry = String.valueOf(params.get("relatedIndustry"));
			String relatedSpecies = String.valueOf(params.get("relatedSpecies"));
			if (StringUtils.isNotBlank(relatedIndustry)) {
                String [] relatedIndustryArray = relatedIndustry.split(" ");
                params.put("relatedIndustryArray", relatedIndustryArray);
            }
			if (StringUtils.isNotBlank(relatedSpecies)) {
                String [] relatedSpeciesArray = relatedSpecies.split(" ");
                params.put("relatedSpeciesArray", relatedSpeciesArray);
            }
			PageInfo<Map<String, Object>> list = getList(employService.expertAllTable(params));
			logger.debug("所有未聘请的专家：{}",list);
			result.setData(list);
		} catch (NumberFormatException e) {
			logger.error("查询所有未聘请专家失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 查询所有邀请通知
     * @param params
     * @return
     */
	@RequestMapping(value = "/selectAllInform",method = RequestMethod.POST)
	public Result selectAllInform(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
            result = new Result();
			logger.debug("查询所有邀请的通知，邀请通知，参数：{}",params);
			PageInfo<List<Map<String, Object>>> AllInformList = employService.selectAllInform(params);
			logger.info("所有邀请通知：{}",AllInformList);
			result.setData(AllInformList);
		} catch (NumberFormatException e) {
			logger.error("查询所有邀请通知失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 解除聘请/同意辞职
     * @param params
     * @return
     */
	@RequestMapping(value = "/removeEmploy", method = RequestMethod.POST)
	public Result remove(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
			logger.debug("解除聘请、同意辞职，参数：{}",params);
			result = new Result();
			result.setData(employService.removeEmploy(MapUtils.getInteger(params,"id")));
			logger.info("解聘成功");
		} catch (NumberFormatException e) {
			logger.error("解除聘请/同意辞职失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 邀请专家
     * @param params
     * @return
     */
	@RequestMapping(value = "/addExpert", method = RequestMethod.POST)
	public Result addExpert(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
            result = new Result();
			logger.debug("邀请专家，参数：{}",params);
			Map<String, Object> map = employService.findByid(MapUtils.getInteger(params,"id"));
			Map<String, Object> expertEmploy = new HashMap();
            String receiverAccount = MapUtils.getString(map,"loginAccount");
            String senderAccount = MapUtils.getString(params,"loginAccount");
			expertEmploy.put("receiverAccount", receiverAccount);
			expertEmploy.put("senderAccount",senderAccount);
			expertEmploy.put("employStatus", 0);
			expertEmploy.put("createTime",new Date());
			//判断记录是否已经存在
            Map<String,Object> isExist = employService.queryIsExist(expertEmploy);
            if (MapUtils.isNotEmpty(isExist)) {
                employService.updateExpert(params);
                expertEmploy.put("id",MapUtils.getString(isExist,"id"));

            } else {
                employService.addExpert(expertEmploy);
                expertEmploy.put("id",MapUtils.getString(expertEmploy,"id"));
            }
            expertEmploy.put("oprRecord",StringUtils.join(senderAccount,"邀请",receiverAccount,"成为专家"));
            employOprService.saveEmployOprInfo(expertEmploy);
			result.setData(1);
			logger.info("邀请专家成功！");
            //邀请成功后添加操作记录
		} catch (NumberFormatException e) {
			logger.error("邀请专家失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 接受邀请
     * @param params
     * @return
     */
	@RequestMapping(value = "/acceptInvitation", method = RequestMethod.POST)
	public Result acceptInvitation(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
			logger.debug("接收邀请，参数：{}",params);
			result = new Result();
			params.put("dealTime",new Date());
			result.setData(employService.acceptInvitation(params));
			Map<String,Object> map = employService.queryById(params);
            params.put("oprRecord",StringUtils.join(MapUtils.getString(map,"senderAccount"),
                    "接收",MapUtils.getString(map,"receiverAccount"),"的专家邀请"));
			logger.info("成功接收邀请！");
            employOprService.saveEmployOprInfo(params);
		} catch (NumberFormatException e) {
			logger.error("接受邀请失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 拒绝邀请
     * @param params
     * @return
     */
	@RequestMapping(value = "/refuseInvitation", method = RequestMethod.POST)
	public Result refuseInvitation(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
			result = new Result();
			logger.info("拒绝邀请，参数：{}",params);
			params.put("dealTime",new Date());
			result.setData(employService.declineInvitation(params));
			logger.info("拒绝邀请成功！");
		} catch (NumberFormatException e) {
			logger.error("拒绝邀请：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

    /**
     * 专家申请辞职
     * @param params
     * @return
     */
    @RequestMapping(value = "/resignation", method = RequestMethod.POST)
    public Result resignation(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
			logger.debug("专家申请辞职，参数：{}",params);
			result = new Result();
			params.put("employStatus", "4");
			result.setData(employService.resignation(params));
			logger.info("申请辞职成功");
		} catch (Exception e) {
			logger.error("专家申请辞职失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
    }

    /**
     * 拒绝专家申请辞职
     * @param params
     * @return
     */
    @RequestMapping(value = "/refuseResignation", method = RequestMethod.POST)
    public Result refuseResignation(@RequestBody Map<String, Object> params) {
		Result result = null;
		try {
			logger.debug("拒绝专家的辞职申请，参数：{}",params);
			result = new Result();
			params.put("employStatus", "1");
			result.setData(employService.resignation(params));
			logger.info("拒绝专家的辞职申请");
		} catch (Exception e) {
			logger.error("拒绝专家申请辞职失败：{}",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
    }

    /**
     * 查新专家的
     * @param pageList
     * @return
     */
    private PageInfo<Map<String, Object>> getList(PageInfo<Map<String, Object>> pageList){
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Map<String, Object>> listAll = pageList.getList();
        if (CollectionUtils.isNotEmpty(listAll) && listAll.size() > 0) {
            for (Map<String, Object> maps : listAll){
                Map<String, Object> map1 =new HashMap<>();
                map1.putAll(maps);
                //相关物种
                //map1.put("relatedSpecies",specieService.getSpecies(MapUtils.getString(maps,"relatedSpecies")));
                //擅长物种
                //map1.put("adeptSpecies",specieService.getSpecies(MapUtils.getString(maps,"adeptSpecies")));
                //相关行业
                String relatedIndustryId = MapUtils.getString(maps,"relatedIndustry");
                /*if (StringUtils.isNotEmpty(relatedIndustryId)) {
                    String [] relatedIndustryIdArray = StringUtils.splitByWholeSeparator(relatedIndustryId,"");
                    List<Map<String,Object>> relatedIndustryName = systemDictService.queryByDictValue(Arrays.asList(relatedIndustryIdArray));
                    StringBuffer name = new StringBuffer();
                    for (Map<String,Object> map : relatedIndustryName) {
                        name.append(MapUtils.getString(map,"dictName")).append(" ");
                    }
                    map1.put("relatedIndustry", name.toString());
                }*/
                mapList.add(map1);
            }
        }
        pageList.setList(mapList);
        return pageList;
    }
}
