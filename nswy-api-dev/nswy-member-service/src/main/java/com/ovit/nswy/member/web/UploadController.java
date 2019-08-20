package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.FastDFSClient;
import com.ovit.nswy.member.util.WaterMark;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Value("${http.uploadIp}")
    private String uploadIp;

    @Value("${fdfs.conf}")
    private String conf;

    @Value("${temp_upload_url}")
    private String uploadFilePath;

    @Value("${water_mark.content}")
    private String content;

    @Value("${water_mark.font_size}")
    private int fontSize;

    @Value("${water_mark.font_color.r}")
    private int r;

    @Value("${water_mark.font_color.g}")
    private int g;

    @Value("${water_mark.font_color.b}")
    private int b;

    /**
     * 上传图片、视频、文件
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public Result uploadPicture(MultipartFile upfile) {
        Result result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "error");
            result = new Result();
            String type = upfile.getOriginalFilename().toLowerCase();
            type = type.substring(type.lastIndexOf("."));
            // =====上传服务器开始======
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
            String fdfsClientConfigFilePath = conf;
            FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
            String uploadUrl = client.uploadFile(upfile.getBytes(), type.substring(1, type.length()));
            map.put("picName", uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length()));
            if(".avi".equals(type) || ".mp4".equals(type) || ".mkv".equals(type) || ".rmvb".equals(type) || ".kux".equals(type)){
                map.put("status", 0);
            }else{
                map.put("status", 1);
            }
            result.setData(map);
        } catch (Exception e) {
            logger.error("上传失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 上传图片并添加水印
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/upWithWaterMark", method = RequestMethod.POST)
    public Result uploadPictureWithWaterMark(MultipartFile upfile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "error");
        Result result = new Result();
        try {
            String type = upfile.getOriginalFilename().toLowerCase();
            type = type.substring(type.lastIndexOf("."));
            String fdfsClientConfigFilePath = conf;
            FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
            BufferedImage bufImg = WaterMark.mark(upfile.getInputStream(), new Color(r, g, b), content, fontSize);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufImg, "png", baos);
            String uploadUrl = client.uploadFile(baos.toByteArray(), type.substring(1, type.length()));
            map.put("picName", uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length()));
            map.put("status", 1);
            result.setData(map);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "pdf")
    public String add_action(MultipartFile upfile) {
        try {
            String filename = upfile.getOriginalFilename();
            InputStream is = upfile.getInputStream();
            // 如果服务器已经存在和上传文件同名的文件，则输出提示信息
            //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
            String realPath = this.getClass().getResource("/").getPath();
            File tempFile = new File(uploadFilePath + filename);

            //request.getSession().getServletContext().getRealPath(pathDir)
            //File file = new File(savePath);
            //判断上传文件的保存目录是否存在
            if (!tempFile.exists() && !tempFile.isDirectory()) {
                logger.info(tempFile + "目录不存在，需要创建");
                //创建目录
                tempFile.mkdirs();
            }
       /*if (tempFile.exists()) {
		 boolean delResult = tempFile.delete(); 
		 System.out.println("删除已存在的文件：" + delResult); 
	 } */
            // 开始保存文件到服务器
            if (!filename.equals("")) {
                FileOutputStream fos = new FileOutputStream(uploadFilePath + filename);
                byte[] buffer = new byte[8192]; // 每次读8K字节
                int count = 0;
                // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count); // 向服务端文件写入字节流
                }
                fos.close(); // 关闭FileOutputStream对象
                is.close(); // InputStream对象
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}