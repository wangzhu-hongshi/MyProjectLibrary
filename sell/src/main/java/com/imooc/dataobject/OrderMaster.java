package com.imooc.dataobject;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@Entity
@Data
@DynamicUpdate//自动更新时间
public class OrderMaster {
    //订单id
    @Id
    private  String orderId;
    //买家名字
    private  String buyerName;
    //买家手机号
    private  String buyerPhone;
    //买家地址
    private  String buyerAddress;
    //买家微信
    private  String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态  默认下 为 新订单状态
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    //支付状态 默认为 未支付
    private Integer payStatus= PayStatusEnum.WAII.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}
