package com.ovit.nswy.member.feigen;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "shop" ,url = "${feign.client.url}")
public interface ShopServiceFeign {

    @RequestMapping(value = "shop/pushShopCommodity/findRecommendProduct",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object findRecommendProduct(@RequestBody Map<String, Object> params);
}
