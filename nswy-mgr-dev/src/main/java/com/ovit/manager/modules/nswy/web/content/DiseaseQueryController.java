package com.ovit.manager.modules.nswy.web.content;


import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.DiseaseQueryService;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/nswy/diseaseQuery")
public class DiseaseQueryController extends BaseController {

    @Autowired
    private DiseaseQueryService diseaseQueryService;


    @RequestMapping(value = "/findDiseaseList")
    public String findDiseaseList(@RequestParam Map<String,Object> map,Model model){
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (map.get("fname") != null && map.get("fname") != ""){
            speciesInfo.setFname(String.valueOf(map.get("fname")));
        }
        List<Map<String,Object>> list =diseaseQueryService.findDiseaseQueryList(map);
        model.addAttribute("list",list);
        model.addAttribute("speciesInfo",speciesInfo);
        return "modules/content/diseaseQueryList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam String id,Model model){
        model.addAttribute("pid",id);

        List<Map<String,Object>> specieslist = diseaseQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/diseaseQueryAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);

        List<Map<String,Object>> specieslist = diseaseQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/diseaseQueryAdd";
    }

    @RequestMapping(value = "/save")
    public String saveDisease(@RequestParam(value = "diseasePic") MultipartFile logoFile,HttpServletRequest request){

        try {
            String diseaseName = request.getParameter("diseaseName");
            String pinyin = request.getParameter("pinyin");
            String speciesId = request.getParameter("speciesId");
            String parentId = request.getParameter("pid");

            Map<String,Object>map = new HashMap<>();

            map.put("diseaseName",diseaseName);
            map.put("pinyin",pinyin);
            map.put("speciesId",speciesId);
            map.put("parentId",parentId);

            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = logoFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)) {
                String type = fileName.toLowerCase();
                type = type.substring(type.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePath = path;
                FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
                String uploadUrl = client.uploadFile(logoFile.getBytes(), type.substring(1, type.length()));
                String uploadIp = Global.getConfig("uploadIp");
                String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
                map.put("diseasePic", url);
            }
            diseaseQueryService.saveDisease(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/diseaseQuery/findDiseaseList";
    }

    @RequestMapping(value = "/update")
    public String update(Model model,@RequestParam String indexid){
        Map<String,Object> map = diseaseQueryService.getSpeciesDiseaseById(indexid);
        model.addAttribute("disease",map);

        List<Map<String,Object>> specieslist = diseaseQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/diseaseQueryFrom";
    }

    @RequestMapping(value = "/updateDisease")
    public String updateDisease(@RequestParam(value = "diseasePic") MultipartFile logoFile, HttpServletRequest request){

        try {
            String diseaseName = request.getParameter("diseaseName");
            String pinyin = request.getParameter("pinyin");
            String speciesId = request.getParameter("speciesId");
            String indexId = request.getParameter("indexId");

            Map<String,Object> map = new HashMap<>();

            map.put("indexId",indexId);
            map.put("diseaseName",diseaseName);
            map.put("pinyin",pinyin);
            map.put("speciesId",speciesId);

            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = logoFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)) {
                String type = fileName.toLowerCase();
                type = type.substring(type.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePath = path;
                FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
                String uploadUrl = client.uploadFile(logoFile.getBytes(), type.substring(1, type.length()));
                String uploadIp = Global.getConfig("uploadIp");
                String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
                map.put("diseasePic", url);
            }
            diseaseQueryService.updateDisease(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/diseaseQuery/findDiseaseList";
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam String indexid){

        diseaseQueryService.deleteDisease(indexid);

        return "redirect:" + adminPath +"/nswy/diseaseQuery/findDiseaseList";
    }

}
