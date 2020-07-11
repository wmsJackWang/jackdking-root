package com.jta.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jta.model.spring.User;
import com.jta.model.test.People;
import com.jta.service.PeopleService;
import com.jta.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONArray;
import unifiedreponse.ResponseData;
import unifiedreponse.ResponseDataUtil;
import unifiedreponse.ResultEnums;

@RestController
@Api(value = "Mybatis测试接口", tags = "OperationApi")
public class OperationApi {
    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The People service.
     */
    @Autowired
    PeopleService peopleService;

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取全部用户信息", notes = "查询全部", response = ResponseData.class)
    public ResponseData getAll() {
//        return ApiResult.prepare().success(JSONParseUtils.object2JsonString(userService.selectAll()));
    	return ResponseDataUtil.buildSuccess(JSON.toJSONString(userService.selectAll()));
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping("/getPeopleAll")
    @ApiOperation(value = "获取全部人类信息", notes = "查询全部", response = ResponseData.class)
    public ResponseData getPeopleAll() {
//        return ApiResult.prepare().success(JSONParseUtils.object2JsonString(peopleService.selectAll()));
    	return ResponseDataUtil.buildSuccess(JSON.toJSONString(peopleService.selectAll()));
    }


    /**
     * 同时操作两个数据源，并且试一个数据源操作失败，测试回滚.
     *
     * @param peopleName the people name
     * @param userName   the user name
     * @return the api result
     * @throws Exception the exception
     */
    @RequestMapping(value = "/insertPeopleAndUser", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加两个表", notes = "测试分布式事务", response = ResponseData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "peopleName", value = "人名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户信息", required = true, dataType = "String")
    })
    public ResponseData insertPeopleAndUser(String peopleName, String userName) throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setPassword("15251251");
        user.setAge(22);
        People people = new People();
        people.setName(peopleName);
        people.setAge(20);
        people.setSex("男");
        Boolean isSuccess = peopleService.insertUserAndPeople(user, people);
        if (isSuccess) {
//            return ApiResult.prepare().success("同时添加两表成功!");
        	return ResponseDataUtil.buildSuccess("同时添加两表成功!");
        }
//        return ResponseData.prepare().error(JSON.toJSONString(people), 500, "添加失败，全部回滚");
        return ResponseDataUtil.buildError("添加失败，全部回滚");
    }

    @PostMapping(value = "/insertPeopleAndUserV2", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加两个表V2", notes = "测试分布式事务", response = ResponseData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "peopleName", value = "人名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户信息", required = true, dataType = "String")
    })
    public ResponseData insertPeopleAndUserV2(String peopleName, String userName) throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setPassword("15251251");
        user.setAge(22);
        People people = new People();
        people.setName(peopleName);
        people.setAge(20);
        people.setSex("男");
        Boolean isSuccess = peopleService.insertUserAndPeopleV2(user, people);
        if (isSuccess) {
//            return ApiResult.prepare().success("同时添加两表成功!");
        	return ResponseDataUtil.buildSuccess("同时添加两表成功!");
        }
//        return ApiResult.prepare().error(JSONParseUtils.object2JsonString(people), 500, "添加失败，全部回滚");
        return ResponseDataUtil.buildError(ResultEnums.ERROR.getCode(), "添加失败，全部回滚", JSON.toJSONString(people));
    }
}
