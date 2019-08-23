package com.imooc.utils;

import com.imooc.VO.ResultVO;

import java.util.List;

/**
 * ResultUtils 数据封装的工具类
 */
public class ResultUtils {
    public static ResultVO sucess(List list){
        ResultVO resultVO=new ResultVO();
        resultVO.setData(list);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO sucess(){
        return sucess(null);
    }
    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
