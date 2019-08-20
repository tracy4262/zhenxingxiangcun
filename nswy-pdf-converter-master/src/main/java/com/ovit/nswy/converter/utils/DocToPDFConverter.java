package com.ovit.nswy.converter.utils;

import java.io.*;
import java.net.URL;

import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;


/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-03-09 上午9:43
 * created by fuck~
 **/
public class DocToPDFConverter extends Converter {

  //  private static PhysicalFontFactory FONT_FACTORY = PhysicalFontFactory.getInstance();


    public DocToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }


    @Override
    public void convert() throws Exception{

        loading();

        InputStream iStream = inStream;


        WordprocessingMLPackage wordMLPackage = getMLPackage(iStream);

        configSimSunFont(wordMLPackage); //配置中文字体

        processing();
        Docx4J.toPDF(wordMLPackage, outStream);

        finished();

    }

    protected WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception{
        PrintStream originalStdout = System.out;

        //Disable stdout temporarily as Doc convert produces alot of output
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));

        WordprocessingMLPackage mlPackage = Doc.convert(iStream);

        System.setOut(originalStdout);
        return mlPackage;
    }

    protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
        File inFile = new File(inputFilePath);
        FileInputStream iStream = new FileInputStream(inFile);
        return iStream;
    }

    protected static OutputStream getOutFileStream(String outputFilePath) throws IOException{
        File outFile = new File(outputFilePath);

        try{
            //Make all directories up to specified
   //         outFile.getParentFile().mkdirs();
        } catch (NullPointerException e){
            //Ignore error since it means not parent directories
        }

        outFile.createNewFile();
        FileOutputStream oStream = new FileOutputStream(outFile);
        return oStream;
    }

    /*public static void main(String[] args) throws Exception {
        String inPath = "/Users/fuck/Desktop/test.doc";
        String outPath = "/Users/fuck/Desktop/test.pdf";
        boolean shouldShowMessages = true;
        InputStream inStream = getInFileStream(inPath);
        OutputStream outStream = getOutFileStream(outPath);

        Converter converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true);
        converter.convert();
    }*/

    protected static void configSimSunFont(WordprocessingMLPackage wordMLPackage) throws Exception {
        Mapper fontMapper = new IdentityPlusMapper();
        wordMLPackage.setFontMapper(fontMapper);

        String fontFamily = "SimSun";

        URL simsunUrl = DocToPDFConverter.class.getResource("/fonts/simsun.ttc"); //加载字体文件（解决linux环境下无中文字体问题）
        PhysicalFonts.addPhysicalFont(fontFamily, simsunUrl);
        PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
        fontMapper.put(fontFamily, simsunFont);

        RFonts rfonts = Context.getWmlObjectFactory().createRFonts(); //设置文件默认字体
        rfonts.setAsciiTheme(null);
        rfonts.setAscii(fontFamily);
        wordMLPackage.getMainDocumentPart().getPropertyResolver()
                .getDocumentDefaultRPr().setRFonts(rfonts);
    }

}
