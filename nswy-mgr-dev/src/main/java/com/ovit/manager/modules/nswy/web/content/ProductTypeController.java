package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ProductTypeInfo;
import com.ovit.manager.modules.nswy.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * 产品类型
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class ProductTypeController extends BaseController {

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping(value = "/productType/control")
    public String productTypeList(ProductTypeInfo productTypeInfo, Model model, HttpServletRequest request, HttpServletResponse response){


        List<ProductTypeInfo> page = productTypeService.findList(productTypeInfo);

        model.addAttribute("list",page);

        return "modules/content/productTypeList";

    }

    @RequestMapping(value = "/productType/add")
    public String add(@RequestParam String id, Model model){
        model.addAttribute("pid",id);
        return "modules/content/productTypeAdd";
    }

    @RequestMapping(value = "/productType/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);
        return "modules/content/productTypeAdd";
    }


    @RequestMapping(value = "/productType/save")
    public String saveProduct( HttpServletRequest request,ProductTypeInfo productTypeInfo){

        try {

            List<ProductTypeInfo> productTypeInfos = productTypeService.findByPidDesc(productTypeInfo);

            if(productTypeInfos.size()>0){
                ProductTypeInfo info  = productTypeInfos.get(0);

                String classificationCode = info.getClassificationCode();
                Integer code  = Integer.parseInt(classificationCode.substring(classificationCode.length()-2))+1;

                StringBuffer classification = new StringBuffer(classificationCode.substring(0,classificationCode.length()-2));

                classificationCode = classification.append(code).toString();

                productTypeInfo.setClassificationCode(classificationCode);

            }else{
                productTypeInfos = productTypeService.findByIdDesc(productTypeInfo);

                ProductTypeInfo info  = productTypeInfos.get(0);

                String classificationCode = info.getClassificationCode();

                StringBuffer classification = new StringBuffer(classificationCode);

                classificationCode = classification.append("01").toString();

                productTypeInfo.setClassificationCode(classificationCode);

            }

            productTypeInfo.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            if(productTypeInfo.getPid().equals("0")){
                productTypeInfo.setPid(null);
            }
            productTypeService.saveProduct(productTypeInfo);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return "redirect:" + adminPath +"/nswy/productType/control/";
    }

    @RequestMapping(value = "/productType/form")
    public String update(@RequestParam String id,Model model){
        ProductTypeInfo productTypeInfo = new ProductTypeInfo();
        productTypeInfo.setId(id);
        List<ProductTypeInfo> postInfos = productTypeService.findList(productTypeInfo);
        model.addAttribute("productTypeInfo",postInfos.get(0));

        return "modules/content/productTypeFrom";
    }

    @RequestMapping(value = "/productType/updateProductType")
    public String updatePost( HttpServletRequest request,ProductTypeInfo productTypeInfo){

        try {
            productTypeService.updateProduct(productTypeInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/productType/control/";
    }

    @RequestMapping(value = "/productType/delete")
    public String deletePost(@RequestParam String id){

        productTypeService.deleteProduct(id);


        return "redirect:" + adminPath +"/nswy/productType/control/";
    }
}
