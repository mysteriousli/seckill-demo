package com.example.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckilldemo.pojo.Goods;
import com.example.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liguangyuan
 * @since 2022-04-06
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<GoodsVo> findGoodsVo();
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
