package com.zhuzirui.brt.config;

import com.zhuzirui.brt.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result<String> ex(Exception e){
        return Result.error(500, e.getMessage());
    }

}
