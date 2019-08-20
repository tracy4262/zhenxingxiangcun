package com.ovit.nswy.wiki.feigen;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "member" ,url = "${feign.client.url}")
public interface RelevantInfoFeign {
    @RequestMapping(value = "member/speciesWiki/getRelevantInfo",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object getRelevantInfo(@RequestBody Map<String, Object> params);
}

