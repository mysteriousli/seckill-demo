package com.example.seckilldemo.vo;

import com.example.seckilldemo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int seckillStatus;
    private int remainSeconds;
}
