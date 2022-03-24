package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @ author He
 * @ create 2022-03-16 13:41
 */
public interface UserService {
    public User findByUsername(String username);
}
