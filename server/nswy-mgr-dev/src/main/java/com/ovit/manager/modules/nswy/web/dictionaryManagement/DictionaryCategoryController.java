package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCategory;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy/dictionaryCategory")
public class DictionaryCategoryController extends BaseController {

    @Autowired
    private DictionaryCategoryService dictionaryCategoryService;

    @RequestMapping("/control")
    public String findList(DictionaryCategory dictionaryCategory, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryCategory> page = dictionaryCategoryService.findList(dictionaryCategory,request,response);
        List<DictionaryCategory> page = dictionaryCategoryService.findList(dictionaryCategory);
        model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryCategoryList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id, @RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryCategoryAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,
                         @RequestParam(value = "level") Integer level,
                         Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryCategoryAdd";
    }


    @RequestMapping(value = "/save")
    public String saveCategory( HttpServletRequest request,DictionaryCategory dictionaryCategory){

        try {

            String pinyin = request.getParameter("pinyin");
            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String dictType = "ba4d3d9c-cc38-4ddb-a7f2-25cd1323c2f7";

            dictionaryCategory.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryCategory.setName(name);
            dictionaryCategory.setValue(value);
            dictionaryCategory.setRemark(remark);
            dictionaryCategory.setPid(pid);
            dictionaryCategory.setLevel(level);
            dictionaryCategory.setDictType(dictType);
            dictionaryCategory.setPinyin(pinyin);

            dictionaryCategoryService.saveCategory(dictionaryCategory);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryCategory/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryCategory dictionaryCategory = new DictionaryCategory();
        dictionaryCategory.setId(id);
        List<DictionaryCategory> dictionaryCategorys = dictionaryCategoryService.findCategoryList(dictionaryCategory);
        model.addAttribute("dictionaryCategory",dictionaryCategorys.get(0));

        return "modules/content/dictionaryManagement/dictionaryCategoryFrom";
    }

    @RequestMapping(value = "/updateCategory")
    public String updateCategory( HttpServletRequest request,DictionaryCategory dictionaryCategory){

        try {
            dictionaryCategoryService.updateCategory(dictionaryCategory);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryCategory/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteCategory(@RequestParam String id){

        dictionaryCategoryService.deleteCategory(id);
        return "redirect:" + adminPath +"/nswy/dictionaryCategory/control/";
    }
}
