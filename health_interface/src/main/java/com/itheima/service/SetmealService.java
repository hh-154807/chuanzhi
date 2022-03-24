package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-09 19:47
 */
public interface SetmealService {

    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBysetmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void delete(Integer id);

    List<Setmeal> findAll();

    Setmeal findSetmealById(Integer id);

    List<Map<String, Object>> findSetmealCount();

}
