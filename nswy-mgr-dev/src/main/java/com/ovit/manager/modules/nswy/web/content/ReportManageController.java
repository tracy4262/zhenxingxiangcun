package com.ovit.manager.modules.nswy.web.content;

import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import com.ovit.manager.modules.nswy.service.PictureService;
import com.ovit.manager.modules.nswy.service.ReportManageService;
import com.ovit.manager.modules.nswy.service.UserTypeService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

import java.net.URL;

/**
 * 报表管理
 *
 * @author haoWen
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class ReportManageController extends BaseController{
    @Autowired
    private ReportManageService reportManageService;

    @Autowired
    private UserTypeService userTypeService;


    private static OpenOfficeConnection connection;

    private StreamOpenOfficeDocumentConverter converter;


    private String conf = "./fdfs_client.conf";

    private String uploadFilePath = "./upload";
/*
    private String uploadIp = "192.168.7.61";

    private String openOfficeIp = "192.168.7.42";

    private String openOfficePort = "8100";

    private String openOfficePath = "/Users/wangrongfei/Downloads/";*/

    private String content;

    private int fontSize;

    private int r;

    private int g;

    private int b;

    private String fileUrl =Global.getConfig("file.url")+"/pdf/upload/file";

/*    @PostConstruct
    public void defaultInitial() {
        try {
            // connect to an OpenOffice.org instance running on port 8100
            connection = new SocketOpenOfficeConnection(openOfficeIp, Integer.parseInt(openOfficePort));
            connection.connect();
            // convert
            //   DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter = new StreamOpenOfficeDocumentConverter(connection);
            logger.info("open office connected");
        } catch (Exception e) {
            logger.error("open office connected error！", e);
        }
    }*/

/*    @ModelAttribute
    public ReportManageInfo get(@RequestParam(required=false) Integer reportId) {
        if (reportId !=null){
            return reportManageService.findReport(reportId);
        }else{
            return new ReportManageInfo();
        }
    }*/

    @RequestMapping(value = "/reportManage/control")
    public String pictureList(ReportManageInfo reportManageInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ReportManageInfo> page = reportManageService.findKnowledgeList(reportManageInfo, request, response);
        model.addAttribute("page",page);
        return "modules/content/reportManageList";
    }
    @RequestMapping(value = "/reportManage/add")
    public String pictureAdd(Model model){
        List<Map<String,Object>> list  = userTypeService.findUserType(new HashMap<>());

        List<Map<String,Object>> reportTypeList = reportManageService.findReportType(new HashMap<>());
        model.addAttribute("list",list);
        model.addAttribute("reportTypeList",reportTypeList);
        return "modules/content/reportManageAdd";
    }

    @RequestMapping(value = "/reportManage/update")
    public String update(@RequestParam Integer reportId,Model model){
        ReportManageInfo reportManageInfo = reportManageService.findReport(reportId);
        model.addAttribute("reportManageInfo",reportManageInfo);

        List<Map<String,Object>> list  = userTypeService.findUserType(new HashMap<>());

        List<Map<String,Object>> reportTypeList = reportManageService.findReportType(new HashMap<>());
        model.addAttribute("list",list);
        model.addAttribute("reportTypeList",reportTypeList);
        return "modules/content/reportManageFrom";
    }

    @RequestMapping(value = "/reportManage/delete")
    public String reportDelete(@RequestParam Integer reportId){
        reportManageService.delete(reportId);
        return "redirect:" + adminPath +"/nswy/reportManage/control/";

    }

    @RequestMapping(value = "/reportManage/save")
    public String savePicture(@RequestParam("file") MultipartFile uploadFile ,HttpServletRequest request){
        try {

            //描述
            String reportType = request.getParameter("reportType");

            String report   = request.getParameter("report");
            String remark   = request.getParameter("remark");

            String[] userTypes = request.getParameterValues("userType");
            String userType = new String();
            String userTypeName = new String();
            //StringBuffer userType = new StringBuffer("");
            //StringBuffer userTypeName = new StringBuffer("");

            Map<String,Object> reportMap = new HashMap<>();
            reportMap.put("reportType",reportType);

            List<Map<String,Object>> reportTypeList = reportManageService.findReportType(reportMap);
            //String user = userType.toString().substring(0,userType.length()-1);
            //String typeName = userTypeName.substring(0,userTypeName.length()-1);
            Map<String, Object> map = new HashMap<>();
            map.put("reportType",reportType);//报表类型

            map.put("reportName",report);//报表名称
            map.put("reportTypeName",MapUtils.getString(reportTypeList.get(0),"reportTypeName"));
            map.put("reportRemark",remark);

            //String fileUrl = uploadFile(uploadFile);

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
            map.put("reportFile", url);
            map.put("reportFileName",fileName);


            //后台模拟post去调用转pfd 的接口
            String result  = uploadFilePdf(uploadFile);
            JSONObject jsStr = JSONObject.parseObject(result);
            Map<String,Object> pdfMap = (Map<String,Object>)jsStr.get("data");

            map.put("reportFilePdf", MapUtils.getString(pdfMap,"src"));

            for(int i=0;i<userTypes.length;i++){
                userType = userTypes[i];
                Map<String,Object> userMap = new HashMap<>();
                userMap.put("userType",userTypes[i]);
                List<Map<String,Object>> userList  = userTypeService.findUserType(userMap);
                userTypeName = MapUtils.getString(userList.get(0),"userTypeName");

                map.put("userType",userType);//适用对象
                map.put("userTypeName",userTypeName);//适用对象
                reportManageService.savePicture(map);
            }



        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/reportManage/control/";
    }

    @RequestMapping(value = "/reportManage/updateReport")
    public String updateReport(@RequestParam("file") MultipartFile uploadFile ,HttpServletRequest request,ReportManageInfo reportManageInfo){
        try {

            //描述
            String reportType = request.getParameter("reportType");

            String userType = request.getParameter("userType");

            Map<String,Object> reportMap = new HashMap<>();
            reportMap.put("reportType",reportType);

            List<Map<String,Object>> reportTypeList = reportManageService.findReportType(reportMap);

            Map<String,Object> userMap = new HashMap<>();
            userMap.put("userType",userType);
            List<Map<String,Object>> userList  = userTypeService.findUserType(userMap);

            String userTypeName = MapUtils.getString(userList.get(0),"userTypeName");

            Map<String, Object> map = new HashMap<>();
            map.put("reportType",reportType);//报表类型

            map.put("reportName",reportManageInfo.getReportName());//报表名称
            map.put("reportTypeName",MapUtils.getString(reportTypeList.get(0),"reportTypeName"));
            map.put("reportRemark",reportManageInfo.getReportRemark());
            map.put("userType",userType);//适用对象
            map.put("userTypeName",userTypeName);//适用对象
            map.put("reportId",reportManageInfo.getReportId());//id



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
                map.put("reportFile", url);
                map.put("reportFileName",fileName);


                //后台模拟post去调用转pfd 的接口
                String result  = uploadFilePdf(uploadFile);
                JSONObject jsStr = JSONObject.parseObject(result);
                Map<String,Object> pdfMap = (Map<String,Object>)jsStr.get("data");

                map.put("reportFilePdf", MapUtils.getString(pdfMap,"src"));
            }


            reportManageService.updateReport(map);



        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/reportManage/control/";
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
