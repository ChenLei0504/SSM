package com.learn.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    List<User> getUserList();
}
