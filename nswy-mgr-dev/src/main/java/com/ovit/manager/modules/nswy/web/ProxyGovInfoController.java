package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ProxyGovInfo;
import com.ovit.manager.modules.nswy.service.ProxyGovInfoService;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class ProxyGovInfoController extends BaseController {

    public static final String FILE_PATH = "/upload/"; //相对路径

    @Autowired
    private ProxyGovInfoService proxyGovInfoService;

    @ModelAttribute
    public ProxyGovInfo get(@RequestParam(required=false) Integer proxyGovInfoId) {
        if (proxyGovInfoId !=null){
            return proxyGovInfoService.getProxyGovInfoById(proxyGovInfoId);
        }else{
            return new ProxyGovInfo();
        }
    }

    @RequestMapping(value = "proxyGovInfoForm")
    public String proxyGovInfoForm(ProxyGovInfo proxyGovInfo, Model model) {
        model.addAttribute("proxyGovInfo",proxyGovInfo);
        return "modules/nswy/proxyGovInfoForm";
    }

    @RequestMapping(value = "proxyGovInfoAuditFrom")
    public String proxyGovInfoAuditFrom(ProxyGovInfo proxyGovInfo, Model model) {
        model.addAttribute("proxyGovInfo",proxyGovInfo);
        return "modules/nswy/proxyGovInfoAuditFrom";
    }


    @RequestMapping("/getProxyGovInfolist")
    public String getProxyGovInfolist(ProxyGovInfo proxyGovInfo,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<ProxyGovInfo> page =proxyGovInfoService.findList(proxyGovInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/proxyGovInfoList";
    }

    @RequestMapping("/updateProxyGovInfo")
    public String updateProxyGovInfo(ProxyGovInfo proxyGovInfo, RedirectAttributes redirectAttributes){
        Integer rows=proxyGovInfoService.updateProxyGovInfo(proxyGovInfo);
        if(rows.equals(0)){
            addMessage(redirectAttributes, "审核失败");
            return "redirect:" + adminPath +"/nswy/proxyGovInfoAuditFrom?id="+proxyGovInfo.getId();
        }else{
            addMessage(redirectAttributes, "审核成功");
            return "redirect:" + adminPath +"/nswy/getProxyGovInfolist";
        }

    }
    @RequestMapping("/deleteproxyGovInfo")
    public String deleteProxyGovInfo(Integer proxyGovInfoId){
        proxyGovInfoService.deleteProxyGovInfo(proxyGovInfoId);
        return "redirect:" + adminPath +"/nswy/getProxyGovInfolist";
    }

    @RequestMapping("/importExcel")
    public String importExcel(Model model,HttpServletRequest request, HttpServletResponse response){
        return "modules/nswy/importExcelList";
    }


    @RequestMapping("/startImportExcel")
    public String startImportExcel(@RequestParam("file") MultipartFile uploadFile , HttpServletRequest request){
        try{
            String fileName = uploadFile.getOriginalFilename();
            // 重命名文件
            String newfileName = new Date().getTime() + String.valueOf(fileName);
            //获得物理路径webapp所在路径
            /**
             * request.getSession().getServletContext().getRealPath("/")表示当前项目的根路径，如
             * D:\Workspaces\eclipse_luna\.metadata\.plugins\org.eclipse.wst.server.core\tmp3\wtpwebapps\sky\
             */
            String pathRoot = request.getSession().getServletContext().getRealPath("");
            // 项目下相对路径
            String path = FILE_PATH + newfileName;

            // 创建文件实例
            File tempFile = new File(pathRoot + path);
            // 判断父级目录是否存在，不存在则创建
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdir();
            }
            // 判断文件是否存在，否则创建文件（夹）
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }
            // 将接收的文件保存到指定文件中
            uploadFile.transferTo(tempFile);
           String result  =  proxyGovInfoService.startImportExcel(tempFile);
           if(!result.equals("success")){
               return "modules/nswy/importExcelList";
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
