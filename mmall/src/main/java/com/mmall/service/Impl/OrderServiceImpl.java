package com.mmall.service.Impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.service.IFileService;
import com.mmall.service.IOrderService;
import com.mmall.utils.BigDecimalUtil;
import com.mmall.utils.DateTimeUtil;
import com.mmall.utils.FTPUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderProductVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单
 */
@Service("iOrderService")
@Slf4j
public class OrderServiceImpl implements IOrderService {
   // private static final Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private PayInfoMapper payInfoMapper;//支付
    @Autowired
    private OrderMapper orderMapper;//订单
    @Autowired
    private OrderItemMapper orderItemMapper;//订单详情
    @Autowired
    private CartMapper cartMapper;//购物车
    @Autowired
    private ProductMapper productMapper;//产品
    @Autowired
    private ShippingMapper shippingMapper;//地址
    @Autowired
    private IFileService iFileService; //文件上传

    /**
     * list
     * @param userId
     * @param pageNum
     * @param pageSie
     * @return
     */
    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSie){
        PageHelper.startPage(pageNum,pageSie);
        //查询所有订单
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = this.assmbleOrderVoList(userId, orderList);
        PageInfo pageInfo=new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
    private List<OrderVo> assmbleOrderVoList(Integer userId,List<Order> orderList){
        List<OrderVo> orderVoList=Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList;
            if (userId == null) {
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else{
                 orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    /**
     * 获取订单的商品信息
     * @param userId
     * @return
     */
    public ServerResponse getOrderCartProduct(Integer userId){
        OrderProductVo orderProductVo=new OrderProductVo();
        //获取购物车列表
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);

        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        //组装 orderItemVo
        List<OrderItem> orderItemList =(List<OrderItem>) serverResponse.getData();
        //计算总价
        BigDecimal payment=new BigDecimal("0");
        List<OrderItemVo> orderItemVoList=Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            payment=BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        //组装 orderProductVo
        orderProductVo.setImageHost("ftp.server.http.prefix");
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);
        return ServerResponse.createBySuccess(orderProductVo);
    }

    /**
     * 订单详情detail
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse detail (Integer userId, Long orderNo){

        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("没有找到该订单");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        OrderVo orderVo=this.assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }
    /**
     *  取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse cancel (Integer userId,Long orderNo){
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        //判断 订单状态 如果不是未支付 则不能取消订单
        if(order.getStatus()!=Const.OrderStatusEnum.NO_PAY.getCode()){
             return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder=new Order();
        updateOrder.setId(order.getId());
        //修改订单未已取消状态
        updateOrder.setStatus(Const.OrderStatusEnum.CANXELED.getCode());
        int rowcount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(rowcount>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse create(Integer userId,Integer shippingId){
        //获取购物车的 已勾选的产品
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
        //查询订单子表
        ServerResponse cartOrderItem = this.getCartOrderItem(userId, cartList);
        if(!cartOrderItem.isSuccess()){
            return cartOrderItem;
        }
        List<OrderItem> orderItemList =(List<OrderItem>) cartOrderItem.getData();
        //总价
        BigDecimal payment=getOrderTotaPrice(orderItemList);
        //生成订单
        Order order=this.assembleOrder(userId,shippingId,payment);
        if(order==null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        //判断 orderItemList是否为空 遍历 写入订单号
        if(CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //给每个订单详情设置订单号
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis 批量插入
        orderItemMapper.batchInsert(orderItemList);
        //生成成功 减少库存
        this.reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);
        //返回前端所需要的数据
        OrderVo orderVo=this.assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);

    }

    /**
     * 组装 OrderVo
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo=new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.paymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping!=null){
            ShippingVo shippingVo=this.assembleShippingVo(shipping);
            orderVo.setShippingVo(shippingVo);
        }
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        //组装 orderItemVo
        List<OrderItemVo> orderItemVoList=Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo=this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * 组装orderItemVo
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo =new OrderItemVo();
        BeanUtils.copyProperties(orderItem,orderItemVo);
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }
    /**
     * 组装 shippingVo
     * @param shipping
     * @return
     */
    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo=new ShippingVo();
        BeanUtils.copyProperties(shipping,shippingVo);
        return shippingVo;
    }
    /**
     * 清空购物车
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList){
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }
    /**
     * 组装订单 写入数据库
     * @param userId
     * @param shippingId
     * @param payment
     * @return
     */
    private Order assembleOrder(Integer userId,Integer shippingId,BigDecimal payment){
        Order order=new Order();

        Long orderNo=this.genreateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.paymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        //发货时间
        //付款时间
        int rowCont = orderMapper.insert(order);
        if(rowCont>0) {
            return order;
        }
        return null;

    }

    /**
     * 减少商品库存
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }
    /**
     * 生成订单号
     * @return
     */
    private Long genreateOrderNo(){
        Long currentTime = System.currentTimeMillis();//当前时间
        return currentTime+new Random().nextInt(100);
    }
    /**
     * 计算所有订单子表的总价
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotaPrice(List<OrderItem> orderItemList){
        BigDecimal payment=new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment=BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }
    /**
     * 组装订单详情列表
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList){
        List<OrderItem> orderItemList=Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //校验购物车的状态
        for (Cart cartItem : cartList) {
            OrderItem orderItem=new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            //判断产品是否是在线状态
            if(Const.ProductStatusEnum.ON_SALE.getCode()!=product.getStatus()){
                return ServerResponse.createByErrorMessage("产品不在线");
            }
            //判断库存
            if(cartItem.getQuantity()>product.getStock()){
                return ServerResponse.createByErrorMessage("产品库存不足");
            }
            //组装 orderItem
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            //计算总价
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    /**
     * 管理员 后台查询订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> mangaeList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        //获取所有订单
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = this.assmbleOrderVoList(null, orderList);
        PageInfo pageInfo=new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 后台 查询订单详情
     * @param orderNo
     * @return
     */
    public ServerResponse<OrderVo> mangaeDetail(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);

        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 按号搜索
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> mangaeSearch(Long orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        PageInfo pageInfo=new PageInfo(Lists.newArrayList(orderVo));
        pageInfo.setList(Lists.newArrayList(orderVo));

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 发货
     * @param orderNo
     * @return
     */
    public  ServerResponse manageSendGoods(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);

        if(order!=null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccessMessage("发货成功");
            }
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    /**
     * hour 个小时以内未付款的订单，进行关闭
     * 关闭订单
     * @param hour 小时
     */
    @Override
    public void closeOrder(int hour) {
        //转化为 hour小时之前的时间
        Date closeDateTime = DateUtils.addHours(new Date(),-hour);
        //查询 未支付 和 过时的订单 拿到订单列表
        List<Order> orderList =orderMapper.selectOrderStatusByCreateTime(Const.OrderStatusEnum.NO_PAY.getCode(),DateTimeUtil.dateToStr(closeDateTime));
        //遍历订单列表
        for (Order order : orderList) {
            //根据订单号拿到订单详情列表
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            //遍历订单详情列表
            for (OrderItem orderItem : orderItemList) {
                //查询该商品的库存 并把库存更新回去
                //一定要用主键 where条件 防止锁表 同时必须时支持MySQL 的InnoDB
                //根据订单详情列表的商品id查询商品的库存
                Integer stock = productMapper.selectStockByProductId(orderItem.getProductId());
                //判断库存 是否空 考虑到已生成的订单里的商品，被删除的情况
                if(stock==null){
                    //为空 跳出此次循环
                    continue;
                }
                //更行库存
                Product product=new Product();
                product.setId(orderItem.getProductId());
                //把订单中的库存返回到原始库存
                product.setStock(stock+orderItem.getQuantity());
                productMapper.updateByPrimaryKeySelective(product);
            }
            //关闭订单 根据订单号 修改订单状态为已取消 状态码 0
            orderMapper.closeOrderByOrderId(order.getId());
            log.info("关闭订单OrderNo:{}",order.getOrderNo());
        }


    }


    /**
     * 支付
     * 返回 订单号和 二维码的路径
     * @param orderNo
     * @param userId
     * @param path
     * @return
     */
    public ServerResponse pay(Long orderNo,Integer userId,String path) {
        Map<String,String> resultMap = Maps.newHashMap();
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order==null) {
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        resultMap.put("order",String.valueOf(order.getOrderNo()));
        //2.组装生成支付宝订单
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuffer().append("happymmall扫码支付,订单号：").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuffer().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        List<OrderItem> orderItemList=orderItemMapper.getByOrderNoUserId(orderNo,userId);
        for (OrderItem orderItem : orderItemList) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(),
                    orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),
                            new BigDecimal(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }


        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder =new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                //细节  path 前面 必须有/
                String qrPath = String.format(path+"/qr-%s.png",
                        response.getOutTradeNo());
                String qrFileName=String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);//生成二维码
                File qrFile = new File(qrPath);
                byte[] bytes;
                try {
                    bytes = IOUtils.toByteArray(new FileInputStream(qrFile));
                } catch (IOException e) {
                    return ServerResponse.createByErrorMessage("二维码上传失败");
                }
                String fileName = String.format("qr-%s.png",response.getOutTradeNo());
                iFileService.upload(bytes, fileName);
                String qrUrl = PropertiesUtil.getProperty("qiniu.resource.server.http.prefix") + fileName;
                resultMap.put("qrUrl", qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }
    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    /**
     * 支付宝回调
     * @param params
     * @return
     */
    public ServerResponse aliCallback(Map<String,String> params){
        //获取 原商户订单号 将其转化为 long类型
        Long orderNo = Long.parseLong (params.get("out_trade_no"));
        //支付宝交易号
        String tradeNO = params.get("trade_no");
        //获取交易状态
        String tradeStatus = params.get("trade_status");
        //查询订单是否是此商户订单号
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("非快乐慕商城的订单,回调忽略");
        }
        //判断订单状态
        if(order.getStatus()>= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccessMessage("重复回调");
        }
        //判断是否是交易成功 如果是 把订单状态修改为已付款
        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            //修改订单状态
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        //组装 PageInfo
        PayInfo payInfo=new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayplatforEnum.ALIPAY.getCode());//支付包平台
        payInfo.setPlatformNumber(tradeNO);//设置交易号
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insert(payInfo);
        return ServerResponse.createBySuccess();
    }

    /**
     * 查询订单状态
     * @param userId
     * @param orderNo
     * @return
     */
    public ServerResponse queryOrderPayStatus(Integer userId,Long orderNo){
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order==null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        //判断订单状态
        if(order.getStatus()>= Const.OrderStatusEnum.PAID.getCode()){
            //当订单状态大于已付款
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
