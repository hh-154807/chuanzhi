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
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String outPutPath;//从属性文件读取要生成的HTML对应的路径

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //将图片名称保存到redis集合
        String img = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
        //添加套餐后需要重新生成静态页面（套餐列表，套餐详情：多个）
        generateMobileStaticHtml();

    }

    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml() {
        //在生成静态页面之前查询数据
        List<Setmeal> list = setmealDao.findAll();
        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);

    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        //为模板提供数据
        map.put("setmealList",list);
        genarateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal : list) {
           Map map= new HashMap<>();
           map.put("setmeal",setmealDao.findSetmealById(setmeal.getId()));
            genarateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }


    //通用生成静态页面
    public void genarateHtml(String templateName, String htmlName, Map map) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        FileWriter writer = null;
        try {
            Template template = configuration.getTemplate(templateName);
            writer = new FileWriter(new File(outPutPath + "/" + htmlName));
            template.process(map, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        //添加套餐后需要重新生成静态页面（套餐列表，套餐详情：多个）
        generateMobileStaticHtml();
    }

    @Override
    public void delete(Integer id) {
        //删除套餐对应的检查组
        setmealDao.deleteAssoication(id);
        //根据id删除检查组
        setmealDao.deleteSetmealById(id);
        generateMobileStaticHtml();
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    //根据套餐id查询套餐详情，（套餐基本信息，对应检查组与检查项信息）
    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealDao.findSetmealById(id);
    }

    //查询套餐预约占比
    public List<Map<String, Object>> findSetmealCount() {
        return  setmealDao.findSetmealCount();
    }

    //设置套餐和检查组多对多关联关系
    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", setmealId);
                map.put("checkgroup_id", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }


}
