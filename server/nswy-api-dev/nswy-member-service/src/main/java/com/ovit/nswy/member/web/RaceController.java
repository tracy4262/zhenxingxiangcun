package com.ovit.nswy.member.web;

import com.ovit.nswy.member.web.base.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取民族
 *
 * @author yangxl
 */
@RestController
@RequestMapping("/race")
public class RaceController {

    private final static String[] RACE_DATA = {"汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族",
            "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族",
            "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族",
            "仫佬族", "羌族", "布朗族", "撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族", "普米族", "塔吉克族",
            "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "崩龙族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族",
            "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族"};


    //查询
    @RequestMapping(value = "")
    public Result selectAll() {
        List<Race> raceList = new ArrayList<Race>();
        for (String str : RACE_DATA) {
            raceList.add(new Race(str));
        }
        Result result = new Result();
        result.setData(raceList);
        return result;
    }

    class Race {
        private String value;
        private String label;

        public Race(String str) {
            this.value = str;
            this.label = str;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

}
