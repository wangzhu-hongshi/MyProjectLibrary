package com.imooc.VO;

import lombok.Data;

import java.util.List;

/**
 * http请求返回的外层对象
 */
@Data
public class ResultVO<T> {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 返回的具体内容
     */
    private List<T> data;
}
