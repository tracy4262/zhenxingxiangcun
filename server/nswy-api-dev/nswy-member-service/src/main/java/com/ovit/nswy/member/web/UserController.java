package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.request.GetUserQuery;
import com.ovit.nswy.member.model.request.ListFollowQuery;
import com.ovit.nswy.member.model.request.UserQuery;
import com.ovit.nswy.member.model.response.FansDTO;
import com.ovit.nswy.member.model.response.ListAppDTO;
import com.ovit.nswy.member.model.response.UserDTO;
import com.ovit.nswy.member.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
@RestController
@RequestMapping("/user")
@Api(tags = "/api/user", description = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/listUser")
    @ApiOperation(value = "用户列表接口", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listUser(@ApiParam(required = true, value = "用户列表参数类") @RequestBody Map<String,Object> params) {
        PageInfo<UserDTO> result = userService.listUser(params);
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/getUserByQuery")
    @ApiOperation(value = "用户列表接口", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getUserByQuery(@RequestBody GetUserQuery getUserQuery) {
        UserDTO result = userService.getUserByQuery(getUserQuery);
        return Response.successResult(result);
    }

    @PostMapping("/getUserMemberName")
    @ApiOperation(value = "用户列表接口", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getUserMemberName(@RequestBody GetUserQuery getUserQuery) {
        UserDTO result = userService.getUserMemberName(getUserQuery);
        return Response.successResult(result);
    }

    @GetMapping("/getAppStartFlag/{account}")
    @ApiOperation(value = "获取应用接口", response = ListAppDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ListAppDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getAppStartFlag(@ApiParam(required = true, value = "用户账号") @PathVariable String account) {
        ListAppDTO result = userService.getAppStartFlag(account);
        return Response.successResult(result);
    }

    @GetMapping("/updateStartFlag/{id}/{startFlag}")
    @ApiOperation(value = "应用是否已开始使用", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateStartFlag(@ApiParam(required = true, value = "应用Id") @PathVariable Integer id, @ApiParam(required = true, value = "应用是否已开始使用[0 未开始 1已开始]") @PathVariable Integer startFlag) {
        Integer result = userService.updateStartFlag(startFlag, id);
        return Response.successResult(result);
    }


    @PostMapping("/listfollow")
    @ApiOperation(value = "获取关注接口", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listfollow( @RequestBody ListFollowQuery listFollowQuery) {
        List<FansDTO> result = userService.listfollow(listFollowQuery);
        return Response.successResult(result);
    }

    @GetMapping("/updateStatusByaccount/{account}/{toAccount}")
    public Response updateStatusByaccount(@PathVariable String account,@PathVariable String toAccount) {
       Integer result = userService.updateStatusByaccount(account,toAccount);
        return Response.successResult();
    }

    @RequestMapping("/getRealNameAndStatus")
    public Response getRealNameAndStatus(@RequestBody Map<String,Object> map){
        Map<String,Object> map1 = userService.queryRealNameAndStatus(map);
        return Response.successResult(map1);
    }

    @RequestMapping("/getDisplayNameByAccount")
    public Response getDisplayNameByAccount(@RequestBody String account){
        Map<String,Object> map1 = userService.getDisplayNameByAccount(account);
        return Response.successResult(map1);
    }

}
