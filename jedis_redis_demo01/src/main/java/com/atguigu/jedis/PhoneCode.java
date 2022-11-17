package com.atguigu.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Random;

/**
 * PhoneCode
 *
 * @author fj
 * @date 2022/10/2 11:06
 */
public class PhoneCode {
    public static void main(String[] args) {
            //verifyCode("122");
            getRedisCode("122","455960");
    }

    //验证码校验
    public static void getRedisCode(String phone,String code){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode=jedis.get(codeKey);
        //判断验证码
        if (redisCode.equals(code)){
            System.out.println("验证成功");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }


    //每个手机每天只能发送3次，验证码放到redis中，设置过期时间为120
    public static void verifyCode(String phone){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode"+phone+":count";
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        //每个手机吗每天只能发送3次
        String count = jedis.get(countKey);
        if (count==null){
            //没有发送过
            //设置发送次数为1
            jedis.setex(countKey,24*60*60,"1");
        } else if (Integer.parseInt(count)> 2) {
            //如果发送次数小于等于2
            //发送次数加一
            jedis.incr(countKey);
        }else {
            System.out.println("今天的发送次数已经超过3次");
            jedis.close();
        }
        String code=getCode();
        System.out.println("code = " + code);
        jedis.setex(codeKey,120,code);
        jedis.close();
    }
    //生成6位数字验证码
    public static String getCode(){
        Random random = new Random();
        String code="";
        for (int i = 0; i < 6;i++) {
            int rand = random.nextInt(10);
            code+=rand;
        }
        return code;
    }
}
