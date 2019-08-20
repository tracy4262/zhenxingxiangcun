package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryBank;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryBankService;
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
@RequestMapping("${adminPath}/nswy/dictionaryBank")
public class DictionaryBankController extends BaseController {

    @Autowired
    private DictionaryBankService dictionaryBankService;

    @RequestMapping("/control")
    public String findList(DictionaryBank dictionaryBank, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryBank> page = dictionaryBankService.findList(dictionaryBank,request,response);
        List<DictionaryBank> page = dictionaryBankService.findList(dictionaryBank);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryBankList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryBankAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryBankAdd";
    }


    @RequestMapping(value = "/save")
    public String saveBank( HttpServletRequest request,DictionaryBank dictionaryBank){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryBank.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryBank.setName(name);
            dictionaryBank.setValue(value);
            dictionaryBank.setRemark(remark);
            dictionaryBank.setPid(pid);
            dictionaryBank.setLevel(level);

            dictionaryBankService.saveBank(dictionaryBank);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryBank/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryBank dictionaryBank = new DictionaryBank();
        dictionaryBank.setId(id);
        List<DictionaryBank> dictionaryBanks = dictionaryBankService.findBankList(dictionaryBank);
        model.addAttribute("dictionaryBank",dictionaryBanks.get(0));

        return "modules/content/dictionaryManagement/dictionaryBankFrom";
    }

    @RequestMapping(value = "/updateBank")
    public String updateBank( HttpServletRequest request,DictionaryBank dictionaryBank){

        try {
            dictionaryBankService.updateBank(dictionaryBank);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryBank/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteBank(@RequestParam String id){

        dictionaryBankService.deleteBank(id);


        return "redirect:" + adminPath +"/nswy/dictionaryBank/control/";
    }
}
