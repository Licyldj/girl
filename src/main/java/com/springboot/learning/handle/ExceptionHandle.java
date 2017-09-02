package com.springboot.learning.handle;

import com.springboot.learning.domain.Result;
import com.springboot.learning.exception.GirlException;
import com.springboot.learning.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by coder on 2017/8/26.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if (e instanceof GirlException){
            GirlException girlException = (GirlException)e;
            return ResultUtil.errot(girlException.getCode(),girlException.getMessage());
        }else {
            logger.error("【系统异常】{}",e);
            return ResultUtil.errot(-1,"未知错误");
        }
    }
}
