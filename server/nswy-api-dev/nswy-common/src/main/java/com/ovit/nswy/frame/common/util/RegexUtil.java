package com.ovit.nswy.frame.common.util;

import com.google.common.collect.Lists;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${huipei.x} on 2017-10-24.
 */
@SuppressWarnings("ALL")
public class RegexUtil {

    public final static String REG_CHINIESE = "[\\u4E00-\\u9FA5]+";
    public final static String REG_REDUPLICATION = "(.)\\1+";
    public final static int[] areaCode = { 1601, 1637, 1833, 2078, 2274,2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    public final static String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x", "y", "z" };

    public static boolean regChinese(String content) {
        Pattern pattern = Pattern.compile(REG_CHINIESE);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static Object countOfRegChinese(String params) {
        int length = 0;
        int count = 0;
        Pattern pattern = Pattern.compile(RegexUtil.REG_CHINIESE);
        char c[] = params.toCharArray();
        length = c.length;
        for (int i = 0; i < length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if (matcher.matches()) {
                count++;
            }

        }
        return count;
    }

        public static List<Object> objectValue (String params){
            List<Object> valueList = Lists.newArrayList();
            int length = 0;
            char c[] = params.toCharArray();
            length = c.length;
            for (int i = 0; i < length; i++) {
                char value = params.charAt(i);
                valueList.add(value);
            }
            return valueList;
        }

    public static String  StringFilter(String content){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return  m.replaceAll("").trim();
    }
    public static String  StringFilterHtml(String content){
        String regEx_html = "<[^>]+>";
        Pattern pattern = Pattern.compile(regEx_html);
        Matcher matcher = pattern.matcher(content);
        String string = matcher.replaceAll("");
        return string;
    }



    public static boolean regReduplication(String content) {
        Pattern pattern = Pattern.compile(REG_REDUPLICATION);
        Matcher matcher = pattern.matcher(content);
         return matcher.matches();
    }

    /**
     * 查看某字符串中是否包含数字
     * @param company
     * @return
     */
    public static boolean isContainNumber(String company) {

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 字符串编码转换
     * @param str 要转换编码的字符串
     * @param charsetName 原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName,String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    /**
     * 取得给定汉字的首字母,即声母
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= areaCode[i]
                            && li_SecPosCode < areaCode[i + 1]) {
                        chinese = letters[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese.toUpperCase();
    }

    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

}