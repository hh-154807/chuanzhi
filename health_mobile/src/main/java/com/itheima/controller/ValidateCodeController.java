package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 验证码操作
 *
 * @ author He
 * @ create 2022-03-13 14:31
 */
@RestController
@RequestMapping("/ValidateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    //用户在线预约发送验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        //随机生成一个四位的数字
//        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        Integer validateCode = 1234;//没有短信服务，用常量1234模拟
        //给用户发送验证码
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode.toString());
//        } catch (ClientException e) {
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }
        //将验证码保存到redis（五分钟有效）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, String.valueOf(validateCode));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    //用户快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //随机生成一个四位的数字
//        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        Integer validateCode = 888888;//没有短信服务，用常量888888模拟
        //给用户发送验证码
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode.toString());
//        } catch (ClientException e) {
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }
        //将验证码保存到redis（五分钟有效）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 300, String.valueOf(validateCode));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
