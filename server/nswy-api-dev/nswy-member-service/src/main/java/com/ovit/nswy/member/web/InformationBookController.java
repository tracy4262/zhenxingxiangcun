package com.ovit.nswy.member.web;

import com.ovit.nswy.member.service.InformationBookService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/informationBook")
public class InformationBookController {

    private static final Logger logger = LogManager.getLogger(InformationBookController.class);


    @Autowired
    private InformationBookService informationBookService;

    /**
     * 传参示例：
     {
     "detailId" : 1
     }
     * 根据detailId查询bookInfo信息
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBookInfo", method = RequestMethod.POST)
    public Result getBookInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            int detailId = Integer.valueOf(params.get("detailId").toString());
            Map<String, Object> dataMap = informationBookService.getInformationBookInfoByDetailId(detailId);
            result.setData(dataMap);
            result.setCode(ResultCode.SUCCESS);
            logger.debug("根据detailId:{} 获取对应的bookInfo", detailId);
        } catch (Exception e) {
            logger.error("根据detailId查询bookInfo信息出错！", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询bookInfoList信息
     * @return
     */
    @RequestMapping(value = "/getBookInfoList")
    public Result getBookInfoList() {
        Result result = new Result();
        try {

            List<Map<String, Object>> dataList = informationBookService.getInformationBookInfoList();
            result.setData(dataList);
            result.setCode(ResultCode.SUCCESS);
            logger.trace("获取bookInfoList:{}", dataList);
        } catch (Exception e) {
            logger.error("查询bookInfoList信息出错！", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 测试方法 因detailId此处无法获取
     {
     "isBook": "1",
     "book_cover_photo": "1",
     "book_author": "book_author",
     "book_edition": "123",
     "book_publish": "12312",
     "book_sheet": "321",
     "book_distribution": "123",
     "book_broadsheet": "312",
     "book_print_time": "",
     "book_word_count": "13",
     "book_pub_date": "",
     "book_paper": "123",
     "book_label": "哈哈哈哈"
     }
     * 插入bookInfo信息关联information-detailId
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertBookInfo", method = RequestMethod.POST)
    public Result insertBookInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            //若发布的为资讯-图书 图书单独存储至information_book_info
            if (MapUtils.getString(params, "isBook") != null) {
                Map<String, Object> bookMap = new HashMap<String, Object>();
                //FIXME
                //bookMap.put("informationDetailId", detail.getId());
                bookMap.put("informationDetailId", new Random().nextInt(100));
                bookMap.put("coverPhoto", MapUtils.getString(params, "book_cover_photo"));
                bookMap.put("author", MapUtils.getString(params, "book_author"));
                bookMap.put("edition", MapUtils.getString(params, "book_edition"));
                bookMap.put("publish", MapUtils.getString(params, "book_publish"));
                bookMap.put("sheet", MapUtils.getString(params, "book_sheet"));
                bookMap.put("distribution", MapUtils.getString(params, "book_distribution"));
                bookMap.put("broadsheet", MapUtils.getString(params, "book_broadsheet"));
                bookMap.put("printTime", MapUtils.getString(params, "book_print_time"));
                bookMap.put("wordCount", MapUtils.getString(params, "book_word_count"));
                bookMap.put("pubDate", MapUtils.getString(params, "book_pub_date"));
                bookMap.put("paper", MapUtils.getString(params, "book_paper"));
                bookMap.put("label", MapUtils.getString(params, "book_label"));
                informationBookService.insert(bookMap);
                //System.out.println(bookMap.get("ID"));
                logger.debug("新增资讯-图书发布，成功插入数据至bookInfo, id:{}", bookMap.get("ID"));
            }
        } catch (Exception e) {
            logger.error("插入数据至bookInfo出错！", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }




}
