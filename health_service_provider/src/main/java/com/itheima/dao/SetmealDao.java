package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-09 19:50
 */
public interface SetmealDao {
    public void add(Setmeal setmeal);

    public void setSetmealAndCheckGroup(Map<String, Integer> map);

    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds);

    Page<CheckGroup> findByCondition(String queryString);
}
