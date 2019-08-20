package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Card;
import com.ovit.nswy.member.model.FastDFSClient;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.CardService;
import com.ovit.nswy.member.util.QRCodeUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * 名片管理模块
 *
 * @author yangfan
 */
@RestController
@RequestMapping("/Card")
public class CardController {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private CardService cardService;
    @Value("${http.uploadIp}")
    private String uploadIp;
    @Value("${fdfs.conf}")
    private String conf;
    @Value("${temp_upload_url}")
    private String tempUrl;

    //将file转化为byte
    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] readInputStream(String imgurl) throws Exception {
        // new一个URL对象
        URL url = new URL(imgurl);
        // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式为"GET"
        conn.setRequestMethod("GET");
        // 超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    //查询
    @RequestMapping(value = "/selectQuery/{page}")
    public Result selectAll(@PathVariable String page, @RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Result result = new Result();
        String card_name = (String) params.get("cardName");
        String name = (String) params.get("name");
        String type = (String) params.get("type");
        if (null == loginUser) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        }
        Card card = new Card();
        card.setCardName(card_name);
        card.setName(name);
        card.setType(type);
        card.setUserAccount(loginUser.getLoginAccount());
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Card> list = cardService.selectQuery(currentPage, pageSize, card);
        result.setData(list);
        return result;
    }

    //id查询
    @RequestMapping(value = "/fingById", method = RequestMethod.POST)
    public Result fingById(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        int id = (int) params.get("id");
        Card card = cardService.fingById(id);
        result.setData(card);
        return result;
    }

    //添加
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
        int num = 0;
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String userAccount = loginUser.getLoginAccount();
        if (userAccount != null) {
            String card_name = (String) params.get("cardName");
            String name = (String) params.get("name");
            String type = (String) params.get("type");
            String synopsis = (String) params.get("synopsis");
            String picture = (String) params.get("picture");
            Card card = new Card();
            card.setCardName(card_name);
            card.setCreateTime(new Date());
            card.setUpdateTime(new Date());
            card.setName(name);
            card.setType(type);
            card.setSynopsis(synopsis);
            card.setPicture(picture);
            card.setUserAccount(userAccount);

            byte[] data = readInputStream(picture);
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("temp.jpg");
            // 创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            // 写入数据
            outStream.write(data);
            // 关闭输出流
            outStream.close();

            //生成二维码
            File directory = new File("");
            String QRFile = tempUrl;
            String encode = QRCodeUtil.encode("个人/企业名称:  " + card.getName(), imageFile.getPath(), QRFile, true);
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
            String fdfsClientConfigFilePath = conf;
            FastDFSClient client = new FastDFSClient(fdfsClientConfigFilePath);
            String uploadUrl = client.uploadFile(file2byte(encode), "jpg");
            String qRcode = uploadIp + "/" + uploadUrl.substring(11, uploadUrl.length());
            card.setQRcode(qRcode);
            num = cardService.insert(card);
            result.setData(num);
        } else {
            result.setData(num);
        }
        return result;
    }

    //删除
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        int id = (int) params.get("id");
        int a = cardService.remove(id);
        result.setData(a);
        return result;
    }

    //查看二维码
    @RequestMapping(value = "/createQR", method = RequestMethod.POST)
    public Result createQR(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
        Result result = new Result();
        int id = (int) params.get("id");
        Card card = cardService.fingById(id);
        result.setData(card.getQRcode());
        return result;
    }

}
