package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPost;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUnit;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryPostService;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryPost")
public class DictionaryPostController extends BaseController {

    @Autowired
    private DictionaryPostService dictionaryPostService;

    @RequestMapping("/control")
    public String findList(DictionaryPost dictionaryPost, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryPost> page = dictionaryPostService.findList(dictionaryPost,request,response);
        List<DictionaryPost> page = dictionaryPostService.findList(dictionaryPost);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryPostList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryPostAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryPostAdd";
    }


    @RequestMapping(value = "/save")
    public String savePost( HttpServletRequest request,DictionaryPost dictionaryPost){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryPost.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryPost.setName(name);
            dictionaryPost.setValue(value);
            dictionaryPost.setRemark(remark);
            dictionaryPost.setPid(pid);
            dictionaryPost.setLevel(level);

            dictionaryPostService.savePost(dictionaryPost);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPost/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryPost dictionaryPost = new DictionaryPost();
        dictionaryPost.setId(id);
        List<DictionaryPost> dictionaryPosts = dictionaryPostService.findPostList(dictionaryPost);
        model.addAttribute("dictionaryPost",dictionaryPosts.get(0));

        return "modules/content/dictionaryManagement/dictionaryPostFrom";
    }

    @RequestMapping(value = "/updatePost")
    public String updatePost( HttpServletRequest request,DictionaryPost dictionaryPost){

        try {
            dictionaryPostService.updatePost(dictionaryPost);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryPost/control/";
    }

    @RequestMapping(value = "/delete")
    public String deletePost(@RequestParam String id){

        dictionaryPostService.deletePost(id);


        return "redirect:" + adminPath +"/nswy/dictionaryPost/control/";
    }
}
