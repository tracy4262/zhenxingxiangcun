package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.Friend;
import com.ovit.nswy.member.service.FriendService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private FriendService friendService;

    @RequestMapping("/")
    public Result findFriends() {
        List<Friend> list = friendService.findAll();
        Result result = new Result();
        result.setData(list);
        return result;
    }
}
