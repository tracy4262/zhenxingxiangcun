package com.ovit.manager.modules.nswy.web.content;


import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.VarieteyQueryService;
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
@RequestMapping("${adminPath}/nswy/varieteyQuery")
public class VarieteyQueryController extends BaseController {

    @Autowired
    private VarieteyQueryService varieteyQueryService;

    //物种
    @RequestMapping(value = "/findVarieteyList")
    public String findVarieteyList(@RequestParam Map<String,Object> map, Model model, HttpServletRequest request, HttpServletResponse response){
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (map.get("fname") != null && map.get("fname") != ""){
            speciesInfo.setFname(String.valueOf(map.get("fname")));
        }
        List<Map<String,Object>> list =varieteyQueryService.findVarieteyQueryList(map);
        model.addAttribute("list",list);
        model.addAttribute("speciesInfo",speciesInfo);

        List<Map<String,Object>> specieslist = varieteyQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/varieteyQueryList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam String id,Model model){
        model.addAttribute("pid",id);

        List<Map<String,Object>> specieslist = varieteyQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/varieteyQueryAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);

        List<Map<String,Object>> specieslist = varieteyQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/varieteyQueryAdd";
    }

    @RequestMapping(value = "/save")
    public String saveVarietey(@RequestParam(value = "diseasePic") MultipartFile logoFile, HttpServletRequest request){

        try {
            Map<String,Object> map = new HashMap<>();

            map.put("parentId",request.getParameter("pid"));
            map.put("varietyName",request.getParameter("varietyName"));
            map.put("pinyin",request.getParameter("pinyin"));
            map.put("speciesId",request.getParameter("speciesId"));
            map.put("fvarietykind",request.getParameter("fvarietykind"));
            map.put("fbreedingunit",request.getParameter("fbreedingunit"));
            map.put("fistransgene",request.getParameter("fistransgene"));
            map.put("fapplydate",request.getParameter("fapplydate"));
            map.put("fapplynumber",request.getParameter("fapplynumber"));
            map.put("fapplyannouncedate",request.getParameter("fapplyannouncedate"));
            map.put("fapplyannouncenumber",request.getParameter("fapplyannouncenumber"));
            map.put("fauthdate",request.getParameter("fauthdate"));
            map.put("fauthnumber",request.getParameter("fauthnumber"));
            map.put("fauthannouncedate",request.getParameter("fauthannouncedate"));
            map.put("fauthannouncenumber",request.getParameter("fauthannouncenumber"));
            map.put("fgrowpeople",request.getParameter("fgrowpeople"));
            map.put("fvarietyowner",request.getParameter("fvarietyowner"));
            map.put("fvarietyapprnum",request.getParameter("fvarietyapprnum"));
            map.put("fvarietyapprdate",request.getParameter("fvarietyapprdate"));
            map.put("foutput",request.getParameter("foutput"));
            map.put("fgrowteachology",request.getParameter("fgrowteachology"));
            map.put("fsuiteplatearea",request.getParameter("fsuiteplatearea"));
            map.put("fmarketsituation",request.getParameter("fmarketsituation"));
            map.put("fvarietyorigin",request.getParameter("fvarietyorigin"));
            map.put("fvarietyapprunit",request.getParameter("fvarietyapprunit"));
            map.put("ffeature",request.getParameter("ffeature"));

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

            varieteyQueryService.saveVarietey(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/varieteyQuery/findVarieteyList";
    }

    @RequestMapping(value = "/update")
    public String update(Model model,@RequestParam String indexid){
        Map<String,Object> map = varieteyQueryService.getSpeciesVarieteyById(indexid);
        model.addAttribute("varietey",map);

        List<Map<String,Object>> specieslist = varieteyQueryService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);
        return "modules/content/varieteyQueryFrom";
    }

    @RequestMapping(value = "/updateVarietey")
    public String updateVarietey( @RequestParam(value = "diseasePic") MultipartFile logoFile,HttpServletRequest request){

        try {
            Map<String,Object> map = new HashMap<>();

            map.put("varietyName",request.getParameter("varietyName"));
            map.put("pinyin",request.getParameter("pinyin"));
            map.put("speciesId",request.getParameter("speciesId"));
            map.put("fvarietykind",request.getParameter("fvarietykind"));
            map.put("fbreedingunit",request.getParameter("fbreedingunit"));
            map.put("fistransgene",request.getParameter("fistransgene"));
            map.put("fapplydate",request.getParameter("fapplydate"));
            map.put("fapplynumber",request.getParameter("fapplynumber"));
            map.put("fapplyannouncedate",request.getParameter("fapplyannouncedate"));
            map.put("fapplyannouncenumber",request.getParameter("fapplyannouncenumber"));
            map.put("fauthdate",request.getParameter("fauthdate"));
            map.put("fauthnumber",request.getParameter("fauthnumber"));
            map.put("fauthannouncedate",request.getParameter("fauthannouncedate"));
            map.put("fauthannouncenumber",request.getParameter("fauthannouncenumber"));
            map.put("fgrowpeople",request.getParameter("fgrowpeople"));
            map.put("fvarietyowner",request.getParameter("fvarietyowner"));
            map.put("fvarietyapprnum",request.getParameter("fvarietyapprnum"));
            map.put("fvarietyapprdate",request.getParameter("fvarietyapprdate"));
            map.put("foutput",request.getParameter("foutput"));
            map.put("fgrowteachology",request.getParameter("fgrowteachology"));
            map.put("fsuiteplatearea",request.getParameter("fsuiteplatearea"));
            map.put("fmarketsituation",request.getParameter("fmarketsituation"));
            map.put("fvarietyorigin",request.getParameter("fvarietyorigin"));
            map.put("fvarietyapprunit",request.getParameter("fvarietyapprunit"));
            map.put("ffeature",request.getParameter("ffeature"));
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
            varieteyQueryService.updateVarietey(map);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/varieteyQuery/findVarieteyList";
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam String indexid){

        varieteyQueryService.deleteVarietey(indexid);

        return "redirect:" + adminPath +"/nswy/varieteyQuery/findVarieteyList";
    }

}
