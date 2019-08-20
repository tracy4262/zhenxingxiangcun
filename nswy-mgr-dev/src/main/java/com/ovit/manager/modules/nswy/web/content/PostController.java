package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;
import com.ovit.manager.modules.nswy.service.PostService;
import com.ovit.manager.modules.nswy.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 职务管理
 *
 * @author wangrongf
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class PostController extends BaseController{
    @Autowired
    private PostService postService;


    @RequestMapping(value = "/post/control")
    public String pictureList(PostInfo postInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        String postName = request.getParameter("postName");
        if(StringUtils.isNotBlank(postName)){
            postInfo.setPostName(postName);
        }
        List<PostInfo> page = postService.findPostInfo(postInfo);

        model.addAttribute("list",page);

        return "modules/content/postList";

    }

    @RequestMapping(value = "/post/add")
    public String add(@RequestParam String id, Model model){
        model.addAttribute("pid",id);
        return "modules/content/postAdd";
    }

    @RequestMapping(value = "/post/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);
        return "modules/content/postAdd";
    }


    @RequestMapping(value = "/post/save")
    public String saveUnit( HttpServletRequest request,PostInfo postInfo){

        try {

            String postName = request.getParameter("postName");

            String pid = request.getParameter("pid");


            postInfo.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            postInfo.setPostName(postName);
            postInfo.setPid(pid);

            postService.savePost(postInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/post/control/";
    }

    @RequestMapping(value = "/post/form")
    public String update(@RequestParam String id,Model model){
        PostInfo postInfo = new PostInfo();
        postInfo.setId(id);
        List<PostInfo> postInfos = postService.findPostInfo(postInfo);
        model.addAttribute("postInfo",postInfos.get(0));

        return "modules/content/postFrom";
    }

    @RequestMapping(value = "/post/updatePost")
    public String updatePost( HttpServletRequest request,PostInfo postInfo){

        try {
            postService.updatePost(postInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/post/control/";
    }

    @RequestMapping(value = "/post/delete")
    public String deletePost(@RequestParam String id){

        postService.deletePost(id);


        return "redirect:" + adminPath +"/nswy/post/control/";
    }

}
