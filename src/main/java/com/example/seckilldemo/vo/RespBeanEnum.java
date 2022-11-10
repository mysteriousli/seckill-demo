package com.example.seckilldemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor//全参构造

public enum RespBeanEnum {
    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(201, "服务器异常！"),
    //登录模块
    LOGIN_ERROR(500210, "用户名或密码错误"),
    PASSWORD_UPDATE_ERROR(500214, "更新密码失败"),
    MOBILE_ERROR(500211, "手机号码不正确"),
    MOBILE_EXITS_ERROR(500213, "手机号码不存在"),
    //
    BIND_ERROR(500212,"参数校验异常！"),
    //秒杀
    EMPTY_ERROR(500500,"库存不足"),
    REPEATE_ERROR(500501,"重复抢购"),
    SESSION_ERROR(500215,"用户不存在"),
    //订单
    ORDER_NOT_EXIST(500300,"订单信息不存在")
    ;

    private final Integer code;
    private final String message;
}
