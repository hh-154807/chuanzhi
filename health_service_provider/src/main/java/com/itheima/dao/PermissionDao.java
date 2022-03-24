package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @ author He
 * @ create 2022-03-16 14:18
 */
public interface PermissionDao {
    public Set<Permission> findByRoleId(Integer roleId);
}
