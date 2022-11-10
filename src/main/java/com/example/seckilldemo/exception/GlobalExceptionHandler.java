package com.example.seckilldemo.exception;

import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice//直接返回responbody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//异常拦截
    public RespBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {//属于全局异常
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        } else if (e instanceof BindException) {//抛出异常判断
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常：" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        log.info(e.toString());
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
