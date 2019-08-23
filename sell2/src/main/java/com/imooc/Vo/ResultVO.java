package com.imooc.Vo;

import lombok.Data;

import java.util.List;

/**
 * http请求的最外层对象
 */
@Data
public class ResultVO<E> {
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回的内容
    private E Data;
}
