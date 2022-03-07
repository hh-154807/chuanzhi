package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项处理
 *
 * @ author He
 * @ create 2022-03-06 16:45
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference//查找服务
    private CheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    //检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkItemService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //编辑检查项
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (RuntimeException e) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    //通过id查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItem);
        } catch (RuntimeException e) {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (RuntimeException e) {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
