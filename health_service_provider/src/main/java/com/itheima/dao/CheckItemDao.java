package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ author He
 * @ create 2022-03-06 17:42
 */

public interface CheckItemDao {

    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(@Param("value") String queryString);

    public long findCountByCheckItemId(Integer id);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();

    public CheckItem findCheckItemById(Integer id);
}

