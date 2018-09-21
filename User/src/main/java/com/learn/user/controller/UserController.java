package com.learn.user.controller;

import com.learn.entity.User;
import com.learn.user.dao.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author 陈雷
 * @date 2018-09-13
 */
@Api(value = "UserController", description = "用户登录登出接口")
@RequestMapping("/sys/user")
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @ApiOperation(value = "mapper获取所有", notes = "用户所有")
    @GetMapping("/getList")
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @ApiOperation(value = "MybatisPlus获取所有", notes = "用户所有")
    @GetMapping("/getList1")
    public List<User> getUserList1() {
        return userMapper.selectList(null);
    }

    @ApiOperation(value = "添加")
    @PostMapping("")
    public int add(@RequestBody User user) {
        return userMapper.insert(user);
    }

}
