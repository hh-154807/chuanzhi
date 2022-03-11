package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-09 19:48
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;


    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //将图片名称保存到redis集合
        String img = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());

    }


    public Setmeal findById(Integer id) {
        return setmealDao.findByID(id);
    }

    @Override
    public List<Integer> findCheckGroupIdsBysetmealId(Integer id) {
        return setmealDao.findCheckGroupIdsBysetmealId(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改套餐基本信息，操作检查组t_setmeal表
        setmealDao.edit(setmeal);
        //清理当前套餐关联的检查组 操作中间关系表
        Integer setmealId = setmeal.getId();
        setmealDao.deleteAssoication(setmealId);
        //重新建立检查组与套餐之间的关系
        this.setSetmealAndCheckGroup(setmealId, checkgroupIds);
//        //将图片名称保存到redis集合
//        String img = setmeal.getImg();
//        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    @Override
    public void delete(Integer id) {
        //删除套餐对应的检查组
        setmealDao.deleteAssoication(id);
        //根据id删除检查组
        setmealDao.deleteSetmealById(id);
    }

    //设置套餐和检查组多对多关联关系
    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmeal_id",setmealId);
                map.put("checkgroup_id",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

}
