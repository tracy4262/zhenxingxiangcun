package com.ovit.manager.modules.nswy.web.content;


import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.PestQueryService;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/nswy/pestQuery")
public class PestQueryController extends BaseController {

    @Autowired
    private PestQueryService pestQueryService;

    //物种
    @RequestMapping(value = "/findPestList")
    public String findSpeciesList(@RequestParam Map<String,Object> map, Model model, HttpServletRequest request, HttpServletResponse response){
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (map.get("fname") != null && map.get("fname") != ""){
            speciesInfo.setFname(String.valueOf(map.get("fname")));
        }
        List<Map<String,Object>> list = pestQueryService.findPestQueryList(map);
        model.addAttribute("list",list);
        model.addAttribute("speciesInfo",speciesInfo);
        return "modules/content/pestQueryList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam String id,Model model){
        model.addAttribute("pid",id);

        List<Map<String,Object>> specieslist = pestQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/pestQueryAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);

        List<Map<String,Object>> specieslist = pestQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/pestQueryAdd";
    }

    @RequestMapping(value = "/save")
    public String saveDisease(@RequestParam(value = "diseasePic") MultipartFile logoFile, HttpServletRequest request){

        try {
            String pestName = request.getParameter("pestName");
            String pinyin = request.getParameter("pinyin");
            String speciesId = request.getParameter("speciesId");
            String mainFeatures = request.getParameter("mainFeatures");
            String habit = request.getParameter("habit");
            String petsRegular = request.getParameter("petsRegular");
            String protectMethod = request.getParameter("protectMethod");
            String remark = request.getParameter("remark");
            String parentId = request.getParameter("pid");
            Map<String,Object> map = new HashMap<>();

            map.put("remark",remark);
            map.put("parentId",parentId);
            map.put("pestName",pestName);
            map.put("pinyin",pinyin);
            map.put("speciesId",speciesId);
            map.put("mainFeatures",mainFeatures);
            map.put("habit",habit);
            map.put("petsRegular",petsRegular);
            map.put("protectMethod",protectMethod);

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
            pestQueryService.savePest(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/pestQuery/findPestList";
    }

    @RequestMapping(value = "/update")
    public String update(Model model,@RequestParam String indexid){
        Map<String,Object> map = pestQueryService.getSpeciesPestById(indexid);
        model.addAttribute("pest",map);

        List<Map<String,Object>> specieslist = pestQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/pestQueryFrom";
    }

    @RequestMapping(value = "/updatePest")
    public String updatePest( @RequestParam(value = "diseasePic") MultipartFile logoFile,HttpServletRequest request){

        try {
            String pestName = request.getParameter("pestName");
            String pinyin = request.getParameter("pinyin");
            String speciesId = request.getParameter("speciesId");
            String mainFeatures = request.getParameter("mainFeatures");
            String habit = request.getParameter("habit");
            String petsRegular = request.getParameter("petsRegular");
            String protectMethod = request.getParameter("protectMethod");
            String remark = request.getParameter("remark");

            Map<String,Object> map = new HashMap<>();

            map.put("remark",remark);
            map.put("pestName",pestName);
            map.put("pinyin",pinyin);
            map.put("speciesId",speciesId);
            map.put("mainFeatures",mainFeatures);
            map.put("habit",habit);
            map.put("petsRegular",petsRegular);
            map.put("protectMethod",protectMethod);
            map.put("indexid",request.getParameter("indexId"));

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

            pestQueryService.updatePest(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/pestQuery/findPestList";
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam String indexid){

        pestQueryService.deletePest(indexid);

        return "redirect:" + adminPath +"/nswy/pestQuery/findPestList";
    }
}
