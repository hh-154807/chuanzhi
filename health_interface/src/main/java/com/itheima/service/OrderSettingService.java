package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @ author He
 * @ create 2022-03-11 14:41
 */
public interface OrderSettingService {
    public void add(List<OrderSetting> list);

    List<Map> getOrderSettingByMonth(String data);

    void editNumberByDate(OrderSetting orderSetting);
}
