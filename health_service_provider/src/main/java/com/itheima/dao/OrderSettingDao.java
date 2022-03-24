package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-11 14:54
 */
public interface OrderSettingDao {



    public void add(OrderSetting orderSetting);

    public void editNumberByOrderDate(OrderSetting orderSetting);

    public long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    public void editReservationsByOrderDate(OrderSetting orderSetting);

    OrderSetting findByOrderDate(Date parseString2Date);
}
