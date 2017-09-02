package com.springboot.learning.utils;

import com.springboot.learning.domain.Result;

/**
 * Created by coder on 2017/8/26.
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success(){

        return success(null);
    }

    public static Result errot(Integer code ,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
