package com.ovit.nswy.member.web;

import com.ovit.nswy.member.service.CmsArticleService;
import com.ovit.nswy.member.web.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cmsArticle")
public class CmsArticleController {
    /**
     *
     */
    @Autowired
    private CmsArticleService cmsArticleService;

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public Result article() throws Exception {
        Result result = new Result();
        result.setData(cmsArticleService.findArticle());
        return result;
    }
}