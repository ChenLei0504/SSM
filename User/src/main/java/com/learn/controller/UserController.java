package com.learn.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.entity.User;
import com.learn.service.IUserService;
import com.learn.service.impl.UserServiceImpl;
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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-08
 */
@RestController
@RequestMapping("/User1/user")
@Api(description = "ces")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "mapper获取所有", notes = "用户所有")
    @GetMapping("/getList")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @ApiOperation(value = "MybatisPlus获取所有", notes = "用户所有")
    @GetMapping("/getList1")
    public List<User> getUserList1() {
        return userService.list(null);
    }

    @ApiOperation(value = "添加")
    @PostMapping("")
    public boolean add(@RequestBody User user) {
        return userService.save(user);
    }
}
