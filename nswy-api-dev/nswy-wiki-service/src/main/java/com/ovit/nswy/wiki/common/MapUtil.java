package com.ovit.nswy.wiki.common;

import com.github.xphsc.util.ObjectUtil;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-12.
 */
public class MapUtil {

    public static boolean isEmpty(Map map, String key) {
        if (map == null){
            return true;
        }
        if (!map.containsKey(key)) {
            return true;
        }
        return ObjectUtil.isEmpty(map.get(key));
    }

}
