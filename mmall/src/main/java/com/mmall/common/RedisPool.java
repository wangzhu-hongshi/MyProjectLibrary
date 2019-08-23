package com.mmall.common;

import com.mmall.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 */
public class RedisPool {
    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal= Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
    private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));//在jedispool中最大的idle（空闲）状态的jedis实例的个数
    private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));//在jedispool中最小的idle（空闲）状态的jedis实例的个数
    private static Boolean testOnBorrow =Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true 代表得到的时候可以使用
    private static Boolean testOnReturn =Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")); //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true 代表返回的实例可以使用
    private static String ip=PropertiesUtil.getProperty("redis1.ip");
    private static Integer host=Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));


    //初始化的方法
    private static void initPool(){
        //配置对象
        JedisPoolConfig config=new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        //连接耗尽的时候 是否阻塞 false会抛出异常 true阻塞直到超时 默认时true
        config.setBlockWhenExhausted(true);

        pool=new JedisPool(config,ip,host,1000*2);//创建连接池
    }
    //随着类加载而加载 调用方法进行初始化
    static {
        //调用初始化的方法
        initPool();
    }
    //从连接池里获取连接对象
    public static Jedis getJedis(){
        return pool.getResource();
    }
    //回收不可用的连接对象
    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }
    //回收可用的连接对象
    public static void retrunResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis=pool.getResource();

        jedis.set("ke","nihao");
        retrunResource(jedis);
        System.out.println("chengg");
    }

}
