package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-13 15:25
 */
public interface OrderService {
    public Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
