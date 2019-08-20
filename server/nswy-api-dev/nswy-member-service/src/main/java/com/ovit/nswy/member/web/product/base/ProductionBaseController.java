package com.ovit.nswy.member.web.product.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 生产基地管理
 *
 * @author haoWen
 * @create 2017-12-19 11:13
 **/
@RestController
@RequestMapping("/product-base")
public class ProductionBaseController {
    private Logger logger = LogManager.getLogger(this.getClass());
    //生产基地基本信息
    @Autowired
    private ProductionBaseService productionBaseService;

    //摄像头位置信息
    @Autowired
    private CameraInfoService cameraInfoService;

    //相册
    @Autowired
    private ProductMediaLibraryService productMediaLibraryService;

    //相册的照片
    @Autowired
    private ProductMediaLibraryDetailService productMediaLibraryDetailService;

    //地理位置
    @Autowired
    private ProductPositionService productPositionService;

    //地形地貌
    @Autowired
    private TopographyPhysiognomyService topographyPhysiognomyService;

    //气候条件
    @Autowired
    private WeatherConditionsService weatherConditionsService;

    //水利条件
    @Autowired
    private WaterConditionService waterConditionService;

    //电力条件
    @Autowired
    private ElectricPowerService electricPowerService;

    //网络通信条件
    @Autowired
    private NetworkCommService networkCommService;



