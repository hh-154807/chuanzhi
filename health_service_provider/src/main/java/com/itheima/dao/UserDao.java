package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @ author He
 * @ create 2022-03-16 14:08
 */
public interface UserDao {
    public User findByUsername(String username);
}
