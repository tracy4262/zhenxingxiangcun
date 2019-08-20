package com.ovit.nswy.member.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 截取视频截图，获取视频时长
 */
public class VideoUtil {

    private Logger logger = LogManager.getLogger(this.getClass());

    private double seconds;
    private String screenImg;

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videofile 源视频文件路径
     * @param framefile 截取帧的图片存放路径
     * @throws Exception
     */
    public void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int length = ff.getLengthInFrames();
        setSeconds(ff.getLengthInTime() / 100000 * 0.1);//单位秒
        int i = 0;
        Frame f = null;
        while (i < length) {
            // 过滤前100帧
            f = ff.grabFrame();
            if ((i > 100) && (f.image != null)) {
                break;
            }
            i++;
        }
        int owidth = f.image.width();
        int oheight = f.image.height();
        logger.info(owidth + "---" + oheight + "---" + seconds);
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Image img = f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        bi.getGraphics().drawImage(img, 0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public String getScreenImg() {
        return screenImg;
    }

    public void setScreenImg(String screenImg) {
        this.screenImg = screenImg;
    }

    @Test
    public void test() {
        VideoUtil util = new VideoUtil();
        try {
            util.fetchFrame("j://photo/V51226-090731.mp4", "j://photo/xxx.jpe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
