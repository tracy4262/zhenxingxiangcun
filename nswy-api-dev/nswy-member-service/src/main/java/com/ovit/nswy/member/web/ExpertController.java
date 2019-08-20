package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Expert;
import com.ovit.nswy.member.model.ExpertInfo;
import com.ovit.nswy.member.service.ExpertInfoService;
import com.ovit.nswy.member.service.ExpertService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ExpertInfoService expertInfoService;

    @RequestMapping("/")
    public Result showExp() {
        PageInfo<Expert> list = expertService.query(0, 9);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 专家展示
     *
     * @return
     */
    @RequestMapping("/find/{page}")
    public Result showExp(@PathVariable String page, HttpServletRequest request) {
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Map<String, Object>> list = expertInfoService.query(currentPage, pageSize);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 搜索查询分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findExpertTitle/{page}", method = RequestMethod.GET)
    public Result findExpertTitle(@PathVariable String page, HttpServletRequest request) {
        String title = request.getParameter("title");
        Map<String, Object> map = new HashMap<String, Object>();
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Expert> list = expertService.findExpertTitle(currentPage, pageSize, title);
        map.put("list", list);
        map.put("title", title);
        Result result = new Result();
        result.setData(map);
        return result;
    }
}
