package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse create(Integer userId,Integer shippingId);

    ServerResponse detail (Integer userId, Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSie);
    ServerResponse<PageInfo> mangaeList(int pageNum,int pageSize);

    ServerResponse mangaeDetail(Long orderNo);
    ServerResponse<PageInfo> mangaeSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse manageSendGoods(Long orderNo);

    //hour个小时以内未付款的订单 进行关闭
    void closeOrder(int hour);
}
