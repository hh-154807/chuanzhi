package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 体检套餐管理
 *
 * @ author He
 * @ create 2022-03-09 18:19
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //使用redispoo操作redis服务
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();//原始文件名
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index);//.jpg
        System.out.println(extention);
        String fileName = UUID.randomUUID().toString() + extention;
        //将文件上传到七牛云
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);

        } catch (IOException e) {

            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL, fileName);
        }
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {

            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);
    }

    //按照id查询套餐
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
         Setmeal setmeal = setmealService.findById(id);
            //查询成功
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            //查询失败
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
    //查询所有检查组
    @RequestMapping("/findCheckGroupIdsBysetmealId")
    public Result findAll(Integer id) {
        try {
            List<Integer> list = setmealService.findCheckGroupIdsBysetmealId(id);
            //查询成功
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {
            //查询失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    //编辑套餐
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.edit(setmeal,checkgroupIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }
    //根据套餐id删除套餐
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setmealService.delete(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {

            //删除失败
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}
