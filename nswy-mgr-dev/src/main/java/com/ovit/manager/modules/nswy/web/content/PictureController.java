package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import com.ovit.manager.modules.nswy.service.PictureService;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片管理
 *
 * @author haoWen
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class PictureController extends BaseController{
    @Autowired
    private PictureService pictureService;



    private String conf = "./fdfs_client.conf";

    private String uploadFilePath = "./upload";

    private String content;

    private int fontSize;

    private int r;

    private int g;

    private int b;

    @RequestMapping(value = "/picture/control")
    public String pictureList(PictureInfo pictureInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<PictureInfo> page = pictureService.findKnowledgeList(pictureInfo, request, response);
        model.addAttribute("page",page);
        return "modules/content/pictureList";
    }
    @RequestMapping(value = "/picture/add")
    public String pictureAdd(){
        return "modules/content/pictureAdd";
    }

    @RequestMapping(value = "/picture/save")
    public String savePicture(@RequestParam("file") MultipartFile uploadFile ,HttpServletRequest request){
        try {
            //图片应用位置
            String used = request.getParameter("used");
            //描述
            String remark = request.getParameter("remark");
            Map<String, Object> map = new HashMap<>();
            map.put("remark",remark);
            if ("1".equals(used)) {
                map.put("pictureUseHome",1);
                map.put("pictureUseInformation",0);
                map.put("pictureProduct",0);
            } else if("2".equals(used)){
                map.put("pictureUseHome",0);
                map.put("pictureUseInformation",1);
                map.put("pictureProduct",0);
            }else{
                map.put("pictureUseHome",0);
                map.put("pictureUseInformation",0);
                map.put("pictureProduct",1);
            }
            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = uploadFile.getOriginalFilename();
            String type = fileName.toLowerCase();
            type = type.substring(type.lastIndexOf("."));
            // =====上传服务器开始======
            String fdfsClientConfigFilePath = path;
            FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
            String uploadUrl = client.uploadFile(uploadFile.getBytes(), type.substring(1, type.length()));
            String uploadIp = Global.getConfig("uploadIp");
            String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
            map.put("pictureUrl", url);
            map.put("fileName",fileName);
            pictureService.savePicture(map);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/picture/control/";
    }

    @ResponseBody
    @RequestMapping(value = "pictureSetOrderNum",method = RequestMethod.POST)
    public String pictureSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("pictureId",request.getParameter("pictureId"));
        pictureService.pictureSetOrderNum(map);
        return "success";
    }

    /**
     * 删除
     * @param pictureId
     * @return
     */
    @RequestMapping("/picture/delete")
    public String deletePicture(int pictureId){
        logger.info("删除知识：{}",pictureId);
        pictureService.deletePicture(pictureId);
        return "redirect:" + adminPath +"/nswy/picture/control/";
    }


    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pictureUpdateDisplay",method = RequestMethod.POST)
    public String pictureUpdateDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("pictureId",request.getParameter("pictureId"));
        logger.info("设置是否在无忧导航显示：{}",map);
        pictureService.pictureUpdateDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/pictureUseHome",method = RequestMethod.POST)
    public String pictureUseHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("pictureId",request.getParameter("pictureId"));
        logger.info("设置是否在无忧导航显示：{}",map);
        pictureService.pictureUseHome(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/pictureUseInformation",method = RequestMethod.POST)
    public String pictureUseInformation(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("pictureId",request.getParameter("pictureId"));
        logger.info("设置是否在无忧导航显示：{}",map);
        pictureService.pictureUseInformation(map);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/pictureProduct",method = RequestMethod.POST)
    public String pictureProduct(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("pictureId",request.getParameter("pictureId"));
        logger.info("设置是否在商城首页显示：{}",map);
        pictureService.pictureProduct(map);
        return "success";
    }
}
