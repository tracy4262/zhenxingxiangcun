
package com.ovit.nswy.im.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;


/**
 * DESCRIPTION:
 * 配置支持jsonp 支持前端跨域请求
 * @author zouyan
 * @create 2018-01-23 下午6:08
 * created by fuck~
 **/

//@ControllerAdvice
//(basePackages = "com.ovit.nswy.im.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpAdvice() {
        super("callback","jsonp");
    }
}
