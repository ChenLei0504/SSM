package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2018-10-08
 */
public interface IUserService extends IService<User> {

    List<User> getUserList();
}
