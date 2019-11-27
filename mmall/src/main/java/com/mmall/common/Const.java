package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量类
 *
 */
public class Const {
    //存入session中的key
    public static final String CURRENT_USER="currentUser";

    public static final String EMAIL="email";
    public static final String USERNAME="username";

    public static final String TOKEN_PREFIX = "token_";//找回密码的token 现在以用存入redis

    //将存入redis的有效期设为三十分钟
    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME=60*30;
    }
    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("Price_desc","price_asc");
    }
    public interface Role{
        int ROLE_CUSTOMER =0;//普通用户
        int ROLE_ADMIN= 1;//管理员
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"在线")
        ;

        private int code;
        private String value;

        ProductStatusEnum(int code,String value){
            this.value=value;
            this.code=code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
    //订单状态
    public enum OrderStatusEnum{
        CANXELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭")
        ;

        OrderStatusEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum value : values()) {
                if(value.getCode()==code){
                    return value;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
    public interface  Cart{
        int CHECKED=1;//购物车选中状态
        int UN_CHECKED=0;//购物车未选中状态
        //限制购物车数量
        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";//
        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
    }
    //交易状态
    public  interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY="WAIT_BUYER_PAY";//等待付款
        String TRADE_STATUS_TRADE_SUCCESS="TRADE_SUCCESS";//付款成功

        //返回值
        String RESPONSE_SUCCESS="success";
        String RESPONSE_FAILED="failed";

    }
    public enum PayplatforEnum{
        ALIPAY(1,"支付宝")
        ;
        PayplatforEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
    public enum paymentTypeEnum{
        ONLINE_PAY(1,"在线支付")
        ;

        paymentTypeEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static paymentTypeEnum codeOf(int code){
            for (paymentTypeEnum value : values()) {
                if(value.getCode()==code){
                    return value;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
    public interface REDIS_LOCK{
        String CLOSE_ORDER_TASK_LOCK="CLOSE_ORDER_TASK_LOCK";//关闭订单的分布式锁 存入Redis中的key
    }
}
