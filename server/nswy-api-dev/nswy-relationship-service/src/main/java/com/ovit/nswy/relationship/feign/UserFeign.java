package com.ovit.nswy.relationship.feign;


import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.request.ListFollowQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.transform.Result;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-6.
 */
@FeignClient(name = "member",url ="${feign.client.url}")
public interface UserFeign {


    @RequestMapping(value = "member/user/getUserMemberName",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object getUserMemberName(@RequestBody GetUserQuery getUserQuery);

    @RequestMapping(value = "member/user/getUserByQuery",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object getUserByQuery(@RequestBody GetUserQuery getUserQuery);

    @RequestMapping(value = "member/user/listfollow",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object listfollow(@RequestBody ListFollowQuery listFollowQuery);

    @RequestMapping(value = "member/user/updateStatusByaccount/{account}/{toAccount}",method = RequestMethod.GET)
    Object updateStatusByaccount(@PathVariable("account") String account,@PathVariable("toAccount") String toAccount);

    @RequestMapping(value = "member/user/getRealNameAndStatus",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object getRealNameAndStatus(@RequestBody Map<String,Object> map);

    @RequestMapping(value = "member/user/getDisplayNameByAccount",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object getDisplayNameByAccount(@RequestBody String account);

    @RequestMapping(value = "member/relationshipCircle/findGroupFriendOfIm",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    Object findGroupFriendOfIm(@RequestBody Map<String,Object> map);
}
