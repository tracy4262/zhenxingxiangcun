package com.ovit.nswy.member.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ManageController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    /*
     * @Autowired private ManageService manageService;
     */

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public boolean login(HttpServletRequest request) {
        String name = request.getParameter("loginName");
        String password = request.getParameter("password");
        boolean result = Boolean.FALSE;
        if (!(StringUtils.isEmpty(name) || StringUtils.isEmpty(password))) {
            request.getSession().setAttribute(name, result);
        }
        return result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String Test() {
        String str = "hello axios";
        return str;
    }
}