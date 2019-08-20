package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.mutable.Integers;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;

import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.modules.nswy.dao.ProxyGovInfoDao;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import com.ovit.manager.modules.nswy.entity.ProxyGovInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Service
@Transactional(readOnly = true)
public class ProxyGovInfoService extends CrudService<ProxyGovInfoDao, ProxyGovInfo> {

    private static Logger logger = LoggerFactory.getLogger(ProxyGovInfoService.class);
    @Autowired
    private ProxyGovInfoDao proxyGovInfoDao;

    public Page<ProxyGovInfo> findList(ProxyGovInfo proxyGovInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<ProxyGovInfo> page = this.findPage(new Page<ProxyGovInfo>(request, response), proxyGovInfo);
        logger.info("代理审核列表查询：{}"+page.getList());
        return page;
    }

   public ProxyGovInfo getProxyGovInfoById(Integer proxyGovInfoId){
       ProxyGovInfo proxyGovInfo = new ProxyGovInfo();
       proxyGovInfo.setProxyGovInfoId(proxyGovInfoId);
       if(Integers.isNotEmpty(proxyGovInfoId)){
           proxyGovInfo=proxyGovInfoDao.selectByPrimaryKey(proxyGovInfo);

       }
       logger.info("通过审核Id查询代理数据{}"+proxyGovInfo);
      return proxyGovInfo;
   }
    @Transactional()
     public int  updateProxyGovInfo(ProxyGovInfo proxyGovInfo){
        Integer result=0;
        if(Integers.isNotEmpty(proxyGovInfo.getProxyGovInfoId())){
            String status = String.valueOf(proxyGovInfo.getApproveStatus());
            if("1".equals(status)){
                String url = Global.getConfig("api.url")+"/member/proxy/smsSendUserInfo";
                JSONObject jsonBody=new JSONObject();
                jsonBody.put("id",proxyGovInfo.getProxyGovInfoId());
                jsonBody.put("TABLE_NAME","proxy_gov_info");
                jsonBody.put("INFO_NAME","gov_info");
                jsonBody.put("name","gov_name");
                try {
                    String rows=   HttpUtil.doPost(url, jsonBody.toString());
                    JSONObject object = JSONObject.parseObject(rows);
                    if(object.get("code").toString().equals("500")){
                        logger.info("发送短信失败{}"+ rows);
                        System.out.println("发送短信失败{}"+rows);
                        result=0;
                    }else{
                        if(proxyGovInfo.getRemark().equals("")){
                            proxyGovInfo.setRemark("同意");
                        }
                        result= proxyGovInfoDao.updateByPrimaryKeySelective(proxyGovInfo);
                        proxyGovInfoDao.updateLoginUser(proxyGovInfo);
                    }
                    System.out.println("发送短信状态{}"+ rows);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(proxyGovInfo.getRemark().equals("")){
                    proxyGovInfo.setRemark("不同意");
                }
                result= proxyGovInfoDao.updateByPrimaryKeySelective(proxyGovInfo);
                proxyGovInfoDao.updateGovInfo(proxyGovInfo);
            }
        }
        logger.info("通过审核Id修改代理数据{}"+result);
        return result;
    }

    @Transactional()
    public int  deleteProxyGovInfo(Integer proxyGovInfoId){
        Integer result=null;
        ProxyGovInfo proxyGovInfo = new ProxyGovInfo();
        proxyGovInfo.setProxyGovInfoId(proxyGovInfoId);
        if(Integers.isNotEmpty(proxyGovInfoId)){
            result= proxyGovInfoDao.deleteByPrimaryKey(proxyGovInfo);
        }
        logger.info("通过审核Id删除代理数据{}"+result);
        return result;
    }


    @Transactional()
    public String startImportExcel(File file){

        if (!file.exists())
            System.out.println("文件不存在");
        try {
            //1.读取Excel的对象
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
            //2.Excel工作薄对象
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            //3.Excel工作表对象
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            //总行数
            int rowLength = hssfSheet.getLastRowNum()+1;
            //4.得到Excel工作表的行
            HSSFRow hssfRow = hssfSheet.getRow(0);
            //总列数
            int colLength = hssfRow.getLastCellNum();
            //得到Excel指定单元格中的内容
            HSSFCell hssfCell = hssfRow.getCell(0);
            //得到单元格样式
            CellStyle cellStyle = hssfCell.getCellStyle();



            for (int i = 0; i < rowLength; i++) {

                Map<String,Object> map = new HashMap<>();
                map.put("pid","0");
                //获取Excel工作表的行
                HSSFRow hssfRow1 = hssfSheet.getRow(i);

                for (int j = 0; j < colLength; j++) {

                    //获取指定单元格
                    HSSFCell hssfCell1 = hssfRow1.getCell(j);

                    //Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
                    //Cannot get a STRING value from a NUMERIC cell
                    //将所有的需要读的Cell表格设置为String格式
                    if (hssfCell1 != null) {
                        hssfRow1.getCell(j).setCellType(hssfCell1.CELL_TYPE_STRING);

                        String postName  = String.valueOf(hssfCell1.getStringCellValue());
                        map.put("postName",postName);
                        /*String technicalTitleName  = String.valueOf(hssfCell1.getStringCellValue());
                        map.put("technicalTitleName",technicalTitleName);*/
                        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");

                        Map<String,Object> postMap = proxyGovInfoDao.findPost(map);
                        if(MapUtils.isNotEmpty(postMap)){
                            //判断是否重复pid 不重复 保存
                            String pid = MapUtils.getString(postMap,"pid");
                            if(MapUtils.getString(map,"pid").equals(pid)){
                                map.put("pid",MapUtils.getString(postMap,"id"));
                            }else{
                                Thread.sleep(1000);
                                map.put("id",uuid);
                                //map.put("titleType",0);
                                proxyGovInfoDao.insertPost(map);
                                map.put("pid",uuid);
                            }

                        }else{
                            Thread.sleep(1000);
                            map.put("id",uuid);
                            //map.put("titleType",0);
                            proxyGovInfoDao.insertPost(map);
                            map.put("pid",uuid);
                        }
                    }

                }


            }
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }



}
