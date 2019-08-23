package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 表单验证的对象
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家电话
     */
    @NotEmpty(message = "手机号必填必填")
    private String phone;

    /**
     * 买家地
     */
    @NotEmpty(message = "地址必填必填")
    private String address;

    /**
     * 买家微信号
     */
    @NotEmpty(message = "微信号必填")
    private String openid;

    /**
     * 买家购物车
     */
    @NotEmpty(message = "购物车必填")
    private String items;


}
