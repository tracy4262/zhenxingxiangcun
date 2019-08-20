package com.ovit.nswy.relationship.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.relationship.model.request.ListLiveQuery;
import com.ovit.nswy.relationship.model.request.LiveCategoryQuery;
import com.ovit.nswy.relationship.model.request.LiveQuery;
import com.ovit.nswy.relationship.model.response.LiveRoomDTO;
import com.ovit.nswy.relationship.service.LiveRoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-11.
 */
@RestController
@RequestMapping("/live")
@Api(tags = "/live",description="直播管理")
public class LiveController {
    @Autowired
    private LiveRoomService liveRoomService;

    @PostMapping("/listlive")
    public Response listlive(@RequestBody ListLiveQuery listLiveQuery){
        PageInfo<LiveRoomDTO> result= liveRoomService.listlive(listLiveQuery) ;
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/saveOrUpdateCategory")
    public Response saveOrUpdateCategory(@RequestBody LiveCategoryQuery liveCategoryQuery){
        Integer result= liveRoomService.saveOrUpdateCategory(liveCategoryQuery) ;
        return Response.successResult(result);
    }

    @PostMapping("/saveOrUpdateLive")
    public Response saveOrUpdateLive(@RequestBody LiveQuery liveQuery){
        Integer result= liveRoomService.saveOrUpdateLive(liveQuery) ;
        return Response.successResult(result);
    }

    @GetMapping("/getLive/{liveId}")
    public Response getLivebyliveId(@PathVariable Integer liveId){
        LiveRoomDTO result= liveRoomService.getLivebyliveId(liveId) ;
        return Response.successResult(result);
    }

    @PostMapping("/findLiveListByAccount")
    public Response findLiveListByAccount(@RequestBody ListLiveQuery listLiveQuery){
        Map<String,Object> result= liveRoomService.findLiveListByAccount(listLiveQuery) ;
        return Response.successResult(result);
    }
}