    /**
     * 新增生产基地基本信息
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result saveProduceBase (@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            logger.debug("添加生产基地基本信息，参数：{}",params);
            result = new Result();
            List list = JSON.parseArray(MapUtils.getString(params,"locationId"));
            String locationId = StringUtils.join(list.toArray(),",");
            params.put("locationId",locationId);
            String productId = MapUtils.getString(params,"productId");
            //如果productId不为空则为修改
            if (StringUtils.isNotEmpty(productId)) {
                productionBaseService.updateProductBase(params);
                logger.info("修改生产基地基本信息成功：{}",params);
            } else {
                //如果productId为空则为新增，校验基地名称是否已存在
                List<Map<String,Object>> isExist =  productionBaseService.queryIsExist(params);
                if (CollectionUtils.isEmpty(isExist)) {
                    //新增
                    params.put("productId",String.valueOf(UUID.randomUUID()) );
                    productionBaseService.insertProductionBase(params);
                    result.setData(params);
                    logger.info("新增生产基地基本信息成功:{}",params.get("productId"));
                } else {
                    //已存在
                    result.setCode(300);
                    result.setMsg("基地名称已存在！");
                }
            }
        } catch (Exception e) {
            logger.error("新增生产基地基本信息失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result deleteProduceBase(@RequestBody Map<String, Object> params){
        logger.debug("删除生产基地所有信息：{}",params);
        Result result = new Result();
        productionBaseService.delete(params);
        logger.info("删除生产基地所有信息成功！");
        return result;
    }
    /**
     * 通过productId查询基地基本信息
     * 用于新增时上一步回显
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-product-id",method = RequestMethod.POST)
    public Result queryByProductId(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("通过productId查询基地基本信息，参数：{}",params);
            result = new Result();
            Map<String,Object> map = productionBaseService.queryByProductId(params);
            String locationId = MapUtils.getString(map,"locationId");
            map.put("locationId",locationId.split(","));
            logger.info("通过productId查询基地基本信息成功：{}",map);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("通过productId查询基地基本信息失败：{}",e);
        }
        return result;
    }
    /**
     * 修改生产基地基本信息
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateProductBase(@RequestBody Map<String, Object> params, HttpServletRequest request){
        Result result = null;
        try {
            logger.debug("修改生产基地基本信息，参数：{}",params);
            result = new Result();
            productionBaseService.updateProductBase(params);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("修改生产基地基本信息失败：{}",e);
        }
        return result;
    }
    /**
     * 新增摄像头位置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/camera-save",method = RequestMethod.POST)
    public Result saveCameraInfo(@RequestBody Map<String, Object> params) {
        logger.debug("新增摄像头位置信息，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            cameraInfoService.saveCameraInfo(params);
            logger.info("新增摄像头成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("新增摄像头位置信息失败：{}",e);
        }
        return result;
    }

    /**
     *删除摄像头信息
     * @param params
     */
    @RequestMapping(value = "/camera-delete",method = RequestMethod.POST)
    public Result deleteCameraInfo(@RequestBody Map<String, Object> params){
        logger.debug("删除摄像头位置信息，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            cameraInfoService.deleteCameraInfoById(params);
            logger.info("删除摄像头成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("删除摄像头位置信息失败：{}",e);
        }
        return result;
    }

    /**
     * 查询摄像头信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/camera-query",method = RequestMethod.POST)
    public Result queryCameraInfo(@RequestBody Map<String, Object> params){
        logger.debug("摄像头查询，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            PageInfo<List<Map<String,Object>>> cameraList = cameraInfoService.queryCameraInfo(params);
            result.setData(cameraList);
            logger.info("摄像头查询成功：{}",cameraList.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("摄像头查询失败：{}",e);
        }
        return result;
    }


    /**
     * 相册添加
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/media-library-save",method = RequestMethod.POST)
    public Result saveMediaLibrary(@RequestBody Map<String, Object> params,HttpServletRequest request){
        logger.debug("相册添加，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> mediaName  = productMediaLibraryService.queryByMediaName(params);
            if (MapUtils.isEmpty(mediaName)){
                productMediaLibraryService.saveProductMediaLibrary(params);
                logger.info("相册添加成功！");
            } else {
                String msg = "相册名已存在！";
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMsg(msg);
                logger.info("相册添加失败：{}",msg);
            }
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("相册添加失败：{}",e);
        }
        return result;
    }

    /**
     * 设置相册封面
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-cover",method = RequestMethod.POST)
    public Result setMediaLibraryCover(@RequestBody Map<String, Object> params){
        logger.debug("设置相册封面，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            productMediaLibraryService.setMediaLibraryCover(params);
            logger.info("设置相册封面成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("设置相册封面失败：{}",e);
        }
        return result;
    }

    /**
     * 编辑相册
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-update",method = RequestMethod.POST)
    public Result updateMediaLibrary(@RequestBody Map<String, Object> params){
        logger.debug("编辑相册，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            productMediaLibraryService.updateMediaLibrary(params);
            logger.info("编辑相册成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("编辑相册失败：{}",e);
        }
        return result;
    }

    /**
     * 查询相册信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-query",method = RequestMethod.POST)
    public Result queryMediaLibrary(@RequestBody Map<String, Object> params,HttpServletRequest request){
        logger.debug("查询相册信息：{}",params);
        Result result = null;
        try {
            result = new Result();
            PageInfo<List<Map<String,Object>>> mediaLibraryList =  productMediaLibraryService.queryAllMediaLibrary(params);
            result.setData(mediaLibraryList);
            logger.info("查询相册成功：{}",mediaLibraryList.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询相册信息失败：{}",e);
        }
        return result;

    }
    /**
     * 删除相册
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-delete",method = RequestMethod.POST)
    public Result deleteMediaLibrary(@RequestBody Map<String, Object> params){
        logger.debug("删除相册，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            productMediaLibraryService.deleteMediaLibrary(params);
            logger.info("删除相册成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("删除相册失败：{}",e);
        }
        return result;
    }

    /**
     * 上传照片
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-detail-save",method = RequestMethod.POST)
    public Result saveMediaLibraryDetail(@RequestBody Map<String, Object> params){
        logger.debug("上传照片，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            String productId = MapUtils.getString(params,"id");
            params.put("productId",productId);
            //如果该productId下存在照片清空再新增，反之直接新增
            List<Map<String,Object>> list = productMediaLibraryDetailService.query(params);
            if (CollectionUtils.isNotEmpty(list)){
                productMediaLibraryDetailService.deleteMediaLibraryDetailByProductId(params);
                logger.info("删除{}的{}张照片成功！",MapUtils.getString(params,"id"),list.size());
            }
            Map<String,Object> map = MapUtils.getMap(params,"data");
            List<String> urls = (List<String>) MapUtils.getObject(map,"urls");
            for (String url : urls) {
                params.put("photoUrl",url);
                productMediaLibraryDetailService.saveMediaLibraryDetail(params);
            }
            logger.info("成功上传{}张照片！",urls.size());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("上传照片失败：{}",e);
        }
        return result;
    }

    /**
     * 删除照片
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-detail-delete",method = RequestMethod.POST)
    public Result deleteMediaLibraryDetail(@RequestBody Map<String, Object> params){
        logger.debug("删除照片，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            productMediaLibraryDetailService.deleteMediaLibraryDetail(params);
            logger.info("删除成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("删除照片失败：{}",e);
        }
        return result;
    }

    /**
     * 照片修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-detail-update",method = RequestMethod.POST)
    public Result updateMediaLibraryDetail(@RequestBody Map<String, Object> params){
        logger.debug("编辑照片，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            productMediaLibraryDetailService.updateMediaLibraryDetail(params);
            logger.info("编辑成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("编辑照片失败：{}",e);
        }
        return result;
    }

    /**
     * 照片查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-detail-query",method = RequestMethod.POST)
    public Result queryMediaLibraryDetail(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.info("照片查询，参数：{}",params);
            result = new Result();
            List<Map<String,Object>> mediaLibraryDetailList =  productMediaLibraryDetailService.query(params);
            result.setData(mediaLibraryDetailList);
            logger.info("照片查询成功：{}",mediaLibraryDetailList);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("照片查询失败：{}",e);
        }
        return result;
    }

    /**
     * 照片查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-detail-query-list",method = RequestMethod.POST)
    public Result queryMediaLibraryDetailList(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.info("照片查询，参数：{}",params);
            result = new Result();
            PageInfo<List<Map<String,Object>>> mediaLibraryDetailList =  productMediaLibraryDetailService.queryList(params);
            result.setData(mediaLibraryDetailList);
            logger.info("照片查询成功：{}",mediaLibraryDetailList.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("照片查询失败：{}",e);
        }
        return result;
    }
    /**
     * 查询所有生产基地基本信息
     * @param params
     * @param request
     * @return
     */
        @RequestMapping(value = "/select-all",method = RequestMethod.POST )
    public Result selectAll(@RequestBody Map<String, Object> params, HttpServletRequest request){
        Result result = null;
        try {
            result = new Result();
            //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            logger.debug("查询所有生产基地基本信息，参数：{}",params);
            PageInfo<List<Map<String,Object>>> listAll = productionBaseService.selectAll(params);
            result.setData(listAll);
            logger.info("查询所有生产基地基本信息成功：{}",listAll.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询生产基地基本信息失败：{}",e);
        }
        return result;
    }

    /**
     * 查询生产基地详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/select-detail",method = RequestMethod.POST )
    public Result selectDetail(@RequestBody Map<String, Object> params, HttpServletRequest request){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询生产基地详情，参数：{}",params);
            Map<String,Object> productDetailMap = productionBaseService.selectDetailByProductId(params);
            List<Map<String,Object>> photoMap = productMediaLibraryDetailService.queryByProduct(params);
            List<Map<String,Object>> camereMap = cameraInfoService.queryCameraByProductId(params);
            Map<String, Object> productionBaseMap = productionBaseService.queryByProductId(params);
            if (CollectionUtils.isNotEmpty(photoMap)) {
                productDetailMap.put("photoMap",photoMap);
            }
            if (CollectionUtils.isNotEmpty(camereMap)) {
                productDetailMap.put("camereMap",camereMap);
            }
            if (MapUtils.isNotEmpty(productionBaseMap)) {
                productDetailMap.put("describle",MapUtils.getString(productionBaseMap,"describle"));
            }
            result.setData(productDetailMap);
            logger.info("查询生产基地详情成功：{}",productDetailMap);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询生产基地详情失败",e);
        }
        return result;
    }

    /**
     * 详细描述
     * @param params
     * @return
     */
    @RequestMapping(value = "/select-full-describe",method = RequestMethod.POST )
    public Result queryFullDescribe(@RequestBody Map<String, Object> params){
        Result result = new Result();
        Map<String, Object> productionBaseMap = productionBaseService.queryByProductId(params);
        //地理位置描述
        Map<String, Object> productPositionMap = productPositionService.queryGeographicalPosition(params);
        //地形地貌
        Map<String, Object> topographyPhysiognomyMap = topographyPhysiognomyService.queryTopographyPhysiognomy(params);
        //气候条件
        Map<String, Object> weatherConditionsMap = weatherConditionsService.queryWeatherConditionsByProductId(params);
        //水利条件
        Map<String, Object> waterConditionMap = waterConditionService.query(params);
        //电力条件
        Map<String, Object> electricPowerMap = electricPowerService.query(params);
        //网络通信条件
        Map<String, Object> networkCommMap = networkCommService.query(params);
        Map<String,Object> map = new HashMap<>();
        map.put("productionBaseMap",productionBaseMap);
        map.put("productPositionMap",productPositionMap);
        map.put("topographyPhysiognomyMap",topographyPhysiognomyMap);
        map.put("weatherConditionsMap",weatherConditionsMap);
        map.put("waterConditionMap",waterConditionMap);
        map.put("electricPowerMap",electricPowerMap);
        map.put("networkCommMap",networkCommMap);
        result.setData(map);
        return result;
    }

    /**
     * 通过用户名查询所有相册
     * 上传照片
     * @param params
     * @return
     */
    @RequestMapping(value = "/media-library-query-all",method = RequestMethod.POST )
    public Result queryMediaLibrary(@RequestBody Map<String, Object> params){
        Result result = null;
        logger.info("通过用户名查询所有相册",params);
        try {
            result = new Result();
            List<Map<String,Object>> all = productMediaLibraryService.queryMediaLibrary(params);
            logger.info("通过用户名查询所有相册：{}",all);
            result.setData(all);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("通过用户名查询所有相册失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "product-is-exist",method = RequestMethod.POST)
   public Result productIsExist(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            List<Map<String,Object>> isExist = productionBaseService.queryByAccount(params);
            result.setData(isExist);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
   }
}
