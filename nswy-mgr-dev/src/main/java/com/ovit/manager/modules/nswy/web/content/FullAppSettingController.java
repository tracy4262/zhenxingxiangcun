package com.ovit.manager.modules.nswy.web.content;

import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.github.xphsc.util.StringUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import com.ovit.manager.modules.nswy.service.FullAppSettingService;
import com.ovit.manager.modules.nswy.service.ReportManageService;
import com.ovit.manager.modules.nswy.service.UserTypeService;
import org.apache.commons.collections.MapUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用管理
 *
 * @author haoWen
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class FullAppSettingController extends BaseController{
    @Autowired
    private FullAppSettingService fullAppSettingService;

    @Autowired
    private UserTypeService userTypeService;

    private String conf = "./fdfs_client.conf";

    private String uploadFilePath = "./upload";

    private String content;

    private int fontSize;

    private int r;

    private int g;

    private int b;


    @RequestMapping(value = "/fullAppSetting/control")
    public String pictureList(FullAppSettingInfo fullAppSettingInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        String level = request.getParameter("level");
        fullAppSettingInfo.setLevel(level);
        Page<FullAppSettingInfo> page = fullAppSettingService.findFullAppSetting(fullAppSettingInfo, request, response);
        model.addAttribute("level",level);
        if(level.equals("2")){
            List<Map<String,Object>> userList  = userTypeService.findUserType(new HashMap<>());
            for(FullAppSettingInfo info: page.getList()){

                if(StringUtils.isNotBlank(info.getUserType())){
                    String[] userType = info.getUserType().split(";");

                    StringBuffer typeName = new StringBuffer();
                    for(Map<String,Object> map : userList){

                        for(int i=0;i<userType.length;i++){
                            if(MapUtils.getString(map,"userType").equals(userType[i])){
                                typeName.append(MapUtils.getString(map,"userTypeName")).append(";");
                                break;
                            }

                        }

                    }

                    info.setUserTypeName(typeName.substring(0,typeName.length()-1).toString());
                }


            }
        }

        model.addAttribute("page",page);

        return "modules/content/fullAppSettingList";

    }


    @RequestMapping(value = "/fullAppSetting/update")
    public String update(@RequestParam String appSettingId,Model model){
        FullAppSettingInfo fullAppSettingInfo = fullAppSettingService.findByAppStting(appSettingId);
        model.addAttribute("fullAppSettingInfo",fullAppSettingInfo);
        List<Map<String,Object>> list  = userTypeService.findUserType(new HashMap<>());
        if(StringUtils.isNotBlank(fullAppSettingInfo.getUserType())){
            String[] userType = fullAppSettingInfo.getUserType().split(";");

            for(Map<String,Object> map : list){

                for(int i=0;i<userType.length;i++){
                    if(MapUtils.getString(map,"userType").equals(userType[i])){
                        map.put("check",true);
                        break;
                    }else{
                        map.put("check",false);
                    }

                }

            }
        }




        model.addAttribute("list",list);

        return "modules/content/fullAppSettingFrom";
    }

    @RequestMapping(value = "/fullAppSetting/updateFullAppSetting")
    public String updateFullAppSetting(@RequestParam(value = "file1", required = false) MultipartFile logoFile ,@RequestParam(value = "file2", required = false) MultipartFile uploadFile ,HttpServletRequest request,FullAppSettingInfo fullAppSettingInfo){

        try {

            String[] userTypes = request.getParameterValues("userType");

            Map<String, Object> map = new HashMap<>();

            if(userTypes !=null){
                StringBuffer userType = new StringBuffer();
                for(int i = 0;i<userTypes.length;i++){

                    userType = userType.append(userTypes[i]).append(";");
                }
                map.put("userType",userType.substring(0,userType.length()-1).toString());
            }

            if(fullAppSettingInfo.getCost().equals("0")){
                map.put("cost","免费");
            }else{
                map.put("cost","不免费");
            }

            map.put("appName",fullAppSettingInfo.getAppName());
            map.put("applicationAbstract",fullAppSettingInfo.getApplicationAbstract());
            map.put("recommend",fullAppSettingInfo.getRecommend());
            map.put("appSettingId",fullAppSettingInfo.getAppSettingId());
            map.put("level",fullAppSettingInfo.getLevel());

            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = logoFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                String type = fileName.toLowerCase();
                type = type.substring(type.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePath = path;
                FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
                String uploadUrl = client.uploadFile(logoFile.getBytes(), type.substring(1, type.length()));
                String uploadIp = Global.getConfig("uploadIp");
                String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
                map.put("logo", url);
                map.put("logoName",fileName);
            }

            /*String fileNames = uploadFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                String types = fileNames.toLowerCase();
                types = types.substring(types.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePaths = path;
                FastDFSClient clients = new FastDFSClient(fdfsClientConfigFilePaths);
                String uploadUrls= clients.uploadFile(uploadFile.getBytes(), types.substring(1, types.length()));
                String uploadIps = Global.getConfig("uploadIp");
                String urls = uploadIps + "/" + uploadUrls.substring(11, uploadUrls.length());
                map.put("image", urls);
                map.put("imageName",fileNames);
            }*/

            fullAppSettingService.updateFullAppSetting(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/fullAppSetting/control?level="+fullAppSettingInfo.getLevel();
    }





}
