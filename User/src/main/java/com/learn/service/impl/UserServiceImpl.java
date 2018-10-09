package com.learn.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.User;
import com.learn.mapper.UserMapper;
import com.learn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    public List<User> getUserList() {
        return userMapper.getUserList();
    }
}
