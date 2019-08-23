package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.common.RedissonManager;
import com.mmall.service.IOrderService;
import com.mmall.utils.PropertiesUtil;
import com.mmall.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 定时任务  定时关闭订单
 */
@Slf4j
@Component
public class CloseOrderTask {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask(){
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout","5000"));
        //如果 之前存在此key 则设置失败
        Long setnxResult = RedisPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if(setnxResult!=null&&setnxResult.intValue()==1){
            //获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else {
            //为获取到锁 继续判断 判断时间戳 看是否可以重置并获取锁
            String lockValueStr = RedisPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if(lockValueStr!=null && System.currentTimeMillis()>Long.parseLong(lockValueStr)){
                String getSetResult = RedisPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                //可以用getSet重置锁 时间戳
                //返回给定的key的旧值 判断旧值 是否可以获取到锁
                //当key没有旧值时 即key不存在时 返回null 看获取锁
                //这里我们 set了一个新的值
                if(getSetResult==null||(getSetResult!=null&& StringUtils.equals(getSetResult,lockValueStr))){
                    //获取锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }else {
                    log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }else {
                log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask2(){
        //拿到锁
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock=false;
        try {
            if(getLock=lock.tryLock(0,50, TimeUnit.SECONDS)){
                log.info("Redisson获取到分布式锁:{} ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
                int hour=Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
                // iOrderService.closeOrder(hour);//获取到锁 执行业务
            }else {
                log.info("Redisson没有获取到分布式锁:{} ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.info("Redisson分布式锁获取异常",e);
        } finally {
            if(!getLock){//获取不到锁 返回
                return;
            }
            lock.unlock();//关闭锁
            log.info("Redisson分布式锁释放锁");
        }

    }

    private void closeOrder(String lockName){
        RedisPoolUtil.expire(lockName,5);
        log.info("获取{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        int hour=Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour"));
//        iOrderService.closeOrder(hour);
        RedisPoolUtil.del(lockName);
        log.info("释放{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());

    }

}
