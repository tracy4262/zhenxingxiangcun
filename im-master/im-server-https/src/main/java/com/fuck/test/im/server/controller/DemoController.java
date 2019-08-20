package com.fuck.test.im.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DESCRIPTION:
 * 模拟外部接口调用获取首页
 * @author zouyan
 * @create 2018-01-09 下午4:26
 * created by fuck~
 **/
@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        return "/demo/index";
    }

    @RequestMapping("/anchor")
    public String anchor() {
        return "/demo/anchor";
    }

    @RequestMapping("/visitor")
    public String visitor() {
        return "/demo/visitor";
    }
}
