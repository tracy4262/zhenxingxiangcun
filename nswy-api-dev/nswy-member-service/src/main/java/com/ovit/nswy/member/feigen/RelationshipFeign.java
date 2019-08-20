package com.ovit.nswy.member.feigen;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "relationship" ,url = "${feign.client.url}")
public interface RelationshipFeign {
    @RequestMapping(value = "relationship/friend/getFriendByAccount",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object getFriendByAccount(@RequestBody String account);
}
