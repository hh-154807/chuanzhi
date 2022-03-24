package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预约设置服务
 *
 * @ author He
 * @ create 2022-03-11 14:50
 */

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置数据
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否进行了预约设置
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    //已经进行了预约操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //没有预约，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {// yyyy-MM
        String begin = date + "-1";//2022-3-1
        String end = date + "-31";//2022-3-31
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //调用dao，根据日期范围预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> mapList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> m = new HashMap<>();
                m.put("date", orderSetting.getOrderDate().getDate());//获取日期数字（几号）
                m.put("number", orderSetting.getNumber());
                m.put("reservations", orderSetting.getReservations());
                mapList.add(m);
            }
        }
        return mapList;
    }

    //根据月份修改预约设置数据
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        int number = orderSetting.getNumber();
        //根据我们的日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if(count>0){
            //已经进行了预约设置，更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //没有预约，执行插入操作
            orderSettingDao.add(orderSetting);
        }

    }
}
