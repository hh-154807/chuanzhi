package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查组管理
 *
 * @ author He
 * @ create 2022-03-07 14:43
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage(queryPageBean);
    }

    //根据id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            //查询成功
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            //查询失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    //根据检查组id查询检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {

            //新增失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //根据检查组id删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
           checkGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {

            //删除失败
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    //查询所有项目组
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            //查询成功
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {
            //查询失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

}
