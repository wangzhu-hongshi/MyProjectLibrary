package com.mmall.service;

import com.mmall.common.ServerResponse;

public interface IOrderItmeService {
    ServerResponse list(Integer userId, Long orderNO);
    ServerResponse sele(Integer Id);
}
