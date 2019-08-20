package com.ovit.manager.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class DocConverter {
    
    private String fileName;
    private File pdfFile;
    private File docFile;
    private File odtFile;
    
    

    public DocConverter(String sourceFile,String targetFile) throws Exception {

             fileName = sourceFile.substring(0, sourceFile.lastIndexOf("/")); 
             docFile = new File(sourceFile);//得到转换前的文件
             System.out.println("****sourceFile=" + sourceFile + "****");
                 //得到文件的不带后缀的名字s
             String s = sourceFile.substring(sourceFile.lastIndexOf("/") + 1,sourceFile.lastIndexOf("."));    
             fileName = fileName + "/" + s;    
             // 用于处理TXT文档转化为PDF格式乱码,获取上传文件的名称（不需要后面的格式＿    
             String txtName = sourceFile.substring(sourceFile.lastIndexOf(".")); //得到文件格式  
             // 判断上传的文件是否是TXT文件    
             if (txtName.equalsIgnoreCase(".txt")) {    
                 //是txt则需要转化为odt，然后再转为pdf
                 odtFile = new File(fileName + ".odt");    
                 // 将上传的文档重新copy丿份，并且修改为ODT格式，然后有ODT格式转化为PDF格式    
                 this.copyFile(docFile, odtFile);    
                 pdfFile = new File(targetFile); // 用于处理PDF文档    
             } else if (txtName.equals(".pdf") || txtName.equals(".PDF")) {    
                 pdfFile = new File(targetFile);    
                 this.copyFile(docFile, pdfFile);    
             } else{    
                 pdfFile = new File(targetFile);    
             }    
             doc2pdf();//调用函数进行转换
     }    
    private void copyFile(File sourceFile,File targetFile)throws Exception{
        //新建文件输入流并对它进行缓冲 
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff  = new BufferedOutputStream(output);
        // 缓冲数组 
        byte[]b = new byte[1024 * 5];
        int len;
        while((len = inBuff.read(b)) != -1){
            outBuff.write(b,0,len);
        }
        // 刷新此缓冲的输出浿
        outBuff.flush();
        // 关闭浿
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }
    private void doc2pdf() throws Exception {
        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                    converter.convert(docFile, pdfFile);
                    // close the connection
                    connection.disconnect();
                    System.out.println("****pdf转换成功，PDF输出＿" + pdfFile.getPath() + "****");
                } catch (java.net.ConnectException e) {
                    // ToDo Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("****swf转换异常，openoffice服务未启动！****");
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，读取转换文件失败****");
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                System.out.println("****已经转换为pdf，不需要再进行转化****");
            }
        } else {
            System.out.println("****swf转换器异常，需要转换的文档不存在，无法转换****");
        }
    }
   }