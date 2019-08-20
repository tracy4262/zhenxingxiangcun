package com.ovit.nswy.converter.service.impl;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.ovit.nswy.converter.fastdfs.FastDFSClient;
import com.ovit.nswy.converter.service.UpLoadService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * DESCRIPTION:
 * 文件上传接口
 * @author zouyan
 * @create 2018-01-29 下午4:32
 * created by fuck~
 **/
@Service
public class UpLoadServiceImpl implements UpLoadService {

    private static final Logger logger = LogManager.getLogger(UpLoadServiceImpl.class);

    @Value("${fdfs.http.uploadIp}")
    private String uploadIp;

    @Value("${fdfs.conf}")
    private String conf;

    @Value("${open-office.ip}")
    private String openOfficeIp;

    @Value("${open-office.port}")
    private String openOfficePort;

    @Value("${open-office.filePath}")
    private String openOfficePath;

    private static OpenOfficeConnection connection;

    private StreamOpenOfficeDocumentConverter converter;

    @PostConstruct
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
    }

    @PreDestroy
    public void destory() {
        try {
            connection.disconnect();
            logger.info("open office disconnect");
        } catch (Exception e) {
            logger.error("open office disconnect error!", e);
        }
    }

    /**
     * 文件上传至fastdfs，返回fastdfs文件访问地址
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> uploadFile(MultipartFile uploadFile) throws Exception {
        Map<String,Object> fileMap = new HashMap<>();
        //获取文件名
        String fileName = uploadFile.getOriginalFilename().toLowerCase();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        FastDFSClient client = new FastDFSClient(conf);
        //上传原始文件至fastdfs
        String uploadUrl = client.uploadFile(uploadFile.getBytes(), suffixName);
        String fileUrl = new StringBuffer("http://").append(uploadIp).append("/").append(uploadUrl.substring(11, uploadUrl.length())).toString();
        fileMap.put("fileUrl", fileUrl);
        logger.debug("上传文件:{}, 上传后的文件地址:{}", fileName, fileUrl);
        if ("pdf".equals(suffixName) || "jpg".equals(suffixName) || "png".equals(suffixName)) {
            return fileMap;
        }
        //pdf转换
        String originalFilePathName = openOfficePath + "/" + UUID.randomUUID() + "-" + fileName;
        File originalfile = new File(originalFilePathName);
        originalfile.createNewFile();
        uploadFile.transferTo(originalfile);
        String pdfFilePathName = originalFilePathName.substring(0, originalFilePathName.lastIndexOf(".")) + ".pdf";
        File pdfFile = new File(pdfFilePathName);
        converter.convert(originalfile, pdfFile);
        //上传pdf至fastdfs
        String pdfUploadUrl = client.uploadFile(FileUtils.readFileToByteArray(pdfFile), "pdf");
        String pdfFileUrl = new StringBuffer("http://").append(uploadIp).append("/").append(pdfUploadUrl.substring(11, pdfUploadUrl.length())).toString();
        fileMap.put("pdfFileUrl", pdfFileUrl);
        logger.debug("上传文件:{}, 上传后的文件pdf地址:{}", fileName, pdfFileUrl);
        //删除临时创建文件
        originalfile.delete();
        pdfFile.delete();
        return fileMap;
    }

    /**
     * 图片base64上传至fastdfs，返回fastdfs文件访问地址
     * @param fileName
     * @param fileData
     * @return
     * @throws Exception
     */
    @Override
    public String uploadBase64Img(String fileName, String fileData) throws Exception {
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        FastDFSClient client = new FastDFSClient(conf);
        //上传原始文件至fastdfs
        String uploadUrl = client.uploadFile(Base64.decodeBase64(fileData), suffixName);
        String fileUrl = new StringBuffer("http://").append(uploadIp).append("/").append(uploadUrl.substring(11, uploadUrl.length())).toString();
        logger.debug("base64图片上传,文件名:{}, 上传后的文件地址:{}", fileName, fileUrl);
        return fileUrl;
    }
}
