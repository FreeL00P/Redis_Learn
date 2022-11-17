package com.atguigu.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JedisDemo1
 *
 * @author fj
 * @date 2022/10/1 23:03
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        //创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String ping = jedis.ping();
        System.out.println("ping = " + ping);
    }

    @Test
    public void demo1(){
        //创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.flushDB();
//        //添加key value
//        jedis.set("k1", "3306");

//        //获取key对应的value
//        String k1 = jedis.get("k1");
//        System.out.println("k1 = " + k1);

        jedis.mset("k1","v1","k2","v2");
//                //显示所有key
//        Set<String> keys= jedis.keys("*");
//        keys.forEach(System.out::println);

        List<String> mget = jedis.mget("k1", "k2");
        mget.forEach(System.out::println);
    }
    //操作list
    @Test
    public void demo2() {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        jedis.rpush("k51","v1","v2","v3");
        //取出list所有值
        List<String> k51= jedis.lrange("k51", 0, -1);
        System.out.println("k51 = " + k51);
    }
    //操作set
    @Test
    public void demo3() {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        jedis.sadd("s1","1","2","3");

        Set<String> s1 = jedis.smembers("s1");
        s1.forEach(System.out::println);
    }

    //操作hash
    @Test
    public void demo4() {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        jedis.hset("h1","name","张三");
        jedis.hset("h1","age","18");
        String hget = jedis.hget("h1", "name");
        System.out.println("hget = " + hget);


        Map<String, String> map = new HashMap<>();
        map.put("name","炸");
        map.put("age","23");
        jedis.hmset("h2",map);
    }
}
