package com.ovit.nswy.member.model.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ColumnSettingsUtils {



    public static Object CreateDefaultColumn() {
        ColumnSettingsDetailRequest detail = new ColumnSettingsDetailRequest();
        detail.setAuthority(0);
        detail.setName("动态");
        detail.setStatus(true);

        ColumnSettingsDetailRequest detail1 = new ColumnSettingsDetailRequest();
        detail1.setAuthority(1);
        detail1.setName("政策");
        detail1.setStatus(true);

        ColumnSettingsDetailRequest detail2 = new ColumnSettingsDetailRequest();
        detail2.setAuthority(2);
        detail2.setName("知识");
        detail2.setStatus(true);

        ColumnSettingsDetailRequest detail3 = new ColumnSettingsDetailRequest();
        detail3.setAuthority(0);
        detail3.setName("产品");
        detail3.setStatus(true);

        ColumnSettingsDetailRequest detail4 = new ColumnSettingsDetailRequest();
        detail4.setAuthority(0);
        detail4.setName("服务");
        detail4.setStatus(true);

        ColumnSettingsDetailRequest detail5 = new ColumnSettingsDetailRequest();
        detail5.setAuthority(0);
        detail5.setName("标准");
        detail5.setStatus(true);

        List<ColumnSettingsDetailRequest> list = new ArrayList<>();
        list.add(detail);
        list.add(detail1);
        list.add(detail2);
        list.add(detail3);
        list.add(detail4);
        list.add(detail5);

        return list;
    }

    public static List<UserSettingsRequest> CreateDefaultUserSettingsColumn() {
        String defaultSettings = "[{\"typeName\": \"动态\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]},{\"typeName\": \"政策\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}, {\"typeName\": \"知识\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}, {\"typeName\": \"资讯\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}, {\"typeName\": \"产品\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}, {\"typeName\": \"服务\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}, {\"typeName\": \"标准\",\"userSettings\": [{\"index\": 0,\"name\": \"全部\"}]}]";
        return json2List(defaultSettings);
    }



    public static List<UserSettingsRequest> json2List(String label) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<UserSettingsRequest> ret = mapper.readValue(label,List.class);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(CreateDefaultUserSettingsColumn()));
    }
}
