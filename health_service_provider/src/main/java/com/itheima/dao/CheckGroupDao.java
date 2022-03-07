package com.itheima.dao;

import com.itheima.pojo.CheckGroup;

import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-07 14:55
 */
public interface CheckGroupDao {

    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map map);

}
