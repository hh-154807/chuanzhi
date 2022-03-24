package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

/**
 * @ author He
 * @ create 2022-03-16 14:13
 */
public interface RoleDao {
    public Set<Role> findByUserId(Integer id);
}
