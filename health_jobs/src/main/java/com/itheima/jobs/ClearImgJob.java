package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @ author He
 * @ create 2022-03-10 16:02
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        //根据redis中板寸的两个集合差值计算，获得垃圾图片名称的集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set!=null){
            for (String picName : set) {
                //清理垃圾图片，删除服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从redi集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);

            }
        }
    }
}
