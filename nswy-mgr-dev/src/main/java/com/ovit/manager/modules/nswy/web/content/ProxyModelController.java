package com.ovit.manager.modules.nswy.web.content;

import com.alibaba.fastjson.JSONObject;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.ProxyModelInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import com.ovit.manager.modules.nswy.service.ProxyModelService;
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
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy")
public class ProxyModelController extends BaseController {

    @Autowired
    private ProxyModelService proxyModelService;

    private String conf = "./fdfs_client.conf";

    private String uploadFilePath = "./upload";


    private String content;

    private int fontSize;

    private int r;

    private int g;

    private int b;

    private String fileUrl = Global.getConfig("file.url")+"/pdf/upload/file";

    @RequestMapping(value = "/proxyModel/control")
    public String pictureList(ProxyModelInfo proxyModelInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<ProxyModelInfo> page = proxyModelService.findProxyModelInfo(proxyModelInfo, request, response);

        model.addAttribute("page",page);

        return "modules/content/proxyModelList";

    }

    @RequestMapping(value = "/proxyModel/add")
    public String proxyModelAdd(Model model){

        List<Map<String,Object>> list = proxyModelService.findFileType(new HashMap<>());
        model.addAttribute("list",list);

        return "modules/content/proxyModelAdd";
    }

    @RequestMapping(value = "/proxyModel/update")
    public String update(@RequestParam String fileId,Model model){
        List<Map<String,Object>> list = proxyModelService.findFileType(new HashMap<>());
        model.addAttribute("list",list);

        ProxyModelInfo proxyModelInfo = proxyModelService.findByProxyModel(fileId);

        model.addAttribute("proxyModelInfo",proxyModelInfo);

        return "modules/content/proxyModelFrom";
    }


    @RequestMapping(value = "/proxyModel/save")
    public String saveProxyModel(@RequestParam("file") MultipartFile uploadFile , HttpServletRequest request){
        try {

            String fileType = request.getParameter("fileType");

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            Map<String, Object> map = new HashMap<>();

            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = uploadFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                String type = fileName.toLowerCase();
                type = type.substring(type.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePath = path;
                FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
                String uploadUrl = client.uploadFile(uploadFile.getBytes(), type.substring(1, type.length()));
                String uploadIp = Global.getConfig("uploadIp");
                String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
                map.put("fileUrl", url);
                map.put("fileName",fileName);

                //后台模拟post去调用转pfd 的接口
                String result  = uploadFilePdf(uploadFile);
                JSONObject jsStr = JSONObject.parseObject(result);
                Map<String,Object> pdfMap = (Map<String,Object>)jsStr.get("data");

                map.put("filePdf", MapUtils.getString(pdfMap,"src"));
            }

            map.put("fileId",uuid);
            map.put("fileType",fileType);
            proxyModelService.saveProxyModel(map);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/proxyModel/control/";
    }

    @RequestMapping(value = "/proxyModel/updateProxyModelInfo")
    public String updateProxyModelInfo(@RequestParam("file") MultipartFile uploadFile , HttpServletRequest request, ProxyModelInfo proxyModelInfo) {
        try {
            String fileType = request.getParameter("fileType");
            Map<String, Object> map = new HashMap<>();

            //获取Resource 下的fdfs_client.conf的路径
            String path = GetResource.class.getClassLoader().getResource("fdfs_client.conf").getPath();
            String fileName = uploadFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                String type = fileName.toLowerCase();
                type = type.substring(type.lastIndexOf("."));
                // =====上传服务器开始======
                String fdfsClientConfigFilePath = path;
                FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
                String uploadUrl = client.uploadFile(uploadFile.getBytes(), type.substring(1, type.length()));
                String uploadIp = Global.getConfig("uploadIp");
                String url = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
                map.put("fileUrl", url);
                map.put("fileName",fileName);

                //后台模拟post去调用转pfd 的接口
                String result  = uploadFilePdf(uploadFile);
                JSONObject jsStr = JSONObject.parseObject(result);
                Map<String,Object> pdfMap = (Map<String,Object>)jsStr.get("data");

                map.put("filePdf", MapUtils.getString(pdfMap,"src"));
            }


            map.put("fileId",proxyModelInfo.getFileId());
            map.put("fileType",fileType);
            proxyModelService.updateProxyModelInfo(map);


        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath + "/nswy/proxyModel/control/";
    }


    @RequestMapping(value = "/proxyModel/delete")
    public String delete(@RequestParam String fileId){
         proxyModelService.deleteProxyModelInfo(fileId);


        return "redirect:" + adminPath + "/nswy/proxyModel/control/";
    }

    private String uploadFilePdf(MultipartFile uploadFile){
        String result="";

        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件

            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + uploadFile.getOriginalFilename()
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(uploadFile.getInputStream());
            byte[] bufferOut = new byte[1024*8];
            int bytes = 0;
            // 每次读8KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line; //这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }
}
