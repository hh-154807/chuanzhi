package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ author He
 * @ create 2022-03-11 19:45
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealControllerM {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    //查询所有套餐
    public Result getSetmeal() {
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
    @RequestMapping("/findSetmealById")
    //根据套餐id查询套餐详情，（套餐基本信息，对应检查组与检查项信息）
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findSetmealById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
