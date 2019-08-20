package com.ovit.nswy.member.util;

import com.google.zxing.LuminanceSource;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BufferedImageLuminanceSource extends LuminanceSource {
	private final BufferedImage image;
	private final int left;
	private final int top;

	public BufferedImageLuminanceSource(BufferedImage image) {
		this(image, 0, 0, image.getWidth(), image.getHeight());
	}

	public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
		super(width, height);
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		if (left + width > sourceWidth || top + height > sourceHeight) {
			throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		}
		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				if ((image.getRGB(x, y) & 0xFF000000) == 0) {
					image.setRGB(x, y, 0xFFFFFFFF); // = white
				}
			}
		}
		this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		this.left = left;
		this.top = top;
	}

	public byte[] getRow(int y, byte[] row) {
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		image.getRaster().getDataElements(left, top + y, width, 1, row);
		return row;
	}

	public byte[] getMatrix() {
		int width = getWidth();
		int height = getHeight();
		int area = width * height;
		byte[] matrix = new byte[area];
		image.getRaster().getDataElements(left, top, width, height, matrix);
		return matrix;
	}

	public boolean isCropSupported() {
		return true;
	}

	public LuminanceSource crop(int left, int top, int width, int height) {
		return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
	}

	public boolean isRotateSupported() {
		return true;
	}

	public LuminanceSource rotateCounterClockwise() {
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
		BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(image, transform, null);
		g.dispose();
		int width = getWidth();
		return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
	}

    /**拼音字母工具类
     * Created by ${huipei.x} on 2017-6-1.
     */
    public static class PinyinUtil {

        private PinyinUtil() {
            throw new IllegalAccessError("Utility class");
        }

        /**
         * 将汉字转换为全拼
         *
         * @param src 源汉字
         * @return String pin yin
         */
        public static String getPinYin(String src) {
            char[] t1 = src.toCharArray();
            String[] t2;
            // 设置汉字拼音输出的格式
            HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
            t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            t3.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuilder t4 = new StringBuilder();
            try {
                for (char aT1 : t1) {
                    // 判断是否为汉字字符
                    if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                        t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);// 将汉字的几种全拼都存到t2数组中
                        t4.append(t2[0]);// 取出该汉字全拼的第一种读音并连接到字符串t4后
                    } else {
                        // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                        t4.append(Character.toString(aT1));
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                System.out.println(e);
            }
            return t4.toString();
        }

        /**
         * 将汉字转换为全拼
         *
         * @param src 源汉字
         * @return String camel pin yin
         */
        public static String getCamelPinYin(String src) {
            char[] t1 = src.toCharArray();
            String[] t2;
            // 设置汉字拼音输出的格式
            HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
            t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            t3.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuilder t4 = new StringBuilder();
            String t;
            try {
                for (char aT1 : t1) {
                    // 判断是否为汉字字符
                    if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                        t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);// 将汉字的几种全拼都存到t2数组中
                        t = t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                    } else {
                        // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                        t = Character.toString(aT1);
                    }
                    t = t.substring(0, 1).toUpperCase() + t.substring(1);
                    t4.append(t);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                System.out.println(e);
            }
            return t4.toString();
        }

        /**
         * 提取每个汉字的首字母
         *
         * @param str 源汉字
         * @return String pin yin head char
         */
        public static String getPinYinHeadChar(String str) {
            StringBuilder convert = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char word = str.charAt(j);
                // 提取汉字的首字母
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    convert.append(pinyinArray[0].charAt(0));
                } else {
                    convert.append(word);
                }
            }
            return convert.toString();
        }

        /**
         * 提取每个汉字的大写首字母
         *
         * @param str 源汉字
         * @return String pin yin head upper char
         */
        public static String getPinYinHeadUpperChar(String str) {
            return getPinYinHeadChar(str).toUpperCase();
        }

          /* public static void main(String[] args) throws Exception {
            System.out.println(getPinYinHeadUpperChar("云飞扬"));
        }*/
    }
}
