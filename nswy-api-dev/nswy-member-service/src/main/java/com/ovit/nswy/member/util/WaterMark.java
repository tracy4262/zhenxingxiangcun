package com.ovit.nswy.member.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class WaterMark {
    /**
     * 图片添加水印
     * @param srcImgPath 需要添加水印的图片的路径
     * @param outImgPath 添加水印后图片输出路径
     * @param markContentColor 水印文字的颜色
     * @param waterMarkContent 水印的文字
     */
    public static BufferedImage mark(InputStream inputStream, Color markContentColor, String waterMarkContent, int fontSize) {
        BufferedImage bufImg = null;
        try {
            // 读取原图片信息
            // File srcImgFile = new File(srcImgPath);
            // Image srcImg = ImageIO.read(srcImgFile);
            Image srcImg = ImageIO.read(inputStream);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色

            g.setFont(font);
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
            int y = srcImgHeight - 3;
            //int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;
            //int y = srcImgHeight / 2;
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            return bufImg;
            // 输出图片
            // FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            // ImageIO.write(bufImg, "jpg", outImgStream);
            // outImgStream.flush();
            // outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImg;
    }

    /**
     * 获取水印文字总长度
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /*public static void main(String[] args) {
        String originalImg = "D:/gaoyuanyuan2.jpeg";
        String targetImg = "D:/gaoyuanyuan2 - 副本.jpeg";
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        mark(originalImg, targetImg, Color.WHITE, "农事无忧");
    }*/
}
