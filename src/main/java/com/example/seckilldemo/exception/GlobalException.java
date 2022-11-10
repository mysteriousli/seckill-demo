package com.example.seckilldemo.exception;

import com.example.seckilldemo.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{//转化为异常
    private RespBeanEnum respBeanEnum;
}
