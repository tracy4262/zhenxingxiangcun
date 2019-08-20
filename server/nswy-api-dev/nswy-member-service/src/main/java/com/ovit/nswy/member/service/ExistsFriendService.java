package com.ovit.nswy.member.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "relationship",url = "${feign.client.url}")
public interface ExistsFriendService {

    @RequestMapping(value = "/relationship/friend/getExistsFriend/{account}/{fromAccount}",method = RequestMethod.GET)
    public Object getExistsFriend(@PathVariable("account") String account, @PathVariable("fromAccount") String fromAccount);

}