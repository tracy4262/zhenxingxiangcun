package com.ovit.nswy.member.feigen;

import com.ovit.nswy.member.certification.model.SpeciesQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "member" ,url = "${feign.client.url}")
public interface MemberServiceFeign {

    @RequestMapping(value = "member/proxy/smsSendMasUserInfo",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object smsSendMasUserInfo(@RequestBody Map<String,Object> params);
}
